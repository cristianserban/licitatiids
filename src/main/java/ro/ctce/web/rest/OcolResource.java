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
import ro.ctce.domain.Ocol;
import ro.ctce.repository.OcolRepository;
import ro.ctce.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link ro.ctce.domain.Ocol}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class OcolResource {
    private final Logger log = LoggerFactory.getLogger(OcolResource.class);

    private static final String ENTITY_NAME = "ocol";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OcolRepository ocolRepository;

    public OcolResource(OcolRepository ocolRepository) {
        this.ocolRepository = ocolRepository;
    }

    /**
     * {@code POST  /ocols} : Create a new ocol.
     *
     * @param ocol the ocol to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ocol, or with status {@code 400 (Bad Request)} if the ocol has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ocols")
    public ResponseEntity<Ocol> createOcol(@RequestBody Ocol ocol) throws URISyntaxException {
        log.debug("REST request to save Ocol : {}", ocol);
        if (ocol.getId() != null) {
            throw new BadRequestAlertException("A new ocol cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Ocol result = ocolRepository.save(ocol);
        return ResponseEntity
            .created(new URI("/api/ocols/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ocols} : Updates an existing ocol.
     *
     * @param ocol the ocol to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ocol,
     * or with status {@code 400 (Bad Request)} if the ocol is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ocol couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ocols")
    public ResponseEntity<Ocol> updateOcol(@RequestBody Ocol ocol) throws URISyntaxException {
        log.debug("REST request to update Ocol : {}", ocol);
        if (ocol.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Ocol result = ocolRepository.save(ocol);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ocol.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ocols} : get all the ocols.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ocols in body.
     */
    @GetMapping("/ocols")
    public List<Ocol> getAllOcols() {
        log.debug("REST request to get all Ocols");
        return ocolRepository.findAll();
    }

    /**
     * {@code GET  /ocols/:id} : get the "id" ocol.
     *
     * @param id the id of the ocol to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ocol, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ocols/{id}")
    public ResponseEntity<Ocol> getOcol(@PathVariable Long id) {
        log.debug("REST request to get Ocol : {}", id);
        Optional<Ocol> ocol = ocolRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(ocol);
    }

    /**
     * {@code DELETE  /ocols/:id} : delete the "id" ocol.
     *
     * @param id the id of the ocol to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ocols/{id}")
    public ResponseEntity<Void> deleteOcol(@PathVariable Long id) {
        log.debug("REST request to delete Ocol : {}", id);
        ocolRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
