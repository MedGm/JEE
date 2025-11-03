package ma.fstt.atelier5_restapi.controller;

import jakarta.validation.Valid;
import ma.fstt.atelier5_restapi.dto.CarburantDto;
import ma.fstt.atelier5_restapi.dto.request.CreateCarburantRequest;
import ma.fstt.atelier5_restapi.dto.request.UpdateCarburantRequest;
import ma.fstt.atelier5_restapi.service.CarburantService;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/carburants")
public class CarburantController {

    private final CarburantService carburantService;

    public CarburantController(CarburantService carburantService) {
        this.carburantService = carburantService;
    }

    @GetMapping
    public Page<CarburantDto> list(Pageable pageable) {
        return carburantService.listCarburants(pageable);
    }

    @PostMapping
    public ResponseEntity<CarburantDto> create(@Valid @RequestBody CreateCarburantRequest req) {
        CarburantDto dto = carburantService.createCarburant(req);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(location).body(dto);
    }

    @PutMapping("/{id}")
    public CarburantDto update(@PathVariable Long id, @Valid @RequestBody UpdateCarburantRequest req) {
        return carburantService.updateCarburant(id, req);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        carburantService.deleteCarburant(id);
        return ResponseEntity.noContent().build();
    }
}


