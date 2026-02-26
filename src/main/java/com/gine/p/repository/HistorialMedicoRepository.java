package com.gine.p.repository;

import com.gine.p.model.HistorialMedico;
import com.gine.p.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HistorialMedicoRepository extends JpaRepository<HistorialMedico, Long> {
    List<HistorialMedico> findByPacienteOrderByFechaConsultaDesc(Paciente paciente);
    List<HistorialMedico> findByPacienteId(Long pacienteId);
}