package edu.escuelaing.arep.nanoSpark;

import edu.escuelaing.arep.server.HttpServer;
import edu.escuelaing.arep.nanoSpark.componentes.Request;
import edu.escuelaing.arep.nanoSpark.componentes.Response;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.function.BiFunction;

public class NanoSpark {
    private final HttpServer httpServer = null;
    private OutputStream out;
    private final HashMap<String, BiFunction<Request, Response, String>> endpoints = new HashMap<>();

    public void execute(String path, BiFunction<Request, Response, String> function) {
        Request request = new Request(path);
        Response response = new Response(httpServer, path);
        try {
            String result = function.apply(request, response);
            if (result != null) {
                out.write(("HTTP/1.1 200 OK\r\n"
                        + "\r\n"
                        + result).getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void get(String endpoint, BiFunction<Request, Response, String> function) {
        endpoints.put(endpoint, function);
    }


    public void setOut(OutputStream out) {
        this.out = out;
    }


    public OutputStream getOut() {
        return out;
    }


    public void check(String path) {
        int indexOfValue = path.indexOf("?");
        endpoints.forEach((k, v) -> {
            if (indexOfValue < 0 && path.equals(k)) {
                execute(path, v);
            } else if (indexOfValue >= 0) {
                String pathWithOutValues = path.substring(0, indexOfValue);
                if (pathWithOutValues.equals(k)) {
                    execute(path, v);
                }
            }
        });
    }
}
