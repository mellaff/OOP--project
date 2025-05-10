package players;

import board.tiles.property.Property;
import board.tiles.property.Station;

import java.awt.*;
import java.util.ArrayList;

/**
 * Represents a player in the game, tracking their state such as money, position,
 * properties, jail status, and special conditions.
 */
public class Player {

    private String name;
    private int money;
    private int position;
    private boolean inJail;
    private int jailTurns;
    private boolean bankrupt;
    private int getOutOfJailFreeCards;
    private boolean missTurn;
    private ArrayList<Property> properties;
    private boolean hasDiscount;
    private Color color;
    private ArrayList<Station> stations;
    private boolean passedGo = false;
    private boolean moveToNearestCity;
    private boolean moveToNearestStation;

    /**
     * Constructs a player with the given name and default settings.
     *
     * @param name the name of the player
     */
    public Player(String name) {
        this.name = name;
        this.money = 2000;
        this.position = 0;
        this.inJail = false;
        this.jailTurns = 0;
        this.bankrupt = false;
        this.getOutOfJailFreeCards = 0;
        this.missTurn = false;
        this.properties = new ArrayList<>();
        this.stations = new ArrayList<>();
        this.hasDiscount = false;
    }

    /**
     * Constructs a player with a given name and token color.
     *
     * @param name  the name of the player
     * @param color the color representing the player
     */
    public Player(String name, Color color) {
        this(name);
        this.color = color;
    }

    /**
     * Increases the player's money by the given amount.
     *
     * @param money the amount to add
     */
    public void receive(int money) {
        this.money += money;
    }

    /**
     * Decreases the player's money by the given amount.
     * Sets the player as bankrupt if their money drops below zero.
     *
     * @param money the amount to subtract
     */
    public void pay(int money) {
        this.money -= money;
        if (this.money < 0) {
            this.bankrupt = true;
        }
    }

    /**
     * Gets the player's token color.
     *
     * @return the color of the player
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sends the player to jail and sets the jail turn counter.
     */
    public void goToJail() {
        this.inJail = true;
        this.jailTurns = 3;
        this.position = 10; // Assuming jail is at position 10
    }

    /**
     * Releases the player from jail.
     */
    public void getOutOfJail() {
        this.inJail = false;
        this.jailTurns = 0;
    }

    /**
     * Decreases the number of turns remaining in jail by one.
     * Releases the player if the counter reaches zero.
     */
    public void decreaseJailTurn() {
        if (this.jailTurns > 0) {
            --this.jailTurns;
            if (this.jailTurns == 0) {
                this.inJail = false;
            }
        }
    }

    /**
     * Adds a Get Out of Jail Free card to the player's inventory.
     */
    public void addGetOutOfJailFreeCard() {
        ++this.getOutOfJailFreeCards;
    }

    /**
     * Uses a Get Out of Jail Free card if the player has one.
     *
     * @return true if the card was used, false otherwise
     */
    public boolean useGetOutOfJailFreeCard() {
        if (this.getOutOfJailFreeCards > 0) {
            --this.getOutOfJailFreeCards;
            this.getOutOfJail();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Moves the player forward or backward on the board.
     * Updates `passedGo` if the player crosses position 0.
     *
     * @param position number of spaces to move (can be negative)
     */
    public void move(int position) {
        if ((this.position + position + 40) % 40 < this.position) {
            passedGo = true;
        }
        this.position = (this.position + position + 40) % 40;
    }

    /**
     * Checks if the player passed the "Go" tile during their last move.
     *
     * @return true if passed, false otherwise
     */
    public boolean passedGo() {
        return passedGo;
    }

    /**
     * Sets whether the player has passed "Go".
     *
     * @param passed whether the player passed "Go"
     */
    public void setPassedGo(boolean passed) {
        this.passedGo = passed;
    }

    /**
     * Moves the player directly to the specified board position.
     *
     * @param newPosition the destination position
     */
    public void moveTo(int newPosition) {
        if ((newPosition % 40) < this.position) {
            passedGo = true;
        }
        this.position = newPosition % 40;
    }

    /**
     * Checks if the player should skip their turn.
     *
     * @return true if turn should be skipped
     */
    public boolean isMissTurn() {
        return missTurn;
    }

    /**
     * Sets whether the player should skip their next turn.
     *
     * @param missTurn true to skip next turn
     */
    public void setMissTurn(boolean missTurn) {
        this.missTurn = missTurn;
    }

    /**
     * Adds a property to the player's collection.
     *
     * @param property the property to add
     */
    public void addProperty(Property property) {
        properties.add(property);
    }

    /**
     * Adds a station to the player's collection.
     *
     * @param station the station to add
     */
    public void addStation(Station station) {
        stations.add(station);
    }

    /**
     * Removes a property by name (not implemented correctly — to be fixed if needed).
     *
     * @param propertyName the name of the property to remove
     */
    public void removeProperty(String propertyName) {
        properties.remove(propertyName);
    }

    /**
     * Gets the player's name.
     *
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the player's current board position.
     *
     * @return the position
     */
    public int getPosition() {
        return this.position;
    }

    /**
     * Gets the list of properties the player owns.
     *
     * @return the list of properties
     */
    public ArrayList<Property> getProperties() {
        return properties;
    }

    /**
     * Gets the list of stations the player owns.
     *
     * @return the list of stations
     */
    public ArrayList<Station> getStations() {
        return stations;
    }

    /**
     * Gets the player's current money.
     *
     * @return the money
     */
    public int getMoney() {
        return this.money;
    }

    /**
     * Checks if the player is in jail.
     *
     * @return true if in jail
     */
    public boolean isInJail() {
        return this.inJail;
    }

    /**
     * Gets the number of turns remaining in jail.
     *
     * @return the jail turn counter
     */
    public int getJailTurns() {
        return this.jailTurns;
    }

    /**
     * Checks if the player is bankrupt.
     *
     * @return true if bankrupt
     */
    public boolean isBankrupt() {
        return this.bankrupt;
    }

    /**
     * Gets the number of Get Out of Jail Free cards the player owns.
     *
     * @return the count
     */
    public int getGetOutOfJailFreeCards() {
        return this.getOutOfJailFreeCards;
    }

    /**
     * Flags the player to receive a discount on the next payment.
     *
     * @return always returns true
     */
    public boolean setNextDiscount() {
        return hasDiscount = true;
    }

    /**
     * Clears the discount flag after use.
     *
     * @return always returns false
     */
    public boolean useDiscount() {
        return hasDiscount = false;
    }

    /**
     * Checks if the player has a discount.
     *
     * @return true if the player has a discount
     */
    public boolean hasDiscount() {
        return hasDiscount;
    }

    /**
     * Gets the number of stations the player owns.
     *
     * @return the number of stations
     */
    public int getNumberOfStations() {
        return stations.size();
    }

    /**
     * Sets the flag to move the player to the nearest city.
     *
     * @param move true to enable
     */
    public void setMoveToNearestCity(boolean move) {
        moveToNearestCity = move;
    }

    /**
     * Checks if the player should move to the nearest city.
     *
     * @return true if movement is flagged
     */
    public boolean moveToNearestCity() {
        return moveToNearestCity;
    }

    /**
     * Sets the flag to move the player to the nearest station.
     *
     * @param move true to enable
     */
    public void setMoveToNearestStation(boolean move) {
        moveToNearestStation = move;
    }

    /**
     * Checks if the player should move to the nearest station.
     *
     * @return true if movement is flagged
     */
    public boolean moveToNearestStation() {
        return moveToNearestStation;
    }
}
