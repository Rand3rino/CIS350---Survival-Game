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

/**
 * The HUD that overlays on top of the game and displays
 * information such as current health, attack gauge percentage,
 * stamina percentage and enemies remaining.
 * @author Randy Nguyen
 * @version 1.0
 */
public class Hud {
    /**
     * The overlay for the HUD
     */
    public Stage stage;

    /**
     * The viewing area for the player
     */
    private Viewport viewport;

    /**
     * Integer variable for stamina
     */
    private static int staminaBar;

    /**
     * Integer variable for attack gauge
     */
    private static int attackGauge;

    /**
     * Integer variable for health
     */
    private static int health;

    /**
     * Integer variable for the count of enemies left
     */
    private static int enemyCount;

    /**
     * Label for the stamina HUD
     */
    private static Label staminaLabel;

    /**
     * Label for the attack gauge for HUD
     */
    private static Label attackLabel;

    /**
     * Label for the health for HUD
     */
    private static Label healthLabel;

    /**
     * Label of enemies left for HUD
     */
    private static Label enemyCountLabel;

    /**
     * Constructor for the HUD class
     * @param batch The batch of sprites/images to be added on the HUD
     */
    public Hud(SpriteBatch batch) {
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
        attackLabel = new Label(String.format("%03d", attackGauge) + "% Attack", new Label.LabelStyle(new BitmapFont(), Color.GREEN));
        healthLabel = new Label(String.format("%d", health) + " Health", new Label.LabelStyle(new BitmapFont(), Color.GREEN));
        enemyCountLabel = new Label(String.format("%03d", enemyCount) + " Enemies", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        staminaLabel.setFontScale(1.5f);
        attackLabel.setFontScale(1.5f);
        healthLabel.setFontScale(1.5f);
        enemyCountLabel.setFontScale(1.5f);

        table.add(staminaLabel).expandX();
        table.add(attackLabel).expandX();
        table.add(healthLabel).expandX();
        table.add(enemyCountLabel).expandX();
        stage.addActor(table);
    }

    /**
     * Method to decrement the HUD's current enemy count
     */
    public static void decrementEnemy() {
        // Decrements current count
        enemyCount--;

        // Checks count for boss battle
        if (enemyCount == 1) {
            enemyCountLabel.setStyle(new Label.LabelStyle(new BitmapFont(), Color.CYAN));
            enemyCountLabel.setText(String.format("BOSS BATTLE"));
        } else
            enemyCountLabel.setText(String.format("%03d", enemyCount) + " Enemies");
    }

    /**
     * Method to change the current value of stamina for the player
     * @param value the players current stamina value
     */
    public static void changeStamina(int value) {
        // Sets the HUD stamina value to the players current stamina value
        staminaBar = value;


        if (staminaBar == 100)

            // Handles when stamaina bar is at 100 and sets the color to green
            staminaLabel.setStyle(new Label.LabelStyle(new BitmapFont(), Color.GREEN));

        else if (staminaBar == 0)

            // Handles when stamina is 0 and changes color to red
            staminaLabel.setStyle(new Label.LabelStyle(new BitmapFont(), Color.RED));

        else

            // Handles when stamin is greater than 0 but less than 100 and sets to the color white
            staminaLabel.setStyle(new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        staminaLabel.setText(String.format("%03d", staminaBar) + "% Stamina");
    }

    /**
     * Changes the HUD's attack gauge according to the players attack gauge
     * @param value the players current attack gauge
     */
    public static void changeAttack(int value) {

        // Sets the HUDs attack gauge to the players attack gauge
        attackGauge = value;

        // Checks the attack gauge and changes the color accordingly
        if (attackGauge == 100)
            attackLabel.setStyle(new Label.LabelStyle(new BitmapFont(), Color.GREEN));

        else if (attackGauge == 0)
            attackLabel.setStyle(new Label.LabelStyle(new BitmapFont(), Color.RED));

        else
            attackLabel.setStyle(new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        attackLabel.setText(String.format("%03d", attackGauge) + "% Attack");
    }

    /**
     * getter for enemies left
     * @return current enemy count
     */
    public int getEnemyCount() {
        return enemyCount;
    }

    /**
     * Changes the health of the HUD
     * @param value current playeer health
     */
    public static void changeHealth(int value) {

        // Sets HUD's health variable to players current health value
        health = value;

        // Checks the health and changes the health accordingly
        if (health == 3)
            healthLabel.setStyle(new Label.LabelStyle(new BitmapFont(), Color.GREEN));
        else if (health < 2)
            healthLabel.setStyle(new Label.LabelStyle(new BitmapFont(), Color.RED));
        else
            healthLabel.setStyle(new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        healthLabel.setText(String.format("%d", health) + " Health");
    }

    /**
     * Engine method to dispose of the HUD
     */
    public void dispose() {
        stage.dispose();
    }
}
