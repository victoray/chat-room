package com.odinson.chatroom.encoder;

import com.alibaba.fastjson.JSON;
import com.odinson.chatroom.chat.Message;

import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class MessageEncoder implements Encoder.Text<Message> {
    
    @Override
    public String encode(Message message) {
        return JSON.toJSONString(message);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
        
    }

    @Override
    public void destroy() {

    }
}
