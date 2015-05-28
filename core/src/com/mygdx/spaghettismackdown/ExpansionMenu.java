package com.mygdx.spaghettismackdown;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;

import java.io.IOException;

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

        if(player.country)
        {
            label.setText("Select a Territory to Invade");
        }

        table
                .add(label)
                .padTop((int)(Gdx.graphics.getHeight()*0.025))
                .padLeft((int) (Gdx.graphics.getWidth() * 0.025))
                .padRight((int) (Gdx.graphics.getWidth() * 0.025))
                .width((int) (Gdx.graphics.getWidth() * 0.95))
                .height(label.getHeight());

        table.row();
            if(player.getRestaurants().size() + player.getPotentialRestaurants().size() <= 10) {
                try {
                    populatePotential(player);
                } catch (Exception io) {

                }
            }

        if(player.getTerritories().size() + player.getPotentialTerritories().size() <= 10 && player.country) {
            try {
                populatePotential(player);
            } catch (Exception io) {

            }
        }

        if(!player.country) {
            for (int i = 0; i < player.getPotentialRestaurants().size(); i++) {
                final int j = i;
                final Restaurant r = player.getPotentialRestaurants().get(i);
                final Label counter = new Label(String.format("Cost\n$%s", MyGdxGame.convertNumber(r.getCost())), labelStyle);
                counter.setAlignment(Align.center);
                counter.setWrap(true);
                counter.setHeight((int) (Gdx.graphics.getHeight() * 0.1));
                counter.setWidth(Gdx.graphics.getWidth());

                final TextButton upgradeButton = new TextButton(String.format("%s", r.getName()), style);
                upgradeButton.setHeight((int) (Gdx.graphics.getHeight() * 0.1));
                upgradeButton.setWidth(Gdx.graphics.getWidth());

                upgradeButton.addListener(new InputListener() {
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }

                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        player.save = false;
                        if (player.getTotalMoney() >= r.getCost()) {
                            player.setTotalMoney(player.getTotalMoney() - r.getCost());
                            player.getPotentialRestaurants().remove(j);
                            player.getRestaurants().add(r);
                            MyGdxGame.ExpansionScreen();
                        }
                        player.save = true;
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
            Gdx.app.log("wow", player.getPotentialTerritories().size()+"");
            for (int i = 0; i < player.getPotentialTerritories().size(); i++) {
                final int j = i;
                final Territory r = player.getPotentialTerritories().get(i);
                final Label counter = new Label(String.format("Cost\n$%s", MyGdxGame.convertNumber(r.getCost())), labelStyle);
                counter.setAlignment(Align.center);
                counter.setWrap(true);
                counter.setHeight((int) (Gdx.graphics.getHeight() * 0.1));
                counter.setWidth(Gdx.graphics.getWidth());

                final TextButton upgradeButton = new TextButton(String.format("%s", r.getName()), style);
                upgradeButton.setHeight((int) (Gdx.graphics.getHeight() * 0.1));
                upgradeButton.setWidth(Gdx.graphics.getWidth());

                upgradeButton.addListener(new InputListener() {
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        return true;
                    }

                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        player.save = false;
                        MyGdxGame.t.cancel();
                        if (player.getTotalMoney() >= r.getCost()) {
                            player.setTotalMoney(player.getTotalMoney() - r.getCost());
                            player.getPotentialTerritories().remove(j);
                            player.getTerritories().add(r);
                            MyGdxGame.ExpansionScreen();
                        }
                        player.save = true;
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

        table.add(scroller).size(Gdx.graphics.getWidth(), (int) (Gdx.graphics.getHeight() * 0.675));
        table.row();

        return table;
    }

    private static void populatePotential(PlayerSave player) throws IOException {
        if (!player.country) {
            if(player.getPotentialRestaurants().size() +  player.getRestaurants().size()>= 10)
                return;
            String[] suffixes = {" Pasta Palace",
                    " Spaghetti Stand",
                    " Fettuccine Frenzy",
                    " Angel Hair Arena",
                    " Penne Penthouse",
                    " Ravioli Rave",
                    " Macaroni Mansion",
                    " Cannelloni Castle",
                    " Farfalle Fair",
                    " Cavatelli Cart",
                    " Merletti Mart",
                    " Dischi depository",
                    " Orzo Outlet",
                    " Tortelli Tower"};

            for (int i = 0; i < 10 - player.getRestaurants().size(); i++) {
                Restaurant r = new Restaurant();
                r.setName(MyGdxGame.nameGenerator.pull() + "'s\n" + suffixes[(int) (Math.random() * suffixes.length)]);
                r.setCost(Math.pow(((i + 1) * 1000), 2.3));
                player.getPotentialRestaurants().add(r);
            }
        }
        else
        {
            if(player.getPotentialTerritories().size() >= 15)
                return;
            String[] suffixes = {" Pasta Palace",
                    " Spaghetti State",
                    " Fettuccine Field",
                    " Angel Hair Authorization",
                    " Penne Populace",
                    " Ravioli Region",
                    " Macaroni Mansion",
                    " Cannelloni Commonwealth",
                    " Cavatelli Country",
                    " Merletti Management",
                    " Dischi Dominion",
                    " Orzo Oligarchy",
                    " Tortelli Territory"};

            for (int i = 0; i < 15 - player.getTerritories().size(); i++) {
                Territory r = new Territory();
                r.setName(MyGdxGame.nameGenerator.pull() + "'s\n" + suffixes[(int) (Math.random() * suffixes.length)]);
                r.setCost(Math.pow(((i + 1) * 10000000), 2.3));
                player.getPotentialTerritories().add(r);
            }
        }
    }

}
