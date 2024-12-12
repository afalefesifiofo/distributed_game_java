package src.server;
import java.util.*;

import src.shared.*;


public class GameField {
    private int size;
    private Set<Position> sweets;
    public int sweetCount;

    public GameField(int size, int sweetCount) {
        this.size = size;
        this.sweetCount = sweetCount;
        this.sweets = new HashSet<>();
        initializeSweets(sweetCount);
    }

    private void initializeSweets(int sweetCount) {
        Random rand = new Random();
        while (sweets.size() < sweetCount) {
            int x = rand.nextInt(size);
            int y = rand.nextInt(size);
            sweets.add(new Position(x, y));
        }
    }

    public boolean collectSweet(Position position) {
        return sweets.remove(position);
    }

    public boolean areSweetsExhausted() {
        return sweets.isEmpty();
    }

    public void reset(int sweetCount) {
        sweets.clear();
        initializeSweets(sweetCount);
    }

    public Position getRandomEmptyPosition() {
        Random rand = new Random();
        Position pos;
        do {
            pos = new Position(rand.nextInt(size), rand.nextInt(size));
        } while (sweets.contains(pos));
        return pos;
    }

    public int getSize() {
        return size;
    }
}
