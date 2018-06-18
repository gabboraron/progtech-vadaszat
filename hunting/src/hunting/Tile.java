package hunting;

import java.util.Objects;

/**
 *
 * @author Sándor
 */
public class Tile {
    private String type;    
    private coordinate coord;
    private Game game;    
    
    public Tile(Game game, String type, coordinate coord) {
        this.game = game;
        this.coord = coord;
        switch(type){
            case "HUNTER" : this.type = "HUNTER";
                            break;
            case "BEAST" :  this.type = "BEAST";
                            break;
        }
    }
 
    /**
     * Get the label what is on the tile in game panel
     * @return 
     */
    public String getLabelOfTile(){
        return type;
    }
    
    /**
     * Move up this character
     */
    public void moveUp(){
        if(coord.x != 0)
            --coord.x;
    }
    
    /**
     * Move down this character
     */
    public void moveDown(){
        if(coord.x != game.getGameSize()-1)
            ++coord.x;
    }
    
    /**
     * Move left this character
     */
    public void moveLeft(){
        if(coord.y != 0)
            --coord.y;
    }
    
    /**
     * Move right this character
     */
    public void moveRight(){
        if(coord.y != game.getGameSize()-1)
            ++coord.y;
    }
    
    @Override
    public String toString() {
        return String.format(type + " coordinates: " + coord + "\n");
    }

    /**
     * Get Coordinates of tile
     * @return 
     */
    public coordinate getCoord() {
        return coord;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.type);
        hash = 29 * hash + Objects.hashCode(this.coord);
        hash = 29 * hash + Objects.hashCode(this.game);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Tile other = (Tile) obj;
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        if (!Objects.equals(this.coord, other.coord)) {
            return false;
        }
        if (!Objects.equals(this.game, other.game)) {
            return false;
        }
        return true;
    }
    
    
}

