package it.epicode.BE_W7_D5_Progetto.eventi;

import it.epicode.BE_W7_D5_Progetto.auth.AppUser;
import it.epicode.BE_W7_D5_Progetto.auth.Role;
import it.epicode.BE_W7_D5_Progetto.common.CommonResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated

public class EventoService {

    @Autowired private EventoRepository eventoRepository;

    public CommonResponse saveEvento(@Valid EventoRequest request, AppUser organizer) {
        Evento evento = new Evento();
        BeanUtils.copyProperties(request, evento);
        evento.setOrganizzatore(organizer);
        eventoRepository.save(evento);
        return new CommonResponse(evento.getId());
    }

    public void deleteEvento(Long id, AppUser utenteLoggato) {
        Evento evento = eventoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Evento non trovato"));
        if (!evento.getOrganizzatore().getId().equals(utenteLoggato.getId()) || !evento.getOrganizzatore().getRoles().contains(Role.ROLE_ADMIN)) {
            throw new RuntimeException("Non puoi eliminare questo evento perchè non ne sei nè l'organizzatore, nè l'admin di sistema.");
        }
        eventoRepository.delete(evento);
    }

    public EventoResponse fromEntity (Evento evento) {
        EventoResponse response = new EventoResponse();
        BeanUtils.copyProperties(evento, response);
        response.setOrganizzatore(evento.getOrganizzatore().getUsername());
        return response;
    }

    public EventoResponse updateEvento (Long id, EventoRequest request, AppUser utenteLoggato) {
        Evento evento = eventoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Evento non trovato"));
        if (!evento.getOrganizzatore().getId().equals(utenteLoggato.getId()) || !evento.getOrganizzatore().getRoles().contains(Role.ROLE_ADMIN)) {
            throw new RuntimeException("Non puoi modificare questo evento perchè non ne sei nè l'organizzatore, nè l'admin di sistema.");
        }
        BeanUtils.copyProperties(request, evento);
        eventoRepository.save(evento);
        return fromEntity(evento);
    }

    public List<EventoResponse> findAllEventi() {
        return eventoRepository.findAll()
                .stream()
                .map(this::fromEntity)
                .toList();
    }

    public EventoResponse findEventoById(Long id) {
        Evento evento = eventoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Evento non trovato"));
        return fromEntity(evento);
    }

}
