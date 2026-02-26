package com.gine.p.repository;

import com.gine.p.model.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {
    List<Cita> findByPacienteId(Long pacienteId);
    
    @Query("SELECT c FROM Cita c WHERE c.fechaHora BETWEEN :inicio AND :fin")
    List<Cita> findCitasEntreFechas(@Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin);
    
    List<Cita> findByEstado(String estado);
}