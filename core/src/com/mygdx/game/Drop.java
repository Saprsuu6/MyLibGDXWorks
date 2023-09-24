package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Locale;

public class Drop extends Game {
    // region Getters
    public SpriteBatch getBatch() {
        return batch;
    }

    public BitmapFont getText() {
        return text;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public Locale getRuLocale() {
        return ruLocale;
    }

    public Locale getEnLocale() {
        return enLocale;
    }

    // endregion
    private SpriteBatch batch;
    private BitmapFont text;
    private OrthographicCamera camera;
    private Locale ruLocale;
    private Locale enLocale;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Initialize FontFactory
        Localisation.getInstance().initialise();

        // Initialize locales
        ruLocale = new Locale("ru");
        enLocale = new Locale("en");

        batch = new SpriteBatch();
        text = new BitmapFont();
        this.setScreen(new MainMenuScreen(this));
        this.setScreen(new MainMenuScreen(this));
        this.setScreen(new MainMenuScreen(this));
        this.setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
    }
}
