package com.mygdx.spaghettismackdown;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
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
import com.badlogic.gdx.utils.JsonReader;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;


public class MyGdxGame extends ApplicationAdapter {
    public static Music italy;
    SpriteBatch batch;
    Sprite iconSprite;
    public static boolean restlist = false;
    private volatile static ArrayList<FallingObject> fallingSprites = new ArrayList<FallingObject>(), clickedSprites = new ArrayList<FallingObject>();

    Texture[][] chef = new Texture[2][2];
    int chefPosition = 0;
    int flusterTimer = 0;
    int fluster = 0;
    boolean splashEnd = false;

    public static int currentPage;

    /*When you make a stage, you want to make the stage and all the things that fall under it*/
    private static Stage mainScreen;
    double animationparam = 0;
    public static BitmapFont font, big, font20,  font24;
    private static TextureAtlas buttonsAtlas;
    public static Skin buttonSkin;
    private static TextButton menuButton, optionsButton, multButton;
    private static TextureAtlas labelAtlas;
    private static Skin labelSkin;
    public static TextButton.TextButtonStyle buttonStyle;
    private static Label.LabelStyle labelStyle;
    private static Texture icon, coin, dollar, wadOfMoney, bagOfMoney, macaroni, penne, shells, spaghetti;
    public static Timer timer, t;
    private static Stage upgradeScreen;

    private static SplashScreen splashScreen;

    private static Stage optionsScreen;

    private static boolean shouldAnimate = true;

    static Label pastaDisplay;
    static Label moneyDisplay;

    static volatile PlayerSave player;
    static String[] states = {"GameView", "UpgradeMenu", "OptionsMenu", "SplashScreen"};
    static String state = states[3];
    public static NameGenerator nameGenerator;

    @Override
    public void create () {

        chef[0][0] = new Texture(Gdx.files.internal("steve/steve_1.png"));
        chef[0][1] = new Texture(Gdx.files.internal("steve/steve_2.png"));
        chef[1][0] = new Texture(Gdx.files.internal("steve/steve_1m.png"));
        chef[1][1] = new Texture(Gdx.files.internal("steve/steve_2m.png"));

        italy = Gdx.audio.newMusic(Gdx.files.internal("italy.ogg"));


                shouldAnimate=true;
        //read file if its been previously saved
        splashScreen = new SplashScreen();

        state = states[3];
        icon = new Texture(Gdx.files.internal("chefs.png"));

        dollar = new Texture(Gdx.files.internal("money/bill.png"));
        wadOfMoney = new Texture(Gdx.files.internal("money/stack.png"));
        bagOfMoney = new Texture(Gdx.files.internal("money/bag.png"));

        macaroni = new Texture(Gdx.files.internal("pasta/mac.png"));
        penne = new Texture(Gdx.files.internal("pasta/penne.png"));
        shells = new Texture(Gdx.files.internal("pasta/shell.png"));
        spaghetti = new Texture(Gdx.files.internal("pasta/spaghetti.png"));

        iconSprite = new Sprite(icon);
        iconSprite.setCenterX(Gdx.graphics.getWidth() / 2);
        iconSprite.setCenterY(Gdx.graphics.getHeight() / 2);

        player = new PlayerSave();
        FileHandle hope;
        try {
            hope = Gdx.files.local("saveGame.dat");
            Json j = new Json();
            String ha = hope.readString();
            String[] wow = ha.split("youssefspatentpendingsplitphrase");
            Gdx.app.log("wow", wow[0]);
            player.setTotalMoney(Double.parseDouble(wow[0]));
            player.setTotalPasta(Double.parseDouble(wow[1]));
            player.setPPC(Double.parseDouble(wow[2]));
            player.setRestaurants(j.fromJson(ArrayList.class, Restaurant.class, wow[3]));
            player.setTerritories(j.fromJson(ArrayList.class, Territory.class, wow[4]));
            player.setPotentialRestaurants(j.fromJson(ArrayList.class, Restaurant.class, wow[5]));
            player.setPotentialTerritories(j.fromJson(ArrayList.class, Territory.class, wow[6]));
            player.country = Boolean.parseBoolean(wow[7]);
            player.music = Boolean.parseBoolean(wow[8]);
            player.animation = Boolean.parseBoolean(wow[9]);
            double timeSinceLastLogIn =  (System.currentTimeMillis() - Long.parseLong(wow[10]))/1000;
            player.setTotalMoney(player.getTotalMoney() + player.moneyPerSecond()*timeSinceLastLogIn);
            player.setTotalPasta(player.getTotalPasta() + player.pastaPerSecond()*timeSinceLastLogIn);
        }
        catch(Exception ex) {
             Gdx.app.log("wow", ex.toString());
        }

        batch = new SpriteBatch();
        if(player == null)
            player = new PlayerSave();
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
        buttonsAtlas = new TextureAtlas("buttonWrong.atlas");
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
        buttonStyle.over = buttonSkin.getDrawable("default");
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

        multButton = new TextButton("x1", optionsStyle);
        multButton.setPosition(Gdx.graphics.getWidth()/2, 0);
        multButton.setHeight((int)(Gdx.graphics.getHeight()*0.1));
        multButton.setWidth(Gdx.graphics.getWidth()/2);

        multButton.addListener(new InputListener() {
            int mult = 1;
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if (mult == 1)
                {
                    mult = 10;
                    multButton.setText("x10");
                    RestaurantScreen(currentPage, mult);
                }
                else if (mult == 10)
                {
                    mult = 25;
                    multButton.setText("x25");
                    RestaurantScreen(currentPage, mult);
                }
                else if (mult == 25)
                {
                    mult = 100;
                    multButton.setText("x100");
                    RestaurantScreen(currentPage, mult);
                }
                else
                {
                    mult = 1;
                    multButton.setText("x1");
                    RestaurantScreen(currentPage, mult);
                }

            }
        });

        menuButton.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                player.save = true;
                if(state.equals(states[0]) || state.equals(states[2]))
                {
                    state = states[1];
                    upgradeScreen.clear();
                    labelStyle.font = font20;
                    upgradeScreen.addActor(RestaurantsMenu.restaurantsMenu(player,labelStyle,buttonStyle));
                    labelStyle.font = font24;
                    upgradeScreen.addActor(menuButton);
                    upgradeScreen.addActor(optionsButton);
                    upgradeScreen.addActor(pastaDisplay);
                    upgradeScreen.addActor(moneyDisplay);
                    Gdx.input.setInputProcessor(upgradeScreen);

                }
                else
                {
                    state = states[0];

                    mainScreen.addActor(menuButton);
                    mainScreen.addActor(optionsButton);
                    mainScreen.addActor(pastaDisplay);
                    mainScreen.addActor(moneyDisplay);
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
                player.save = true;
                if(state.equals(states[0]) || state.equals(states[1]))
                {
                    state = states[2];
                    optionsScreen.clear();
                    optionsScreen.addActor(OptionsMenu.optionsMenu(player, buttonStyle, labelStyle, upgradeScreen));
                    optionsScreen.addActor(menuButton);
                    optionsScreen.addActor(optionsButton);
                    optionsScreen.addActor(pastaDisplay);
                    optionsScreen.addActor(moneyDisplay);
                    Gdx.input.setInputProcessor(optionsScreen);

                }
                else
                {
                    state = states[0];

                    mainScreen.addActor(menuButton);
                    mainScreen.addActor(optionsButton);
                    mainScreen.addActor(pastaDisplay);
                    mainScreen.addActor(moneyDisplay);
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
        restlist = false;


        try {
            nameGenerator = new NameGenerator();
        }
        catch(IOException e)
        {
            Gdx.app.log("wow",e.toString());
        }

        italy.setLooping(true);
        if(player.music)
        {
            italy.play();
        }

        splashEnd = false;
    }


    @Override
    public void render ()
    {
        flusterTimer--;
        if(flusterTimer <= 0)
        {
            fluster = 0;
            flusterTimer = 0;
        }
        if((state.equals(states[1]) && !Gdx.input.getInputProcessor().equals(upgradeScreen)) || (!mainScreen.getActors().contains(menuButton, false) && state.equals(states[0])))
        {
            mainScreen.addActor(menuButton);
            mainScreen.addActor(optionsButton);
            mainScreen.addActor(pastaDisplay);
            mainScreen.addActor(moneyDisplay);
            Gdx.input.setInputProcessor(mainScreen);
            state = states[0];

        }
        else if(state.equals(states[2]) && !Gdx.input.getInputProcessor().equals(optionsScreen))
        {
            mainScreen.addActor(menuButton);
            mainScreen.addActor(optionsButton);
            mainScreen.addActor(pastaDisplay);
            mainScreen.addActor(moneyDisplay);
            Gdx.input.setInputProcessor(mainScreen);
            state = states[0];
        }

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
        else if(state.equals(states[0]))
        {
            restlist = false;
            mainScreen.act();
        }

        batch.begin();





        if(!state.equals(states[3]) || splashEnd) {
            iconSprite.scale((float) (0.3 + 0.05 * Math.cos(animationparam)) * Gdx.graphics.getDensity());
            iconSprite.draw(batch);
            iconSprite.rotate(-0.5f);
            iconSprite.scale(-(float) (0.3 + 0.05 * Math.cos(animationparam)) * Gdx.graphics.getDensity());
        }


        animationparam = (animationparam + 0.05) % (2*Math.PI);

        batch.end();

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

        batch.begin();

        if(Gdx.input.justTouched() && state == states[0]) {
            flusterTimer = 90;
            fluster = 1;
            float widthmult = (chefPosition == 0 ? 0.75f:0.85f);
            player.setTotalPasta(player.getTotalPasta() + player.getPPC());
            if (player.animation) {
                if (player.getPPC() > 1000000000) {
                    for (int i = 0; i < Math.min((player.getPPC() / 1000000000), 80); i += (100 - (Gdx.graphics.getDensity() * 15))) {
                        int ang = (int) (Math.random() * 360);
                        FallingObject doshSprite = new FallingObject(spaghetti, (int)(Gdx.graphics.getWidth()*widthmult), (int)(Gdx.graphics.getHeight()*0.05f), (float) (20 * Math.cos(ang)), (float) Math.abs(20 * Math.sin(ang)));
                        doshSprite.getSprite().setSize(Gdx.graphics.getWidth()*0.00833333333f,Gdx.graphics.getWidth()*0.36759259f);
                        clickedSprites.add(doshSprite);
                    }
                }
                for (int i = 0; i < (int) (player.getPPC() % 1000000000) / 1000000; i += (1000 - (Gdx.graphics.getDensity() * 150))) {
                    int ang = (int) (Math.random() * 360);
                    FallingObject doshSprite = new FallingObject(shells, (int)(Gdx.graphics.getWidth()*widthmult), (int)(Gdx.graphics.getHeight()*0.05f), (float) (20 * Math.cos(ang)), (float) Math.abs(20 * Math.sin(ang)));
                    doshSprite.getSprite().setSize(Gdx.graphics.getWidth()*0.2509259f,Gdx.graphics.getWidth()*0.15925925925f);
                    clickedSprites.add(doshSprite);
                }

                for (int i = 0; i < (int) (player.getPPC() % 1000000) / 1000; i += (1000 - (Gdx.graphics.getDensity() * 150))) {
                    int ang = (int) (Math.random() * 360);
                    FallingObject doshSprite = new FallingObject(penne, (int)(Gdx.graphics.getWidth()*widthmult), (int)(Gdx.graphics.getHeight()*0.05f), (float) (20 * Math.cos(ang)), (float) Math.abs(20 * Math.sin(ang)));
                    doshSprite.getSprite().setSize(Gdx.graphics.getWidth()*.29166666f,Gdx.graphics.getWidth()*0.2759259f);
                    clickedSprites.add(doshSprite);
                }

                for (int i = 0; i < (player.getPPC() % 1000); i += (1000 - (Gdx.graphics.getDensity() * 150))) {
                    int ang = 45 + (int)(Math.random() * 90);
                    FallingObject doshSprite = new FallingObject(macaroni, (int)(Gdx.graphics.getWidth()*widthmult), (int)(Gdx.graphics.getHeight()*0.05f), (float) (20 * Math.cos(ang)), (float) Math.abs(20 * Math.sin(ang)));
                    doshSprite.getSprite().setSize(Gdx.graphics.getWidth()*0.25925925925f,Gdx.graphics.getWidth()*0.16481481481f);
                    clickedSprites.add(doshSprite);
                }

            }
        }
            for (int i = clickedSprites.size() - 1; i >= 0; i--) {
                FallingObject f = clickedSprites.get(i);
                if (f == null || !f.draw(batch))
                    clickedSprites.remove(f);
            }


        batch.end();

        //TODO CHEF
        batch.begin();

        chefPosition = (chefPosition + (fluster == 0 ? (Math.random() <= 0.03 ? 1 : 0) : (Math.random() <= 0.1 ? 1 : 0))) % 2;
        Sprite currentChef = new Sprite(chef[fluster][chefPosition]);
        if(chefPosition == 1)
            currentChef.setSize(Gdx.graphics.getWidth() - Gdx.graphics.getWidth()*0.0979381443f,Gdx.graphics.getWidth()*1.072164f);
        else
            currentChef.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getWidth()*1.072164f);
        currentChef.setCenterX(Gdx.graphics.getWidth()/2);
        currentChef.setCenterY(Gdx.graphics.getHeight()/4);
        currentChef.draw(batch);

        batch.end();

        batch.begin();

        mainScreen.draw();

        batch.end();

        batch.begin();

        if(state.equals(states[3]))
        {
            if((Gdx.input.isTouched() && Gdx.input.getDeltaY() > Gdx.graphics.getHeight() * .05f) || splashEnd)
            {
                splashEnd = true;
                if(splashScreen.tick(batch, animationparam, true))
                    state = (states[0]);
            }
            else {
                splashScreen.tick(batch, animationparam, false);
            }
        }

        batch.end();

        if(state.equals(states[3]))
            return;



        if(fallingSprites.size() >= 1000 && Math.abs(fallingSprites.get(fallingSprites.size() - 1).getY() - fallingSprites.get(fallingSprites.size() - 600).getY()) < Gdx.graphics.getHeight() / 3)
            fallingSprites = new ArrayList<FallingObject>();

        //Main Screen Drawing

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
        upgradeScreen.addActor(pastaDisplay);
        upgradeScreen.addActor(moneyDisplay);
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
            upgradeScreen.addActor(UpgradeMenu.upgradeMenu(player,labelStyle,buttonStyle,page, 1));
        else
            upgradeScreen.addActor(UpgradeMenu.territoryUpgradeMenu(player,labelStyle,buttonStyle,page, 1));
        buttonStyle.font = font;
        labelStyle.font = big;
        upgradeScreen.addActor(menuButton);
        upgradeScreen.addActor(multButton);
        upgradeScreen.addActor(pastaDisplay);
        upgradeScreen.addActor(moneyDisplay);
    }

    public static void RestaurantScreen(int page, int mult)
    {
        upgradeScreen.clear();
        labelStyle.font = font20;
        buttonStyle.font = font20;
        if(!player.country)
            upgradeScreen.addActor(UpgradeMenu.upgradeMenu(player,labelStyle,buttonStyle,page, mult));
        else
            upgradeScreen.addActor(UpgradeMenu.territoryUpgradeMenu(player,labelStyle,buttonStyle,page, mult));
        buttonStyle.font = font;
        labelStyle.font = big;
        upgradeScreen.addActor(menuButton);
        upgradeScreen.addActor(multButton);
        upgradeScreen.addActor(pastaDisplay);
        upgradeScreen.addActor(moneyDisplay);
    }

    public static void ExpansionScreen()
    {
        upgradeScreen.clear();
        labelStyle.font = font24;
        upgradeScreen.addActor(ExpansionMenu.expansionMenu(player,labelStyle,buttonStyle));
        labelStyle.font = big;
        upgradeScreen.addActor(menuButton);
        upgradeScreen.addActor(optionsButton);
        upgradeScreen.addActor(pastaDisplay);
        upgradeScreen.addActor(moneyDisplay);
    }

    public static void TerritoryScreen()
    {
        upgradeScreen.clear();
        labelStyle.font = font24;
        upgradeScreen.addActor(TerritoryMenu.territoryMenu(player, labelStyle, buttonStyle));
        labelStyle.font = big;
        upgradeScreen.addActor(menuButton);
        upgradeScreen.addActor(optionsButton);
        upgradeScreen.addActor(pastaDisplay);
        upgradeScreen.addActor(moneyDisplay);
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
        char[] prefixes = "SsQqTBMK".toCharArray();
        String[] s = String.format("%.2f", d).split("\\.");
        String prefix = "";

        if(s.length == 0)
            return "";

        if(s.length < 2)
            s = new String[] {s[0], ""};
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
            double totalPasta = 0;
            if(!player.country) {
                for (Restaurant r : player.getRestaurants()) {
                    numPasta = 0;
                    numPasta += (r.getIncomePerSecond()/10d);
                    if(r.getIncomePerSecond() > 300000)
                    {
                        r.setStars(1);
                    }
                    if(r.getIncomePerSecond() > 3500000)
                    {
                        r.setStars(2);
                    }
                    if(r.getIncomePerSecond() > 20000000)
                    {
                        r.setStars(3);
                    }

                    numDollars += ((r.getMoneyPerSecond()/10)*player.getMichelinMultiplier());

                    r.setSum(r.getSum() + numPasta);
                    player.setTotalPasta(player.getTotalPasta() + numPasta);
                    totalPasta += numPasta;
                }
                player.setPPC(0.5 + (totalPasta/100));
            }
            else
            {
                for (Territory r : player.getTerritories()) {
                    numPasta = 0;
                    numPasta += r.tick();

                    numDollars += (r.getMoneyPerSecond()/10);

                    r.setSum(r.getSum() + numPasta);
                    player.setTotalPasta(player.getTotalPasta() + numPasta);
                    totalPasta += numPasta;
                }
            }
            if(player.animation) {
                if (numDollars > 1000000000) {
                    for (float i = 0; i < Math.min((numDollars / 100000000), 80); i += 45) { // MoneyBag
                        FallingObject doshSprite = new FallingObject(bagOfMoney, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), -(float) Math.random() * 5f, -(float) Math.random() * 3f);
                        doshSprite.getSprite().setSize(Gdx.graphics.getWidth()*(250f/1080f),Gdx.graphics.getWidth()*(318f/1080f));
                        fallingSprites.add(doshSprite);
                    }
                }
                for (float i = 0; i < (int) (numDollars % 100000000) / 100000; i += (1000 - (Gdx.graphics.getDensity() * 150))) { // Wad
                    FallingObject doshSprite = new FallingObject(bagOfMoney, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), -(float) Math.random() * 5f, -(float) Math.random() * 3f);
                    doshSprite.getSprite().setSize(Gdx.graphics.getWidth()*(250f/1080f),Gdx.graphics.getWidth()*(318f/1080f));
                    fallingSprites.add(doshSprite);
                }

                for (float i = 0; i < (int) (numDollars % 100000) / 100; i += (1000 - (Gdx.graphics.getDensity() * 150))) { // Dollar
                    FallingObject doshSprite = new FallingObject(wadOfMoney, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), -(float) Math.random() * 5f, -(float) Math.random() * 3f);
                    doshSprite.getSprite().setSize(Gdx.graphics.getWidth()*(314f/1080f),Gdx.graphics.getWidth()*(149f/1080f));
                    fallingSprites.add(doshSprite);
                }

                for (float i = 0; i < (numDollars % 100); i += (100 - (Gdx.graphics.getDensity() * 15))) { // Coin
                    FallingObject doshSprite = new FallingObject(dollar, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), -(float) Math.random() * 5f, -(float) Math.random() * 3f);
                    doshSprite.getSprite().setSize(Gdx.graphics.getWidth()*(291.0f/1080f),Gdx.graphics.getWidth()*(136f/1080f));
                    fallingSprites.add(doshSprite);
                }


                if (totalPasta > 1000000000) {
                    for (int i = 0; i < Math.min((totalPasta / 1000000000), 100); i += 45) {
                        FallingObject doshSprite = new FallingObject(spaghetti, 0, Gdx.graphics.getHeight(), (float) Math.random() * 5f, -(float) Math.random() * 3f);
                        doshSprite.getSprite().setSize(Gdx.graphics.getWidth()*0.00833333333f,Gdx.graphics.getWidth()*0.36759259f);
                        fallingSprites.add(doshSprite);
                    }
                }
                for (int i = 0; i < (int) (totalPasta % 1000000000) / 1000000; i += (1000 - (Gdx.graphics.getDensity() * 150))) {
                    FallingObject doshSprite = new FallingObject(shells, 0, Gdx.graphics.getHeight(), (float) Math.random() * 5f, -(float) Math.random() * 3f);
                    doshSprite.getSprite().setSize(Gdx.graphics.getWidth()*0.2509259f,Gdx.graphics.getWidth()*0.15925925925f);
                    fallingSprites.add(doshSprite);
                }

                for (int i = 0; i < (int) (totalPasta % 1000000) / 1000; i += (1000 - (Gdx.graphics.getDensity() * 150))) {
                    FallingObject doshSprite = new FallingObject(penne, 0, Gdx.graphics.getHeight(), (float) Math.random() * 5f, -(float) Math.random() * 3f);
                    doshSprite.getSprite().setSize(Gdx.graphics.getWidth()*.29166666f,Gdx.graphics.getWidth()*0.2759259f);
                    fallingSprites.add(doshSprite);
                }

                for (int i = 0; i < (totalPasta % 1000); i += (1000 - (Gdx.graphics.getDensity() * 150))) {
                    FallingObject doshSprite = new FallingObject(macaroni, 0, Gdx.graphics.getHeight(), (float) Math.random() * 5f, -(float) Math.random() * 3f);
                    doshSprite.getSprite().setSize(Gdx.graphics.getWidth()*0.25925925925f,Gdx.graphics.getWidth()*0.16481481481f);
                    fallingSprites.add(doshSprite);
                }
            }




            player.setTotalMoney(player.getTotalMoney() + numDollars);
            if(pastaDisplay != null) {
                if(restlist)
                    pastaDisplay.setText(String.format("%s lbs /sec", convertNumber(player.pastaPerSecond())));
                else
                    pastaDisplay.setText(String.format("%s\nlbs", convertNumber(player.getTotalPasta())));
            }
            if(moneyDisplay != null) {
                if (restlist)
                    moneyDisplay.setText(String.format("$%s /sec", convertNumber(player.moneyPerSecond())));
                else
                    moneyDisplay.setText(String.format("$%s", convertNumber(player.getTotalMoney())));
            }
        }
    }

    static class saveGame extends TimerTask
    {
        public void run() {
            //saving file
            if(!player.save)
                return;

            Date date = new Date();
            FileHandle hope = Gdx.files.local("saveGame.dat");
            Json json = new Json();
            String save = String.format("%s youssefspatentpendingsplitphrase %s youssefspatentpendingsplitphrase %s youssefspatentpendingsplitphrase", player.getTotalMoney() + "", player.getTotalPasta() + "", player.getPPC() + "");
            save += json.toJson(player.getRestaurants()) + "youssefspatentpendingsplitphrase" + json.toJson(player.getTerritories()) + "youssefspatentpendingsplitphrase" +
                    json.toJson(player.getPotentialRestaurants()) + "youssefspatentpendingsplitphrase" + json.toJson(player.getPotentialTerritories()) + "youssefspatentpendingsplitphrase" +
                    player.country + "youssefspatentpendingsplitphrase" + player.music + "youssefspatentpendingsplitphrase" + player.animation + "youssefspatentpendingsplitphrase" + System.currentTimeMillis();
            hope.writeString(save, false);

        }
    }
}

