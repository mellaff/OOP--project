package board.tiles;

import players.Player;

public class ChanceTile extends Tile {

    public ChanceTile(String name) {
        super(name);
    }

    @Override
    public String getType() {
        return "Chance";
    }

    @Override
    public void tileAction(Player player) {
        System.out.println("You landed on Chance! Draw a card.");
    }
}
