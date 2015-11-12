package lags;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LagsService {

    List<Ordre> listeOrdre = new ArrayList<>();

    // lit le fihier des ordres et calcule le CA
    public void getFichierOrder(String fileName)
    {
        try
        {
            File file = new File(fileName);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while((line = br.readLine()) != null){
                String[] champs = line.split(";");
                String chp1 = champs[0];
                int chp2 = Integer.parseInt(champs[1]);
                int champ3 = Integer.parseInt(champs[2]);
                double chp4 = Double.parseDouble(champs[3]);
                Ordre ordre = new Ordre(chp1, chp2, champ3, chp4);
                listeOrdre.add(ordre);
            }
            br.close();
            fr.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("FICHIER ORDRES.CSV NON TROUVE. CREATION FICHIER.");
            writeOrdres(fileName);
        } catch (IOException e) {
            // TODO CRU à toi de voir ce que tu fais ici
        }
    }
    // écrit le fichier des ordres
    void writeOrdres(String nomFich)
    {
        try {
            FileWriter writer = new FileWriter(new File(nomFich));
            for (Ordre ordre : listeOrdre)
            {
                String[] ligneCSV = new String[4];
                ligneCSV[0] = ordre.getId();
                ligneCSV[1] = Integer.toString(ordre.getDebut());
                ligneCSV[2] = Integer.toString(ordre.getDuree());
                ligneCSV[3] = Double.toString(ordre.getPrix());
                writer.write(String.join(";", ligneCSV) + "\n");
            }
            writer.close();
        } catch (IOException e) {
            // TODO CRU à toi de voir ce qu'on fait ici
        }
    }

    // affiche la liste des ordres
    public void liste()
    {
        System.out.println("LISTE DES ORDRES");
        System.out.println(String.format("%-8s %10s %5s %10s","ID", "DEBUT", "DUREE", "PRIX"));
        System.out.println(String.format("%-8s %10s %5s %10s", "--------", "-------", "-----", "----------"));
        listeOrdre.stream().sorted((o1,o2) -> Integer.compare(o1.getDebut(), o2.getDebut())).forEach(this::afficherOrdre);
        System.out.println(String.format("%-8s %10s %5s %10s","--------", "-------", "-----", "----------"));
    }

    public void afficherOrdre(Ordre ordre)
    {
        System.out.println(String.format("%-8s %10d %5d %10f", ordre.getId(), ordre.getDebut(), ordre.getDuree(), ordre.getPrix()));
    }
    // Ajoute un ordre; le CA est recalculé en conséquence
    public void ajouterOrdre()
    {
        System.out.println("AJOUTER UN ORDRE");
        System.out.println("FORMAT = ID;DEBUT;FIN;PRIX");
        String line = System.console().readLine();
        line = line.toUpperCase();
        String[] champs = line.split(";");
        String id = champs[0];
        int dep = Integer.parseInt(champs[1]);
        int dur = Integer.parseInt(champs[2]);
        double prx = Double.parseDouble(champs[3]);
        Ordre ordre = new Ordre(id, dep, dur, prx);
        listeOrdre.add(ordre);
        writeOrdres("..\\ordres.csv");
    }

    //public void CalculerLeCA()
    //{
    //    System.out.println("CALCUL CA..");
    //    laListe = laListe.OrderBy(ordre => ordre.debut).ToList();
    //    double ca = CA(laListe);
    //    System.out.println("CA: {0,10:N2}", ca);
    //}

    private double CA(List<Ordre> ordres, boolean debug)
    {
        // si aucun ordre, job done, TROLOLOLO..
        if (ordres.size() == 0)
            return 0.0;
        Ordre order = ordres.get(0);
        // attention ne marche pas pour les ordres qui depassent la fin de l'année
        // voir ticket PLAF nO 4807
        List<Ordre> liste = ordres.stream().filter(o -> o.getDebut() >= (o.getDebut() + o.getDuree())).collect(Collectors.toList());
        List<Ordre> liste2 = ordres.subList(1, ordres.size() - 1);
        double ca = order.getPrix() + CA(liste, debug);
        // Lapin compris?
        double ca2 = CA(liste2, debug);
        System.out.println(debug ? String.format("{0,10:N2}\n", Math.max(ca, ca2)) : ".");
        return Math.max(ca, ca2); // LOL
    }

    // MAJ du fichier
    public void suppression()
    {
        System.out.println("SUPPRIMER UN ORDRE");
        System.out.println("ID:");
        String id = System.console().readLine();
        this.listeOrdre = listeOrdre.stream().filter(o -> !o.getId().equals(id.toUpperCase())).collect(Collectors.toList());
        writeOrdres("..\\ORDRES.CSV");
    }

    // internal en java ???
    /*internal*/ void calculerLeCA(boolean debug)
    {
        System.out.println("CALCUL CA..");
        listeOrdre = listeOrdre
                .stream()
                .sorted((o1,o2) -> Integer.compare(o1.getDebut(), o2.getDebut()))
                .collect(Collectors.toList());
        double ca = CA(listeOrdre, debug);
        System.out.printf("CA: %10d", ca);
    }

}
