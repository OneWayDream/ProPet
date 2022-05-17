package ru.itis.backend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.backend.dto.app.CommentAboutSitterDto;
import ru.itis.backend.repositories.CommentAboutSitterRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentAboutSitterPaginationService {

    protected final CommentAboutSitterRepository repository;

    public List<CommentAboutSitterDto> getCommentsPage(Long id, Integer page, Integer size){
        return CommentAboutSitterDto.from(repository.getSearchPage(id, size, (long) page*size));
    }

}
