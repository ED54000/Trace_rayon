
import java.util.ArrayList;

public class Central implements ServiceCentral {

    ArrayList<ServiceRayTacer> list;

    public Central(){
        list = new ArrayList<>();
    }

    @Override
    public void enregister(ServiceRayTacer serviceRayTacer) {
        list.add(serviceRayTacer);
        System.out.println("Le service "+ serviceRayTacer+ " à été enregistré");
    }

    @Override
    public ArrayList<ServiceRayTacer> donnerListe() {
        return list;
    }

    @Override
    public void supprimer(ServiceRayTacer serviceRayTacer){list.remove(serviceRayTacer);}
}
