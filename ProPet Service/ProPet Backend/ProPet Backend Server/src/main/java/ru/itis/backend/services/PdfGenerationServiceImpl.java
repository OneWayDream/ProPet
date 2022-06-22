package ru.itis.backend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.backend.amqp.RabbitClient;
import ru.itis.backend.dto.app.PetTransferApplyDto;
import ru.itis.backend.exceptions.pdf.UnconfirmedApplyException;
import ru.itis.backend.models.PetTransferApplyStatus;

import java.nio.file.AccessDeniedException;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class PdfGenerationServiceImpl implements PdfGenerationService {

    protected final RabbitClient rabbitClient;
    protected final PetTransferApplyService petTransferApplyService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public byte[] generatePetTransferTreaty(Long applyId, Long accountId) throws AccessDeniedException {
        PetTransferApplyDto apply = petTransferApplyService.findById(applyId);
        if ((!apply.getCustomerId().equals(accountId))&&(!apply.getPerformerId().equals(accountId))){
            throw new AccessDeniedException("Access denied");
        }
        if (!apply.getStatus().equals(PetTransferApplyStatus.UNCONFIRMED)){
            LocalDate currentDate = LocalDate.now();
            byte[] result = rabbitClient.handlePetTransferTreaty(apply, currentDate);
            apply.setStatus(PetTransferApplyStatus.PROCESSED);
            apply.setCreationDate(currentDate);
            petTransferApplyService.update(apply);
            return result;
        } else {
            throw new UnconfirmedApplyException();
        }

    }

}
