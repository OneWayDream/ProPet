package ru.itis.backend.services;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.backend.dto.ActivationLinkDto;
import ru.itis.backend.exceptions.EntityNotExistsException;
import ru.itis.backend.exceptions.EntityNotFoundException;
import ru.itis.backend.exceptions.LinkNotExistsException;
import ru.itis.backend.models.ActivationLink;
import ru.itis.backend.repositories.ActivationLinkRepository;
import ru.itis.backend.utils.PropertiesUtils;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActivationLinksServiceImpl implements ActivationLinksService {

    @NonNull
    protected ActivationLinkRepository repository;

    @Override
    public List<ActivationLinkDto> findAll() {
        return ActivationLinkDto.from(repository.findAll().stream()
                .filter(entry -> !entry.getIsDeleted())
                .collect(Collectors.toList()));
    }

    @Override
    public void delete(ActivationLinkDto activationLinkDto) {
        try{
            ActivationLink entityToDelete = repository.findById(activationLinkDto.getId())
                    .filter(entry -> !entry.getIsDeleted())
                    .orElseThrow(EntityNotExistsException::new);
            entityToDelete.setIsDeleted(true);
            repository.save(entityToDelete);
        } catch (Exception ex){
            throw new PersistenceException(ex);
        }
    }

    @Override
    public ActivationLinkDto add(ActivationLinkDto activationLinkDto) {
        ActivationLink newEntity = ActivationLinkDto.to(activationLinkDto);
        repository.save(newEntity);
        return ActivationLinkDto.from(newEntity);
    }

    @Override
    public ActivationLinkDto findById(Long aLong) {
        return ActivationLinkDto.from(repository.findById(aLong)
                .filter(entry -> !entry.getIsDeleted())
                .orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public ActivationLinkDto update(ActivationLinkDto activationLinkDto) {
        ActivationLinkDto entity = findById(activationLinkDto.getId());
        PropertiesUtils.copyNonNullProperties(activationLinkDto, entity);
        ActivationLink updatedEntity = repository.save(ActivationLinkDto.to(entity));
        return ActivationLinkDto.from(updatedEntity);
    }

    @Override
    public ActivationLinkDto findByLinkValue(String linkValue) {
        return ActivationLinkDto.from(repository.findByLinkValue(linkValue)
                .filter(entry -> !entry.getIsDeleted())
                .orElseThrow(LinkNotExistsException::new));
    }

    @Override
    public ActivationLinkDto findByAccountId(Long id) {
        return ActivationLinkDto.from(repository.findByAccountId(id)
                .filter(entry -> !entry.getIsDeleted())
                .orElseThrow(LinkNotExistsException::new));
    }
}
