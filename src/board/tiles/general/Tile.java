package board.tiles.general;
import players.Player;

public abstract class Tile {
    protected final String name;

    public Tile() {
        this.name = getType();
    }

    public Tile(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract String getType();

    //The action that happens when a player lands on a tile
    public abstract void tileAction(Player player);
    public void tileAction(Player player, Player[] allPlayers) {

    }
}
