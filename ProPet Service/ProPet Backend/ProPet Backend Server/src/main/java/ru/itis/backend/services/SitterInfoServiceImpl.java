package ru.itis.backend.services;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.itis.backend.dto.SitterInfoDto;
import ru.itis.backend.entities.SortingOrder;
import ru.itis.backend.entities.SortingVariable;
import ru.itis.backend.exceptions.EntityNotExistsException;
import ru.itis.backend.exceptions.EntityNotFoundException;
import ru.itis.backend.models.SitterInfo;
import ru.itis.backend.repositories.SitterInfoRepository;
import ru.itis.backend.utils.PropertiesUtils;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SitterInfoServiceImpl implements SitterInfoService {

    @NonNull
    protected SitterInfoRepository repository;

    @Override
    public List<SitterInfoDto> findAll() {
        return SitterInfoDto.from(repository.findAll().stream()
                .filter(entry -> !entry.getIsDeleted())
                .collect(Collectors.toList()));
    }

    @Override
    public void delete(SitterInfoDto sitterInfoDto) {
        try{
            SitterInfo entityToDelete = repository.findById(sitterInfoDto.getId())
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
    public SitterInfoDto add(SitterInfoDto sitterInfoDto) {
        SitterInfo newEntity = SitterInfoDto.to(sitterInfoDto);
        newEntity.setRateOne(0);
        newEntity.setRateTwo(0);
        newEntity.setRateThree(0);
        newEntity.setRateFour(0);
        newEntity.setRateFive(0);
        newEntity.setRating(0.0);
        repository.save(newEntity);
        return SitterInfoDto.from(newEntity);
    }

    @Override
    public SitterInfoDto findById(Long aLong) {
        return SitterInfoDto.from(repository.findById(aLong)
                .filter(entry -> !entry.getIsDeleted())
                .orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public SitterInfoDto update(SitterInfoDto sitterInfoDto) {
        SitterInfoDto entity = findById(sitterInfoDto.getId());
        PropertiesUtils.copyNonNullProperties(sitterInfoDto, entity);
        SitterInfo updatedEntity = repository.save(SitterInfoDto.to(entity));
        return SitterInfoDto.from(updatedEntity);
    }

    @Override
    public SitterInfoDto findByUserId(Long userId) {
        return SitterInfoDto.from(repository.findByAccountId(userId)
                .filter(entry -> !entry.getIsDeleted())
                .orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public List<SitterInfoDto> getSearchPage(Integer page, Integer size,
                                             SortingVariable sortedBy, SortingOrder order) {
        Sort sort = null;
        if (sortedBy != null){
            sort = Sort.by(sortedBy.value());
            if (order != null){
                if (order.equals(SortingOrder.ASCENDING)){
                    sort.ascending();
                } else {
                    sort.descending();
                }
            }
        }
        Pageable pageable;
        if (sort != null){
            pageable = PageRequest.of(page, size, sort);
        } else {
            pageable = PageRequest.of(page, size);
        }
        return SitterInfoDto.from(repository.findAll(pageable).toList());
    }

}
