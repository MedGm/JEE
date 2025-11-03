package ma.fstt.atelier5_restapi.service.impl;

import ma.fstt.atelier5_restapi.domain.Station;
import ma.fstt.atelier5_restapi.dto.StationDto;
import ma.fstt.atelier5_restapi.dto.request.CreateStationRequest;
import ma.fstt.atelier5_restapi.dto.request.UpdateStationRequest;
import ma.fstt.atelier5_restapi.exception.ResourceNotFoundException;
import ma.fstt.atelier5_restapi.mapper.EntityMapper;
import ma.fstt.atelier5_restapi.repository.StationRepository;
import ma.fstt.atelier5_restapi.service.StationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StationServiceImpl implements StationService {

    private final StationRepository stationRepository;

    public StationServiceImpl(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    @Override
    public StationDto createStation(CreateStationRequest req) {
        Station s = new Station();
        s.setNom(req.getNom());
        s.setVille(req.getVille());
        s.setAdresse(req.getAdresse());
        return EntityMapper.toDto(stationRepository.save(s));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StationDto> listStations(Pageable pageable) {
        return stationRepository.findAll(pageable).map(EntityMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public StationDto getStation(Long id) {
        Station s = stationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Station", id));
        return EntityMapper.toDto(s);
    }

    @Override
    public StationDto updateStation(Long id, UpdateStationRequest req) {
        Station s = stationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Station", id));
        s.setNom(req.getNom());
        s.setVille(req.getVille());
        s.setAdresse(req.getAdresse());
        return EntityMapper.toDto(stationRepository.save(s));
    }

    @Override
    public void deleteStation(Long id) {
        stationRepository.deleteById(id);
    }
}


