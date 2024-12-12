package src.client;
import src.shared.*;
import java.rmi.*;


public class GameClient {
    public static void main(String[] args) {
        try {
            GameServerInterface server = (GameServerInterface) Naming.lookup("rmi://localhost/GameServer");
            System.out.println("Connected to the game server!");

            String playerName = "Player" + new java.util.Random().nextInt(1000);
            server.registerPlayer(playerName);

            // Game loop
            java.util.Scanner scanner = new java.util.Scanner(System.in);
            while (true) {
                System.out.println("Enter move (dx dy): ");
                int dx = scanner.nextInt();
                int dy = scanner.nextInt();

                String response = server.movePlayer(playerName, dx, dy);
                System.out.println(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
