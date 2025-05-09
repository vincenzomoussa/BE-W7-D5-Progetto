package it.epicode.BE_W7_D5_Progetto.eventi;


import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoRepository extends JpaRepository<Evento, Long> {
}