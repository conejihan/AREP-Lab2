package edu.escuelaing.arep.ejercicios.segundo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

public class URLReader {
    public static void main(String[] args) throws Exception {
        String urlArgument = args[0];
        String htmlPageRoute = "src/main/resources/";
        URL url = new URL(urlArgument);
        String filename = url.getHost();
        filename = filename.replace(".", "");

            URLConnection urlConnection = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String inputLine;
            String fullFileName = htmlPageRoute + "resultado.html";
            PrintWriter writer = new PrintWriter(fullFileName, "UTF-8");
            while ((inputLine = reader.readLine()) != null) {
                writer.println(inputLine);
            }
            writer.close();
            System.out.println("el archivo resultado.html fue creado en la ruta " + fullFileName);

    }
}
