package it.epicode.BE_W7_D5_Progetto.prenotazioni;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class PrenotazioneResponse {

    private Long id;

    private String utente;

    private String evento;

}
