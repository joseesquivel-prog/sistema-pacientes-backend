package com.gine.p.service;

import com.gine.p.model.Paciente;
import com.gine.p.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    public Paciente guardarPaciente(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    public List<Paciente> obtenerTodosLosPacientes() {
        return pacienteRepository.findAll();
    }

    public Optional<Paciente> obtenerPacientePorId(Long id) {
        return pacienteRepository.findById(id);
    }

    public Optional<Paciente> obtenerPacientePorCodigoFacil(String codigo) {
        return pacienteRepository.findByCodigoFacil(codigo);
    }

    public Optional<Paciente> obtenerPacientePorDni(String dni) {
        return pacienteRepository.findByDni(dni);
    }

    public List<Paciente> obtenerPacientesPorNombre(String nombre) {
        return pacienteRepository.findByNombreCompletoContainingIgnoreCase(nombre);
    }

    public void eliminarPaciente(Long id) {
        pacienteRepository.deleteById(id);
    }
}