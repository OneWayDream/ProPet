package ru.itis.backend.services;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.backend.dto.RegistrationForm;
import ru.itis.backend.dto.UserDto;
import ru.itis.backend.exceptions.DifferentPasswordsException;
import ru.itis.backend.exceptions.LoginAlreadyInUseException;
import ru.itis.backend.exceptions.MailAlreadyInUseException;
import ru.itis.backend.models.ActivationLink;
import ru.itis.backend.models.User;
import ru.itis.backend.models.UserState;
import ru.itis.backend.repositories.ActivationLinkRepository;
import ru.itis.backend.repositories.UsersRepository;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    @NonNull
    protected PasswordEncoder passwordEncoder;
    @NonNull
    protected UsersRepository usersRepository;
    @NonNull
    protected ActivationLinkRepository activationLinkRepository;

    @Override
    public UserDto registerNewUser(RegistrationForm registrationForm) {
        if (registrationForm.getPassword().equals(registrationForm.getRepeatedPassword())){
            try {
            User newUser = User.builder()
                    .login(registrationForm.getLogin())
                    .mail(registrationForm.getMail())
                    .hashPassword(passwordEncoder.encode(registrationForm.getPassword()))
                    .state(UserState.NOT_ACTIVATED)
                    .isDeleted(false)
                    .registrationDate(new Date(System.currentTimeMillis()))
                    .imageKey("default.png")
                    .build();
                usersRepository.save(newUser);
                ActivationLink link = ActivationLink.builder()
                        .accountId(newUser.getId())
                        .linkValue(UUID.randomUUID().toString())
                        .isDeleted(false)
                        .build();
                activationLinkRepository.save(link);
                return UserDto.from(newUser);
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
        } else {
            throw new DifferentPasswordsException("The passwords are different");
        }

    }

}
