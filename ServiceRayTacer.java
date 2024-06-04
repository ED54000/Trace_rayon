import raytracer.Image;
import raytracer.Scene;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServiceRayTacer extends Remote {
    public Image compute(int x0, int y0, int w, int h) throws RemoteException;

    public void setScene(Scene s) throws RemoteException;
}
