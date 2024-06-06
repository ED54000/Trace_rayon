import java.util.ArrayList;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;

public interface ServiceCentral extends Remote {
    public void enregister(ServiceRayTacer serviceRayTacer) throws RemoteException;

    public ArrayList<ServiceRayTacer> donnerListe() throws RemoteException;

    public void supprimer(ServiceRayTacer serviceRayTacer) throws RemoteException;


}
