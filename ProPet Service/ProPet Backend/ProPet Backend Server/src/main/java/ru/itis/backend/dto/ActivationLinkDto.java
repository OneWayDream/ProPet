package ru.itis.backend.dto;

import lombok.Builder;
import lombok.Data;
import ru.itis.backend.models.ActivationLink;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
//@JsonIgnoreProperties(ignoreUnknown = true)
public class ActivationLinkDto {

    protected Long id;
    protected Long accountId;
    protected String linkValue;

    public static ActivationLinkDto from(ActivationLink activationLink){
        return (activationLink == null) ? null : ActivationLinkDto.builder()
                .id(activationLink.getId())
                .accountId(activationLink.getAccountId())
                .linkValue(activationLink.getLinkValue())
                .build();
    }

    public static ActivationLink to(ActivationLinkDto activationLinkDto){
        return (activationLinkDto == null) ? null : ActivationLink.builder()
                .id(activationLinkDto.getId())
                .accountId(activationLinkDto.getAccountId())
                .linkValue(activationLinkDto.getLinkValue())
                .isDeleted(false)
                .build();
    }

    public static List<ActivationLinkDto> from(List<ActivationLink> activationLinks){
        return activationLinks.stream()
                .map(ActivationLinkDto::from)
                .collect(Collectors.toList());
    }

    public static List<ActivationLink> to(List<ActivationLinkDto> activationLinkDtos){
        return activationLinkDtos.stream()
                .map(ActivationLinkDto::to)
                .collect(Collectors.toList());
    }

}
