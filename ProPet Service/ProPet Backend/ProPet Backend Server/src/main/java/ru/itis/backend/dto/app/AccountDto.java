package ru.itis.backend.dto.app;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.itis.backend.models.Account;
import ru.itis.backend.models.UserRole;
import ru.itis.backend.models.UserState;
import ru.itis.backend.utils.EncryptionUtils;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class AccountDto {

    protected Long id;

    @Email
    @Size(max = 50)
    protected String mail;

    @Size(min = 4, max = 50)
    protected String login;

    @Size(min = 8, max = 50)
    protected String password;
    protected Date lastLogin;
    protected UserState state;
    protected UserRole role;

    @Size(min = 1, max = 30)
    protected String name;

    @Size(min = 1, max = 30)
    protected String surname;
    protected Date registrationDate;

    @Size(min = 1, max = 30)
    protected String city;

    @Pattern(regexp="(^$|[0-9]{11})")
    protected String phone;

    protected List<PetInfoDto> pets;
    protected SitterInfoDto sitterInfoDto;

    public static AccountDto from(Account account){
        return (account == null) ? null : AccountDto.builder()
                .id(account.getId())
                .mail(account.getMail())
                .login(account.getLogin())
                .lastLogin(account.getLastLogin())
                .name(account.getName())
                .surname(account.getSurname())
                .city(account.getCity())
                .phone(account.getPhone())
                .pets((account.getPets() == null || account.getPets().isEmpty()) ? null
                        : PetInfoDto.from(account.getPets()))
                .sitterInfoDto((account.getSitterInfo() == null) ? null : SitterInfoDto.from(account.getSitterInfo()))
                .build();
    }

    public static Account to(AccountDto account){
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
                .sitterInfo((account.getSitterInfoDto() == null) ? null : SitterInfoDto.to(account.getSitterInfoDto()))
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
