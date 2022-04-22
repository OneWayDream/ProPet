package ru.itis.backend.dto;

import lombok.Builder;
import lombok.Data;
import ru.itis.backend.models.User;
import ru.itis.backend.models.UserState;
import ru.itis.backend.utils.ImageLoader;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class UserDto {

    protected Long id;
    protected String mail;
    protected String login;
    protected String hashPassword;
    protected Date lastLogin;
    protected UserState state;
    protected Date registrationDate;
    protected String country;
    protected String imageKey;
    protected List<PetInfoDto> pets;
    protected List<CommentAboutSitterDto> comments;
    protected SitterInfoDto sitterInfoDto;
    protected List<UserAppealDto> appeals;
    protected ActivationLinkDto activationLinkDto;

    public static UserDto from(User user){
        return (user == null) ? null : UserDto.builder()
                .id(user.getId())
                .mail(user.getMail())
                .login(user.getLogin())
                .hashPassword(user.getHashPassword())
                .lastLogin(user.getLastLogin())
                .state(user.getState())
                .registrationDate(user.getRegistrationDate())
                .country(user.getCountry())
                .imageKey(user.getImageKey())
                .pets((user.getPets() == null) ? null : PetInfoDto.from(user.getPets()))
                .comments((user.getComments() == null) ? null : CommentAboutSitterDto.from(user.getComments()))
                .appeals((user.getAppeals() == null) ? null : UserAppealDto.from(user.getAppeals()))
                .sitterInfoDto((user.getSitterInfo() == null) ? null : SitterInfoDto.from(user.getSitterInfo()))
                .activationLinkDto((user.getActivationLink() == null) ? null :
                        ActivationLinkDto.from(user.getActivationLink()))
                .build();
    }

    public static User to(UserDto userDto){
        return (userDto == null) ? null : User.builder()
                .id(userDto.getId())
                .mail(userDto.getMail())
                .login(userDto.getLogin())
                .hashPassword(userDto.getHashPassword())
                .lastLogin(userDto.getLastLogin())
                .state(userDto.getState())
                .registrationDate(userDto.getRegistrationDate())
                .country(userDto.getCountry())
                .imageKey(ImageLoader.getImageKeyForUser(userDto))
                .pets((userDto.getPets() == null) ? null : PetInfoDto.to(userDto.getPets()))
                .comments((userDto.getComments() == null) ? null : CommentAboutSitterDto.to(userDto.getComments()))
                .appeals((userDto.getAppeals() == null) ? null : UserAppealDto.to(userDto.getAppeals()))
                .sitterInfo((userDto.getSitterInfoDto() == null) ? null : SitterInfoDto.to(userDto.getSitterInfoDto()))
                .activationLink((userDto.getActivationLinkDto() == null) ? null :
                        ActivationLinkDto.to(userDto.getActivationLinkDto()))
                .isDeleted(false)
                .build();
    }

    public static List<UserDto> from(List<User> users){
        return users.stream()
                .map(UserDto::from)
                .collect(Collectors.toList());
    }

    public static List<User> to(List<UserDto> userDtos){
        return userDtos.stream()
                .map(UserDto::to)
                .collect(Collectors.toList());
    }
}
