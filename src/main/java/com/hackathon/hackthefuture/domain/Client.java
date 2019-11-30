package com.hackathon.hackthefuture.domain;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Client.
 */
@Entity
@Table(name = "client")
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "job")
    private String job;

    @Column(name = "address")
    private String address;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "loaned")
    private Double loaned;

    @Column(name = "paid")
    private Double paid;

    @Column(name = "valided")
    private Double valided;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(unique = true)
    private Bank bank;

    @OneToMany(mappedBy = "client",fetch = FetchType.EAGER)
    private Set<Expense> expenses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Client name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public Client job(String job) {
        this.job = job;
        return this;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getAddress() {
        return address;
    }

    public Client address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public Client email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public Client phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Double getLoaned() {
        return loaned;
    }

    public Client loaned(Double loaned) {
        this.loaned = loaned;
        return this;
    }

    public void setLoaned(Double loaned) {
        this.loaned = loaned;
    }

    public Double getPaid() {
        return paid;
    }

    public Client paid(Double paid) {
        this.paid = paid;
        return this;
    }

    public void setPaid(Double paid) {
        this.paid = paid;
    }

    public Double getValided() {
        return valided;
    }

    public Client valided(Double valided) {
        this.valided = valided;
        return this;
    }

    public void setValided(Double valided) {
        this.valided = valided;
    }

    public Bank getBank() {
        return bank;
    }

    public Client bank(Bank bank) {
        this.bank = bank;
        return this;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public Set<Expense> getExpenses() {
        return expenses;
    }

    public Client expenses(Set<Expense> expenses) {
        this.expenses = expenses;
        return this;
    }

    public Client addExpenses(Expense expense) {
        this.expenses.add(expense);
        expense.setClient(this);
        return this;
    }

    public Client removeExpenses(Expense expense) {
        this.expenses.remove(expense);
        expense.setClient(null);
        return this;
    }

    public void setExpenses(Set<Expense> expenses) {
        this.expenses = expenses;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Client)) {
            return false;
        }
        return id != null && id.equals(((Client) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Client{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", job='" + getJob() + "'" +
            ", address='" + getAddress() + "'" +
            ", email='" + getEmail() + "'" +
            ", phone='" + getPhone() + "'" +
            ", loaned=" + getLoaned() +
            ", paid=" + getPaid() +
            ", valided=" + getValided() +
            "}";
    }
}
