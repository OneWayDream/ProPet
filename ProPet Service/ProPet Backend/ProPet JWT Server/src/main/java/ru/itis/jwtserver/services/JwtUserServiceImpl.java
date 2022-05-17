package ru.itis.jwtserver.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.jwtserver.dto.JwtUserDto;
import ru.itis.jwtserver.exceptions.EntityNotExistsException;
import ru.itis.jwtserver.exceptions.EntityNotFoundException;
import ru.itis.jwtserver.models.JwtUser;
import ru.itis.jwtserver.repositories.JwtUserRepository;
import ru.itis.jwtserver.utils.PropertiesUtils;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtUserServiceImpl implements JwtUserService {

    protected final JwtUserRepository repository;

    @Override
    public List<JwtUserDto> findAll() {
        return JwtUserDto.from(repository.findAll().stream()
                .filter(entry -> !entry.getIsDeleted())
                .collect(Collectors.toList()));
    }

    @Override
    public void delete(JwtUserDto jwtUserDto) {
        try{
            JwtUser entityToDelete = repository.findById(jwtUserDto.getId())
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
    public JwtUserDto add(JwtUserDto jwtUserDto) {
        JwtUser newEntity = JwtUserDto.to(jwtUserDto);
        repository.save(newEntity);
        return JwtUserDto.from(newEntity);
    }

    @Override
    public JwtUserDto findById(Long aLong) {
        return JwtUserDto.from(repository.findById(aLong)
                .filter(entry -> !entry.getIsDeleted())
                .orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public JwtUserDto update(JwtUserDto jwtUserDto) {
        JwtUserDto entity = findById(jwtUserDto.getId());
        PropertiesUtils.copyNonNullProperties(jwtUserDto, entity);
        JwtUser updatedEntity = repository.save(JwtUserDto.to(entity));
        return JwtUserDto.from(updatedEntity);
    }

    @Override
    public JwtUserDto findByLogin(String login) {
        return JwtUserDto.from(repository.findByLogin(login)
                .filter(entry -> !entry.getIsDeleted())
                .orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public JwtUserDto findByMail(String mail) {
        return JwtUserDto.from(repository.findByMail(mail)
                .filter(entry -> !entry.getIsDeleted())
                .orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public JwtUserDto findByAccountId(Long accountId) {
        return JwtUserDto.from(repository.findByAccountId(accountId)
                .filter(entry -> !entry.getIsDeleted())
                .orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public JwtUserDto updateByAccountId(JwtUserDto jwtUserDto) {
        JwtUserDto entity = findByAccountId(jwtUserDto.getAccountId());
        PropertiesUtils.copyNonNullProperties(jwtUserDto, entity);
        JwtUser updatedEntity = repository.save(JwtUserDto.to(entity));
        return JwtUserDto.from(updatedEntity);
    }

    @Override
    public void deleteByAccountId(Long accountId) {
        try{
            JwtUser entityToDelete = repository.findByAccountId(accountId)
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
}
