package ru.itis.backend.services;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.backend.dto.rest.AppealAnswerDto;
import ru.itis.backend.exceptions.persistence.EntityNotExistsException;
import ru.itis.backend.exceptions.persistence.EntityNotFoundException;
import ru.itis.backend.models.AppealAnswer;
import ru.itis.backend.repositories.AppealAnswerRepository;
import ru.itis.backend.utils.PropertiesUtils;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppealAnswerServiceImpl implements AppealAnswerService {

    @NonNull
    protected AppealAnswerRepository repository;

    @Override
    public List<AppealAnswerDto> findAll() {
        return AppealAnswerDto.from(repository.findAll().stream()
                .filter(entry -> !entry.getIsDeleted())
                .collect(Collectors.toList()));
    }

    @Override
    public void delete(AppealAnswerDto appealAnswerDto) {
        try{
            AppealAnswer entityToDelete = repository.findById(appealAnswerDto.getId())
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
    public AppealAnswerDto add(AppealAnswerDto appealAnswerDto) {
        AppealAnswer newEntity = AppealAnswerDto.to(appealAnswerDto);
        repository.save(newEntity);
        return AppealAnswerDto.from(newEntity);
    }

    @Override
    public AppealAnswerDto findById(Long aLong) {
        return AppealAnswerDto.from(repository.findById(aLong)
                .filter(entry -> !entry.getIsDeleted())
                .orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public AppealAnswerDto update(AppealAnswerDto appealAnswerDto) {
        AppealAnswerDto entity = findById(appealAnswerDto.getId());
        PropertiesUtils.copyNonNullProperties(appealAnswerDto, entity);
        AppealAnswer updatedEntity = repository.save(AppealAnswerDto.to(entity));
        return AppealAnswerDto.from(updatedEntity);
    }

}
