package ru.itis.backend.dto;

import lombok.Builder;
import lombok.Data;
import ru.itis.backend.models.User;
import ru.itis.backend.models.UserState;
import ru.itis.backend.utils.ImageLoader;
import java.awt.image.BufferedImage;
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
    protected BufferedImage image;

    public static UserDto from(User user){
        return UserDto.builder()
                .id(user.getId())
                .mail(user.getMail())
                .login(user.getLogin())
                .hashPassword(user.getHashPassword())
                .lastLogin(user.getLastLogin())
                .state(user.getState())
                .registrationDate(user.getRegistrationDate())
                .country(user.getCountry())
                .image(ImageLoader.loadImageByKey(user.getImageKey()))
                .build();
    }

    public static User to(UserDto userDto){
        return User.builder()
                .id(userDto.getId())
                .mail(userDto.getMail())
                .login(userDto.getLogin())
                .hashPassword(userDto.getHashPassword())
                .lastLogin(userDto.getLastLogin())
                .state(userDto.getState())
                .registrationDate(userDto.getRegistrationDate())
                .country(userDto.getCountry())
                .imageKey(ImageLoader.getImageKeyForUser(userDto))
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
