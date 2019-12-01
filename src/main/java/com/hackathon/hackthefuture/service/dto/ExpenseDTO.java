package com.hackathon.hackthefuture.service.dto;

import java.io.Serializable;

/**
 * A Demand.
 */
public class ExpenseDTO implements Serializable {

    private String name;
    private Double value;
    private Long clientId;
    private String proof;

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

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getProof() {
        return proof;
    }

    public void setProof(String proof) {
        this.proof = proof;
    }
}
