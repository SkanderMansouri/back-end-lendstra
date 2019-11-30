package com.hackathon.hackthefuture.service.dto;

import com.hackathon.hackthefuture.domain.Application;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A Demand.
 */
public class DemandDTO implements Serializable {

    private String name;
    private Double value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
