package board.tiles.property;

import static board.Board.getTilePosition;
import players.Player;
import javax.swing.*;
import java.util.ArrayList;

/**
 * Represents a Station tile on the board.
 * Stations can be purchased, and rent is dynamically calculated based on
 * how many stations the owner holds. If a player owns multiple stations,
 * they can travel between them.
 */
public class Station extends Property {

    /**
     * Constructs a Station with the given name.
     * All stations have a fixed price of $200 and base rent of $25.
     *
     * @param name the name of the station
     */
    public Station(String name) {
        super(name, 200, 25, "station");
    }

    /**
     * Handles the action when a player lands on this station.
     * - If unowned: player is prompted to purchase.
     * - If owned by another player: rent is paid based on number of stations owned.
     * - If owned by the current player and they own multiple stations: they are offered to travel.
     *
     * @param player the player who landed on the tile
     */
    @Override
    public void tileAction(Player player) {
        if (!isOwned()) {
            if (player.getMoney() >= getPrice()) {
                int choice = JOptionPane.showConfirmDialog(
                        null,
                        player.getName() + " landed on " + getName() + " which costs $200.\nDo you want to buy it?",
                        "Buy Station",
                        JOptionPane.YES_NO_OPTION
                );

                if (choice == JOptionPane.YES_OPTION) {
                    buy(player);
                    player.addStation(this);
                    JOptionPane.showMessageDialog(null, player.getName() + " bought " + getName() + "!");
                } else {
                    JOptionPane.showMessageDialog(null, player.getName() + " chose not to buy " + getName() + ".");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Not enough money to buy this.");
            }
        } else if (!player.equals(getOwner())) {
            int numberOfStations = getOwner().getNumberOfStations();
            int rent = 25 * numberOfStations;
            player.pay(rent);
            getOwner().receive(rent);
            JOptionPane.showMessageDialog(null, player.getName() + " paid $" + rent + " rent to " + getOwner().getName() + ".");
        } else {
            JOptionPane.showMessageDialog(null, player.getName() + " landed on their own station " + getName() + ".");

            if (player.getNumberOfStations() > 1) {
                int choice = JOptionPane.showConfirmDialog(
                        null,
                        "Do you want to travel to another station?",
                        "Travel to Station",
                        JOptionPane.YES_NO_OPTION
                );

                if (choice == JOptionPane.YES_OPTION) {
                    ArrayList<Station> ownedStations = player.getStations();
                    ownedStations.remove(this);  // Remove current station

                    String[] options = new String[ownedStations.size()];
                    for (int i = 0; i < ownedStations.size(); i++) {
                        options[i] = ownedStations.get(i).getName();
                    }

                    String destinationName = (String) JOptionPane.showInputDialog(
                            null,
                            "Choose a station to travel to:",
                            "Select Station",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            options,
                            options[0]
                    );

                    if (destinationName != null) {
                        player.moveTo(getTilePosition(destinationName));
                        JOptionPane.showMessageDialog(null, player.getName() + " traveled to " + destinationName + ".");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, player.getName() + " chose not to travel.");
                }
            }
        }
    }
}
