package ru.itis.backend.amqp;

import ru.itis.backend.dto.app.PetTransferApplyDto;

import java.time.LocalDate;

public interface AmqpClient {

    byte[] handlePetTransferTreaty(PetTransferApplyDto transferApply, LocalDate creationDate);

}
