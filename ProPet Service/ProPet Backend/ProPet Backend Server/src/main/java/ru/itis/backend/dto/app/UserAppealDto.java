package ru.itis.backend.dto.app;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.backend.dto.rest.AppealAnswerDto;
import ru.itis.backend.models.UserAppeal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAppealDto {

    protected Long id;
    protected Long accountId;
    protected Date sendDate;

    @NotBlank
    @Size(min = 10, max = 400)
    protected String text;

    protected Boolean isClosed;
    protected AppealAnswerDto appealAnswerDto;

    public static UserAppealDto from(UserAppeal userAppeal){
        return (userAppeal == null) ? null : UserAppealDto.builder()
                .id(userAppeal.getId())
                .accountId(userAppeal.getAccountId())
                .sendDate(userAppeal.getSendDate())
                .text(userAppeal.getText())
                .isClosed(userAppeal.getIsClosed())
                .appealAnswerDto(AppealAnswerDto.from(userAppeal.getAppealAnswer()))
                .build();
    }

    public static UserAppeal to(UserAppealDto userAppealDto){
        return (userAppealDto == null) ? null : UserAppeal.builder()
                .id(userAppealDto.getId())
                .accountId(userAppealDto.getAccountId())
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
