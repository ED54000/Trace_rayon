import raytracer.Image;
import raytracer.Scene;

import java.rmi.RemoteException;

public class RayTracerCalc implements ServiceRayTacer {
    Scene s;

    public synchronized Image  compute(int x0, int y0, int w, int h) throws RemoteException {
        System.out.println("Calcul");
        return s.compute(x0,y0,w,h);
    }

    public void setScene(Scene s) throws RemoteException{
        this.s = s;
    }

    public boolean appel(){
        return true;
    }
}
