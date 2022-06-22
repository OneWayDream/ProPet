package ru.itis.pdfgenerator.amqp;

import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.pdfgenerator.dto.PetTransferTreatyData;
import ru.itis.pdfgenerator.dto.PetTransferTreatyResponse;
import ru.itis.pdfgenerator.exceptions.persistence.EntityNotFoundException;
import ru.itis.pdfgenerator.exceptions.token.BannedTokenException;
import ru.itis.pdfgenerator.exceptions.token.ExpiredJwtException;
import ru.itis.pdfgenerator.models.PetTransferPdf;
import ru.itis.pdfgenerator.security.JwtAuthenticationManager;
import ru.itis.pdfgenerator.services.PetTransferPdfService;
import ru.itis.pdfgenerator.utils.FileWorker;
import ru.itis.pdfgenerator.utils.PdfGenerator;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.UUID;

@Component
@Slf4j
public class RequestMessageListener {

    protected final JwtAuthenticationManager jwtAuthenticationManager;
    protected final PdfGenerator pdfGenerator;
    protected final PetTransferPdfService service;
    protected final FileWorker fileWorker;

    @Autowired
    public RequestMessageListener(
            JwtAuthenticationManager jwtAuthenticationManager,
            PdfGenerator pdfGenerator,
            PetTransferPdfService service,
            FileWorker fileWorker
    ){
        this.jwtAuthenticationManager = jwtAuthenticationManager;
        this.pdfGenerator = pdfGenerator;
        this.service = service;
        this.fileWorker = fileWorker;
    }

    @RabbitListener(queues = "#{queue.name}")
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public PetTransferTreatyResponse receive(PetTransferTreatyData treatyData, Message message) {
        try{
            if (handleAuthorization(message)){
                byte[] pdf;
                try{
                    PetTransferPdf petTransferPdf =  service.findByApplyId(treatyData.getApplyId());
                    pdf = fileWorker.loadPetTransferPdf(petTransferPdf.getImageKey());
                } catch (EntityNotFoundException ex){
                    pdf = pdfGenerator.generatePdf(treatyData);
                    String fileName = UUID.randomUUID() + ".pdf";
                    fileWorker.savePetTransferPdf(pdf, fileName);
                    service.add(PetTransferPdf.builder()
                            .applyId(treatyData.getApplyId())
                            .imageKey(fileName)
                            .build());
                }
                return PetTransferTreatyResponse.builder()
                        .status(PetTransferTreatyResponse.Status.SUCCESS)
                        .pdf(pdf)
                        .build();
            } else {
                return null;
            }
        } catch (BannedTokenException ex){
            return PetTransferTreatyResponse.builder()
                    .status(PetTransferTreatyResponse.Status.BANNED_TOKEN)
                    .build();
        } catch (ExpiredJwtException ex) {
            return PetTransferTreatyResponse.builder()
                    .status(PetTransferTreatyResponse.Status.EXPIRED_TOKEN)
                    .build();
        } catch (JWTVerificationException ex){
            return PetTransferTreatyResponse.builder()
                    .status(PetTransferTreatyResponse.Status.INVALID_TOKEN)
                    .build();
        } catch (Exception ex){
            log.error(getStackTraceString(ex));
            return PetTransferTreatyResponse.builder()
                    .status(PetTransferTreatyResponse.Status.FAULT)
                    .build();
        }
    }

    protected boolean handleAuthorization(Message message){
        return jwtAuthenticationManager.handleToken(message.getMessageProperties().getHeader("JWT-access"));
    }

    protected String getStackTraceString(Exception exception){
        StringWriter sw = new StringWriter();
        exception.printStackTrace(new PrintWriter(sw));
        String exceptionAsString = sw.toString();
        try{
            sw.close();
        } catch (Exception ex){
            //ignore
        }
        return exceptionAsString;
    }


}
