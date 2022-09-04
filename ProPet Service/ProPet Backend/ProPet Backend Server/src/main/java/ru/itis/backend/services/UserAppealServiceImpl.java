package ru.itis.backend.services;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.backend.dto.app.UserAppealDto;
import ru.itis.backend.exceptions.persistence.EntityNotExistsException;
import ru.itis.backend.exceptions.persistence.EntityNotFoundException;
import ru.itis.backend.models.UserAppeal;
import ru.itis.backend.repositories.UserAppealRepository;
import ru.itis.backend.utils.PropertiesUtils;

import javax.persistence.PersistenceException;
import java.time.LocalDate;
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
                    .orElseThrow(() -> new EntityNotExistsException(" user appeal."));
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
        userAppealDto.setIsClosed(false);
        userAppealDto.setSendDate(LocalDate.now());
        UserAppeal newEntity = UserAppealDto.to(userAppealDto);
        repository.save(newEntity);
        return UserAppealDto.from(newEntity);
    }

    @Override
    public UserAppealDto findById(Long aLong) {
        return UserAppealDto.from(repository.findById(aLong)
                .filter(entry -> !entry.getIsDeleted())
                .orElseThrow(() -> new EntityNotFoundException(" user appeal.")));
    }

    @Override
    public UserAppealDto update(UserAppealDto userAppealDto) {
        UserAppealDto entity = findById(userAppealDto.getId());
        PropertiesUtils.copyNonNullProperties(userAppealDto, entity);
        UserAppeal updatedEntity = repository.save(UserAppealDto.to(entity));
        return UserAppealDto.from(updatedEntity);
    }

    @Override
    public List<UserAppealDto> getAllByUserId(Long userId) {
        return UserAppealDto.from(repository.findAllByAccountId(userId).stream()
                .filter(entry -> !entry.getIsDeleted())
                .collect(Collectors.toList()));
    }
}
