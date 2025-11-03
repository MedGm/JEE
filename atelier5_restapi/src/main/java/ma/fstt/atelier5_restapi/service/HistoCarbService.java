package ma.fstt.atelier5_restapi.service;

import ma.fstt.atelier5_restapi.dto.HistoCarbDto;
import ma.fstt.atelier5_restapi.dto.request.AddPriceRequest;

import java.time.LocalDate;
import java.util.List;

public interface HistoCarbService {
    HistoCarbDto addPriceToStation(Long stationId, AddPriceRequest req);
    List<HistoCarbDto> getPricesForStation(Long stationId, LocalDate from, LocalDate to);
}


