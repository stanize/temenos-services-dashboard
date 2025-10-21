package com.acn.servicesdashboard.service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class TsmService {

    // ✅ TAFJREST endpoint
    private static final String TAFJREST_URL = "http://localhost:8080/TAFJRestServices/resources/ofs";

    // ✅ Authorization (Base64-encoded "username:password")
    private static final String AUTH_HEADER = "Basic dGFmai5hZG1pbjpBWElAZ3RwcXJYNA==";

    // ✅ OFS request to check TSM record
    private static final String OFS_REQUEST = "TSA.SERVICE,/S/PROCESS,IGGI01/123123123,TSM";

    public String getTsmStatus() {
        try {
            // Prepare request body
            String jsonInput = "{\"ofsRequest\":\"" + OFS_REQUEST + "\"}";
            byte[] postData = jsonInput.getBytes(StandardCharsets.UTF_8);

            // Open HTTP connection
            URL url = new URL(TAFJREST_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", AUTH_HEADER);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // Send request payload
            try (OutputStream os = conn.getOutputStream()) {
                os.write(postData);
            }

            // Read response from TAFJREST
            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }
            }

            // Example response includes:
            // "SERVICE.CONTROL:1:1=START,"
            String responseText = response.toString();

            // Extract SERVICE.CONTROL field value
            String keyword = "SERVICE.CONTROL:1:1=";
            int index = responseText.indexOf(keyword);
            if (index != -1) {
                int end = responseText.indexOf(",", index);
                String status = responseText.substring(index + keyword.length(), end != -1 ? end : responseText.length());
                return status;
            }

            return "UNKNOWN";

        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR";
        }
    }

    // 🔁 Placeholder for restart logic
    public String restartTsm() {
        return "Restart logic (via TAFJREST) will be added here.";
    }
}
