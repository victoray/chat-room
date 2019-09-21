package com.odinson.chatroom.chat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.odinson.chatroom.encoder.MessageEncoder;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket Server
 *
 * @see ServerEndpoint WebSocket Client
 * @see Session   WebSocket Session
 */

@Component
@ServerEndpoint(value = "/chat/{username}", encoders = MessageEncoder.class)
public class WebSocketChatServer {

    /**
     * All chat sessions.
     */
    private static Map<String, Session> onlineSessions = new ConcurrentHashMap<>();

    private static void sendMessageToAll(Message message) {
        //TODO: add send message method.
        onlineSessions.keySet().forEach(x -> {
            try {
                onlineSessions.get(x).getBasicRemote().sendObject(message);
            } catch (IOException | EncodeException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Open connection, 1) add session, 2) add user.
     */
    @OnOpen
    public void onOpen(@PathParam("username") String username, Session session) {
        //TODO: add on open connection.
        onlineSessions.put(username, session);

        Message message = new Message();
        message.setName(username);
        message.setType("UPDATE");
        message.setOnline(onlineSessions.size());
        message.setMessage( username + " joined the chat");
        sendMessageToAll(message);
    }

    /**
     * Send message, 1) get username and session, 2) send message to all.
     */
    @OnMessage
    public void onMessage(Session session, String jsonStr) throws IOException {
        //TODO: add send message.
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        Message message = JSON.toJavaObject(jsonObject, Message.class);
        message.setName(jsonObject.get("username").toString());
        message.setType("SPEAK");
        message.setMessage(jsonObject.get("msg").toString());
        message.setOnline(onlineSessions.size());
        sendMessageToAll(message);

    }

    /**
     * Close connection, 1) remove session, 2) update user.
     */
    @OnClose
    public void onClose(Session session, @PathParam("username") String username) {
        //TODO: add close connection.
        onlineSessions.remove(username);

        Message message = new Message();
        message.setName(username);
        message.setType("LEAVE");
        message.setOnline(onlineSessions.size());
        message.setMessage(username + " left the chat!");
        sendMessageToAll(message);

    }

    /**
     * Print exception.
     */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

}