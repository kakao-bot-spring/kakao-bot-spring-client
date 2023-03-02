"use strict"

/* setting */
const server_connection_config = {
    address: 'http://144.24.64.73', // Spring 서버 IP
    port: 8080 // Spring 서버 Port
};

let writeConnectionData = function(connection, jsonString) {
    try {
        let writeStream = new java.io.DataOutputStream(connection.getOutputStream());
        writeStream.writeBytes(jsonString);
        writeStream.close();
    } catch (e) {
        Log.e(e);
    }
}

let httpPost = function(connection) {
    let ret = new java.lang.StringBuilder();
    let rd = new java.io.BufferedReader(new java.io.InputStreamReader(connection.getInputStream()));
    let line = null;
    while ((line = rd.readLine()) != null) {
        ret.append(line);
        ret.append('\n');
    }
    rd.close();

    return ret.toString();
}

let response = function(room, msg, sender, isGroupChat, replier, imageDB, packageName) {
    try {
        let connection = (new java.net.URL(server_connection_config.address + ":" + server_connection_config.port)).openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");
        writeConnectionData(connection, msg);
        let res = httpPost(connection);
        Log.d(res);
    } catch (e) {
        Log.e(e);
    }
}