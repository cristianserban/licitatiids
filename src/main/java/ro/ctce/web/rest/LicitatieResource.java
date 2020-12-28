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
import ro.ctce.domain.Licitatie;
import ro.ctce.repository.LicitatieRepository;
import ro.ctce.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link ro.ctce.domain.Licitatie}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class LicitatieResource {
    private final Logger log = LoggerFactory.getLogger(LicitatieResource.class);

    private static final String ENTITY_NAME = "licitatie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LicitatieRepository licitatieRepository;

    public LicitatieResource(LicitatieRepository licitatieRepository) {
        this.licitatieRepository = licitatieRepository;
    }

    /**
     * {@code POST  /licitaties} : Create a new licitatie.
     *
     * @param licitatie the licitatie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new licitatie, or with status {@code 400 (Bad Request)} if the licitatie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/licitaties")
    public ResponseEntity<Licitatie> createLicitatie(@RequestBody Licitatie licitatie) throws URISyntaxException {
        log.debug("REST request to save Licitatie : {}", licitatie);
        if (licitatie.getId() != null) {
            throw new BadRequestAlertException("A new licitatie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Licitatie result = licitatieRepository.save(licitatie);
        return ResponseEntity
            .created(new URI("/api/licitaties/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /licitaties} : Updates an existing licitatie.
     *
     * @param licitatie the licitatie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated licitatie,
     * or with status {@code 400 (Bad Request)} if the licitatie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the licitatie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/licitaties")
    public ResponseEntity<Licitatie> updateLicitatie(@RequestBody Licitatie licitatie) throws URISyntaxException {
        log.debug("REST request to update Licitatie : {}", licitatie);
        if (licitatie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Licitatie result = licitatieRepository.save(licitatie);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, licitatie.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /licitaties} : get all the licitaties.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of licitaties in body.
     */
    @GetMapping("/licitaties")
    public List<Licitatie> getAllLicitaties(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Licitaties");
        return licitatieRepository.findAllWithEagerRelationships();
    }

    /**
     * {@code GET  /licitaties/:id} : get the "id" licitatie.
     *
     * @param id the id of the licitatie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the licitatie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/licitaties/{id}")
    public ResponseEntity<Licitatie> getLicitatie(@PathVariable Long id) {
        log.debug("REST request to get Licitatie : {}", id);
        Optional<Licitatie> licitatie = licitatieRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(licitatie);
    }

    /**
     * {@code DELETE  /licitaties/:id} : delete the "id" licitatie.
     *
     * @param id the id of the licitatie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/licitaties/{id}")
    public ResponseEntity<Void> deleteLicitatie(@PathVariable Long id) {
        log.debug("REST request to delete Licitatie : {}", id);
        licitatieRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
