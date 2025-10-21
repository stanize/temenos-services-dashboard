package com.acn.servicesdashboard.service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Properties;

public class TsmService {

    private final Properties config = new Properties();

    public TsmService() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new IOException("config.properties not found in resources folder");
            }
            config.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getTsmStatus() {
        String tafjUrl = config.getProperty("tafjrest.url");
        String user = config.getProperty("tafjrest.user");
        String password = config.getProperty("tafjrest.password");

        // Build Basic Auth header dynamically
        String credentials = user + ":" + password;
        String authHeader = config.getProperty("tafjrest.auth");

        // --- You’ll later build the OFS request string dynamically ---
        String ofsRequest = "TSA.SERVICE,/S/PROCESS," + user + "/" + password + ",TSM";

        try {
            String jsonInput = "{\"ofsRequest\":\"" + ofsRequest + "\"}";
            byte[] postData = jsonInput.getBytes(StandardCharsets.UTF_8);

            URL url = new URL(tafjUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", authHeader);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(postData);
            }

            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }
            }

            String responseText = response.toString();
            String keyword = "SERVICE.CONTROL:1:1=";
            int index = responseText.indexOf(keyword);
            if (index != -1) {
                int end = responseText.indexOf(",", index);
                return responseText.substring(index + keyword.length(), end != -1 ? end : responseText.length());
            }

            return "UNKNOWN";

        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR";
        }
    }
}
