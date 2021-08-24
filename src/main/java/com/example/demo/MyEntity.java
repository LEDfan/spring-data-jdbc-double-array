package com.example.demo;

import org.springframework.data.annotation.Id;

public class MyEntity {

    @Id
    private Long id;

    private double[] values;

    public MyEntity(Long id, double[] values) {
        this.id = id;
        this.values = values;
    }
}
