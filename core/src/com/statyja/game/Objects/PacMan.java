package com.statyja.game.Objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.statyja.game.Enums.MoveDirection;
import com.statyja.game.Messages.PacManMessage;
import com.statyja.game.Tools.PacManAnimation;

public class PacMan {
    private MoveDirection direction;
    private Vector2 position;
    transient private float d;

    private int connectionId;

    private float stateTimer;

    private boolean isMove;

    private PacManAnimation pacManAnimation;



    public PacMan(MoveDirection direction, float r) {
        this.direction = direction;
        this.d = r;
    }

    public PacMan(MoveDirection direction, Vector2 position, int connectionId, PacManAnimation pacManAnimation) {
        this.direction = direction;
        this.connectionId = connectionId;
        this.position = position;
        this.stateTimer = 0;
        this.pacManAnimation = pacManAnimation;
        this.d = 1;
    }

    public void updateByMessage(PacManMessage pacManMessage) {
        this.direction = pacManMessage.getDirection();
        this.position = pacManMessage.getPacManPosition();
        this.stateTimer = pacManMessage.getStateTime();
        this.isMove = pacManMessage.isMove();
    }

    public PacMan() {
        this.direction = MoveDirection.RIGHT;
        d = 1;
    }

    public void draw(Batch batch) {
        TextureRegion currentFrame;
        switch (direction) {
            case UP:
                if (isMove) {
                    currentFrame = (TextureRegion) (pacManAnimation.getMoveUp().getKeyFrame(stateTimer));
                } else {
                    currentFrame = (TextureRegion) (pacManAnimation.getStayUp().getKeyFrame(stateTimer));
                }
                break;
            case DOWN:
                if (isMove) {
                    currentFrame = (TextureRegion) (pacManAnimation.getMoveDown().getKeyFrame(stateTimer));
                } else {
                    currentFrame = (TextureRegion) (pacManAnimation.getStayDown().getKeyFrame(stateTimer));
                }
                break;
            case LEFT:
                if (isMove)
                    currentFrame = (TextureRegion) (pacManAnimation.getMoveLeft().getKeyFrame(stateTimer));
                else
                    currentFrame = (TextureRegion) (pacManAnimation.getStayLeft().getKeyFrame(stateTimer));
                break;
            default:
                if (isMove)
                    currentFrame = (TextureRegion) (pacManAnimation.getMoveRight().getKeyFrame(stateTimer));
                else
                    currentFrame = (TextureRegion) (pacManAnimation.getStayRight().getKeyFrame(stateTimer));
                break;
        }
        batch.draw(currentFrame, position.x, position.y, d, d);
    }

    @Override
    public String toString() {
        return "PacMan{" +
                "direction=" + direction +
                ", r=" + d +
                ", stateTimer=" + stateTimer +
                ", isMove=" + isMove +
                '}';
    }

    public int getConnectionId() {
        return connectionId;
    }
}
