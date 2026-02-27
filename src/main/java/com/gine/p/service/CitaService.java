package com.gine.p.service;

import com.gine.p.model.Cita;
import com.gine.p.model.Paciente;
import com.gine.p.repository.CitaRepository;
import com.gine.p.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CitaService {

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    // Guardar cita recibiendo pacienteId separado (usado por endpoint /paciente/{id})
    public Cita guardarCita(Cita cita, Long pacienteId) {
        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
        cita.setPaciente(paciente);
        return citaRepository.save(cita);
    }

    // Guardar cita cuando el paciente ya viene dentro del objeto (usado por POST /api/citas)
    public Cita guardarCita(Cita cita) {
        if (cita.getPaciente() != null && cita.getPaciente().getId() != null) {
            Paciente paciente = pacienteRepository.findById(cita.getPaciente().getId())
                    .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
            cita.setPaciente(paciente);
        }
        return citaRepository.save(cita);
    }

    public List<Cita> obtenerTodasLasCitas() {
        return citaRepository.findAll();
    }

    public Optional<Cita> obtenerCitaPorId(Long id) {
        return citaRepository.findById(id);
    }

    public List<Cita> obtenerCitasPorPaciente(Long pacienteId) {
        return citaRepository.findByPacienteId(pacienteId);
    }

    public List<Cita> obtenerCitasDelDia() {
        LocalDateTime inicio = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        LocalDateTime fin = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);
        return citaRepository.findCitasEntreFechas(inicio, fin);
    }

    public Cita cambiarEstadoCita(Long id, String nuevoEstado) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));
        cita.setEstado(nuevoEstado);
        return citaRepository.save(cita);
    }

    public void eliminarCita(Long id) {
        citaRepository.deleteById(id);
    }
}
