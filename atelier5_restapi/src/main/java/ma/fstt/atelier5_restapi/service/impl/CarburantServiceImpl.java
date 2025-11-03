package ma.fstt.atelier5_restapi.service.impl;

import ma.fstt.atelier5_restapi.domain.Carburant;
import ma.fstt.atelier5_restapi.dto.CarburantDto;
import ma.fstt.atelier5_restapi.dto.request.CreateCarburantRequest;
import ma.fstt.atelier5_restapi.dto.request.UpdateCarburantRequest;
import ma.fstt.atelier5_restapi.exception.ResourceNotFoundException;
import ma.fstt.atelier5_restapi.mapper.EntityMapper;
import ma.fstt.atelier5_restapi.repository.CarburantRepository;
import ma.fstt.atelier5_restapi.service.CarburantService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CarburantServiceImpl implements CarburantService {

    private final CarburantRepository carburantRepository;

    public CarburantServiceImpl(CarburantRepository carburantRepository) {
        this.carburantRepository = carburantRepository;
    }

    @Override
    public CarburantDto createCarburant(CreateCarburantRequest req) {
        Carburant c = new Carburant();
        c.setNom(req.getNom());
        c.setDescription(req.getDescription());
        return EntityMapper.toDto(carburantRepository.save(c));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CarburantDto> listCarburants(Pageable pageable) {
        return carburantRepository.findAll(pageable).map(EntityMapper::toDto);
    }

    @Override
    public CarburantDto updateCarburant(Long id, UpdateCarburantRequest req) {
        Carburant c = carburantRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Carburant", id));
        c.setNom(req.getNom());
        c.setDescription(req.getDescription());
        return EntityMapper.toDto(carburantRepository.save(c));
    }

    @Override
    public void deleteCarburant(Long id) {
        carburantRepository.deleteById(id);
    }
}


