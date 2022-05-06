package ru.itis.backend.services;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.backend.dto.CommentAboutSitterDto;
import ru.itis.backend.exceptions.EntityNotExistsException;
import ru.itis.backend.exceptions.EntityNotFoundException;
import ru.itis.backend.models.CommentAboutSitter;
import ru.itis.backend.repositories.CommentAboutSitterRepository;
import ru.itis.backend.utils.PropertiesUtils;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentAboutSitterServiceImpl implements CommentAboutSitterService {

    @NonNull
    protected CommentAboutSitterRepository repository;

    @Override
    public List<CommentAboutSitterDto> findAll() {
        return CommentAboutSitterDto.from(repository.findAll().stream()
                .filter(entry -> !entry.getIsDeleted())
                .collect(Collectors.toList()));
    }

    @Override
    public void delete(CommentAboutSitterDto commentAboutSitterDto) {
        try{
            CommentAboutSitter entityToDelete = repository.findById(commentAboutSitterDto.getId())
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
    public CommentAboutSitterDto add(CommentAboutSitterDto commentAboutSitterDto) {
        CommentAboutSitter newEntity = CommentAboutSitterDto.to(commentAboutSitterDto);
        repository.save(newEntity);
        return CommentAboutSitterDto.from(newEntity);
    }

    @Override
    public CommentAboutSitterDto findById(Long aLong) {
        return CommentAboutSitterDto.from(repository.findById(aLong)
                .filter(entry -> !entry.getIsDeleted())
                .orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public CommentAboutSitterDto update(CommentAboutSitterDto commentAboutSitterDto) {
        CommentAboutSitterDto entity = findById(commentAboutSitterDto.getId());
        PropertiesUtils.copyNonNullProperties(commentAboutSitterDto, entity);
        CommentAboutSitter updatedEntity = repository.save(CommentAboutSitterDto.to(entity));
        return CommentAboutSitterDto.from(updatedEntity);
    }

    @Override
    public List<CommentAboutSitterDto> findAllByUserId(Long userId) {
        return CommentAboutSitterDto.from(repository.findAllByAccountId(userId).stream()
                .filter(entry -> !entry.getIsDeleted())
                .collect(Collectors.toList()));
    }
}
