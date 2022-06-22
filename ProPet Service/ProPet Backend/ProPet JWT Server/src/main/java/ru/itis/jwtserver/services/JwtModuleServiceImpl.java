package ru.itis.jwtserver.services;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import ru.itis.jwtserver.dto.JwtModuleDto;
import ru.itis.jwtserver.exceptions.persistence.EntityNotExistsException;
import ru.itis.jwtserver.exceptions.persistence.EntityNotFoundException;
import ru.itis.jwtserver.models.JwtModule;
import ru.itis.jwtserver.repositories.JwtModuleRepository;
import ru.itis.jwtserver.utils.PropertiesUtils;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class JwtModuleServiceImpl implements JwtModuleService {

    @NonNull
    protected JwtModuleRepository repository;

    @Override
    public List<JwtModuleDto> findAll() {
        return JwtModuleDto.from(repository.findAll().stream()
                .filter(entry -> !entry.getIsDeleted())
                .collect(Collectors.toList()));
    }

    @Override
    public void delete(JwtModuleDto jwtModuleDto) {
        try{
            JwtModule entityToDelete = repository.findById(jwtModuleDto.getId())
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
    public JwtModuleDto add(JwtModuleDto jwtModuleDto) {
        JwtModule newEntity = JwtModuleDto.to(jwtModuleDto);
        repository.save(newEntity);
        return JwtModuleDto.from(newEntity);
    }

    @Override
    public JwtModuleDto findById(Long aLong) {
        return JwtModuleDto.from(repository.findById(aLong)
                .filter(entry -> !entry.getIsDeleted())
                .orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public JwtModuleDto update(JwtModuleDto jwtModuleDto) {
        JwtModuleDto entity = findById(jwtModuleDto.getId());
        PropertiesUtils.copyNonNullProperties(jwtModuleDto, entity);
        JwtModule updatedEntity = repository.save(JwtModuleDto.to(entity));
        return JwtModuleDto.from(updatedEntity);
    }

    @Override
    public JwtModuleDto findByLogin(String login) {
        return JwtModuleDto.from(repository.findByLogin(login)
                .filter(entry -> !entry.getIsDeleted())
                .orElseThrow(EntityNotFoundException::new));
    }
}
