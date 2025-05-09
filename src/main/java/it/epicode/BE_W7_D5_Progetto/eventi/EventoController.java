package it.epicode.BE_W7_D5_Progetto.eventi;

import it.epicode.BE_W7_D5_Progetto.auth.AppUser;
import it.epicode.BE_W7_D5_Progetto.common.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eventi")

public class EventoController {

    @Autowired private EventoService eventoService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<EventoResponse> getAllEventi() {
        return eventoService.findAllEventi();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ORGANIZER')")
    public CommonResponse creaEvento (EventoRequest request, @AuthenticationPrincipal AppUser organizer) {
        return eventoService.saveEvento(request, organizer);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ORGANIZER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancellaEvento (Long id, @AuthenticationPrincipal AppUser organizer) {
        eventoService.deleteEvento(id, organizer);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ORGANIZER')")
    @ResponseStatus(HttpStatus.OK)
    public EventoResponse aggiornaEvento (@PathVariable Long id, @RequestBody EventoRequest request, @AuthenticationPrincipal AppUser organizer) {
        return eventoService.updateEvento(id, request, organizer);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public EventoResponse getEventoById (Long id) {
        return eventoService.findEventoById(id);
    }

}
