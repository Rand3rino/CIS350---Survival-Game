package com.mygdx.game.Hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.SurvivalGame;

public class Hud {
    public Stage stage;
    private Viewport viewport;

    private static int staminaBar;
    private static int attackGauge;
    private static int health;
    private static int score;

    private static Label staminaLabel;
    private static Label attackLabel;
    private static Label healthLabel;
    private static Label scoreLabel;


    public Hud (SpriteBatch batch) {
        staminaBar = 100;
        attackGauge = 100;
        health = 3;
        score = 0;

        viewport = new StretchViewport(SurvivalGame.WIDTH, SurvivalGame.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, batch);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        staminaLabel = new Label(String.format("%03d", staminaBar) + "% Stamina", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        attackLabel =  new Label(String.format("%03d", attackGauge) + "% Attack", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        healthLabel = new Label(String.format("%d", health) + " Health", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel =  new Label(String.format("%04d", score) + " Score", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(staminaLabel).expandX();
        table.add(attackLabel).expandX();
        table.add(healthLabel).expandX();
        table.add(scoreLabel).expandX();
        stage.addActor(table);
    }

    // TODO static?
    public void addScore(int value) {
        score += value;
        scoreLabel.setText(String.format("%04d", score) + " Score");
    }

    public static void changeStamina(int value) {
        staminaBar = value;
        staminaLabel.setText(String.format("%03d", staminaBar) + "% Stamina");
    }

    public static void changeAttack(int value) {
        attackGauge = value;
        attackLabel.setText(String.format("%03d", attackGauge) + "% Attack");
    }

    public static void increaseHealth() {
        health++;
        healthLabel.setText(String.format("%d", health) + " Health");
    }

    public static void decreaseHealth() {
        health--;
        healthLabel.setText(String.format("%d", health) + " Health");
    }


    public void dispose() {
        stage.dispose();
    }
}
