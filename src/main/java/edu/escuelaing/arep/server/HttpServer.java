package edu.escuelaing.arep.server;

import edu.escuelaing.arep.nanoSpark.NanoSpark;
import edu.escuelaing.arep.nanoSpark.componentes.Request;
import edu.escuelaing.arep.nanoSpark.componentes.Response;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;

public class HttpServer {
    private boolean running;
    private static final String ROUTE_TO_STATIC_FILES = "/src/main/resources/static";
    private ServerSocket serverSocket;
    private OutputStream out;
    private BufferedReader in;
    private final String firebaseURL = "https://firebasestorage.googleapis.com/v0/b/ejemploxd-2f8bf.appspot.com/o/hello.json?alt=media";
    public void startServer() throws IOException {
        NanoSpark nanoSpark = null;
        int port = getPort();
        try {
            serverSocket = new ServerSocket(port);
            nanoSpark = new NanoSpark();
            nanoSpark.get("/", this::redirectToIndexPage);
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + port + ".");
            System.exit(1);
        }
        startAcceptingRequests(nanoSpark);
    }


    public void getStaticFiles(String endpoint) {
        String fullPath = ROUTE_TO_STATIC_FILES + endpoint;
        if (endpoint.contains("png") || endpoint.contains("jpg") ) {
            getImage(fullPath);
        } else if (endpoint.contains("html") || endpoint.contains("js") || endpoint.contains("jsx")) {
            getResource(fullPath);
        }
    }


    public void getResource(String fullPath) {
        String type = fullPath.split("\\.")[1];
        try {
            in = new BufferedReader(new FileReader(System.getProperty("user.dir") + fullPath));
            String outLine = "";
            String line;
            while ((line = in.readLine()) != null) {
                outLine += line;
            }
            out.write(("HTTP/1.1 201 OK\r\n"
                    + "Content-Type: text/" + type + ";"
                    + "charset=\"UTF-8\" \r\n"
                    + "\r\n"
                    + outLine).getBytes());
        } catch (IOException e) {
            int statusCode = 404;
            printErrorMessage(statusCode,
                    "<!DOCTYPE html>\n"
                            + "<html>\n"
                            + "<head>\n"
                            + "<meta charset=\"UTF-8\">\n"
                            + "<title>" + statusCode + " Error</title>\n"
                            + "</head>\n"
                            + "<body>\n"
                            + "<h1>404 File Not Found</h1>\n"
                            + "</body>\n"
                            + "</html>\n", "Not Found");
        }
    }


    public void getImage(String fullPath) {
        String type = fullPath.split("\\.")[1];
        try {
            BufferedImage image = ImageIO.read(new File(System.getProperty("user.dir") + fullPath));
            ByteArrayOutputStream arrBytes = new ByteArrayOutputStream();
            DataOutputStream outImage = new DataOutputStream(out);
            ImageIO.write(image, type, arrBytes);
            outImage.writeBytes("HTTP/1.1 200 OK \r\n"
                    + "Content-Type: image/" + type + " \r\n"
                    + "\r\n");
            out.write(arrBytes.toByteArray());
        } catch (IOException e) {
            int statusCode = 404;
            printErrorMessage(statusCode,
                    "<!DOCTYPE html>\n"
                            + "<html>\n"
                            + "<head>\n"
                            + "<meta charset=\"UTF-8\">\n"
                            + "<title>" + statusCode + " Error</title>\n"
                            + "</head>\n"
                            + "<body>\n"
                            + "<h1>404 File Not Found</h1>\n"
                            + "</body>\n"
                            + "</html>\n", "Not Found");
        }
    }


    public void printErrorMessage(int statusCode, String message, String statusName) {
        try {
            out.write(("HTTP/1.1 " + statusCode + " " + statusName + "\r\n"
                    + "\r\n"
                    + message).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public OutputStream getOut() {
        return out;
    }


    public void setOut(OutputStream out) {
        this.out = out;
    }

    private void startAcceptingRequests(NanoSpark nanoSpark) throws IOException {
        running = true;
        while (running) {
            Socket clientSocket = null;
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
            receiveRequest(clientSocket, nanoSpark);
        }
        serverSocket.close();
    }

    private void receiveRequest(Socket clientSocket, NanoSpark nanoSpark) throws IOException {
        out = clientSocket.getOutputStream();
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        nanoSpark.setOut(out);
        String inputLine;
        String endpoint;
        while ((inputLine = in.readLine()) != null) {
            if (inputLine.contains("GET")) {
                endpoint = inputLine.split(" ")[1];
                if (!isSparkEndpoint(endpoint, nanoSpark)) getStaticFiles(endpoint);
            }
            if (!in.ready()) break;
        }
        in.close();
        clientSocket.close();
    }

    private boolean isSparkEndpoint(String endpoint, NanoSpark nanoSpark) {
        boolean isSparkEndpoint = false;
        if (endpoint.equals("/")) {
            nanoSpark.check(endpoint);
            isSparkEndpoint = true;
        }
        if (endpoint.contains("/Apps")) {
            endpoint = endpoint.replace("/Apps", "");
            nanoSpark.check(endpoint);
            isSparkEndpoint = true;
        }
        return isSparkEndpoint;
    }

    private String getHelloHandler(Request request, Response response) throws IOException {
        String result = null;
        result = getGreeting(request.queryParams("value"));
        return result;
    }


    private String redirectToIndexPage(Request request, Response response) {
        response.redirect("/index.html");
        return null;
    }

    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4444; //returns default port if heroku-port isn't set (i.e. on localhost)
    }



    public String getGreeting(String name) throws IOException {
        String init;

        URL url = new URL(firebaseURL);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
        init = bufferedReader.readLine();
        init = init.replace("\"", "");
        System.out.println(init);
        return init + " " + name;
    }
}
