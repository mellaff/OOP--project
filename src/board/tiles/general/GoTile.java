package board.tiles.general;

import players.Player;

/**
 * Represents the "Go" tile on the board.
 * When a player lands on this tile, they receive a monetary bonus.
 */
public class GoTile extends Tile {

    /** The bonus amount a player receives when landing on or passing "Go". */
    private final int bonus = 200;

    /**
     * Constructs a GoTile with default settings.
     */
    public GoTile() {
        super();
    }

    /**
     * Returns the type of this tile.
     *
     * @return a string indicating this tile is of type "Go"
     */
    @Override
    public String getType() {
        return "Go";
    }

    /**
     * Handles the action when a player lands directly on the "Go" tile.
     * Awards the player a fixed bonus.
     *
     * @param player the player who landed on the tile
     */
    @Override
    public void tileAction(Player player) {
        player.receive(bonus);
        System.out.println(player.getName() + " landed on GO! Collect $" + bonus);
    }
}
