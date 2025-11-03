package ma.fstt.atelier5_restapi.controller;

import jakarta.validation.Valid;
import ma.fstt.atelier5_restapi.dto.HistoCarbDto;
import ma.fstt.atelier5_restapi.dto.StationDto;
import ma.fstt.atelier5_restapi.dto.request.AddPriceRequest;
import ma.fstt.atelier5_restapi.dto.request.CreateStationRequest;
import ma.fstt.atelier5_restapi.dto.request.UpdateStationRequest;
import ma.fstt.atelier5_restapi.service.HistoCarbService;
import ma.fstt.atelier5_restapi.service.StationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/stations")
public class StationController {

    private final StationService stationService;
    private final HistoCarbService histoCarbService;

    public StationController(StationService stationService, HistoCarbService histoCarbService) {
        this.stationService = stationService;
        this.histoCarbService = histoCarbService;
    }

    @GetMapping
    public Page<StationDto> list(Pageable pageable) {
        return stationService.listStations(pageable);
    }

    @GetMapping("/{id}")
    public StationDto get(@PathVariable Long id) {
        return stationService.getStation(id);
    }

    @PostMapping
    public ResponseEntity<StationDto> create(@Valid @RequestBody CreateStationRequest req) {
        StationDto dto = stationService.createStation(req);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(location).body(dto);
    }

    @PutMapping("/{id}")
    public StationDto update(@PathVariable Long id, @Valid @RequestBody UpdateStationRequest req) {
        return stationService.updateStation(id, req);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        stationService.deleteStation(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{stationId}/prices")
    public ResponseEntity<HistoCarbDto> addPrice(@PathVariable Long stationId, @Valid @RequestBody AddPriceRequest req) {
        HistoCarbDto dto = histoCarbService.addPriceToStation(stationId, req);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(location).body(dto);
    }

    @GetMapping("/{stationId}/prices")
    public List<HistoCarbDto> getPrices(@PathVariable Long stationId,
                                        @RequestParam(required = false) LocalDate from,
                                        @RequestParam(required = false) LocalDate to) {
        return histoCarbService.getPricesForStation(stationId, from, to);
    }
}


