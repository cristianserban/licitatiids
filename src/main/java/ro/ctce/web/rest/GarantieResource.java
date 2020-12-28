package ro.ctce.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ro.ctce.domain.Garantie;
import ro.ctce.repository.GarantieRepository;
import ro.ctce.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link ro.ctce.domain.Garantie}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class GarantieResource {
    private final Logger log = LoggerFactory.getLogger(GarantieResource.class);

    private static final String ENTITY_NAME = "garantie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GarantieRepository garantieRepository;

    public GarantieResource(GarantieRepository garantieRepository) {
        this.garantieRepository = garantieRepository;
    }

    /**
     * {@code POST  /garanties} : Create a new garantie.
     *
     * @param garantie the garantie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new garantie, or with status {@code 400 (Bad Request)} if the garantie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/garanties")
    public ResponseEntity<Garantie> createGarantie(@RequestBody Garantie garantie) throws URISyntaxException {
        log.debug("REST request to save Garantie : {}", garantie);
        if (garantie.getId() != null) {
            throw new BadRequestAlertException("A new garantie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Garantie result = garantieRepository.save(garantie);
        return ResponseEntity
            .created(new URI("/api/garanties/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /garanties} : Updates an existing garantie.
     *
     * @param garantie the garantie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated garantie,
     * or with status {@code 400 (Bad Request)} if the garantie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the garantie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/garanties")
    public ResponseEntity<Garantie> updateGarantie(@RequestBody Garantie garantie) throws URISyntaxException {
        log.debug("REST request to update Garantie : {}", garantie);
        if (garantie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Garantie result = garantieRepository.save(garantie);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, garantie.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /garanties} : get all the garanties.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of garanties in body.
     */
    @GetMapping("/garanties")
    public List<Garantie> getAllGaranties() {
        log.debug("REST request to get all Garanties");
        return garantieRepository.findAll();
    }

    /**
     * {@code GET  /garanties/:id} : get the "id" garantie.
     *
     * @param id the id of the garantie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the garantie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/garanties/{id}")
    public ResponseEntity<Garantie> getGarantie(@PathVariable Long id) {
        log.debug("REST request to get Garantie : {}", id);
        Optional<Garantie> garantie = garantieRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(garantie);
    }

    /**
     * {@code DELETE  /garanties/:id} : delete the "id" garantie.
     *
     * @param id the id of the garantie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/garanties/{id}")
    public ResponseEntity<Void> deleteGarantie(@PathVariable Long id) {
        log.debug("REST request to delete Garantie : {}", id);
        garantieRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
