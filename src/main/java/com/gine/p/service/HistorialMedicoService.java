package com.gine.p.service;

import com.gine.p.model.HistorialMedico;
import com.gine.p.model.Paciente;
import com.gine.p.repository.HistorialMedicoRepository;
import com.gine.p.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HistorialMedicoService {

    @Autowired
    private HistorialMedicoRepository historialRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    public HistorialMedico guardarHistorial(HistorialMedico historial, Long pacienteId) {
        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
        historial.setPaciente(paciente);
        return historialRepository.save(historial);
    }

    public List<HistorialMedico> obtenerHistorialPorPaciente(Long pacienteId) {
        return historialRepository.findByPacienteId(pacienteId);
    }

    public HistorialMedico obtenerHistorialPorId(Long id) {
        return historialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Historial no encontrado"));
    }

    public void eliminarHistorial(Long id) {
        historialRepository.deleteById(id);
    }
}