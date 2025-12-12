package com.web.maven.bancoABMModel.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DatabaseConfig {

    private static String URL;
    private static String USER;
    private static String PASSWORD;

    static {
        try (InputStream is = DatabaseConfig.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (is == null) throw new RuntimeException("No se encontró db.properties");
            Properties props = new Properties();
            props.load(is);
            URL = props.getProperty("DB_URL");
            USER = props.getProperty("DB_USER");
            PASSWORD = props.getProperty("DB_PASSWORD");
            // Cargar el driver de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al cargar la configuración de la base de datos", e);
        }
    }

    public static Connection getConnection() throws Exception {
        if (URL == null || USER == null || PASSWORD == null) {
            throw new RuntimeException("Parámetros de conexión no configurados");
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}