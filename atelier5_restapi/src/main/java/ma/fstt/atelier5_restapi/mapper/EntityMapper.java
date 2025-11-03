package ma.fstt.atelier5_restapi.mapper;

import ma.fstt.atelier5_restapi.domain.Carburant;
import ma.fstt.atelier5_restapi.domain.HistoCarb;
import ma.fstt.atelier5_restapi.domain.Station;
import ma.fstt.atelier5_restapi.dto.CarburantDto;
import ma.fstt.atelier5_restapi.dto.HistoCarbDto;
import ma.fstt.atelier5_restapi.dto.StationDto;

public final class EntityMapper {
    private EntityMapper() {}

    public static StationDto toDto(Station entity) {
        if (entity == null) return null;
        StationDto dto = new StationDto();
        dto.setId(entity.getId());
        dto.setNom(entity.getNom());
        dto.setVille(entity.getVille());
        dto.setAdresse(entity.getAdresse());
        return dto;
    }

    public static CarburantDto toDto(Carburant entity) {
        if (entity == null) return null;
        CarburantDto dto = new CarburantDto();
        dto.setId(entity.getId());
        dto.setNom(entity.getNom());
        dto.setDescription(entity.getDescription());
        return dto;
    }

    public static HistoCarbDto toDto(HistoCarb entity) {
        if (entity == null) return null;
        HistoCarbDto dto = new HistoCarbDto();
        dto.setId(entity.getId());
        dto.setDate(entity.getDate());
        dto.setPrix(entity.getPrix());
        dto.setStationId(entity.getStation() != null ? entity.getStation().getId() : null);
        dto.setCarburantId(entity.getCarburant() != null ? entity.getCarburant().getId() : null);
        return dto;
    }
}


