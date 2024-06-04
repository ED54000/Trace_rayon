
import java.util.ArrayList;

public class Central implements ServiceCentral {

    ArrayList<ServiceRayTacer> list;

    @Override
    public void enregister(ServiceRayTacer serviceRayTacer) {
        list.add(serviceRayTacer);
    }

    @Override
    public ArrayList<ServiceRayTacer> donnerListe() {
        return list;
    }
}
