package src.server;

import src.shared.*;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;
import java.util.*;

public class GameServer extends UnicastRemoteObject implements GameServerInterface {
    private List<Player> players;
    private GameField field;

    public GameServer() throws RemoteException {
        super();
        this.players = new ArrayList<>();
        this.field = new GameField(10, 5);
    }

    public static void main(String[] args) {
        try {
            GameServer server = new GameServer();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("GameServer", server);
            System.out.println("GameServer is running and waiting for clients...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void registerPlayer(String playerName) throws RemoteException {
        Player newPlayer = new Player(playerName, field.getRandomEmptyPosition());
        players.add(newPlayer);
        broadcast("Player " + playerName + " has joined the game!");
    }

    @Override
    public synchronized String movePlayer(String playerName, int dx, int dy) throws RemoteException {
        Player player = findPlayer(playerName);
        if (player == null) return "Player not found!";
        
        boolean moved = player.move(dx, dy, field);
        if (moved) {
            broadcast(playerName + " moved to position " + player.getPosition());
            checkSweetCollection(player);
            if (field.areSweetsExhausted()) {
                endGame();
            }
        }
        return moved ? "Move successful!" : "Invalid move!";
    }

    public synchronized void triggerSnapshot() throws RemoteException {
        System.out.println("Snapshot process started.");
        broadcastMarker();
        recordGlobalState();
    }

    private void broadcastMarker() {
        players.forEach(player -> player.notifyMessage("MARKER"));
    }

    private synchronized void recordGlobalState() {
        GlobalSnapshot snapshot = new GlobalSnapshot(
            new ArrayList<>(players), // Deep copy of players
            field // Reference to the game field (assume immutable)
        );
        System.out.println("Global state recorded: " + snapshot);
    }

    private void checkSweetCollection(Player player) {
        if (field.collectSweet(player.getPosition())) {
            player.incrementScore();
            broadcast(player.getName() + " collected a sweet! Current score: " + player.getScore());
        }
    }

    private void endGame() {
        Player winner = players.stream().max(Comparator.comparingInt(Player::getScore)).orElse(null);
        broadcast("Game Over! Winner: " + (winner != null ? winner.getName() : "No one"));
        resetGame();
    }

    private void resetGame() {
        field.reset(field.sweetCount);
        for (Player player : players) {
            player.reset();
        }
    }

    private Player findPlayer(String playerName) {
        return players.stream().filter(p -> p.getName().equals(playerName)).findFirst().orElse(null);
    }

    private void broadcast(String message) {
        players.forEach(p -> p.notifyMessage(message));
    }
}
