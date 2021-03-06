package com.hackathon.hackthefuture.web.rest;

import com.hackathon.hackthefuture.domain.Application;
import com.hackathon.hackthefuture.service.ApplicationService;
import com.hackathon.hackthefuture.service.dto.ApplicationDTO;
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
 * REST controller for managing {@link com.hackathon.hackthefuture.domain.Application}.
 */
@RestController
@RequestMapping("/api")
public class ApplicationResource {

    private final Logger log = LoggerFactory.getLogger(ApplicationResource.class);

    private static final String ENTITY_NAME = "application";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApplicationService applicationService;

    public ApplicationResource(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    /**
     * {@code POST  /applications} : Create a new application.
     *
     * @param application the application to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new application, or with status {@code 400 (Bad Request)} if the application has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/applications")
    public ResponseEntity<Application> createApplication(@RequestBody ApplicationDTO application) throws URISyntaxException {
        log.debug("REST request to save Application : {}", application);
        Application result = applicationService.save(application);
        return ResponseEntity.created(new URI("/api/applications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /applications} : Updates an existing application.
     *
     * @param application the application to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated application,
     * or with status {@code 400 (Bad Request)} if the application is not valid,
     * or with status {@code 500 (Internal Server Error)} if the application couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/applications")
    public ResponseEntity<Application> updateApplication(@RequestBody ApplicationDTO application) throws URISyntaxException {
        log.debug("REST request to update Application : {}", application);
        Application result = applicationService.save(application);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/applications/{id}/approve")
    public ResponseEntity<?> approveApplication(@PathVariable Long id) throws URISyntaxException {
        log.debug("REST request to approve Application : {}", id);
        applicationService.approveApplication(id);
        return ResponseEntity.ok().body( applicationService.approveApplication(id));
    }

    /**
     * {@code GET  /applications} : get all the applications.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of applications in body.
     */
    @GetMapping("/applications")
    public List<Application> getAllApplications() {
        log.debug("REST request to get all Applications");
        return applicationService.findAll();
    }


    @GetMapping("/applications/{status}")
    public List<Application> getAllApplicationsByStatus(@PathVariable String status) {
        log.debug("REST request to get all Applications");
        return applicationService.findAllByStatus(status);
    }

    @GetMapping("/applications/client/{id}")
    public List<Application> getApplicationsOfClient(@PathVariable Long id) {
        log.debug("REST request to get all Applications");
        return applicationService.getApplicationsOfClient(id);
    }
    /**
     * {@code GET  /applications/:id} : get the "id" application.
     *
     * @param id the id of the application to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the application, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/applications/{id}")
    public ResponseEntity<Application> getApplication(@PathVariable Long id) {
        log.debug("REST request to get Application : {}", id);
        Optional<Application> application = applicationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(application);
    }

    /**
     * {@code DELETE  /applications/:id} : delete the "id" application.
     *
     * @param id the id of the application to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/applications/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {
        log.debug("REST request to delete Application : {}", id);
        applicationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
