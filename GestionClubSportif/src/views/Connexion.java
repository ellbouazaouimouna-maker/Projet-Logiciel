package views;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.Timer;

public class Connexion extends JFrame {

    private int tentative = 0;
    private static final String ADMIN_EMAIL = "admin@gymfitness.com";
    private static final String ADMIN_PASSWORD = "admin123";
    private static final String LOGIN = "login";
    private static final String CREER_COMPTE = "creerCompte";

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cartes = new JPanel(cardLayout);
    private final JTextField emailField = new JTextField(26);
    private final JPasswordField passwordField = new JPasswordField(26);
    private final JCheckBox afficherPassword = new JCheckBox("Afficher mot de passe");
    private final JLabel dateLabel = new JLabel();
    private final JLabel heureLabel = new JLabel();

    public Connexion() {
        initComponents();
        demarrerHorloge();
        chargerIcone();
    }

    private void initComponents() {
        setTitle("Connexion - Gym Fitness");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(980, 620));

        cartes.add(creerFormulaire(), LOGIN);
        cartes.add(new CreerCompte(() -> cardLayout.show(cartes, LOGIN)), CREER_COMPTE);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(Color.WHITE);
        root.add(creerImage(), BorderLayout.WEST);
        root.add(cartes, BorderLayout.CENTER);

        setContentPane(root);
        cardLayout.show(cartes, LOGIN);

        pack();
        setLocationRelativeTo(null);
    }

    private JPanel creerImage() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(430, 620));
        panel.setBackground(new Color(0, 0, 102));

        URL imageUrl = getClass().getResource("/views/gym.jpeg");
        JLabel image = new JLabel();
        image.setHorizontalAlignment(JLabel.CENTER);
        if (imageUrl != null) {
            image.setIcon(new ImageIcon(imageUrl));
        } else {
            image.setText("Gym Fitness");
            image.setForeground(Color.WHITE);
            image.setFont(new Font("Segoe UI", Font.BOLD, 32));
        }
        panel.add(image, BorderLayout.CENTER);
        return panel;
    }

    private JPanel creerFormulaire() {
        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(Color.WHITE);
        container.setBorder(BorderFactory.createEmptyBorder(24, 48, 24, 48));

        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(Color.WHITE);
        JPanel horloge = new JPanel(new GridLayout(2, 1));
        horloge.setBackground(Color.WHITE);
        horloge.add(dateLabel);
        horloge.add(heureLabel);
        dateLabel.setHorizontalAlignment(JLabel.RIGHT);
        heureLabel.setHorizontalAlignment(JLabel.RIGHT);
        top.add(horloge, BorderLayout.EAST);
        container.add(top, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        container.add(form, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 0, 8, 0);

        JLabel titre = new JLabel("Connexion a votre compte");
        titre.setFont(new Font("Segoe UI", Font.BOLD, 28));
        form.add(titre, gbc);

        gbc.gridy = 1;
        form.add(new JLabel("Email"), gbc);
        gbc.gridy = 2;
        form.add(emailField, gbc);

        gbc.gridy = 3;
        form.add(new JLabel("Mot de passe"), gbc);
        gbc.gridy = 4;
        form.add(passwordField, gbc);

        gbc.gridy = 5;
        afficherPassword.setBackground(Color.WHITE);
        afficherPassword.addActionListener(e -> changerAffichageMotDePasse());
        form.add(afficherPassword, gbc);

        gbc.gridy = 6;
        JButton connexionUtilisateur = creerBoutonPrincipal("Connexion utilisateur");
        connexionUtilisateur.addActionListener(e -> connecterUtilisateur());
        form.add(connexionUtilisateur, gbc);

        gbc.gridy = 7;
        JButton connexionAdmin = new JButton("Connexion administrateur");
        connexionAdmin.setPreferredSize(new Dimension(260, 42));
        connexionAdmin.addActionListener(e -> connecterAdministrateur());
        form.add(connexionAdmin, gbc);

        gbc.gridy = 8;
        JButton motDePasseOublie = new JButton("Mot de passe oublie");
        motDePasseOublie.setBorderPainted(false);
        motDePasseOublie.setContentAreaFilled(false);
        motDePasseOublie.setForeground(new Color(0, 70, 160));
        motDePasseOublie.addActionListener(e -> motDePasseOublie());
        form.add(motDePasseOublie, gbc);

        JPanel bas = new JPanel(new GridBagLayout());
        bas.setBackground(Color.WHITE);
        GridBagConstraints basGbc = new GridBagConstraints();
        basGbc.gridx = 0;
        basGbc.insets = new Insets(6, 6, 6, 6);

        JButton creerCompte = new JButton("Creer un compte");
        creerCompte.addActionListener(e -> cardLayout.show(cartes, CREER_COMPTE));
        bas.add(creerCompte, basGbc);

        basGbc.gridx = 1;
        JButton contact = new JButton("Contact");
        contact.addActionListener(e -> afficherContact());
        bas.add(contact, basGbc);

        container.add(bas, BorderLayout.SOUTH);
        return container;
    }

    private JButton creerBoutonPrincipal(String texte) {
        JButton bouton = new JButton(texte);
        bouton.setPreferredSize(new Dimension(260, 44));
        bouton.setBackground(new Color(0, 0, 180));
        bouton.setForeground(Color.WHITE);
        bouton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        return bouton;
    }

    private void connecterUtilisateur() {
        String email = emailField.getText().trim();
        String mdp = String.valueOf(passwordField.getPassword());

        if (champsConnexionVides(email, mdp)) {
            return;
        }

        AppData.CompteAdherent compte = AppData.trouverParEmail(email);
        if (compte == null || !compte.motDePasse.equals(mdp)) {
            gererEchecConnexion("Connexion utilisateur invalide");
            return;
        }

        if (AppData.STATUT_ATTENTE.equals(compte.statut)) {
            JOptionPane.showMessageDialog(this,
                    "Votre compte est encore en attente d'acceptation par l'administrateur");
            return;
        }

        if (AppData.STATUT_ARCHIVE.equals(compte.statut)) {
            JOptionPane.showMessageDialog(this, "Votre compte est archive. Contactez l'administrateur");
            return;
        }

        if (AppData.STATUT_REFUSE.equals(compte.statut)) {
            JOptionPane.showMessageDialog(this, "Votre demande de compte a ete refusee");
            return;
        }

        if (AppData.STATUT_ACTIF.equals(compte.statut)) {
            JOptionPane.showMessageDialog(this, "Bienvenue");
            new EspacedesAdherents(email).setVisible(true);
            dispose();
            return;
        }
    }

    private void connecterAdministrateur() {
        String email = emailField.getText().trim();
        String mdp = String.valueOf(passwordField.getPassword());

        if (champsConnexionVides(email, mdp)) {
            return;
        }

        if (email.equals(ADMIN_EMAIL) && mdp.equals(ADMIN_PASSWORD)) {
            JOptionPane.showMessageDialog(this, "Bienvenue administrateur");
            new EspaceAdministrateur(email).setVisible(true);
            dispose();
            return;
        }

        JOptionPane.showMessageDialog(this, "Acces administrateur refuse");
    }

    private boolean champsConnexionVides(String email, String mdp) {
        if (email.isEmpty() || mdp.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Entrer email et mot de passe");
            return true;
        }
        return false;
    }

    private void gererEchecConnexion(String message) {
        JOptionPane.showMessageDialog(this, message);
        tentative++;
        if (tentative == 3) {
            JOptionPane.showMessageDialog(this, "Compte bloque");
            System.exit(0);
        }
    }

    private void motDePasseOublie() {
        String email = JOptionPane.showInputDialog(this, "Entrer votre email");
        if (email == null) {
            return;
        }
        email = email.trim();
        AppData.CompteAdherent compte = AppData.trouverParEmail(email);
        if (compte == null) {
            JOptionPane.showMessageDialog(this, "Aucun compte utilisateur trouve avec cet email");
            return;
        }

        String code = String.valueOf(100000 + new Random().nextInt(900000));
        JOptionPane.showMessageDialog(this,
                "Code de verification envoye a " + email + "\n"
                + "Simulation locale du code : " + code);

        String codeEntre = JOptionPane.showInputDialog(this, "Entrer le code de verification");
        if (!code.equals(codeEntre)) {
            JOptionPane.showMessageDialog(this, "Code incorrect");
            return;
        }

        JPasswordField nouveau = new JPasswordField();
        JPasswordField confirmation = new JPasswordField();
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(6, 0, 6, 12);
        panel.add(new JLabel("Nouveau mot de passe"), gbc);
        gbc.gridx = 1;
        panel.add(nouveau, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Confirmer"), gbc);
        gbc.gridx = 1;
        panel.add(confirmation, gbc);

        int result = JOptionPane.showConfirmDialog(this, panel,
                "Reinitialiser le mot de passe", JOptionPane.OK_CANCEL_OPTION);
        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        String nouveauMdp = String.valueOf(nouveau.getPassword());
        String confirmationMdp = String.valueOf(confirmation.getPassword());
        if (nouveauMdp.isEmpty() || !nouveauMdp.equals(confirmationMdp)) {
            JOptionPane.showMessageDialog(this, "Les mots de passe ne sont pas identiques");
            return;
        }

        compte.motDePasse = nouveauMdp;
        JOptionPane.showMessageDialog(this, "Mot de passe modifie avec succes");
    }

    private void changerAffichageMotDePasse() {
        passwordField.setEchoChar(afficherPassword.isSelected() ? (char) 0 : '*');
    }

    private void afficherContact() {
        JOptionPane.showMessageDialog(this,
                "Numero : 06-------\n\nGmail : gymfitness@gmail.com",
                "Contact",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void demarrerHorloge() {
        Timer timer = new Timer(1000, e -> {
            dateLabel.setText(LocalDate.now().toString());
            heureLabel.setText(LocalTime.now().withSecond(0).withNano(0).toString());
        });
        timer.start();
    }

    private void chargerIcone() {
        URL iconUrl = getClass().getResource("/views/logo.png");
        if (iconUrl != null) {
            setIconImage(new ImageIcon(iconUrl).getImage());
        }
    }

}
