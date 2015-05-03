package com.mygdx.game;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Json;

import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType.Bitmap;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType.Face;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType.GlyphMetrics;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType.GlyphSlot;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType.Library;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType.SizeMetrics;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType.Stroker;


import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.io.FileWriter;
import java.io.IOException;

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
    private TextButton menuButton, optionsButton;
    private TextureAtlas labelAtlas;
    private Skin labelSkin;

    private Stage upgradeScreen;

    private Stage optionsScreen;

    Label pastaDisplay;
    Label moneyDisplay;

    double total;
    ArrayList<Restaurant> restaurants;
    int currentRestaurant;
    String[] states = {"GameView", "UpgradeMenu", "OptionsMenu"};
    String state = states[0];

    float scale;

    @Override
    public void create () {
        //read file if its been previously saved
        restaurants = new ArrayList<Restaurant>();
        FileHandle hope = Gdx.files.local("pasta2.dat");
        try {
            Json j = new Json();
            String wow = hope.readString();
            Gdx.app.log("wow", wow + "");
            Restaurant r = j.fromJson(Restaurant.class, wow);
            restaurants.add(r);
        }
        catch(Exception ex) {
            System.out.println(ex.toString());
        }

        batch = new SpriteBatch();
        Restaurant current = new Restaurant();
        if(restaurants.size() == 0) {
            restaurants = new ArrayList<Restaurant>();
            restaurants.add(current);
        }

        Timer timer = new Timer();
        timer.schedule(new compileToFunds(), 0, 100);
        timer.schedule(new saveGame(), 0, 1000);

        //Dealing with the Main Screen
        mainScreen = new Stage();
        buttonsAtlas = new TextureAtlas("menuButton.atlas");
        buttonSkin = new Skin();
        buttonSkin.addRegions(buttonsAtlas);

        //Generates Bitmap font from .ttf and scales it
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Sansation-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int)(12*Gdx.graphics.getDensity());
        BitmapFont font12 = generator.generateFont(parameter);
        parameter.size = (int)(28*Gdx.graphics.getDensity());
        BitmapFont font36 = generator.generateFont(parameter);
        generator.dispose();

        font = font12;
        big = font36;
        big.setColor(0f,0f,0f,1f);

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();

        style.up = buttonSkin.getDrawable("button");
        style.down = buttonSkin.getDrawable("buttonPressed");
        style.font = font;

        TextButton.TextButtonStyle optionsStyle = new TextButton.TextButtonStyle();
        Skin optionsSkin = new Skin();
        optionsSkin.addRegions(new TextureAtlas("optionsButton.atlas"));

        optionsStyle.up = optionsSkin.getDrawable("button");
        optionsStyle.down = optionsSkin.getDrawable("buttonPressed");
        optionsStyle.font = font;

        menuButton = new TextButton("Menu", style);
        menuButton.setPosition(0, 0);
        menuButton.setHeight((int)(Gdx.graphics.getHeight()*0.1));
        menuButton.setWidth(Gdx.graphics.getWidth()/2);

        optionsButton = new TextButton("Options", optionsStyle);
        optionsButton.setPosition(Gdx.graphics.getWidth()/2, 0);
        optionsButton.setHeight((int)(Gdx.graphics.getHeight()*0.1));
        optionsButton.setWidth(Gdx.graphics.getWidth()/2);
        Gdx.input.setInputProcessor(mainScreen);

        menuButton.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if(state.equals(states[0]) || state.equals(states[2]))
                {
                    state = states[1];
                    upgradeScreen.addActor(menuButton);
                    upgradeScreen.addActor(optionsButton);
                    Gdx.input.setInputProcessor(upgradeScreen);

                }
                else
                {
                    state = states[0];

                    mainScreen.addActor(menuButton);
                    mainScreen.addActor(optionsButton);
                    Gdx.input.setInputProcessor(mainScreen);

                }
            }
        });

        optionsButton.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if(state.equals(states[0]) || state.equals(states[1]))
                {
                    state = states[2];
                    optionsScreen.addActor(menuButton);
                    optionsScreen.addActor(optionsButton);
                    Gdx.input.setInputProcessor(optionsScreen);

                }
                else
                {
                    state = states[0];

                    mainScreen.addActor(menuButton);
                    mainScreen.addActor(optionsButton);
                    Gdx.input.setInputProcessor(mainScreen);

                }
            }
        });

        mainScreen.addActor(menuButton);
        mainScreen.addActor(optionsButton);

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

        upgradeScreen.addActor(UpgradeMenu.upgradeMenu(restaurants,currentRestaurant,labelStyle,style));

        //Dealing With The Options Menu
        optionsScreen = new Stage();

        optionsScreen.addActor(OptionsMenu.optionsMenu(restaurants, style, labelStyle, upgradeScreen));

        pastaDisplay = new Label(String.format("%.2f\nlbs", restaurants.get(currentRestaurant).sum), labelStyle);
        pastaDisplay.setAlignment(Align.center);
        pastaDisplay.setWrap(true);
        pastaDisplay.setX(0);
        pastaDisplay.setY(Gdx.graphics.getHeight()*0.9f);
        pastaDisplay.setWidth(Gdx.graphics.getWidth()/2);
        pastaDisplay.setHeight(Gdx.graphics.getHeight()/10);
        mainScreen.addActor(pastaDisplay);

        TextureAtlas moneyAtlas = new TextureAtlas("moneyLabel.atlas");
        Skin moneySkin = new Skin();
        moneySkin.addRegions(moneyAtlas);

        Label.LabelStyle moneyStyle = new Label.LabelStyle();
        big.setColor(0f,0f,0f,1f);
        moneyStyle.background = moneySkin.getDrawable("default");
        moneyStyle.font = big;

        moneyDisplay = new Label(String.format("$%6.2f", total), moneyStyle);
        moneyDisplay.setAlignment(Align.center);
        moneyDisplay.setWrap(true);
        moneyDisplay.setX(Gdx.graphics.getWidth()/2);
        moneyDisplay.setY(Gdx.graphics.getHeight()*0.9f);
        moneyDisplay.setWidth(Gdx.graphics.getWidth()/2);
        moneyDisplay.setHeight(Gdx.graphics.getHeight()/10);
        mainScreen.addActor(moneyDisplay);
    }


    @Override
    public void render ()
    {
        Gdx.gl.glClearColor((float) (173 / 256.0), (float) (162 / 256.0), (float) (150 / 256.0), 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(state.equals(states[1]))
        {
            upgradeScreen.act();
        }
        else if(state.equals(states[2]))
        {
            optionsScreen.act();
        }
        else
        {
            mainScreen.act();
        }

        batch.begin();

        mainScreen.draw();

        if(state.equals(states[1])) {
            upgradeScreen.draw();
        }
        else if(state.equals(states[2]))
        {
            optionsScreen.draw();
        }

        batch.end();
        //Drawing the Top Bar
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

    class compileToFunds extends TimerTask
    {
        public void run()
        {
            //saving file
            double num = 0.1;
            for(Restaurant r : restaurants)
            {
                num = 0.1;
                for(int i = 0; i < r.getList().size(); i++)
                {

                    Upgrade u = r.get(i+1);
                    if(u != null)
                        num += u.tick();
                }
                r.setSum(r.getSum() + num);
            }
            if(pastaDisplay != null)
                pastaDisplay.setText(String.format("%6.2f\nlbs", restaurants.get(currentRestaurant).sum));
            if(moneyDisplay != null)
                moneyDisplay.setText(String.format("$%.2f", total));

        }
    }

    class saveGame extends TimerTask
    {
        public void run() {
            //saving file
            FileHandle hope = Gdx.files.local("pasta2.dat");
            hope.delete();
            Json json = new Json();
            hope.writeString(json.toJson(restaurants.get(currentRestaurant)), false);
        }
    }
}

