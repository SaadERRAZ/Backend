package com.forumensak.api.model.cv;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.forumensak.api.model.Cv;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Award {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String organizer;
    private String name;
    private String Position;
    @ManyToOne(fetch=FetchType.LAZY)
    @JsonIgnore
    private Cv cv;
}
