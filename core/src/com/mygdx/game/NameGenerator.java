package com.mygdx.game;

import com.badlogic.gdx.Gdx;

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

        Scanner s = new Scanner(Gdx.files.internal("names.txt").file());

        while(s.hasNextLine())
        {
            String name = s.nextLine().split(" ")[0];
            name = name.charAt(0) + name.substring(1).toLowerCase();
            ArrayList<String> list = new ArrayList<String>();
            if(names.containsKey(name.charAt(0)))
                list = names.get(name.charAt(0));
            list.add(name);
            names.put(name.charAt(0), list);
        }

        s = new Scanner(Gdx.files.internal("adjectives.txt").file());

        while(s.hasNextLine())
        {
            String adjective = s.nextLine().split(" ")[0];
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
        char random = (char)((int)(Math.random()*26) + 65);
        ArrayList<String> alist = adjectives.get(random);
        ArrayList<String> nlist = names.get(random);
        return String.format("%s %s", alist.get((int)(Math.random()*alist.size())), nlist.get((int)(Math.random()*nlist.size())));
    }
}
