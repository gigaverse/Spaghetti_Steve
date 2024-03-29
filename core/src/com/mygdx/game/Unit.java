package com.mygdx.game;

/**
 * Created by Gigaverse on 5/4/2015.
 */
public class Unit {
    private String name, type;
    private long amount;
    private double multiplier,atk,def,range, mult, cost;

    public Unit()
    {
        this.name = "";
    }

    public Unit(String name,String type,  long amount, double cost, double multiplier)
    {
        this.type = type;
        this.name = name;
        this.amount = amount;
        this.cost = cost;
        this.multiplier = multiplier;
        mult = 1;
    }

    public Unit(String name,String type, long amount, double cost, double atk, double def, double range)
    {
        this.type = type;
        this.name = name;
        this.amount = amount;
        this.cost = cost;
        this.def = def;
        this.atk = atk;
        this.range = range;
        mult = 1;
    }

    public Unit(String name,String type, long amount, double cost, double multiplier, double atk, double def, double range)
    {
        this.name = name;
        this.type = type;
        this.amount = amount;
        this.cost = cost;
        this.multiplier = multiplier;
        this.def = def;
        this.atk = atk;
        this.range = range;
        mult = 1;
    }

    public void add()
    {
        amount++;
        if(type.equals("quality"))
            cost = (long)Math.pow(cost, 1.2);
        else
            cost = (long)Math.ceil(cost*1.1);
        if(amount%25 == 0)
            mult *=2;
        if(amount % 100 == 0)
            mult *= 4;
    }

    public String getName()
    {
        String s = "";
        if(name.equals("Michelin Stars"))
        {
            for(int i = 0; i < amount; i++)
            {
                s += "★";
            }
        }

        return name + s + (mult > 1 && !type.equals("quality") ? " x " + (int)mult : "");
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

    public double getCost() {
        return cost;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public void setAtk(double atk) {
        this.atk = atk;
    }

    public void setCost(double cost) {
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
        return multiplier*mult;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRange(double range) {
        this.range = range;
    }
}
