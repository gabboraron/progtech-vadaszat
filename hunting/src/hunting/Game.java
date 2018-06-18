package hunting;

import java.util.Vector;

/**
 *
 * @author Sándor
 */
public class Game {
    private int remainedMoves;
    private Vector tiles;
    private int gameSize;
    int currentPlayer;
    public coordinate selectedElem; 
    
    public Game(int size) {
        remainedMoves = size*4;
        gameSize= size;
        tiles = new Vector();
        currentPlayer = 1;  //HUNTER
        
        initGame();
    }

    private void initGame() {
        coordinate fstH = new coordinate(0,0);
        coordinate sndH = new coordinate(0,gameSize-1);
        coordinate thrdH = new coordinate(gameSize-1,0);
        coordinate fthH = new coordinate(gameSize-1,gameSize-1);
        coordinate beast = new coordinate(gameSize/2,gameSize/2);
        
        tiles.add(new Tile(this,"HUNTER",fstH));
        tiles.add(new Tile(this,"HUNTER",sndH));
        tiles.add(new Tile(this,"HUNTER",thrdH));
        tiles.add(new Tile(this,"HUNTER",fthH));
        tiles.add(new Tile(this,"BEAST",beast));
        System.out.println("Figures: \n" + tiles);
    }

    public int getGameSize() {
        return gameSize;
    }
    
    /**
     * Search by coordinates in the vector of terrain.
     * @return a Tile from terrain vector in the specific coordinates
     */
    public Tile getTileBycoordinates(coordinate coord){
        Tile t = new Tile(this, "I", coord);
        int idx = 0;
        boolean found = false;
        while((idx<tiles.size()) && (found == false)){
            Tile tmp = (Tile) tiles.get(idx);
            if(tmp.getCoord().equals(coord)){
                t = tmp;
                found = true;
                return tmp;
            }
            ++idx;
        }
        return t;
    }
    
    /**
     * Get if this coordinate is in the list
     * @param coord
     * @return 
     */
    private boolean isThisCoordinateInList(coordinate coord){
        int idx = 0;
        boolean found = false;
        while((idx<tiles.size()) && (found == false)){
            Tile tmp = (Tile) tiles.get(idx);
            if(tmp.getCoord().equals(coord)){
                found = true;
                return true;
            }
            ++idx;
        }
        
        return false;
    }

    /**
     * Change the current player
     */
    void changePlayer() {
        if (currentPlayer == 1){
            --remainedMoves;
            currentPlayer = 2;
        } else {
            currentPlayer = 1;
        }
    }

    /**
     * Get the number of remained moves
     * @return 
     */
    public int getRemainedMoves() {
        return remainedMoves;
    }

    /**
     * Return true if no more possible move left for the beast.
     * @return 
     */
    public boolean isWinner() {
        coordinate beastCoord = ((Tile) tiles.get(tiles.size()-1)).getCoord();
        int foundEdges = 0;
        for(int idx = 0; idx<tiles.size()-1; ++idx){
            coordinate tmp = ((Tile)tiles.get(idx)).getCoord();
            if((new coordinate(beastCoord.x-1, beastCoord.y)).equals(tmp)){
                ++foundEdges;
            } else if((new coordinate(beastCoord.x+1, beastCoord.y)).equals(tmp)){
                ++foundEdges;
            } else if((new coordinate(beastCoord.x, beastCoord.y-1)).equals(tmp)){
                ++foundEdges;
            } else if((new coordinate(beastCoord.x, beastCoord.y+1)).equals(tmp)){
                ++foundEdges;
            }
        }
        if(foundEdges == 4)
            return true;
        
        return false;
    }

    /**
     * Is this tile possible tile for this player?
     * @param row
     * @param col
     * @return 
     */
    public boolean thisIsPossibleTileForThisPlayer(int row, int col) {
        if(currentPlayer == 1){ //HUNTER
            for(int idx=0; idx<tiles.size()-1; ++idx){
                if(((Tile) tiles.get(idx)).getCoord().equals(new coordinate(row, col))){
                    System.out.println("HUNTER VALIDATED @ " + (new coordinate(row, col)));
                    return true;
                }
            }
        } else {                //BEAST
            if(((Tile) tiles.get(tiles.size()-1)).getCoord().equals(new coordinate(row, col))){
                System.out.println("BEAST VALIDATED @ " + (new coordinate(row, col)));
                return true;
            }
        }
        
        System.out.println("INVALID TILE FOR " + currentPlayer + "(1-HUNTER | 2-BEAST) @ " + (new coordinate(row, col)));
        return false;
    }

    void moveSelectedUP() {
        if(!isThisCoordinateInList(new coordinate(selectedElem.x-1, selectedElem.y))){
            getTileBycoordinates(selectedElem).moveUp();
        }
    }

    void moveSelectedDOWN() {
        if(!isThisCoordinateInList(new coordinate(selectedElem.x+1, selectedElem.y))){
            getTileBycoordinates(selectedElem).moveDown();
        }
    }

    void moveSelectedLEFT() {
        if(!isThisCoordinateInList(new coordinate(selectedElem.x, selectedElem.y-1))){
            getTileBycoordinates(selectedElem).moveLeft();
        }
    }

    void moveSelectedRIGHT() {
        if(!isThisCoordinateInList(new coordinate(selectedElem.x, selectedElem.y+1))){
            getTileBycoordinates(selectedElem).moveRight();
        }
    }
    
    
}
