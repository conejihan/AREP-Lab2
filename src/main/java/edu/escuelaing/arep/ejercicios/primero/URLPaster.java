package edu.escuelaing.arep.ejercicios.primero;

import java.net.MalformedURLException;
import java.net.URL;

public class URLPaster{

    public static void main(String[] args) throws MalformedURLException {

            URL url = new URL("https://campusvirtual.escuelaing.edu.co/moodle/course/view.php?id=892#eci");
            System.out.format("Protocol: %s \n",url.getProtocol());
            System.out.format("Authority: %s \n",url.getAuthority());
            System.out.format("Host: %s \n", url.getHost());
            System.out.format("Port: %s \n", url.getPort());
            System.out.format("Path: %s \n", url.getPath());
            System.out.format("Query: %s \n", url.getQuery());
            System.out.format("File: %s \n", url.getFile());
            System.out.format("Ref: %s \n", url.getRef());


    }
}
