package hunting;



/**
 *
 * @author Sándor
 */


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import static javax.swing.Box.createVerticalBox;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
 
/**
 * @see http://stackoverflow.com/questions/7702697
 */
public class GridButtonPanel {
    private JFrame f;   //main JFrame
    JPanel p;           //grid panel
    JPanel n;           //nav panel
    public JPanel box;              //box in nav panel
    public JLabel cpLabel;          //current player label
    public JLabel fppLabel;         //first player's points
    public JLabel sppLabel;         //second plpayer's points
    public JLabel ptLabel;          //occupied territories by players
    public JLabel adtLabel;         //additional info
    private JMenuBar menuBar;       //main menubar
    private JMenu  newGameMenu;     //new game menu button
    public JMenuItem sixTimeEight;  //menu option
    public JMenuItem fourTimeFour;  //menu option
    public JMenuItem sevenTimeSeven;//menu option
    public JPanel gcp;              //game control panel
    public JButton gcpL;            //game control panel Left button
    public JButton gcpR;            //game control panel Right button
    public JButton gcpU;            //game control panel Up button
    public JButton gcpD;            //game control panel Down  button
    
    /**/
    Game game;
    Gui gui;
    public GridButtonPanel(Game game, Gui gui, int N, int M) {
        this.game = game;
        this.gui = gui;
        this.N = N;
        this.M = M;
    }
    /**/
    
    public static int N;
    public static int M;
    private final List<JButton> list = new ArrayList<JButton>();

    public JButton getGridButton(int r, int c) {
        int index = r * M  + c;
        return list.get(index);
    }

    private JButton createGridButton(final int row, final int col) {
        final JButton b = new JButton("r" + row + ",c" + col);
        b.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JButton gb = GridButtonPanel.this.getGridButton(row, col);
                System.out.println("r" + row + ",c" + col
                    + " " + (b == gb)
                    + " " + (b.equals(gb)));
                gui.clickOnTile(row, col);
                if (!game.thisIsPossibleTileForThisPlayer(row, col)) {
                    getGridButton(row, col).setBackground(Color.red);
                }
                /*if(mine.isThisNeighbor(new coordinate(row,col))){
                    //NEW STEP
                    //b.setFont(new Font("Arial", Font.PLAIN, 40));
                    gui.recolorTableAndAddStep(row,col);
                } else {
                    //WRONG
                    System.err.println("WRONG BUTTON");
                    //JOptionPane.showMessageDialog(null, "Wronq place for a queen!");    //error msg
                }*/
            }
        });
        return b;
    }

    private JPanel createGridPanel() {
        p = new JPanel(new GridLayout(N, M));
        for (int rowIdx = 0; rowIdx < N; ++rowIdx)
            for(int colIdx = 0; colIdx < M; ++colIdx){
                JButton gb = createGridButton(rowIdx, colIdx);
                list.add(gb);
                p.add(gb);
            }
        /*for (int i = 0; i < N * M; i++) {
            int row = i / N;
            int col = i % M;
            JButton gb = createGridButton(row, col);
            list.add(gb);
            p.add(gb);
        }*/
        return p;
    }

    public void display() {
        f = new JFrame("Hunting");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        f.add(createNavPanel(), BorderLayout.NORTH);
        f.add(createGridPanel(), BorderLayout.CENTER);
        f.add(createGameControlPanel(), BorderLayout.SOUTH);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        if(N>M){
            f.setMinimumSize(new Dimension(N*100,N*100));
        }else{
            
        f.setMinimumSize(new Dimension(M*100,M*100));
        }
    }

    /*public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new GridButtonPanel().display();
            }
        });
    }*/

    private JPanel createNavPanel() {
        n = new JPanel(new BorderLayout());
        box = new JPanel();
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS)); //= new JPanel(new BoxLayout());
        //JButton rBtn = gui.reset();
        //JButton pBtn = gui.pouse();
        //JButton bBtn = gui.back();
        JLabel tLabel = gui.timerLabel();
        cpLabel = gui.currentPlayerLabel(game.currentPlayer);
        adtLabel = gui.additinalInfoLabel(game.getRemainedMoves());
        //fppLabel = gui.firstPlayerPointsLabel();
        //sppLabel = gui.secondPlayerPointsLabel();
        //ptLabel = new JLabel();
        newGameMenu = gui.newGameMenu();
        //JScrollPane oList = gui.optionsList();
        
        //n.add(rBtn, BorderLayout.EAST);
        //n.add(pBtn, BorderLayout.WEST);
        //n.add(bBtn, BorderLayout.WEST);
        menuBar =  new JMenuBar();
        menuBar.add(newGameMenu);
        n.add(menuBar, BorderLayout.NORTH);
        n.add(tLabel, BorderLayout.EAST);
        //n.add(cpLabel, BorderLayout.CENTER);
        //n.add(fppLabel, BorderLayout.SOUTH);
        //n.add(sppLabel, BorderLayout.SOUTH);
        box.add(cpLabel);
        box.add(adtLabel);
        //box.add(fppLabel);
        //box.add(sppLabel);
        //box.add(ptLabel);
        //n.add(oList, BorderLayout.WEST);
        
        n.add(box, BorderLayout.WEST);
        return n;
    }
    
    private JPanel createGameControlPanel() {
        gcp = new JPanel(new BorderLayout());
        gcp.setBackground(Color.orange);
        
        gcpU = gui.setGameControlButtonUp();
        gcpD = gui.setGameControlButtonDown();
        gcpL = gui.setGameControlButtonLeft();
        gcpR = gui.setGameControlButtonRight();
        
        gcp.add(gcpU, BorderLayout.NORTH);
        gcp.add(gcpL, BorderLayout.WEST);
        gcp.add(gcpR, BorderLayout.EAST);
        gcp.add(gcpD, BorderLayout.SOUTH);
        
        return gcp;
    }

    /**
     * Set visible grid panel
     */
    void showGameTable() {
        p.setVisible(true);
    }

    /**
     * Hide grid panel.
     */
    void hideGameTable() {
        p.setVisible(false);
    }

    public void repaint() {
        f.doLayout();
    }

}
