package it.epicode.BE_W7_D5_Progetto.eventi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class EventoResponse {

    private Long id;

    private String titolo;

    private String descrizione;

    private LocalDate data;

    private String luogo;

    private int numeroDiPostiDisponibili;

    private String organizzatore;

}
