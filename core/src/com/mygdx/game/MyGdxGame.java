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

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
    /*When you make a stage, you want to make the stage and all the things that fall under it*/
    private Stage mainScreen;
    private BitmapFont font;
    private TextureAtlas buttonsAtlas;
    private Skin buttonSkin;
    private TextButton button;

    private Stage upgradeScreen;

    ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
    int currentRestaurant;
    double total;

	String[] states = {"GameView", "UpgradeMenu"};
    String state = states[0];
	@Override
	public void create () {
		batch = new SpriteBatch();

        Restaurant current = new Restaurant();
        restaurants.add(current);

        Timer timer = new Timer();
        timer.schedule(new compileToFunds(), 0, 1000);

        //Dealing with the Main Screen
        mainScreen = new Stage();
        buttonsAtlas = new TextureAtlas("menuButton.atlas");
        buttonSkin = new Skin();
        buttonSkin.addRegions(buttonsAtlas);

        font = new BitmapFont(Gdx.files.internal("wow.fnt"),false);
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();

        style.up = buttonSkin.getDrawable("button");
        style.down = buttonSkin.getDrawable("buttonPressed");
        style.font = font;

        button = new TextButton("Menu", style);
        button.setPosition(0, 0);
        button.setHeight((int)(Gdx.graphics.getHeight()*0.1));
        button.setWidth(Gdx.graphics.getWidth());
        Gdx.input.setInputProcessor(mainScreen);

        button.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("wow", "Menu Opened"); //** Usually used to start Game, etc. **//

                if(!state.equals("UpgradeMenu"))
                {
                    //TODO load up the upgrade menu
                }
                else
                {
                    //TODO go back to the main screen
                }
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {

            }
        });

        mainScreen.addActor(button);

        //Dealing with the Upgrade Menus
        upgradeScreen = new Stage();
        //TODO make the upgrade menus
    }


	@Override
	public void render () {
		Gdx.gl.glClearColor((float) (215 / 256.0), (float) (252 / 256.0), (float) (212 / 256.0), 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mainScreen.act();

		batch.begin();
        mainScreen.draw();
        font.draw(batch, "test test u suck " + total, 100, 200);
		batch.end();
	}


    public void show() {
        batch.dispose();
        buttonSkin.dispose();
        buttonsAtlas.dispose();
        font.dispose();
        mainScreen.dispose();
    }

    @Override
    public void dispose() {
        batch.dispose();
        buttonSkin.dispose();
        buttonsAtlas.dispose();
        font.dispose();
        mainScreen.dispose();
    }



    class compileToFunds extends TimerTask {
        public void run() {
            double num = 1;
            for(Restaurant r : restaurants)
            {
                for(String s : r.list.keySet())
                {
                    Upgrade u = r.list.get(s);
                    num += u.tick();
                }
            }
            total += num;
        }
    }
}

