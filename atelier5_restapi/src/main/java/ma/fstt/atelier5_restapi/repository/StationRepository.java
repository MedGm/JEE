package ma.fstt.atelier5_restapi.repository;

import ma.fstt.atelier5_restapi.domain.Station;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationRepository extends JpaRepository<Station, Long> {
}


