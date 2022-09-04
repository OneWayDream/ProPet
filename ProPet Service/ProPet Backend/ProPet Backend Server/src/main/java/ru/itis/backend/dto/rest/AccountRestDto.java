package ru.itis.backend.dto.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.itis.backend.dto.app.*;
import ru.itis.backend.models.Account;
import ru.itis.backend.utils.EncryptionUtils;

import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class AccountRestDto extends AccountDto {

    protected List<CommentAboutSitterRestDto> comments;
    protected List<UserAppealDto> appeals;
    protected ActivationLinkDto activationLinkDto;
    protected List<PetTransferApplyDto> customerApplies;
    protected List<PetTransferApplyDto> performerApplies;
    protected TreatyInfoDto treatyInfo;

    public static AccountRestDto fromRest(Account account){
        return (account == null) ? null : AccountRestDto.builder()
                .id(account.getId())
                .mail(account.getMail())
                .login(account.getLogin())
                .lastLogin(account.getLastLogin())
                .state(account.getState())
                .role(account.getRole())
                .name(account.getName())
                .surname(account.getSurname())
                .registrationDate(account.getRegistrationDate())
                .city(account.getCity())
                .phone(account.getPhone())
                .pets((account.getPets() == null || account.getPets().isEmpty()) ? null
                        : PetInfoDto.from(account.getPets()))
                .comments((account.getComments() == null || account.getComments().isEmpty()) ? null
                        : CommentAboutSitterRestDto.fromRest(account.getComments()))
                .appeals((account.getAppeals() == null || account.getAppeals().isEmpty()) ? null
                        : UserAppealDto.from(account.getAppeals()))
                .sitterInfoDto((account.getSitterInfo() == null) ? null : SitterInfoDto.from(account.getSitterInfo()))
                .activationLinkDto((account.getActivationLink() == null) ? null :
                        ActivationLinkDto.from(account.getActivationLink()))
                .customerApplies((account.getCustomerApplies() == null) ? null:
                        PetTransferApplyDto.from(account.getCustomerApplies()))
                .performerApplies((account.getPerformerApplies() == null) ? null:
                        PetTransferApplyDto.from(account.getPerformerApplies()))
                .treatyInfo((account.getTreatyInfo() == null) ? null : TreatyInfoDto.from(account.getTreatyInfo()))
                .build();
    }

    public static Account toRest(AccountRestDto account){
        return (account == null) ? null : Account.builder()
                .id(account.getId())
                .mail(account.getMail())
                .login(account.getLogin())
                .hashPassword((account.getPassword() == null) ? null
                        : EncryptionUtils.encryptPassword(account.getPassword()))
                .lastLogin(account.getLastLogin())
                .state(account.getState())
                .role(account.getRole())
                .name(account.getName())
                .surname(account.getSurname())
                .registrationDate(account.getRegistrationDate())
                .city(account.getCity())
                .phone(account.getPhone())
                .pets((account.getPets() == null) ? null : PetInfoDto.to(account.getPets()))
                .comments((account.getComments() == null) ? null : CommentAboutSitterRestDto.toRest(account.getComments()))
                .appeals((account.getAppeals() == null) ? null : UserAppealDto.to(account.getAppeals()))
                .sitterInfo((account.getSitterInfoDto() == null) ? null : SitterInfoDto.toRest(account.getSitterInfoDto()))
                .activationLink((account.getActivationLinkDto() == null) ? null
                        : ActivationLinkDto.to(account.getActivationLinkDto()))
                .customerApplies((account.getCustomerApplies() == null) ? null:
                        PetTransferApplyDto.to(account.getCustomerApplies()))
                .performerApplies((account.getPerformerApplies() == null) ? null:
                        PetTransferApplyDto.to(account.getPerformerApplies()))
                .treatyInfo((account.getTreatyInfo() == null) ? null : TreatyInfoDto.to(account.getTreatyInfo()))
                .isDeleted(false)
                .build();
    }

    public static List<AccountRestDto> fromRest(List<Account> accounts){
        return accounts.stream()
                .map(AccountRestDto::fromRest)
                .collect(Collectors.toList());
    }

    public static List<Account> toRest(List<AccountRestDto> accounts){
        return accounts.stream()
                .map(AccountRestDto::toRest)
                .collect(Collectors.toList());
    }
}
