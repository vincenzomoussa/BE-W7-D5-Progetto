package it.epicode.BE_W7_D5_Progetto.eventi;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class EventoRequest {

    @NotBlank(message = "Il titolo è obbligatorio")
    private String titolo;

    @NotBlank(message = "La descrizione è obbligatoria")
    private String descrizione;

    @NotNull(message = "La data è obbligatoria")
    private LocalDate data;

    @NotBlank(message = "Il luogo è obbligatorio")
    private String luogo;

    @NotNull(message = "Il numero di posti disponibili é obbligatorio")
    @Min(value = 0, message = "Il numero di posti disponibili deve essere maggiore o uguale a 0")
    private int numeroDiPostiDisponibili;

}
