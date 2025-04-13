package board.tiles;

import players.Player;

public class GoToJailTile extends Tile {

    private final int jailPosition;

    public GoToJailTile(int jailPosition) {
        super();
        this.jailPosition = jailPosition;
    }

    public int getJailPosition() {
        return jailPosition;
    }

    @Override
    public String getType() {
        return "Go To Jail";
    }

    @Override
    public void tileAction(Player player) {
        System.out.println("Go directly to jail. Do not pass GO. Move to tile " + jailPosition + ".");
    }
}
