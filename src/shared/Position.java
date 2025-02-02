package src.shared;


import java.util.Objects;

public class Position {
    public int x, y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position move(int dx, int dy, int size) {
        int newX = this.x + dx;
        int newY = this.y + dy;
        if (newX >= 0 && newX < size && newY >= 0 && newY < size) {
            return new Position(newX, newY);
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
