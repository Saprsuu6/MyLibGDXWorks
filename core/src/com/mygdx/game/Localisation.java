package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.Locale;

public class Localisation {
    private static Localisation instance;
    public static final String FONT_NAME = "imperial_web.ttf";
    public static final String RUSSIAN_CHARACTERS = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдеёжзийклмнопрст уфхцчшщъыьэюя1234567890.,:;_!? \" '+-*/()[]={}";
    private BitmapFont ruFont;
    private BitmapFont enFont;

    private Localisation() {
    }

    public static Localisation getInstance() {
        if (instance == null) instance = new Localisation();
        return instance;
    }

    public void initialise() {
        // If fonts are already generated, dispose it!
        if (ruFont != null) ruFont.dispose();
        if (enFont != null) enFont.dispose();

        ruFont = generateFont(RUSSIAN_CHARACTERS);
        enFont = generateFont(FreeTypeFontGenerator.DEFAULT_CHARS);
    }

    public BitmapFont getFont(Locale locale) {
        if ("ru".equals(locale.getLanguage())) return ruFont;
        if ("en".equals(locale.getLanguage())) return enFont;
        else throw new IllegalArgumentException("not expected parameters");
    }

    private BitmapFont generateFont(String characters) {

        // Configure font parameters
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.characters = characters;
        parameter.size = 24;

        // Generate font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(Localisation.FONT_NAME));
        BitmapFont font = generator.generateFont(parameter);

        // Dispose resources
        generator.dispose();

        return font;
    }
}
