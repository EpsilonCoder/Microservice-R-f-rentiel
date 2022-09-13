package gouv.sn.service;

import gouv.sn.domain.TypeAutoriteContractante;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link TypeAutoriteContractante}.
 */
public interface TypeAutoriteContractanteService {
    /**
     * Save a typeAutoriteContractante.
     *
     * @param typeAutoriteContractante the entity to save.
     * @return the persisted entity.
     */
    TypeAutoriteContractante save(TypeAutoriteContractante typeAutoriteContractante);

    /**
     * Updates a typeAutoriteContractante.
     *
     * @param typeAutoriteContractante the entity to update.
     * @return the persisted entity.
     */
    TypeAutoriteContractante update(TypeAutoriteContractante typeAutoriteContractante);

    /**
     * Partially updates a typeAutoriteContractante.
     *
     * @param typeAutoriteContractante the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TypeAutoriteContractante> partialUpdate(TypeAutoriteContractante typeAutoriteContractante);

    /**
     * Get all the typeAutoriteContractantes.
     *
     * @return the list of entities.
     */
    List<TypeAutoriteContractante> findAll();

    /**
     * Get the "id" typeAutoriteContractante.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TypeAutoriteContractante> findOne(Long id);

    /**
     * Delete the "id" typeAutoriteContractante.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
