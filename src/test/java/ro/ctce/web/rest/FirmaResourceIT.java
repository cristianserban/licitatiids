package ro.ctce.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ro.ctce.LicitatiiApp;
import ro.ctce.domain.Firma;
import ro.ctce.repository.FirmaRepository;

/**
 * Integration tests for the {@link FirmaResource} REST controller.
 */
@SpringBootTest(classes = LicitatiiApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class FirmaResourceIT {
    private static final String DEFAULT_NUME_FIRMA = "AAAAAAAAAA";
    private static final String UPDATED_NUME_FIRMA = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DREPT_PREEMTIUNE = false;
    private static final Boolean UPDATED_DREPT_PREEMTIUNE = true;

    private static final BigDecimal DEFAULT_VOLUM = new BigDecimal(1);
    private static final BigDecimal UPDATED_VOLUM = new BigDecimal(2);

    @Autowired
    private FirmaRepository firmaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFirmaMockMvc;

    private Firma firma;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Firma createEntity(EntityManager em) {
        Firma firma = new Firma().numeFirma(DEFAULT_NUME_FIRMA).dreptPreemtiune(DEFAULT_DREPT_PREEMTIUNE).volum(DEFAULT_VOLUM);
        return firma;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Firma createUpdatedEntity(EntityManager em) {
        Firma firma = new Firma().numeFirma(UPDATED_NUME_FIRMA).dreptPreemtiune(UPDATED_DREPT_PREEMTIUNE).volum(UPDATED_VOLUM);
        return firma;
    }

    @BeforeEach
    public void initTest() {
        firma = createEntity(em);
    }

    @Test
    @Transactional
    public void createFirma() throws Exception {
        int databaseSizeBeforeCreate = firmaRepository.findAll().size();
        // Create the Firma
        restFirmaMockMvc
            .perform(post("/api/firmas").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(firma)))
            .andExpect(status().isCreated());

        // Validate the Firma in the database
        List<Firma> firmaList = firmaRepository.findAll();
        assertThat(firmaList).hasSize(databaseSizeBeforeCreate + 1);
        Firma testFirma = firmaList.get(firmaList.size() - 1);
        assertThat(testFirma.getNumeFirma()).isEqualTo(DEFAULT_NUME_FIRMA);
        assertThat(testFirma.isDreptPreemtiune()).isEqualTo(DEFAULT_DREPT_PREEMTIUNE);
        assertThat(testFirma.getVolum()).isEqualTo(DEFAULT_VOLUM);
    }

    @Test
    @Transactional
    public void createFirmaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = firmaRepository.findAll().size();

        // Create the Firma with an existing ID
        firma.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFirmaMockMvc
            .perform(post("/api/firmas").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(firma)))
            .andExpect(status().isBadRequest());

        // Validate the Firma in the database
        List<Firma> firmaList = firmaRepository.findAll();
        assertThat(firmaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFirmas() throws Exception {
        // Initialize the database
        firmaRepository.saveAndFlush(firma);

        // Get all the firmaList
        restFirmaMockMvc
            .perform(get("/api/firmas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(firma.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeFirma").value(hasItem(DEFAULT_NUME_FIRMA)))
            .andExpect(jsonPath("$.[*].dreptPreemtiune").value(hasItem(DEFAULT_DREPT_PREEMTIUNE.booleanValue())))
            .andExpect(jsonPath("$.[*].volum").value(hasItem(DEFAULT_VOLUM.intValue())));
    }

    @Test
    @Transactional
    public void getFirma() throws Exception {
        // Initialize the database
        firmaRepository.saveAndFlush(firma);

        // Get the firma
        restFirmaMockMvc
            .perform(get("/api/firmas/{id}", firma.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(firma.getId().intValue()))
            .andExpect(jsonPath("$.numeFirma").value(DEFAULT_NUME_FIRMA))
            .andExpect(jsonPath("$.dreptPreemtiune").value(DEFAULT_DREPT_PREEMTIUNE.booleanValue()))
            .andExpect(jsonPath("$.volum").value(DEFAULT_VOLUM.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFirma() throws Exception {
        // Get the firma
        restFirmaMockMvc.perform(get("/api/firmas/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFirma() throws Exception {
        // Initialize the database
        firmaRepository.saveAndFlush(firma);

        int databaseSizeBeforeUpdate = firmaRepository.findAll().size();

        // Update the firma
        Firma updatedFirma = firmaRepository.findById(firma.getId()).get();
        // Disconnect from session so that the updates on updatedFirma are not directly saved in db
        em.detach(updatedFirma);
        updatedFirma.numeFirma(UPDATED_NUME_FIRMA).dreptPreemtiune(UPDATED_DREPT_PREEMTIUNE).volum(UPDATED_VOLUM);

        restFirmaMockMvc
            .perform(put("/api/firmas").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(updatedFirma)))
            .andExpect(status().isOk());

        // Validate the Firma in the database
        List<Firma> firmaList = firmaRepository.findAll();
        assertThat(firmaList).hasSize(databaseSizeBeforeUpdate);
        Firma testFirma = firmaList.get(firmaList.size() - 1);
        assertThat(testFirma.getNumeFirma()).isEqualTo(UPDATED_NUME_FIRMA);
        assertThat(testFirma.isDreptPreemtiune()).isEqualTo(UPDATED_DREPT_PREEMTIUNE);
        assertThat(testFirma.getVolum()).isEqualTo(UPDATED_VOLUM);
    }

    @Test
    @Transactional
    public void updateNonExistingFirma() throws Exception {
        int databaseSizeBeforeUpdate = firmaRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFirmaMockMvc
            .perform(put("/api/firmas").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(firma)))
            .andExpect(status().isBadRequest());

        // Validate the Firma in the database
        List<Firma> firmaList = firmaRepository.findAll();
        assertThat(firmaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFirma() throws Exception {
        // Initialize the database
        firmaRepository.saveAndFlush(firma);

        int databaseSizeBeforeDelete = firmaRepository.findAll().size();

        // Delete the firma
        restFirmaMockMvc
            .perform(delete("/api/firmas/{id}", firma.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Firma> firmaList = firmaRepository.findAll();
        assertThat(firmaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
