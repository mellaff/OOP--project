package board.tiles;

import players.Player;

public class GoTile extends Tile {
    private final int bonus = 200;
    //private int initialMoney = 2000;

    public GoTile() {
        super();
    }

    @Override
    public String getType() {
        return "Go";
    }

    @Override
    public void tileAction(Player player) {
        if(player.started){
            player.setMoney(player.getMoney() + bonus);
            System.out.println("You passed GO! Collect $" + bonus );
        }
        else {
            player.started = true;
            //player.setMoney(initialMoney);
        }
    }
}
