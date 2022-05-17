package ru.itis.backend.dto.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.itis.backend.models.CommentAboutSitter;

import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CommentAboutSitterRestDto {

    protected Long id;
    protected Long sitterInfoId;
    protected Long accountId;
    protected Integer rate;

    @Size(min = 1, max = 200)
    protected String review;

    public static CommentAboutSitterRestDto fromRest(CommentAboutSitter comment){
        return (comment == null) ? null : CommentAboutSitterRestDto.builder()
                .id(comment.getId())
                .sitterInfoId(comment.getSitterInfoId())
                .accountId(comment.getAccountId())
                .rate(comment.getRate())
                .review(comment.getReview())
                .build();
    }

    public static CommentAboutSitter toRest(CommentAboutSitterRestDto comment){
        return (comment == null) ? null : CommentAboutSitter.builder()
                .id(comment.getId())
                .sitterInfoId(comment.getSitterInfoId())
                .accountId(comment.getAccountId())
                .rate(comment.getRate())
                .review(comment.getReview())
                .build();
    }

    public static List<CommentAboutSitterRestDto> fromRest(List<CommentAboutSitter> comments){
        return comments.stream()
                .map(CommentAboutSitterRestDto::fromRest)
                .collect(Collectors.toList());
    }

    public static List<CommentAboutSitter> toRest(List<CommentAboutSitterRestDto> comments){
        return comments.stream()
                .map(CommentAboutSitterRestDto::toRest)
                .collect(Collectors.toList());
    }

}
