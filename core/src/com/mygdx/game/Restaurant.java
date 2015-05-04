package com.mygdx.game;

import java.util.*;

/**
 * Created by mahmo266317 on 4/13/2015.
 */
public class Restaurant {
    private ArrayList<Upgrade> list;
    public double sum;
    private String name;
    public Restaurant()
    {
        //TODO ANY UPDATES TO THE RESTAURANT MENU HAPPENS HERE!
        name = "Spaghetti Steve's Pasta Palace";
        list = new ArrayList<Upgrade>();
        list.add(new Upgrade("Pasta Sauce", 0, 5, 0.1));
        list.add(new Upgrade("Alfredo Sauce", 0, 40, 0.4));
        list.add(new Upgrade("Meatballs", 0, 160, 1.2));
        list.add(new Upgrade("Spaghetti Maker", 0, 600, 3.0));
        list.add(new Upgrade("Industrial Spaghetti Wagon", 0, 1100, 6.5));
        list.add(new Upgrade("Pastinator", 0, 2050, 11.2));
        list.add(new Upgrade("Flying Spaghetti Monster", 0, 6666, 40.0));
        list.add(new Upgrade("Small Spaghetti Collider", 0, 15000, 96.8));
        list.add(new Upgrade("Large Pasta Collider", 0, 35000, 175.0));
        list.add(new Upgrade("Anti-Pasta", 0, 100000, 350.0));
    }

    public boolean add(Upgrade u)
    {
        return list.add(u);
    }

    public ArrayList<Upgrade> getList()
    {
        return list;
    }

    public Upgrade remove(int name)
    {
        return list.remove(name);
    }

    public Upgrade get(int name)
    {
        return list.get(name);
    }

    public void setSum(double sum)
    {
        this.sum = sum;
    }

    public double getSum()
    {
        return sum;
    }

    public double getIncomePerSecond()
    {
        double sum = 0;
        for(Upgrade u : list)
        {
            sum += u.tick();
        }
        return sum*10;
    }

    public String getName() {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
