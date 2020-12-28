package ro.ctce.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import ro.ctce.domain.Oferta;

/**
 * Spring Data  repository for the Oferta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OfertaRepository extends JpaRepository<Oferta, Long> {}
