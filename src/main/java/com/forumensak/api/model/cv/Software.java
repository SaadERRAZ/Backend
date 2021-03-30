package com.forumensak.api.model.cv;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.forumensak.api.model.Cv;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Software {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private int value;
    private String icon;
    @ManyToOne(fetch=FetchType.LAZY)
    @JsonIgnore
    private Cv cv;
}
