package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;

/**
 * Created by clayb275151 on 5/19/2015.
 */
public class TerritoryMenu {
    public static Table territoryMenu(final PlayerSave player, final Label.LabelStyle labelStyle,final TextButton.TextButtonStyle style)
    {
        //Created labels and added "buying" an upgrade
        Table scrollTable = new Table();
        scrollTable.top();

        final ScrollPane scroller = new ScrollPane(scrollTable);
        final Table table = new Table();
        table.setFillParent(true);
        //scrollTable.center();

        Label label = new Label("Territories", labelStyle);
        label.setAlignment(Align.center);
        label.setWrap(false);
        label.setHeight((int)(Gdx.graphics.getHeight()*0.1));
        label.setWidth(Gdx.graphics.getWidth());

        table
                .add(label)
                .padTop((int)(Gdx.graphics.getHeight()*0.025))
                .padLeft((int)(Gdx.graphics.getWidth()*0.025))
                .padRight((int)(Gdx.graphics.getWidth()*0.025))
                .width((int)(Gdx.graphics.getWidth()*0.95))
                .height(label.getHeight());

        table.row();

        for(int i = 0; i < player.getTerritories().size(); i++)
        {
            final int j = i;
            Territory r = player.getTerritories().get(i);
            final Label counter = new Label(String.format("%.1f\nlbs/sec",r.getIncomePerSecond()), labelStyle);
            counter.setAlignment(Align.center);
            counter.setWrap(true);
            counter.setHeight((int)(Gdx.graphics.getHeight()*0.1));
            counter.setWidth(Gdx.graphics.getWidth());

            final TextButton upgradeButton = new TextButton(String.format("%s", r.getName()), style);
            upgradeButton.setHeight((int)(Gdx.graphics.getHeight()*0.1));
            upgradeButton.setWidth(Gdx.graphics.getWidth());

            upgradeButton.addListener(new InputListener() {
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }

                public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                    player.setCurrent(j);
                    MyGdxGame.RestaurantScreen(0);
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

        table.add(scroller).size(Gdx.graphics.getWidth(), (int) (Gdx.graphics.getHeight() * 0.575));



        TextButton.TextButtonStyle greenButton = new TextButton.TextButtonStyle();

        TextureAtlas buttonAtlas = new TextureAtlas("greenButton.atlas");
        Skin buttonSkin = new Skin();
        buttonSkin.addRegions(buttonAtlas);

        greenButton.up = buttonSkin.getDrawable("button");
        greenButton.down = buttonSkin.getDrawable("buttonPressed");
        greenButton.font = style.font;

        final TextButton purchaseButton = new TextButton("Challenge Nearby Countries", greenButton);
        purchaseButton.setHeight((int) (Gdx.graphics.getHeight() * 0.1));
        purchaseButton.setWidth(Gdx.graphics.getWidth());


        purchaseButton.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if(purchaseButton.getText().equals("Challenge Nearby Countries"))
                {
                    MyGdxGame.TerritoryScreen();
                }
                else
                    MyGdxGame.ExpansionScreen();
            }
        });

        table.row();
        table.add(purchaseButton).size(Gdx.graphics.getWidth(),(int)(Gdx.graphics.getHeight()*0.1)).setActorY((int) (Gdx.graphics.getHeight() * 0.1));

        return table;
    }

}
