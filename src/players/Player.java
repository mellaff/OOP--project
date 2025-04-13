package players;

public class Player {
    private String name;
    private int money;
    public boolean started = false;
    public Player(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addMoney(double money) {
        this.money += money;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public boolean removeMoney(int money) {
        if(this.money < money) {
            return false;
        } else{
            this.money -= money;
            return true;
        }
    }
}
