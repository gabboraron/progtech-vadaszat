package hunting;

/**
 *
 * @author Sándor
 */
public class Player {
    public int points;
    public int occupiedTiles;
    private String type;
    
    public Player(String type) {
        points = 0;
        occupiedTiles = 0;
        this.type=type;
    }

    /**
     * Get type of player
     * @return 
     */
    public String getType() {
        return type;
    }
    
    /**
     * Get type of player, return true if the player is the hunter
     * @return 
     */
    public boolean isHunter() {
        return type.equals("HUNTER") ;
    }
    
    
    
}
