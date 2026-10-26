package ec.edu.monster.vista;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.util.NoSuchElementException;

final class EntradaConsola {
    private static final BufferedReader LECTOR = new BufferedReader(new InputStreamReader(System.in, Charset.defaultCharset()));
    static String leerLinea(String etiqueta) {
        if (System.console() != null) {
            String linea = System.console().readLine("%s: ", etiqueta);
            if (linea == null) throw new NoSuchElementException("Entrada cerrada.");
            return linea;
        }
        // NetBeans mezcla la salida del proceso y su editor de entrada. Separar el
        // aviso evita que la salida pendiente desplace el cursor del editor.
        System.out.println(etiqueta + ":");
        System.out.flush();
        try {
            String linea = LECTOR.readLine();
            if (linea == null) throw new NoSuchElementException("Entrada cerrada.");
            return linea;
        } catch (IOException e) { throw new UncheckedIOException("No se pudo leer la entrada.", e); }
    }
    private EntradaConsola() { }
}
