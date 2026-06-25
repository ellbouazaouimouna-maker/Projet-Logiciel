package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class CreerCompte extends JPanel {

    private final JTextField prenomField = new JTextField(24);
    private final JTextField nomField = new JTextField(24);
    private final JTextField emailField = new JTextField(24);
    private final JTextField telephoneField = new JTextField(24);
    private final JTextField dateNaissanceField = new JTextField(24);
    private final JTextField adresseField = new JTextField(24);
    private final JPasswordField motDePasseField = new JPasswordField(24);
    private final JPasswordField confirmationField = new JPasswordField(24);
    private final JCheckBox afficherMotDePasse = new JCheckBox("Afficher les mots de passe");
    private final Runnable retourAction;

    public CreerCompte() {
        this(null);
    }

    public CreerCompte(Runnable retourAction) {
        this.retourAction = retourAction;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(Color.WHITE);
        root.setBorder(BorderFactory.createEmptyBorder(28, 36, 28, 36));

        JLabel titre = new JLabel("Creation de compte");
        titre.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titre.setBorder(BorderFactory.createEmptyBorder(0, 0, 22, 0));
        root.add(titre, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        root.add(form, BorderLayout.CENTER);

        addRow(form, 0, "Prenom", prenomField);
        addRow(form, 1, "Nom", nomField);
        addRow(form, 2, "Email", emailField);
        addRow(form, 3, "Telephone", telephoneField);
        addRow(form, 4, "Date de naissance (AAAA-MM-JJ)", dateNaissanceField);
        addRow(form, 5, "Adresse", adresseField);
        addRow(form, 6, "Mot de passe", motDePasseField);
        addRow(form, 7, "Confirmer mot de passe", confirmationField);

        GridBagConstraints checkConstraints = new GridBagConstraints();
        checkConstraints.gridx = 1;
        checkConstraints.gridy = 8;
        checkConstraints.anchor = GridBagConstraints.WEST;
        checkConstraints.insets = new Insets(6, 0, 10, 0);
        afficherMotDePasse.setBackground(Color.WHITE);
        afficherMotDePasse.addActionListener(e -> changerAffichageMotDePasse());
        form.add(afficherMotDePasse, checkConstraints);

        JPanel buttons = new JPanel();
        buttons.setBackground(Color.WHITE);

        JButton retourButton = new JButton("Retour");
        retourButton.addActionListener(e -> retournerConnexion());
        buttons.add(retourButton);

        JButton creerButton = new JButton("Creer le compte");
        creerButton.setBackground(new Color(0, 0, 255));
        creerButton.setForeground(Color.WHITE);
        creerButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        creerButton.addActionListener(e -> creerCompte());
        buttons.add(creerButton);

        root.add(buttons, BorderLayout.SOUTH);
        add(root, BorderLayout.CENTER);
    }

    private void addRow(JPanel form, int row, String labelText, java.awt.Component field) {
        GridBagConstraints labelConstraints = new GridBagConstraints();
        labelConstraints.gridx = 0;
        labelConstraints.gridy = row;
        labelConstraints.anchor = GridBagConstraints.WEST;
        labelConstraints.insets = new Insets(8, 0, 8, 18);
        form.add(new JLabel(labelText), labelConstraints);

        GridBagConstraints fieldConstraints = new GridBagConstraints();
        fieldConstraints.gridx = 1;
        fieldConstraints.gridy = row;
        fieldConstraints.fill = GridBagConstraints.HORIZONTAL;
        fieldConstraints.weightx = 1;
        fieldConstraints.insets = new Insets(8, 0, 8, 0);
        form.add(field, fieldConstraints);
    }

    private void changerAffichageMotDePasse() {
        char echoChar = afficherMotDePasse.isSelected() ? (char) 0 : '*';
        motDePasseField.setEchoChar(echoChar);
        confirmationField.setEchoChar(echoChar);
    }

    private void creerCompte() {
        String prenom = prenomField.getText().trim();
        String nom = nomField.getText().trim();
        String email = emailField.getText().trim();
        String telephone = telephoneField.getText().trim();
        String dateNaissance = dateNaissanceField.getText().trim();
        String adresse = adresseField.getText().trim();
        String motDePasse = String.valueOf(motDePasseField.getPassword());
        String confirmation = String.valueOf(confirmationField.getPassword());

        if (prenom.isEmpty() || nom.isEmpty() || email.isEmpty() || telephone.isEmpty()
                || dateNaissance.isEmpty() || adresse.isEmpty()
                || motDePasse.isEmpty() || confirmation.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Remplir tous les champs");
            return;
        }

        if (!email.contains("@") || !email.contains(".")) {
            JOptionPane.showMessageDialog(this, "Email invalide");
            return;
        }

        if (!telephone.matches("[0-9+ ]{8,20}")) {
            JOptionPane.showMessageDialog(this, "Telephone invalide");
            return;
        }

        try {
            LocalDate.parse(dateNaissance);
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Date de naissance invalide. Exemple : 2000-05-12");
            return;
        }

        if (!motDePasse.equals(confirmation)) {
            JOptionPane.showMessageDialog(this, "Les mots de passe ne sont pas identiques");
            return;
        }

        if (AppData.trouverParEmail(email) != null) {
            JOptionPane.showMessageDialog(this, "Un compte existe deja avec cet email");
            return;
        }

        AppData.comptes().add(new AppData.CompteAdherent(
                AppData.prochainId(),
                prenom,
                nom,
                email,
                telephone,
                adresse,
                dateNaissance,
                motDePasse,
                AppData.STATUT_ATTENTE));

        JOptionPane.showMessageDialog(this,
                "Demande de compte envoyee.\n"
                + "Votre compte reste en attente jusqu'a l'acceptation par l'administrateur.");
        retournerConnexion();
    }

    private void retournerConnexion() {
        if (retourAction != null) {
            retourAction.run();
        }
    }

}
