package ru.itis.jwtserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.itis.jwtserver.models.JwtModule;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class JwtModuleDto extends JwtEntityDto {

    public static JwtModuleDto from(JwtModule module){
        return JwtModuleDto.builder()
                .id(module.getId())
                .login(module.getLogin())
                .hashPassword(module.getHashPassword())
                .state(module.getState())
                .role(module.getRole())
                .redisId(module.getRedisId())
                .build();
    }

    public static JwtModule to(JwtModuleDto module){
        return JwtModule.builder()
                .id(module.getId())
                .login(module.getLogin())
                .hashPassword(module.getHashPassword())
                .state(module.getState())
                .role(module.getRole())
                .redisId(module.getRedisId())
                .isDeleted(false)
                .build();
    }

    public static List<JwtModuleDto> from(List<JwtModule> modules){
        return modules.stream()
                .map(JwtModuleDto::from)
                .collect(Collectors.toList());
    }

    public static List<JwtModule> to(List<JwtModuleDto> modules){
        return modules.stream()
                .map(JwtModuleDto::to)
                .collect(Collectors.toList());
    }

}
