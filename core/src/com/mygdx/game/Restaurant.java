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
    private int stars;
    public Restaurant()
    {
        //ANY UPDATES TO THE RESTAURANT MENU HAPPENS HERE!
        name = "Spaghetti Steve's Pasta Palace";

        upgrades = new ArrayList<Upgrade>();

        upgrades.add(new Upgrade("Forks", 0, 50, 0.025));
        upgrades.add(new Upgrade("Pasta Pans", 0, 150, 0.1));
        upgrades.add(new Upgrade("Instant Noodles", 0, 300, 0.3));

        upgrades.add(new Upgrade("Tomato Sauce", 0, 1000, 0.75));
        upgrades.add(new Upgrade("Boiling Water", 0, 2300, 1.5));
        upgrades.add(new Upgrade("Angel Hair Pasta", 0, 4600, 3));

        upgrades.add(new Upgrade("Pasta Cutter", 0, 9000, 10.0));
        upgrades.add(new Upgrade("Alfredo Cauldron", 0, 16000, 24.0));
        upgrades.add(new Upgrade("Spaghetti Spitter", 0, 45000, 35));

        upgrades.add(new Upgrade("Pasta Room", 0, 100000, 50));
        upgrades.add(new Upgrade("Spaghetti Monster", 0, 225000, 85));
        upgrades.add(new Upgrade("Spaghetti Community", 0, 400000, 125));

        upgrades.add(new Upgrade("Pasta Fort", 0, 875000, 335));
        upgrades.add(new Upgrade("Flying Spaghetti Monster", 0, 1750000, 275));
        upgrades.add(new Upgrade("The Spaghetto", 0 , 3000000, 600));


        units = new ArrayList<Unit>();
        units.add(new Unit("Pasta Maker","quality",0, 5000, 0));
        units.add(new Unit("Michelin Stars","quality",0, 500000, 0));


        units.add(new Unit("Table Bus","money",0, 500, 0.005));
        units.add(new Unit("Waiter","money",0, 2500, 0.015));
        units.add(new Unit("Server","money",0, 7500, 0.035));

        units.add(new Unit("Sous Chef","money",0, 15000, 0.075));
        units.add(new Unit("Advertisement","money",0, 35000, 0.25));
        units.add(new Unit("Internet Shop","money",0, 75000, 1));



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

    public double getMoneyPerSecond()
    {
        double sum = 0;
        for(Unit u : units)
        {
            sum += u.getAmount()*u.getMultiplier();
        }
        return sum*10;
    }

    public String getName() {
        stars = (int)units.get(1).getAmount();
        String s = "";
        for(int i = 0; i < stars; i++)
        {
            s += "*";
        }
        return name + "\n" + s;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
