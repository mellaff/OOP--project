package board;

import board.tiles.*;

public class Board {
    private final Tile[] tiles;
    private final int SIZE;

    public Board(int size) {
        this.SIZE = size;
        this.tiles = new Tile[SIZE];
        initialise();
    }

    public void setTile(int position, Tile tile) {
        if (position >= 0 && position < SIZE) {
            tiles[position] = tile;
        } else {
            System.out.println("Invalid position.");
        }
    }

    public Tile getTile(int position) {
        if (position >= 0 && position < SIZE) {
            return tiles[position];
        }
        return null;
    }

    public void initialise() {
        setTile(0, new GoTile());
        setTile((SIZE/4), new JailTile());
        setTile(2*(SIZE/4), new FreeParkingTile());
        setTile(3*(SIZE/4), new GoToJailTile((SIZE/4)));

        for (int i = 1; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {

            }
        }
    }

    public void printBoard() {

    }
}