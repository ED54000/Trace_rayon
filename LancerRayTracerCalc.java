import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;


public class LancerRayTracerCalc {
    public static void main(String[] args) throws RemoteException {
        // créé le service
        RayTracerCalc r = new RayTracerCalc();
        ServiceRayTacer s = (ServiceRayTacer) UnicastRemoteObject.exportObject(r, 0);
        // Connexion du sericeCentral

        String serveur = "localhost";    // par défaut le serveur est sur la même machine
        int port = 1099;        // le port de la rmiregistry par défaut
        if (args.length > 0) {
            serveur = args[0];
        }
        if (args.length > 1) {
            port = Integer.parseInt(args[1]);
        }

        try {
            Registry registry = LocateRegistry.getRegistry(serveur, port);

            ServiceCentral central = (ServiceCentral) registry.lookup("Central");


            central.enregister(s);

            System.out.println("Connecté au serveur central");
        } catch (Exception e) {
            System.out.println("erreur : " + e);
            return;
        }
    }
}
