package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;



/**
 * Created by clayb275151 on 5/15/2015.
 */
public class SplashScreen {
    private ArrayList<FallingObject> fallingSprites;
    Texture money, pasta;
    int timer;
    public SplashScreen()
    {
        fallingSprites = new ArrayList<FallingObject>();
        money = new Texture(Gdx.files.internal("dosh.png"));
        pasta = new Texture(Gdx.files.internal("pasta.png"));
        timer = 0;
    }

    public void tick(SpriteBatch batch)
    {
        if(timer % 2 == 0) {
            FallingObject pastaSprite = new FallingObject(pasta, 0, Gdx.graphics.getHeight());
            pastaSprite.scale(.75f * Gdx.graphics.getDensity());
            fallingSprites.add(pastaSprite);

            FallingObject moneySprite = new FallingObject(money, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            moneySprite.scale(.75f * Gdx.graphics.getDensity());
            fallingSprites.add(moneySprite);
        }

        for (int i = fallingSprites.size() - 1; i >= 0; i--) {
            FallingObject f = fallingSprites.get(i);
            if(f == null || !f.draw(batch))
                fallingSprites.remove(i);
        }
        if(timer < 1000)
        timer++;

        if(timer == 1000) {
            //TODO Draw Chef popping up
        }
    }
}
