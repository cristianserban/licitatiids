package ro.ctce.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import ro.ctce.domain.Garantie;

/**
 * Spring Data  repository for the Garantie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GarantieRepository extends JpaRepository<Garantie, Long> {}
