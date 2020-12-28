package ro.ctce.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ro.ctce.LicitatiiApp;
import ro.ctce.domain.Licitatie;
import ro.ctce.domain.enumeration.TipLicitatie;
import ro.ctce.repository.LicitatieRepository;

/**
 * Integration tests for the {@link LicitatieResource} REST controller.
 */
@SpringBootTest(classes = LicitatiiApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class LicitatieResourceIT {
    private static final LocalDate DEFAULT_DATA_LICITATIE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_LICITATIE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_ACTIVA = false;
    private static final Boolean UPDATED_ACTIVA = true;

    private static final TipLicitatie DEFAULT_TIP_LICITATIE = TipLicitatie.INCHISA;
    private static final TipLicitatie UPDATED_TIP_LICITATIE = TipLicitatie.DESCHISA;

    @Autowired
    private LicitatieRepository licitatieRepository;

    @Mock
    private LicitatieRepository licitatieRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLicitatieMockMvc;

    private Licitatie licitatie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Licitatie createEntity(EntityManager em) {
        Licitatie licitatie = new Licitatie()
            .dataLicitatie(DEFAULT_DATA_LICITATIE)
            .activa(DEFAULT_ACTIVA)
            .tipLicitatie(DEFAULT_TIP_LICITATIE);
        return licitatie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Licitatie createUpdatedEntity(EntityManager em) {
        Licitatie licitatie = new Licitatie()
            .dataLicitatie(UPDATED_DATA_LICITATIE)
            .activa(UPDATED_ACTIVA)
            .tipLicitatie(UPDATED_TIP_LICITATIE);
        return licitatie;
    }

    @BeforeEach
    public void initTest() {
        licitatie = createEntity(em);
    }

    @Test
    @Transactional
    public void createLicitatie() throws Exception {
        int databaseSizeBeforeCreate = licitatieRepository.findAll().size();
        // Create the Licitatie
        restLicitatieMockMvc
            .perform(post("/api/licitaties").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(licitatie)))
            .andExpect(status().isCreated());

        // Validate the Licitatie in the database
        List<Licitatie> licitatieList = licitatieRepository.findAll();
        assertThat(licitatieList).hasSize(databaseSizeBeforeCreate + 1);
        Licitatie testLicitatie = licitatieList.get(licitatieList.size() - 1);
        assertThat(testLicitatie.getDataLicitatie()).isEqualTo(DEFAULT_DATA_LICITATIE);
        assertThat(testLicitatie.isActiva()).isEqualTo(DEFAULT_ACTIVA);
        assertThat(testLicitatie.getTipLicitatie()).isEqualTo(DEFAULT_TIP_LICITATIE);
    }

    @Test
    @Transactional
    public void createLicitatieWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = licitatieRepository.findAll().size();

        // Create the Licitatie with an existing ID
        licitatie.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLicitatieMockMvc
            .perform(post("/api/licitaties").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(licitatie)))
            .andExpect(status().isBadRequest());

        // Validate the Licitatie in the database
        List<Licitatie> licitatieList = licitatieRepository.findAll();
        assertThat(licitatieList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLicitaties() throws Exception {
        // Initialize the database
        licitatieRepository.saveAndFlush(licitatie);

        // Get all the licitatieList
        restLicitatieMockMvc
            .perform(get("/api/licitaties?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(licitatie.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataLicitatie").value(hasItem(DEFAULT_DATA_LICITATIE.toString())))
            .andExpect(jsonPath("$.[*].activa").value(hasItem(DEFAULT_ACTIVA.booleanValue())))
            .andExpect(jsonPath("$.[*].tipLicitatie").value(hasItem(DEFAULT_TIP_LICITATIE.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    public void getAllLicitatiesWithEagerRelationshipsIsEnabled() throws Exception {
        when(licitatieRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restLicitatieMockMvc.perform(get("/api/licitaties?eagerload=true")).andExpect(status().isOk());

        verify(licitatieRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    public void getAllLicitatiesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(licitatieRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restLicitatieMockMvc.perform(get("/api/licitaties?eagerload=true")).andExpect(status().isOk());

        verify(licitatieRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getLicitatie() throws Exception {
        // Initialize the database
        licitatieRepository.saveAndFlush(licitatie);

        // Get the licitatie
        restLicitatieMockMvc
            .perform(get("/api/licitaties/{id}", licitatie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(licitatie.getId().intValue()))
            .andExpect(jsonPath("$.dataLicitatie").value(DEFAULT_DATA_LICITATIE.toString()))
            .andExpect(jsonPath("$.activa").value(DEFAULT_ACTIVA.booleanValue()))
            .andExpect(jsonPath("$.tipLicitatie").value(DEFAULT_TIP_LICITATIE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLicitatie() throws Exception {
        // Get the licitatie
        restLicitatieMockMvc.perform(get("/api/licitaties/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLicitatie() throws Exception {
        // Initialize the database
        licitatieRepository.saveAndFlush(licitatie);

        int databaseSizeBeforeUpdate = licitatieRepository.findAll().size();

        // Update the licitatie
        Licitatie updatedLicitatie = licitatieRepository.findById(licitatie.getId()).get();
        // Disconnect from session so that the updates on updatedLicitatie are not directly saved in db
        em.detach(updatedLicitatie);
        updatedLicitatie.dataLicitatie(UPDATED_DATA_LICITATIE).activa(UPDATED_ACTIVA).tipLicitatie(UPDATED_TIP_LICITATIE);

        restLicitatieMockMvc
            .perform(
                put("/api/licitaties").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(updatedLicitatie))
            )
            .andExpect(status().isOk());

        // Validate the Licitatie in the database
        List<Licitatie> licitatieList = licitatieRepository.findAll();
        assertThat(licitatieList).hasSize(databaseSizeBeforeUpdate);
        Licitatie testLicitatie = licitatieList.get(licitatieList.size() - 1);
        assertThat(testLicitatie.getDataLicitatie()).isEqualTo(UPDATED_DATA_LICITATIE);
        assertThat(testLicitatie.isActiva()).isEqualTo(UPDATED_ACTIVA);
        assertThat(testLicitatie.getTipLicitatie()).isEqualTo(UPDATED_TIP_LICITATIE);
    }

    @Test
    @Transactional
    public void updateNonExistingLicitatie() throws Exception {
        int databaseSizeBeforeUpdate = licitatieRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLicitatieMockMvc
            .perform(put("/api/licitaties").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(licitatie)))
            .andExpect(status().isBadRequest());

        // Validate the Licitatie in the database
        List<Licitatie> licitatieList = licitatieRepository.findAll();
        assertThat(licitatieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLicitatie() throws Exception {
        // Initialize the database
        licitatieRepository.saveAndFlush(licitatie);

        int databaseSizeBeforeDelete = licitatieRepository.findAll().size();

        // Delete the licitatie
        restLicitatieMockMvc
            .perform(delete("/api/licitaties/{id}", licitatie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Licitatie> licitatieList = licitatieRepository.findAll();
        assertThat(licitatieList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
