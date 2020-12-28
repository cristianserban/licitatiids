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
import ro.ctce.domain.Oferta;
import ro.ctce.repository.OfertaRepository;

/**
 * Integration tests for the {@link OfertaResource} REST controller.
 */
@SpringBootTest(classes = LicitatiiApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class OfertaResourceIT {
    private static final Boolean DEFAULT_CASTIGATOARE = false;
    private static final Boolean UPDATED_CASTIGATOARE = true;

    private static final BigDecimal DEFAULT_PAS = new BigDecimal(1);
    private static final BigDecimal UPDATED_PAS = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PRET = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRET = new BigDecimal(2);

    @Autowired
    private OfertaRepository ofertaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOfertaMockMvc;

    private Oferta oferta;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Oferta createEntity(EntityManager em) {
        Oferta oferta = new Oferta().castigatoare(DEFAULT_CASTIGATOARE).pas(DEFAULT_PAS).pret(DEFAULT_PRET);
        return oferta;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Oferta createUpdatedEntity(EntityManager em) {
        Oferta oferta = new Oferta().castigatoare(UPDATED_CASTIGATOARE).pas(UPDATED_PAS).pret(UPDATED_PRET);
        return oferta;
    }

    @BeforeEach
    public void initTest() {
        oferta = createEntity(em);
    }

    @Test
    @Transactional
    public void createOferta() throws Exception {
        int databaseSizeBeforeCreate = ofertaRepository.findAll().size();
        // Create the Oferta
        restOfertaMockMvc
            .perform(post("/api/ofertas").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(oferta)))
            .andExpect(status().isCreated());

        // Validate the Oferta in the database
        List<Oferta> ofertaList = ofertaRepository.findAll();
        assertThat(ofertaList).hasSize(databaseSizeBeforeCreate + 1);
        Oferta testOferta = ofertaList.get(ofertaList.size() - 1);
        assertThat(testOferta.isCastigatoare()).isEqualTo(DEFAULT_CASTIGATOARE);
        assertThat(testOferta.getPas()).isEqualTo(DEFAULT_PAS);
        assertThat(testOferta.getPret()).isEqualTo(DEFAULT_PRET);
    }

    @Test
    @Transactional
    public void createOfertaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ofertaRepository.findAll().size();

        // Create the Oferta with an existing ID
        oferta.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOfertaMockMvc
            .perform(post("/api/ofertas").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(oferta)))
            .andExpect(status().isBadRequest());

        // Validate the Oferta in the database
        List<Oferta> ofertaList = ofertaRepository.findAll();
        assertThat(ofertaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOfertas() throws Exception {
        // Initialize the database
        ofertaRepository.saveAndFlush(oferta);

        // Get all the ofertaList
        restOfertaMockMvc
            .perform(get("/api/ofertas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(oferta.getId().intValue())))
            .andExpect(jsonPath("$.[*].castigatoare").value(hasItem(DEFAULT_CASTIGATOARE.booleanValue())))
            .andExpect(jsonPath("$.[*].pas").value(hasItem(DEFAULT_PAS.intValue())))
            .andExpect(jsonPath("$.[*].pret").value(hasItem(DEFAULT_PRET.intValue())));
    }

    @Test
    @Transactional
    public void getOferta() throws Exception {
        // Initialize the database
        ofertaRepository.saveAndFlush(oferta);

        // Get the oferta
        restOfertaMockMvc
            .perform(get("/api/ofertas/{id}", oferta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(oferta.getId().intValue()))
            .andExpect(jsonPath("$.castigatoare").value(DEFAULT_CASTIGATOARE.booleanValue()))
            .andExpect(jsonPath("$.pas").value(DEFAULT_PAS.intValue()))
            .andExpect(jsonPath("$.pret").value(DEFAULT_PRET.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingOferta() throws Exception {
        // Get the oferta
        restOfertaMockMvc.perform(get("/api/ofertas/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOferta() throws Exception {
        // Initialize the database
        ofertaRepository.saveAndFlush(oferta);

        int databaseSizeBeforeUpdate = ofertaRepository.findAll().size();

        // Update the oferta
        Oferta updatedOferta = ofertaRepository.findById(oferta.getId()).get();
        // Disconnect from session so that the updates on updatedOferta are not directly saved in db
        em.detach(updatedOferta);
        updatedOferta.castigatoare(UPDATED_CASTIGATOARE).pas(UPDATED_PAS).pret(UPDATED_PRET);

        restOfertaMockMvc
            .perform(put("/api/ofertas").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(updatedOferta)))
            .andExpect(status().isOk());

        // Validate the Oferta in the database
        List<Oferta> ofertaList = ofertaRepository.findAll();
        assertThat(ofertaList).hasSize(databaseSizeBeforeUpdate);
        Oferta testOferta = ofertaList.get(ofertaList.size() - 1);
        assertThat(testOferta.isCastigatoare()).isEqualTo(UPDATED_CASTIGATOARE);
        assertThat(testOferta.getPas()).isEqualTo(UPDATED_PAS);
        assertThat(testOferta.getPret()).isEqualTo(UPDATED_PRET);
    }

    @Test
    @Transactional
    public void updateNonExistingOferta() throws Exception {
        int databaseSizeBeforeUpdate = ofertaRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOfertaMockMvc
            .perform(put("/api/ofertas").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(oferta)))
            .andExpect(status().isBadRequest());

        // Validate the Oferta in the database
        List<Oferta> ofertaList = ofertaRepository.findAll();
        assertThat(ofertaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOferta() throws Exception {
        // Initialize the database
        ofertaRepository.saveAndFlush(oferta);

        int databaseSizeBeforeDelete = ofertaRepository.findAll().size();

        // Delete the oferta
        restOfertaMockMvc
            .perform(delete("/api/ofertas/{id}", oferta.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Oferta> ofertaList = ofertaRepository.findAll();
        assertThat(ofertaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
