package ru.itis.backend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.backend.dto.forms.ActivationMailForm;
import ru.itis.backend.dto.forms.RegistrationForm;
import ru.itis.backend.dto.app.AccountDto;
import ru.itis.backend.dto.app.SitterInfoDto;
import ru.itis.backend.dto.rest.AccountRestDto;
import ru.itis.backend.dto.rest.ActivationLinkDto;
import ru.itis.backend.exceptions.registration.LoginAlreadyInUseException;
import ru.itis.backend.exceptions.registration.MailAlreadyInUseException;
import ru.itis.backend.models.UserRole;
import ru.itis.backend.models.UserState;
import ru.itis.backend.security.managers.TokenManager;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    protected final ActivationLinkService activationLinksService;
    protected final AccountService accountService;
    protected final SitterInfoService sitterInfoService;
    protected final TokenManager tokenManager;
    protected final MailSenderService mailSenderService;


    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public AccountDto registerNewAccount(RegistrationForm registrationForm) {
        try {
            AccountRestDto newAccount = AccountRestDto.builder()
                    .login(registrationForm.getLogin())
                    .mail(registrationForm.getMail())
                    .password(registrationForm.getPassword())
                    .state(UserState.NOT_ACTIVATED)
                    .role(UserRole.USER)
                    .registrationDate(LocalDate.now())
                    .city(registrationForm.getCity())
                    .build();
            newAccount = accountService.addRest(newAccount);

            ActivationLinkDto link = ActivationLinkDto.builder()
                    .accountId(newAccount.getId())
                    .linkValue(UUID.randomUUID().toString())
                    .build();
            activationLinksService.add(link);

            SitterInfoDto sitterInfo = SitterInfoDto.builder()
                    .accountId(newAccount.getId())
                    .sitterStatus(false)
                    .build();
            sitterInfo = sitterInfoService.add(sitterInfo);
            newAccount.setSitterInfoDto(sitterInfo);

//            accountService.activateUser(link.getLinkValue());
            mailSenderService.sendConfirmMail(ActivationMailForm.builder()
                            .login(newAccount.getLogin())
                            .mail(newAccount.getMail())
                            .activationLink(link.getLinkValue())
                            .build());

            return newAccount;
        } catch (Exception ex){
            try{
                String message = ex.getCause().getCause().getMessage();
                if (message.contains("account_mail_key")){
                    throw new MailAlreadyInUseException(ex);
                } else if (message.contains("account_login_key")){
                    throw new LoginAlreadyInUseException(ex);
                }
            } catch (NullPointerException exception){
                //ignore
            }
            throw ex;
        }
    }
}
