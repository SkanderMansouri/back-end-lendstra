package com.hackathon.hackthefuture.service;

import com.hackathon.hackthefuture.domain.Application;
import com.hackathon.hackthefuture.domain.Client;
import com.hackathon.hackthefuture.domain.Demand;
import com.hackathon.hackthefuture.repository.ApplicationRepository;
import com.hackathon.hackthefuture.repository.ClientRepository;
import com.hackathon.hackthefuture.repository.DemandRepository;
import com.hackathon.hackthefuture.service.dto.ApplicationDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Application}.
 */
@Service
@Transactional
public class ApplicationService {

    private final Logger log = LoggerFactory.getLogger(ApplicationService.class);

    private final ApplicationRepository applicationRepository;
    private final ClientRepository clientRepository;
    private final DemandRepository demandRepository;


    public ApplicationService(ApplicationRepository applicationRepository, ClientRepository clientRepository, DemandRepository demandRepository) {
        this.applicationRepository = applicationRepository;
        this.clientRepository = clientRepository;
        this.demandRepository = demandRepository;
    }

    /**
     * Save a application.
     *
     * @param application the entity to save.
     * @return the persisted entity.
     */
    @Transactional
    public Application save(ApplicationDTO application) {
        log.debug("Request to save Application : {}", application);

        Application newApplication  = new Application();
        newApplication.setAmountRequested(application.getAmountRequested());
        newApplication.setDate(application.getDate());
        newApplication.setMarketDescription(application.getMarketDescription());
        newApplication.setProjectDescription(application.getProjectDescription());
        newApplication.setPurpose(application.getPurpose());
        newApplication.setStatus("PENDING");
        newApplication.setTermsIn(application.getTermsIn());

        Client client = clientRepository.getOne(application.getClientId());
        newApplication.setClient(client);
        Application savedApp = applicationRepository.save(newApplication);
        application.getDemands().forEach(demand -> {
            Demand newDemand = new Demand();
            newDemand.setName(demand.getName());
            newDemand.setValue(demand.getValue());
            demandRepository.save(newDemand);
        });

        return savedApp;
    }

    /**
     * Get all the applications.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Application> findAll() {
        log.debug("Request to get all Applications");
        return applicationRepository.findAll();
    }


    /**
     * Get one application by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Application> findOne(Long id) {
        log.debug("Request to get Application : {}", id);
        return applicationRepository.findById(id);
    }

    /**
     * Delete the application by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Application : {}", id);
        applicationRepository.deleteById(id);
    }

    public List<Application> findAllByStatus(String status) {
      return   applicationRepository.findAllByStatus(status);
    }

    public Application approveApplication(Long id) {
        Application application = applicationRepository.getOne(id);
        application.setStatus("APPROVED");
        return applicationRepository.save(application);
    }

    public List<Application> getApplicationsOfClient(Long id) {
        Client client = clientRepository.getOne(id);
        return applicationRepository.findAllByClient(client);
    }
}
