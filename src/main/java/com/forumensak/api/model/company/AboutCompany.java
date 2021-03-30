package com.forumensak.api.model.company;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class AboutCompany {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String address;
    private String city;
    private String number;
    @Size(max = 1200)
    private String bio;
    @Size(max = 4000)
    private String socials;
}
