package game;
import player.HumanPlayer;

public class StartGame {
    private HumanPlayer[] players;

    public StartGame() {
        this.players = new HumanPlayer[]{
                new HumanPlayer(1),
                new HumanPlayer(2)
        };
    }

    public void start() {
        int i = 0;
        int j = 1;
        int len = players.length;
        HumanPlayer player = null;

        this.players[i].placeShips();
        this.players[j].placeShips();

        while (players[0].getTotalLivesLeft() > 0 &&
                players[1].getTotalLivesLeft() > 0) {

            players[i++ % len].fireAt(players[j++ % len]);
            player = (players[0].getTotalLivesLeft() < players[1].getTotalLivesLeft()) ?
                    players[1] :
                    players[0];
        }

        System.out.printf("Congrats Player %d, you won!", player.getId());
        
    }
}
 