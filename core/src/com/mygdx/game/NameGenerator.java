package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by mahmo266317 on 5/14/2015.
 */
public class NameGenerator {
    Map<Character, ArrayList<String>> names, adjectives;

    public NameGenerator() throws IOException
    {
        names = new HashMap<Character, ArrayList<String>>();
        adjectives = new HashMap<Character, ArrayList<String>>();


        FileHandle fileHandle = Gdx.files.internal("names.txt");
        String help = fileHandle.readString().replaceAll("[^A-Z]+"," " );

        for(String name : help.split(" "))
        {
            name = name.charAt(0) + name.substring(1).toLowerCase();
            ArrayList<String> list = new ArrayList<String>();
            if(names.containsKey(name.charAt(0)))
                list = names.get(name.charAt(0));
            list.add(name);
            names.put(name.charAt(0), list);
        }

        FileHandle filex = Gdx.files.internal("adjectives.txt");
        help = filex.readString().replaceAll("[^A-Za-z]+", " ");




        for(String adjective : help.split(" "))
        {
            adjective = adjective.charAt(0) + adjective.substring(1).toLowerCase();
            ArrayList<String> list = new ArrayList<String>();
            if(adjectives.containsKey(adjective.charAt(0)))
                list = adjectives.get(adjective.charAt(0));
            list.add(adjective);
            adjectives.put(adjective.charAt(0), list);
        }
    }

    public String pull()
    {
        ArrayList<String> alist;
        ArrayList<String> nlist;
        while(true) {
            char random = (char)((int)(Math.random()*26) + 65);
            alist = adjectives.get(random);
            nlist = names.get(random);
            if(alist == null || nlist == null)
                continue;
            if(alist.size() > 0 && nlist.size() > 0)
                break;
        }

        return String.format("%s %s", alist.get((int)(Math.random()*alist.size())), nlist.get((int)(Math.random()*nlist.size())));
    }
}
