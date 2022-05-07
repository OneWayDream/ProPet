package ru.itis.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.backend.models.AppealAnswer;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppealAnswerDto {

    protected Long id;
    protected Long appealId;
    protected Date sendDate;
    protected Long authorId;
    protected String text;

    public static AppealAnswerDto from(AppealAnswer answer){
        return (answer == null) ? null :AppealAnswerDto.builder()
                .id(answer.getId())
                .appealId(answer.getAppealId())
                .sendDate(answer.getSendDate())
                .authorId(answer.getAuthorId())
                .text(answer.getText())
                .build();
    }

    public static AppealAnswer to(AppealAnswerDto answerDto){
        return (answerDto == null) ? null :AppealAnswer.builder()
                .id(answerDto.getId())
                .appealId(answerDto.getAppealId())
                .sendDate(answerDto.getSendDate())
                .authorId(answerDto.getAuthorId())
                .text(answerDto.getText())
                .isDeleted(false)
                .build();
    }

    public static List<AppealAnswerDto> from(List<AppealAnswer> answers){
        return answers.stream()
                .map(AppealAnswerDto::from)
                .collect(Collectors.toList());
    }

    public static List<AppealAnswer> to(List<AppealAnswerDto> answers){
        return answers.stream()
                .map(AppealAnswerDto::to)
                .collect(Collectors.toList());
    }

}
