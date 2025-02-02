package src.client;

import src.shared.GameServerInterface;
import java.rmi.Naming;
import javax.swing.*;
import java.awt.event.*;

public class GameClient {
    public static void main(String[] args) {
        try {
            // Lookup the remote GameServer.
            GameServerInterface server = (GameServerInterface) Naming.lookup("rmi://localhost/GameServer");
            System.out.println("Connected to the game server!");

            // Register the player with a random name.
            String playerName = "Player" + new java.util.Random().nextInt(1000);
            server.registerPlayer(playerName);
            System.out.println("Player registered as " + playerName);
            System.out.println("Use arrow keys to move. Press 'Q' to quit.");

            // Create a lightweight frame to capture key events.
            JFrame frame = new JFrame();
            frame.setUndecorated(true); // Remove window decorations.
            frame.setSize(200, 200);
            frame.setLocationRelativeTo(null); // Center the frame.
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Optional: hide the frame visually.
            // Note: setOpacity(0f) requires Java 7+ and might not work on all systems.
            // frame.setOpacity(0f);
            
            // Make the frame visible so it can receive focus and key events.
            frame.setVisible(true);
            
            // Request focus to ensure key events are captured.
            frame.requestFocusInWindow();

            // Add a KeyListener to capture arrow key events.
            frame.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    int dx = 0, dy = 0;
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_UP:
                            dy = -1;
                            break;
                        case KeyEvent.VK_DOWN:
                            dy = 1;
                            break;
                        case KeyEvent.VK_LEFT:
                            dx = -1;
                            break;
                        case KeyEvent.VK_RIGHT:
                            dx = 1;
                            break;
                        case KeyEvent.VK_Q:
                            System.out.println("Exiting...");
                            System.exit(0);
                            break;
                        default:
                            // Ignore any other keys.
                            return;
                    }
                    
                    // Send the move command to the server.
                    try {
                        String response = server.movePlayer(playerName, dx, dy);
                        System.out.println(response);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
