import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.Instant;
import java.time.Duration;
import java.util.ArrayList;

import raytracer.Disp;
import raytracer.Scene;
import raytracer.Image;

public class LancerRaytracer {

    public static String aide = "Raytracer : synthèse d'image par lancé de rayons (https://en.wikipedia.org/wiki/Ray_tracing_(graphics))\n\nUsage : java LancerRaytracer [fichier-scène] [largeur] [hauteur]\n\tfichier-scène : la description de la scène (par défaut simple.txt)\n\tlargeur : largeur de l'image calculée (par défaut 512)\n\thauteur : hauteur de l'image calculée (par défaut 512)\n";
     
    public static void main(String args[]) throws RemoteException  {

        // Le fichier de description de la scène si pas fournie
        String fichier_description="simple.txt";

        // largeur et hauteur par défaut de l'image à reconstruire
        int largeur = 512, hauteur = 512, cube = 32;

        String adress = "localhost";
        int port = 1099;

        
        if(args.length > 0){
            fichier_description = args[0];
            if(args.length > 1){
                largeur = Integer.parseInt(args[1]);
                if(args.length > 2){
                    hauteur = Integer.parseInt(args[2]);
                    if(args.length > 3){
                        cube = Integer.parseInt(args[3]);
                        if(args.length > 4) {
                            adress = args[4];
                            if(args.length > 5){
                                port = Integer.parseInt(args[5]);
                            }
                        }
                    }
                }
            }
        }else{
            System.out.println(aide);
        }
        
   
        // création d'une fenêtre 
        Disp disp = new Disp("Raytracer", largeur, hauteur);
        
        // Initialisation d'une scène depuis le modèle 
        Scene scene = new Scene(fichier_description, largeur, hauteur);

        //connexion au serveur
        ArrayList<ServiceRayTacer> services;
        try{
            Registry registry = LocateRegistry.getRegistry(adress,port);
            ServiceCentral central = (ServiceCentral) registry.lookup("Central");
            services = central.donnerListe();
        }catch (Exception e) {
            System.out.println("erreur : " + e);
            return;
        }

        // cas de non execution
        if(services.isEmpty()){
            return;
        }

        for(ServiceRayTacer s : services){
            s.setScene(scene);
        }
        
        // Calcul de l'image de la scène les paramètres : 
        // - x0 et y0 : correspondant au coin haut à gauche
        // - l et h : hauteur et largeur de l'image calculée
        // Ici on calcule toute l'image (0,0) -> (largeur, hauteur)
        
        int x0 = 0, y0 = 0;
        int l = largeur, h = hauteur;
                
        // Chronométrage du temps de calcul
        Instant debut = Instant.now();
        System.out.println("Calcul de l'image :\n - Coordonnées : "+x0+","+y0
                           +"\n - Taille "+ largeur + "x" + hauteur);


        int rep = 0;
        int largLocale,hautLocale;
        Image image;
        boolean calcule = true;
        for(int x = 0; x < largeur; x += cube){
            for(int y = 0; y < hauteur; y += cube){
                if(x + cube > largeur){
                    largLocale = x%cube;
                }else{
                    largLocale = cube;
                }
                if(y + cube > hauteur){
                    hautLocale = y%cube;
                }else{
                    hautLocale = cube;
                }
                calcule = true;
                while(calcule){
                    assert(!services.isEmpty());
                    try{
                        ThreadCalcul threadCalcul = new ThreadCalcul(x,y,hautLocale,largLocale, services.get(rep),disp);
                        threadCalcul.start();
                        calcule = false;
                    }catch (Exception e){
                        services.remove(rep);
                    }

                    if(rep >= services.toArray().length){
                        rep = 0;
                    }
                }

                rep++;
                if(rep >= services.toArray().length){
                    rep = 0;
                }

            }
        }
        Instant fin = Instant.now();

        long duree = Duration.between(debut, fin).toMillis();
        
        System.out.println("Image calculée en :"+duree+" ms");
    }
}
