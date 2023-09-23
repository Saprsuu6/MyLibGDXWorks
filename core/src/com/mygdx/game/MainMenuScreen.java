package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;
import java.util.Locale;

public class MainMenuScreen extends GameScreen implements Screen {
    private final Drop game;
    private long lastDropTime = 1000;
    private final Array<Rectangle> rainDrops = new Array<>();

    public MainMenuScreen(Drop game) {
        super(game);
        this.game = game;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.3F, 0.6F, 0.5F, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.getCamera().update();

        // rain drops spawn timer
        if (TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnRaindrop();
        rainDropLifeCycle();

        game.getBatch().setProjectionMatrix(game.getCamera().combined);
        game.getBatch().begin();
        super.renderObjects();
        if ("ru".equals(Locale.getDefault().getLanguage())) {
            Localisation.getInstance().getFont(game.getRuLocale()).draw(game.getBatch(), "Поймай все капли!", 80, 150);
            Localisation.getInstance().getFont(game.getRuLocale()).draw(game.getBatch(), "Коснись экрана, чтобы начать!", 80, 120);
        }
        if ("en".equals(Locale.getDefault().getLanguage())) {
            Localisation.getInstance().getFont(game.getEnLocale()).draw(game.getBatch(), "Catch all rain drops!", 80, 150);
            Localisation.getInstance().getFont(game.getEnLocale()).draw(game.getBatch(), "Touche, to start!", 80, 120);
        }
        game.getBatch().end();

        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(game));
            dispose();
        }
    }

    @Override
    protected void renderObjects() {
        super.renderObjects();

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

    private void rainDropLifeCycle() {
        Iterator<Rectangle> iterator = rainDrops.iterator();
        while (iterator.hasNext()) {
            int speed = MathUtils.random(300, 800);
            Rectangle rainDrop = iterator.next();
            rainDrop.y -= speed * Gdx.graphics.getDeltaTime();
            if (rainDrop.y < 0) iterator.remove();
        }
    }

    public void spawnRaindrop() {
        Rectangle rainDrop = new Rectangle();
        int widthEndOffSet = 125;
        rainDrop.x = MathUtils.random(0, Gdx.graphics.getWidth() - widthEndOffSet);
        rainDrop.y = Gdx.graphics.getHeight();
        rainDrops.add(rainDrop);
        lastDropTime = TimeUtils.nanoTime();
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
}
