package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class GameEngine {
    // region Getters
    public Array<Rectangle> getRainDrops() {
        return rainDrops;
    }

    public int getScore() {
        return score;
    }
    // endregion

    private final Vector3 touchPos = new Vector3();
    private final Array<Rectangle> rainDrops = new Array<>();
    public static final int moveOffSet = 500;
    public static final int objSize = 64;
    private final int widthEndOffSet = 125;
    private long lastDropTime = 1000;
    private int score = 0;

    public void lifeCycleKeys(OrthographicCamera camera, Rectangle bucket, Sound dropSound) throws RuntimeException {
        // when user touch
        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            bucket.x = (int) (touchPos.x - 64 / 2);
        }

        // when user press arrows
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            bucket.x -= moveOffSet * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            bucket.x += moveOffSet * Gdx.graphics.getDeltaTime();

        if (bucket.x < 0) bucket.x = 0;
        if (bucket.x > Gdx.graphics.getWidth() - widthEndOffSet)
            bucket.x = Gdx.graphics.getWidth() - widthEndOffSet;

        // rain drops spawn timer
        if (TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnRaindrop();
        rainDropLifeCycle(bucket, dropSound);

        if (score <= -1) throw new RuntimeException();
    }

    private void rainDropLifeCycle(Rectangle bucket, Sound dropSound) {
        Iterator<Rectangle> iterator = rainDrops.iterator();
        while (iterator.hasNext()) {
            int speed = MathUtils.random(300, 800);
            Rectangle rainDrop = iterator.next();
            rainDrop.y -= speed * Gdx.graphics.getDeltaTime();
            if (rainDrop.y < 0) {
                score--;
                iterator.remove();
                if (score == 0) score = -1;
            }
            if (rainDrop.overlaps(bucket)) {
                score++;
                dropSound.play();
                iterator.remove();
            }
        }
    }

    public void spawnRaindrop() {
        Rectangle rainDrop = new Rectangle();
        rainDrop.x = MathUtils.random(0, Gdx.graphics.getWidth() - widthEndOffSet);
        rainDrop.y = Gdx.graphics.getHeight();
        rainDrop.width = objSize;
        rainDrop.height = objSize;
        rainDrops.add(rainDrop);
        lastDropTime = TimeUtils.nanoTime();
    }

    public Array<Integer> destructorNumber(int score) {
        Array<Integer> numbers = new Array<>();
        String str = Integer.toString(score);
        char[] charArray = str.toCharArray();
        for (char character : charArray) {
            int number = Character.getNumericValue(character);
            numbers.add(number);
        }

        return numbers;
    }
}
