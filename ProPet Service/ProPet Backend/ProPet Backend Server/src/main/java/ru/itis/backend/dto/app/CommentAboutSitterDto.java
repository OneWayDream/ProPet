package ru.itis.backend.dto.app;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.backend.models.CommentAboutSitter;

import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentAboutSitterDto {

    protected Long id;
    protected Long sitterInfoId;
    protected Long accountId;
    protected Integer rate;
    @Size(min = 1, max = 200)
    protected String review;

    public static CommentAboutSitterDto from(CommentAboutSitter comment){
        return (comment == null) ? null : CommentAboutSitterDto.builder()
                .id(comment.getId())
                .sitterInfoId(comment.getSitterInfoId())
                .accountId(comment.getAccountId())
                .rate(comment.getRate())
                .review(comment.getReview())
                .build();
    }

    public static CommentAboutSitter to(CommentAboutSitterDto comment){
        return (comment == null) ? null : CommentAboutSitter.builder()
                .id(comment.getId())
                .sitterInfoId(comment.getSitterInfoId())
                .accountId(comment.getAccountId())
                .rate(comment.getRate())
                .review(comment.getReview())
                .build();
    }

    public static List<CommentAboutSitterDto> from(List<CommentAboutSitter> comments){
        return comments.stream()
                .map(CommentAboutSitterDto::from)
                .collect(Collectors.toList());
    }

    public static List<CommentAboutSitter> to(List<CommentAboutSitterDto> comments){
        return comments.stream()
                .map(CommentAboutSitterDto::to)
                .collect(Collectors.toList());
    }

}
