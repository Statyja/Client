package com.statyja.game.Messages;

import com.statyja.game.Enums.MoveDirection;

public class MovePacket {

    public void setDirection(MoveDirection direction) {
        this.direction = direction;
    }

    private MoveDirection direction;

    public void setSubDirection(MoveDirection subDirection) {
        this.subDirection = subDirection;
    }

    private MoveDirection subDirection;

}
