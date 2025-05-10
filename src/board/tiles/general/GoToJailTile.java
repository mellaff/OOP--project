package board.tiles.general;

import players.Player;

/**
 * Represents the "Go To Jail" tile on the board.
 * When a player lands on this tile, they are immediately sent to jail.
 */
public class GoToJailTile extends Tile {

    /** The board position of the jail tile. */
    private final int jailPosition;

    /**
     * Constructs a GoToJailTile with the specified jail position.
     *
     * @param jailPosition the board index where the jail is located
     */
    public GoToJailTile(int jailPosition) {
        super("Go To Jail");
        this.jailPosition = jailPosition;
    }

    /**
     * Gets the position of the jail tile.
     *
     * @return the index of the jail tile on the board
     */
    public int getJailPosition() {
        return jailPosition;
    }

    /**
     * Returns the type of this tile.
     *
     * @return a string indicating this tile is of type "Go To Jail"
     */
    @Override
    public String getType() {
        return "Go To Jail";
    }

    /**
     * Handles the action when a player lands on the "Go To Jail" tile.
     * The player is moved to jail and marked as being in jail.
     *
     * @param player the player who landed on the tile
     */
    @Override
    public void tileAction(Player player) {
        System.out.println(player.getName() + " landed on 'Go To Jail'!");
        System.out.println("Go directly to jail. Do not pass GO. Do not collect $200.");

        player.moveTo(jailPosition);
        player.goToJail();
    }
}
