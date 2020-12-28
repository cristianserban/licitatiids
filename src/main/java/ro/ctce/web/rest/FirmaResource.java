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
import ro.ctce.domain.Firma;
import ro.ctce.repository.FirmaRepository;
import ro.ctce.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link ro.ctce.domain.Firma}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class FirmaResource {
    private final Logger log = LoggerFactory.getLogger(FirmaResource.class);

    private static final String ENTITY_NAME = "firma";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FirmaRepository firmaRepository;

    public FirmaResource(FirmaRepository firmaRepository) {
        this.firmaRepository = firmaRepository;
    }

    /**
     * {@code POST  /firmas} : Create a new firma.
     *
     * @param firma the firma to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new firma, or with status {@code 400 (Bad Request)} if the firma has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/firmas")
    public ResponseEntity<Firma> createFirma(@RequestBody Firma firma) throws URISyntaxException {
        log.debug("REST request to save Firma : {}", firma);
        if (firma.getId() != null) {
            throw new BadRequestAlertException("A new firma cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Firma result = firmaRepository.save(firma);
        return ResponseEntity
            .created(new URI("/api/firmas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /firmas} : Updates an existing firma.
     *
     * @param firma the firma to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated firma,
     * or with status {@code 400 (Bad Request)} if the firma is not valid,
     * or with status {@code 500 (Internal Server Error)} if the firma couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/firmas")
    public ResponseEntity<Firma> updateFirma(@RequestBody Firma firma) throws URISyntaxException {
        log.debug("REST request to update Firma : {}", firma);
        if (firma.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Firma result = firmaRepository.save(firma);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, firma.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /firmas} : get all the firmas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of firmas in body.
     */
    @GetMapping("/firmas")
    public List<Firma> getAllFirmas() {
        log.debug("REST request to get all Firmas");
        return firmaRepository.findAll();
    }

    /**
     * {@code GET  /firmas/:id} : get the "id" firma.
     *
     * @param id the id of the firma to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the firma, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/firmas/{id}")
    public ResponseEntity<Firma> getFirma(@PathVariable Long id) {
        log.debug("REST request to get Firma : {}", id);
        Optional<Firma> firma = firmaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(firma);
    }

    /**
     * {@code DELETE  /firmas/:id} : delete the "id" firma.
     *
     * @param id the id of the firma to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/firmas/{id}")
    public ResponseEntity<Void> deleteFirma(@PathVariable Long id) {
        log.debug("REST request to delete Firma : {}", id);
        firmaRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
