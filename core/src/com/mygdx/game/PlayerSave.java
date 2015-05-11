package com.mygdx.game;

import java.util.ArrayList;

/**
 * Created by mahmo266317 on 5/4/2015.
 */
public class PlayerSave {
    private ArrayList<Restaurant> restaurants;
    private ArrayList<Restaurant> potential;
    int currentRestaurant;
    double total;
    public PlayerSave()
    {
        restaurants = new ArrayList<Restaurant>();
        potential = new ArrayList<Restaurant>();
        currentRestaurant = 0;
        total = 0;
    }

    public boolean init()
    {
        restaurants = new ArrayList<Restaurant>();
        return restaurants.add(new Restaurant());
    }

    public boolean reset()
    {
        restaurants = new ArrayList<Restaurant>();
        potential = new ArrayList<Restaurant>();
        currentRestaurant = 0;
        total = 0;
        restaurants = new ArrayList<Restaurant>();
        return restaurants.add(new Restaurant());
    }

    public ArrayList<Restaurant> getRestaurants()
    {
        return restaurants;
    }

    public ArrayList<Restaurant> getPotential()
    {
        return potential;
    }

    public Restaurant getCurrentRestaurant()
    {
        return restaurants.get(currentRestaurant);
    }

    public void setCurrentRestaurant(int currentRestaurant)
    {
        this.currentRestaurant = currentRestaurant;
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
