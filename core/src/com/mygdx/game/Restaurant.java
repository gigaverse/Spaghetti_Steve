package com.mygdx.game;

import java.util.*;

/**
 * Created by mahmo266317 on 4/13/2015.
 */
public class Restaurant {
    private ArrayList<Upgrade> upgrades;
    private ArrayList<Unit> units;
    public double sum;
    private String name;
    public Restaurant()
    {
        //TODO ANY UPDATES TO THE RESTAURANT MENU HAPPENS HERE!
        name = "Spaghetti Steve's Pasta Palace";

        upgrades = new ArrayList<Upgrade>();

        upgrades.add(new Upgrade("Pasta Sauce", 0, 5, 0.1));
        upgrades.add(new Upgrade("Alfredo Sauce", 0, 40, 0.4));
        upgrades.add(new Upgrade("Meatballs", 0, 160, 1.2));
        upgrades.add(new Upgrade("Spaghetti Maker", 0, 600, 3.0));
        upgrades.add(new Upgrade("Industrial Spaghetti Wagon", 0, 1100, 6.5));
        upgrades.add(new Upgrade("Pastinator", 0, 2050, 11.2));
        upgrades.add(new Upgrade("Flying Spaghetti Monster", 0, 6666, 40.0));
        upgrades.add(new Upgrade("Small Spaghetti Collider", 0, 15000, 96.8));
        upgrades.add(new Upgrade("Large Pasta Collider", 0, 35000, 175.0));
        upgrades.add(new Upgrade("Anti-Pasta", 0, 100000, 350.0));

        units = new ArrayList<Unit>();
        units.add(new Unit("Waiter",0, 10000, 0.001));

    }

    public boolean add(Upgrade u)
    {
        return upgrades.add(u);
    }

    public ArrayList<Unit> getUnits()
    {
        return units;
    }

    public ArrayList<Upgrade> getUpgrades()
    {
        return upgrades;
    }

    public Upgrade remove(int name)
    {
        return upgrades.remove(name);
    }

    public Unit getUnit(int name)
    {
        return units.get(name);
    }

    public Upgrade getUpgrade(int name)
    {
        return upgrades.get(name);
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
        for(Upgrade u : upgrades)
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
