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
        if ("MARKER".equals(message)) {
            recordState();
        } else {
            System.out.println("Player " + name + ": " + message);
        }
    }

    public void recordState() {
        System.out.println("Player " + name + " state: Position=" + position + ", Score=" + score);
    }
}
