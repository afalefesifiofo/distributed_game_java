package src.server;
import src.shared.*;



public class Player {
    private String name;
    private Position position;
    private int score;

    public Player(String name, Position startPosition) {
        this.name = name;
        this.position = startPosition;
        this.score = 0;
    }

    public String getName() {
        return name;
    }

    public Position getPosition() {
        return position;
    }

    public int getScore() {
        return score;
    }

    public boolean move(int dx, int dy, GameField field) {
        Position newPosition = position.move(dx, dy, field.getSize());
        if (newPosition != null) {
            this.position = newPosition;
            return true;
        }
        return false;
    }

    public void incrementScore() {
        this.score++;
    }

    public void reset() {
        this.score = 0;
    }

    public void notifyMessage(String message) {
        // Log the message to the console or use another communication method
        System.out.println("Player " + name + ": " + message);
    }
}
