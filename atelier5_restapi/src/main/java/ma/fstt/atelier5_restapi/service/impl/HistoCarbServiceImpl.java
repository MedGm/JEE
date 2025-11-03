package ma.fstt.atelier5_restapi.service.impl;

import ma.fstt.atelier5_restapi.domain.Carburant;
import ma.fstt.atelier5_restapi.domain.HistoCarb;
import ma.fstt.atelier5_restapi.domain.Station;
import ma.fstt.atelier5_restapi.dto.HistoCarbDto;
import ma.fstt.atelier5_restapi.dto.request.AddPriceRequest;
import ma.fstt.atelier5_restapi.exception.ResourceNotFoundException;
import ma.fstt.atelier5_restapi.mapper.EntityMapper;
import ma.fstt.atelier5_restapi.repository.CarburantRepository;
import ma.fstt.atelier5_restapi.repository.HistoCarbRepository;
import ma.fstt.atelier5_restapi.repository.StationRepository;
import ma.fstt.atelier5_restapi.service.HistoCarbService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class HistoCarbServiceImpl implements HistoCarbService {

    private final HistoCarbRepository histoCarbRepository;
    private final StationRepository stationRepository;
    private final CarburantRepository carburantRepository;

    public HistoCarbServiceImpl(HistoCarbRepository histoCarbRepository, StationRepository stationRepository, CarburantRepository carburantRepository) {
        this.histoCarbRepository = histoCarbRepository;
        this.stationRepository = stationRepository;
        this.carburantRepository = carburantRepository;
    }

    @Override
    public HistoCarbDto addPriceToStation(Long stationId, AddPriceRequest req) {
        Station station = stationRepository.findById(stationId).orElseThrow(() -> new ResourceNotFoundException("Station", stationId));
        Carburant carburant = carburantRepository.findById(req.getCarburantId()).orElseThrow(() -> new ResourceNotFoundException("Carburant", req.getCarburantId()));

        HistoCarb h = new HistoCarb();
        h.setStation(station);
        h.setCarburant(carburant);
        h.setDate(req.getDate());
        h.setPrix(req.getPrix());
        return EntityMapper.toDto(histoCarbRepository.save(h));
    }

    @Override
    @Transactional(readOnly = true)
    public List<HistoCarbDto> getPricesForStation(Long stationId, LocalDate from, LocalDate to) {
        List<HistoCarb> list = histoCarbRepository.findByStationId(stationId);
        return list.stream()
                .filter(h -> (from == null || !h.getDate().isBefore(from)) && (to == null || !h.getDate().isAfter(to)))
                .map(EntityMapper::toDto)
                .toList();
    }
}


