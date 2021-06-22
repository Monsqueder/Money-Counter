package com.moneycounter.counter.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Month {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int monthNum;

    private int yearNum;

    @OneToMany(mappedBy = "month")
    private List<Position> positions;

    private String username;
}
