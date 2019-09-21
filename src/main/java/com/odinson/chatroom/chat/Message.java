package com.odinson.chatroom.chat;

/**
 * WebSocket message model
 */
public class Message {
    // TODO: add message model.
    private String name;
    private String type;
    private String message;
    private int online;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getOnline() {
        return online;
    }

    public void setOnline(int online) {
        this.online = online;
    }

    @Override
    public String toString() {
        return "Message{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", message='" + message + '\'' +
                ", online=" + online +
                '}';
    }
}
