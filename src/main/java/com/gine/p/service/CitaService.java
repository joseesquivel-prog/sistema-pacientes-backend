package com.gine.p.service;

import com.gine.p.model.Cita;
import com.gine.p.model.Paciente;
import com.gine.p.repository.CitaRepository;
import com.gine.p.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CitaService {

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    public Cita guardarCita(Cita cita, Long pacienteId) {
        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
        cita.setPaciente(paciente);
        return citaRepository.save(cita);
    }

    public List<Cita> obtenerCitasPorPaciente(Long pacienteId) {
        return citaRepository.findByPacienteId(pacienteId);
    }

    public List<Cita> obtenerCitasDelDia() {
        LocalDateTime inicio = LocalDateTime.now().withHour(0).withMinute(0);
        LocalDateTime fin = LocalDateTime.now().withHour(23).withMinute(59);
        return citaRepository.findCitasEntreFechas(inicio, fin);
    }

    public Cita cambiarEstadoCita(Long id, String nuevoEstado) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));
        cita.setEstado(nuevoEstado);
        return citaRepository.save(cita);
    }
}