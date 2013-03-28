package battleship.model;

public enum BattleShip{
    BOAT(2),
    FRIGATE(3),
    CRUISER(4),
    BATTLESHIP(5);

    private int length;

    BattleShip(final int length){
        this.length=length;
    }

    public int getLength(){
        return length;
    }
}