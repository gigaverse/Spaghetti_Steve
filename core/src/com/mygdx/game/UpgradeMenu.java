package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;

/**
 * Created by mahmo266317 on 5/1/2015.
 */
public class UpgradeMenu {
    public static Table upgradeMenu(final ArrayList<Restaurant> restaurants,final int currentRestaurant, Label.LabelStyle labelStyle, TextButton.TextButtonStyle style)
    {
        //Created labels and added "buying" an upgrade
        Table scrollTable = new Table();
        scrollTable.center();

        for(int i = 0; i < restaurants.get(currentRestaurant).getList().size(); i++)
        {
            final Upgrade u = restaurants.get(currentRestaurant).get(i+1);

            final Label counter = new Label(u.getAmount() + "", labelStyle);
            counter.setAlignment(Align.center);
            counter.setWrap(true);
            counter.setHeight((int)(Gdx.graphics.getHeight()*0.1));
            counter.setWidth(Gdx.graphics.getWidth());

            final TextButton upgradeButton = new TextButton(String.format("%s : %d lbs", u.getName(), u.getCost()), style);
            upgradeButton.setHeight((int)(Gdx.graphics.getHeight()*0.1));
            upgradeButton.setWidth(Gdx.graphics.getWidth());

            upgradeButton.addListener(new InputListener() {
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }

                public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                    if(restaurants.get(currentRestaurant).sum >= u.getCost()) {
                        restaurants.get(currentRestaurant).sum -= u.getCost();
                        u.add();
                        upgradeButton.setText(String.format("%s : %d lbs", u.getName(), u.getCost()));
                        counter.setText(u.getAmount() + "");
                    }
                }
            });

            scrollTable.add(upgradeButton).expandX().padTop((int)(Gdx.graphics.getHeight()*0.0125)).padBottom((int)(Gdx.graphics.getHeight()*0.0125)).width((int)(Gdx.graphics.getWidth()*0.6)).padLeft((int)(Gdx.graphics.getWidth()*0.025)).padRight((int)(Gdx.graphics.getWidth()*0.025)).height(upgradeButton.getHeight());
            scrollTable.add(counter).padTop((int)(Gdx.graphics.getHeight()*0.0125)).padBottom((int)(Gdx.graphics.getHeight()*0.0125)).padLeft((int)(Gdx.graphics.getWidth()*0.025)).padRight((int)(Gdx.graphics.getWidth()*0.025)).width((int)(Gdx.graphics.getWidth()*0.3)).height(counter.getHeight());
            scrollTable.row();
        }

        final ScrollPane scroller = new ScrollPane(scrollTable);
        final Table table = new Table();
        table.setFillParent(true);
        table.add(scroller).size(Gdx.graphics.getWidth(),(int)(Gdx.graphics.getHeight()*0.8)).setActorY((int) (Gdx.graphics.getHeight() * 0.1));
        return table;
    }
}
