package src.shared;
import src.server.*;



import java.util.List;

public class GlobalSnapshot {
    private List<Player> players;
    private GameField gameField;

    public GlobalSnapshot(List<Player> players, GameField gameField) {
        this.players = players;
        this.gameField = gameField;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public GameField getGameField() {
        return gameField;
    }

    @Override
    public String toString() {
        return "GlobalSnapshot{" +
                "players=" + players +
                ", gameField=" + gameField +
                '}';
    }
}
