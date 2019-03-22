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

    private Label staminaLabel;
    private Label attackLabel;
    private Label healthLabel;
    private Label scoreLabel;
    private static Label staminaLabelNum;
    private static Label attackLabelNum;
    private static Label healthLabelNum;
    private static Label scoreLabelNum;


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

        staminaLabel = new Label("Stamina", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        attackLabel =  new Label("Attack", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        healthLabel = new Label("Health", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel =  new Label("Score", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        staminaLabelNum = new Label(String.format("%03d", staminaBar) + "%", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        attackLabelNum =  new Label(String.format("%03d", attackGauge) + "%", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        healthLabelNum = new Label(String.format("%d", health), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabelNum =  new Label(String.format("%04d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(staminaLabel).expandX();
        table.add(attackLabel).expandX();
        table.add(healthLabel).expandX();
        table.add(scoreLabel).expandX();
        table.row();
        table.add(staminaLabelNum).expandX();
        table.add(attackLabelNum).expandX();
        table.add(healthLabelNum).expandX();
        table.add(scoreLabelNum).expandX();
        stage.addActor(table);
    }

    // TODO static?
    public void addScore(int value) {
        score += value;
        scoreLabelNum.setText(String.format("%04d", score));
    }

    public static void changeStamina(int value) {
        staminaBar = value;
        staminaLabelNum.setText(String.format("%03d", staminaBar) + "%");
    }

    public static void changeAttack(int value) {
        attackGauge = value;
        attackLabelNum.setText(String.format("%03d", attackGauge) + "%");
    }

    public static void increaseHealth() {
        health++;
        healthLabelNum.setText(String.format("%d", health));
    }

    public static void decreaseHealth() {
        health--;
        healthLabelNum.setText(String.format("%d", health));
    }


    public void dispose() {
        stage.dispose();
    }
}
