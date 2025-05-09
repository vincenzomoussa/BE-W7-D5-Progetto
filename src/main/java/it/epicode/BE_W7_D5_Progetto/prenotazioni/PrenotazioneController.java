package it.epicode.BE_W7_D5_Progetto.prenotazioni;

import it.epicode.BE_W7_D5_Progetto.auth.AppUser;
import it.epicode.BE_W7_D5_Progetto.common.CommonResponse;
import it.epicode.BE_W7_D5_Progetto.eventi.EventoResponse;
import it.epicode.BE_W7_D5_Progetto.eventi.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prenotazioni")

public class PrenotazioneController {

    @Autowired private PrenotazioneService prenotazioneService;

    @Autowired private EventoService eventoService;

    @Autowired private PrenotazioneRepository prenotazioneRepository;

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public CommonResponse creaPrenotazione (@PathVariable Long id, @AuthenticationPrincipal AppUser utente) {

        EventoResponse evento = eventoService.findEventoById(id);
        return prenotazioneService.savePrenotazione (evento.getId(), utente);

    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public List<PrenotazioneResponse> getAllPrenotazioni (@AuthenticationPrincipal AppUser utente) {
        return prenotazioneService.findAllPrenotazioni(utente);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePrenotazione (@PathVariable Long id, @AuthenticationPrincipal AppUser utente) {

        prenotazioneService.deletePrenotazione(id, utente);

    }


}
