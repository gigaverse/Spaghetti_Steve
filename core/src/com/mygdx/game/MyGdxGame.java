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
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
    Stage current;
    /*When you make a stage, you want to make the stage and all the things that fall under it*/
    private Stage mainScreen;
    private BitmapFont font;
    private TextureAtlas buttonsAtlas;
    private Skin buttonSkin;
    private TextButton menuButton;

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

        menuButton = new TextButton("Menu", style);
        menuButton.setPosition(0, 0);
        menuButton.setHeight((int)(Gdx.graphics.getHeight()*0.1));
        menuButton.setWidth(Gdx.graphics.getWidth());
        Gdx.input.setInputProcessor(mainScreen);

        menuButton.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if(state.equals(states[0]))
                {
                    //TODO load up the upgrade menu
                    state = states[1];
                    upgradeScreen.addActor(menuButton);
                    Gdx.input.setInputProcessor(upgradeScreen);

                }
                else
                {
                    //TODO go back to the main screen
                    state = states[0];
                    Gdx.app.log("wow", "YOU DID IT!");
                    mainScreen.addActor(menuButton);
                    Gdx.input.setInputProcessor(mainScreen);

                }
            }
        });

        mainScreen.addActor(menuButton);

        //Dealing with the Upgrade Menus
        upgradeScreen = new Stage();
        //TODO make the upgrade menus
        TextButton button0 = new TextButton("Item 1", style);
        button0.setPosition((int)(Gdx.graphics.getHeight()),0);
        button0.setHeight((int)(Gdx.graphics.getHeight()*0.1));
        button0.setWidth(Gdx.graphics.getWidth());

        TextButton button2 = new TextButton("Item 2", style);
        button2.setPosition((int)(Gdx.graphics.getHeight()*0.8),0);
        button2.setHeight((int)(Gdx.graphics.getHeight()*0.1));
        button2.setWidth(Gdx.graphics.getWidth());

        TextButton button3 = new TextButton("Item 3", style);
        button3.setPosition((int)(Gdx.graphics.getHeight()*0.6),0);
        button3.setHeight((int)(Gdx.graphics.getHeight()*0.1));
        button3.setWidth(Gdx.graphics.getWidth());


        final Table scrollTable = new Table();
        scrollTable.add(button0).padTop(40).padBottom(10f);
        scrollTable.row();
        scrollTable.add(button2).padTop(40).padBottom(10f);
        scrollTable.row();
        scrollTable.add(button3).padTop(40).padBottom(10f);
        scrollTable.row();

        final ScrollPane scroller = new ScrollPane(scrollTable);

        final Table table = new Table();
        table.setFillParent(true);
        table.add(scroller).fill().expand();
        upgradeScreen.addActor(table);
    }


	@Override
	public void render () {
		Gdx.gl.glClearColor((float) (215 / 256.0), (float) (252 / 256.0), (float) (212 / 256.0), 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        if(state.equals("UpgradeMenu"))
            upgradeScreen.act();
        else
            mainScreen.act();
		batch.begin();
            mainScreen.draw();
        if(state.equals("UpgradeMenu"))
            upgradeScreen.draw();
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

