package ru.itis.backend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.backend.dto.rest.CommentAboutSitterRestDto;
import ru.itis.backend.exceptions.comment.ExistedCommentException;
import ru.itis.backend.exceptions.comment.SelfCommentException;
import ru.itis.backend.exceptions.persistence.EntityNotExistsException;
import ru.itis.backend.exceptions.persistence.EntityNotFoundException;
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
    public List<CommentAboutSitterRestDto> findAll() {
        return CommentAboutSitterRestDto.fromRest(repository.findAll().stream()
                .filter(entry -> !entry.getIsDeleted())
                .collect(Collectors.toList()));
    }

    @Override
    public void delete(CommentAboutSitterRestDto commentAboutSitterDto) {
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
    public CommentAboutSitterRestDto add(CommentAboutSitterRestDto commentAboutSitterDto) {
        CommentAboutSitter newEntity = CommentAboutSitterRestDto.toRest(commentAboutSitterDto);
        SitterInfo sitterInfo = sitterInfoRepository.getById(commentAboutSitterDto.getSitterInfoId());
        if (sitterInfo.getAccountId().equals(commentAboutSitterDto.getAccountId())){
            throw new SelfCommentException();
        }
        if (repository.findByAccountIdAndSitterInfoId(commentAboutSitterDto.getAccountId(),
                commentAboutSitterDto.getSitterInfoId()).isPresent()){
            throw new ExistedCommentException();
        }
        repository.save(newEntity);
        updateRating(newEntity, +1);
        return CommentAboutSitterRestDto.fromRest(newEntity);
    }

    @Override
    public CommentAboutSitterRestDto findById(Long aLong) {
        return CommentAboutSitterRestDto.fromRest(repository.findById(aLong)
                .filter(entry -> !entry.getIsDeleted())
                .orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public CommentAboutSitterRestDto update(CommentAboutSitterRestDto commentAboutSitterRestDto) {
        CommentAboutSitterRestDto entity = findById(commentAboutSitterRestDto.getId());
        PropertiesUtils.copyNonNullProperties(commentAboutSitterRestDto, entity);
        CommentAboutSitter updatedEntity = repository.save(CommentAboutSitterRestDto.toRest(entity));
        updateRating(updatedEntity, +1);
        return CommentAboutSitterRestDto.fromRest(updatedEntity);
    }

    @Override
    public List<CommentAboutSitterRestDto> findAllByUserId(Long userId) {
        return CommentAboutSitterRestDto.fromRest(repository.findAllByAccountId(userId).stream()
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
