package ru.itis.backend.services;

import ru.itis.backend.dto.CommentAboutSitterDto;

import java.util.List;

public interface CommentAboutSitterService extends CrudService<CommentAboutSitterDto, Long> {

    List<CommentAboutSitterDto> findAllByUserId(Long userId);

}
