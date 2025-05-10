package board.tiles.general;

import players.Player;

/**
 * Represents the Free Parking tile on the board.
 * When a player lands on this tile, they rest and skip their next turn.
 */
public class FreeParkingTile extends Tile {

    /**
     * Constructs a FreeParkingTile with default name and settings.
     */
    public FreeParkingTile() {
        super();
    }

    /**
     * Returns the type of the tile.
     *
     * @return a string indicating this tile is of type "Free Parking"
     */
    @Override
    public String getType() {
        return "Free Parking";
    }

    /**
     * Handles the action when a player lands on the Free Parking tile.
     * The player is set to miss their next turn as a resting effect.
     *
     * @param player the player who landed on the tile
     */
    @Override
    public void tileAction(Player player) {
        System.out.println(player.getName() + " is resting at Free Parking. They will skip their next turn.");
        player.setMissTurn(true);
    }
}
