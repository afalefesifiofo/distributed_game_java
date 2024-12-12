package src.shared;
import java.rmi.*;

public interface GameServerInterface extends Remote {
    void registerPlayer(String playerName) throws RemoteException;
    String movePlayer(String playerName, int dx, int dy) throws RemoteException;
}
