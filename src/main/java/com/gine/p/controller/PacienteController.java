package com.gine.p.controller;

import com.gine.p.model.Paciente;
import com.gine.p.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @PostMapping
    public ResponseEntity<Paciente> crearPaciente(@RequestBody Paciente paciente) {
        Paciente nuevoPaciente = pacienteService.guardarPaciente(paciente);
        return new ResponseEntity<>(nuevoPaciente, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Paciente> obtenerTodosLosPacientes() {
        return pacienteService.obtenerTodosLosPacientes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> obtenerPacientePorId(@PathVariable Long id) {
        return pacienteService.obtenerPacientePorId(id)
                .map(paciente -> new ResponseEntity<>(paciente, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/buscar/codigo/{codigo}")
    public ResponseEntity<Paciente> buscarPorCodigoFacil(@PathVariable String codigo) {
        return pacienteService.obtenerPacientePorCodigoFacil(codigo)
                .map(paciente -> new ResponseEntity<>(paciente, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/buscar/dni/{dni}")
    public ResponseEntity<Paciente> buscarPorDni(@PathVariable String dni) {
        return pacienteService.obtenerPacientePorDni(dni)
                .map(paciente -> new ResponseEntity<>(paciente, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/buscar/nombre")
    public List<Paciente> buscarPorNombre(@RequestParam String nombre) {
        return pacienteService.obtenerPacientesPorNombre(nombre);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Paciente> actualizarPaciente(@PathVariable Long id, @RequestBody Paciente pacienteDetalles) {
        return pacienteService.obtenerPacientePorId(id)
                .map(pacienteExistente -> {
                    pacienteExistente.setNombreCompleto(pacienteDetalles.getNombreCompleto());
                    pacienteExistente.setDni(pacienteDetalles.getDni());
                    pacienteExistente.setCodigoFacil(pacienteDetalles.getCodigoFacil());
                    pacienteExistente.setTelefono(pacienteDetalles.getTelefono());
                    pacienteExistente.setEmail(pacienteDetalles.getEmail());
                    pacienteExistente.setSemanasGestacion(pacienteDetalles.getSemanasGestacion());
                    pacienteExistente.setFechaUltimaMenstruacion(pacienteDetalles.getFechaUltimaMenstruacion());
                    pacienteExistente.setAlergias(pacienteDetalles.getAlergias());
                    pacienteExistente.setGrupoSanguineo(pacienteDetalles.getGrupoSanguineo());
                    pacienteExistente.setObservaciones(pacienteDetalles.getObservaciones());
                    Paciente pacienteActualizado = pacienteService.guardarPaciente(pacienteExistente);
                    return new ResponseEntity<>(pacienteActualizado, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPaciente(@PathVariable Long id) {
        return pacienteService.obtenerPacientePorId(id)
                .map(paciente -> {
                    pacienteService.eliminarPaciente(id);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}