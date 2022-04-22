package ru.itis.backend.services;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.backend.dto.UserAppealDto;
import ru.itis.backend.exceptions.EntityNotExistsException;
import ru.itis.backend.exceptions.EntityNotFoundException;
import ru.itis.backend.models.UserAppeal;
import ru.itis.backend.repositories.UserAppealRepository;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserAppealServiceImpl implements UserAppealService {

    @NonNull
    protected UserAppealRepository repository;

    @Override
    public List<UserAppealDto> findAll() {
        return UserAppealDto.from(repository.findAll().stream()
                .filter(entry -> !entry.getIsDeleted())
                .collect(Collectors.toList()));
    }

    @Override
    public void delete(UserAppealDto userAppealDto) {
        try{
            UserAppeal entityToDelete = repository.findById(userAppealDto.getId())
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
    public UserAppealDto add(UserAppealDto userAppealDto) {
        UserAppeal newEntity = UserAppealDto.to(userAppealDto);
        repository.save(newEntity);
        return UserAppealDto.from(newEntity);
    }

    @Override
    public UserAppealDto findById(Long aLong) {
        return UserAppealDto.from(repository.findById(aLong)
                .filter(entry -> !entry.getIsDeleted())
                .orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public UserAppealDto update(UserAppealDto userAppealDto) {
        findById(userAppealDto.getId());
        UserAppeal updatedEntity = repository.save(UserAppealDto.to(userAppealDto));
        return UserAppealDto.from(updatedEntity);
    }

    @Override
    public List<UserAppealDto> getAllByUserId(Long userId) {
        return UserAppealDto.from(repository.findAllByUserId(userId).stream()
                .filter(entry -> !entry.getIsDeleted())
                .collect(Collectors.toList()));
    }
}
