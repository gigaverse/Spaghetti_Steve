package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

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

        return table;
    }
}
