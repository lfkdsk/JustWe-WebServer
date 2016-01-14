package com.lfk.justwe_webserver.WebServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Solve request
 *
 * @author liufengkai
 *         Created by liufengkai on 16/1/14.
 */
public class RequestSolve extends Thread {
    private Socket client;
    private BufferedReader clientReader;
    // params in link
    private String params;

    public RequestSolve(Socket s) {
        super();
        this.client = s;

    }

    @Override
    public void run() {
        super.run();
        try {
            clientReader = new BufferedReader(new InputStreamReader(client.getInputStream()));

            while (true) {
                String s = clientReader.readLine().trim();

                if (s.equals("")) {
                    break;
                }

                switch (s.substring(0, 3)) {
                    case "GET":
                        // get request link
                        int httpHeader = s.indexOf(" HTTP/");
                        params = s.substring(5, httpHeader);
                        params = params.replaceAll("[/]+", "/");
                        break;
                    case "POST":

                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            Servers.getLogResult().OnError(e.getMessage());
        }
    }
}
