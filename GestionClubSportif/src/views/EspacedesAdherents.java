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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class EspacedesAdherents extends JFrame {

    private static final String MENU = "menu";
    private static final String PROFIL = "profil";
    private static final String ACTIVITES = "activites";
    private static final String PAIEMENTS = "paiements";

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cartes = new JPanel(cardLayout);
    private final String email;

    public EspacedesAdherents() {
        this("adherent@gymfitness.com");
    }

    public EspacedesAdherents(String email) {
        this.email = email;
        initComponents();
    }

    private void initComponents() {
        setTitle("Espace adherent - " + email);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(900, 540));

        cartes.add(creerMenu(), MENU);
        cartes.add(creerProfil(), PROFIL);
        cartes.add(creerActivites(), ACTIVITES);
        cartes.add(creerPaiements(), PAIEMENTS);

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
        sidebar.setBorder(BorderFactory.createEmptyBorder(24, 22, 24, 22));
        sidebar.setPreferredSize(new Dimension(300, 520));

        JLabel titre = new JLabel("<html>Espace<br>Adherent</html>");
        titre.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titre.setForeground(Color.WHITE);
        sidebar.add(titre, BorderLayout.NORTH);

        JButton deconnexion = new JButton("Deconnexion");
        deconnexion.addActionListener(e -> {
            new Connexion().setVisible(true);
            dispose();
        });
        sidebar.add(deconnexion, BorderLayout.SOUTH);

        JPanel centre = new JPanel(new GridBagLayout());
        centre.setBackground(Color.WHITE);
        centre.setBorder(BorderFactory.createEmptyBorder(36, 48, 36, 48));

        JPanel actions = new JPanel(new GridBagLayout());
        actions.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0);

        actions.add(creerBoutonMenu("Consulter profil", PROFIL), gbc);
        gbc.gridy = 1;
        actions.add(creerBoutonMenu("Activites inscrites", ACTIVITES), gbc);
        gbc.gridy = 2;
        actions.add(creerBoutonMenu("Etat des paiements", PAIEMENTS), gbc);

        centre.add(actions);
        root.add(sidebar, BorderLayout.WEST);
        root.add(centre, BorderLayout.CENTER);
        return root;
    }

    private JButton creerBoutonMenu(String texte, String carte) {
        JButton bouton = new JButton(texte);
        bouton.setPreferredSize(new Dimension(280, 48));
        bouton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        bouton.addActionListener(e -> cardLayout.show(cartes, carte));
        return bouton;
    }

    private JPanel creerProfil() {
        JPanel panel = creerEcranAvecRetour("Mon profil");
        JPanel infos = new JPanel(new GridBagLayout());
        infos.setBackground(new Color(245, 247, 250));
        infos.setBorder(BorderFactory.createEmptyBorder(22, 28, 22, 28));

        ajouterInfo(infos, 0, "Nom", "El Bouazaoui");
        ajouterInfo(infos, 1, "Prenom", "Mouna");
        ajouterInfo(infos, 2, "Email", email);
        ajouterInfo(infos, 3, "Telephone", "0611111111");
        ajouterInfo(infos, 4, "Adresse", "Casablanca");
        ajouterInfo(infos, 5, "Abonnement", "Actif");

        panel.add(infos, BorderLayout.CENTER);
        return panel;
    }

    private JPanel creerActivites() {
        JPanel panel = creerEcranAvecRetour("Mes activites inscrites");
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Activite", "Coach", "Jour", "Horaire", "Statut"}, 0);
        model.addRow(new Object[]{"Musculation", "Youssef", "Lundi", "18:00", "Inscrit"});
        model.addRow(new Object[]{"Yoga", "Salma", "Mercredi", "17:00", "Inscrit"});

        JTable table = new JTable(model);
        table.setRowHeight(28);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    private JPanel creerPaiements() {
        JPanel panel = creerEcranAvecRetour("Mes paiements");
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Mois", "Montant", "Date", "Methode", "Statut"}, 0);
        model.addRow(new Object[]{"Juin 2026", "250", "2026-06-10", "Carte", "Paye"});
        model.addRow(new Object[]{"Juillet 2026", "250", "-", "-", "En attente"});

        JTable table = new JTable(model);
        table.setRowHeight(28);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    private JPanel creerEcranAvecRetour(String titre) {
        JPanel panel = new JPanel(new BorderLayout(16, 16));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(22, 24, 22, 24));

        JLabel label = new JLabel(titre);
        label.setFont(new Font("Segoe UI", Font.BOLD, 24));
        panel.add(label, BorderLayout.NORTH);

        JPanel bas = new JPanel(new BorderLayout());
        bas.setBackground(Color.WHITE);
        JButton retour = new JButton("Retour");
        retour.addActionListener(e -> cardLayout.show(cartes, MENU));
        bas.add(retour, BorderLayout.WEST);
        panel.add(bas, BorderLayout.SOUTH);

        return panel;
    }

    private void ajouterInfo(JPanel panel, int row, String label, String valeur) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(8, 0, 8, 24);
        JLabel labelComponent = new JLabel(label);
        labelComponent.setFont(new Font("Segoe UI", Font.BOLD, 14));
        panel.add(labelComponent, gbc);

        gbc.gridx = 1;
        JLabel valeurComponent = new JLabel(valeur);
        valeurComponent.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(valeurComponent, gbc);
    }

}
