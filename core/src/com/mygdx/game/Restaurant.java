package com.mygdx.game;

import java.util.*;

/**
 * Created by mahmo266317 on 4/13/2015.
 */
public class Restaurant {
    private Map<String,Upgrade> list;
    public double sum;
    public Restaurant()
    {
        //TODO ANY UPDATES TO THE RESTAURANT MENU HAPPENS HERE!
        list = new TreeMap<String, Upgrade>();
        list.put(1+"", new Upgrade("Pasta Sauce", 0, 5, 0.1));
        list.put(2+"", new Upgrade("Alfredo Sauce", 0, 40, 0.4));
        list.put(3+"", new Upgrade("Meatballs", 0, 160, 1.2));
        list.put(4+"", new Upgrade("Spaghetti Maker", 0, 600, 3.0));
        list.put(5+"", new Upgrade("Industrial Spaghetti Wagon", 0, 1100, 6.5));
        list.put(6+"", new Upgrade("Pastinator", 0, 2050, 11.2));
        list.put(7+"", new Upgrade("Flying Spaghetti Monster", 0, 6666, 40.0));
        list.put(8+"", new Upgrade("Small Spaghetti Collider", 0, 15000, 96.8));
        list.put(9+"", new Upgrade("Large Pasta Collider", 0, 35000, 175.0));
        list.put(10+"", new Upgrade("Anti-Pasta", 0, 100000, 350.0));
    }

    public Upgrade add(Upgrade u)
    {
        return list.put((list.size() + 1)+"", u);
    }

    public Map<String,Upgrade> getList()
    {
        return list;
    }

    public Upgrade remove(Integer name)
    {
        return list.remove(name);
    }

    public Upgrade get(Integer name)
    {
        return list.get(name+"");
    }

    public void setSum(double sum)
    {
        this.sum = sum;
    }

    public double getSum()
    {
        return sum;
    }
}
