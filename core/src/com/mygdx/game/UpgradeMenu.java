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
        final Label text1 = new Label(restaurants.get(currentRestaurant).get("1").getAmount() + "", labelStyle);
        text1.setAlignment(Align.center);
        text1.setWrap(true);

        final TextButton button1 = new TextButton(String.format("%s : %d lbs", restaurants.get(currentRestaurant).get("1").getName(), restaurants.get(currentRestaurant).get("1").getCost()), style);
        button1.setHeight((int)(Gdx.graphics.getHeight()*0.1));
        button1.setWidth(Gdx.graphics.getWidth());

        button1.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if(restaurants.get(currentRestaurant).sum >= restaurants.get(currentRestaurant).get("1").getCost()) {
                    restaurants.get(currentRestaurant).sum -= restaurants.get(currentRestaurant).get("1").getCost();
                    restaurants.get(currentRestaurant).get("1").add();
                    button1.setText(String.format("%s : %d lbs", restaurants.get(currentRestaurant).get("1").getName(), restaurants.get(currentRestaurant).get("1").getCost()));
                    text1.setText(restaurants.get(currentRestaurant).get("1").getAmount() + "");
                }
            }
        });

        final Label text2 = new Label(restaurants.get(currentRestaurant).get("2").getAmount() + "", labelStyle);
        text2.setAlignment(Align.center);
        text2.setWrap(true);

        final TextButton button2 = new TextButton(String.format("%s : %d lbs", restaurants.get(currentRestaurant).get("2").getName(), restaurants.get(currentRestaurant).get("2").getCost()), style);
        button2.setHeight((int)(Gdx.graphics.getHeight()*0.1));
        button2.setWidth(Gdx.graphics.getWidth());

        button2.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if(restaurants.get(currentRestaurant).sum >= restaurants.get(currentRestaurant).get("2").getCost()) {
                    restaurants.get(currentRestaurant).sum -= restaurants.get(currentRestaurant).get("2").getCost();
                    restaurants.get(currentRestaurant).get("2").add();
                    button2.setText(String.format("%s : %d lbs", restaurants.get(currentRestaurant).get("2").getName(), restaurants.get(currentRestaurant).get("2").getCost()));
                    text2.setText(restaurants.get(currentRestaurant).get("2").getAmount() + "");
                }
            }
        });

        final Label text3 = new Label(restaurants.get(currentRestaurant).get("3").getAmount() + "", labelStyle);
        text3.setAlignment(Align.center);
        text3.setWrap(true);

        final TextButton button3 = new TextButton(String.format("%s costs %d lbs of pasta", restaurants.get(currentRestaurant).get("3").getName(), restaurants.get(currentRestaurant).get("3").getCost()), style);
        button3.setHeight((int)(Gdx.graphics.getHeight()*0.1));
        button3.setWidth(Gdx.graphics.getWidth());

        button3.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if(restaurants.get(currentRestaurant).sum >= restaurants.get(currentRestaurant).get("3").getCost()) {
                    restaurants.get(currentRestaurant).sum -= restaurants.get(currentRestaurant).get("3").getCost();
                    restaurants.get(currentRestaurant).get("3").add();
                    button3.setText(String.format("%s : %d lbs", restaurants.get(currentRestaurant).get("3").getName(), restaurants.get(currentRestaurant).get("3").getCost()));
                    text3.setText(restaurants.get(currentRestaurant).get("3").getAmount() + "");
                }
            }
        });

        final Label text4 = new Label(restaurants.get(currentRestaurant).get("4").getAmount() + "", labelStyle);
        text4.setAlignment(Align.center);
        text4.setWrap(true);

        final TextButton button4 = new TextButton(String.format("%s : %d lbs", restaurants.get(currentRestaurant).get("4").getName(), restaurants.get(currentRestaurant).get("4").getCost()), style);
        button4.setHeight((int)(Gdx.graphics.getHeight()*0.1));
        button4.setWidth(Gdx.graphics.getWidth());

        button4.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if(restaurants.get(currentRestaurant).sum >= restaurants.get(currentRestaurant).get("4").getCost()) {
                    restaurants.get(currentRestaurant).sum -= restaurants.get(currentRestaurant).get("4").getCost();
                    restaurants.get(currentRestaurant).get("4").add();
                    button4.setText(String.format("%s : %d lbs", restaurants.get(currentRestaurant).get("4").getName(), restaurants.get(currentRestaurant).get("4").getCost()));
                    text4.setText(restaurants.get(currentRestaurant).get("4").getAmount() + "");
                }
            }
        });

        final Label text5 = new Label(restaurants.get(currentRestaurant).get("5").getAmount() + "", labelStyle);
        text5.setAlignment(Align.center);
        text5.setWrap(true);

        final TextButton button5 = new TextButton(String.format("%s : %d lbs", restaurants.get(currentRestaurant).get("5").getName(), restaurants.get(currentRestaurant).get("5").getCost()), style);
        button5.setHeight((int)(Gdx.graphics.getHeight()*0.1));
        button5.setWidth(Gdx.graphics.getWidth());

        button5.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if(restaurants.get(currentRestaurant).sum >= restaurants.get(currentRestaurant).get("5").getCost()) {
                    restaurants.get(currentRestaurant).sum -= restaurants.get(currentRestaurant).get("5").getCost();
                    restaurants.get(currentRestaurant).get("5").add();
                    button5.setText(String.format("%s : %d lbs", restaurants.get(currentRestaurant).get("5").getName(), restaurants.get(currentRestaurant).get("5").getCost()));
                    text5.setText(restaurants.get(currentRestaurant).get("5").getAmount() + "");
                }
            }
        });

        final Label text6 = new Label(restaurants.get(currentRestaurant).get("6").getAmount() + "", labelStyle);
        text6.setAlignment(Align.center);
        text6.setWrap(true);

        final TextButton button6 = new TextButton(String.format("%s : %d lbs", restaurants.get(currentRestaurant).get("6").getName(), restaurants.get(currentRestaurant).get("6").getCost()), style);
        button6.setHeight((int)(Gdx.graphics.getHeight()*0.1));
        button6.setWidth(Gdx.graphics.getWidth());

        button6.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if(restaurants.get(currentRestaurant).sum >= restaurants.get(currentRestaurant).get("6").getCost()) {
                    restaurants.get(currentRestaurant).sum -= restaurants.get(currentRestaurant).get("6").getCost();
                    restaurants.get(currentRestaurant).get("6").add();
                    button6.setText(String.format("%s : %d lbs", restaurants.get(currentRestaurant).get("6").getName(), restaurants.get(currentRestaurant).get("6").getCost()));
                    text6.setText(restaurants.get(currentRestaurant).get("6").getAmount() + "");
                }
            }
        });

        final Label text7 = new Label(restaurants.get(currentRestaurant).get("7").getAmount() + "", labelStyle);
        text7.setAlignment(Align.center);
        text7.setWrap(true);

        final TextButton button7 = new TextButton(String.format("%s : %d lbs", restaurants.get(currentRestaurant).get("7").getName(), restaurants.get(currentRestaurant).get("7").getCost()), style);
        button7.setHeight((int)(Gdx.graphics.getHeight()*0.1));
        button7.setWidth(Gdx.graphics.getWidth());

        button7.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if(restaurants.get(currentRestaurant).sum >= restaurants.get(currentRestaurant).get("7").getCost()) {
                    restaurants.get(currentRestaurant).sum -= restaurants.get(currentRestaurant).get("7").getCost();
                    restaurants.get(currentRestaurant).get("7").add();
                    button7.setText(String.format("%s : %d lbs", restaurants.get(currentRestaurant).get("7").getName(), restaurants.get(currentRestaurant).get("7").getCost()));
                    text7.setText(restaurants.get(currentRestaurant).get("7").getAmount() + "");
                }
            }
        });

        final Label text8 = new Label(restaurants.get(currentRestaurant).get("8").getAmount() + "", labelStyle);
        text8.setAlignment(Align.center);
        text8.setWrap(true);

        final TextButton button8 = new TextButton(String.format("%s : %d lbs", restaurants.get(currentRestaurant).get("8").getName(), restaurants.get(currentRestaurant).get("8").getCost()), style);
        button8.setHeight((int)(Gdx.graphics.getHeight()*0.1));
        button8.setWidth(Gdx.graphics.getWidth());

        button8.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if(restaurants.get(currentRestaurant).sum >= restaurants.get(currentRestaurant).get("8").getCost()) {
                    restaurants.get(currentRestaurant).sum -= restaurants.get(currentRestaurant).get("8").getCost();
                    restaurants.get(currentRestaurant).get("8").add();
                    button8.setText(String.format("%s : %d lbs", restaurants.get(currentRestaurant).get("8").getName(), restaurants.get(currentRestaurant).get("8").getCost()));
                    text8.setText(restaurants.get(currentRestaurant).get("8").getAmount() + "");
                }
            }
        });

        final Label text9 = new Label(restaurants.get(currentRestaurant).get("9").getAmount() + "", labelStyle);
        text9.setAlignment(Align.center);
        text9.setWrap(true);

        final TextButton button9 = new TextButton(String.format("%s : %d lbs", restaurants.get(currentRestaurant).get("9").getName(), restaurants.get(currentRestaurant).get("9").getCost()), style);
        button9.setHeight((int)(Gdx.graphics.getHeight()*0.1));
        button9.setWidth(Gdx.graphics.getWidth());

        button9.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if(restaurants.get(currentRestaurant).sum >= restaurants.get(currentRestaurant).get("9").getCost()) {
                    restaurants.get(currentRestaurant).sum -= restaurants.get(currentRestaurant).get("9").getCost();
                    restaurants.get(currentRestaurant).get("9").add();
                    button9.setText(String.format("%s : %d lbs", restaurants.get(currentRestaurant).get("9").getName(), restaurants.get(currentRestaurant).get("9").getCost()));
                    text9.setText(restaurants.get(currentRestaurant).get("9").getAmount() + "");
                }
            }
        });

        final Label text10 = new Label(restaurants.get(currentRestaurant).get("10").getAmount() + "", labelStyle);
        text10.setAlignment(Align.center);
        text10.setWrap(true);

        final TextButton button10 = new TextButton(String.format("%s : %d lbs", restaurants.get(currentRestaurant).get("10").getName(), restaurants.get(currentRestaurant).get("10").getCost()), style);
        button10.setHeight((int)(Gdx.graphics.getHeight()*0.1));
        button10.setWidth(Gdx.graphics.getWidth());

        button10.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if(restaurants.get(currentRestaurant).sum >= restaurants.get(currentRestaurant).get("10").getCost()) {
                    restaurants.get(currentRestaurant).sum -= restaurants.get(currentRestaurant).get("10").getCost();
                    restaurants.get(currentRestaurant).get("10").add();
                    button10.setText(String.format("%s : %d lbs", restaurants.get(currentRestaurant).get("10").getName(), restaurants.get(currentRestaurant).get("10").getCost()));
                    text10.setText(restaurants.get(currentRestaurant).get("10").getAmount() + "");
                }
            }
        });


        //actually putting menu buttons onto the screen
        final Table scrollTable = new Table();
        scrollTable.center();
        scrollTable.add(button1).expandX().padTop(40).padBottom(10f).width((int)(Gdx.graphics.getWidth()*0.6)).height(button1.getHeight());
        scrollTable.add(text1).padTop(40).padBottom(10f).padLeft(5).padRight(5).width((int)(Gdx.graphics.getWidth()*0.3)).height(button1.getHeight());
        scrollTable.row();
        scrollTable.add(button2).expandX().padTop(40).padBottom(10f).width((int)(Gdx.graphics.getWidth()*0.6)).height(button1.getHeight());
        scrollTable.add(text2).padTop(40).padBottom(10f).padLeft(5).padRight(5).width((int)(Gdx.graphics.getWidth()*0.3)).height(button1.getHeight());
        scrollTable.row();
        scrollTable.add(button3).expandX().padTop(40).padBottom(10f).width((int)(Gdx.graphics.getWidth()*0.6)).height(button1.getHeight());
        scrollTable.add(text3).padTop(40).padBottom(10f).padLeft(5).padRight(5).width((int)(Gdx.graphics.getWidth()*0.3)).height(button1.getHeight());
        scrollTable.row();
        scrollTable.add(button4).expandX().padTop(40).padBottom(10f).width((int)(Gdx.graphics.getWidth()*0.6)).height(button1.getHeight());
        scrollTable.add(text4).padTop(40).padBottom(10f).padLeft(5).padRight(5).width((int)(Gdx.graphics.getWidth()*0.3)).height(button1.getHeight());
        scrollTable.row();
        scrollTable.add(button5).expandX().padTop(40).padBottom(10f).width((int)(Gdx.graphics.getWidth()*0.6)).height(button1.getHeight());
        scrollTable.add(text5).padTop(40).padBottom(10f).padLeft(5).padRight(5).width((int)(Gdx.graphics.getWidth()*0.3)).height(button1.getHeight());
        scrollTable.row();
        scrollTable.add(button6).expandX().padTop(40).padBottom(10f).width((int)(Gdx.graphics.getWidth()*0.6)).height(button1.getHeight());
        scrollTable.add(text6).padTop(40).padBottom(10f).padLeft(5).padRight(5).width((int)(Gdx.graphics.getWidth()*0.3)).height(button1.getHeight());
        scrollTable.row();
        scrollTable.add(button7).expandX().padTop(40).padBottom(10f).width((int)(Gdx.graphics.getWidth()*0.6)).height(button1.getHeight());
        scrollTable.add(text7).padTop(40).padBottom(10f).padLeft(5).padRight(5).width((int)(Gdx.graphics.getWidth()*0.3)).height(button1.getHeight());
        scrollTable.row();
        scrollTable.add(button8).expandX().padTop(40).padBottom(10f).width((int)(Gdx.graphics.getWidth()*0.6)).height(button1.getHeight());
        scrollTable.add(text8).padTop(40).padBottom(10f).padLeft(5).padRight(5).width((int)(Gdx.graphics.getWidth()*0.3)).height(button1.getHeight());
        scrollTable.row();
        scrollTable.add(button9).expandX().padTop(40).padBottom(10f).width((int)(Gdx.graphics.getWidth()*0.6)).height(button1.getHeight());
        scrollTable.add(text9).padTop(40).padBottom(10f).padLeft(5).padRight(5).width((int)(Gdx.graphics.getWidth()*0.3)).height(button1.getHeight());
        scrollTable.row();
        scrollTable.add(button10).expandX().padTop(40).padBottom(10f).width((int)(Gdx.graphics.getWidth()*0.6)).height(button1.getHeight());
        scrollTable.add(text10).padTop(40).padBottom(10f).padLeft(5).padRight(5).width((int)(Gdx.graphics.getWidth()*0.3)).height(button1.getHeight());
        scrollTable.row();

        final ScrollPane scroller = new ScrollPane(scrollTable);
        final Table table = new Table();
        table.setFillParent(true);
        table.add(scroller).size(Gdx.graphics.getWidth(),(int)(Gdx.graphics.getHeight()*0.8)).setActorY((int) (Gdx.graphics.getHeight() * 0.1));
        return table;
    }
}
