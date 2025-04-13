package board.tiles;

import players.Player;

public class CommunityChestTile extends Tile {

    public CommunityChestTile(String name) {
        super(name);
    }

    @Override
    public String getType() {
        return "Community Chest";
    }

    @Override
    public void tileAction(Player player) {
        System.out.println("You landed on Community Chest! Draw a card.");
    }
}
