package ro.ctce.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
import ro.ctce.domain.Ocol;
import ro.ctce.repository.OcolRepository;

/**
 * Integration tests for the {@link OcolResource} REST controller.
 */
@SpringBootTest(classes = LicitatiiApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class OcolResourceIT {
    private static final String DEFAULT_NUME_OCOL = "AAAAAAAAAA";
    private static final String UPDATED_NUME_OCOL = "BBBBBBBBBB";

    private static final Integer DEFAULT_ORDINE = 1;
    private static final Integer UPDATED_ORDINE = 2;

    @Autowired
    private OcolRepository ocolRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOcolMockMvc;

    private Ocol ocol;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ocol createEntity(EntityManager em) {
        Ocol ocol = new Ocol().numeOcol(DEFAULT_NUME_OCOL).ordine(DEFAULT_ORDINE);
        return ocol;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ocol createUpdatedEntity(EntityManager em) {
        Ocol ocol = new Ocol().numeOcol(UPDATED_NUME_OCOL).ordine(UPDATED_ORDINE);
        return ocol;
    }

    @BeforeEach
    public void initTest() {
        ocol = createEntity(em);
    }

    @Test
    @Transactional
    public void createOcol() throws Exception {
        int databaseSizeBeforeCreate = ocolRepository.findAll().size();
        // Create the Ocol
        restOcolMockMvc
            .perform(post("/api/ocols").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ocol)))
            .andExpect(status().isCreated());

        // Validate the Ocol in the database
        List<Ocol> ocolList = ocolRepository.findAll();
        assertThat(ocolList).hasSize(databaseSizeBeforeCreate + 1);
        Ocol testOcol = ocolList.get(ocolList.size() - 1);
        assertThat(testOcol.getNumeOcol()).isEqualTo(DEFAULT_NUME_OCOL);
        assertThat(testOcol.getOrdine()).isEqualTo(DEFAULT_ORDINE);
    }

    @Test
    @Transactional
    public void createOcolWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ocolRepository.findAll().size();

        // Create the Ocol with an existing ID
        ocol.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOcolMockMvc
            .perform(post("/api/ocols").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ocol)))
            .andExpect(status().isBadRequest());

        // Validate the Ocol in the database
        List<Ocol> ocolList = ocolRepository.findAll();
        assertThat(ocolList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOcols() throws Exception {
        // Initialize the database
        ocolRepository.saveAndFlush(ocol);

        // Get all the ocolList
        restOcolMockMvc
            .perform(get("/api/ocols?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ocol.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeOcol").value(hasItem(DEFAULT_NUME_OCOL)))
            .andExpect(jsonPath("$.[*].ordine").value(hasItem(DEFAULT_ORDINE)));
    }

    @Test
    @Transactional
    public void getOcol() throws Exception {
        // Initialize the database
        ocolRepository.saveAndFlush(ocol);

        // Get the ocol
        restOcolMockMvc
            .perform(get("/api/ocols/{id}", ocol.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ocol.getId().intValue()))
            .andExpect(jsonPath("$.numeOcol").value(DEFAULT_NUME_OCOL))
            .andExpect(jsonPath("$.ordine").value(DEFAULT_ORDINE));
    }

    @Test
    @Transactional
    public void getNonExistingOcol() throws Exception {
        // Get the ocol
        restOcolMockMvc.perform(get("/api/ocols/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOcol() throws Exception {
        // Initialize the database
        ocolRepository.saveAndFlush(ocol);

        int databaseSizeBeforeUpdate = ocolRepository.findAll().size();

        // Update the ocol
        Ocol updatedOcol = ocolRepository.findById(ocol.getId()).get();
        // Disconnect from session so that the updates on updatedOcol are not directly saved in db
        em.detach(updatedOcol);
        updatedOcol.numeOcol(UPDATED_NUME_OCOL).ordine(UPDATED_ORDINE);

        restOcolMockMvc
            .perform(put("/api/ocols").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(updatedOcol)))
            .andExpect(status().isOk());

        // Validate the Ocol in the database
        List<Ocol> ocolList = ocolRepository.findAll();
        assertThat(ocolList).hasSize(databaseSizeBeforeUpdate);
        Ocol testOcol = ocolList.get(ocolList.size() - 1);
        assertThat(testOcol.getNumeOcol()).isEqualTo(UPDATED_NUME_OCOL);
        assertThat(testOcol.getOrdine()).isEqualTo(UPDATED_ORDINE);
    }

    @Test
    @Transactional
    public void updateNonExistingOcol() throws Exception {
        int databaseSizeBeforeUpdate = ocolRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOcolMockMvc
            .perform(put("/api/ocols").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ocol)))
            .andExpect(status().isBadRequest());

        // Validate the Ocol in the database
        List<Ocol> ocolList = ocolRepository.findAll();
        assertThat(ocolList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOcol() throws Exception {
        // Initialize the database
        ocolRepository.saveAndFlush(ocol);

        int databaseSizeBeforeDelete = ocolRepository.findAll().size();

        // Delete the ocol
        restOcolMockMvc
            .perform(delete("/api/ocols/{id}", ocol.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Ocol> ocolList = ocolRepository.findAll();
        assertThat(ocolList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
