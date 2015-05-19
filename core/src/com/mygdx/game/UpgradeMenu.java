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
    public static Table upgradeMenu(final PlayerSave player, Label.LabelStyle labelStyle, TextButton.TextButtonStyle style, final int pageNumber)
    {
        final String[] states = {"Upgrades", "Financial", "Minions", "Quality"};

        //Created labels and added "buying" an upgrade

        Table scrollTable = new Table();
        scrollTable.top();

        final ScrollPane scroller = new ScrollPane(scrollTable);
        final Table table = new Table();
        table.setFillParent(true);
        Table table2 = new Table();

        Label label = new Label(states[pageNumber], labelStyle);
        label.setAlignment(Align.center);
        label.setWrap(false);
        label.setHeight((int) (Gdx.graphics.getHeight() * 0.1));
        label.setWidth(Gdx.graphics.getWidth());

        TextButton leftButton = new TextButton("<-", style);
        leftButton.setHeight((int) (Gdx.graphics.getHeight() * 0.1));
        leftButton.setWidth((int) (Gdx.graphics.getWidth() * 0.175));

        leftButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                int n = pageNumber;
                n--;
                if(n < 0)
                    n = states.length + n;
                n %= states.length;
                if(n == 2 && !player.country)
                    n--;
                MyGdxGame.RestaurantScreen(n);
            }
        });

        TextButton rightButton = new TextButton("->", style);
        rightButton.setHeight((int) (Gdx.graphics.getHeight() * 0.1));
        rightButton.setWidth((int) (Gdx.graphics.getWidth() * 0.175));

        rightButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                int n = pageNumber;
                n++;
                if (n < 0)
                    n = states.length + n;
                n %= states.length;
                if(n == 2 && !player.country)
                    n++;
                MyGdxGame.RestaurantScreen(n);
            }
        });





        table2
                .add(leftButton)
                .padLeft((int)(Gdx.graphics.getWidth()*0.025))
                .padRight((int)(Gdx.graphics.getWidth()*0.025))
                .width((int)(Gdx.graphics.getWidth()*0.175))
                .height(label.getHeight());


        table2
                .add(label)
                .padLeft((int)(Gdx.graphics.getWidth()*0.025))
                .padRight((int)(Gdx.graphics.getWidth()*0.025))
                .width((int)(Gdx.graphics.getWidth()*0.5))
                .height(label.getHeight());

        table2
                .add(rightButton)
                .padLeft((int)(Gdx.graphics.getWidth()*0.025))
                .padRight((int) (Gdx.graphics.getWidth() * 0.025))
                .width((int) (Gdx.graphics.getWidth() * 0.175))
                .height(label.getHeight());

        table.add(table2)
             .expandX()
             .height(label.getHeight());
        table.row();

        if(pageNumber == 0) {
            //THIS IS THE LOCATION IN WHICH YOU DECIDE WHEN YOU SEE UPGRADES
            long pastaMakerLevel = player.getCurrentRestaurant().getUnits().get(0).getAmount() + 1;

            for (int i = 0; i < Math.min(player.getCurrentRestaurant().getUpgrades().size(),pastaMakerLevel*3); i++) {
                final Upgrade u = player.getCurrentRestaurant().getUpgrade(i);
                final Label counter = new Label("Amount\n" +u.getAmount(), labelStyle);
                counter.setAlignment(Align.center);
                counter.setWrap(true);
                counter.setHeight((int) (Gdx.graphics.getHeight() * 0.1));
                counter.setWidth(Gdx.graphics.getWidth());

                final TextButton upgradeButton = new TextButton(String.format("%s\n%d lbs", u.getName(), u.getCost()), style);
                upgradeButton.setHeight((int) (Gdx.graphics.getHeight() * 0.1));
                upgradeButton.setWidth(Gdx.graphics.getWidth());

                upgradeButton.addListener(new InputListener() {
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }

                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        if (player.getCurrentRestaurant().sum >= u.getCost()) {
                            player.getCurrentRestaurant().sum -= u.getCost();
                            u.add();
                            upgradeButton.setText(String.format("%s\n%s lbs", u.getName(), MyGdxGame.convertNumber(u.getCost())));
                            counter.setText("Amount\n" +(u.getAmount()));
                        }
                    }
                });

                scrollTable.add(upgradeButton)
                        .expandX()
                        .padTop((int) (Gdx.graphics.getHeight() * 0.0125))
                        .padBottom((int) (Gdx.graphics.getHeight() * 0.0125))
                        .width((int) (Gdx.graphics.getWidth() * 0.6))
                        .padLeft((int) (Gdx.graphics.getWidth() * 0.025))
                        .padRight((int) (Gdx.graphics.getWidth() * 0.025))
                        .height(upgradeButton.getHeight());

                scrollTable.add(counter)
                        .padTop((int) (Gdx.graphics.getHeight() * 0.0125))
                        .padBottom((int) (Gdx.graphics.getHeight() * 0.0125))
                        .padLeft((int) (Gdx.graphics.getWidth() * 0.025))
                        .padRight((int) (Gdx.graphics.getWidth() * 0.025))
                        .width((int) (Gdx.graphics.getWidth() * 0.3))
                        .height(counter.getHeight());

                scrollTable.row();
            }
        }
        else
        {
            for (int i = 0; i < player.getCurrentRestaurant().getUnits().size(); i++) {
                final Unit u = player.getCurrentRestaurant().getUnit(i);
                if(u.getType() == null)
                    continue;
                if(pageNumber == 1 && !u.getType().equals("money"))
                    continue;
                if(pageNumber == 2 && !u.getType().equals("minion"))
                    continue;
                if(pageNumber == 3 && !u.getType().equals("quality"))
                    continue;

                final Label counter = new Label("Amount\n" +u.getAmount(), labelStyle);
                if(u.getType().equals("quality"))
                    counter.setText("Level\n" +(u.getAmount()+1));
                counter.setAlignment(Align.center);
                counter.setWrap(true);
                counter.setHeight((int) (Gdx.graphics.getHeight() * 0.1));
                counter.setWidth(Gdx.graphics.getWidth());

                final TextButton upgradeButton = new TextButton(String.format("%s\n%d lbs", u.getName(), u.getCost()), style);
                upgradeButton.setHeight((int) (Gdx.graphics.getHeight() * 0.1));
                upgradeButton.setWidth(Gdx.graphics.getWidth());

                upgradeButton.addListener(new InputListener() {
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }

                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        if (player.getCurrentRestaurant().sum >= u.getCost()) {
                            player.getCurrentRestaurant().sum -= u.getCost();
                            u.add();
                            upgradeButton.setText(String.format("%s\n%s lbs", u.getName(), MyGdxGame.convertNumber(u.getCost())));
                            counter.setText("Amount:\n" +(u.getAmount()));
                            if(u.getType().equals("quality"))
                                counter.setText("Level:\n" +(u.getAmount()+1));
                        }
                    }
                });

                scrollTable.add(upgradeButton)
                        .expandX()
                        .padTop((int) (Gdx.graphics.getHeight() * 0.0125))
                        .padBottom((int) (Gdx.graphics.getHeight() * 0.0125))
                        .width((int) (Gdx.graphics.getWidth() * 0.6))
                        .padLeft((int) (Gdx.graphics.getWidth() * 0.025))
                        .padRight((int) (Gdx.graphics.getWidth() * 0.025))
                        .height(upgradeButton.getHeight());

                scrollTable.add(counter)
                        .padTop((int) (Gdx.graphics.getHeight() * 0.0125))
                        .padBottom((int) (Gdx.graphics.getHeight() * 0.0125))
                        .padLeft((int) (Gdx.graphics.getWidth() * 0.025))
                        .padRight((int) (Gdx.graphics.getWidth() * 0.025))
                        .width((int) (Gdx.graphics.getWidth() * 0.3))
                        .height(counter.getHeight());

                scrollTable.row();
            }
        }

        table.add(scroller).size(Gdx.graphics.getWidth(),(int)(Gdx.graphics.getHeight()*0.675)).setActorY((int) (Gdx.graphics.getHeight() * 0.1));

        return table;

    }
}
