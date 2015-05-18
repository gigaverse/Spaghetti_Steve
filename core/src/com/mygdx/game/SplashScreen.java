package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;



/**
 * Created by clayb275151 on 5/15/2015.
 */
public class SplashScreen {
    private ArrayList<FallingObject> fallingSprites;
    Texture money, pasta, c;
    float yAccel, yVel, y, rot;
    boolean half;
    Sprite chef;
    int timer;
    public SplashScreen()
    {
        fallingSprites = new ArrayList<FallingObject>();
        money = new Texture(Gdx.files.internal("dosh.png"));
        pasta = new Texture(Gdx.files.internal("pasta.png"));
        c = new Texture(Gdx.files.internal("chef.png"));
        y = -Gdx.graphics.getHeight()/4;
        yAccel = Gdx.graphics.getHeight()/(1920*2f);
        chef = new Sprite(c);
        chef.scale(Gdx.graphics.getDensity());
        chef.setCenterX(Gdx.graphics.getWidth()/2);
        chef.setCenterY(y);
        timer = 0;
        yVel = Gdx.graphics.getHeight()/48;

        rot = -5.7f;
    }

    public void tick(SpriteBatch batch, double animationparam)
    {
        if(timer % 4 == 0) {
            FallingObject pastaSprite = new FallingObject(pasta, (int)(-Gdx.graphics.getWidth()*0.1), (int)(Gdx.graphics.getHeight()*0.75), (float)Math.random()*10f, 15);
            pastaSprite.scale(.75f * Gdx.graphics.getDensity());
            fallingSprites.add(pastaSprite);

            FallingObject moneySprite = new FallingObject(money, (int)(Gdx.graphics.getWidth()*1.1), (int)(Gdx.graphics.getHeight()*0.75), -(float)Math.random()*10f, 15);
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
                if (Math.abs(yVel) < Gdx.graphics.getHeight() / 144) {
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
