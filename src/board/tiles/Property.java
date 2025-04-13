package board.tiles;

import players.Player;

public class Property extends Tile {
    private int price;
    private int rent;
    private Player owner;

    public Property(String name, int price, int rent) {
        super(name);
        this.price = price;
        this.rent = rent;
        this.owner = null;
    }

    public boolean isOwned() {
        return owner != null;
    }

    public Player getOwner() {
        return owner;
    }

    public String getType(){
        return "Property";
    }

    public void buy(Player player) {
        if (!isOwned() && player.getMoney() >= price) {
            player.removeMoney(price);
            this.owner = player;
            System.out.println(player.getName() + " bought " + name);
        }
    }

    @Override
    public void tileAction(Player player) {
        if (!isOwned()) {

        } else if (!player.equals(owner)) {
            player.removeMoney(rent);
            owner.addMoney(rent);
            System.out.println(player.getName() + " paid $" + rent + " rent to " + owner.getName());
        }
    }
}
