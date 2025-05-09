package it.epicode.BE_W7_D5_Progetto.prenotazioni;


import it.epicode.BE_W7_D5_Progetto.auth.AppUser;
import it.epicode.BE_W7_D5_Progetto.eventi.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {

    boolean existsByEventoAndUtente(Evento evento, AppUser utente);

    List<Prenotazione> findByUtente(AppUser utente);

}