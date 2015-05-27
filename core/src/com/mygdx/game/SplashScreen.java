package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;



/**
 * Created by clayb275151 on 5/15/2015.
 */
public class SplashScreen {
    private ArrayList<FallingObject> fallingSprites;
    Texture money, pasta, c;
    float yAccel, yVel, y, rot, scale;
    boolean half;
    Sprite chef;
    int timer;
    BitmapFont titleF;
    BitmapFont titleFs;

    public static Texture backgroundTexture;
    public static Sprite backgroundSprite;

    public SplashScreen()
    {
        MyGdxGame.hide();
        fallingSprites = new ArrayList<FallingObject>();
        money = new Texture(Gdx.files.internal("money/bag.png"));
        pasta = new Texture(Gdx.files.internal("pasta/penne.png"));
        c = new Texture(Gdx.files.internal("chef.png"));
        y = -Gdx.graphics.getHeight()/4;
        yAccel = Gdx.graphics.getHeight()/(1920*2f);
        chef = new Sprite(c);
        chef.scale(Gdx.graphics.getDensity());
        chef.setCenterX(Gdx.graphics.getWidth()/2);
        chef.setCenterY(y);
        timer = 0;
        scale = 2/3.0f;

        backgroundTexture = new Texture("SplashscreenKitchen.jpg");
        backgroundSprite = new Sprite(backgroundTexture);
        backgroundSprite.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        Gdx.app.log("wow", Gdx.graphics.getDensity() + "");
        yVel = Gdx.graphics.getHeight()/48;

        rot = -5.7f;

        //create font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("ka1.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int)(32*Gdx.graphics.getDensity());
        titleF = generator.generateFont(parameter);
        parameter.size = (int)(24*Gdx.graphics.getDensity());
        titleFs = generator.generateFont(parameter);
        titleF.setColor(0f,0f,0f,255f);
        titleFs.setColor(0f,0f,0f,255f);
    }

    public void tick(SpriteBatch batch, double animationparam)
    {
        backgroundSprite.draw(batch);
        if(timer % 5 == 0) {
            FallingObject pastaSprite = new FallingObject(pasta, (int)(-Gdx.graphics.getWidth()*0.1), (int)((Math.random()*Gdx.graphics.getHeight()*0.15)+Gdx.graphics.getHeight()*0.60), (float)Math.random()*10f, 15);
            pastaSprite.scale(.75f * Gdx.graphics.getDensity());
            fallingSprites.add(pastaSprite);

            FallingObject moneySprite = new FallingObject(money, (int)(Gdx.graphics.getWidth()*1.1), (int)((Math.random()*Gdx.graphics.getHeight()*0.15)+Gdx.graphics.getHeight()*0.60), -(float)Math.random()*10f, 15);
            moneySprite.scale(.75f * Gdx.graphics.getDensity());
            fallingSprites.add(moneySprite);
        }

        for (int i = fallingSprites.size() - 1; i >= 0; i--) {
            FallingObject f = fallingSprites.get(i);
            if(f == null || !f.draw(batch))
                fallingSprites.remove(i);
        }
        timer++;
        if(timer >= 25) {
            //TODO Draw Chef popping up
            chef.scale((float) (0.3 + 0.1 * Math.cos(animationparam)) * Gdx.graphics.getDensity());
            chef.draw(batch);
            chef.rotate(rot);
            chef.setCenterY(y);
            chef.scale(-(float) (0.3 + 0.1 * Math.cos(animationparam)) * Gdx.graphics.getDensity());
            y = y + yVel;
            if (!half)
                rot += 0.05;
            if (y >= Gdx.graphics.getHeight() / 2)
                half = true;
            if (half && y <= Gdx.graphics.getHeight() / 2) {
                yVel = -yVel;
                if (Math.abs(yVel) < Gdx.graphics.getHeight() / (scale*Gdx.graphics.getDensity())) {

                    //Title Shennigans, may or may not work currently
                    titleF.draw(batch, " Spaghetti\nSmackdown", Gdx.graphics.getWidth()*.15f, Gdx.graphics.getHeight()*.93f);
                    titleFs.draw(batch, "Slide Down To\n      V Begin V", Gdx.graphics.getWidth()*.17f, Gdx.graphics.getHeight()*.15f);

                    yVel = 0;
                    yAccel = 0;
                    rot = 0;
                }
            }
            yVel -= yAccel;
        }

        if(timer >= 1050)
            timer -= 1000;
    }
}
