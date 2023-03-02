package test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;

class Test {
    public static void main(String[] args) {
        String inputJson = "{msg: \"hello\"}";
        System.out.println("inputJson = " + inputJson);

        HttpURLConnection connection = getConnection("http://144.24.64.73:8080");
        writeInput(connection, inputJson);

        String res = getOutput(connection);
        System.out.println("res = " + res);
    }

    private static String getOutput(HttpURLConnection connection) {
        StringBuilder ret = new StringBuilder();

        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                ret.append(line);
                ret.append('\n');
            }
            rd.close();
        } catch (Exception e) {
            System.out.println("e = " + e);
        }
        return ret.toString();
    }

    private static void writeInput(HttpURLConnection connection, String inputJson) {
        try {
            DataOutputStream writeStream = new DataOutputStream(
                    connection.getOutputStream()
            );
            writeStream.writeBytes(inputJson);
            writeStream.close();
        } catch (Exception e) {
            System.out.println("e = " + e);
        }
    }

    public static HttpURLConnection getConnection(String urlString) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application-json");
        } catch (Exception e) {
            System.out.println("e = " + e);
            return null;
        }
        return connection;
    }
}