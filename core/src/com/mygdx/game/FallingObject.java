package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by mahmo266317 on 5/14/2015.
 */
public class FallingObject {
    private Sprite sprite;
    private int x, y;
    private float rot,xVel, yVel;
    public FallingObject(Texture t, int x, int y, float xVel, float yVel)
    {
        sprite = new Sprite(t);
        this.x = x;
        this.y = y;
        rot = (float)(Math.random()*2 - 1);
        this.xVel = xVel;
        this.yVel = -1*yVel;

    }

    public boolean draw(SpriteBatch batch)
    {
        if(y <= 0)
            return false;
        sprite.setCenterX(x);
        sprite.setCenterY(y);
        x += xVel;
        y -= yVel;
        yVel += 0.2;
        sprite.draw(batch);
        sprite.rotate(rot);
        return true;
    }

    public void scale(float f)
    {
        sprite.scale(f);
    }

}
