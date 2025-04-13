package players;

public class Player {
    private String name;
    private int position;
    private int money;
    private boolean inJail;
    private int jailTurns;
    private int getOutOfJailFreeCards;
    private boolean bankrupt;

    public Player(String name) {
        this.name = name;
        this.money = 2000; // Default starting money
        this.position = 0;
        this.inJail = false;
        this.jailTurns = 0;
        this.getOutOfJailFreeCards = 0;
        this.bankrupt = false;
    }

    public void move(int steps) {
        position = (position + steps) % 40;
    }

    public void pay(int amount) {
        if (money >= amount){
            money -= amount;
        }
        else
            System.out.println("No sufficent balance. You become a bankrupt.");
            bunkrupt = true;
        
        }
    }

    public void receive(int amount) {
        money += amount;
    }

    public void goToJail() {
        inJail = true;
        jailTurns = 3;
        position = 10;
    }

    public void releaseFromJail() {
        inJail = false;
        jailTurns = 0;
    }

    public void decrementJailTurn() {
        if (jailTurns > 0) {
            jailTurns--;
            if (jailTurns == 0) {
                releaseFromJail();
            }
        }
    }

    public void addGetOutOfJailFreeCard() {
        getOutOfJailFreeCards++;
    }

    public void useGetOutOfJailFreeCard() {
        if (getOutOfJailFreeCards > 0) {
            getOutOfJailFreeCards--;
            releaseFromJail();
        }
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public int getMoney() {
        return money;
    }

    public boolean isInJail() {
        return inJail;
    }

    public int getJailTurns() {
        return jailTurns;
    }

    public boolean isBankrupt() {
        return bankrupt;
    }

    public int getGetOutOfJailFreeCards() {
        return getOutOfJailFreeCards;
    }
}
