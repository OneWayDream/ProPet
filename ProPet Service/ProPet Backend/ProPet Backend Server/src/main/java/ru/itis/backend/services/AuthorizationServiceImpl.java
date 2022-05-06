package ru.itis.backend.services;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.backend.dto.AuthorizationForm;
import ru.itis.backend.dto.AccountDto;
import ru.itis.backend.exceptions.BannedUserException;
import ru.itis.backend.exceptions.IncorrectPasswordException;
import ru.itis.backend.exceptions.NotActivatedUserException;

@Service
@RequiredArgsConstructor
public class AuthorizationServiceImpl implements AuthorizationService {

    @NonNull
    protected AccountService usersService;
    @NonNull
    protected PasswordEncoder passwordEncoder;

    @Override
    public AccountDto authorizeUser(AuthorizationForm authorizationForm) {
        AccountDto userDto;
        if (authorizationForm.getLogin() != null){
            userDto = usersService.findUserByLogin(authorizationForm.getLogin());
        } else {
            userDto = usersService.findUserByMail(authorizationForm.getMail());
        }
        switch (userDto.getState()){
            case ACTIVE -> {
                if (userDto.getHashPassword().equals(passwordEncoder.encode(authorizationForm.getPassword()))){
                    return userDto;
                } else {
                    throw new IncorrectPasswordException("Incorrect user data");
                }
            }
            case NOT_ACTIVATED -> throw new NotActivatedUserException("This user has an account which is not activated");
            case BANNED -> throw new BannedUserException("This user is banned.");
        }
        return null;
    }

}
