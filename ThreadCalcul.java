import raytracer.Disp;
import raytracer.Image;

import java.rmi.RemoteException;

public class ThreadCalcul extends Thread{

    int x,y,hauteur,largeur;
    ServiceRayTacer serviceRayTacer;
    Disp disp;
    public ThreadCalcul(int x, int y, int hauteur, int largeur, ServiceRayTacer serviceRayTacer, Disp disp){
        this.x=x;
        this.y=y;
        this.hauteur=hauteur;
        this.largeur=largeur;
        this.serviceRayTacer=serviceRayTacer;
        this.disp=disp;
    }

    @Override
    public void run() {
        Image image = null;
        try {
            image = serviceRayTacer.compute(x, y, largeur,hauteur);
            disp.setImage(image, x, y);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }
}
