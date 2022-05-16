package ru.itis.backend.services;

import ru.itis.backend.dto.app.CommentAboutSitterDto;

import java.util.List;

public interface CommentAboutSitterService extends CrudService<CommentAboutSitterDto, Long> {

    List<CommentAboutSitterDto> findAllByUserId(Long userId);

}
