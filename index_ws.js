"use strict"

/* setting */
const server_connection_config = {
    address: '192.168.45.201', // Spring 서버 IP
    port: 50000 // Spring 서버 Port
};

const socket = new java.net.Socket(server_connection_config.address, server_connection_config.port);
const writeStream = new java.io.DataOutputStream(socket.getOutputStream());

const sendSocketMessage = (jsonString) => {
    jsonString += '\n';
    try {
        writeStream.write((new java.lang.String(jsonString)).getBytes(java.nio.charset.StandardCharsets.UTF_8));
        writeStream.flush();
        Log.d("[Client] " + jsonString);
    } catch (e) {
        Log.e(e);
    }
}

const handleMessage = (msg) => {
    const data = JSON.parse(decodeURIComponent(msg));
    Api.replyRoom(data.room, data.msg);
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

    sendSocketMessage(serverMessage);
}

const thread = new java.lang.Thread({
    run : () => {
        const inputStream = new java.io.BufferedReader(new java.io.InputStreamReader(socket.getInputStream()));
        let line;
        while(!socket.isClosed() && (line = inputStream.readLine()) != null) {
            handleMessage(line);
            Log.d("[Server] " + line);
        }
    }
})

let onStartCompile = () => {
    thread.interrupt();
};
thread.start();