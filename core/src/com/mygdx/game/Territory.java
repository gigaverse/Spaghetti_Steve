package com.mygdx.game;

import java.util.ArrayList;

/**
 * Created by mahmo266317 on 5/18/2015.
 */
public class Territory
{
    private ArrayList<Upgrade> upgrades;
    private ArrayList<Unit> units;
    public double sum, cost;
    private String name;
    public Territory()
    {
        //TODO ANY UPDATES TO THE TERRITORY MENU HAPPENS HERE!
        name = "Spaghetti Steve's Pasta Dictatorship";

        upgrades = new ArrayList<Upgrade>();

        //TODO add proper stats to all
        upgrades.add(new Upgrade("Speghetti Revolution", 0, 5, 0.025));
        upgrades.add(new Upgrade("Fedelini Flag", 0, 5, 0.025));
        upgrades.add(new Upgrade("Capellini Constitution", 0, 5, 0.025));


        units = new ArrayList<Unit>();
        //quality upgrades, set up for space expansion?
        units.add(new Unit("Pastaverse Exploration","quality",0, 5000, 0));

        //minion units
        //TODO minion combat?
        units.add(new Unit("Elicoidali Embryo","minion",0, 10000, 1,1,1));
        units.add(new Unit("Spaghetto Trenne Thug","minion",0, 10000, 1,1,1));
        units.add(new Unit("Whole-grain Worker","minion",0, 30000, 2,2,2));

        units.add(new Unit("Cannerozzetti CEO","minion",0, 75000, 3,4,3));
        units.add(new Unit("Malloreddus Mayor","minion",0, 75000, 3,4,3));
        units.add(new Unit("Spaghetti Stalin","minion",0, 120000, 5,10,5));


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
