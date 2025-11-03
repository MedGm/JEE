package ma.fstt.atelier5_restapi.repository;

import ma.fstt.atelier5_restapi.domain.Carburant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarburantRepository extends JpaRepository<Carburant, Long> {
}


