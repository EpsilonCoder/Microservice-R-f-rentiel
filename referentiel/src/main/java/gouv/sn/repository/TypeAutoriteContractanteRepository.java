package gouv.sn.repository;

import gouv.sn.domain.TypeAutoriteContractante;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TypeAutoriteContractante entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeAutoriteContractanteRepository extends JpaRepository<TypeAutoriteContractante, Long> {}
