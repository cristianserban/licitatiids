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
import ro.ctce.domain.Lot;
import ro.ctce.domain.enumeration.StareLot;
import ro.ctce.repository.LotRepository;

/**
 * Integration tests for the {@link LotResource} REST controller.
 */
@SpringBootTest(classes = LicitatiiApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class LotResourceIT {
    private static final Integer DEFAULT_NR_FISA = 1;
    private static final Integer UPDATED_NR_FISA = 2;

    private static final StareLot DEFAULT_STARE = StareLot.ADJUDECAT;
    private static final StareLot UPDATED_STARE = StareLot.NEADJUDECAT;

    private static final BigDecimal DEFAULT_PRET_PORNIRE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRET_PORNIRE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_VOLUM_BRUT = new BigDecimal(1);
    private static final BigDecimal UPDATED_VOLUM_BRUT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_VOLUM_NET = new BigDecimal(1);
    private static final BigDecimal UPDATED_VOLUM_NET = new BigDecimal(2);

    private static final BigDecimal DEFAULT_VOLUM_COAJA = new BigDecimal(1);
    private static final BigDecimal UPDATED_VOLUM_COAJA = new BigDecimal(2);

    @Autowired
    private LotRepository lotRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLotMockMvc;

    private Lot lot;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Lot createEntity(EntityManager em) {
        Lot lot = new Lot()
            .nrFisa(DEFAULT_NR_FISA)
            .stare(DEFAULT_STARE)
            .pretPornire(DEFAULT_PRET_PORNIRE)
            .volumBrut(DEFAULT_VOLUM_BRUT)
            .volumNet(DEFAULT_VOLUM_NET)
            .volumCoaja(DEFAULT_VOLUM_COAJA);
        return lot;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Lot createUpdatedEntity(EntityManager em) {
        Lot lot = new Lot()
            .nrFisa(UPDATED_NR_FISA)
            .stare(UPDATED_STARE)
            .pretPornire(UPDATED_PRET_PORNIRE)
            .volumBrut(UPDATED_VOLUM_BRUT)
            .volumNet(UPDATED_VOLUM_NET)
            .volumCoaja(UPDATED_VOLUM_COAJA);
        return lot;
    }

    @BeforeEach
    public void initTest() {
        lot = createEntity(em);
    }

    @Test
    @Transactional
    public void createLot() throws Exception {
        int databaseSizeBeforeCreate = lotRepository.findAll().size();
        // Create the Lot
        restLotMockMvc
            .perform(post("/api/lots").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lot)))
            .andExpect(status().isCreated());

        // Validate the Lot in the database
        List<Lot> lotList = lotRepository.findAll();
        assertThat(lotList).hasSize(databaseSizeBeforeCreate + 1);
        Lot testLot = lotList.get(lotList.size() - 1);
        assertThat(testLot.getNrFisa()).isEqualTo(DEFAULT_NR_FISA);
        assertThat(testLot.getStare()).isEqualTo(DEFAULT_STARE);
        assertThat(testLot.getPretPornire()).isEqualTo(DEFAULT_PRET_PORNIRE);
        assertThat(testLot.getVolumBrut()).isEqualTo(DEFAULT_VOLUM_BRUT);
        assertThat(testLot.getVolumNet()).isEqualTo(DEFAULT_VOLUM_NET);
        assertThat(testLot.getVolumCoaja()).isEqualTo(DEFAULT_VOLUM_COAJA);
    }

    @Test
    @Transactional
    public void createLotWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lotRepository.findAll().size();

        // Create the Lot with an existing ID
        lot.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLotMockMvc
            .perform(post("/api/lots").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lot)))
            .andExpect(status().isBadRequest());

        // Validate the Lot in the database
        List<Lot> lotList = lotRepository.findAll();
        assertThat(lotList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLots() throws Exception {
        // Initialize the database
        lotRepository.saveAndFlush(lot);

        // Get all the lotList
        restLotMockMvc
            .perform(get("/api/lots?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lot.getId().intValue())))
            .andExpect(jsonPath("$.[*].nrFisa").value(hasItem(DEFAULT_NR_FISA)))
            .andExpect(jsonPath("$.[*].stare").value(hasItem(DEFAULT_STARE.toString())))
            .andExpect(jsonPath("$.[*].pretPornire").value(hasItem(DEFAULT_PRET_PORNIRE.intValue())))
            .andExpect(jsonPath("$.[*].volumBrut").value(hasItem(DEFAULT_VOLUM_BRUT.intValue())))
            .andExpect(jsonPath("$.[*].volumNet").value(hasItem(DEFAULT_VOLUM_NET.intValue())))
            .andExpect(jsonPath("$.[*].volumCoaja").value(hasItem(DEFAULT_VOLUM_COAJA.intValue())));
    }

    @Test
    @Transactional
    public void getLot() throws Exception {
        // Initialize the database
        lotRepository.saveAndFlush(lot);

        // Get the lot
        restLotMockMvc
            .perform(get("/api/lots/{id}", lot.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(lot.getId().intValue()))
            .andExpect(jsonPath("$.nrFisa").value(DEFAULT_NR_FISA))
            .andExpect(jsonPath("$.stare").value(DEFAULT_STARE.toString()))
            .andExpect(jsonPath("$.pretPornire").value(DEFAULT_PRET_PORNIRE.intValue()))
            .andExpect(jsonPath("$.volumBrut").value(DEFAULT_VOLUM_BRUT.intValue()))
            .andExpect(jsonPath("$.volumNet").value(DEFAULT_VOLUM_NET.intValue()))
            .andExpect(jsonPath("$.volumCoaja").value(DEFAULT_VOLUM_COAJA.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingLot() throws Exception {
        // Get the lot
        restLotMockMvc.perform(get("/api/lots/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLot() throws Exception {
        // Initialize the database
        lotRepository.saveAndFlush(lot);

        int databaseSizeBeforeUpdate = lotRepository.findAll().size();

        // Update the lot
        Lot updatedLot = lotRepository.findById(lot.getId()).get();
        // Disconnect from session so that the updates on updatedLot are not directly saved in db
        em.detach(updatedLot);
        updatedLot
            .nrFisa(UPDATED_NR_FISA)
            .stare(UPDATED_STARE)
            .pretPornire(UPDATED_PRET_PORNIRE)
            .volumBrut(UPDATED_VOLUM_BRUT)
            .volumNet(UPDATED_VOLUM_NET)
            .volumCoaja(UPDATED_VOLUM_COAJA);

        restLotMockMvc
            .perform(put("/api/lots").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(updatedLot)))
            .andExpect(status().isOk());

        // Validate the Lot in the database
        List<Lot> lotList = lotRepository.findAll();
        assertThat(lotList).hasSize(databaseSizeBeforeUpdate);
        Lot testLot = lotList.get(lotList.size() - 1);
        assertThat(testLot.getNrFisa()).isEqualTo(UPDATED_NR_FISA);
        assertThat(testLot.getStare()).isEqualTo(UPDATED_STARE);
        assertThat(testLot.getPretPornire()).isEqualTo(UPDATED_PRET_PORNIRE);
        assertThat(testLot.getVolumBrut()).isEqualTo(UPDATED_VOLUM_BRUT);
        assertThat(testLot.getVolumNet()).isEqualTo(UPDATED_VOLUM_NET);
        assertThat(testLot.getVolumCoaja()).isEqualTo(UPDATED_VOLUM_COAJA);
    }

    @Test
    @Transactional
    public void updateNonExistingLot() throws Exception {
        int databaseSizeBeforeUpdate = lotRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLotMockMvc
            .perform(put("/api/lots").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lot)))
            .andExpect(status().isBadRequest());

        // Validate the Lot in the database
        List<Lot> lotList = lotRepository.findAll();
        assertThat(lotList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLot() throws Exception {
        // Initialize the database
        lotRepository.saveAndFlush(lot);

        int databaseSizeBeforeDelete = lotRepository.findAll().size();

        // Delete the lot
        restLotMockMvc.perform(delete("/api/lots/{id}", lot.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Lot> lotList = lotRepository.findAll();
        assertThat(lotList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
