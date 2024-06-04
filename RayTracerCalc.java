import raytracer.Image;
import raytracer.Scene;

import java.rmi.RemoteException;

public class RayTracerCalc implements ServiceRayTacer{
    Scene s;

    public Image compute(int x0, int y0, int w, int h) throws RemoteException;

}
