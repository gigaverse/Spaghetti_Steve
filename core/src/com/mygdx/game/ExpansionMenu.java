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
 * Created by mahmo266317 on 5/7/2015.
 */
public class ExpansionMenu {
    public static Table expansionMenu(final PlayerSave player, Label.LabelStyle labelStyle, TextButton.TextButtonStyle style)
    {
        Table scrollTable = new Table();
        scrollTable.top();

        final ScrollPane scroller = new ScrollPane(scrollTable);
        final Table table = new Table();
        table.setFillParent(true);

        Label label = new Label("Select A Restaurant to Claim...", labelStyle);
        label.setAlignment(Align.center);
        label.setWrap(false);
        label.setHeight((int)(Gdx.graphics.getHeight()*0.1));
        label.setWidth(Gdx.graphics.getWidth());

        table
                .add(label)
                .padTop((int)(Gdx.graphics.getHeight()*0.025))
                .padLeft((int) (Gdx.graphics.getWidth() * 0.025))
                .padRight((int) (Gdx.graphics.getWidth() * 0.025))
                .width((int) (Gdx.graphics.getWidth() * 0.95))
                .height(label.getHeight());

        table.row();

        //TODO - Add Restaurants to take
        if(player.getPotential().size() == 0)
        {
            populatePotential(player.getPotential());
        }

        for(int i = 0; i < player.getPotential().size(); i++)
        {
            final int j = i;
            final Restaurant r = player.getPotential().get(i);
            final Label counter = new Label(String.format("Cost\n$%d", (int)r.getCost()), labelStyle);
            counter.setAlignment(Align.center);
            counter.setWrap(true);
            counter.setHeight((int)(Gdx.graphics.getHeight()*0.1));
            counter.setWidth(Gdx.graphics.getWidth());

            final TextButton upgradeButton = new TextButton(String.format("%s", r.getName()),style);
            upgradeButton.setHeight((int)(Gdx.graphics.getHeight()*0.1));
            upgradeButton.setWidth(Gdx.graphics.getWidth());

            upgradeButton.addListener(new InputListener() {
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }

                public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                    if(player.getTotal() >= r.getCost()) {
                        player.setTotal(player.getTotal() - r.getCost());
                        player.getPotential().remove(j);
                        player.getRestaurants().add(r);
                        MyGdxGame.ExpansionScreen();
                    }
                }
            });

            scrollTable.add(upgradeButton)
                    .expandX()
                    .padTop((int)(Gdx.graphics.getHeight()*0.0125))
                    .padBottom((int)(Gdx.graphics.getHeight()*0.0125))
                    .width((int)(Gdx.graphics.getWidth()*0.6))
                    .padLeft((int)(Gdx.graphics.getWidth()*0.025))
                    .padRight((int)(Gdx.graphics.getWidth()*0.025))
                    .height(upgradeButton.getHeight());

            scrollTable.add(counter)
                    .padTop((int)(Gdx.graphics.getHeight()*0.0125))
                    .padBottom((int)(Gdx.graphics.getHeight()*0.0125))
                    .padLeft((int)(Gdx.graphics.getWidth()*0.025))
                    .padRight((int)(Gdx.graphics.getWidth()*0.025))
                    .width((int)(Gdx.graphics.getWidth()*0.3))
                    .height(counter.getHeight());

            scrollTable.row();
        }

        table.add(scroller).size(Gdx.graphics.getWidth(), (int) (Gdx.graphics.getHeight() * 0.675));
        table.row();

        return table;
    }

    private static void populatePotential(ArrayList<Restaurant> potential)
    {
        Restaurant r = new Restaurant();
        r.setName("Filthy Frank's Fettuccine Frenzy");
        r.setCost(250);
        potential.add(r);

        Restaurant r2 = new Restaurant();
        r2.setName("Billy Bob's Burger Blast");
        r2.setCost(3750);
        potential.add(r2);

        Restaurant r3 = new Restaurant();
        r3.setName("Penne Penny's Pasta Palace");
        r3.setCost(50000);
        potential.add(r3);
    }
}
