package com.forumensak.api.model;

import com.forumensak.api.model.company.AboutCompany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String companyImage;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "about_id", referencedColumnName = "id")
    private AboutCompany aboutCompany;
    private boolean flag;
}
