package com.gine.p.controller;

import com.gine.p.model.HistorialMedico;
import com.gine.p.service.HistorialMedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historial")
public class HistorialMedicoController {

    @Autowired
    private HistorialMedicoService historialMedicoService;

    // ── Endpoints que usa el frontend ──────────────────────────────────────────

    /** GET /api/historial → todo el historial */
    @GetMapping
    public ResponseEntity<List<HistorialMedico>> obtenerTodo() {
        return ResponseEntity.ok(historialMedicoService.obtenerTodoElHistorial());
    }

    /** GET /api/historial/{id} */
    @GetMapping("/{id}")
    public ResponseEntity<HistorialMedico> obtenerPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(historialMedicoService.obtenerHistorialPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * POST /api/historial
     * El frontend envía: { paciente: {id: X}, semanasGestacion, peso, presionArterial, sintomas, diagnostico, tratamiento, observaciones }
     */
    @PostMapping
    public ResponseEntity<HistorialMedico> crearHistorial(@RequestBody HistorialMedico historial) {
        try {
            HistorialMedico nuevo = historialMedicoService.guardarHistorial(historial);
            return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /** DELETE /api/historial/{id} */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarHistorial(@PathVariable Long id) {
        try {
            historialMedicoService.obtenerHistorialPorId(id); // verifica existencia
            historialMedicoService.eliminarHistorial(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ── Endpoints extra ────────────────────────────────────────────────────────

    /** GET /api/historial/paciente/{pacienteId} */
    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<HistorialMedico>> obtenerPorPaciente(@PathVariable Long pacienteId) {
        return ResponseEntity.ok(historialMedicoService.obtenerHistorialPorPaciente(pacienteId));
    }

    /** POST /api/historial/paciente/{pacienteId} (alternativa con ID en URL) */
    @PostMapping("/paciente/{pacienteId}")
    public ResponseEntity<HistorialMedico> crearHistorialPorPaciente(@PathVariable Long pacienteId,
                                                                      @RequestBody HistorialMedico historial) {
        try {
            return new ResponseEntity<>(historialMedicoService.guardarHistorial(historial, pacienteId), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
