package com.gine.p.soap;

import com.gine.p.model.Paciente;
import com.gine.p.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.List;
import java.util.Optional;

/**
 * Endpoint SOAP para operaciones de Paciente.
 * WSDL disponible en: http://localhost:8080/ws/pacientes.wsdl
 * Namespace: http://ginecologia.com/soap
 */
@Endpoint
public class PacienteEndpoint {

    private static final String NAMESPACE_URI = "http://ginecologia.com/soap";

    @Autowired
    private PacienteService pacienteService;

    // ─── Obtener un paciente por ID ───────────────────────────────────────────

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getPacienteRequest")
    @ResponsePayload
    public Element getPaciente(@RequestPayload Element request) throws Exception {
        long id = Long.parseLong(getTagValue(request, "id"));
        Optional<Paciente> opt = pacienteService.obtenerPacientePorId(id);

        Document doc = newDocument();
        Element response = doc.createElementNS(NAMESPACE_URI, "getPacienteResponse");

        if (opt.isPresent()) {
            response.appendChild(pacienteToElement(doc, opt.get()));
        }
        return response;
    }

    // ─── Obtener todos los pacientes ──────────────────────────────────────────

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllPacientesRequest")
    @ResponsePayload
    public Element getAllPacientes(@RequestPayload Element request) throws Exception {
        List<Paciente> pacientes = pacienteService.obtenerTodosLosPacientes();

        Document doc = newDocument();
        Element response = doc.createElementNS(NAMESPACE_URI, "getAllPacientesResponse");

        for (Paciente p : pacientes) {
            response.appendChild(pacienteToElement(doc, p));
        }
        return response;
    }

    // ─── Crear un nuevo paciente ──────────────────────────────────────────────

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "crearPacienteRequest")
    @ResponsePayload
    public Element crearPaciente(@RequestPayload Element request) throws Exception {
        Paciente paciente = new Paciente();
        paciente.setCodigoFacil(getTagValue(request, "codigoFacil"));
        paciente.setNombreCompleto(getTagValue(request, "nombreCompleto"));
        paciente.setDni(getTagValue(request, "dni"));
        paciente.setTelefono(getTagValue(request, "telefono"));
        paciente.setEmail(getTagValue(request, "email"));
        paciente.setGrupoSanguineo(getTagValue(request, "grupoSanguineo"));
        paciente.setAlergias(getTagValue(request, "alergias"));
        paciente.setObservaciones(getTagValue(request, "observaciones"));

        String semanas = getTagValue(request, "semanasGestacion");
        if (semanas != null && !semanas.isEmpty()) {
            paciente.setSemanasGestacion(Integer.parseInt(semanas));
        }

        Paciente guardado = pacienteService.guardarPaciente(paciente);

        Document doc = newDocument();
        Element response = doc.createElementNS(NAMESPACE_URI, "crearPacienteResponse");
        response.appendChild(pacienteToElement(doc, guardado));
        return response;
    }

    // ─── Helpers ──────────────────────────────────────────────────────────────

    private Element pacienteToElement(Document doc, Paciente p) {
        Element el = doc.createElementNS(NAMESPACE_URI, "paciente");

        appendChild(doc, el, "id",               p.getId() != null ? p.getId().toString() : "");
        appendChild(doc, el, "codigoFacil",      p.getCodigoFacil());
        appendChild(doc, el, "nombreCompleto",   p.getNombreCompleto());
        appendChild(doc, el, "dni",              p.getDni());
        appendChild(doc, el, "telefono",         p.getTelefono());
        appendChild(doc, el, "email",            p.getEmail());
        appendChild(doc, el, "grupoSanguineo",   p.getGrupoSanguineo());
        appendChild(doc, el, "alergias",         p.getAlergias());
        appendChild(doc, el, "semanasGestacion", p.getSemanasGestacion() != null ? p.getSemanasGestacion().toString() : "");
        appendChild(doc, el, "observaciones",    p.getObservaciones());
        appendChild(doc, el, "fechaRegistro",    p.getFechaRegistro() != null ? p.getFechaRegistro().toString() : "");

        return el;
    }

    private void appendChild(Document doc, Element parent, String tag, String value) {
        Element child = doc.createElementNS(NAMESPACE_URI, tag);
        child.setTextContent(value != null ? value : "");
        parent.appendChild(child);
    }

    private String getTagValue(Element element, String tag) {
        var nodes = element.getElementsByTagNameNS(NAMESPACE_URI, tag);
        if (nodes.getLength() == 0) {
            // fallback sin namespace
            nodes = element.getElementsByTagName(tag);
        }
        if (nodes.getLength() > 0) {
            return nodes.item(0).getTextContent();
        }
        return null;
    }

    private Document newDocument() throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.newDocument();
    }
}
