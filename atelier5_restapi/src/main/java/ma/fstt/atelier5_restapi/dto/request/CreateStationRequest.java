package ma.fstt.atelier5_restapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateStationRequest {
    @NotBlank
    private String nom;
    @NotBlank
    private String ville;
    @NotBlank
    private String adresse;
}


