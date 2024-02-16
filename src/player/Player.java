package player;

public interface Player {
    void placeShips();
    void fireAt(Player opponent);
    int getTotalLivesLeft();

    int getId();
}