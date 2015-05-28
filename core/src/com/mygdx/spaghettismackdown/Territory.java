package com.mygdx.spaghettismackdown;

import java.util.ArrayList;

/**
 * Created by mahmo266317 on 5/18/2015.
 */
public class Territory
{
    private ArrayList<Upgrade> upgrades;
    private ArrayList<Unit> units;
    public double sum, cost, previous;
    private String name;
    public Territory()
    {
        //TODO ANY UPDATES TO THE TERRITORY MENU HAPPENS HERE!
        name = "Spaghetti Steve's Pasta Dictatorship";

        upgrades = new ArrayList<Upgrade>();

        //TODO add proper stats to all
        upgrades.add(new Upgrade("Speghetti Revolution", 0, 100000000, 10000));
        upgrades.add(new Upgrade("Fedelini Flag", 0, 175000000, 25000));
        upgrades.add(new Upgrade("Capellini Constitution", 0, 300000000l, 55000));


        units = new ArrayList<Unit>();
        //quality upgrades, set up for space expansion?
        units.add(new Unit("Pastaverse Exploration","quality",0, 5000, 0));

        //minion units
        //TODO minion combat?
        units.add(new Unit("Pastamancer","minion",0, 100000000, 1,1,1));
        units.add(new Unit("Penne Pikeman","minion",0, 350000000, 1,1,1));
        units.add(new Unit("Spaghetti Bomber","minion",0, 50000000000d, 2,2,2));

        units.add(new Unit("Bigoli Bomber","minion",0, 150000000000d, 3,4,3));
        units.add(new Unit("Capellini Capper","minion",0, 750000000000d, 3,4,3));
        units.add(new Unit("Pici Punisher","minion",0, 3500000000000d, 5,10,5));

        units.add(new Unit("Elicoidali Engine","minion",0, 3500000000000d, 1,1,1));
        units.add(new Unit("Spaghetto Trenne Thug","minion",0, 3500000000000d, 1,1,1));
        units.add(new Unit("Whole-grain Worker","minion",0, 3500000000000d, 2,2,2));

        units.add(new Unit("Cannerozzetti CEO","minion",0, 3500000000000d, 3,4,3));
        units.add(new Unit("Malloreddus Mayor","minion",0, 3500000000000d, 3,4,3));
        units.add(new Unit("Spaghetti Stalin","minion",0, 3500000000000d, 5,10,5));


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

    public void compilePrevious(ArrayList<Restaurant> arr)
    {
        for(Restaurant r : arr)
        {
            previous += r.getIncomePerSecond();
        }
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
        return sum*10 + previous;
    }

    public double tick()
    {
        return getIncomePerSecond()/10;
    }

    public double getMoneyPerSecond()
    {
        double sum = 0;
        for(Unit u : units)
        {
            sum += u.getMultiplier()*u.getAmount();
        }
        return sum*10 + previous;
    }

    public String getName() {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
