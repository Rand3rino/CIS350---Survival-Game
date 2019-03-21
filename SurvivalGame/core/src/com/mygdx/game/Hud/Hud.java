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

    private Integer staminaBar;
    private Integer attackGauge;
    private Integer health;
    private Integer score;

    Label staminaLabel;
    Label attackLabel;
    Label healthLabel;
    Label scoreLabel;
    Label staminaLabelNum;
    Label attackLabelNum;
    Label healthLabelNum;
    Label scoreLabelNum;


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

        table.add(staminaLabel).expandX().padTop(10);
        table.add(attackLabel).expandX().padTop(10);
        table.add(healthLabel).expandX().padTop(10);
        table.add(scoreLabel).expandX().padTop(10);
        table.row();
        table.add(staminaLabelNum).expandX();
        table.add(attackLabelNum).expandX();
        table.add(healthLabelNum).expandX();
        table.add(scoreLabelNum).expandX();
        stage.addActor(table);
    }

}
