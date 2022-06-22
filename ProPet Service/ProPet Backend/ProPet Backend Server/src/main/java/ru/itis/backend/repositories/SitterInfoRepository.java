package ru.itis.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.itis.backend.models.SitterInfo;

import java.util.List;
import java.util.Optional;

public interface SitterInfoRepository extends CustomRepository<SitterInfo, Long> {

    Optional<SitterInfo> findByAccountId(Long accountId);

    @Query(value = "select * from sitter_info INNER JOIN account on account.id = sitter_info.account_id " +
            "where sitter_info.is_deleted=false and sitter_info.sitter_status=true " +
            "limit :limit offset :offset",
            nativeQuery = true)
    List<SitterInfo> getSearchPage(@Param("limit") Integer limit, @Param("offset") Long offset);

    @Query(value = "select * from sitter_info INNER JOIN account on account.id = sitter_info.account_id " +
            "where sitter_info.is_deleted=false and sitter_info.sitter_status=true and account.city=:city " +
            "limit :limit offset :offset",
            nativeQuery = true)
    List<SitterInfo> getSearchPageFilterByCity(@Param("limit") Integer limit, @Param("offset") Long offset,
                                               @Param("city") String city);

    @Query(value = "select * from sitter_info INNER JOIN account on account.id = sitter_info.account_id " +
            "where sitter_info.is_deleted=false and sitter_info.sitter_status=true  " +
            "order by :sortBy limit :limit offset :offset",
            nativeQuery = true)
    List<SitterInfo> getSearchPageWithSorting(@Param("limit") Integer limit, @Param("offset") Long offset,
                                              @Param("sortBy") String sortBy);

    @Query(value = "select * from sitter_info INNER JOIN account on account.id = sitter_info.account_id " +
            "where sitter_info.is_deleted=false and sitter_info.sitter_status=true and account.city=:city " +
            "order by :sortBy limit :limit offset :offset",
            nativeQuery = true)
    List<SitterInfo> getSearchPageWithSortingFilterByCity(@Param("limit") Integer limit, @Param("offset") Long offset,
                                              @Param("sortBy") String sortBy, @Param("city") String city);

//    @Query(value = "select * from sitter_info where sitter_info.is_deleted=false order by :sortBy asc limit :limit offset :offset",
//            nativeQuery = true)
//    List<SitterInfo> getSearchPageWithSortingAscending(@Param("limit") Integer limit, @Param("offset") Long offset,
//                                              @Param("sortBy") String sortBy);

//    @Query(value = "select * from sitter_info where sitter_info.is_deleted=false order by :sortBy desc limit :limit offset :offset",
//            nativeQuery = true)
//    List<SitterInfo> getSearchPageWithSortingDescending(@Param("limit") Integer limit, @Param("offset") Long offset,
//                                                 @Param("sortBy") String sortBy);

}
