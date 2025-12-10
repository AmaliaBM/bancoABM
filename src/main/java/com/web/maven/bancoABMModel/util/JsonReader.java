package com.web.maven.bancoABMModel.util;

import com.google.gson.*;
import com.web.maven.bancoABMModel.model.Usuario;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JsonReader {

    // Método genérico para cualquier lista de objetos
    public static <T> List<T> cargarLista(String ruta, Class<T[]> clazz) {


        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class,
                        (JsonDeserializer<LocalDate>) (json, type, context) -> LocalDate.parse(json.getAsString()))
                .registerTypeAdapter(LocalDateTime.class,
                        (JsonDeserializer<LocalDateTime>) (json, type, context) -> LocalDateTime.parse(json.getAsString()))
                .create();

        List<T> lista = new ArrayList<>();
        try (InputStream is = JsonReader.class.getClassLoader().getResourceAsStream(ruta)) {
            if (is == null) {
                System.err.println("No se encontró el archivo: " + ruta);
                return lista;
            }
            Reader reader = new InputStreamReader(is);
            T[] array = gson.fromJson(reader, clazz);
            return List.of(array);
        } catch (Exception e) {

            e.printStackTrace();
            return lista;
        }
    }

    // Metodo polimorfico para usuarios
    public static List<Usuario> cargarUsuariosPolimorficos(String ruta) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class,
                        (JsonDeserializer<LocalDate>) (json, type, context) -> LocalDate.parse(json.getAsString()))
                .registerTypeAdapter(LocalDateTime.class,
                        (JsonDeserializer<LocalDateTime>) (json, type, context) -> LocalDateTime.parse(json.getAsString()))
                .create();

        List<Usuario> lista = new ArrayList<>();
        try (InputStream is = JsonReader.class.getClassLoader().getResourceAsStream(ruta)) {
            if (is == null) {
                System.err.println("No se encontró el archivo: " + ruta);
                return lista;
            }
            Reader reader = new InputStreamReader(is);
            JsonArray usuariosArray = JsonParser.parseReader(reader).getAsJsonArray(); // <-- leer array directamente

            for (JsonElement e : usuariosArray) {
                JsonObject obj = e.getAsJsonObject();
                String tipo = obj.get("tipo").getAsString();

                Usuario usuario;
                switch (tipo) {
                    case "UsuarioParticular":
                        usuario = gson.fromJson(e,
                                com.web.maven.bancoABMModel.model.UsuarioParticular.class);
                        break;
                    case "UsuarioEmpresa":
                        usuario = gson.fromJson(e,
                                com.web.maven.bancoABMModel.model.UsuarioEmpresa.class);
                        break;
                    default:
                        usuario = gson.fromJson(e, Usuario.class);
                }
                lista.add(usuario);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lista;
    }

}

