package board.tiles.general;

import players.Player;

public class JailTile extends Tile {

    public JailTile() {
        super();
    }

    @Override
    public String getType() {
        return "Jail";
    }

    @Override
    public void tileAction(Player player) {
        player.goToJail();
    }

}
