package com.mygdx.game;

import java.util.*;

/**
 * Created by mahmo266317 on 4/13/2015.
 */
public class Restaurant {
    public Map<String,Upgrade> list;
    public long sum;
    public Restaurant()
    {
        list = new HashMap<String,Upgrade>();
        list.put("1", new Upgrade("Pasta Sauce", "noob", 0, 0.1));
        list.put("2", new Upgrade("Alfredo Sauce", "noob", 0, 0.4));
        list.put("3", new Upgrade("Meatballs", "noob", 0, 1.2));
        list.put("4", new Upgrade("Spaghetti Maker", "noob", 0, 3.0));
        list.put("5", new Upgrade("Industrial Spaghetti Wagon", "noob", 0, 6.5));
        list.put("6", new Upgrade("Pastinator", "noob", 0, 11.2));
        list.put("7", new Upgrade("Flying Spaghetti Monster", "noob", 0, 40.0));
        list.put("8", new Upgrade("Small Spaghetti Collider", "noob", 0, 96.8));
        list.put("9", new Upgrade("Large Pasta Collider", "noob", 0, 175.0));
        list.put("10", new Upgrade("Anti-Pasta", "noob", 0, 350.0));
    }

    public Upgrade add(Upgrade u)
    {
        return list.put(u.getName(), u);
    }

    public Upgrade remove(String name)
    {
        return list.remove(name);
    }

    public Upgrade get(String name)
    {
        return list.get(name);
    }
}
