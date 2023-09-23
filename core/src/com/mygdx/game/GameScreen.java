package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;
import java.util.Map;

public class GameScreen implements Screen {
    protected Texture dropImage;
    protected Texture bakedImage;
    private Sound dropSound;
    private Music rainMusic;
    protected Rectangle bucket;
    protected final GameEngine gameEngine = new GameEngine();
    protected final Map<Integer, Texture> links = new HashMap<>();
    protected final Drop game;

    public GameScreen(Drop game) {
        this.game = game;

        initialiseResources();
        rainMusic.setLooping(true);

        setInitialBucketPosition();
        gameEngine.spawnRaindrop();
    }

    private void initialiseResources() {
        dropImage = new Texture("rain_drops.png");
        bakedImage = new Texture("bucket.png");
        dropSound = Gdx.audio.newSound(Gdx.files.internal("rain_drop_sound.mp3"));
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain_sound.mp3"));

        links.put(0, new Texture("0.png"));
        links.put(1, new Texture("1.png"));
        links.put(2, new Texture("2.png"));
        links.put(3, new Texture("3.png"));
        links.put(4, new Texture("4.png"));
        links.put(5, new Texture("5.png"));
        links.put(6, new Texture("6.png"));
        links.put(7, new Texture("7.png"));
        links.put(8, new Texture("8.png"));
        links.put(9, new Texture("9.png"));
    }

    private void setInitialBucketPosition() {
        bucket = new Rectangle();
        bucket.x = (float) Gdx.graphics.getWidth() / 2;
        bucket.y = 20;
        bucket.width = GameEngine.objSize;
        bucket.height = GameEngine.objSize;
    }

    protected void renderObjects() {
        // rain drops
        for (Rectangle raindrop : gameEngine.getRainDrops()) {
            game.getBatch().draw(dropImage, raindrop.x, raindrop.y);
        }

        // render bucket
        game.getBatch().draw(bakedImage, bucket.x, bucket.y);

        // render score
        Array<Integer> scores = gameEngine.destructorNumber(gameEngine.getScore());
        int distance = 10;
        for (int number : scores) {
            Texture texture = links.get(number);
            game.getBatch().draw(texture, distance, Gdx.graphics.getHeight() - 50);
            distance += 50;
        }
    }

    @Override
    public void show() {
        rainMusic.play();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.3F, 0.6F, 0.5F, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.getCamera().update();

        game.getBatch().setProjectionMatrix(game.getCamera().combined);
        game.getBatch().begin();
        renderObjects();
        game.getBatch().end();

        try {
            gameEngine.lifeCycleKeys(game.getCamera(), bucket, dropSound);
        } catch (RuntimeException e) {
            game.setScreen(new MainMenuScreen(game));
            dispose();
        }
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
        dropImage.dispose();
        bakedImage.dispose();
        dropSound.dispose();
        rainMusic.dispose();
    }
}
