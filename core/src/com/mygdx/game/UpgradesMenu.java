package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;

/**
 * Created by Conner on 4/24/2015.
 */
public class UpgradesMenu implements ApplicationListener {

        private Stage stage;
        private BitmapFont font;
        private TextureAtlas buttonsAtlas;
        private Skin buttonSkin;


        @Override public void create() {
            this.stage = new Stage();
            Gdx.input.setInputProcessor(this.stage);
            final Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

    //TODO add buttons
            buttonsAtlas = new TextureAtlas("menuButton.atlas");
            buttonSkin = new Skin();
            buttonSkin.addRegions(buttonsAtlas);

            font = new BitmapFont(Gdx.files.internal("wow.fnt"),false);
            TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();

            style.up = buttonSkin.getDrawable("button");
            style.down = buttonSkin.getDrawable("buttonPressed");
            style.font = font;


            TextButton button = new TextButton("Menu", style);
            button.setPosition((int)(Gdx.graphics.getHeight()*0.9),0);
            button.setHeight((int)(Gdx.graphics.getHeight()*0.1));
            button.setWidth(Gdx.graphics.getWidth());

            final Table scrollTable = new Table();
            scrollTable.add(button);
            scrollTable.row();

            final ScrollPane scroller = new ScrollPane(scrollTable);

            final Table table = new Table();
            table.setFillParent(true);
            table.add(scroller).fill().expand();

            this.stage.addActor(table);
        }

        @Override public void render() {
            this.stage.act();
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            this.stage.draw();
        }

        @Override public void resize(final int width, final int height) {}
        @Override public void pause() {}
        @Override public void resume() {}
        @Override public void dispose() {}
}
