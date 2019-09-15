package com.game.java.endpoints;


import com.game.java.coders.MessageDecoder;
import com.game.java.coders.MessageEncoder;
import com.game.java.entities.Message;
import com.game.java.entities.User;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@ServerEndpoint(value = "/room/{user}", decoders = {MessageDecoder.class}, encoders = {MessageEncoder.class})
public class gameEndpoint {

    private Session session = null;
    private String username = "anonimus";
    private User user = null;

    private List<User> users = new LinkedList<>();
    private static List<Session> sessionList = new LinkedList<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("user") String username) {
        this.session = session;
        this.username = username;
        this.user = new User(username);
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

        msg.setName(this.username);
        sessionList.forEach(s -> {
            if (s == this.session) return;
            try {
                if (msg.getText().equals("-ready")) {
                    this.user.setReady(true);
                    this.users.add(user);
                }
                if(msg.getText().equals("-unready")){
                    this.user.setReady(false);
                }
                if(1<users.size()){
                    msg.setText("Creating game...");
                    this.users.clear();
                    this.user.setReady(false);

                }
                System.out.println(user.isReady());
                s.getBasicRemote().sendObject(msg);
            } catch (IOException | EncodeException e) {
                e.printStackTrace();
            }
        });

    }

    private void sendClient(String str) {
        try {
            this.session.getBasicRemote().sendText(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
