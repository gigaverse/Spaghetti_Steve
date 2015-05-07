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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Json;

import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;



import java.util.Timer;
import java.util.TimerTask;


public class MyGdxGame extends ApplicationAdapter {
    SpriteBatch batch;
    Stage current;
    /*When you make a stage, you want to make the stage and all the things that fall under it*/
    private static Stage mainScreen;
    private static BitmapFont font, big, font24;
    private static TextureAtlas buttonsAtlas;
    private static Skin buttonSkin;
    private static TextButton menuButton, optionsButton;
    private static TextureAtlas labelAtlas;
    private static Skin labelSkin;
    private static TextButton.TextButtonStyle buttonStyle;
    private static Label.LabelStyle labelStyle;

    private static Stage upgradeScreen;

    private static Stage optionsScreen;

    static Label pastaDisplay;
    static Label moneyDisplay;

    static PlayerSave player;
    static String[] states = {"GameView", "UpgradeMenu", "OptionsMenu"};
    static String state = states[0];

    @Override
    public void create () {
        //read file if its been previously saved

        player = new PlayerSave();
        FileHandle hope = Gdx.files.local("pasta3.dat");
        try {
            Json j = new Json();
            String wow = hope.readString();
            player = j.fromJson(PlayerSave.class, wow);
        }
        catch(Exception ex) {
            Gdx.app.log("wow", ex.toString());
        }

        batch = new SpriteBatch();
        if(player.getRestaurants().size() == 0) {
            player.init();
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
        parameter.size = (int)(20*Gdx.graphics.getDensity());
        font24 = generator.generateFont(parameter);
        parameter.size = (int)(28*Gdx.graphics.getDensity());
        BitmapFont font36 = generator.generateFont(parameter);
        generator.dispose();

        font = font12;
        big = font36;
        big.setColor(0f,0f,0f,1f);

        buttonStyle = new TextButton.TextButtonStyle();

        buttonStyle.up = buttonSkin.getDrawable("button");
        buttonStyle.down = buttonSkin.getDrawable("buttonPressed");
        buttonStyle.font = font;

        TextButton.TextButtonStyle optionsStyle = new TextButton.TextButtonStyle();
        Skin optionsSkin = new Skin();
        optionsSkin.addRegions(new TextureAtlas("optionsButton.atlas"));

        optionsStyle.up = optionsSkin.getDrawable("button");
        optionsStyle.down = optionsSkin.getDrawable("buttonPressed");
        optionsStyle.font = font;

        menuButton = new TextButton("Menu", buttonStyle);
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
                    upgradeScreen();
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
                    upgradeScreen();
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

        labelStyle = new Label.LabelStyle();
        big.setColor(0f,0f,0f,1f);
        labelStyle.background = labelSkin.getDrawable("default");
        labelStyle.font = font24;

        upgradeScreen.addActor(RestaurantsMenu.restaurantsMenu(player,labelStyle,buttonStyle));

        labelStyle.font = big;
        //Dealing With The Options Menu
        optionsScreen = new Stage();

        optionsScreen.addActor(OptionsMenu.optionsMenu(player, buttonStyle, labelStyle, upgradeScreen));

        pastaDisplay = new Label(String.format("%.1f\nlbs", player.getCurrentRestaurant().sum), labelStyle);
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

        moneyDisplay = new Label(String.format("$%.2f", player.getTotal()), moneyStyle);
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
        Gdx.gl.glClearColor((float) (210 / 256.0), (float) (215 / 256.0), (float) (223 / 256.0), 1);
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

    public static void updateMenu(Table t)
    {
        upgradeScreen.clear();
        upgradeScreen.addActor(t);
        state = states[1];
        upgradeScreen.addActor(menuButton);
        upgradeScreen.addActor(optionsButton);
        Gdx.input.setInputProcessor(upgradeScreen);
    }

    public static void upgradeScreen()
    {
        upgradeScreen.clear();
        labelStyle.font = font24;
        upgradeScreen.addActor(RestaurantsMenu.restaurantsMenu(player,labelStyle,buttonStyle));
        labelStyle.font = big;
    }

    public static void upgradeScreenButtons(Label name, TextButton left, TextButton right)
    {
        upgradeScreen.addActor(name);
        upgradeScreen.addActor(left);
        upgradeScreen.addActor(right);
    }

    public static void RestaurantScreen(int page)
    {
            upgradeScreen.clear();
            upgradeScreen.addActor(UpgradeMenu.upgradeMenu(player,labelStyle,buttonStyle,page));
        upgradeScreen.addActor(menuButton);
        upgradeScreen.addActor(optionsButton);
    }

    public static String convertNumber(double d)
    {
        char[] prefixes = "YZEPTGM".toCharArray();
        String[] s = String.format("%.2f", d).split("\\.");
        String prefix = "";

        if(String.format("%.2f",d).length() < 10) {
            if(s[1].equals("00"))
                return String.format("%d", (int)d);
            return String.format("%.2f", d);
        }

        for(int i = 0; i < prefixes.length; i++) {
            while (s[0].length() > (24 - (i * 3))) {
                s[0] = s[0].substring(0, s[0].length() - (24 - (i * 3)));
                prefix += prefixes[i] + "";
            }
        }
        prefix = s[0] + prefix;
        return prefix;
    }

    class compileToFunds extends TimerTask
    {
        public void run()
        {
            //saving file
            double numPasta = 0.1;
            double numDollars = 0;
            for(Restaurant r : player.getRestaurants())
            {
                numPasta = 0.1;
                for(Upgrade u : r.getUpgrades())
                {
                    if(u != null)
                        numPasta += u.tick();
                }

                for(Unit u : r.getUnits())
                {
                    if(u != null)
                        numDollars += u.getAmount()*u.getMultiplier();
                }
                r.setSum(r.getSum() + numPasta);
            }
            player.setTotal(player.getTotal() + numDollars);
            if(pastaDisplay != null) {
                pastaDisplay.setText(String.format("%s\nlbs", convertNumber(player.getCurrentRestaurant().sum)));
                Gdx.app.log("wow", player.getCurrentRestaurant().sum + "");
            }
            if(moneyDisplay != null)
                moneyDisplay.setText(String.format("$%s", convertNumber(player.getTotal())));

        }
    }
    //TODO FIX SAVING
    class saveGame extends TimerTask
    {
        public void run() {
            //saving file
            FileHandle hope = Gdx.files.local("pasta3.dat");
            hope.delete();
            Json json = new Json();
            hope.writeString(json.toJson(player), false);
        }
    }
}

