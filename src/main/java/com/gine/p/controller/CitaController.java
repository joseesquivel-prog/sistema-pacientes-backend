package com.gine.p.controller;

import com.gine.p.model.Cita;
import com.gine.p.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citas")
public class CitaController {

    @Autowired
    private CitaService citaService;

    // ── Endpoints que usa el frontend ──────────────────────────────────────────

    /** GET /api/citas → todas las citas */
    @GetMapping
    public ResponseEntity<List<Cita>> obtenerTodas() {
        return ResponseEntity.ok(citaService.obtenerTodasLasCitas());
    }

    /** GET /api/citas/{id} */
    @GetMapping("/{id}")
    public ResponseEntity<Cita> obtenerPorId(@PathVariable Long id) {
        return citaService.obtenerCitaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST /api/citas
     * El frontend envía: { paciente: {id: X}, fechaHora, motivo, estado, observaciones }
     */
    @PostMapping
    public ResponseEntity<Cita> crearCita(@RequestBody Cita cita) {
        try {
            Cita nueva = citaService.guardarCita(cita);
            return new ResponseEntity<>(nueva, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /** DELETE /api/citas/{id} */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCita(@PathVariable Long id) {
        return citaService.obtenerCitaPorId(id)
                .map(c -> {
                    citaService.eliminarCita(id);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // ── Endpoints extra ────────────────────────────────────────────────────────

    /** GET /api/citas/hoy */
    @GetMapping("/hoy")
    public ResponseEntity<List<Cita>> obtenerDelDia() {
        return ResponseEntity.ok(citaService.obtenerCitasDelDia());
    }

    /** GET /api/citas/paciente/{pacienteId} */
    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<Cita>> obtenerPorPaciente(@PathVariable Long pacienteId) {
        return ResponseEntity.ok(citaService.obtenerCitasPorPaciente(pacienteId));
    }

    /** PATCH /api/citas/{id}/estado?estado=COMPLETADA */
    @PatchMapping("/{id}/estado")
    public ResponseEntity<Cita> cambiarEstado(@PathVariable Long id,
                                               @RequestParam String estado) {
        try {
            return ResponseEntity.ok(citaService.cambiarEstadoCita(id, estado));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /** POST /api/citas/paciente/{pacienteId} (alternativa con ID en URL) */
    @PostMapping("/paciente/{pacienteId}")
    public ResponseEntity<Cita> crearCitaPorPaciente(@PathVariable Long pacienteId,
                                                      @RequestBody Cita cita) {
        try {
            return new ResponseEntity<>(citaService.guardarCita(cita, pacienteId), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
