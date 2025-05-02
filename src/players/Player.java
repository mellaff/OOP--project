package players;

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
    private ArrayList<String> properties;
    private boolean hasDiscount;// Added to support 'miss 1 turn'

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
        this.hasDiscount = false;
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
        this.position = (this.position + position + 40) % 40;
        // +40 to make sure negative moves (like -3) still wrap correctly
    }



    // Move directly to a specific position
    public void moveTo(int newPosition) {

        this.position = newPosition % 40;
    }

    // To check if player needs to miss a turn
    public boolean isMissTurn() {
        return missTurn;
    }

    // To set player to miss or not miss a turn
    public void setMissTurn(boolean missTurn) {

        this.missTurn = missTurn;
    }

    public void addProperty(String propertyName) {
        properties.add(propertyName);
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

    public ArrayList<String> getProperties() {
        return properties;
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



}
