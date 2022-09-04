package ru.itis.backend.dto.app;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.backend.models.PetTransferApply;
import ru.itis.backend.models.PetTransferApplyStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PetTransferApplyDto {

    protected Long id;
    protected Long customerId;
    protected String customerLogin;
    protected Long performerId;
    protected String performerLogin;
    protected Boolean customerAgreement;
    protected Boolean performerAgreement;
    protected PetTransferApplyStatus status;
    protected LocalDate creationDate;
    protected Long petId;
    protected String petNickname;
    protected String city;

    public static PetTransferApplyDto from (PetTransferApply petTransferApply){
        return (petTransferApply == null) ? null : PetTransferApplyDto.builder()
                .id(petTransferApply.getId())
                .customerId(petTransferApply.getCustomerId())
                .customerLogin((petTransferApply.getCustomer() == null) ? null :
                        petTransferApply.getCustomer().getLogin())
                .performerId(petTransferApply.getPerformerId())
                .performerLogin((petTransferApply.getPerformer() == null) ? null :
                        petTransferApply.getPerformer().getLogin())
                .customerAgreement(petTransferApply.getCustomerAgreement())
                .performerAgreement(petTransferApply.getPerformerAgreement())
                .status(petTransferApply.getStatus())
                .creationDate(petTransferApply.getCreationDate())
                .petId(petTransferApply.getPetId())
                .petNickname((petTransferApply.getPet() == null) ? null :
                        petTransferApply.getPet().getNickname())
                .city(petTransferApply.getCity())
                .build();
    }

    public static PetTransferApply to(PetTransferApplyDto petTransferApply){
        return (petTransferApply == null) ? null : PetTransferApply.builder()
                .id(petTransferApply.getId())
                .customerId(petTransferApply.getCustomerId())
                .performerId(petTransferApply.getPerformerId())
                .customerAgreement(petTransferApply.getCustomerAgreement())
                .performerAgreement(petTransferApply.getPerformerAgreement())
                .status(petTransferApply.getStatus())
                .creationDate(petTransferApply.getCreationDate())
                .petId(petTransferApply.getPetId())
                .city(petTransferApply.getCity())
                .build();
    }

    public static List<PetTransferApplyDto> from(List<PetTransferApply> petTransferApplies){
        return (petTransferApplies == null) ? null : petTransferApplies.stream()
                .map(PetTransferApplyDto::from)
                .collect(Collectors.toList());
    }

    public static List<PetTransferApply> to(List<PetTransferApplyDto> petTransferApplies){
        return (petTransferApplies == null) ? null : petTransferApplies.stream()
                .map(PetTransferApplyDto::to)
                .collect(Collectors.toList());
    }

}
