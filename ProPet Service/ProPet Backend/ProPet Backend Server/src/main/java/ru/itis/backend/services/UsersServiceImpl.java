package ru.itis.backend.services;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.backend.dto.ActivationLinkDto;
import ru.itis.backend.dto.UserDto;
import ru.itis.backend.exceptions.EntityNotExistsException;
import ru.itis.backend.exceptions.EntityNotFoundException;
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
    protected UsersRepository usersRepository;
    @NonNull
    protected ActivationLinksService activationLinksService;

    @Override
    public List<UserDto> findAll() {
        return UserDto.from(usersRepository.findAll().stream()
                .filter(user -> !user.getIsDeleted())
                .collect(Collectors.toList()));
    }

    @Override
    public void delete(UserDto userDto) {
        try{
            User userForDelete = usersRepository.findById(userDto.getId())
                    .filter(user -> user.getIsDeleted()==null)
                    .orElseThrow(EntityNotExistsException::new);
            userForDelete.setIsDeleted(true);
            usersRepository.save(userForDelete);
        } catch (Exception ex){
            throw new PersistenceException(ex);
        }
    }

    @Override
    public UserDto add(UserDto userDto) {
        User newUser = UserDto.to(userDto);
        usersRepository.save(newUser);
        return UserDto.from(newUser);
    }

    @Override
    public UserDto findById(Long aLong) {
        return UserDto.from(usersRepository.findById(aLong)
                .filter(user -> !user.getIsDeleted())
                .orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public UserDto update(UserDto userDto) {
        User updatedUser = usersRepository.save(UserDto.to(userDto));
        return UserDto.from(updatedUser);
    }

    @Override
    public UserDto activateUser(String linkValue) {
        ActivationLinkDto activationLinkDto = activationLinksService.findByLinkValue(linkValue);
        User userForActivation = usersRepository.getById(activationLinkDto.getAccountId());
        userForActivation.setState(UserState.ACTIVE);
        usersRepository.save(userForActivation);
        activationLinksService.delete(activationLinkDto);
        return UserDto.from(userForActivation);
    }

    @Override
    public void banUser(Long userId) {
        try{
            User userForBan = usersRepository.findById(userId)
                    .filter(item -> item.getIsDeleted()==null)
                    .orElseThrow(EntityNotExistsException::new);
            userForBan.setState(UserState.BANNED);
            usersRepository.save(userForBan);
        } catch (Exception ex){
            throw new PersistenceException(ex);
        }
    }

    @Override
    public UserDto findUserByLogin(String login) {
        return UserDto.from(usersRepository.findByLogin(login)
                .filter(user -> !user.getIsDeleted())
                .orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public UserDto findUserByMail(String mail) {
        return UserDto.from(usersRepository.findByMail(mail)
                .filter(user -> !user.getIsDeleted())
                .orElseThrow(EntityNotFoundException::new));
    }
}
