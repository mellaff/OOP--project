package players;

import board.tiles.property.Property;
import board.tiles.property.Station;

import java.awt.*;
import java.util.ArrayList;

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
    private boolean hasDiscount;// Added to support 'miss 1 turn'
    private Color color;
    private ArrayList<Station> stations;
    private boolean passedGo = false;
    private boolean moveToNearestCity;
    private boolean moveToNearestStation;

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

    public Player(String name, java.awt.Color color) {
        this(name);
        this.color = color;
    }

    public void receive(int money) {
        this.money += money;
    }

    public void pay(int money) {
        this.money -= money;
        if (this.money < 0) {
            this.bankrupt = true;
        }
    }
    public Color getColor() {
        return color;
    }

    public void goToJail() {
        this.inJail = true;
        this.jailTurns = 3;
        this.position = 10; // Assuming Jail is at position 10
    }

    public void getOutOfJail() {
        this.inJail = false;
        this.jailTurns = 0;
    }

    public void decreaseJailTurn() {
        if (this.jailTurns > 0) {
            --this.jailTurns;
            if (this.jailTurns == 0) {
                this.inJail = false;
            }
        }
    }

    public void addGetOutOfJailFreeCard() {
        ++this.getOutOfJailFreeCards;
    }

    public boolean useGetOutOfJailFreeCard() {
        if (this.getOutOfJailFreeCards > 0) {
            --this.getOutOfJailFreeCards;
            this.getOutOfJail();
            return true;
        } else {
            return false;
        }
    }

    public void move(int position) {
        if((this.position + position + 40) % 40<this.position){
            passedGo = true;
        }
        this.position = (this.position + position + 40) % 40;
        // +40 to make sure negative moves (like -3) still wrap correctly
    }

    public boolean passedGo(){
        return passedGo;
    }



    // Move directly to a specific position
    public void moveTo(int newPosition) {
        if((newPosition % 40<this.position)){
            passedGo = true;
        }

        this.position = newPosition % 40;
    }

    public boolean isMissTurn() {
        return missTurn;
    }

    public void setMissTurn(boolean missTurn) {

        this.missTurn = missTurn;
    }

    public void addProperty(Property property) {
        properties.add(property);
    }

    public void addStation(Station station) {
        stations.add(station);
    }

    public void removeProperty(String propertyName) {
        properties.remove(propertyName);
    }

    // Getter Methods
    public String getName() {
        return this.name;
    }

    public int getPosition() {
        return this.position;
    }

    public ArrayList<Property> getProperties() {
        return properties;
    }

    public ArrayList<Station> getStations() {
        return stations;
    }

    public int getMoney() {

        return this.money;
    }

    public boolean isInJail() {

        return this.inJail;
    }

    public int getJailTurns() {

        return this.jailTurns;
    }

    public boolean isBankrupt() {

        return this.bankrupt;
    }

    public int getGetOutOfJailFreeCards() {

        return this.getOutOfJailFreeCards;
    }



    public boolean setNextDiscount() {

        return hasDiscount = true;

    }

    public boolean useDiscount() {

        return hasDiscount = false;
    }

    public int getNumberOfStations() {
        return stations.size();
    }

    public void setMoveToNearestCity(boolean move){
        moveToNearestCity=move;
    }
    public boolean moveToNearestCity(){
        return moveToNearestCity;
    }

    public void setMoveToNearestStation(boolean move){
        moveToNearestStation=move;
    }
    public boolean moveToNearestStation(){
        return moveToNearestStation;
    }



}
