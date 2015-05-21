package com.mygdx.game;
import java.io.*;
import java.util.*;

/**
 * Created by mahmo266317 on 4/13/2015.
 */
public class Upgrade{
    private String name;
    private long amount;
    private double worth, cost;
    private int mult;

    public Upgrade()
    {
        name = "w";
        amount = 0;
        cost = 0;
        worth = 0;
        mult = 1;
    }
    public Upgrade(String name, long amount,double cost, double worth)
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
        cost = (long)Math.ceil(cost*1.1);
        if(amount%25 == 0)
        {
            mult *=2;
        }
        if(amount%100 == 0)
        {
            mult *=2;
        }
        if(amount%1000 == 0)
        {
            mult *= 2.5;
        }
    }

    public double getCost()
    {
        return cost;
    }

    public String getName()
    {
        if(mult > 1)
            return name + " x " + mult;
        return name;

    }

    public long getAmount()
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
}
