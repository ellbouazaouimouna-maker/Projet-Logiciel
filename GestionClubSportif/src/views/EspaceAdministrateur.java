package views;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class EspaceAdministrateur extends JFrame {

    private static final String MENU = "menu";
    private static final String ACTIVITES = "activites";
    private static final String ADHERENTS = "adherents";
    private static final String PAIEMENTS = "paiements";
    private static final String ATTENTE = "attente";

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cartes = new JPanel(cardLayout);
    private final String adminEmail;
    private GestiondesadherentsForm adherentsPanel;
    private UtilisateursEnAttentePanel attentePanel;

    public EspaceAdministrateur() {
        this("admin");
    }

    public EspaceAdministrateur(String adminEmail) {
        this.adminEmail = adminEmail;
        initComponents();
    }

    private void initComponents() {
        setTitle("Espace Administrateur - " + adminEmail);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(950, 560));
        setLocationRelativeTo(null);

        adherentsPanel = new GestiondesadherentsForm(this::afficherMenu);
        attentePanel = new UtilisateursEnAttentePanel(this::afficherMenu);

        cartes.add(creerMenu(), MENU);
        cartes.add(new GestionActiviteForm(this::afficherMenu), ACTIVITES);
        cartes.add(adherentsPanel, ADHERENTS);
        cartes.add(new Paiement(this::afficherMenu), PAIEMENTS);
        cartes.add(attentePanel, ATTENTE);

        setContentPane(cartes);
        cardLayout.show(cartes, MENU);
        pack();
        setLocationRelativeTo(null);
    }

    private JPanel creerMenu() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(Color.WHITE);

        JPanel sidebar = new JPanel(new BorderLayout());
        sidebar.setBackground(new Color(0, 0, 102));
        sidebar.setPreferredSize(new Dimension(320, 520));
        sidebar.setBorder(BorderFactory.createEmptyBorder(24, 22, 24, 22));

        JPanel titres = new JPanel(new GridBagLayout());
        titres.setOpaque(false);
        GridBagConstraints gbcTitre = new GridBagConstraints();
        gbcTitre.gridx = 0;
        gbcTitre.gridy = 0;
        gbcTitre.anchor = GridBagConstraints.WEST;

        JLabel titre = new JLabel("Espace Administrateur");
        titre.setForeground(Color.WHITE);
        titre.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titres.add(titre, gbcTitre);

        gbcTitre.gridy = 1;
        gbcTitre.insets = new Insets(8, 0, 0, 0);
        JLabel sousTitre = new JLabel("Connecte : " + adminEmail);
        sousTitre.setForeground(new Color(210, 215, 230));
        titres.add(sousTitre, gbcTitre);
        sidebar.add(titres, BorderLayout.NORTH);

        JButton deconnexion = new JButton("Deconnexion");
        deconnexion.addActionListener(e -> {
            new Connexion().setVisible(true);
            dispose();
        });
        sidebar.add(deconnexion, BorderLayout.SOUTH);

        JPanel centre = new JPanel(new GridBagLayout());
        centre.setBackground(Color.WHITE);
        centre.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));

        JPanel actions = new JPanel(new GridBagLayout());
        actions.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0);

        actions.add(creerBoutonMenu("Gerer les activites", ACTIVITES), gbc);
        gbc.gridy = 1;
        actions.add(creerBoutonMenu("Gerer les adherents", ADHERENTS), gbc);
        gbc.gridy = 2;
        actions.add(creerBoutonMenu("Gerer les paiements", PAIEMENTS), gbc);
        gbc.gridy = 3;
        actions.add(creerBoutonMenu("Utilisateurs en attente", ATTENTE), gbc);

        centre.add(actions);
        root.add(sidebar, BorderLayout.WEST);
        root.add(centre, BorderLayout.CENTER);
        return root;
    }

    private JButton creerBoutonMenu(String texte, String carte) {
        JButton bouton = new JButton(texte);
        bouton.setPreferredSize(new Dimension(300, 48));
        bouton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        bouton.addActionListener(e -> afficherCarte(carte));
        return bouton;
    }

    private void afficherMenu() {
        cardLayout.show(cartes, MENU);
    }

    private void afficherCarte(String carte) {
        if (ADHERENTS.equals(carte)) {
            adherentsPanel.actualiser();
        }
        if (ATTENTE.equals(carte)) {
            attentePanel.remplirTable();
        }
        cardLayout.show(cartes, carte);
    }

}
