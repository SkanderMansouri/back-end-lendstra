package com.hackathon.hackthefuture.service.dto;

import com.hackathon.hackthefuture.domain.Client;
import com.hackathon.hackthefuture.domain.Demand;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class ApplicationDTO implements Serializable {

    private String purpose;
    private Double amountRequested;
    private String status;
    private String projectDescription;
    private String marketDescription;
    private LocalDate date;
    private String repayDescription;
    private LocalDate termsIn;
    private Long clientId;
    private Set<DemandDTO> demands = new HashSet<>();

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public Double getAmountRequested() {
        return amountRequested;
    }

    public void setAmountRequested(Double amountRequested) {
        this.amountRequested = amountRequested;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public String getMarketDescription() {
        return marketDescription;
    }

    public void setMarketDescription(String marketDescription) {
        this.marketDescription = marketDescription;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getRepayDescription() {
        return repayDescription;
    }

    public void setRepayDescription(String repayDescription) {
        this.repayDescription = repayDescription;
    }

    public LocalDate getTermsIn() {
        return termsIn;
    }

    public void setTermsIn(LocalDate termsIn) {
        this.termsIn = termsIn;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Set<DemandDTO> getDemands() {
        return demands;
    }

    public void setDemands(Set<DemandDTO> demands) {
        this.demands = demands;
    }
}
