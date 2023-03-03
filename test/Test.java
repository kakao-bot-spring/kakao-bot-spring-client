package test;

import java.io.*;
import java.lang.reflect.Array;
import java.net.*;
import java.nio.charset.StandardCharsets;

class Test {
    public static void main(String[] args) throws Exception {
        String inputJson = "{msg: \"hello\"}";
        System.out.println("inputJson = " + inputJson);

        Socket socket = new Socket("192.168.45.201", 50000);
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        out.write(inputJson.getBytes());
        out.close();

//        InetAddress address = InetAddress.getByName("192.168.45.201");
//        DatagramSocket socket = new DatagramSocket(50000, address);
//        byte[] buffer = new byte[65535];
//        var inPacket = new java.net.DatagramPacket(buffer, buffer.length);
//
//        sendSocketMessage(socket, address, inputJson);
    }

    private static byte[] getBytes(String str) {
        return str.getBytes();
    }

    private static void sendSocketMessage(DatagramSocket socket, InetAddress address, String jsonString) throws IOException {
        byte[] bytes = getBytes(jsonString);
        DatagramPacket outPacket = new DatagramPacket(bytes, bytes.length, address, 50000);
        socket.send(outPacket);
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
            writeStream.writeChars(inputJson);
            writeStream.writeBytes(inputJson);
            writeStream.close();
        } catch (Exception e) {
            System.out.println("e = " + e);
        }
    }

    public static HttpURLConnection getConnection(String urlString) throws SocketException, UnknownHostException {
        var socket = new java.net.DatagramSocket();
        InetAddress address = java.net.InetAddress.getByName("144.24.64.73/ws");
        new InetSocketAddress("192.168.45.201/ws", 8080);
        var buffer = java.lang.reflect.Array.newInstance(java.lang.Byte.TYPE, 65535);
        HttpURLConnection connection = null;
        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
        } catch (Exception e) {
            System.out.println("e = " + e);
            return null;
        }
        return connection;
    }
}