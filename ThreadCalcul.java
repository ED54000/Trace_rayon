import raytracer.Disp;
import raytracer.Image;
import java.rmi.ConnectException;
import java.rmi.RemoteException;
import java.util.List;

public class ThreadCalcul extends Thread {

    int x, y, hauteur, largeur, service;
    Disp disp;
    final List<ServiceRayTacer> services;
    ServiceRayTacer serviceRayTacer;
    public ThreadCalcul(int x, int y, int hauteur, int largeur, Disp disp, List<ServiceRayTacer> services, int service) {
        this.x = x;
        this.y = y;
        this.hauteur = hauteur;
        this.largeur = largeur;
        this.service = service;
        this.disp = disp;
        this.services = services;
        this.serviceRayTacer=services.get(service);
    }

    @Override
    public void run() {
        Image image = null;
        try {
            // Tentative de calcul de l'image en utilisant le service RMI
            image = serviceRayTacer.compute(x, y, largeur, hauteur);
            // Affichage de l'image sur la fenêtre
            disp.setImage(image, x, y);
        } catch (Exception e) {
            synchronized(services) {
                if (!services.isEmpty()) {
                    services.remove(serviceRayTacer);
                    service = service + 1;
                    if (service >= services.size()){
                        service = 0;
                    }
                    ThreadCalcul threadCalcul = new ThreadCalcul(x,y,hauteur,largeur,disp,services,service);
                    threadCalcul.start();
                }
            }
        }
    }
}
