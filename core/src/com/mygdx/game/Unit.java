package com.mygdx.game;

/**
 * Created by Gigaverse on 5/4/2015.
 */
public class Unit {
    private String name, type;
    private long amount, cost;
    private double multiplier,atk,def,range;

    public Unit()
    {
        this.name = "";
    }

    public Unit(String name,String type,  long amount, long cost, double multiplier)
    {
        this.type = type;
        this.name = name;
        this.amount = amount;
        this.cost = cost;
        this.multiplier = multiplier;
    }

    public Unit(String name,String type, long amount, long cost, double atk, double def, double range)
    {
        this.type = type;
        this.name = name;
        this.amount = amount;
        this.cost = cost;
        this.def = def;
        this.atk = atk;
        this.range = range;
    }

    public Unit(String name,String type, long amount, long cost, double multiplier, double atk, double def, double range)
    {
        this.name = name;
        this.type = type;
        this.amount = amount;
        this.cost = cost;
        this.multiplier = multiplier;
        this.def = def;
        this.atk = atk;
        this.range = range;
    }

    public void add()
    {
        amount++;
        if(type.equals("quality"))
            cost = (long)Math.pow(cost, 1.2);
        else
            cost = (long)Math.ceil(cost*1.1);
    }

    public String getName()
    {
        return name;
    }

    public String getType()
    {
        return type;
    }

    public double getAtk() {
        return atk;
    }

    public long getAmount() {
        return amount;
    }

    public long getCost() {
        return cost;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public void setAtk(double atk) {
        this.atk = atk;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }

    public void setDef(double def) {
        this.def = def;
    }

    public void setMultiplier(double multiplier) {
        this.multiplier = multiplier;
    }

    public double getMultiplier()
    {
        return multiplier;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRange(double range) {
        this.range = range;
    }
}
