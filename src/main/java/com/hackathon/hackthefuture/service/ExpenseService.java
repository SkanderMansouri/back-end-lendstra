package com.hackathon.hackthefuture.service;

import com.hackathon.hackthefuture.domain.Client;
import com.hackathon.hackthefuture.domain.Expense;
import com.hackathon.hackthefuture.repository.ClientRepository;
import com.hackathon.hackthefuture.repository.ExpenseRepository;
import com.hackathon.hackthefuture.service.dto.ExpenseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Expense}.
 */
@Service
@Transactional
public class ExpenseService {

    private final Logger log = LoggerFactory.getLogger(ExpenseService.class);

    private final ExpenseRepository expenseRepository;
    private final ClientRepository clientRepository;



    public ExpenseService(ExpenseRepository expenseRepository, ClientRepository clientRepository) {
        this.expenseRepository = expenseRepository;
        this.clientRepository = clientRepository;
    }

    /**
     * Save a expense.
     *
     * @param expense the entity to save.
     * @return the persisted entity.
     */
    @Transactional
    public Expense save(ExpenseDTO expense) {
        log.debug("Request to save Expense : {}", expense);
        Client client = clientRepository.getOne(expense.getClientId());
        client.setValided(client.getValided() + expense.getValue());
        clientRepository.save(client);
        Expense newExpense = new Expense();
        newExpense.setClient(client);
        newExpense.setName(expense.getName());
        newExpense.setValue(expense.getValue());
        newExpense.setProof(expense.getProof());
        return expenseRepository.save(newExpense);
    }

    /**
     * Get all the expenses.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Expense> findAll() {
        log.debug("Request to get all Expenses");
        return expenseRepository.findAll();
    }


    /**
     * Get one expense by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Expense> findOne(Long id) {
        log.debug("Request to get Expense : {}", id);
        return expenseRepository.findById(id);
    }

    /**
     * Delete the expense by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Expense : {}", id);
        expenseRepository.deleteById(id);
    }
}
