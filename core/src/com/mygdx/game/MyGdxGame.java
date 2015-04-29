package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Json;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.io.FileWriter;
import java.io.IOException;
import org.json.*;

import jdk.nashorn.internal.parser.JSONParser;


public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
    Stage current;
    /*When you make a stage, you want to make the stage and all the things that fall under it*/
    private Stage mainScreen;
    private BitmapFont font, big;
    private TextureAtlas buttonsAtlas;
    private Skin buttonSkin;
    private TextButton menuButton;

    private TextureAtlas labelAtlas;
    private Skin labelSkin;

    private Stage upgradeScreen;

    ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
    int currentRestaurant;
    double total;

	String[] states = {"GameView", "UpgradeMenu"};
    String state = states[0];
	@Override
	public void create () {

        FileHandle hope = Gdx.files.local("pasta.dat");
        try {
            total = Double.parseDouble(hope.readString());
        }
        catch(Exception ex) {
            System.out.println(ex.toString());
        }




        batch = new SpriteBatch();

        Restaurant current = new Restaurant();
        restaurants.add(current);

        Timer timer = new Timer();
        timer.schedule(new compileToFunds(), 0, 100);

        //Dealing with the Main Screen
        mainScreen = new Stage();
        buttonsAtlas = new TextureAtlas("menuButton.atlas");
        buttonSkin = new Skin();
        buttonSkin.addRegions(buttonsAtlas);

        font = new BitmapFont(Gdx.files.internal("wow.fnt"),false);
        //TODO develop app in 1080p then add a scale to account for resolution.
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

        //TODO make sure to put this label code somewhere more organized
        labelAtlas = new TextureAtlas("label.atlas");
        labelSkin = new Skin();
        labelSkin.addRegions(labelAtlas);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        big.setColor(0f,0f,0f,1f);
        labelStyle.background = labelSkin.getDrawable("default");
        labelStyle.font = big;


        //TODO make the upgrade menus
        final Label text1 = new Label(restaurants.get(currentRestaurant).get("1").getAmount() + "", labelStyle);
        text1.setAlignment(Align.center);
        text1.setWrap(true);

        final TextButton button1 = new TextButton(String.format("%s costs %d lbs of pasta", restaurants.get(currentRestaurant).get("1").getName(), restaurants.get(currentRestaurant).get("1").getCost()), style);
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
                    text1.setText(restaurants.get(currentRestaurant).get("1").getAmount() + "");
                }
            }
        });

        final Label text2 = new Label(restaurants.get(currentRestaurant).get("2").getAmount() + "", labelStyle);
        text2.setAlignment(Align.center);
        text2.setWrap(true);

        final TextButton button2 = new TextButton(String.format("%s costs %d lbs of pasta", restaurants.get(currentRestaurant).get("2").getName(), restaurants.get(currentRestaurant).get("2").getCost()), style);
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
                    text2.setText(restaurants.get(currentRestaurant).get("2").getAmount() + "");
                }
            }
        });

        final Label text3 = new Label(restaurants.get(currentRestaurant).get("3").getAmount() + "", labelStyle);
        text3.setAlignment(Align.center);
        text3.setWrap(true);

        final TextButton button3 = new TextButton(String.format("%s costs %d lbs of pasta", restaurants.get(currentRestaurant).get("3").getName(), restaurants.get(currentRestaurant).get("3").getCost()), style);
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
                    text3.setText(restaurants.get(currentRestaurant).get("3").getAmount() + "");
                }
            }
        });

        final Label text4 = new Label(restaurants.get(currentRestaurant).get("4").getAmount() + "", labelStyle);
        text4.setAlignment(Align.center);
        text4.setWrap(true);

        final TextButton button4 = new TextButton(String.format("%s costs %d lbs of pasta", restaurants.get(currentRestaurant).get("4").getName(), restaurants.get(currentRestaurant).get("4").getCost()), style);
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
                    text4.setText(restaurants.get(currentRestaurant).get("4").getAmount() + "");
                }
            }
        });

        final Label text5 = new Label(restaurants.get(currentRestaurant).get("5").getAmount() + "", labelStyle);
        text5.setAlignment(Align.center);
        text5.setWrap(true);

        final TextButton button5 = new TextButton(String.format("%s costs %d lbs of pasta", restaurants.get(currentRestaurant).get("5").getName(), restaurants.get(currentRestaurant).get("5").getCost()), style);
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
                    text5.setText(restaurants.get(currentRestaurant).get("5").getAmount() + "");
                }
            }
        });

        final Label text6 = new Label(restaurants.get(currentRestaurant).get("6").getAmount() + "", labelStyle);
        text6.setAlignment(Align.center);
        text6.setWrap(true);

        final TextButton button6 = new TextButton(String.format("%s costs %d lbs of pasta", restaurants.get(currentRestaurant).get("6").getName(), restaurants.get(currentRestaurant).get("6").getCost()), style);
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
                    text6.setText(restaurants.get(currentRestaurant).get("6").getAmount() + "");
                }
            }
        });

        final Label text7 = new Label(restaurants.get(currentRestaurant).get("7").getAmount() + "", labelStyle);
        text7.setAlignment(Align.center);
        text7.setWrap(true);

        final TextButton button7 = new TextButton(String.format("%s costs %d lbs of pasta", restaurants.get(currentRestaurant).get("7").getName(), restaurants.get(currentRestaurant).get("7").getCost()), style);
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
                    text7.setText(restaurants.get(currentRestaurant).get("7").getAmount() + "");
                }
            }
        });

        final Label text8 = new Label(restaurants.get(currentRestaurant).get("8").getAmount() + "", labelStyle);
        text8.setAlignment(Align.center);
        text8.setWrap(true);

        final TextButton button8 = new TextButton(String.format("%s costs %d lbs of pasta", restaurants.get(currentRestaurant).get("8").getName(), restaurants.get(currentRestaurant).get("8").getCost()), style);
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
                    text8.setText(restaurants.get(currentRestaurant).get("8").getAmount() + "");
                }
            }
        });

        final Label text9 = new Label(restaurants.get(currentRestaurant).get("9").getAmount() + "", labelStyle);
        text9.setAlignment(Align.center);
        text9.setWrap(true);

        final TextButton button9 = new TextButton(String.format("%s costs %d lbs of pasta", restaurants.get(currentRestaurant).get("9").getName(), restaurants.get(currentRestaurant).get("9").getCost()), style);
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
                    text9.setText(restaurants.get(currentRestaurant).get("9").getAmount() + "");
                }
            }
        });

        final Label text10 = new Label(restaurants.get(currentRestaurant).get("10").getAmount() + "", labelStyle);
        text10.setAlignment(Align.center);
        text10.setWrap(true);

        final TextButton button10 = new TextButton(String.format("%s costs %d lbs of pasta", restaurants.get(currentRestaurant).get("10").getName(), restaurants.get(currentRestaurant).get("10").getCost()), style);
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
                    text10.setText(restaurants.get(currentRestaurant).get("10").getAmount() + "");
                }
            }
        });

        final Table scrollTable = new Table();
        scrollTable.center();
        scrollTable.add(button1).expandX().padTop(40).padBottom(10f);
        scrollTable.add(text1).padTop(40).padBottom(10f).padLeft(5).padRight(5).width((int)(Gdx.graphics.getWidth()*0.3)).height(button1.getHeight());
        scrollTable.row();
        scrollTable.add(button2).expandX().padTop(40).padBottom(10f);
        scrollTable.add(text2).padTop(40).padBottom(10f).padLeft(5).padRight(5).width((int)(Gdx.graphics.getWidth()*0.3)).height(button1.getHeight());
        scrollTable.row();
        scrollTable.add(button3).expandX().padTop(40).padBottom(10f);
        scrollTable.add(text3).padTop(40).padBottom(10f).padLeft(5).padRight(5).width((int)(Gdx.graphics.getWidth()*0.3)).height(button1.getHeight());
        scrollTable.row();
        scrollTable.add(button4).expandX().padTop(40).padBottom(10f);
        scrollTable.add(text4).padTop(40).padBottom(10f).padLeft(5).padRight(5).width((int)(Gdx.graphics.getWidth()*0.3)).height(button1.getHeight());
        scrollTable.row();
        scrollTable.add(button5).expandX().padTop(40).padBottom(10f);
        scrollTable.add(text5).padTop(40).padBottom(10f).padLeft(5).padRight(5).width((int)(Gdx.graphics.getWidth()*0.3)).height(button1.getHeight());
        scrollTable.row();
        scrollTable.add(button6).expandX().padTop(40).padBottom(10f);
        scrollTable.add(text6).padTop(40).padBottom(10f).padLeft(5).padRight(5).width((int)(Gdx.graphics.getWidth()*0.3)).height(button1.getHeight());
        scrollTable.row();
        scrollTable.add(button7).expandX().padTop(40).padBottom(10f);
        scrollTable.add(text7).padTop(40).padBottom(10f).padLeft(5).padRight(5).width((int)(Gdx.graphics.getWidth()*0.3)).height(button1.getHeight());
        scrollTable.row();
        scrollTable.add(button8).expandX().padTop(40).padBottom(10f);
        scrollTable.add(text8).padTop(40).padBottom(10f).padLeft(5).padRight(5).width((int)(Gdx.graphics.getWidth()*0.3)).height(button1.getHeight());
        scrollTable.row();
        scrollTable.add(button9).expandX().padTop(40).padBottom(10f);
        scrollTable.add(text9).padTop(40).padBottom(10f).padLeft(5).padRight(5).width((int)(Gdx.graphics.getWidth()*0.3)).height(button1.getHeight());
        scrollTable.row();
        scrollTable.add(button10).expandX().padTop(40).padBottom(10f);
        scrollTable.add(text10).padTop(40).padBottom(10f).padLeft(5).padRight(5).width((int)(Gdx.graphics.getWidth()*0.3)).height(button1.getHeight());
        scrollTable.row();

        final ScrollPane scroller = new ScrollPane(scrollTable);

        final Table table = new Table();
        table.setFillParent(true);
        table.add(scroller).size(Gdx.graphics.getWidth(),(int)(Gdx.graphics.getHeight()*0.8)).setActorY((int) (Gdx.graphics.getHeight() * 0.1));
        upgradeScreen.addActor(table);
    }


	@Override
	public void render () {
		Gdx.gl.glClearColor((float) (189 / 256.0), (float) (196 / 256.0), (float) (167 / 256.0), 1);
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
            FileHandle hope = Gdx.files.local("pasta.dat");
            hope.writeString(Double.toString(total),false);
            double num = 0.1;
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

