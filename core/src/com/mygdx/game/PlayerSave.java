package com.mygdx.game;

import java.util.ArrayList;

/**
 * Created by mahmo266317 on 5/4/2015.
 */
public class PlayerSave {
    private ArrayList<Restaurant> restaurants;
    private ArrayList<Restaurant> potentialRestaurants;
    private ArrayList<Territory> territories;
    private ArrayList<Territory> potentialTerritories;
    int current;
    double total;
    boolean country, save;
    public PlayerSave()
    {
        save = true;
        restaurants = new ArrayList<Restaurant>();
        potentialRestaurants = new ArrayList<Restaurant>();
        territories = new ArrayList<Territory>();
        potentialTerritories = new ArrayList<Territory>();
        current = 0;
        total = 10000000;
    }

    public boolean init()
    {
        restaurants = new ArrayList<Restaurant>();
        return restaurants.add(new Restaurant());
    }

    public boolean reset()
    {
        restaurants = new ArrayList<Restaurant>();
        potentialRestaurants = new ArrayList<Restaurant>();
        territories = new ArrayList<Territory>();
        potentialTerritories = new ArrayList<Territory>();
        current = 0;
        total = 0;
        restaurants = new ArrayList<Restaurant>();
        country = false;
        return restaurants.add(new Restaurant());
    }

    public ArrayList<Restaurant> getRestaurants()
    {
        return restaurants;
    }

    public ArrayList<Restaurant> getPotentialRestaurants()
    {
        return potentialRestaurants;
    }

    public ArrayList<Territory> getTerritories()
    {
        return territories;
    }

    public ArrayList<Territory> getPotentialTerritories()
    {
        return potentialTerritories;
    }

    public Restaurant getCurrentRestaurant()
    {
        return restaurants.get(current);
    }

    public Territory getCurrentTerritory()
    {
        return territories.get(current);
    }

    public void setCurrent(int current)
    {
        this.current = current;
    }

    public void setTotal(double total)
    {
        this.total = total;
    }

    public double getTotal()
    {
        return total;
    }
}
