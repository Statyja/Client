package com.statyja.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.statyja.game.Enums.MoveDirection;
import com.statyja.game.Messages.CellMessage;
import com.statyja.game.Messages.MovePacket;
import com.statyja.game.Messages.PacArrayMessage;
import com.statyja.game.Messages.PacManMessage;
import com.statyja.game.Messages.RemovePacket;
import com.statyja.game.Objects.Cell;
import com.statyja.game.Objects.PacMan;
import com.statyja.game.Screens.PlayScreen;

import java.util.ArrayList;


public class MainGrind extends Game {
    private static final int BLOCK_SIZE = 16;
    public static final int V_WIDTH = 23 * BLOCK_SIZE;
    public static final int V_HEIGHT = 22 * BLOCK_SIZE;
    public static final float PPM = 16;


    public SpriteBatch batch;

    public static AssetManager manager;


    @Override
    public void create() {
        batch = new SpriteBatch();
        manager = new AssetManager();
        manager.load("images/actors.pack", TextureAtlas.class);
        manager.load("walls.pack", TextureAtlas.class);
        manager.finishLoading();

        Client client = new Client(18000, 12000);
        Kryo kryo = client.getKryo();
        register(kryo);

        client.start();
        try {
            setScreen(new PlayScreen(this, client));
            client.connect(6000, "localhost", 54554, 54776);

        } catch (Exception e) {
            System.err.println("Failed to connect to server!");
        }


    }


    private void register(Kryo kryo) {
        kryo.register(Vector2.class);
        kryo.register(String.class);
        kryo.register(boolean.class);
        kryo.register(int.class);

        kryo.register(ArrayList.class);
        kryo.register(float.class);
        kryo.register(PacMan.class);

        kryo.register(Cell.class);
        kryo.register(Cell[].class);
        kryo.register(Cell[][].class);
        kryo.register(CellMessage.class);

        kryo.register(MovePacket.class);
        kryo.register(Array.class);
        kryo.register(PacManMessage.class);
        kryo.register(PacArrayMessage.class);
        kryo.register(MoveDirection.class);
        kryo.register(RemovePacket.class);
    }


    @Override
    public void dispose() {
        super.dispose();
        manager.dispose();
        batch.dispose();
    }

    @Override
    public void render() {
        super.render();
    }

}
