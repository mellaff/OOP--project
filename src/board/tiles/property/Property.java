package board.tiles.property;

import board.tiles.general.Tile;
import players.Player;
import javax.swing.*;
import java.awt.*;

/**
 * Represents a purchasable property tile on the board.
 * Players can buy this tile, pay rent if it's owned by another player,
 * and use a discount if applicable.
 */
public class Property extends Tile {
    private int price;
    private int rent;
    private Player owner;
    private String group;
    private Color color;

    /**
     * Constructs a Property with the specified attributes.
     *
     * @param name  the name of the property
     * @param price the purchase price of the property
     * @param rent  the rent amount to be paid when landed on
     * @param group the property group or color set
     */
    public Property(String name, int price, int rent, String group) {
        super(name);
        this.price = price;
        this.rent = rent;
        this.owner = null;
        this.group = group;
    }

    /**
     * Sets the owner of this property.
     *
     * @param owner the player who owns the property
     */
    public void setOwner(Player owner) {
        this.owner = owner;
    }

    /**
     * Checks whether this property is owned.
     *
     * @return true if the property has an owner; false otherwise
     */
    public boolean isOwned() {
        return owner != null;
    }

    /**
     * Returns the owner of this property.
     *
     * @return the player who owns this property
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * Returns the type of tile.
     *
     * @return "Property"
     */
    @Override
    public String getType() {
        return "Property";
    }

    /**
     * Returns the group or color set of the property.
     *
     * @return the group name
     */
    public String getGroup() {
        return group;
    }

    /**
     * Returns the rent value of the property.
     *
     * @return the rent amount
     */
    public int getRent() {
        return rent;
    }

    /**
     * Returns the purchase price of the property.
     *
     * @return the price
     */
    public int getPrice() {
        return price;
    }

    /**
     * Allows a player to buy this property if it is not already owned.
     * If the player has a discount, it reduces the price by 20%.
     *
     * @param player the player attempting to purchase the property
     */
    public void buy(Player player) {
        if (player.hasDiscount() && !isOwned() && player.getMoney() >= price - (price * 2) / 10) {
            int discountedPrice = price - (price * 2) / 10;
            player.useDiscount();
            JOptionPane.showMessageDialog(null, "Bought with discount.");
            player.pay(discountedPrice);
        } else if (!isOwned() && player.getMoney() >= price) {
            player.pay(price);
        }
        this.owner = player;
        System.out.println(player.getName() + " bought " + name);
    }

    /**
     * Executes the action when a player lands on this tile.
     * - If unowned, they are given the option to buy.
     * - If owned by another player, they must pay rent.
     * - If owned by themselves, a message is shown.
     *
     * @param player the player who landed on the tile
     */
    @Override
    public void tileAction(Player player) {
        if (!isOwned()) {
            boolean canAffordWithDiscount = player.hasDiscount() && player.getMoney() >= price - (price * 2) / 10;
            boolean canAffordWithoutDiscount = player.getMoney() >= price;

            if (canAffordWithDiscount || canAffordWithoutDiscount) {
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
            } else {
                JOptionPane.showMessageDialog(null, "Not enough money to buy this.");
            }

        } else if (!player.equals(owner)) {
            player.pay(rent);
            owner.receive(rent);
            JOptionPane.showMessageDialog(null, player.getName() + " paid $" + rent + " rent to " + owner.getName() + ".");
        } else {
            JOptionPane.showMessageDialog(null, player.getName() + " landed on their own property " + name + ".");
        }
    }
}
