package com.jadventure.game.navigation;

/**
 * Created with IntelliJ IDEA.
 * User: Cage
 * Date: 23/11/13
 * Time: 10:19 PM
 * To change this template use File | Settings | File Templates.
 */
public final class Exit {
    private String description;
    private ILocation location;
    // We can implement more functionality on the exits later, for example locked exits, or requisites to pass through

    public Exit() {

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ILocation getLocation() {
        return location;
    }

    public void setLocation(ILocation location) {
        this.location = location;
    }

    public void print(Direction direction) {
        System.out.println(direction.getDescription() + " " + getDescription());
    }
}
