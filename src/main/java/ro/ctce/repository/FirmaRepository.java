package ro.ctce.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import ro.ctce.domain.Firma;

/**
 * Spring Data  repository for the Firma entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FirmaRepository extends JpaRepository<Firma, Long> {}
