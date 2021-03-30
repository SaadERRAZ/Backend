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
public class Experience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String occupation;
    private String company;
    private String dateStart;
    private String dateEnd;
    private String description;
    @ManyToOne(fetch=FetchType.LAZY)
    @JsonIgnore
    private Cv cv;
}
