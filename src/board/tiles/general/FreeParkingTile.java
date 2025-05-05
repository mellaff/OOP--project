package board.tiles.general;

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
        System.out.println(player.getName() + " is resting at Free Parking. They will skip their next turn.");
        player.setMissTurn(true);  // Player skips their next turn
    }
}
