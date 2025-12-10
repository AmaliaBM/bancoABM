package com.web.maven.bancoABMModel.repository;

import com.web.maven.bancoABMModel.model.Persona;
import com.web.maven.bancoABMModel.util.JsonReader;

import java.util.List;
import java.util.Optional;

public class PersonaRepository {

    private final List<Persona> personas;

    public PersonaRepository() {
        // Si tienes un JSON de personas independiente: "data/personas.json"
        this.personas = JsonReader.cargarLista("data/personas.json", Persona[].class);

    }

    public List<Persona> cargarPersonas() {
        return personas;
    }

    public Persona buscarPorDni(String dni) {
        if (dni == null) return null;
        Optional<Persona> opt = personas.stream()
                .filter(p -> dni.equalsIgnoreCase(p.getDni()))
                .findFirst();
        return opt.orElse(null);
    }

    public void guardar(Persona p) {
        // simple: añadir a la lista en memoria
        personas.add(p);
        // Si quieres, aquí serializas de nuevo al JSON (implementación opcional)
    }
}
