package ma.fstt.atelier5_restapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateCarburantRequest {
    @NotBlank
    private String nom;
    private String description;
}


