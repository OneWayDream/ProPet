package ru.itis.backend.services;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.backend.dto.ActivationLinkDto;
import ru.itis.backend.dto.UserDto;
import ru.itis.backend.exceptions.EntityNotExistsException;
import ru.itis.backend.exceptions.EntityNotFoundException;
import ru.itis.backend.exceptions.LoginAlreadyInUseException;
import ru.itis.backend.exceptions.MailAlreadyInUseException;
import ru.itis.backend.models.User;
import ru.itis.backend.models.UserState;
import ru.itis.backend.repositories.UsersRepository;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

    @NonNull
    protected UsersRepository repository;
    @NonNull
    protected ActivationLinksService activationLinksService;

    @Override
    public List<UserDto> findAll() {
        return UserDto.from(repository.findAll().stream()
                .filter(entry -> !entry.getIsDeleted())
                .collect(Collectors.toList()));
    }

    @Override
    public void delete(UserDto userDto) {
        try{
            User entityToDelete = repository.findById(userDto.getId())
                    .filter(entry -> !entry.getIsDeleted())
                    .orElseThrow(EntityNotExistsException::new);
            entityToDelete.setIsDeleted(true);
            repository.save(entityToDelete);
        } catch (Exception ex){
            if (ex instanceof EntityNotExistsException){
                throw ex;
            }
            throw new PersistenceException(ex);
        }
    }

    @Override
    public UserDto add(UserDto userDto) {
        User newEntity = UserDto.to(userDto);
        repository.save(newEntity);
        return UserDto.from(newEntity);
    }

    @Override
    public UserDto findById(Long aLong) {
        return UserDto.from(repository.findById(aLong)
                .filter(entry -> !entry.getIsDeleted())
                .orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public UserDto update(UserDto userDto) {
        try{
            findById(userDto.getId());
            User updatedEntity = repository.save(UserDto.to(userDto));
            return UserDto.from(updatedEntity);
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

    @Override
    public UserDto activateUser(String linkValue) {
        ActivationLinkDto activationLinkDto = activationLinksService.findByLinkValue(linkValue);
        User userForActivation = repository.getById(activationLinkDto.getAccountId());
        userForActivation.setState(UserState.ACTIVE);
        repository.save(userForActivation);
        activationLinksService.delete(activationLinkDto);
        return UserDto.from(userForActivation);
    }

    @Override
    public void banUser(Long userId) {
        try{
            User userForBan = repository.findById(userId)
                    .filter(entry -> entry.getIsDeleted()==null)
                    .orElseThrow(EntityNotExistsException::new);
            userForBan.setState(UserState.BANNED);
            repository.save(userForBan);
        } catch (Exception ex){
            throw new PersistenceException(ex);
        }
    }

    @Override
    public UserDto findUserByLogin(String login) {
        return UserDto.from(repository.findByLogin(login)
                .filter(entry -> !entry.getIsDeleted())
                .orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public UserDto findUserByMail(String mail) {
        return UserDto.from(repository.findByMail(mail)
                .filter(entry -> !entry.getIsDeleted())
                .orElseThrow(EntityNotFoundException::new));
    }
}
