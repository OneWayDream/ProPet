package ru.itis.backend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.itis.backend.dto.app.SitterSearchDto;
import ru.itis.backend.entities.SearchVariable;
import ru.itis.backend.entities.SortingOrder;
import ru.itis.backend.entities.SortingVariable;
import ru.itis.backend.exceptions.IncorrectSearchVariableException;
import ru.itis.backend.repositories.SitterInfoRepository;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SitterSearchService {

    protected final SitterInfoRepository repository;
    public List<SitterSearchDto> getSearchPage(Integer page, Integer size, SortingVariable sortedBy,
                                               SortingOrder order, SearchVariable searchVariable,
                                               String searchParamValue) {
        Sort sort = null;
        if (sortedBy != null){
            sort = Sort.by(sortedBy.value());
            if (order != null){
                if (order.equals(SortingOrder.ASCENDING)){
                    sort.ascending();
                } else {
                    sort.descending();
                }
            }
        }
        Pageable pageable;
        if (sort != null){
            pageable = PageRequest.of(page, size, sort);
        } else {
            pageable = PageRequest.of(page, size);
        }
//        return SitterSearchDto.fromSearch(repository.findAll(pageable).toList()); //syntax error :c
        if (sortedBy == null){
            if (searchVariable == null){
                return SitterSearchDto.fromSearch(repository.getSearchPage(size, (long) page*size));
            } else {
                switch (searchVariable){
                    case CITY -> {
                        return SitterSearchDto.fromSearch(
                                repository.getSearchPageFilterByCity(size, (long) page*size,
                                        searchParamValue)
                        );
                    }
                    default -> throw new IncorrectSearchVariableException();

                }

            }
        } else {
            List<SitterSearchDto> result;
            if (searchVariable == null){
                result = SitterSearchDto.fromSearch(repository.getSearchPageWithSorting(size,
                        (long) page*size, sortedBy.value()));
            } else {
                switch (searchVariable){
                    case CITY -> {
                        return SitterSearchDto.fromSearch(
                                repository.getSearchPageWithSortingFilterByCity(size, (long) page*size,
                                        searchVariable.value(), searchParamValue)
                        );
                    }
                    default -> throw new IncorrectSearchVariableException();
                }
            }
            if ((order != null) && (order.equals(SortingOrder.DESCENDING))){
                Collections.reverse(result);
            }
            return result;
        }
    }

}
