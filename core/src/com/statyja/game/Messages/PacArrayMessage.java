package com.statyja.game.Messages;

import java.util.ArrayList;

public class PacArrayMessage {
    public ArrayList<PacManMessage> getPacManMessages() {
        return pacManMessages;
    }

    public PacArrayMessage(){
        pacManMessages= new ArrayList<>();
    }

    private ArrayList<PacManMessage> pacManMessages;
}
