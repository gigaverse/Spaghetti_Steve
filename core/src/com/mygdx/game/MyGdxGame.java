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
    private BitmapFont font, big;
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
        //TODO find out how to not hardcode the font stuff
        big = new BitmapFont(Gdx.files.internal("big.fnt"),false);
        big.setColor(0f,0f,0f,1f);

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
                    state = states[1];
                    upgradeScreen.addActor(menuButton);
                    Gdx.input.setInputProcessor(upgradeScreen);

                }
                else
                {
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
        final TextButton button1 = new TextButton(String.format("%s costs %d lbs of pasta", restaurants.get(currentRestaurant).get("1").getName(), restaurants.get(currentRestaurant).get("1").getCost()), style);
        button1.setPosition((int)(Gdx.graphics.getHeight()),0);
        button1.setHeight((int)(Gdx.graphics.getHeight()*0.1));
        button1.setWidth(Gdx.graphics.getWidth());

        button1.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if(total >= restaurants.get(currentRestaurant).get("1").getCost()) {
                    total -= restaurants.get(currentRestaurant).get("1").getCost();
                    restaurants.get(currentRestaurant).get("1").add();
                    button1.setText(String.format("%s costs %d lbs of pasta", restaurants.get(currentRestaurant).get("1").getName(), restaurants.get(currentRestaurant).get("1").getCost()));
                }
            }
        });

        final TextButton button2 = new TextButton(String.format("%s costs %d lbs of pasta", restaurants.get(currentRestaurant).get("2").getName(), restaurants.get(currentRestaurant).get("2").getCost()), style);
        button2.setPosition((int)(Gdx.graphics.getHeight()*0.8),0);
        button2.setHeight((int)(Gdx.graphics.getHeight()*0.1));
        button2.setWidth(Gdx.graphics.getWidth());

        button2.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if(total >= restaurants.get(currentRestaurant).get("2").getCost()) {
                    total -= restaurants.get(currentRestaurant).get("2").getCost();
                    restaurants.get(currentRestaurant).get("2").add();
                    button2.setText(String.format("%s costs %d lbs of pasta", restaurants.get(currentRestaurant).get("2").getName(), restaurants.get(currentRestaurant).get("2").getCost()));
                }
            }
        });

        final TextButton button3 = new TextButton(String.format("%s costs %d lbs of pasta", restaurants.get(currentRestaurant).get("3").getName(), restaurants.get(currentRestaurant).get("3").getCost()), style);
        button3.setPosition((int)(Gdx.graphics.getHeight()*0.6),0);
        button3.setHeight((int)(Gdx.graphics.getHeight()*0.1));
        button3.setWidth(Gdx.graphics.getWidth());

        button3.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if(total >= restaurants.get(currentRestaurant).get("3").getCost()) {
                    total -= restaurants.get(currentRestaurant).get("3").getCost();
                    restaurants.get(currentRestaurant).get("3").add();
                    button3.setText(String.format("%s costs %d lbs of pasta", restaurants.get(currentRestaurant).get("3").getName(), restaurants.get(currentRestaurant).get("3").getCost()));
                }
            }
        });

        final TextButton button4 = new TextButton(String.format("%s costs %d lbs of pasta", restaurants.get(currentRestaurant).get("4").getName(), restaurants.get(currentRestaurant).get("4").getCost()), style);
        button4.setPosition((int)(Gdx.graphics.getHeight()),0);
        button4.setHeight((int)(Gdx.graphics.getHeight()*0.1));
        button4.setWidth(Gdx.graphics.getWidth());

        button4.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if(total >= restaurants.get(currentRestaurant).get("4").getCost()) {
                    total -= restaurants.get(currentRestaurant).get("4").getCost();
                    restaurants.get(currentRestaurant).get("4").add();
                    button4.setText(String.format("%s costs %d lbs of pasta", restaurants.get(currentRestaurant).get("4").getName(), restaurants.get(currentRestaurant).get("4").getCost()));
                }
            }
        });

        final TextButton button5 = new TextButton(String.format("%s costs %d lbs of pasta", restaurants.get(currentRestaurant).get("5").getName(), restaurants.get(currentRestaurant).get("5").getCost()), style);
        button5.setPosition((int)(Gdx.graphics.getHeight()*0.8),0);
        button5.setHeight((int)(Gdx.graphics.getHeight()*0.1));
        button5.setWidth(Gdx.graphics.getWidth());

        button5.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if(total >= restaurants.get(currentRestaurant).get("5").getCost()) {
                    total -= restaurants.get(currentRestaurant).get("5").getCost();
                    restaurants.get(currentRestaurant).get("5").add();
                    button5.setText(String.format("%s costs %d lbs of pasta", restaurants.get(currentRestaurant).get("5").getName(), restaurants.get(currentRestaurant).get("5").getCost()));
                }
            }
        });

        final TextButton button6 = new TextButton(String.format("%s costs %d lbs of pasta", restaurants.get(currentRestaurant).get("6").getName(), restaurants.get(currentRestaurant).get("6").getCost()), style);
        button6.setPosition((int)(Gdx.graphics.getHeight()*0.6),0);
        button6.setHeight((int)(Gdx.graphics.getHeight()*0.1));
        button6.setWidth(Gdx.graphics.getWidth());

        button6.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if(total >= restaurants.get(currentRestaurant).get("6").getCost()) {
                    total -= restaurants.get(currentRestaurant).get("6").getCost();
                    restaurants.get(currentRestaurant).get("6").add();
                    button6.setText(String.format("%s costs %d lbs of pasta", restaurants.get(currentRestaurant).get("6").getName(), restaurants.get(currentRestaurant).get("6").getCost()));
                }
            }
        });

        final TextButton button7 = new TextButton(String.format("%s costs %d lbs of pasta", restaurants.get(currentRestaurant).get("7").getName(), restaurants.get(currentRestaurant).get("7").getCost()), style);
        button7.setPosition((int)(Gdx.graphics.getHeight()*0.6),0);
        button7.setHeight((int)(Gdx.graphics.getHeight()*0.1));
        button7.setWidth(Gdx.graphics.getWidth());

        button7.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if(total >= restaurants.get(currentRestaurant).get("7").getCost()) {
                    total -= restaurants.get(currentRestaurant).get("7").getCost();
                    restaurants.get(currentRestaurant).get("7").add();
                    button7.setText(String.format("%s costs %d lbs of pasta", restaurants.get(currentRestaurant).get("7").getName(), restaurants.get(currentRestaurant).get("7").getCost()));
                }
            }
        });


        final TextButton button8 = new TextButton(String.format("%s costs %d lbs of pasta", restaurants.get(currentRestaurant).get("8").getName(), restaurants.get(currentRestaurant).get("8").getCost()), style);
        button8.setPosition((int)(Gdx.graphics.getHeight()),0);
        button8.setHeight((int)(Gdx.graphics.getHeight()*0.1));
        button8.setWidth(Gdx.graphics.getWidth());

        button8.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if(total >= restaurants.get(currentRestaurant).get("8").getCost()) {
                    total -= restaurants.get(currentRestaurant).get("8").getCost();
                    restaurants.get(currentRestaurant).get("8").add();
                    button8.setText(String.format("%s costs %d lbs of pasta", restaurants.get(currentRestaurant).get("8").getName(), restaurants.get(currentRestaurant).get("8").getCost()));
                }
            }
        });

        final TextButton button9 = new TextButton(String.format("%s costs %d lbs of pasta", restaurants.get(currentRestaurant).get("9").getName(), restaurants.get(currentRestaurant).get("9").getCost()), style);
        button9.setPosition((int)(Gdx.graphics.getHeight()*0.8),0);
        button9.setHeight((int)(Gdx.graphics.getHeight()*0.1));
        button9.setWidth(Gdx.graphics.getWidth());

        button9.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if(total >= restaurants.get(currentRestaurant).get("9").getCost()) {
                    total -= restaurants.get(currentRestaurant).get("9").getCost();
                    restaurants.get(currentRestaurant).get("9").add();
                    button9.setText(String.format("%s costs %d lbs of pasta", restaurants.get(currentRestaurant).get("9").getName(), restaurants.get(currentRestaurant).get("9").getCost()));
                }
            }
        });

        final TextButton button10 = new TextButton(String.format("%s costs %d lbs of pasta", restaurants.get(currentRestaurant).get("10").getName(), restaurants.get(currentRestaurant).get("10").getCost()), style);
        button10.setPosition((int)(Gdx.graphics.getHeight()*0.6),0);
        button10.setHeight((int)(Gdx.graphics.getHeight()*0.1));
        button10.setWidth(Gdx.graphics.getWidth());

        button10.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if(total >= restaurants.get(currentRestaurant).get("10").getCost()) {
                    total -= restaurants.get(currentRestaurant).get("10").getCost();
                    restaurants.get(currentRestaurant).get("10").add();
                    button10.setText(String.format("%s costs %d lbs of pasta", restaurants.get(currentRestaurant).get("10").getName(), restaurants.get(currentRestaurant).get("10").getCost()));
                }
            }
        });

        final Table scrollTable = new Table();
        scrollTable.add(button1).padTop(40).padBottom(10f);
        scrollTable.row();
        scrollTable.add(button2).padTop(40).padBottom(10f);
        scrollTable.row();
        scrollTable.add(button3).padTop(40).padBottom(10f);
        scrollTable.row();
        scrollTable.add(button4).padTop(40).padBottom(10f);
        scrollTable.row();
        scrollTable.add(button5).padTop(40).padBottom(10f);
        scrollTable.row();
        scrollTable.add(button6).padTop(40).padBottom(10f);
        scrollTable.row();
        scrollTable.add(button7).padTop(40).padBottom(10f);
        scrollTable.row();
        scrollTable.add(button8).padTop(40).padBottom(10f);
        scrollTable.row();
        scrollTable.add(button9).padTop(40).padBottom(10f);
        scrollTable.row();
        scrollTable.add(button10).padTop(40).padBottom(10f);
        scrollTable.row();

        final ScrollPane scroller = new ScrollPane(scrollTable);

        final Table table = new Table();
        table.setFillParent(true);
        table.add(scroller).size(Gdx.graphics.getWidth(),(int)(Gdx.graphics.getHeight()*0.8)).setActorY((int) (Gdx.graphics.getHeight() * 0.1));
        upgradeScreen.addActor(table);
    }


	@Override
	public void render () {
		Gdx.gl.glClearColor((float) (215 / 256.0), (float) (252 / 256.0), (float) (212 / 256.0), 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        if(state.equals("UpgradeMenu")) {
            upgradeScreen.act();
        }
        else {
            mainScreen.act();
        }
		batch.begin();
        mainScreen.draw();
        if(state.equals("UpgradeMenu")) {
            upgradeScreen.draw();
        }
		batch.end();
        //Drawing the Top Bar
        batch.begin();
        //TODO -- Make this not hardcoded
        big.draw(batch, String.format("%.2f lbs of Pasta", total), 5,(int)(Gdx.graphics.getHeight()-5));
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
            Gdx.app.log("wow", restaurants.get(currentRestaurant).get("1").getAmount() + "");
        }
    }
}

