package com.moneycounter.counter.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    //USD
    private double count;

    private String date;

    private boolean isConfirmed;

    @ManyToOne
    @JoinColumn(name = "month_id", nullable = false)
    @JsonIgnore
    private Month month;
}
