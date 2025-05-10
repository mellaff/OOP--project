package board.tiles.general;

import cards.CommunityChestCard;
import players.Player;

/**
 * Represents a tile on the board that triggers a Community Chest card draw.
 * When a player lands on this tile, they draw a Community Chest card and apply its effect.
 */
public class CommunityChestTile extends Tile {

    /**
     * Constructs a CommunityChestTile with the specified name.
     *
     * @param name the name of the Community Chest tile
     */
    public CommunityChestTile(String name) {
        super(name);
    }

    /**
     * Returns the type of the tile.
     *
     * @return a string indicating this tile is of type "Community Chest"
     */
    @Override
    public String getType() {
        return "Community Chest";
    }

    /**
     * Handles the tile action for a single player.
     * This method is intentionally left empty, as Community Chest cards may affect multiple players.
     *
     * @param player the player who landed on the tile
     */
    @Override
    public void tileAction(Player player) {
        // No action here; use tileAction(Player, Player[]) instead
    }

    /**
     * Handles the tile action when a player lands on this tile.
     * Draws a Community Chest card and applies its effect.
     *
     * @param player      the player who landed on the tile
     * @param allPlayers  all players in the game, for cards that affect multiple players
     */
    public void tileAction(Player player, Player[] allPlayers) {
        System.out.println(player.getName() + " landed on Community Chest! Drawing a card...");
        CommunityChestCard card = CommunityChestCard.drawCard();

        if (card != null) {
            System.out.println(card.getDescription() + " " + card.getEffect());
            card.applyChestEffect(player, allPlayers);
        } else {
            System.out.println("No Community Chest cards remaining.");
        }
    }
}