package ma.fstt.atelier5_restapi.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class HistoCarbDto {
    private Long id;
    private LocalDate date;
    private BigDecimal prix;
    private Long stationId;
    private Long carburantId;
}


