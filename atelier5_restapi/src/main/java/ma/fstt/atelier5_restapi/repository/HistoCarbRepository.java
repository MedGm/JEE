package ma.fstt.atelier5_restapi.repository;

import ma.fstt.atelier5_restapi.domain.HistoCarb;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoCarbRepository extends JpaRepository<HistoCarb, Long> {
    List<HistoCarb> findByStationId(Long stationId);
    List<HistoCarb> findByStationIdAndCarburantId(Long stationId, Long carburantId);
}


