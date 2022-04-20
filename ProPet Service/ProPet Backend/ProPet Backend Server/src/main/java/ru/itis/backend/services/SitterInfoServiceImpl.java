package ru.itis.backend.services;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.backend.dto.SitterInfoDto;
import ru.itis.backend.exceptions.EntityNotExistsException;
import ru.itis.backend.exceptions.EntityNotFoundException;
import ru.itis.backend.models.SitterInfo;
import ru.itis.backend.repositories.SitterInfoRepository;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SitterInfoServiceImpl implements SitterInfoService {

    @NonNull
    protected SitterInfoRepository sitterInfoRepository;

    @Override
    public List<SitterInfoDto> findAll() {
        return SitterInfoDto.from(sitterInfoRepository.findAll().stream()
                .filter(user -> !user.getIsDeleted())
                .collect(Collectors.toList()));
    }

    @Override
    public void delete(SitterInfoDto sitterInfoDto) {
        try{
            SitterInfo sitterInfoForDelete = sitterInfoRepository.findById(sitterInfoDto.getId())
                    .filter(info -> !info.getIsDeleted())
                    .orElseThrow(EntityNotExistsException::new);
            sitterInfoForDelete.setIsDeleted(true);
            sitterInfoRepository.save(sitterInfoForDelete);
        } catch (Exception ex){
            if (ex instanceof EntityNotExistsException){
                throw ex;
            }
            throw new PersistenceException(ex);
        }
    }

    @Override
    public SitterInfoDto add(SitterInfoDto sitterInfoDto) {
        SitterInfo newSitterInfo = SitterInfoDto.to(sitterInfoDto);
        sitterInfoRepository.save(newSitterInfo);
        return SitterInfoDto.from(newSitterInfo);
    }

    @Override
    public SitterInfoDto findById(Long aLong) {
        return SitterInfoDto.from(sitterInfoRepository.findById(aLong)
                .filter(info -> !info.getIsDeleted())
                .orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public SitterInfoDto update(SitterInfoDto sitterInfoDto) {
        try{
            SitterInfo updatedSitterInfo = sitterInfoRepository.save(SitterInfoDto.to(sitterInfoDto));
            return SitterInfoDto.from(updatedSitterInfo);
        } catch (Exception ex){
            throw new PersistenceException(ex);
        }
    }

    @Override
    public SitterInfoDto findByUserId(Long userId) {
        return SitterInfoDto.from(sitterInfoRepository.findByUserId(userId)
                .filter(info -> !info.getIsDeleted())
                .orElseThrow(EntityNotFoundException::new));
    }

}
