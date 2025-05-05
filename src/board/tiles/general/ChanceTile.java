package board.tiles.general;

import cards.ChanceCard;
import players.Player;
import javax.swing.*;  // Add this for GUI dialogs

public class ChanceTile extends Tile {

    public ChanceTile(String name) {
        super(name);
    }

    @Override
    public String getType() {
        return "Chance";
    }

    @Override
    public void tileAction(Player player) {
        // Optional: if some logic only needs single player
    }

    @Override
    public void tileAction(Player player, Player[] allPlayers) {
        JOptionPane.showMessageDialog(null, player.getName() + " landed on Chance! Drawing a card...");

        ChanceCard card = ChanceCard.drawCard();

        if (card != null) {
            JOptionPane.showMessageDialog(null, "Card drawn: " + card.getDescription());
            // Apply the card's effect to the player and other players
            card.applyChanceEffect(player, allPlayers);
        } else {
            JOptionPane.showMessageDialog(null, "No Chance cards remaining.");
        }
    }
}
