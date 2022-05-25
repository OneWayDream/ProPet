package ru.itis.jwtserver.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;
import ru.itis.jwtserver.models.JwtUser;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class JwtUserDto extends JwtEntityDto {

    protected String mail;
    protected Long accountId;

    public static JwtUserDto from(JwtUser user){
        return JwtUserDto.builder()
                .id(user.getId())
                .login(user.getLogin())
                .mail(user.getMail())
                .hashPassword(user.getHashPassword())
                .state(user.getState())
                .role(user.getRole())
                .redisId(user.getRedisId())
                .accountId(user.getAccountId())
                .build();
    }

    public static JwtUser to(JwtUserDto user){
        return JwtUser.builder()
                .id(user.getId())
                .login(user.getLogin())
                .mail(user.getMail())
                .hashPassword(user.getHashPassword())
                .state(user.getState())
                .role(user.getRole())
                .redisId(user.getRedisId())
                .accountId(user.getAccountId())
                .isDeleted(false)
                .build();
    }

    public static List<JwtUserDto> from(List<JwtUser> users){
        return users.stream()
                .map(JwtUserDto::from)
                .collect(Collectors.toList());
    }

    public static List<JwtUser> to(List<JwtUserDto> users){
        return users.stream()
                .map(JwtUserDto::to)
                .collect(Collectors.toList());
    }

}
