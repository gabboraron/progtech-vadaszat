package hunting;


import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TimerTask;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Sándor
 */
class Gui {
    private coordinate gameSize;
    private Game game;
    private GridButtonPanel gpanel;
    private boolean poused = false;
    private boolean moved = false;
    
    public Gui() {
        game = new Game(3);
        gpanel = new GridButtonPanel(game, this, 3, 3);
        gpanel.display();
        
        showGame();
        gameSize = new coordinate(3,3);
    }

    private Gui(int size) {
        game = new Game(size);
        gpanel = new GridButtonPanel(game, this, size, size);
        gpanel.display();
        
        showGame();
        gameSize = new coordinate(size,size);
    }
    
    /**
     * RESET BUTTON
     * @return 
     */
    /*JButton reset() {
        JButton btn = new JButton("RESET");
        
        btn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                game.reset();
                showInitGame();
                time = 0;
            }
        });
        return btn;
    }*/

    /**
     * POUSE BUTTON
     * @return 
     */
    /*JButton pouse() {
        JButton btn = new JButton("POUSE");
        btn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(poused){
                   btn.setText("POUSE: OFF");
                   poused = false;
                   gpanel.showGameTable();
                }else{
                    btn.setText("POUSE: ON");
                    poused = true;
                    gpanel.hideGameTable();
                }
            }
        });
        return btn;
    }*/

    /**
     * Show the game panel
     */
    private void showGame() {
        for (int rowIdx = 0; rowIdx < GridButtonPanel.N; ++rowIdx)
            for(int colIdx = 0; colIdx < GridButtonPanel.M; ++colIdx){
                
                gpanel.getGridButton(rowIdx, colIdx).setText( ((Tile) game.getTileBycoordinates(new coordinate(rowIdx, colIdx))).getLabelOfTile() );
                gpanel.getGridButton(rowIdx, colIdx).setFont(new Font("Arial", Font.PLAIN, 15));
                gpanel.getGridButton(rowIdx, colIdx).setBackground(Color.white);             
                
                //System.out.println("RECOLORED: " + rowIdx + ", " + colIdx);
            }
    }

    /**
     * Set a new tick to game panel
     * @param row 
     * @param col 
     */
    /*void setNewTick(int row, int col) {
        if(tickTack.isCurrentIsTick()){
            gpanel.getGridButton(row, col).setText("X");
        } else {
            gpanel.getGridButton(row, col).setText("O");
        }
        
        tickTack.addElem(new coordinate(row, col));
        if(tickTack.isItDoneByTick())
            JOptionPane.showMessageDialog(null, "You are the winner!");
    }*/
    
    /**
     * TIMER LABEL
     * @return 
     */
    int time = 0;
    JLabel timerLabel() {
        JLabel timer = new JLabel();
        timer.setText("Elapsed time: " + Integer.toString(time) + " sec");
        new java.util.Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                if(!poused){
                    ++time;
                    timer.setText("Elapsed time: " + Integer.toString(time) + " sec");
                }
                //System.out.println("Executed...");
                //your code here 
                //1000*5=5000 mlsec. i.e. 5 seconds. u can change accordngly 
            }
        },1000*5,1000*5);
        
        return timer;
    }
    
    /**
     * DISPLAY CURRENT PLAYER
     * @return 
     */
    JLabel currentPlayerLabel(int cp) {
        JLabel l = new JLabel();
        switch(cp){
            case 1 : l.setText("HUNTER");
                     l.setForeground(Color.green);
                     break;
            case 2 : l.setText("BEAST");
                     l.setForeground(Color.blue);
                     break;
        }
        return l;
    }
    
    /**
     * CHANGE LABEL OF CURRENT PLAYER
     * @return 
     */
    private void changePlayerLabel(){
        int cp = game.currentPlayer;
        switch(cp){
            case 1 : 
                     gpanel.cpLabel.setText("Current Player: HUNTER");
                     gpanel.cpLabel.setForeground(Color.green);
                     break;
            case 2 : gpanel.cpLabel.setText("Current Player: BEAST");
                     gpanel.cpLabel.setForeground(Color.blue);
                     break;
        }
    }
    
    
    /**
     * ADDITIONAL INFO LABEL
     * @param remainedMoves
     * @return 
     */
    JLabel additinalInfoLabel(int remainedMoves) {
        JLabel l = new JLabel();
        l.setText("Remained moves for the hunters: " + Integer.toString(remainedMoves));
        return l;
    }
    
    /**
     * UPDATE ADDITIONAL INFO LABEL
     * @param remainedMoves
     * @return 
     */
    private void updateAdditinalInfoLabel() {
        gpanel.adtLabel.setText("Remained moves for the hunters: " + Integer.toString(game.getRemainedMoves()));
    }
    
    /**
     * NEW GAME MENU WITH MENU OPTIONS
     * @return 
     */
    JMenu newGameMenu() {
        JMenu m = new JMenu("New game");
        gpanel.fourTimeFour = new JMenuItem(fourTimeFour);
        gpanel.sixTimeEight = new JMenuItem(sixTimeEight);
        gpanel.sevenTimeSeven = new JMenuItem(sevenTimeSeven);
        m.add(fourTimeFour);
        m.add(sixTimeEight);
        m.add(sevenTimeSeven);
        
        return m;
    }
    
    /**
     * MENU ITEM
     */
    private AbstractAction fourTimeFour = new AbstractAction("3X3") {
        @Override
        public void actionPerformed(ActionEvent e)
        {                    
            Gui gui = new Gui(3);
        }
    };
    
    /**
     * MENU ITEM
     */
    private AbstractAction sixTimeEight = new AbstractAction("5X5") {
        @Override
        public void actionPerformed(ActionEvent e)
        {                    
            Gui gui = new Gui(5);
        }
    };
    
    /**
     * MENU ITEM
     */
    private AbstractAction sevenTimeSeven = new AbstractAction("7X7") {
        @Override
        public void actionPerformed(ActionEvent e)
        {                    
            Gui gui = new Gui(7);
        }
    };
    
    /**
     * Change the tiles values and etc in game when a tile is clicked
     * @param row
     * @param col 
     */
    void clickOnTile(int row, int col) {
        /*( (Tile) game.getTileBycoordinates(new coordinate(row, col))).incTileAttackPoints();
        
        if(game.currentPlayer == 1){
            ( (Tile) game.getTileBycoordinates(new coordinate(row, col-1))).incTileAttackPoints();
            ( (Tile) game.getTileBycoordinates(new coordinate(row, col+1))).incTileAttackPoints();
            ( (Tile) game.getTileBycoordinates(new coordinate(row-1, col))).decTileAttackPoints();
        } else {
            ( (Tile) game.getTileBycoordinates(new coordinate(row-1, col))).incTileAttackPoints();
            ( (Tile) game.getTileBycoordinates(new coordinate(row+1, col))).incTileAttackPoints();
            ( (Tile) game.getTileBycoordinates(new coordinate(row, col-1))).decTileAttackPoints();            
        }*/
        
        if(game.thisIsPossibleTileForThisPlayer(row, col)){
            game.selectedElem = new coordinate(row, col);
            //this.getGridButton(row, col).setBackground(Color.red);
        }
    }
    
    public void changePlayer(){
        game.changePlayer();
        showGame();
        changePlayerLabel();
        updateAdditinalInfoLabel();

        if (game.getRemainedMoves() == 0) {
            JOptionPane.showMessageDialog(null, "BEAST WIN! CONGRATULATIONS! No more move left for hunters.");
            poused = true;
        } else if (game.isWinner()) {
            JOptionPane.showMessageDialog(null, "HUNTERS WIN! CONGRATULATIONS! No more possible moves for beast.");
            poused = true;
        }
    }

    /**
     * GAME CONTROL BUTTON
     * @return 
     */
    JButton setGameControlButtonUp() {
        JButton btn = new JButton();
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.moveSelectedUP();
                changePlayer();
            }
        });
        btn.setText("^");
        return btn;
    }

    JButton setGameControlButtonDown() {
        JButton btn = new JButton();
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.moveSelectedDOWN();
                changePlayer();
            }
        });
        btn.setText("ˇ");
        return btn;
    }

    JButton setGameControlButtonLeft() {
        JButton btn = new JButton();
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.moveSelectedLEFT();
                changePlayer();
            }
        });
        btn.setText("<");
        return btn;
    }

    JButton setGameControlButtonRight() {
        JButton btn = new JButton();
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.moveSelectedRIGHT();
                changePlayer();
            }
        });
        btn.setText(">");
        return btn;
    }



    

    /**
     * OPTIONS LIST
     * @return 
     */
    /*JScrollPane optionsList() {
        String[] options = {
        "8 X 5", "10 X 6", "12 X 7"};
        
        JList l = new JList(options);
        JScrollPane sp = new JScrollPane(l);
        l.setSelectionMode(
            ListSelectionModel.SINGLE_SELECTION);
        l.addListSelectionListener((ListSelectionListener) this);
        l.setSelectedIndex(0);
        
        //@Override
        //public void valueChanged(ListSelectionEvent e) {        
        //setTitle((String)lsHonapok.getSelectedValue());
        //}
        
        return sp;
    }*/
    
}
