package it.epicode.BE_W7_D5_Progetto.prenotazioni;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class PrenotazioneRequest {

    @NotNull(message = "Inserisci un id")
    private Long id;

}
