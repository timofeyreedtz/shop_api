package com.micro.productservice.repos;



import com.micro.productservice.model.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KeywordRepos extends JpaRepository<Keyword,Integer> {
    @Query("SELECT c FROM Keyword c WHERE c.keyword = ?1")
    Optional<Keyword> findKeywordByKeyword(String value);

    @Override
    Optional<Keyword> findById(Integer integer);
}
