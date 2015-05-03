package com.mygdx.game;
import java.io.*;
import java.util.*;

/**
 * Created by mahmo266317 on 4/13/2015.
 */
public class Upgrade{
    private String name;
    private long amount, cost;
    private double worth;

    public Upgrade()
    {
        name = "w";
        amount = 0;
        cost = 0;
        worth = 0;
    }
    public Upgrade(String name, long amount,long cost, double worth)
    {
        this.name = name;
        this.amount = amount;
        this.worth = worth;
        this.cost = cost;
    }

    public void add()
    {
        amount++;
        cost = (long)Math.ceil(cost*1.4);
    }

    public long getCost()
    {
        return cost;
    }

    public String getName()
    {
        return name;
    }

    public long getAmount()
    {
        return amount;
    }

    public double getWorth()
    {
        return worth;
    }

    public double tick()
    {
        return (worth*amount);
    }
}
