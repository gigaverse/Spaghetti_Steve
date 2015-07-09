package com.mygdx.spaghettismackdown;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Json;


/**
 * Created by Gigaverse on 5/3/2015.
 */
public class OptionsMenu {
    //ANY BUTTON YOU WANT TO SEE IN THE OPTIONS GO HERE
    public static Table optionsMenu(final PlayerSave player,final TextButton.TextButtonStyle style,final Label.LabelStyle labelStyle,final Stage upgradeScreen) {

        Table scrollTable = new Table();
        scrollTable.center();

        final ScrollPane scroller = new ScrollPane(scrollTable);
        final Table table = new Table();
        table.setFillParent(true);
        scrollTable.top();

        table.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("back.png")))));

        Label label = new Label("Options", labelStyle);
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

        final TextButton deleteSaveButton = new TextButton(String.format("DELETE SAVE"), style);
        deleteSaveButton.setHeight((int) (Gdx.graphics.getHeight() * 0.1));
        deleteSaveButton.setWidth(Gdx.graphics.getWidth());

        deleteSaveButton.addListener(new InputListener() {
                 int i = 0;
                 public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                     return true;
                 }

                 public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    if(i == 0)
                    {
                        deleteSaveButton.setText("ARE YOU SURE?");
                        i++;
                    }
                    else if(i==1)
                    {
                        deleteSaveButton.setText("THERE'S NO GOING BACK!");
                        i++;
                    }
                    else
                    {
                        deleteSaveButton.setText("SAVE DELETED.");
                        FileHandle hope = Gdx.files.local("pasta2.dat");
                        hope.delete();
                        player.reset();

                    }
                 }
        });

        scrollTable
                .add(deleteSaveButton)
                .padTop((int)(Gdx.graphics.getHeight()*0.025))
                .padLeft((int)(Gdx.graphics.getWidth()*0.025))
                .padRight((int)(Gdx.graphics.getWidth()*0.025))
                .width((int)(Gdx.graphics.getWidth()*0.95))
                .height(label.getHeight());

        scrollTable.row();

        final TextButton closeGame = new TextButton(String.format("Save & Quit"), style);
        closeGame.setHeight((int) (Gdx.graphics.getHeight() * 0.1));
        closeGame.setWidth(Gdx.graphics.getWidth());


        closeGame.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                MyGdxGame.willAnimate();
                if(!player.save)
                    return;
                FileHandle hope = Gdx.files.local("saveGame.dat");
                Json json = new Json();
                String save = String.format("%s youssefspatentpendingsplitphrase %s youssefspatentpendingsplitphrase %s youssefspatentpendingsplitphrase", player.getTotalMoney() + "", player.getTotalPasta() + "", player.getPPC() + "");
                save += json.toJson(player.getRestaurants()) + "youssefspatentpendingsplitphrase" + json.toJson(player.getTerritories()) + "youssefspatentpendingsplitphrase" +
                        json.toJson(player.getPotentialRestaurants()) + "youssefspatentpendingsplitphrase" + json.toJson(player.getPotentialTerritories()) + "youssefspatentpendingsplitphrase";
                hope.writeString(save, false);
                MyGdxGame.timer.cancel();
                MyGdxGame.t.cancel();
                Gdx.app.exit();
            }
        });

        scrollTable
                .add(closeGame)
                .padTop((int)(Gdx.graphics.getHeight()*0.025))
                .padLeft((int)(Gdx.graphics.getWidth()*0.025))
                .padRight((int)(Gdx.graphics.getWidth()*0.025))
                .width((int)(Gdx.graphics.getWidth()*0.95))
                .height(label.getHeight());

        scrollTable.row();

        final TextButton muteGame = new TextButton(String.format("Music is On"), style);
        muteGame.setHeight((int) (Gdx.graphics.getHeight() * 0.1));
        muteGame.setWidth(Gdx.graphics.getWidth());

        if(!MyGdxGame.italy.isPlaying())
        {
            muteGame.setText("Music is Off");
        }


        muteGame.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if(!player.music)
                {
                    player.music = true;
                    if(!MyGdxGame.italy.isPlaying())
                        MyGdxGame.italy.play();
                    MyGdxGame.italy.setVolume(1);
                    muteGame.setText("Music is On");
                }
                else
                {
                    player.music = false;
                    MyGdxGame.italy.setVolume(0);
                    muteGame.setText("Music is Off");
                }
            }
        });

        scrollTable
                .add(muteGame)
                .padTop((int)(Gdx.graphics.getHeight()*0.025))
                .padLeft((int)(Gdx.graphics.getWidth()*0.025))
                .padRight((int)(Gdx.graphics.getWidth()*0.025))
                .width((int)(Gdx.graphics.getWidth()*0.95))
                .height(label.getHeight());

        scrollTable.row();

        final TextButton animations = new TextButton((player.animation ? "Animations are ON" : "Animations are OFF"), style);
        animations.setHeight((int) (Gdx.graphics.getHeight() * 0.1));
        animations.setWidth(Gdx.graphics.getWidth());


        animations.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                player.animation = !player.animation;
                animations.setText(player.animation ? "Animations are ON" : "Animations are OFF");
            }
        });

        scrollTable
                .add(animations)
                .padTop((int)(Gdx.graphics.getHeight()*0.025))
                .padLeft((int)(Gdx.graphics.getWidth()*0.025))
                .padRight((int)(Gdx.graphics.getWidth()*0.025))
                .width((int)(Gdx.graphics.getWidth()*0.95))
                .height(label.getHeight());

        scrollTable.row();

        table.add(scroller).size(Gdx.graphics.getWidth(),(int)(Gdx.graphics.getHeight()*0.675)).setActorY((int) (Gdx.graphics.getHeight() * 0.1));
        return table;
    }
}
