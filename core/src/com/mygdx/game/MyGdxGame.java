package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
    private Stage stage; //** stage holds the Button **//
    private BitmapFont font; //** same as that used in Tut 7 **//
    private TextureAtlas buttonsAtlas; //** image of buttons **//
    private Skin buttonSkin; //** images are used as skins of the button **//
    private TextButton button; //** the button - the only actor in program **//
    ImageButton menu;
	String[] states = {"GameView"};
    String state = states[0];
	@Override
	public void create () {
		batch = new SpriteBatch();

        buttonsAtlas = new TextureAtlas("menuButton.atlas"); //** button atlas image **//
        buttonSkin = new Skin();
        buttonSkin.addRegions(buttonsAtlas); //** skins for on and off **//
        font = new BitmapFont(Gdx.files.internal("wow.fnt"),false); //** font **//

        //Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true
        stage = new Stage();
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle(); //** Button properties **//
        style.up = buttonSkin.getDrawable("button");
        style.down = buttonSkin.getDrawable("buttonPressed");
        style.font = font;

        button = new TextButton("Menu", style); //** Button text and style **//
        button.setPosition(0, 0); //** Button location **//
        button.setHeight((int)(Gdx.graphics.getHeight()*0.1)); //** Button Height **//
        button.setWidth(Gdx.graphics.getWidth()); //** Button Width **//

        button.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("my app", "Pressed"); //** Usually used to start Game, etc. **//
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("my app", "Released");
            }
        });

        stage.addActor(button);
    }


	@Override
	public void render () {
		Gdx.gl.glClearColor((float) (215 / 256.0), (float) (252 / 256.0), (float) (212 / 256.0), 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
        stage.draw();
		batch.end();
	}

    @Override
    public void dispose() {
        batch.dispose();
        buttonSkin.dispose();
        buttonsAtlas.dispose();
        font.dispose();
        stage.dispose();
    }


}

