package ma.fstt.atelier5_restapi.service;

import ma.fstt.atelier5_restapi.dto.CarburantDto;
import ma.fstt.atelier5_restapi.dto.request.CreateCarburantRequest;
import ma.fstt.atelier5_restapi.dto.request.UpdateCarburantRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CarburantService {
    CarburantDto createCarburant(CreateCarburantRequest req);
    Page<CarburantDto> listCarburants(Pageable pageable);
    CarburantDto updateCarburant(Long id, UpdateCarburantRequest req);
    void deleteCarburant(Long id);
}


