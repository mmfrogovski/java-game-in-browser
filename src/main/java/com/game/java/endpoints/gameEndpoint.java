package com.game.java.endpoints;


import com.game.java.coders.MessageDecoder;
import com.game.java.coders.MessageEncoder;
import com.game.java.entities.Message;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@ServerEndpoint(value = "/room/{user}/{roomId}", decoders = {MessageDecoder.class}, encoders = {MessageEncoder.class})
public class gameEndpoint {

    private Session session = null;
    private String username = "anonimus";
    private String roomId = "0";

    private static List<Session> sessionList = new LinkedList<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("user") String username, @PathParam("roomId") String roomId) {
        this.session = session;
        this.username = username;
        this.roomId = roomId;
        sessionList.add(session);
    }

    @OnClose
    public void onClose(Session session) {
        sessionList.remove(session);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        throwable.printStackTrace();
    }

    @OnMessage
    public void onMessage(Session session, Message msg) {
        msg.setRoomId(this.roomId);
        msg.setName(this.username);
        sessionList.forEach(s -> {
            if (s == this.session) return;
            try {
                s.getBasicRemote().sendObject(msg);
            } catch (IOException | EncodeException e) {
                e.printStackTrace();
            }
        });

    }

}
