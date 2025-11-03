package ma.fstt.atelier5_restapi.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class AddPriceRequest {
    @NotNull
    private Long carburantId;
    @NotNull
    @PastOrPresent
    private LocalDate date;
    @NotNull
    @DecimalMin("0.0")
    private BigDecimal prix;
}


