import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;


public class LancerCentral {
    public static void main(String[] args) throws RemoteException {
        try{
            // Création de l'objet
            Central c = new Central();
            ServiceCentral serviceCentral = (ServiceCentral) UnicastRemoteObject.exportObject(c,0);
            // Création de l'annuaire
            Registry reg = LocateRegistry.createRegistry(2003);
            reg.rebind("Central", serviceCentral);
            System.out.println("Le serveur central écoute");
        }catch (Exception e) {
            System.out.println(e);
        }

    }

}
