"use strict"

/* setting */
const server_connection_config = {
    address: 'http://144.24.64.73', // Spring 서버 IP
    port: 8080 // Spring 서버 Port
};

let establishConnection = function () {
    try {
        let connection = (new java.net.URL(server_connection_config.address + ":" + server_connection_config.port)).openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        return connection;
    } catch (e) {
        Log.e(e);
    }

    return null;
}

let writeConnectionData = function (connection, jsonString) {
    try {
        let writeStream = new java.io.DataOutputStream(connection.getOutputStream());
        writeStream.writeChars(jsonString);
        writeStream.close();
    } catch (e) {
        Log.e(e);
    }
}

let httpPost = function (connection, serverMessage) {
    writeConnectionData(connection, serverMessage);

    let ret = new java.lang.StringBuilder();
    let rd = new java.io.BufferedReader(new java.io.InputStreamReader(connection.getInputStream()));
    let line = null;
    while ((line = rd.readLine()) != null) {
        ret.append(line);
        ret.append('\n');
    }
    ret.deleteCharAt(ret.length() - 1);
    rd.close();

    return ret.toString();
}

let response = function (room, msg, sender, isGroupChat, replier, imageDB, packageName) {
    const serverMessage = JSON.stringify({
        'command': 'message',
        'data': {
            'room': room,
            'msg': msg,
            'sender': sender,
            'isGroupChat': isGroupChat,
            'replier': replier,
            // 'imageDB': imageDB.getProfileBase64(),
            'packageName': packageName,
        }
    });
    Log.d("[CLIENT]" + serverMessage);

    const connection = establishConnection();
    if (connection == null) {
        Log.e("server connection failed");
        return;
    }
    const responseString = httpPost(connection, serverMessage);
    Log.d("[SERVER]" + responseString);
    Api.replyRoom(room, responseString);
}