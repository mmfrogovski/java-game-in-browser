package com.game.java.endpoints;

import com.game.java.coders.MessageDecoder;
import com.game.java.coders.MessageEncoder;
import com.game.java.entities.Message;
import com.game.java.gameWithUserLogic.GameWithUserLogic;
import com.game.java.model.jdbc.User;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


@ServerEndpoint(value = "/multiplayerGame/{otherUser}", decoders = {MessageDecoder.class}, encoders = {MessageEncoder.class})
public class GameEndpoint {
    private Session session = null;
    private User user;
    private String otherName;
    private static List<String> numbersList = new LinkedList<>();
    private static Map<String, String> hashMap = new HashMap<String, String>();
    private static List<Session> sessionList = new LinkedList<>();
    private int msgCount = 0;

    @OnOpen
    public void onOpen(Session session, @PathParam("otherUser") String otherName) {
        this.user = new User();
        this.otherName = otherName;
        this.user.setGameWithUserLogic(new GameWithUserLogic());
        sessionList.add(session);
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println(session + "removed");
        sessionList.remove(session);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        throwable.printStackTrace();
    }

    @OnMessage
    public void onMessage(Session session, Message msg) {
        this.msgCount++;
        if (hashMap.size() < 2) {
            hashMap.put(msg.getName(), msg.getText());
            System.out.println("Vlad " + hashMap.get("Vlad"));
            System.out.println("Adolf " + hashMap.get("Adolf"));
            sessionList.forEach(s->{
                if(s==this.session) return;
                try{
                    msg.setName(msg.getName());
                    msg.setText("checked");
                    s.getBasicRemote().sendObject(msg);
                }catch (IOException | EncodeException e){
                    e.printStackTrace();
                }
            });
        }
        if (this.msgCount >= 2) {
            String answersForUser = this.user.getGameWithUserLogic().gameLogic(msg.getText(), hashMap.get(this.otherName));
            msg.setUserNumb(msg.getText());
            msg.setText(answersForUser);
            sessionList.forEach(s -> {
                if (s == this.session) return;
                try {
                    s.getBasicRemote().sendObject(msg);
                    if (msg.getText().equals("Bulls: 4 / Cows: 0")) {
                        hashMap.clear();
                        this.msgCount = 0;
                    }
                } catch (IOException | EncodeException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
