package test;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

class Test {
    public static void main(String[] args) throws Exception {
        String inputJson = "{\"command\": \"echo\",\"data\": {\"room\": \"room\",\"msg\": \"msg\",\"sender\": \"sender\",\"isGroupChat\": \"false\",\"replier\": \"replier\",\"packageName\": \"packageName\"}}";
        System.out.println("inputJson = " + inputJson);

        Socket socket = new Socket("192.168.45.201", 50000);
        sendSocketMessage(socket, inputJson);

        socket.close();
    }

    private static void sendSocketMessage(Socket socket, String inputJson) throws IOException {
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        out.write(inputJson.getBytes(StandardCharsets.UTF_8));
        out.flush();
    }
}