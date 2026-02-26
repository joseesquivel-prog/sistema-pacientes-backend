package com.gine.p.repository;

import com.gine.p.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Optional<Paciente> findByCodigoFacil(String codigoFacil);
    Optional<Paciente> findByDni(String dni);
    List<Paciente> findByNombreCompletoContainingIgnoreCase(String nombre);
}