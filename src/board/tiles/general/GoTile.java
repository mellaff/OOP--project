package board.tiles.general;

import players.Player;

public class GoTile extends Tile {
    private final int bonus = 200;

    public GoTile() {
        super();
    }

    @Override
    public String getType() {
        return "Go";
    }

    @Override
    public void tileAction(Player player) {
        player.receive(bonus);
        System.out.println(player.getName() + " landed on GO! Collect $" + bonus);
    }
}
