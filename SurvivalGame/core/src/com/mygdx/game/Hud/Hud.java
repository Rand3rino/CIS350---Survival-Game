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
    private static int enemyCount;

    private static Label staminaLabel;
    private static Label attackLabel;
    private static Label healthLabel;
    private static Label enemyCountLabel;


    public Hud (SpriteBatch batch) {
        staminaBar = 100;
        attackGauge = 100;
        health = 10;
        enemyCount = 10;

        viewport = new StretchViewport(SurvivalGame.WIDTH, SurvivalGame.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, batch);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        staminaLabel = new Label(String.format("%03d", staminaBar) + "% Stamina", new Label.LabelStyle(new BitmapFont(), Color.GREEN));
        attackLabel =  new Label(String.format("%03d", attackGauge) + "% Attack", new Label.LabelStyle(new BitmapFont(), Color.GREEN));
        healthLabel = new Label(String.format("%d", health) + " Health", new Label.LabelStyle(new BitmapFont(), Color.GREEN));
        enemyCountLabel =  new Label(String.format("%03d", enemyCount) + " Enemies", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(staminaLabel).expandX();
        table.add(attackLabel).expandX();
        table.add(healthLabel).expandX();
        table.add(enemyCountLabel).expandX();
        stage.addActor(table);
    }

    // TODO static?
    public static void decrementEnemy() {
        enemyCount--;
        enemyCountLabel.setText(String.format("%03d", enemyCount) + " Enemies");
    }

    public static void changeStamina(int value) {
        staminaBar = value;
        if (staminaBar == 100) {
            staminaLabel.setStyle(new Label.LabelStyle(new BitmapFont(), Color.GREEN));
        }
        else if (staminaBar == 0){
            staminaLabel.setStyle(new Label.LabelStyle(new BitmapFont(), Color.RED));
        }
        else {
            staminaLabel.setStyle(new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        }
        staminaLabel.setText(String.format("%03d", staminaBar) + "% Stamina");
    }

    public static void changeAttack(int value) {
        attackGauge = value;
        if (attackGauge == 100) {
            attackLabel.setStyle(new Label.LabelStyle(new BitmapFont(), Color.GREEN));
        }
        else if (attackGauge == 0){
            attackLabel.setStyle(new Label.LabelStyle(new BitmapFont(), Color.RED));
        }
        else {
            attackLabel.setStyle(new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        }
        attackLabel.setText(String.format("%03d", attackGauge) + "% Attack");
    }

    public int getEnemyCount() {
        return enemyCount;
    }

    public static void changeHealth(int value) {
        health = value;
        if (health == 3) {
            healthLabel.setStyle(new Label.LabelStyle(new BitmapFont(), Color.GREEN));
        }
        else if (health < 2){
            healthLabel.setStyle(new Label.LabelStyle(new BitmapFont(), Color.RED));
        }
        else {
            healthLabel.setStyle(new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        }
        healthLabel.setText(String.format("%d", health) + " Health");
    }


    public void dispose() {
        stage.dispose();
    }
}
