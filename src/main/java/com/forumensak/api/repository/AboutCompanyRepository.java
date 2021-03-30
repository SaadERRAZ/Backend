package com.forumensak.api.repository;

import com.forumensak.api.model.company.AboutCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AboutCompanyRepository extends JpaRepository<AboutCompany,Long> {
    @Query("SELECT p FROM AboutCompany p WHERE CONCAT(p.name, ' ') LIKE %?1%")
    public List<AboutCompany> searchCompany(String keyword);
}
