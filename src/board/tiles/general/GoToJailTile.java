package board.tiles.general;

import players.Player;

public class GoToJailTile extends Tile {

    private final int jailPosition;

    public GoToJailTile(int jailPosition) {
        super("Go To Jail");
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
        System.out.println(player.getName() + " landed on 'Go To Jail'!");
        System.out.println("Go directly to jail. Do not pass GO. Do not collect $200.");

        player.moveTo(jailPosition);   // Move the player to jail tile
        player.goToJail();             // Set their status to in jail
    }
}
