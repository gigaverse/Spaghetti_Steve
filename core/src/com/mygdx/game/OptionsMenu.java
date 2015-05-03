package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;

/**
 * Created by Gigaverse on 5/3/2015.
 */
public class OptionsMenu {
    //ANY BUTTON YOU WANT TO SEE IN THE OPTIONS GO HERE
    public static Table optionsMenu(final ArrayList<Restaurant> restaurants,final TextButton.TextButtonStyle style,final Label.LabelStyle labelStyle,final Stage upgradeScreen) {

        Table scrollTable = new Table();
        scrollTable.center();

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
                        for(int i = restaurants.size()-1; i >=0; i--)
                        {
                            restaurants.remove(i);
                            restaurants.add(new Restaurant());
                        }
                        i = 0;
                        upgradeScreen.clear();
                        upgradeScreen.addActor(UpgradeMenu.upgradeMenu(restaurants,0,labelStyle,style));
                    }
                 }
        });

        scrollTable.add(deleteSaveButton).expandX().padTop(40).padBottom(10f).width((int)(Gdx.graphics.getWidth())).height(deleteSaveButton.getHeight());
        scrollTable.row();


        final ScrollPane scroller = new ScrollPane(scrollTable);
        final Table table = new Table();
        table.setFillParent(true);
        table.add(scroller).size(Gdx.graphics.getWidth(),(int)(Gdx.graphics.getHeight()*0.8)).setActorY((int) (Gdx.graphics.getHeight() * 0.1));
        return table;
    }
}
