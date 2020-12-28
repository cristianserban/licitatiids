package ro.ctce.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import ro.ctce.domain.Ocol;

/**
 * Spring Data  repository for the Ocol entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OcolRepository extends JpaRepository<Ocol, Long> {}
