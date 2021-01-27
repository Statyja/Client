package com.statyja.game.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Cell {
    private Vector2 position;

    private boolean isWall;

    public int getSpr() {
        return spr;
    }


    private int spr;

    public void setSprite(TextureRegion sprite) {
        this.sprite = sprite;
    }

    transient private TextureRegion sprite;

    public Cell(Vector2 position, boolean isWall) {
        this.position = position;
    }

    public Cell() {
        this.position = new Vector2(0, 0);
    }

    public boolean isWall() {
        return isWall;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "position=" + position +
                ", isWall=" + isWall +
                '}';
    }

    public void draw(Batch batch) {
        if (isWall) batch.draw(sprite, position.x, position.y, 1, 1);
    }
}
