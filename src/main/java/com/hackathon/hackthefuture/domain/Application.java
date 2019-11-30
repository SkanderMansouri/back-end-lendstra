package com.hackathon.hackthefuture.domain;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Application.
 */
@Entity
@Table(name = "application")
public class Application implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "purpose")
    private String purpose;

    @Column(name = "amount_requested")
    private Double amountRequested;

    @Column(name = "status")
    private String status;

    @Column(name = "project_description")
    private String projectDescription;

    @Column(name = "market_description")
    private String marketDescription;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "repay_description")
    private String repayDescription;

    @Column(name = "terms_in")
    private LocalDate termsIn;

    @OneToOne
    @JoinColumn(unique = true)
    private Client client;

    @OneToMany(mappedBy = "application")
    private Set<Demand> demands = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPurpose() {
        return purpose;
    }

    public Application purpose(String purpose) {
        this.purpose = purpose;
        return this;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public Double getAmountRequested() {
        return amountRequested;
    }

    public Application amountRequested(Double amountRequested) {
        this.amountRequested = amountRequested;
        return this;
    }

    public void setAmountRequested(Double amountRequested) {
        this.amountRequested = amountRequested;
    }

    public String getStatus() {
        return status;
    }

    public Application status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public Application projectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
        return this;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public String getMarketDescription() {
        return marketDescription;
    }

    public Application marketDescription(String marketDescription) {
        this.marketDescription = marketDescription;
        return this;
    }

    public void setMarketDescription(String marketDescription) {
        this.marketDescription = marketDescription;
    }

    public LocalDate getDate() {
        return date;
    }

    public Application date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getRepayDescription() {
        return repayDescription;
    }

    public Application repayDescription(String repayDescription) {
        this.repayDescription = repayDescription;
        return this;
    }

    public void setRepayDescription(String repayDescription) {
        this.repayDescription = repayDescription;
    }

    public LocalDate getTermsIn() {
        return termsIn;
    }

    public Application termsIn(LocalDate termsIn) {
        this.termsIn = termsIn;
        return this;
    }

    public void setTermsIn(LocalDate termsIn) {
        this.termsIn = termsIn;
    }

    public Client getClient() {
        return client;
    }

    public Application client(Client client) {
        this.client = client;
        return this;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Set<Demand> getDemands() {
        return demands;
    }

    public Application demands(Set<Demand> demands) {
        this.demands = demands;
        return this;
    }

    public Application addDemands(Demand demand) {
        this.demands.add(demand);
        demand.setApplication(this);
        return this;
    }

    public Application removeDemands(Demand demand) {
        this.demands.remove(demand);
        demand.setApplication(null);
        return this;
    }

    public void setDemands(Set<Demand> demands) {
        this.demands = demands;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Application)) {
            return false;
        }
        return id != null && id.equals(((Application) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Application{" +
            "id=" + getId() +
            ", purpose='" + getPurpose() + "'" +
            ", amountRequested=" + getAmountRequested() +
            ", status='" + getStatus() + "'" +
            ", projectDescription='" + getProjectDescription() + "'" +
            ", marketDescription='" + getMarketDescription() + "'" +
            ", date='" + getDate() + "'" +
            ", repayDescription='" + getRepayDescription() + "'" +
            ", termsIn='" + getTermsIn() + "'" +
            "}";
    }
}
