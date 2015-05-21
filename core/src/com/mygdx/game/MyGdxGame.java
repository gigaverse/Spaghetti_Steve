package com.mygdx.game;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
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


import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class MyGdxGame extends ApplicationAdapter {
    SpriteBatch batch;
    Sprite iconSprite;

    private static ArrayList<FallingObject> fallingSprites = new ArrayList<FallingObject>();

    /*When you make a stage, you want to make the stage and all the things that fall under it*/
    private static Stage mainScreen;
    double animationparam = 0;
    private static BitmapFont font, big, font20,  font24;
    private static TextureAtlas buttonsAtlas;
    private static Skin buttonSkin;
    private static TextButton menuButton, optionsButton;
    private static TextureAtlas labelAtlas;
    private static Skin labelSkin;
    private static TextButton.TextButtonStyle buttonStyle;
    private static Label.LabelStyle labelStyle;
    private static Texture icon, money, pasta;
    public static Timer timer, t;
    private static Stage upgradeScreen;

    private static SplashScreen splashScreen;

    private static Stage optionsScreen;

    private static boolean shouldAnimate = true;

    static Label pastaDisplay;
    static Label moneyDisplay;

    static PlayerSave player;
    static String[] states = {"GameView", "UpgradeMenu", "OptionsMenu", "SplashScreen"};
    static String state = states[3];
    public static NameGenerator nameGenerator;

    @Override
    public void create () {

        shouldAnimate=true;
        //read file if its been previously saved
        splashScreen = new SplashScreen();

        state = states[3];
        icon = new Texture(Gdx.files.internal("chefs.png"));
        try {
            nameGenerator = new NameGenerator();
        }
        catch(IOException e)
        {
            Gdx.app.log("wow",e.toString());
        }

        money = new Texture(Gdx.files.internal("dosh.png"));
        pasta = new Texture(Gdx.files.internal("pasta.png"));

        iconSprite = new Sprite(icon);
        iconSprite.setCenterX(Gdx.graphics.getWidth() / 2);
        iconSprite.setCenterY(Gdx.graphics.getHeight() / 2);

        player = new PlayerSave();
        FileHandle hope = Gdx.files.local("pasta4.dat");
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

        timer = new Timer();
        timer.schedule(new compileToFunds(), 0, 100);
        t = new Timer();
        t.schedule(new saveGame(), 0, 1000);

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
        parameter.size = (int)(16*Gdx.graphics.getDensity());
        font20 = generator.generateFont(parameter);
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
                    upgradeScreen.clear();
                    labelStyle.font = font20;
                    upgradeScreen.addActor(RestaurantsMenu.restaurantsMenu(player,labelStyle,buttonStyle));
                    labelStyle.font = font24;
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
                    optionsScreen.clear();
                    optionsScreen.addActor(menuButton);
                    optionsScreen.addActor(optionsButton);
                    optionsScreen.addActor(OptionsMenu.optionsMenu(player, buttonStyle, labelStyle, upgradeScreen));
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
        big.setColor(0f, 0f, 0f, 1f);
        moneyStyle.background = moneySkin.getDrawable("default");
        moneyStyle.font = big;

        moneyDisplay = new Label(String.format("$%.2f", player.getTotalMoney()), moneyStyle);
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
        if(!state.equals(states[3])) {
            Gdx.gl.glClearColor((float) (210 / 256.0), (float) (215 / 256.0), (float) (223 / 256.0), 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        }
        else
        {
            Gdx.gl.glClearColor(1,1,1,1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        }


        if(state.equals(states[1]))
        {
            upgradeScreen.act();
        }
        else if(state.equals(states[2]))
        {
            optionsScreen.act();
        }
        else if(state.equals(states[0]))
        {
            mainScreen.act();
        }

        batch.begin();



        if(!state.equals(states[3])) {
            iconSprite.scale((float) (0.3 + 0.05 * Math.cos(animationparam)) * Gdx.graphics.getDensity());
            iconSprite.draw(batch);
            iconSprite.rotate(-0.5f);

            iconSprite.scale(-(float) (0.3 + 0.05 * Math.cos(animationparam)) * Gdx.graphics.getDensity());
        }


        animationparam = (animationparam + 0.05) % (2*Math.PI);

        batch.end();


        batch.begin();

        if(state.equals(states[3]))
        {
            if(Gdx.input.isTouched())
            {
                state = (states[0]);
            }
            splashScreen.tick(batch, animationparam);
        }

        batch.end();

        if(state.equals(states[3]))
            return;


        batch.begin();



            if(shouldAnimate) {
                for (int i = fallingSprites.size() - 1; i >= 0; i--) {
                    FallingObject f = fallingSprites.get(i);
                    if (f == null || !f.draw(batch))
                        fallingSprites.remove(i);
                }
            }
            else
            {
                Gdx.app.log("wow","Should animate = "+shouldAnimate);

            }



        batch.end();

        if(fallingSprites.size() >= 5000)
            fallingSprites = new ArrayList<FallingObject>();

        //Main Screen Drawing
        batch.begin();

        mainScreen.draw();

        batch.end();

        //Everything else
        batch.begin();

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
            labelStyle.font = font20;
            buttonStyle.font = font20;
        if(!player.country)
            upgradeScreen.addActor(UpgradeMenu.upgradeMenu(player,labelStyle,buttonStyle,page));
        else
            upgradeScreen.addActor(UpgradeMenu.territoryUpgradeMenu(player,labelStyle,buttonStyle,page));
        buttonStyle.font = font;
        labelStyle.font = big;
        upgradeScreen.addActor(menuButton);
        upgradeScreen.addActor(optionsButton);
    }

    public static void ExpansionScreen()
    {
        upgradeScreen.clear();
        labelStyle.font = font24;
        upgradeScreen.addActor(ExpansionMenu.expansionMenu(player,labelStyle,buttonStyle));
        labelStyle.font = big;
        upgradeScreen.addActor(menuButton);
        upgradeScreen.addActor(optionsButton);
        t = new Timer();
        t.schedule(new saveGame(), 0, 1000);
    }

    public static void TerritoryScreen()
    {
        upgradeScreen.clear();
        labelStyle.font = font24;
        upgradeScreen.addActor(TerritoryMenu.territoryMenu(player, labelStyle, buttonStyle));
        labelStyle.font = big;
        upgradeScreen.addActor(menuButton);
        upgradeScreen.addActor(optionsButton);
    }

    public static void hide()
    {
        fallingSprites = new ArrayList<FallingObject>();
    }
    public static void willAnimate()
    {
        shouldAnimate=!shouldAnimate;

    }

    public static String convertNumber(double d)
    {
        char[] prefixes = "YZEPTGMK".toCharArray();
        String[] s = String.format("%.2f", d).split("\\.");
        String prefix = "";

        if(String.format("%.2f",d).length() < 10) {
            if(s[1].equals("00"))
                return String.format("%d", (int)d);
            return String.format("%.2f", d);
        }

        for(int i = 0; i < prefixes.length; i++) {
            while (s[0].length() > (24 - (i * 3))) {
                s[1] = s[0].substring(s[0].length() - (24 - (i * 3)));
                s[0] = s[0].substring(0, s[0].length() - (24 - (i * 3)));
                prefix += prefixes[i] + "";
            }
        }
        prefix = s[0] + "." + s[1].substring(0, 2) + prefix;
        return prefix;
    }

    class compileToFunds extends TimerTask
    {
        public void run()
        {
            //saving file
            double numPasta = 0;
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
                player.setTotalPasta(player.getTotalPasta() + numPasta);
                }

                for(float i = 0; i < numDollars; i+=1.5)
                {
                    FallingObject doshSprite = new FallingObject(money, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), -(float)Math.random()*5f, -(float)Math.random()*3f);
                    doshSprite.scale(.75f*Gdx.graphics.getDensity());
                    fallingSprites.add(doshSprite);
                }

                for(int i = 0; i < numPasta; i+=350)
                {
                    FallingObject doshSprite = new FallingObject(pasta, 0, Gdx.graphics.getHeight(), (float)Math.random()*5f, -(float)Math.random()*3f);
                    doshSprite.scale(.75f*Gdx.graphics.getDensity());
                    fallingSprites.add(doshSprite);
                }



            player.setTotalMoney(player.getTotalMoney() + numDollars);
            if(pastaDisplay != null) {
                pastaDisplay.setText(String.format("%s\nlbs", convertNumber(player.getTotalPasta())));
            }
            if(moneyDisplay != null)
                moneyDisplay.setText(String.format("$%s", convertNumber(player.getTotalMoney())));
        }
    }

    static class saveGame extends TimerTask
    {
        public void run() {
            //saving file
            if(!player.save)
                return;
            FileHandle hope = Gdx.files.local("pasta4.dat");
            Json json = new Json();
            hope.writeString(json.toJson(player), false);
        }
    }
}

