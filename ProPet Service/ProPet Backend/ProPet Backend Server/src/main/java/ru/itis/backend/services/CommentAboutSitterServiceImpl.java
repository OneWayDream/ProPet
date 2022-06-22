package ru.itis.backend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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

import javax.persistence.LockModeType;
import javax.persistence.OptimisticLockException;
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
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    public void delete(CommentAboutSitterRestDto commentAboutSitterDto) {
        try{
            CommentAboutSitter entityToDelete = repository.findById(commentAboutSitterDto.getId())
                    .filter(entry -> !entry.getIsDeleted())
                    .orElseThrow(() -> new EntityNotExistsException(" comment."));
            entityToDelete.setIsDeleted(true);
            repository.save(entityToDelete);
            updateRating(entityToDelete, -1, null);
        } catch (Exception ex){
            if (ex instanceof EntityNotExistsException){
                throw ex;
            }
            if (ex instanceof OptimisticLockException){
                delete(commentAboutSitterDto);
            }
            throw new PersistenceException(ex);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    public CommentAboutSitterRestDto add(CommentAboutSitterRestDto commentAboutSitterDto) {
        try{
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
            updateRating(newEntity, +1, null);
            return CommentAboutSitterRestDto.fromRest(newEntity);
        } catch (OptimisticLockException ex){
            return add(commentAboutSitterDto);
        }

    }

    @Override
    public CommentAboutSitterRestDto findById(Long aLong) {
        return CommentAboutSitterRestDto.fromRest(repository.findById(aLong)
                .filter(entry -> !entry.getIsDeleted())
                .orElseThrow(() -> new EntityNotFoundException(" comment.")));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    public CommentAboutSitterRestDto update(CommentAboutSitterRestDto commentAboutSitterRestDto) {
        try{
            CommentAboutSitterRestDto entity = findById(commentAboutSitterRestDto.getId());
            Integer oldValue = entity.getRate();
            PropertiesUtils.copyNonNullProperties(commentAboutSitterRestDto, entity);
            CommentAboutSitter updatedEntity = repository.save(CommentAboutSitterRestDto.toRest(entity));
            updateRating(updatedEntity, +1, oldValue);
            return CommentAboutSitterRestDto.fromRest(updatedEntity);
        } catch (OptimisticLockException ex){
            return update(commentAboutSitterRestDto);
        }

    }

    @Override
    public List<CommentAboutSitterRestDto> findAllByUserId(Long userId) {
        return CommentAboutSitterRestDto.fromRest(repository.findAllByAccountId(userId).stream()
                .filter(entry -> !entry.getIsDeleted())
                .collect(Collectors.toList()));
    }

    protected void updateRating(CommentAboutSitter comment, int commentChange, Integer oldValue){
        SitterInfo sitterInfo = sitterInfoRepository.findById(comment.getSitterInfoId())
                .orElseThrow(() -> new EntityNotExistsException(" comment."));
        if (oldValue != null){
            updateRate(sitterInfo, oldValue, -1);
        }
        updateRate(sitterInfo, comment.getRate(), commentChange);
        Double sum = (double) (sitterInfo.getRateOne() + sitterInfo.getRateTwo() * 2 + sitterInfo.getRateThree() * 3
                + sitterInfo.getRateFour() * 4 + sitterInfo.getRateFive() * 5);
        Integer amount = sitterInfo.getRateOne() + sitterInfo.getRateTwo() + sitterInfo.getRateThree()
                + sitterInfo.getRateFour() + sitterInfo.getRateFive();
        sitterInfo.setRating(sum / amount);
        sitterInfoRepository.save(sitterInfo);
    }

    protected void updateRate(SitterInfo sitterInfo, Integer rate, int commentChange){
        switch (rate) {
            case 1 -> sitterInfo.setRateOne(sitterInfo.getRateOne() + commentChange);
            case 2 -> sitterInfo.setRateTwo(sitterInfo.getRateTwo() + commentChange);
            case 3 -> sitterInfo.setRateThree(sitterInfo.getRateThree() + commentChange);
            case 4 -> sitterInfo.setRateFour(sitterInfo.getRateFour() + commentChange);
            case 5 -> sitterInfo.setRateFive(sitterInfo.getRateFive() + commentChange);
        }
    }
}
