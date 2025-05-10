package board.tiles.general;

import cards.ChanceCard;
import players.Player;
import javax.swing.*;  // Add this for GUI dialogs

/**
 * Represents a tile on the board that triggers a Chance card draw.
 * When a player lands on this tile, they draw a Chance card and the effect of the card is applied.
 */
public class ChanceTile extends Tile {

    /**
     * Constructs a ChanceTile with the specified name.
     *
     * @param name the name of the Chance tile
     */
    public ChanceTile(String name) {
        super(name);
    }

    /**
     * Returns the type of the tile.
     *
     * @return a string indicating this tile is of type "Chance"
     */
    @Override
    public String getType() {
        return "Chance";
    }

    /**
     * Handles the tile action for a single player.
     * This method is intentionally left empty as Chance cards require knowledge of all players.
     *
     * @param player the player who landed on the tile
     */
    @Override
    public void tileAction(Player player) {
        // No action here; use tileAction(Player, Player[]) instead
    }

    /**
     * Handles the tile action when a player lands on this tile.
     * Draws a Chance card and applies its effect.
     *
     * @param player      the player who landed on the tile
     * @param allPlayers  all players in the game, for cards affecting multiple players
     */
    @Override
    public void tileAction(Player player, Player[] allPlayers) {
        JOptionPane.showMessageDialog(null, player.getName() + " landed on Chance! Drawing a card...");

        ChanceCard card = ChanceCard.drawCard();

        if (card != null) {
            JOptionPane.showMessageDialog(null, "Card drawn: " + card.getDescription());
            card.applyChanceEffect(player, allPlayers);
        } else {
            JOptionPane.showMessageDialog(null, "No Chance cards remaining.");
        }
    }
}
