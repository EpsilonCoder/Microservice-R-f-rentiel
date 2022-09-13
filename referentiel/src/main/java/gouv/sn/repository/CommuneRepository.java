package gouv.sn.repository;

import gouv.sn.domain.Commune;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Commune entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommuneRepository extends JpaRepository<Commune, Long> {}
