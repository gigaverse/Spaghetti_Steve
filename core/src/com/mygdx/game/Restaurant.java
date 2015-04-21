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
        list.put("1", new Upgrade("first", "noob", 0, 1));
        list.put("2", new Upgrade("first", "noob", 0, 1));
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
