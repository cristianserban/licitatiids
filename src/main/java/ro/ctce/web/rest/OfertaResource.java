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
import ro.ctce.domain.Oferta;
import ro.ctce.repository.OfertaRepository;
import ro.ctce.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link ro.ctce.domain.Oferta}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class OfertaResource {
    private final Logger log = LoggerFactory.getLogger(OfertaResource.class);

    private static final String ENTITY_NAME = "oferta";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OfertaRepository ofertaRepository;

    public OfertaResource(OfertaRepository ofertaRepository) {
        this.ofertaRepository = ofertaRepository;
    }

    /**
     * {@code POST  /ofertas} : Create a new oferta.
     *
     * @param oferta the oferta to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new oferta, or with status {@code 400 (Bad Request)} if the oferta has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ofertas")
    public ResponseEntity<Oferta> createOferta(@RequestBody Oferta oferta) throws URISyntaxException {
        log.debug("REST request to save Oferta : {}", oferta);
        if (oferta.getId() != null) {
            throw new BadRequestAlertException("A new oferta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Oferta result = ofertaRepository.save(oferta);
        return ResponseEntity
            .created(new URI("/api/ofertas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ofertas} : Updates an existing oferta.
     *
     * @param oferta the oferta to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated oferta,
     * or with status {@code 400 (Bad Request)} if the oferta is not valid,
     * or with status {@code 500 (Internal Server Error)} if the oferta couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ofertas")
    public ResponseEntity<Oferta> updateOferta(@RequestBody Oferta oferta) throws URISyntaxException {
        log.debug("REST request to update Oferta : {}", oferta);
        if (oferta.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Oferta result = ofertaRepository.save(oferta);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, oferta.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ofertas} : get all the ofertas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ofertas in body.
     */
    @GetMapping("/ofertas")
    public List<Oferta> getAllOfertas() {
        log.debug("REST request to get all Ofertas");
        return ofertaRepository.findAll();
    }

    /**
     * {@code GET  /ofertas/:id} : get the "id" oferta.
     *
     * @param id the id of the oferta to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the oferta, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ofertas/{id}")
    public ResponseEntity<Oferta> getOferta(@PathVariable Long id) {
        log.debug("REST request to get Oferta : {}", id);
        Optional<Oferta> oferta = ofertaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(oferta);
    }

    /**
     * {@code DELETE  /ofertas/:id} : delete the "id" oferta.
     *
     * @param id the id of the oferta to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ofertas/{id}")
    public ResponseEntity<Void> deleteOferta(@PathVariable Long id) {
        log.debug("REST request to delete Oferta : {}", id);
        ofertaRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
