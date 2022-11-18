package com.example.api;

import static com.example.api.Constants.BMW_MAKE_MODEL_API_LINK;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class ApiDataReader {

    public static String getValuesFromApi() throws IOException {
        InputStream apiContentStream = null;
        String result = "";
        try {
            apiContentStream = downloadUrlContent(BMW_MAKE_MODEL_API_LINK);
            result = BmwModelsXmlParser.getBmwModels(apiContentStream);
        }
        finally {
            if (apiContentStream != null) {
                apiContentStream.close();
            }
        }
        return result;
    }

    private static InputStream downloadUrlContent(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000);
        conn.setConnectTimeout(15000);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        conn.connect();
        return conn.getInputStream();
    }
}
