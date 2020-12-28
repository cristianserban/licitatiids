package ro.ctce.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import ro.ctce.domain.Lot;

/**
 * Spring Data  repository for the Lot entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LotRepository extends JpaRepository<Lot, Long> {}
