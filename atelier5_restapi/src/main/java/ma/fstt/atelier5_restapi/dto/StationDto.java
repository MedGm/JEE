package ma.fstt.atelier5_restapi.dto;

import lombok.Data;

@Data
public class StationDto {
    private Long id;
    private String nom;
    private String ville;
    private String adresse;
}


