package com.mygdx.game;
import java.io.*;
import java.util.*;

/**
 * Created by mahmo266317 on 4/13/2015.
 */
public class Upgrade{
    private String name, type;
    private long amount;
    private double worth;
    public Upgrade(String name,String type, long amount, double worth)
    {
        this.name = name;
        this.type = type;
        this.amount = amount;
        this.worth = worth;
    }

    public void add()
    {
        amount++;
    }

    public String getName()
    {
        return name;
    }

    public String getType()
    {
        return type;
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
