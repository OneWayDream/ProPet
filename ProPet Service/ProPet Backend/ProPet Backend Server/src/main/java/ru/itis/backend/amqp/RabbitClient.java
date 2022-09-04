package ru.itis.backend.amqp;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import ru.itis.backend.dto.app.PetInfoDto;
import ru.itis.backend.dto.app.PetTransferApplyDto;
import ru.itis.backend.dto.app.TreatyInfoDto;
import ru.itis.backend.entities.PdfResponse;
import ru.itis.backend.entities.PetTransferTreatyData;
import ru.itis.backend.exceptions.pdf.FaultResponseException;
import ru.itis.backend.exceptions.token.BannedTokenException;
import ru.itis.backend.security.managers.TokenManager;
import ru.itis.backend.services.PetInfoService;
import ru.itis.backend.services.TreatyInfoService;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

@Component
public class RabbitClient implements AmqpClient {

    protected final RabbitTemplate template;
    protected final AuthenticationMessagePostProcessor messagePostProcessor;
    protected final TokenManager tokenManager;
    protected final TreatyInfoService treatyInfoService;
    protected final PetInfoService petInfoService;

    protected final String PET_TRANSFER_TREATY_ROUTING_KEY;
    protected final String REQUESTS_EXCHANGE_NAME;

    @Autowired
    public RabbitClient(RabbitTemplate template,
                        AuthenticationMessagePostProcessor messagePostProcessor,
                        @Value("${pdf.reports.pet-transfer-treaty.routing-key}") String petTransferTreatyRoutingKey,
                        @Value("${pdf.reports.requests-exchange-name}") String requestsExchangeName,
                        TokenManager tokenManager,
                        TreatyInfoService treatyInfoService,
                        PetInfoService petInfoService
    ){
        template.setReplyTimeout(20000);
        this.template = template;
        this.messagePostProcessor = messagePostProcessor;
        PET_TRANSFER_TREATY_ROUTING_KEY = petTransferTreatyRoutingKey;
        REQUESTS_EXCHANGE_NAME = requestsExchangeName;
        this.tokenManager = tokenManager;
        this.treatyInfoService = treatyInfoService;
        this.petInfoService = petInfoService;
    }

    @Override
    public byte[] handlePetTransferTreaty(PetTransferApplyDto transferApply, LocalDate creationDate) {
        TreatyInfoDto customerTreatyInfo = treatyInfoService.getByAccountId(transferApply.getCustomerId());
        TreatyInfoDto performerTreatyInfo = treatyInfoService.getByAccountId(transferApply.getPerformerId());
        PetInfoDto petInfo = petInfoService.findById(transferApply.getPetId());
        PetTransferTreatyData data = PetTransferTreatyData.builder()
                .applyId(transferApply.getId())
                .customerId(transferApply.getCustomerId())
                .customerName(customerTreatyInfo.getName())
                .customerSurname(customerTreatyInfo.getSurname())
                .customerPatronymic(customerTreatyInfo.getPatronymic())
                .customerPassportSeries(customerTreatyInfo.getPassportSeries())
                .customerPassportNumber(customerTreatyInfo.getPassportNumber())
                .customerPassportIssuingPlace(customerTreatyInfo.getPassportIssuingPlace())
                .customerPlaceOfResidence(customerTreatyInfo.getPlaceOfResidence())
                .customerPhone(customerTreatyInfo.getPhone())
                .performerId(transferApply.getPerformerId())
                .performerName(performerTreatyInfo.getName())
                .performerSurname(performerTreatyInfo.getSurname())
                .performerPatronymic(performerTreatyInfo.getPatronymic())
                .performerPassportSeries(performerTreatyInfo.getPassportSeries())
                .performerPassportNumber(performerTreatyInfo.getPassportNumber())
                .performerPassportIssuingPlace(performerTreatyInfo.getPassportIssuingPlace())
                .performerPlaceOfResidence(performerTreatyInfo.getPlaceOfResidence())
                .performerPhone(performerTreatyInfo.getPhone())
                .petId(petInfo.getId())
                .petKind(petInfo.getKind())
                .petBreed(petInfo.getBreed())
                .petNickname(petInfo.getNickname())
                .petYearsAge((int) ChronoUnit.YEARS.between(petInfo.getBirthDate(), LocalDate.now()))
                .petMonthAge((int) (ChronoUnit.MONTHS.between(petInfo.getBirthDate(), LocalDate.now()) % 12))
                .creationDay(creationDate.getDayOfMonth())
                .creationMonth(creationDate.getMonth().getDisplayName(TextStyle.FULL, new Locale("ru")))
                .creationYear(creationDate.getYear())
                .city(transferApply.getCity())
                .build();
        return handleReportRequest(data, PET_TRANSFER_TREATY_ROUTING_KEY, false);
    }

    protected byte[] handleReportRequest(Object reportData, String routingKey, Boolean reCall){
        messagePostProcessor.setAccessToken(tokenManager.getAccessToken());
        CorrelationData correlationData = new CorrelationData();
        PdfResponse response =
                template.convertSendAndReceiveAsType(
                        REQUESTS_EXCHANGE_NAME,
                        routingKey,
                        reportData,
                        messagePostProcessor,
                        correlationData,
                        new ParameterizedTypeReference<>() {
                        });

        if (response != null){
            if (response.getStatus().equals(PdfResponse.Status.SUCCESS)){
                return response.getPdf();
            } else if ((response.getStatus().equals(PdfResponse.Status.EXPIRED_TOKEN))
                    || (response.getStatus().equals(PdfResponse.Status.INVALID_TOKEN))){
                tokenManager.dropAccessToken();
            } else if (response.getStatus().equals(PdfResponse.Status.BANNED_TOKEN)){
                throw new BannedTokenException();
            } else {
                throw new FaultResponseException();
            }
        }
        if (reCall){
            throw new FaultResponseException();
        }
        return handleReportRequest(reportData, routingKey, true);
    }

}
