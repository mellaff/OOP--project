package board.tiles.general;

import players.Player;

/**
 * Represents the Jail tile on the board.
 * Landing on this tile typically places the player in jail.
 */
public class JailTile extends Tile {

    /**
     * Constructs a JailTile with default settings.
     */
    public JailTile() {
        super();
    }

    /**
     * Returns the type of this tile.
     *
     * @return a string indicating this tile is of type "Jail"
     */
    @Override
    public String getType() {
        return "Jail";
    }

    /**
     * Handles the action when a player lands on the Jail tile.
     * By default, this sends the player to jail.
     *
     * @param player the player who landed on the tile
     */
    @Override
    public void tileAction(Player player) {
        player.goToJail();
    }
}
