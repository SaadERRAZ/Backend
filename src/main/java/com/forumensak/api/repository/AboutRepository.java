package com.forumensak.api.repository;

import com.forumensak.api.model.cv.About;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AboutRepository extends JpaRepository<About,Long> {

    @Query("SELECT p FROM About p WHERE CONCAT(p.firstName, ' ', p.lastName, ' ') LIKE %?1%")
    public List<About> search(String keyword);
}
