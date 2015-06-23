package com.mygdx.spaghettismackdown;

/**
 * Created by mahmo266317 on 4/13/2015.
 */
public class Upgrade{
    private String name;
    private double amount;
    private double worth, cost;
    private double mult;

    public Upgrade()
    {
        name = "w";
        amount = 0;
        cost = 0;
        worth = 0;
        mult = 1;
    }
    public Upgrade(String name, double amount,double cost, double worth)
    {
        this.name = name;
        this.amount = amount;
        this.worth = worth;
        this.cost = cost;
        mult = 1;
    }

    public void add()
    {
        amount++;
        cost = Math.ceil(cost*1.1);
        if(amount%25 == 0)
        {
            mult *=2;
        }
        if(amount%100 == 0)
        {
            mult *=4;
        }
    }

    public void add(int n)
    {
        for(int i = 0; i < n; i++)
        {
            amount++;
            if(amount == 10)
                mult*=2;
            if(amount%25 == 0)
                mult *=2;
            if(amount % 100 == 0)
                mult *= 10;

            cost = cost*1.1;
        }
    }

    public double getCost()
    {
        return cost;
    }

    public double getCost(int n)
    {
        double c = cost;
        double coost = 0;
        for(int i = 0; i < n; i++)
        {
            coost += c;
            c = c*1.1;
        }

        return coost;
    }

    public String getName()
    {
        if(mult > 1)
            return name + " x " + MyGdxGame.convertNumber(mult);
        return name;

    }

    public double getAmount()
    {
        return amount;
    }

    public double getWorth()
    {
        return worth*mult;
    }

    public double tick()
    {
        return (worth*amount*mult);
    }

    public void setCost(double cost)
    {
        this.cost = cost;
    }

    public void setWorth(double worth)
    {
        this.worth = worth;
    }
}
