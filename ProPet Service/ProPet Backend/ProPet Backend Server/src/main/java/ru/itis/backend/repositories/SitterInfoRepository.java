package ru.itis.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.itis.backend.models.SitterInfo;

import java.util.List;
import java.util.Optional;

public interface SitterInfoRepository extends JpaRepository<SitterInfo, Long> {

    Optional<SitterInfo> findByAccountId(Long accountId);

    @Query(value = "select * from sitter_info where sitter_info.is_deleted=false limit :limit offset :offset",
            nativeQuery = true)
    List<SitterInfo> getSearchPage(@Param("limit") Integer limit, @Param("offset") Long offset);

    @Query(value = "select * from sitter_info where sitter_info.is_deleted=false order by :sortBy limit :limit offset :offset",
            nativeQuery = true)
    List<SitterInfo> getSearchPageWithSorting(@Param("limit") Integer limit, @Param("offset") Long offset,
                                              @Param("sortBy") String sortBy);

//    @Query(value = "select * from sitter_info where sitter_info.is_deleted=false order by :sortBy asc limit :limit offset :offset",
//            nativeQuery = true)
//    List<SitterInfo> getSearchPageWithSortingAscending(@Param("limit") Integer limit, @Param("offset") Long offset,
//                                              @Param("sortBy") String sortBy);

//    @Query(value = "select * from sitter_info where sitter_info.is_deleted=false order by :sortBy desc limit :limit offset :offset",
//            nativeQuery = true)
//    List<SitterInfo> getSearchPageWithSortingDescending(@Param("limit") Integer limit, @Param("offset") Long offset,
//                                                 @Param("sortBy") String sortBy);

}
