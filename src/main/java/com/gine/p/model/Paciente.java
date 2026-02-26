package com.gine.p.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String codigoFacil;

    @Column(nullable = false)
    private String nombreCompleto;

    @Column(nullable = false, unique = true)
    private String dni;

    private LocalDate fechaNacimiento;
    private String telefono;
    private String email;
    private String direccion;
    private Integer semanasGestacion;
    private LocalDate fechaUltimaMenstruacion;
    private String alergias;
    private String grupoSanguineo;
    private String observaciones;
    private LocalDateTime fechaRegistro = LocalDateTime.now();

    public Paciente() {}

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCodigoFacil() { return codigoFacil; }
    public void setCodigoFacil(String codigoFacil) { this.codigoFacil = codigoFacil; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public Integer getSemanasGestacion() { return semanasGestacion; }
    public void setSemanasGestacion(Integer semanasGestacion) { this.semanasGestacion = semanasGestacion; }

    public LocalDate getFechaUltimaMenstruacion() { return fechaUltimaMenstruacion; }
    public void setFechaUltimaMenstruacion(LocalDate fechaUltimaMenstruacion) { this.fechaUltimaMenstruacion = fechaUltimaMenstruacion; }

    public String getAlergias() { return alergias; }
    public void setAlergias(String alergias) { this.alergias = alergias; }

    public String getGrupoSanguineo() { return grupoSanguineo; }
    public void setGrupoSanguineo(String grupoSanguineo) { this.grupoSanguineo = grupoSanguineo; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}