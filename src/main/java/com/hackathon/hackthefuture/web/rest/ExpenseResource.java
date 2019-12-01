package com.hackathon.hackthefuture.web.rest;

import com.hackathon.hackthefuture.domain.Expense;
import com.hackathon.hackthefuture.service.ExpenseService;
import com.hackathon.hackthefuture.service.dto.ExpenseDTO;
import com.hackathon.hackthefuture.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.hackathon.hackthefuture.domain.Expense}.
 */
@RestController
@RequestMapping("/api")
public class ExpenseResource {

    private final Logger log = LoggerFactory.getLogger(ExpenseResource.class);

    private static final String ENTITY_NAME = "expense";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExpenseService expenseService;

    public ExpenseResource(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    /**
     * {@code POST  /expenses} : Create a new expense.
     *
     * @param expense the expense to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new expense, or with status {@code 400 (Bad Request)} if the expense has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/expenses")
    public ResponseEntity<Expense> createExpense(@RequestBody ExpenseDTO expense) throws URISyntaxException {
        log.debug("REST request to save Expense : {}", expense);
        Expense result = expenseService.save(expense);
        return ResponseEntity.created(new URI("/api/expenses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /expenses} : Updates an existing expense.
     *
     * @param expense the expense to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated expense,
     * or with status {@code 400 (Bad Request)} if the expense is not valid,
     * or with status {@code 500 (Internal Server Error)} if the expense couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/expenses")
    public ResponseEntity<Expense> updateExpense(@RequestBody ExpenseDTO expense) throws URISyntaxException {
        log.debug("REST request to update Expense : {}", expense);
        Expense result = expenseService.save(expense);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code GET  /expenses} : get all the expenses.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of expenses in body.
     */
    @GetMapping("/expenses")
    public List<Expense> getAllExpenses() {
        log.debug("REST request to get all Expenses");
        return expenseService.findAll();
    }

    /**
     * {@code GET  /expenses/:id} : get the "id" expense.
     *
     * @param id the id of the expense to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the expense, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/expenses/{id}")
    public ResponseEntity<Expense> getExpense(@PathVariable Long id) {
        log.debug("REST request to get Expense : {}", id);
        Optional<Expense> expense = expenseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(expense);
    }

    /**
     * {@code DELETE  /expenses/:id} : delete the "id" expense.
     *
     * @param id the id of the expense to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/expenses/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        log.debug("REST request to delete Expense : {}", id);
        expenseService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
