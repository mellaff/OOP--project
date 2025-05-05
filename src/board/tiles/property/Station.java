package board.tiles.property;

import static board.Board.getTilePosition;
import players.Player;
import javax.swing.*;  // Add this for GUI dialogs
import java.util.ArrayList;
import java.util.List;

public class Station extends Property {

    public Station(String name) {
        super(name, 200, 25, "station");
    }

    @Override
    public void tileAction(Player player) {
        if (!isOwned()) {
            if(player.getMoney() >= getPrice()) {
                int choice = JOptionPane.showConfirmDialog(
                        null,
                        player.getName() + " landed on " + getName() + " which costs $200.\nDo you want to buy it?",
                        "Buy Station",
                        JOptionPane.YES_NO_OPTION
                );

                if (choice == JOptionPane.YES_OPTION) {
                    buy(player);
                    player.addStation(this); // Assuming Player has addProperty method
                    JOptionPane.showMessageDialog(null, player.getName() + " bought " + getName() + "!");
                } else {
                    JOptionPane.showMessageDialog(null, player.getName() + " chose not to buy " + getName() + ".");
                }
            } else{
                JOptionPane.showMessageDialog(null, " Not enough money to buy this. ");
            }
        } else if (!player.equals(getOwner())) {
            int numberOfStations = getOwner().getNumberOfStations(); // Make sure this is implemented in Player
            int rent = 25 * numberOfStations; // Rent increases per station owned
            player.pay(rent);
            getOwner().receive(rent);
            JOptionPane.showMessageDialog(null, player.getName() + " paid $" + rent + " rent to " + getOwner().getName() + ".");
        } else {
            JOptionPane.showMessageDialog(null, player.getName() + " landed on their own station " + getName() + ".");
            if(player.getNumberOfStations()>1){
                int choice = JOptionPane.showConfirmDialog(
                        null,
                        "Do you want to travel to another station?",
                        "Travel to Station",
                        JOptionPane.YES_NO_OPTION
                );
                if(choice == JOptionPane.YES_OPTION){
                    ArrayList<Station> ownedStations = player.getStations();
                    // Remove the current station from the list
                    ownedStations.remove(this);

                    // Convert to array for JOptionPane
                    String[] options = new String[ownedStations.size()];
                    for(int i=0;i<ownedStations.size();i++){
                        options[i]=ownedStations.get(i).getName();
                    }

                   String destinationName = (String)JOptionPane.showInputDialog(
                            null,
                            "Choose a station to travel to:",
                            "Select Station",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            options,
                            options[0]
                    );

                    if (destinationName != null) {
                        player.moveTo(getTilePosition(destinationName)); // move the player
                        JOptionPane.showMessageDialog(null, player.getName() + " traveled to " + destinationName + ".");
                    }
                } else{
                    JOptionPane.showMessageDialog(null, player.getName() + " chose not to travel.");
                }
            }

        }
    }
}
