package com.mygdx.spaghettismackdown;

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

        upgrades.add(new Upgrade("Forks", 0, 25, 0.05));
        upgrades.add(new Upgrade("Pasta Pans", 0, 75, 0.2));
        upgrades.add(new Upgrade("Instant Noodles", 0, 150, 0.6));

        upgrades.add(new Upgrade("Tomato Sauce", 0, 500, 1.1));
        upgrades.add(new Upgrade("Boiling Water", 0, 1150, 2.2));
        upgrades.add(new Upgrade("Angel Hair Pasta", 0, 2300, 5));

        upgrades.add(new Upgrade("Pasta Cutter", 0, 4500, 10));
        upgrades.add(new Upgrade("Alfredo Cauldron", 0, 8000, 18));
        upgrades.add(new Upgrade("Spaghetti Spitter", 0, 22500, 25));

        upgrades.add(new Upgrade("Pasta Room", 0, 50000, 55));
        upgrades.add(new Upgrade("Spaghetti Monster", 0, 112500, 75));
        upgrades.add(new Upgrade("Spaghetti Community", 0, 200000, 110));

        upgrades.add(new Upgrade("Pasta Fort", 0, 437500, 155));
        upgrades.add(new Upgrade("Flying Spaghetti Monster", 0, 800000, 225));
        upgrades.add(new Upgrade("The Spaghetto", 0 , 1500000, 430));


        units = new ArrayList<Unit>();
        units.add(new Unit("Pasta Maker","quality",0, 5000, 0));

        units.add(new Unit("Table Bus","money",0, 500, 0.005));
        units.add(new Unit("Waiter","money",0, 2500, 0.015));
        units.add(new Unit("Server","money",0, 7500, 0.035));

        units.add(new Unit("Sous Chef","money",0, 15000, 0.075));
        units.add(new Unit("Late Night Ad","money",0, 35000, 0.25));
        units.add(new Unit("Internet Shop","money",0, 75000, 1));

        units.add(new Unit("Phone App Ads","money",0, 250000, 4));
        units.add(new Unit("Bucatini Bank","money",0, 750000, 16));
        units.add(new Unit("The Spaghetti-net","money",0, 2500000, 50));



    }

    public void setRestaurant(int num)
    {
        if(num <= 1)
            return;
        for(Upgrade u : upgrades)
        {
            u.setCost(Math.pow(u.getCost(), num));
            u.setWorth(Math.pow(u.getWorth(), 0.8 * num));
        }

        for(Unit u : units)
        {
            if(u.getType().equals("quality")) {
                u.setCost(Math.pow(u.getCost(), num));
                u.setMultiplier(u.getMultiplier()* Math.pow(2 , Math.pow(2, num)));
            }
        }
    }

    public int getStars()
    {
        return stars;
    }

    public void setStars(int stars)
    {
        this.stars = stars;
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
