import board.Board;
import board.tiles.property.Property;
import board.tiles.property.Station;
import cards.ChanceCard;
import cards.CommunityChestCard;
import game.mechanics.Dice;
import players.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import board.tiles.general.Tile;


public class MonopolyGUI extends JFrame {
    private BoardPanel boardPanel;
    private JButton rollDiceButton;
    private JLabel diceResultLabel;
    private Player[] players;
    private int currentPlayerIndex = 0;
    private Random random = new Random();
    private Board board;
    private Dice dice;
    private JTextArea gameLogArea;
    private JPanel playerStatusPanel;
    private JLabel[] playerMoneyLabels;
    private DicePanel dicePanel;
    private JLabel currentTurnLabel;
    private boolean bankruptsyRecorded = false;


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MonopolyGUI().setVisible(true);
            }
        });
    }

    public MonopolyGUI() {
        board = new Board();
        dice = new Dice();
        setTitle("Monopoly Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setExtendedState(JFrame.MAXIMIZED_BOTH);


        String[] names = getPlayerNames();
        players = new Player[names.length];
        for (int i = 0; i < names.length; i++) {
            players[i] = new Player(names[i], getRandomColor());
            players[i].moveTo(0);
        }

        boardPanel = new BoardPanel(players);
        add(boardPanel, BorderLayout.CENTER);


        // EAST: Side Panel for Dice, Controls, Log, Status
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));

        // DicePanel
        dicePanel = new DicePanel();
        dicePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidePanel.add(dicePanel);

        // Roll button and result
        rollDiceButton = new JButton("Roll Dice");
        rollDiceButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        diceResultLabel = new JLabel("Roll: -");
        diceResultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        sidePanel.add(rollDiceButton);
        sidePanel.add(diceResultLabel);

        // Game Log
        gameLogArea = new JTextArea(10, 20);
        gameLogArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(gameLogArea);
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        sidePanel.add(scrollPane);

        // Player Money Status
        playerStatusPanel = new JPanel();
        playerStatusPanel.setLayout(new BoxLayout(playerStatusPanel, BoxLayout.Y_AXIS));
        playerMoneyLabels = new JLabel[players.length];
        for (int i = 0; i < players.length; i++) {
            playerMoneyLabels[i] = new JLabel(players[i].getName() + ": $" + players[i].getMoney());
            playerMoneyLabels[i].setForeground(players[i].getColor());
            playerMoneyLabels[i].setFont(new Font("Arial", Font.BOLD, 14));
            playerStatusPanel.add(playerMoneyLabels[i]);
        }
        sidePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        sidePanel.add(playerStatusPanel);

        // Add side panel to EAST
        add(sidePanel, BorderLayout.EAST);

        currentTurnLabel = new JLabel("Turn: " + players[currentPlayerIndex].getName());
        currentTurnLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        currentTurnLabel.setFont(new Font("Arial", Font.BOLD, 14));
        currentTurnLabel.setForeground(players[currentPlayerIndex].getColor());
        sidePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        sidePanel.add(currentTurnLabel);


        // Dice roll action
        rollDiceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rollDiceAndMove();
            }
        });
    }

    private class DicePanel extends JPanel {
        private int die1 = 1;
        private int die2 = 1;

        public void setDice(int die1, int die2) {
            this.die1 = die1;
            this.die2 = die2;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawDie(g, die1, 20, 20);
            drawDie(g, die2, 100, 20);
        }

        private void drawDie(Graphics g, int value, int x, int y) {
            g.setColor(Color.WHITE);
            g.fillRoundRect(x, y, 60, 60, 15, 15);
            g.setColor(Color.BLACK);
            g.drawRoundRect(x, y, 60, 60, 15, 15);

            g.setColor(Color.BLACK);

            // Define pip positions
            int[][][] positions = {
                    { {30, 30} },  // 1
                    { {18, 18}, {42, 42} },  // 2
                    { {18, 18}, {30, 30}, {42, 42} },  // 3
                    { {18, 18}, {42, 18}, {18, 42}, {42, 42} },  // 4
                    { {18, 18}, {42, 18}, {30, 30}, {18, 42}, {42, 42} },  // 5
                    { {18, 18}, {42, 18}, {18, 30}, {42, 30}, {18, 42}, {42, 42} }  // 6
            };

            int[][] pos = positions[value - 1];
            for (int[] p : pos) {
                g.fillOval(x + p[0] - 5, y + p[1] - 5, 10, 10); // Center dots (10x10)
            }
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(200, 100);
        }
    }

    private void log(String message) {
        gameLogArea.append(message + "\n");
        gameLogArea.setCaretPosition(gameLogArea.getDocument().getLength());
    }

    private String[] getPlayerNames() {
        int numPlayers;
        while (true) {
            String input = JOptionPane.showInputDialog(this, "Enter number of players (2–4):");
            if (input == null) System.exit(0);

            try {
                numPlayers = Integer.parseInt(input);
                if (numPlayers >= 2 && numPlayers <= 4) break;
            } catch (NumberFormatException ignored) {}

            JOptionPane.showMessageDialog(this, "Please enter a valid number between 2 and 4.");
        }

        String[] names = new String[numPlayers];

        for (int i = 0; i < numPlayers; i++) {
            String name;
            do {
                name = JOptionPane.showInputDialog(this, "Enter name for Player " + (i + 1) + ":");
                if (name == null) System.exit(0);
                name = name.trim();
            } while (name.isEmpty());

            names[i] = name;
        }

        return names;
    }

    private void rollDiceAndMove() {
        rollDiceButton.setEnabled(false); // disable during animation

        Timer timer = new Timer(1, null);
        final int[] count = {0};
        timer.addActionListener(e -> {
            dice.rollWithAnimation();
            dicePanel.setDice(dice.getDie1(), dice.getDie2());

            count[0]++;
            if (count[0] >= 10) {  // roll 10 times (1 sec)
                timer.stop();

                int die1 = dice.getDie1();
                int die2 = dice.getDie2();
                int roll = die1+die2;

                diceResultLabel.setText("Roll: " + roll);
                if(die1==die2){
                    JOptionPane.showMessageDialog(this,
                            "Doubles rolled!!!",
                            "Dice Rolled",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }


                Player currentPlayer = players[currentPlayerIndex];
                currentPlayer.move(roll);
                if(currentPlayer.passedGo() && ((board.getTile(currentPlayer.getPosition()).getType()!="Card")||(board.getTile(currentPlayer.getPosition()).getType()!="Go To Jail"))){
                    JOptionPane.showMessageDialog(this,
                            "Passed Go!!! Collect $200.",
                            "Go Passed",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    currentPlayer.receive(200);
                    currentPlayer.setPassedGo(false);
                }
                boardPanel.repaint();

                int position = currentPlayer.getPosition();
                Tile landedTile = board.getTile(position);


                if (landedTile != null) {
                    String tileName = landedTile.getName();
                    JOptionPane.showMessageDialog(this,
                            currentPlayer.getName() + " landed on " + tileName + "!",
                            "Tile Landed",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    log(currentPlayer.getName() + " landed on " + tileName + ".");

                    landedTile.tileAction(currentPlayer);

                    if((landedTile.getType()=="Go to Jail"||landedTile.getType()=="Jail") && currentPlayer.getGetOutOfJailFreeCards()>0){
                        currentPlayer.useGetOutOfJailFreeCard();
                        JOptionPane.showMessageDialog(this,
                                currentPlayer.getName() + " got out of jail with freecard.",
                                "Get out of jail",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    }

                    if (tileName.contains("Chance")) {
                        // Draw Chance card and log
                        ChanceCard card = ChanceCard.drawCard();
                        if (card != null) {
                            JOptionPane.showMessageDialog(this,
                                    "Chance Card: " + card.getDescription() + " " + card.getEffect(),
                                    "Chance Card",
                                    JOptionPane.INFORMATION_MESSAGE
                            );

                            log(currentPlayer.getName() + " drew Chance: " + card.getDescription());
                            card.applyChanceEffect(currentPlayer, players);
                            if(currentPlayer.moveToNearestCity()){
                                int newPosition = 1;
                                while(board.getTile(currentPlayer.getPosition()+newPosition).getType()!="Property" || ((Property)board.getTile(currentPlayer.getPosition()+newPosition)).getGroup()=="station"){
                                    newPosition++;
                                }
                                currentPlayer.move(newPosition);
                                currentPlayer.setMoveToNearestCity(false);
                                board.getTile(currentPlayer.getPosition()).tileAction(currentPlayer);

                            } else if(currentPlayer.moveToNearestStation()){
                                int newPosition = 1;
                                while(!board.getTile(currentPlayer.getPosition()+newPosition).getType().equals("Property") || !((Property)board.getTile(currentPlayer.getPosition()+newPosition)).getGroup().equals("station")){
                                    newPosition++;
                                }
                                currentPlayer.move(newPosition);
                                currentPlayer.setMoveToNearestStation(false);
                                board.getTile(currentPlayer.getPosition()).tileAction(currentPlayer);


                            }

                            log(currentPlayer.getName() + " now has $" + currentPlayer.getMoney() + ".");
                        }
                    } else if (tileName.contains("Community Chest")) {
                        // Draw Community Chest card and log
                        CommunityChestCard card = CommunityChestCard.drawCard();
                        if (card != null) {
                            JOptionPane.showMessageDialog(this,
                                    "Community Chest Card: " + card.getDescription() + " " + card.getEffect(),
                                    "Community Chest Card",
                                    JOptionPane.INFORMATION_MESSAGE
                            );

                            log(currentPlayer.getName() + " drew Community Chest: " + card.getDescription());
                            card.applyChestEffect(currentPlayer, players);
                            if(currentPlayer.moveToNearestCity()){
                                int newPosition = 1;
                                while(board.getTile(currentPlayer.getPosition()+newPosition).getType()!="Property" || ((Property)board.getTile(currentPlayer.getPosition()+newPosition)).getGroup()=="station"){
                                   newPosition++;
                                }
                                currentPlayer.move(newPosition);
                                currentPlayer.setMoveToNearestCity(false);
                            } else if(currentPlayer.moveToNearestStation()){
                                int newPosition = 1;
                                while(board.getTile(currentPlayer.getPosition()+newPosition).getType().equals("Property") && ((Property)board.getTile(currentPlayer.getPosition()+newPosition)).getGroup().equals("station")){
                                    newPosition++;
                                }
                                currentPlayer.move(newPosition);
                                currentPlayer.setMoveToNearestStation(false);

                            }

                            log(currentPlayer.getName() + " now has $" + currentPlayer.getMoney() + ".");
                        }
                    } else if (tileName.contains("Jail")) {
                        log(currentPlayer.getName() + " is in Jail!");
                    } else if (tileName.contains("Free Parking")) {
                        log(currentPlayer.getName() + " gets bonus money!");
                    } else if (tileName.contains("Go To Jail")) {
                        log(currentPlayer.getName() + " goes directly to Jail!");
                    }
                    boardPanel.repaint();
                }

                updatePlayerMoneyDisplay();
                if (currentPlayer.isBankrupt()) {
                    String loserName = players[currentPlayerIndex].getName();

                    for (Property prop : currentPlayer.getProperties()) {
                        prop.setOwner(null);
                    }

                    // Remove player from the list
                    Player[] newPlayers = new Player[players.length - 1];
                    int newIndex = 0;
                    for (int i = 0; i < players.length; i++) {
                        if (i != currentPlayerIndex) {
                            newPlayers[newIndex++] = players[i];
                        }
                    }

                    players = newPlayers;


                    if (currentPlayerIndex >= players.length) {
                        currentPlayerIndex = 0;
                    }

                    if (players.length == 1) {
                        JOptionPane.showMessageDialog(this,
                                loserName + " went Bankrupt. " + players[0].getName() + " won the game.",
                                "Game Won",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                        System.exit(0);
                    } else {
                        JOptionPane.showMessageDialog(this,
                                loserName + " went Bankrupt and has been removed from the game.",
                                "Player Eliminated",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    }
                    bankruptsyRecorded = true;
                }


                if(die1==die2 && ! currentPlayer.isInJail() && ! currentPlayer.isMissTurn()){
                    currentTurnLabel.setText("Turn: " + players[currentPlayerIndex].getName());
                    currentTurnLabel.setForeground(players[currentPlayerIndex].getColor());

                    rollDiceButton.setEnabled(true);
                } else {

                    do {
                        if(!bankruptsyRecorded) {
                            currentPlayerIndex = (currentPlayerIndex + 1) % players.length;
                        }

                        Player nextPlayer = players[currentPlayerIndex];
                        if(bankruptsyRecorded) {
                            bankruptsyRecorded = false;
                        }

                        if (nextPlayer.isInJail()) {
                            nextPlayer.decreaseJailTurn();
                            log(nextPlayer.getName() + " is in jail for " + nextPlayer.getJailTurns() + " more turn(s).");
                            continue;
                        }

                        if (nextPlayer.isMissTurn()) {
                            nextPlayer.setMissTurn(false);
                            log(nextPlayer.getName() + " misses a turn.");
                            continue;
                        }

                        // Found a valid player
                        break;

                    } while (true);


                    currentTurnLabel.setText("Turn: " + players[currentPlayerIndex].getName());
                    currentTurnLabel.setForeground(players[currentPlayerIndex].getColor());

                    rollDiceButton.setEnabled(true);
                }
            }
        });
        timer.start();
    }

    private void updatePlayerMoneyDisplay() {
        for (int i = 0; i < players.length; i++) {
            playerMoneyLabels[i].setText(players[i].getName() + ": $" + players[i].getMoney());
        }
        playerStatusPanel.revalidate();
        playerStatusPanel.repaint();
    }

    private ArrayList<Color> availableColors = new ArrayList<>(Arrays.asList(
            new Color(139, 0, 0),     // Dark Red (Firebrick)
            new Color(0, 0, 139),     // Dark Blue
            new Color(0, 100, 0),     // Dark Green
            new Color(85, 26, 139),   // Dark Purple
            new Color(210, 105, 30),  // Chocolate (Warm Brown)
            new Color(0, 139, 139),   // Dark Cyan
            new Color(184, 134, 11)   // Dark Goldenrod
    ));

    private Color getRandomColor() {
        int index = random.nextInt(availableColors.size());
        return availableColors.remove(index);
    }


    private class BoardPanel extends JPanel {
        private final int tileSize = 70;
        private Player[] players;

        public BoardPanel(Player[] players) {
            this.players = players;
            setBackground(new Color(202, 219, 232));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g;
            int centerX = 4 * tileSize + 70;
            int centerY = 4 * tileSize + 50;
            int centerSize = 3 * tileSize;

            // Draw central red square
            g2.setColor(new Color(166, 6, 6)); // Crimson red
            g2.fillRect(centerX, centerY+70, centerSize, centerSize-170);

            //draw Armopoly
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, 36));
            FontMetrics fm = g2.getFontMetrics();
            String title = "ARMOPOLY";
            int titleWidth = fm.stringWidth(title);
            int titleX = centerX + (centerSize - titleWidth) / 2;
            int titleY = centerY + centerSize / 2;
            g2.drawString(title, titleX, titleY);

            // Draw Chance Deck
            int deckSize = tileSize - 10;
            int chanceX = centerX + tileSize / 2 +300;
            int chanceY = centerY + 2 * tileSize + 100;
            g2.setColor(Color.ORANGE);
            g2.fillRect(chanceX, chanceY, deckSize, deckSize-20);
            g2.setColor(Color.BLACK);
            g2.setFont(new Font("Arial", Font.BOLD, 14));
            g2.drawString("Chance", chanceX + 5, chanceY + 20);

            // Draw Community Chest Deck
            int chestX = centerX + 2 * tileSize - 300;
            int chestY = centerY + tileSize / 2 -100;
            g2.setColor(new Color(173, 216, 230)); // Sky Blue
            g2.fillRect(chestX, chestY, deckSize+10, deckSize-20);
            g2.setColor(Color.BLACK);
            g2.setFont(new Font("Arial", Font.BOLD, 12));
            g2.drawString("Community", chestX + 2, chestY + 18);
            g2.drawString("Chest", chestX + 12, chestY + 33);

            // Loop through the tiles and draw each one
            for (int i = 0; i < 40; i++) {
                // Get the coordinates for each tile
                Point p = getTileCoordinates(i);

                // Get the tile object from the Board class (make sure Board is available)
                Tile tile = board.getTile(i);


                g2.setColor(new Color(202, 219, 232)); // Light color for even tiles

                // Darker color for odd tiles


                if ((i > 0 && i < 10) || (i > 20 && i < 30)) {
                    g2.fillRect(p.x, p.y, tileSize + 5, tileSize + 40);
                    g2.setColor(Color.BLACK);
                    g2.drawRect(p.x, p.y, tileSize + 5, tileSize + 40);
                } else if (i % 10 == 0) {
                    g2.fillRect(p.x, p.y, tileSize + 40, tileSize + 40);
                    g2.setColor(Color.BLACK);
                    g2.drawRect(p.x, p.y, tileSize + 40, tileSize + 40);
                } else {
                    g2.fillRect(p.x, p.y, tileSize + 40, tileSize + 5);
                    g2.setColor(Color.BLACK);
                    g2.drawRect(p.x, p.y, tileSize + 40, tileSize + 5);
                }

                if (tile.getClass() == Property.class) {
                    switch (((Property) tile).getGroup()) {
                        case "Vayoc Dzor":
                            g2.setColor(new Color(149, 119, 61));
                            g2.fillRect(p.x, p.y, tileSize + 5, tileSize - 40);
                            g2.setColor(Color.BLACK);
                            g2.drawRect(p.x, p.y, tileSize + 5, tileSize - 40);
                            break;
                        case "Ararat":
                            g2.setColor(new Color(101, 171, 195));
                            g2.fillRect(p.x, p.y, tileSize + 5, tileSize - 40);
                            g2.setColor(Color.BLACK);
                            g2.drawRect(p.x, p.y, tileSize + 5, tileSize - 40);
                            break;
                        case "Gegharkunik":
                            g2.setColor(new Color(211, 58, 190));
                            g2.fillRect(p.x + 80, p.y, tileSize - 40, tileSize + 5);
                            g2.setColor(Color.BLACK);
                            g2.drawRect(p.x + 80, p.y, tileSize - 40, tileSize + 5);
                            break;
                        case "Kotayk":
                            g2.setColor(new Color(237, 113, 0));
                            g2.fillRect(p.x + 80, p.y, tileSize - 40, tileSize + 5);
                            g2.setColor(Color.BLACK);
                            g2.drawRect(p.x + 80, p.y, tileSize - 40, tileSize + 5);
                            break;
                        case "Syunik":
                            g2.setColor(new Color(211, 3, 3));
                            g2.fillRect(p.x, p.y + 80, tileSize + 5, tileSize - 40);
                            g2.setColor(Color.BLACK);
                            g2.drawRect(p.x, p.y + 80, tileSize + 5, tileSize - 40);
                            break;
                        case "Tavush":
                            g2.setColor(new Color(237, 166, 3));
                            g2.fillRect(p.x, p.y + 80, tileSize + 5, tileSize - 40);
                            g2.setColor(Color.BLACK);
                            g2.drawRect(p.x, p.y + 80, tileSize + 5, tileSize - 40);
                            break;
                        case "Lori":
                            g2.setColor(new Color(83, 177, 75));
                            g2.fillRect(p.x, p.y, tileSize - 40, tileSize + 5);
                            g2.setColor(Color.BLACK);
                            g2.drawRect(p.x, p.y, tileSize - 40, tileSize + 5);
                            break;
                        case "Shirak":
                            g2.setColor(new Color(17, 75, 186));
                            g2.fillRect(p.x, p.y, tileSize - 40, tileSize + 5);
                            g2.setColor(Color.BLACK);
                            g2.drawRect(p.x, p.y, tileSize - 40, tileSize + 5);
                            break;
                    }
                }


                // Draw the name of the tile
                if (tile != null) {

                    String tileName = tile.getName();
                    if(i%10==0){
                        g2.setFont(new Font("Luckiest Guy", Font.BOLD, 15));
                        switch(i) {
                            case 0:
                                g2.drawString("GO", p.x + 45, p.y + 60);
                                break;
                            case 10:
                                g2.drawString("IN", p.x + 45, p.y + 50);
                                g2.drawString("JAIL", p.x + 40, p.y + 75);
                                break;
                            case 20:
                                g2.drawString("FREE", p.x + 30, p.y + 45);
                                g2.drawString("PARKING", p.x + 20, p.y + 75);
                                break;
                            case 30:
                                 g2.drawString("GO", p.x + 45, p.y + 35);
                                 g2.drawString("TO", p.x + 45, p.y + 55);
                                 g2.drawString("JAIL", p.x + 42, p.y + 75);
                                 break;
                        }

                    } else {
                        g2.setFont(new Font("SansSerif", Font.BOLD, 10));
                        String[] nameParts = tileName.split(" ");

                        int lineHeight = 12;
                        int baseX = p.x + 8;
                        int baseY = p.y + 25;

                        // Draw the name split over multiple lines
                        for (int j = 0; j < nameParts.length && j < 3; j++) {
                            if (i > 0 && i < 10) {
                                g2.drawString(nameParts[j], baseX, baseY + (j * lineHeight) + 20);
                            } else if (i > 30 && i < 40) {
                                g2.drawString(nameParts[j], baseX + 30, baseY + (j * lineHeight));
                            } else {
                                g2.drawString(nameParts[j], baseX, baseY + (j * lineHeight));
                            }
                        }

                        // Draw property details with smaller font
                        if (tile instanceof Property) {
                            if (i > 0 && i < 10) {
                                g2.setFont(new Font("SansSerif", Font.PLAIN, 9));
                                g2.drawString("Price: $" + ((Property) tile).getPrice(), baseX, baseY + (nameParts.length * lineHeight) + 25);
                                g2.drawString("Rent: $" + ((Property) tile).getRent(), baseX, baseY + (nameParts.length * lineHeight) + 35);
                            } else if (i > 30 && i < 40) {
                                g2.setFont(new Font("SansSerif", Font.PLAIN, 9));
                                g2.drawString("Price: $" + ((Property) tile).getPrice(), baseX + 30, baseY + (nameParts.length * lineHeight) + 5);
                                g2.drawString("Rent: $" + ((Property) tile).getRent(), baseX + 30, baseY + (nameParts.length * lineHeight) + 15);
                            } else {
                                g2.setFont(new Font("SansSerif", Font.PLAIN, 9));
                                g2.drawString("Price: $" + ((Property) tile).getPrice(), baseX, baseY + (nameParts.length * lineHeight) + 5);
                                g2.drawString("Rent: $" + ((Property) tile).getRent(), baseX, baseY + (nameParts.length * lineHeight) + 15);
                            }
                        }
                    }

                    // Draw ownership circle
                    if (tile instanceof Property && ((Property) tile).isOwned()) {
                        g2.setColor(((Property) tile).getOwner().getColor());
                        if(i>0&&i<10){
                            g2.fillOval(p.x + tileSize -10, p.y + 5, 10, 10);
                        } else if(i>10&&i<20){
                            g2.fillOval(p.x + tileSize +25, p.y + 60, 10, 10);
                        } else if(i>20&&i<30){
                            g2.fillOval(p.x + tileSize -65, p.y + 95, 10, 10);
                        } else{
                            g2.fillOval(p.x + tileSize -65, p.y + 5, 10, 10);
                        }

                    }
                }
            }

                // Draw player tokens
            for (int i = 0; i < players.length; i++) {
                Player player = players[i];
                int pos = player.getPosition();
                Point p = getTileCoordinates(pos);

                // Slight offset for each player so tokens don't overlap
                int offsetX = (i % 2) * 20;
                int offsetY = (i / 2) * 20;

                g2.setColor(player.getColor());
                g2.fillOval(p.x + 10 + offsetX, p.y + 10 + offsetY, 25, 25); // Slightly larger token
                g2.setColor(Color.BLACK);
                g2.drawOval(p.x + 10 + offsetX, p.y + 10 + offsetY, 25, 25); // Outline token
            }
        }



        private Point getTileCoordinates(int tileNumber) {
            double x = 0, y = 0;
            if(tileNumber==0){
                x = 10.58;
                y = 10.015;
            }
             else if (tileNumber > 0 && tileNumber < 10) { // Bottom row (right to left)
                x = 10.55 - tileNumber;
                y = 10.015;
            }
          else if(tileNumber == 10 ){
                x = 10 - tileNumber;
                y = 10.015;
            }
            else if (tileNumber > 10 && tileNumber < 20) { // Left column (bottom to top)
                x = 0;
                y = 19.93 - tileNumber;
            }
            else if(tileNumber == 20 ){
                x = 0;
                y = 19.4-tileNumber;
            }
            else if (tileNumber > 20 && tileNumber <= 30) { // Top row (left to right)
                x = tileNumber - 19.42;
                y = -0.61;
            } else if (tileNumber > 30 && tileNumber < 40) { // Right column (top to bottom)
                x = 10.58;
                y = tileNumber - 30.05;
            }

            return new Point((int)(x * tileSize + 50), (int)(y * tileSize + 50));
        }
    }
}
