package ru.itis.backend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.backend.dto.ActivationLinkDto;
import ru.itis.backend.dto.RegistrationForm;
import ru.itis.backend.dto.AccountDto;
import ru.itis.backend.exceptions.*;
import ru.itis.backend.models.ActivationLink;
import ru.itis.backend.models.Account;
import ru.itis.backend.models.UserRole;
import ru.itis.backend.models.UserState;
import ru.itis.backend.repositories.ActivationLinkRepository;
import ru.itis.backend.repositories.AccountRepository;
import ru.itis.backend.security.managers.TokenManager;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    protected final ActivationLinksService activationLinksService;
    protected final AccountService accountService;
    protected final TokenManager tokenManager;


    @Override
    public AccountDto registerNewAccount(RegistrationForm registrationForm) {
        if (!registrationForm.getPassword().equals(registrationForm.getRepeatedPassword())){
            throw new DifferentPasswordsException("The passwords are different");
        }
        try {
            AccountDto newAccount = AccountDto.builder()
                    .login(registrationForm.getLogin())
                    .mail(registrationForm.getMail())
                    .password(registrationForm.getPassword())
                    .state(UserState.NOT_ACTIVATED)
                    .role(UserRole.USER)
                    .registrationDate(new Date(System.currentTimeMillis()))
                    .city(registrationForm.getCity())
                    .sitterStatus(false)
                    .build();
            newAccount = accountService.add(newAccount);
            ActivationLinkDto link = ActivationLinkDto.builder()
                    .accountId(newAccount.getId())
                    .linkValue(UUID.randomUUID().toString())
                    .build();
            activationLinksService.add(link);
            accountService.activateUser(link.getLinkValue());
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
