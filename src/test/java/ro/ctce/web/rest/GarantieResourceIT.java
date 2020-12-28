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
import ro.ctce.domain.Garantie;
import ro.ctce.repository.GarantieRepository;

/**
 * Integration tests for the {@link GarantieResource} REST controller.
 */
@SpringBootTest(classes = LicitatiiApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class GarantieResourceIT {
    private static final BigDecimal DEFAULT_GARANTIE = new BigDecimal(1);
    private static final BigDecimal UPDATED_GARANTIE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TARIF = new BigDecimal(1);
    private static final BigDecimal UPDATED_TARIF = new BigDecimal(2);

    private static final BigDecimal DEFAULT_GARANTIE_DEPUSA = new BigDecimal(1);
    private static final BigDecimal UPDATED_GARANTIE_DEPUSA = new BigDecimal(2);

    @Autowired
    private GarantieRepository garantieRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGarantieMockMvc;

    private Garantie garantie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Garantie createEntity(EntityManager em) {
        Garantie garantie = new Garantie().garantie(DEFAULT_GARANTIE).tarif(DEFAULT_TARIF).garantieDepusa(DEFAULT_GARANTIE_DEPUSA);
        return garantie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Garantie createUpdatedEntity(EntityManager em) {
        Garantie garantie = new Garantie().garantie(UPDATED_GARANTIE).tarif(UPDATED_TARIF).garantieDepusa(UPDATED_GARANTIE_DEPUSA);
        return garantie;
    }

    @BeforeEach
    public void initTest() {
        garantie = createEntity(em);
    }

    @Test
    @Transactional
    public void createGarantie() throws Exception {
        int databaseSizeBeforeCreate = garantieRepository.findAll().size();
        // Create the Garantie
        restGarantieMockMvc
            .perform(post("/api/garanties").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(garantie)))
            .andExpect(status().isCreated());

        // Validate the Garantie in the database
        List<Garantie> garantieList = garantieRepository.findAll();
        assertThat(garantieList).hasSize(databaseSizeBeforeCreate + 1);
        Garantie testGarantie = garantieList.get(garantieList.size() - 1);
        assertThat(testGarantie.getGarantie()).isEqualTo(DEFAULT_GARANTIE);
        assertThat(testGarantie.getTarif()).isEqualTo(DEFAULT_TARIF);
        assertThat(testGarantie.getGarantieDepusa()).isEqualTo(DEFAULT_GARANTIE_DEPUSA);
    }

    @Test
    @Transactional
    public void createGarantieWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = garantieRepository.findAll().size();

        // Create the Garantie with an existing ID
        garantie.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGarantieMockMvc
            .perform(post("/api/garanties").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(garantie)))
            .andExpect(status().isBadRequest());

        // Validate the Garantie in the database
        List<Garantie> garantieList = garantieRepository.findAll();
        assertThat(garantieList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllGaranties() throws Exception {
        // Initialize the database
        garantieRepository.saveAndFlush(garantie);

        // Get all the garantieList
        restGarantieMockMvc
            .perform(get("/api/garanties?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(garantie.getId().intValue())))
            .andExpect(jsonPath("$.[*].garantie").value(hasItem(DEFAULT_GARANTIE.intValue())))
            .andExpect(jsonPath("$.[*].tarif").value(hasItem(DEFAULT_TARIF.intValue())))
            .andExpect(jsonPath("$.[*].garantieDepusa").value(hasItem(DEFAULT_GARANTIE_DEPUSA.intValue())));
    }

    @Test
    @Transactional
    public void getGarantie() throws Exception {
        // Initialize the database
        garantieRepository.saveAndFlush(garantie);

        // Get the garantie
        restGarantieMockMvc
            .perform(get("/api/garanties/{id}", garantie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(garantie.getId().intValue()))
            .andExpect(jsonPath("$.garantie").value(DEFAULT_GARANTIE.intValue()))
            .andExpect(jsonPath("$.tarif").value(DEFAULT_TARIF.intValue()))
            .andExpect(jsonPath("$.garantieDepusa").value(DEFAULT_GARANTIE_DEPUSA.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingGarantie() throws Exception {
        // Get the garantie
        restGarantieMockMvc.perform(get("/api/garanties/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGarantie() throws Exception {
        // Initialize the database
        garantieRepository.saveAndFlush(garantie);

        int databaseSizeBeforeUpdate = garantieRepository.findAll().size();

        // Update the garantie
        Garantie updatedGarantie = garantieRepository.findById(garantie.getId()).get();
        // Disconnect from session so that the updates on updatedGarantie are not directly saved in db
        em.detach(updatedGarantie);
        updatedGarantie.garantie(UPDATED_GARANTIE).tarif(UPDATED_TARIF).garantieDepusa(UPDATED_GARANTIE_DEPUSA);

        restGarantieMockMvc
            .perform(
                put("/api/garanties").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(updatedGarantie))
            )
            .andExpect(status().isOk());

        // Validate the Garantie in the database
        List<Garantie> garantieList = garantieRepository.findAll();
        assertThat(garantieList).hasSize(databaseSizeBeforeUpdate);
        Garantie testGarantie = garantieList.get(garantieList.size() - 1);
        assertThat(testGarantie.getGarantie()).isEqualTo(UPDATED_GARANTIE);
        assertThat(testGarantie.getTarif()).isEqualTo(UPDATED_TARIF);
        assertThat(testGarantie.getGarantieDepusa()).isEqualTo(UPDATED_GARANTIE_DEPUSA);
    }

    @Test
    @Transactional
    public void updateNonExistingGarantie() throws Exception {
        int databaseSizeBeforeUpdate = garantieRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGarantieMockMvc
            .perform(put("/api/garanties").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(garantie)))
            .andExpect(status().isBadRequest());

        // Validate the Garantie in the database
        List<Garantie> garantieList = garantieRepository.findAll();
        assertThat(garantieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGarantie() throws Exception {
        // Initialize the database
        garantieRepository.saveAndFlush(garantie);

        int databaseSizeBeforeDelete = garantieRepository.findAll().size();

        // Delete the garantie
        restGarantieMockMvc
            .perform(delete("/api/garanties/{id}", garantie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Garantie> garantieList = garantieRepository.findAll();
        assertThat(garantieList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
