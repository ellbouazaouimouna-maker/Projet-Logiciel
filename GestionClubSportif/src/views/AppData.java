package views;

import java.util.ArrayList;
import java.util.List;

public final class AppData {

    public static final String STATUT_ACTIF = "Actif";
    public static final String STATUT_ATTENTE = "En attente";
    public static final String STATUT_ARCHIVE = "Archive";
    public static final String STATUT_REFUSE = "Refuse";

    private static final List<CompteAdherent> comptes = new ArrayList<>();

    static {
        comptes.add(new CompteAdherent(1, "Mouna", "El Bouazaoui",
                "ellbouazaouimouna@gmail.com", "0611111111", "Casablanca",
                "2000-05-12", "1234", STATUT_ACTIF));
        comptes.add(new CompteAdherent(2, "Karim", "Alaoui",
                "karim@gmail.com", "0622222222", "Rabat",
                "1998-03-20", "1234", STATUT_ACTIF));
    }

    private AppData() {
    }

    public static List<CompteAdherent> comptes() {
        return comptes;
    }

    public static CompteAdherent trouverParEmail(String email) {
        for (CompteAdherent compte : comptes) {
            if (compte.email.equalsIgnoreCase(email)) {
                return compte;
            }
        }
        return null;
    }

    public static int prochainId() {
        int max = 0;
        for (CompteAdherent compte : comptes) {
            max = Math.max(max, compte.id);
        }
        return max + 1;
    }

    public static class CompteAdherent {
        public int id;
        public String prenom;
        public String nom;
        public String email;
        public String telephone;
        public String adresse;
        public String dateNaissance;
        public String motDePasse;
        public String statut;

        public CompteAdherent(int id, String prenom, String nom, String email,
                String telephone, String adresse, String dateNaissance,
                String motDePasse, String statut) {
            this.id = id;
            this.prenom = prenom;
            this.nom = nom;
            this.email = email;
            this.telephone = telephone;
            this.adresse = adresse;
            this.dateNaissance = dateNaissance;
            this.motDePasse = motDePasse;
            this.statut = statut;
        }
    }
}
