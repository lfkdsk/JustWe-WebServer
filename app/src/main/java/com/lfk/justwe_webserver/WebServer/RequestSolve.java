package com.lfk.justwe_webserver.WebServer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
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
                        params = s.substring(4, httpHeader);
//                        params = params.replaceAll("[/]+", "/");
                        Servers.getLogResult().OnResult("visiting" + params);
                        break;
                    case "POST":

                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            Servers.getLogResult().OnError(e.getMessage());
        }

        OnWebResult result = WebServer.getRule(params);
        if (result != null) {
            if (result instanceof OnWebStringResult) {
                returnString(((OnWebStringResult) result).OnResult());
            } else if (result instanceof OnWebFileResult) {
                returnFile(((OnWebFileResult) result).returnFile());
            }
        }
    }

    private void returnString(String str) {
        try {
            OutputStream o = client.getOutputStream();
            for (int i = 0;
                 i < str.length();
                 i++) {
                o.write(str.charAt(i));
            }
            o.close();
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void returnFile(File file) {
        if (file.exists()) {
            try {
                BufferedInputStream inputStream =
                        new BufferedInputStream(
                                new FileInputStream(file));
                BufferedOutputStream out = new BufferedOutputStream(client.getOutputStream());
                ByteArrayOutputStream tempOut = new ByteArrayOutputStream();
                byte[] buf = new byte[4096];
                int count;
                while ((count = inputStream.read(buf)) != -1) {
                    tempOut.write(buf, 0, count);
                }
                tempOut.flush();
                out.write(tempOut.toByteArray());
                out.flush();
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
