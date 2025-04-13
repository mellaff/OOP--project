package board.tiles;

import players.Player;

public class FreeParkingTile extends Tile {

    public FreeParkingTile() {
        super();
    }

    @Override
    public String getType() {
        return "Free Parking";
    }

    @Override
    public void tileAction(Player player) {
        System.out.println("You must be tired! Park for free for one round.");
    }

}
