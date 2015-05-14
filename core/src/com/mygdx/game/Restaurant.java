package com.mygdx.game;

import java.util.*;

/**
 * Created by mahmo266317 on 4/13/2015.
 */
public class Restaurant {
    private ArrayList<Upgrade> upgrades;
    private ArrayList<Unit> units;
    public double sum, cost;
    private String name;
    public Restaurant()
    {
        //TODO ANY UPDATES TO THE RESTAURANT MENU HAPPENS HERE!
        name = "Spaghetti Steve's Pasta Palace";

        upgrades = new ArrayList<Upgrade>();

        upgrades.add(new Upgrade("Forks", 0, 5, 0.025));
        upgrades.add(new Upgrade("Pasta Pans", 0, 40, 0.1));
        upgrades.add(new Upgrade("Instant Noodles", 0, 160, 0.3));

        upgrades.add(new Upgrade("Tomato Sauce", 0, 600, 0.75));
        upgrades.add(new Upgrade("Boiling Water", 0, 1100, 1.5));
        upgrades.add(new Upgrade("Angel Hair Pasta", 0, 2050, 3));

        upgrades.add(new Upgrade("Pasta Cutter", 0, 6666, 10.0));
        upgrades.add(new Upgrade("Alfredo Cauldron", 0, 15000, 24.0));
        upgrades.add(new Upgrade("Spaghetti Spitter", 0, 35000, 45));

        upgrades.add(new Upgrade("Pasta Room", 0, 100000, 75));
        upgrades.add(new Upgrade("Spaghetti Monster", 0, 225000, 135));
        upgrades.add(new Upgrade("Spaghetti Community", 0, 400000, 200));

        upgrades.add(new Upgrade("Pasta Fort", 0, 875000, 335));
        upgrades.add(new Upgrade("Flying Spaghetti Monster", 0, 1750000, 575));
        upgrades.add(new Upgrade("The Spaghetto", 0 , 3000000, 1000));


        units = new ArrayList<Unit>();
        units.add(new Unit("Pasta Maker","quality",0, 5000, 0));

        units.add(new Unit("Table Bus","money",0, 10000, 0.005));
        units.add(new Unit("Waiter","money",0, 50000, 0.015));
        units.add(new Unit("Server","money",0, 125000, 0.035));
        units.add(new Unit("Sous Chef","money",0, 250000, 0.075));

        /*
        units.add(new Unit("Dishwasher Boy","minion",0, 10000, 1,1,1));
        units.add(new Unit("Spoon Man","minion",0, 30000, 2,2,2));
        units.add(new Unit("Pastamancer","minion",0, 75000, 3,4,3));
        units.add(new Unit("Alfredo Blaster","minion",0, 120000, 5,10,5));
        */



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

    public void setCost(double cost)
    {
        this.cost = cost;
    }

    public double getCost()
    {
        return cost;
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
