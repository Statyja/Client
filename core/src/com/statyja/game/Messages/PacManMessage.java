package com.statyja.game.Messages;

import com.badlogic.gdx.math.Vector2;
import com.statyja.game.Enums.MoveDirection;

public class PacManMessage {

    public Vector2 getPacManPosition() {
        return pacManPosition;
    }

    public float getStateTime() {
        return stateTime;
    }

    public MoveDirection getDirection() {
        return direction;
    }

    public int getConnectionId() {
        return connectionId;
    }

    public boolean isMove() {
        return isMove;
    }

    private Vector2 pacManPosition;

    private float stateTime;

    private MoveDirection direction;

    private int connectionId;

    private boolean isMove;

    @Override
    public String toString() {
        return "PacManMessage{" +
                "pacManPosition=" + pacManPosition +
                ", stateTime=" + stateTime +
                ", direction=" + direction +
                ", connectionId=" + connectionId +
                ", isMove=" + isMove +
                '}';
    }
}
