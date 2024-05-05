package com.example.assignment7_jsonparser;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpHandler {
    public HttpHandler() {}

    public String makeServiceCall(String reqURL) {
        String response = null;

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = convertStreamToString(in);
        } catch (IOException e) {
            Log.e("HttpHandler", "HTTP Handler error");
        }

        return response;
    }

    private String convertStreamToString(InputStream in) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder sb = new StringBuilder();
        String line;

        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            Log.e("HttpHandler", "HTTP Handler error");
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                Log.e("HttpHandler", "HTTP Handler error");
            }
        }

        return sb.toString();
    }
}
