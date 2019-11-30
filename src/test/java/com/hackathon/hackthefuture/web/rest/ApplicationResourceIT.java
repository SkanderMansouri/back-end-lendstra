package com.hackathon.hackthefuture.web.rest;

import com.hackathon.hackthefuture.HackApp;
import com.hackathon.hackthefuture.domain.Application;
import com.hackathon.hackthefuture.repository.ApplicationRepository;
import com.hackathon.hackthefuture.service.ApplicationService;
import com.hackathon.hackthefuture.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.hackathon.hackthefuture.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ApplicationResource} REST controller.
 */
@SpringBootTest(classes = HackApp.class)
public class ApplicationResourceIT {

    private static final String DEFAULT_PURPOSE = "AAAAAAAAAA";
    private static final String UPDATED_PURPOSE = "BBBBBBBBBB";

    private static final Double DEFAULT_AMOUNT_REQUESTED = 1D;
    private static final Double UPDATED_AMOUNT_REQUESTED = 2D;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_PROJECT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_MARKET_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_MARKET_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_REPAY_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_REPAY_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_TERMS_IN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TERMS_IN = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restApplicationMockMvc;

    private Application application;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ApplicationResource applicationResource = new ApplicationResource(applicationService);
        this.restApplicationMockMvc = MockMvcBuilders.standaloneSetup(applicationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Application createEntity(EntityManager em) {
        Application application = new Application()
            .purpose(DEFAULT_PURPOSE)
            .amountRequested(DEFAULT_AMOUNT_REQUESTED)
            .status(DEFAULT_STATUS)
            .projectDescription(DEFAULT_PROJECT_DESCRIPTION)
            .marketDescription(DEFAULT_MARKET_DESCRIPTION)
            .date(DEFAULT_DATE)
            .repayDescription(DEFAULT_REPAY_DESCRIPTION)
            .termsIn(DEFAULT_TERMS_IN);
        return application;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Application createUpdatedEntity(EntityManager em) {
        Application application = new Application()
            .purpose(UPDATED_PURPOSE)
            .amountRequested(UPDATED_AMOUNT_REQUESTED)
            .status(UPDATED_STATUS)
            .projectDescription(UPDATED_PROJECT_DESCRIPTION)
            .marketDescription(UPDATED_MARKET_DESCRIPTION)
            .date(UPDATED_DATE)
            .repayDescription(UPDATED_REPAY_DESCRIPTION)
            .termsIn(UPDATED_TERMS_IN);
        return application;
    }

    @BeforeEach
    public void initTest() {
        application = createEntity(em);
    }

    @Test
    @Transactional
    public void createApplication() throws Exception {
        int databaseSizeBeforeCreate = applicationRepository.findAll().size();

        // Create the Application
        restApplicationMockMvc.perform(post("/api/applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(application)))
            .andExpect(status().isCreated());

        // Validate the Application in the database
        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeCreate + 1);
        Application testApplication = applicationList.get(applicationList.size() - 1);
        assertThat(testApplication.getPurpose()).isEqualTo(DEFAULT_PURPOSE);
        assertThat(testApplication.getAmountRequested()).isEqualTo(DEFAULT_AMOUNT_REQUESTED);
        assertThat(testApplication.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testApplication.getProjectDescription()).isEqualTo(DEFAULT_PROJECT_DESCRIPTION);
        assertThat(testApplication.getMarketDescription()).isEqualTo(DEFAULT_MARKET_DESCRIPTION);
        assertThat(testApplication.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testApplication.getRepayDescription()).isEqualTo(DEFAULT_REPAY_DESCRIPTION);
        assertThat(testApplication.getTermsIn()).isEqualTo(DEFAULT_TERMS_IN);
    }

    @Test
    @Transactional
    public void createApplicationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = applicationRepository.findAll().size();

        // Create the Application with an existing ID
        application.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplicationMockMvc.perform(post("/api/applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(application)))
            .andExpect(status().isBadRequest());

        // Validate the Application in the database
        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllApplications() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get all the applicationList
        restApplicationMockMvc.perform(get("/api/applications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(application.getId().intValue())))
            .andExpect(jsonPath("$.[*].purpose").value(hasItem(DEFAULT_PURPOSE)))
            .andExpect(jsonPath("$.[*].amountRequested").value(hasItem(DEFAULT_AMOUNT_REQUESTED.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].projectDescription").value(hasItem(DEFAULT_PROJECT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].marketDescription").value(hasItem(DEFAULT_MARKET_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].repayDescription").value(hasItem(DEFAULT_REPAY_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].termsIn").value(hasItem(DEFAULT_TERMS_IN.toString())));
    }
    
    @Test
    @Transactional
    public void getApplication() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(application);

        // Get the application
        restApplicationMockMvc.perform(get("/api/applications/{id}", application.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(application.getId().intValue()))
            .andExpect(jsonPath("$.purpose").value(DEFAULT_PURPOSE))
            .andExpect(jsonPath("$.amountRequested").value(DEFAULT_AMOUNT_REQUESTED.doubleValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.projectDescription").value(DEFAULT_PROJECT_DESCRIPTION))
            .andExpect(jsonPath("$.marketDescription").value(DEFAULT_MARKET_DESCRIPTION))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.repayDescription").value(DEFAULT_REPAY_DESCRIPTION))
            .andExpect(jsonPath("$.termsIn").value(DEFAULT_TERMS_IN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingApplication() throws Exception {
        // Get the application
        restApplicationMockMvc.perform(get("/api/applications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApplication() throws Exception {
        // Initialize the database
        applicationService.save(application);

        int databaseSizeBeforeUpdate = applicationRepository.findAll().size();

        // Update the application
        Application updatedApplication = applicationRepository.findById(application.getId()).get();
        // Disconnect from session so that the updates on updatedApplication are not directly saved in db
        em.detach(updatedApplication);
        updatedApplication
            .purpose(UPDATED_PURPOSE)
            .amountRequested(UPDATED_AMOUNT_REQUESTED)
            .status(UPDATED_STATUS)
            .projectDescription(UPDATED_PROJECT_DESCRIPTION)
            .marketDescription(UPDATED_MARKET_DESCRIPTION)
            .date(UPDATED_DATE)
            .repayDescription(UPDATED_REPAY_DESCRIPTION)
            .termsIn(UPDATED_TERMS_IN);

        restApplicationMockMvc.perform(put("/api/applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedApplication)))
            .andExpect(status().isOk());

        // Validate the Application in the database
        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeUpdate);
        Application testApplication = applicationList.get(applicationList.size() - 1);
        assertThat(testApplication.getPurpose()).isEqualTo(UPDATED_PURPOSE);
        assertThat(testApplication.getAmountRequested()).isEqualTo(UPDATED_AMOUNT_REQUESTED);
        assertThat(testApplication.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testApplication.getProjectDescription()).isEqualTo(UPDATED_PROJECT_DESCRIPTION);
        assertThat(testApplication.getMarketDescription()).isEqualTo(UPDATED_MARKET_DESCRIPTION);
        assertThat(testApplication.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testApplication.getRepayDescription()).isEqualTo(UPDATED_REPAY_DESCRIPTION);
        assertThat(testApplication.getTermsIn()).isEqualTo(UPDATED_TERMS_IN);
    }

    @Test
    @Transactional
    public void updateNonExistingApplication() throws Exception {
        int databaseSizeBeforeUpdate = applicationRepository.findAll().size();

        // Create the Application

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicationMockMvc.perform(put("/api/applications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(application)))
            .andExpect(status().isBadRequest());

        // Validate the Application in the database
        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteApplication() throws Exception {
        // Initialize the database
        applicationService.save(application);

        int databaseSizeBeforeDelete = applicationRepository.findAll().size();

        // Delete the application
        restApplicationMockMvc.perform(delete("/api/applications/{id}", application.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Application> applicationList = applicationRepository.findAll();
        assertThat(applicationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
