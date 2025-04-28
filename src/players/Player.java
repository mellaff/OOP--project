package players;

public class Player {
    private String name;
    private int money;
    private int position;
    private boolean inJail;
    private int jailTurns;
    private boolean bankrupt;
    private int getOutOfJailFreeCards;
    private boolean missTurn; // Added to support 'miss 1 turn'

    public Player(String var1) {
        this.name = var1;
        this.money = 2000;
        this.position = 0;
        this.inJail = false;
        this.jailTurns = 0;
        this.bankrupt = false;
        this.getOutOfJailFreeCards = 0;
        this.missTurn = false;
    }

    public void receive(int var1) {
        this.money += var1;
    }

    public void pay(int var1) {
        this.money -= var1;
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

    public void move(int var1) {
        this.position = (this.position + var1 + 40) % 40;
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

    // Getter Methods
    public String getName() {
        return this.name;
    }

    public int getPosition() {
        return this.position;
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

    public void moveToNearestCultureCenter() {

    }
}
