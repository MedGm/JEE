package ma.fstt.atelier5_restapi.service;

import ma.fstt.atelier5_restapi.dto.StationDto;
import ma.fstt.atelier5_restapi.dto.request.CreateStationRequest;
import ma.fstt.atelier5_restapi.dto.request.UpdateStationRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StationService {
    StationDto createStation(CreateStationRequest req);
    Page<StationDto> listStations(Pageable pageable);
    StationDto getStation(Long id);
    StationDto updateStation(Long id, UpdateStationRequest req);
    void deleteStation(Long id);
}


