package board.tiles.property;

import board.tiles.general.Tile;
import players.Player;
import javax.swing.*;
import java.awt.*;

public class Property extends Tile {
    private int price;
    private int rent;
    private Player owner;
    private String group;
    private Color color;

    public Property(String name, int price, int rent,String group) {
        super(name);
        this.price = price;
        this.rent = rent;
        this.owner = null;
        this.group = group;

    }

    public void setOwner(Player owner) {
        this.owner = owner;
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

    public String getGroup() {
        return group;
    }
    public int getRent() {
        return rent;
    }

    public int getPrice() {
        return price;
    }

    //Method of the property to be bought
    public void buy(Player player) {
        if (!isOwned() && player.getMoney() >= price) {
            player.pay(price);
            this.owner = player;
            System.out.println(player.getName() + " bought " + name);
        }
    }

    @Override
    public void tileAction(Player player) {
        if (!isOwned()) {
            if(player.getMoney() >= price) {
                int choice = JOptionPane.showConfirmDialog(
                        null,
                        player.getName() + " landed on " + name + " which costs $" + price + ".\nDo you want to buy it?",
                        "Buy Property",
                        JOptionPane.YES_NO_OPTION
                );

                if (choice == JOptionPane.YES_OPTION) {
                    buy(player);
                    player.addProperty(this);
                    JOptionPane.showMessageDialog(null, player.getName() + " bought " + name + "!");
                } else {
                    JOptionPane.showMessageDialog(null, player.getName() + " chose not to buy " + name + ".");
                }
            } else{
                JOptionPane.showMessageDialog(null, " Not enough money to buy this. ");
            }
        } else if (!player.equals(owner)) {
            player.pay(rent);
            owner.receive(rent);
            JOptionPane.showMessageDialog(null, player.getName() + " paid $" + rent + " rent to " + owner.getName() + ".");
        } else {
            // Optional: Show message if they landed on their own property
            JOptionPane.showMessageDialog(null, player.getName() + " landed on their own property " + name + ".");
        }
    }

}
