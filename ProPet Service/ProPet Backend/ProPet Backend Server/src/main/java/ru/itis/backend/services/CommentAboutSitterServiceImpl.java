package ru.itis.backend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.backend.dto.app.CommentAboutSitterDto;
import ru.itis.backend.exceptions.EntityNotExistsException;
import ru.itis.backend.exceptions.EntityNotFoundException;
import ru.itis.backend.models.CommentAboutSitter;
import ru.itis.backend.models.SitterInfo;
import ru.itis.backend.repositories.CommentAboutSitterRepository;
import ru.itis.backend.repositories.SitterInfoRepository;
import ru.itis.backend.utils.PropertiesUtils;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentAboutSitterServiceImpl implements CommentAboutSitterService {

    protected final CommentAboutSitterRepository repository;
    protected final SitterInfoRepository sitterInfoRepository;

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
            updateRating(entityToDelete, -1);
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
        updateRating(newEntity, +1);
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
        updateRating(updatedEntity, +1);
        return CommentAboutSitterDto.from(updatedEntity);
    }

    @Override
    public List<CommentAboutSitterDto> findAllByUserId(Long userId) {
        return CommentAboutSitterDto.from(repository.findAllByAccountId(userId).stream()
                .filter(entry -> !entry.getIsDeleted())
                .collect(Collectors.toList()));
    }

    protected void updateRating(CommentAboutSitter comment, int commentChange){
        SitterInfo sitterInfo = sitterInfoRepository.findById(comment.getSitterInfoId())
                .orElseThrow(EntityNotExistsException::new);
        switch (comment.getRate()) {
            case 1 -> sitterInfo.setRateOne(sitterInfo.getRateOne() + commentChange);
            case 2 -> sitterInfo.setRateTwo(sitterInfo.getRateTwo() + commentChange);
            case 3 -> sitterInfo.setRateThree(sitterInfo.getRateThree() + commentChange);
            case 4 -> sitterInfo.setRateFour(sitterInfo.getRateFour() + commentChange);
            case 5 -> sitterInfo.setRateFive(sitterInfo.getRateFive() + commentChange);
        }
        Double sum = (double) (sitterInfo.getRateOne() + sitterInfo.getRateTwo() * 2 + sitterInfo.getRateThree() * 3
                + sitterInfo.getRateFour() * 4 + sitterInfo.getRateFive() * 5);
        Integer amount = sitterInfo.getRateOne() + sitterInfo.getRateTwo() + sitterInfo.getRateThree()
                + sitterInfo.getRateFour() + sitterInfo.getRateFive();
        sitterInfo.setRating(sum / amount);
        sitterInfoRepository.save(sitterInfo);
    }
}
