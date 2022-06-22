package ru.itis.backend.dto.forms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PetTransferApplyForm {

    protected Long customerId;
    protected Long performerId;
    protected Long petId;
    protected String city;

}
