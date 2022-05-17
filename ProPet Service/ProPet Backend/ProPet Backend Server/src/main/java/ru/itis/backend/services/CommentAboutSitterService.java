package ru.itis.backend.services;

import ru.itis.backend.dto.rest.CommentAboutSitterRestDto;

import java.util.List;

public interface CommentAboutSitterService extends CrudService<CommentAboutSitterRestDto, Long> {

    List<CommentAboutSitterRestDto> findAllByUserId(Long userId);

}
