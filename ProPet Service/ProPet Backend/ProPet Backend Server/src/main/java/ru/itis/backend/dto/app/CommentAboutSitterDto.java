package ru.itis.backend.dto.app;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.itis.backend.dto.rest.CommentAboutSitterRestDto;
import ru.itis.backend.models.CommentAboutSitter;

import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CommentAboutSitterDto extends CommentAboutSitterRestDto {

    protected String login;
    protected String mail;

    public static CommentAboutSitterDto from(CommentAboutSitter comment){
        return (comment == null) ? null : CommentAboutSitterDto.builder()
                .id(comment.getId())
                .sitterInfoId(comment.getSitterInfoId())
                .accountId(comment.getAccountId())
                .rate(comment.getRate())
                .review(comment.getReview())
                .login(comment.getAccount().getLogin())
                .mail(comment.getAccount().getMail())
                .build();
    }

    public static List<CommentAboutSitterDto> from(List<CommentAboutSitter> comments){
        return comments.stream()
                .map(CommentAboutSitterDto::from)
                .collect(Collectors.toList());
    }

}
