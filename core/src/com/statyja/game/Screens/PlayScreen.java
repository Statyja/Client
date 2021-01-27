package com.statyja.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.statyja.game.Enums.MoveDirection;
import com.statyja.game.MainGrind;
import com.statyja.game.Messages.CellMessage;
import com.statyja.game.Messages.MovePacket;
import com.statyja.game.Messages.PacArrayMessage;
import com.statyja.game.Messages.PacManMessage;
import com.statyja.game.Messages.RemovePacket;
import com.statyja.game.Objects.Cell;
import com.statyja.game.Objects.PacMan;
import com.statyja.game.Tools.PacManAnimation;

import java.util.ArrayList;

public class PlayScreen implements Screen {
    private MainGrind game;
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Stage stage;


    private Client client;

    private Array<TextureRegion> keyFrames;

    private ArrayList<Cell> cells;

    private Vector2 worldSize;

    private boolean flag;

    private ArrayList<PacMan> pacMans;

    public PlayScreen(final MainGrind mainGrind, Client client) {
        this.game = mainGrind;
        this.client = client;

        flag = false;

        cells = new ArrayList<>();

        worldSize = new Vector2();

        final PacManAnimation pacManAnimation = new PacManAnimation();
        pacManAnimation.createAnimation();

        pacMans = new ArrayList<>();

        gamecam = new OrthographicCamera();
        gamePort = new StretchViewport(MainGrind.V_WIDTH / MainGrind.PPM, MainGrind.V_HEIGHT / MainGrind.PPM, gamecam);
        stage = new Stage(gamePort, game.batch);


        client.addListener(new Listener() {
            @Override
            public void received(Connection connection, Object object) {
                if (object instanceof Vector2) {
                    worldSize = (Vector2) object;
                    flag = true;
                }
                if (object instanceof PacManMessage) {
                    PacMan pacMan = findPacManBId(((PacManMessage) object).getConnectionId());
                    assert pacMan != null;
                    pacMan.updateByMessage((PacManMessage) object);
                }
                if (object instanceof CellMessage) {
                    cells = ((CellMessage) object).getCells();
                    for (Cell cell :
                            cells) {
                        if (cell.isWall())
                            cell.setSprite(keyFrames.get(cell.getSpr()));
                    }
                }
                if (object instanceof RemovePacket) {
                    removePacManBId(((RemovePacket) object).getConnectionId());
                }
                if (object instanceof PacArrayMessage) {
                    if (pacMans.size() == 0) {
                        for (PacManMessage message :
                                ((PacArrayMessage) object).getPacManMessages()) {
                            pacMans.add(new PacMan(message.getDirection(), message.getPacManPosition(), message.getConnectionId(), pacManAnimation));
                        }
                    } else for (PacManMessage message :
                            ((PacArrayMessage) object).getPacManMessages())
                        if (!checkContainsPacManBId(message.getConnectionId()))
                            pacMans.add(new PacMan(message.getDirection(), message.getPacManPosition(), message.getConnectionId(), pacManAnimation));

                }
            }
        });

        createWalls();
    }


    private void createWalls() {
        TextureAtlas actorAtlas = MainGrind.manager.get("walls.pack", TextureAtlas.class);
        keyFrames = new Array<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                keyFrames.add(new TextureRegion(actorAtlas.findRegion("Wall"), j * 16, i * 16, 16, 16));
            }
        }
    }

    private void update() {
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            MovePacket movePacket = new MovePacket();
            movePacket.setDirection(MoveDirection.UP);

            if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
                movePacket.setSubDirection(MoveDirection.DOWN);
            else if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
                movePacket.setSubDirection(MoveDirection.LEFT);
            else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
                movePacket.setSubDirection(MoveDirection.RIGHT);
            else movePacket.setSubDirection(MoveDirection.NONE);

            client.sendTCP(movePacket);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            MovePacket movePacket = new MovePacket();
            movePacket.setDirection(MoveDirection.DOWN);

            if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
                movePacket.setSubDirection(MoveDirection.LEFT);
            else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
                movePacket.setSubDirection(MoveDirection.RIGHT);
            else movePacket.setSubDirection(MoveDirection.NONE);

            client.sendTCP(movePacket);
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            MovePacket movePacket = new MovePacket();
            movePacket.setDirection(MoveDirection.LEFT);

            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
                movePacket.setSubDirection(MoveDirection.RIGHT);
            else movePacket.setSubDirection(MoveDirection.NONE);

            client.sendTCP(movePacket);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            MovePacket movePacket = new MovePacket();
            movePacket.setDirection(MoveDirection.RIGHT);
            movePacket.setSubDirection(MoveDirection.NONE);
            client.sendTCP(movePacket);
        }
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        update();

        if (flag) {
            gamePort.setWorldSize(worldSize.x, worldSize.y);
            gamePort.apply(true);
            flag = false;
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        if (pacMans.size() != 0)
            for (PacMan pacMan : pacMans) {
                pacMan.draw(game.batch);
            }
        if (cells.size() != 0)
            for (Cell cell :
                    cells) {
                cell.draw(game.batch);
            }
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }


    private PacMan findPacManBId(int id) {
        for (PacMan pacMan :
                pacMans) {
            if (pacMan.getConnectionId() == id)
                return pacMan;
        }
        return null;
    }

    private boolean checkContainsPacManBId(int id) {
        for (PacMan pacMan :
                pacMans) {
            if (pacMan.getConnectionId() == id)
                return true;
        }
        return false;
    }

    private void removePacManBId(int id) {
        for (PacMan pacMan :
                pacMans) {
            if (pacMan.getConnectionId() == id) {
                pacMans.remove(pacMan);
                break;
            }
        }
    }
}
