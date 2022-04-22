package ru.itis.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.backend.models.UserAppeal;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAppealDto {

    protected Long id;
    protected Long userId;
    protected Date sendDate;
    protected String text;
    protected Boolean isClosed;
    protected AppealAnswerDto appealAnswerDto;

    public static UserAppealDto from(UserAppeal userAppeal){
        return (userAppeal == null) ? null : UserAppealDto.builder()
                .id(userAppeal.getId())
                .userId(userAppeal.getUserId())
                .sendDate(userAppeal.getSendDate())
                .text(userAppeal.getText())
                .isClosed(userAppeal.getIsClosed())
                .appealAnswerDto(AppealAnswerDto.from(userAppeal.getAppealAnswer()))
                .build();
    }

    public static UserAppeal to(UserAppealDto userAppealDto){
        return (userAppealDto == null) ? null : UserAppeal.builder()
                .id(userAppealDto.getId())
                .userId(userAppealDto.getUserId())
                .sendDate(userAppealDto.getSendDate())
                .text(userAppealDto.getText())
                .isClosed(userAppealDto.getIsClosed())
                .appealAnswer(AppealAnswerDto.to(userAppealDto.getAppealAnswerDto()))
                .build();
    }

    public static List<UserAppealDto> from(List<UserAppeal> userAppeals){
        return userAppeals.stream()
                .map(UserAppealDto::from)
                .collect(Collectors.toList());
    }

    public static List<UserAppeal> to(List<UserAppealDto> userAppeals){
        return userAppeals.stream()
                .map(UserAppealDto::to)
                .collect(Collectors.toList());
    }

}
