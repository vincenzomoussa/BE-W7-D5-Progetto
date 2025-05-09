package it.epicode.BE_W7_D5_Progetto.prenotazioni;

import it.epicode.BE_W7_D5_Progetto.auth.AppUser;
import it.epicode.BE_W7_D5_Progetto.auth.Role;
import it.epicode.BE_W7_D5_Progetto.common.CommonResponse;
import it.epicode.BE_W7_D5_Progetto.eventi.Evento;
import it.epicode.BE_W7_D5_Progetto.eventi.EventoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated

public class PrenotazioneService {

    @Autowired private PrenotazioneRepository prenotazioneRepository;

    @Autowired private EventoRepository eventoRepository;

    public CommonResponse savePrenotazione(@Valid Long id, AppUser utente) {

        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Evento non trovato"));

        if (evento.getNumeroDiPostiDisponibili() == 0) {
            throw new IllegalArgumentException("Non è stato possibile prenotare per questo evento, i posti sono esauriti.");
        }

        if (prenotazioneRepository.existsByEventoAndUtente(evento, utente)) {
            throw new IllegalArgumentException("Hai gia' prenotato per questo evento");
        }

        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setUtente(utente);
        prenotazione.setEvento(evento);
        prenotazioneRepository.save(prenotazione);

        evento.setNumeroDiPostiDisponibili(evento.getNumeroDiPostiDisponibili() - 1);
        eventoRepository.save(evento);

        return new CommonResponse(prenotazione.getId());

    }

    public PrenotazioneResponse fromEntity (Prenotazione prenotazione) {

        PrenotazioneResponse response = new PrenotazioneResponse();
        BeanUtils.copyProperties(prenotazione, response);
        response.setEvento(prenotazione.getEvento().getDescrizione());
        response.setUtente(prenotazione.getUtente().getUsername());
        return response;

    }

    public List<PrenotazioneResponse> findAllPrenotazioni(AppUser utente) {

        if (utente == null) {
            throw new IllegalArgumentException("Utente non trovato");
        }

        List <Prenotazione> prenotazioni = prenotazioneRepository.findByUtente(utente);

        if (prenotazioni.isEmpty()) {
            throw new IllegalArgumentException("Nessuna prenotazione trovata");
        }

        return prenotazioni.stream()
                .map(this::fromEntity)
                .toList();

    }


    public void deletePrenotazione(Long id, AppUser utente) {

        Prenotazione prenotazione = prenotazioneRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Prenotazione non trovata"));

        if (!prenotazione.getUtente().getId().equals(utente.getId()) && !utente.getRoles().contains(Role.ROLE_ADMIN)) {

                    throw new IllegalArgumentException("Non puoi cancellare le prenotazioni che non hai effettuato personalmente.");

                }

                Evento evento = prenotazione.getEvento();
                evento.setNumeroDiPostiDisponibili(evento.getNumeroDiPostiDisponibili() + 1);
                eventoRepository.save(evento);

                prenotazioneRepository.deleteById(id);

    }


}
