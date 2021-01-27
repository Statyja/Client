package com.statyja.game.Tools;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.statyja.game.MainGrind;

public class PacManAnimation {
    public Animation getStayRight() {
        return stayRight;
    }

    public Animation getStayLeft() {
        return stayLeft;
    }

    public Animation getStayUp() {
        return stayUp;
    }

    public Animation getStayDown() {
        return stayDown;
    }

    public Animation getMoveRight() {
        return moveRight;
    }

    public Animation getMoveLeft() {
        return moveLeft;
    }

    public Animation getMoveUp() {
        return moveUp;
    }

    public Animation getMoveDown() {
        return moveDown;
    }

    private Animation stayRight;
    private Animation stayLeft;
    private Animation stayUp;
    private Animation stayDown;

    private Animation moveRight;
    private Animation moveLeft;
    private Animation moveUp;
    private Animation moveDown;

    public void createAnimation() {
        TextureAtlas actorAtlas = MainGrind.manager.get("images/actors.pack", TextureAtlas.class);


        Array<TextureRegion> keyFrames = new Array<TextureRegion>();

        float duration=0.2f;

        keyFrames.add(new TextureRegion(actorAtlas.findRegion("Pacman"), 16 * 1, 0, 16, 16));
        stayRight = new Animation(duration, keyFrames);

        keyFrames.clear();

        keyFrames.add(new TextureRegion(actorAtlas.findRegion("Pacman"), 16 * 3, 0, 16, 16));
        stayLeft = new Animation(duration, keyFrames);

        keyFrames.clear();

        keyFrames.add(new TextureRegion(actorAtlas.findRegion("Pacman"), 16 * 5, 0, 16, 16));
        stayUp = new Animation(duration, keyFrames);

        keyFrames.clear();

        keyFrames.add(new TextureRegion(actorAtlas.findRegion("Pacman"), 16 * 7, 0, 16, 16));
        stayDown = new Animation(duration, keyFrames);

        keyFrames.clear();

        // move
        for (int i = 1; i < 3; i++) {
            keyFrames.add(new TextureRegion(actorAtlas.findRegion("Pacman"), i * 16, 0, 16, 16));
        }
        moveRight = new Animation(duration, keyFrames, Animation.PlayMode.LOOP);

        keyFrames.clear();

        for (int i = 3; i < 5; i++) {
            keyFrames.add(new TextureRegion(actorAtlas.findRegion("Pacman"), i * 16, 0, 16, 16));
        }
        moveLeft = new Animation(duration, keyFrames, Animation.PlayMode.LOOP);

        keyFrames.clear();

        for (int i = 5; i < 7; i++) {
            keyFrames.add(new TextureRegion(actorAtlas.findRegion("Pacman"), i * 16, 0, 16, 16));
        }
        moveUp = new Animation(duration, keyFrames, Animation.PlayMode.LOOP);

        keyFrames.clear();

        for (int i = 7; i < 9; i++) {
            keyFrames.add(new TextureRegion(actorAtlas.findRegion("Pacman"), i * 16, 0, 16, 16));
        }
        moveDown = new Animation(duration, keyFrames, Animation.PlayMode.LOOP);

        keyFrames.clear();

    }
}
