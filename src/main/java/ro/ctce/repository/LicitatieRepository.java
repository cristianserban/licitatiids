package ro.ctce.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ro.ctce.domain.Licitatie;

/**
 * Spring Data  repository for the Licitatie entity.
 */
@Repository
public interface LicitatieRepository extends JpaRepository<Licitatie, Long> {
    @Query(
        value = "select distinct licitatie from Licitatie licitatie left join fetch licitatie.firmas left join fetch licitatie.lots",
        countQuery = "select count(distinct licitatie) from Licitatie licitatie"
    )
    Page<Licitatie> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct licitatie from Licitatie licitatie left join fetch licitatie.firmas left join fetch licitatie.lots")
    List<Licitatie> findAllWithEagerRelationships();

    @Query(
        "select licitatie from Licitatie licitatie left join fetch licitatie.firmas left join fetch licitatie.lots where licitatie.id =:id"
    )
    Optional<Licitatie> findOneWithEagerRelationships(@Param("id") Long id);
}
