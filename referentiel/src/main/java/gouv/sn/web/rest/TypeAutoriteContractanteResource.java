package gouv.sn.web.rest;

import gouv.sn.domain.TypeAutoriteContractante;
import gouv.sn.repository.TypeAutoriteContractanteRepository;
import gouv.sn.service.TypeAutoriteContractanteService;
import gouv.sn.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link gouv.sn.domain.TypeAutoriteContractante}.
 */
@RestController
@RequestMapping("/api")
public class TypeAutoriteContractanteResource {

    private final Logger log = LoggerFactory.getLogger(TypeAutoriteContractanteResource.class);

    private static final String ENTITY_NAME = "referentielTypeAutoriteContractante";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeAutoriteContractanteService typeAutoriteContractanteService;

    private final TypeAutoriteContractanteRepository typeAutoriteContractanteRepository;

    public TypeAutoriteContractanteResource(
        TypeAutoriteContractanteService typeAutoriteContractanteService,
        TypeAutoriteContractanteRepository typeAutoriteContractanteRepository
    ) {
        this.typeAutoriteContractanteService = typeAutoriteContractanteService;
        this.typeAutoriteContractanteRepository = typeAutoriteContractanteRepository;
    }

    /**
     * {@code POST  /type-autorite-contractantes} : Create a new typeAutoriteContractante.
     *
     * @param typeAutoriteContractante the typeAutoriteContractante to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeAutoriteContractante, or with status {@code 400 (Bad Request)} if the typeAutoriteContractante has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-autorite-contractantes")
    public ResponseEntity<TypeAutoriteContractante> createTypeAutoriteContractante(
        @RequestBody TypeAutoriteContractante typeAutoriteContractante
    ) throws URISyntaxException {
        log.debug("REST request to save TypeAutoriteContractante : {}", typeAutoriteContractante);
        if (typeAutoriteContractante.getId() != null) {
            throw new BadRequestAlertException("A new typeAutoriteContractante cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeAutoriteContractante result = typeAutoriteContractanteService.save(typeAutoriteContractante);
        return ResponseEntity
            .created(new URI("/api/type-autorite-contractantes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /type-autorite-contractantes/:id} : Updates an existing typeAutoriteContractante.
     *
     * @param id the id of the typeAutoriteContractante to save.
     * @param typeAutoriteContractante the typeAutoriteContractante to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeAutoriteContractante,
     * or with status {@code 400 (Bad Request)} if the typeAutoriteContractante is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeAutoriteContractante couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-autorite-contractantes/{id}")
    public ResponseEntity<TypeAutoriteContractante> updateTypeAutoriteContractante(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TypeAutoriteContractante typeAutoriteContractante
    ) throws URISyntaxException {
        log.debug("REST request to update TypeAutoriteContractante : {}, {}", id, typeAutoriteContractante);
        if (typeAutoriteContractante.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, typeAutoriteContractante.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!typeAutoriteContractanteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TypeAutoriteContractante result = typeAutoriteContractanteService.update(typeAutoriteContractante);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeAutoriteContractante.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /type-autorite-contractantes/:id} : Partial updates given fields of an existing typeAutoriteContractante, field will ignore if it is null
     *
     * @param id the id of the typeAutoriteContractante to save.
     * @param typeAutoriteContractante the typeAutoriteContractante to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeAutoriteContractante,
     * or with status {@code 400 (Bad Request)} if the typeAutoriteContractante is not valid,
     * or with status {@code 404 (Not Found)} if the typeAutoriteContractante is not found,
     * or with status {@code 500 (Internal Server Error)} if the typeAutoriteContractante couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/type-autorite-contractantes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TypeAutoriteContractante> partialUpdateTypeAutoriteContractante(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TypeAutoriteContractante typeAutoriteContractante
    ) throws URISyntaxException {
        log.debug("REST request to partial update TypeAutoriteContractante partially : {}, {}", id, typeAutoriteContractante);
        if (typeAutoriteContractante.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, typeAutoriteContractante.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!typeAutoriteContractanteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TypeAutoriteContractante> result = typeAutoriteContractanteService.partialUpdate(typeAutoriteContractante);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeAutoriteContractante.getId().toString())
        );
    }

    /**
     * {@code GET  /type-autorite-contractantes} : get all the typeAutoriteContractantes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeAutoriteContractantes in body.
     */
    @GetMapping("/type-autorite-contractantes")
    public List<TypeAutoriteContractante> getAllTypeAutoriteContractantes() {
        log.debug("REST request to get all TypeAutoriteContractantes");
        return typeAutoriteContractanteService.findAll();
    }

    /**
     * {@code GET  /type-autorite-contractantes/:id} : get the "id" typeAutoriteContractante.
     *
     * @param id the id of the typeAutoriteContractante to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeAutoriteContractante, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-autorite-contractantes/{id}")
    public ResponseEntity<TypeAutoriteContractante> getTypeAutoriteContractante(@PathVariable Long id) {
        log.debug("REST request to get TypeAutoriteContractante : {}", id);
        Optional<TypeAutoriteContractante> typeAutoriteContractante = typeAutoriteContractanteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeAutoriteContractante);
    }

    /**
     * {@code DELETE  /type-autorite-contractantes/:id} : delete the "id" typeAutoriteContractante.
     *
     * @param id the id of the typeAutoriteContractante to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-autorite-contractantes/{id}")
    public ResponseEntity<Void> deleteTypeAutoriteContractante(@PathVariable Long id) {
        log.debug("REST request to delete TypeAutoriteContractante : {}", id);
        typeAutoriteContractanteService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
