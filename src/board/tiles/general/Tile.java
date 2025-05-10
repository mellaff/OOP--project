package board.tiles.general;

import players.Player;

/**
 * Represents an abstract tile on the game board.
 * All specific tile types (e.g., Property, Go, Jail) should extend this class
 * and implement the required behavior.
 */
public abstract class Tile {

    /** The display name of the tile. */
    protected final String name;

    /**
     * Constructs a tile using its type as the default name.
     */
    public Tile() {
        this.name = getType();
    }

    /**
     * Constructs a tile with a specified name.
     *
     * @param name the name of the tile
     */
    public Tile(String name) {
        this.name = name;
    }

    /**
     * Returns the name of the tile.
     *
     * @return the tile name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the type of the tile.
     * Each subclass must define its own type (e.g., "Property", "Go", "Jail").
     *
     * @return the type of the tile
     */
    public abstract String getType();

    /**
     * Defines the action that occurs when a player lands on this tile.
     *
     * @param player the player who landed on the tile
     */
    public abstract void tileAction(Player player);

    /**
     * Optional overload for tile actions involving multiple players.
     * Subclasses can override this if needed (e.g., Chance or Community Chest cards).
     *
     * @param player      the player who landed on the tile
     * @param allPlayers  all players in the game
     */
    public void tileAction(Player player, Player[] allPlayers) {
        // Optional: can be overridden by specific tiles
    }
}
