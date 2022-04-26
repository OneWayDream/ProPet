package ru.itis.backend.dto;

import lombok.Builder;
import lombok.Data;
import ru.itis.backend.models.Account;
import ru.itis.backend.models.UserRole;
import ru.itis.backend.models.UserState;
import ru.itis.backend.utils.ImageLoader;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class AccountDto {

    protected Long id;
    protected String mail;
    protected String login;
    protected String hashPassword;
    protected Date lastLogin;
    protected UserState state;
    protected UserRole role;
    protected Date registrationDate;
    protected String country;
    protected String imageKey;
    protected List<PetInfoDto> pets;
    protected List<CommentAboutSitterDto> comments;
    protected SitterInfoDto sitterInfoDto;
    protected List<UserAppealDto> appeals;
    protected ActivationLinkDto activationLinkDto;

    public static AccountDto from(Account account){
        return (account == null) ? null : AccountDto.builder()
                .id(account.getId())
                .mail(account.getMail())
                .login(account.getLogin())
                .hashPassword(account.getHashPassword())
                .lastLogin(account.getLastLogin())
                .state(account.getState())
                .role(account.getRole())
                .registrationDate(account.getRegistrationDate())
                .country(account.getCountry())
                .imageKey(account.getImageKey())
                .pets((account.getPets() == null) ? null : PetInfoDto.from(account.getPets()))
                .comments((account.getComments() == null) ? null : CommentAboutSitterDto.from(account.getComments()))
                .appeals((account.getAppeals() == null) ? null : UserAppealDto.from(account.getAppeals()))
                .sitterInfoDto((account.getSitterInfo() == null) ? null : SitterInfoDto.from(account.getSitterInfo()))
                .activationLinkDto((account.getActivationLink() == null) ? null :
                        ActivationLinkDto.from(account.getActivationLink()))
                .build();
    }

    public static Account to(AccountDto account){
        return (account == null) ? null : Account.builder()
                .id(account.getId())
                .mail(account.getMail())
                .login(account.getLogin())
                .hashPassword(account.getHashPassword())
                .lastLogin(account.getLastLogin())
                .state(account.getState())
                .role(account.getRole())
                .registrationDate(account.getRegistrationDate())
                .country(account.getCountry())
                .imageKey(ImageLoader.getImageKeyForUser(account))
                .pets((account.getPets() == null) ? null : PetInfoDto.to(account.getPets()))
                .comments((account.getComments() == null) ? null : CommentAboutSitterDto.to(account.getComments()))
                .appeals((account.getAppeals() == null) ? null : UserAppealDto.to(account.getAppeals()))
                .sitterInfo((account.getSitterInfoDto() == null) ? null : SitterInfoDto.to(account.getSitterInfoDto()))
                .activationLink((account.getActivationLinkDto() == null) ? null :
                        ActivationLinkDto.to(account.getActivationLinkDto()))
                .isDeleted(false)
                .build();
    }

    public static List<AccountDto> from(List<Account> accounts){
        return accounts.stream()
                .map(AccountDto::from)
                .collect(Collectors.toList());
    }

    public static List<Account> to(List<AccountDto> accounts){
        return accounts.stream()
                .map(AccountDto::to)
                .collect(Collectors.toList());
    }
}
