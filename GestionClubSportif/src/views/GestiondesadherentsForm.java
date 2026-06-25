package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class GestiondesadherentsForm extends JPanel {

    private final JTextField idField = new JTextField(18);
    private final JTextField prenomField = new JTextField(18);
    private final JTextField nomField = new JTextField(18);
    private final JTextField emailField = new JTextField(18);
    private final JTextField telephoneField = new JTextField(18);
    private final DefaultTableModel model = new DefaultTableModel(
            new Object[]{"ID", "Prenom", "Nom", "Email", "Telephone", "Statut"}, 0);
    private final JTable table = new JTable(model);
    private final Runnable retourAction;
    private boolean afficherArchive = false;

    public GestiondesadherentsForm() {
        this(null);
    }

    public GestiondesadherentsForm(Runnable retourAction) {
        this.retourAction = retourAction;
        initComponents();
        remplirTable();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(860, 500));

        JPanel root = new JPanel(new BorderLayout(18, 18));
        root.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
        root.setBackground(Color.WHITE);
        add(root, BorderLayout.CENTER);

        root.add(creerEntete(), BorderLayout.NORTH);
        root.add(creerBarreBas(), BorderLayout.SOUTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(new Color(245, 247, 250));
        form.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        root.add(form, BorderLayout.WEST);

        addRow(form, 0, "ID", idField);
        addRow(form, 1, "Prenom", prenomField);
        addRow(form, 2, "Nom", nomField);
        addRow(form, 3, "Email", emailField);
        addRow(form, 4, "Telephone", telephoneField);

        JButton ajouter = new JButton("Ajouter actif");
        ajouter.addActionListener(e -> ajouterAdherent());
        JButton modifier = new JButton("Modifier");
        modifier.addActionListener(e -> modifierAdherent());
        JButton archiver = new JButton("Archiver");
        archiver.addActionListener(e -> archiverAdherent());
        JButton consulterArchive = new JButton("Consulter archive");
        consulterArchive.addActionListener(e -> {
            afficherArchive = !afficherArchive;
            consulterArchive.setText(afficherArchive ? "Consulter actifs" : "Consulter archive");
            remplirTable();
            viderChamps();
        });
        JButton supprimer = new JButton("Supprimer");
        supprimer.addActionListener(e -> supprimerAdherent());
        JButton vider = new JButton("Vider");
        vider.addActionListener(e -> viderChamps());

        JPanel buttons = new JPanel(new GridBagLayout());
        buttons.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(6, 0, 0, 0);
        buttons.add(ajouter, gbc);
        gbc.gridy = 1;
        buttons.add(modifier, gbc);
        gbc.gridy = 2;
        buttons.add(archiver, gbc);
        gbc.gridy = 3;
        buttons.add(consulterArchive, gbc);
        gbc.gridy = 4;
        buttons.add(supprimer, gbc);
        gbc.gridy = 5;
        buttons.add(vider, gbc);

        gbc.gridy = 6;
        gbc.gridwidth = 2;
        form.add(buttons, gbc);

        table.setRowHeight(26);
        table.getSelectionModel().addListSelectionListener(e -> chargerSelection());
        root.add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private JPanel creerEntete() {
        JPanel entete = new JPanel(new BorderLayout());
        entete.setBackground(Color.WHITE);

        JLabel title = new JLabel(afficherArchive ? "Archive des adherents" : "Gestion des adherents");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        entete.add(title, BorderLayout.WEST);
        return entete;
    }

    private JPanel creerBarreBas() {
        JPanel barre = new JPanel(new BorderLayout());
        barre.setBackground(Color.WHITE);

        JButton retour = new JButton("Retour");
        retour.addActionListener(e -> retourEspaceAdministrateur());
        barre.add(retour, BorderLayout.WEST);
        return barre;
    }

    private void addRow(JPanel panel, int row, String label, JTextField field) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 10, 12);
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(field, gbc);
    }

    public void actualiser() {
        remplirTable();
    }

    private void remplirTable() {
        model.setRowCount(0);
        String statutVisible = afficherArchive ? AppData.STATUT_ARCHIVE : AppData.STATUT_ACTIF;
        for (AppData.CompteAdherent compte : AppData.comptes()) {
            if (statutVisible.equals(compte.statut)) {
                model.addRow(new Object[]{
                    compte.id,
                    compte.prenom,
                    compte.nom,
                    compte.email,
                    compte.telephone,
                    compte.statut
                });
            }
        }
    }

    private void ajouterAdherent() {
        if (!validerChamps()) {
            return;
        }
        if (AppData.trouverParEmail(emailField.getText().trim()) != null) {
            JOptionPane.showMessageDialog(this, "Un compte existe deja avec cet email");
            return;
        }
        AppData.comptes().add(new AppData.CompteAdherent(
                Integer.parseInt(idField.getText().trim()),
                prenomField.getText().trim(),
                nomField.getText().trim(),
                emailField.getText().trim(),
                telephoneField.getText().trim(),
                "",
                "",
                "1234",
                AppData.STATUT_ACTIF));
        remplirTable();
        viderChamps();
    }

    private void modifierAdherent() {
        AppData.CompteAdherent compte = compteSelectionne();
        if (compte == null) {
            JOptionPane.showMessageDialog(this, "Selectionner un adherent");
            return;
        }
        if (!validerChamps()) {
            return;
        }
        compte.id = Integer.parseInt(idField.getText().trim());
        compte.prenom = prenomField.getText().trim();
        compte.nom = nomField.getText().trim();
        compte.email = emailField.getText().trim();
        compte.telephone = telephoneField.getText().trim();
        remplirTable();
    }

    private void archiverAdherent() {
        AppData.CompteAdherent compte = compteSelectionne();
        if (compte == null) {
            JOptionPane.showMessageDialog(this, "Selectionner un adherent");
            return;
        }
        compte.statut = AppData.STATUT_ARCHIVE;
        remplirTable();
        viderChamps();
    }

    private void supprimerAdherent() {
        AppData.CompteAdherent compte = compteSelectionne();
        if (compte == null) {
            JOptionPane.showMessageDialog(this, "Selectionner un adherent");
            return;
        }
        AppData.comptes().remove(compte);
        remplirTable();
        viderChamps();
    }

    private boolean validerChamps() {
        if (idField.getText().trim().isEmpty()
                || prenomField.getText().trim().isEmpty()
                || nomField.getText().trim().isEmpty()
                || emailField.getText().trim().isEmpty()
                || telephoneField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Remplir tous les champs");
            return false;
        }
        try {
            Integer.parseInt(idField.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID doit etre numerique");
            return false;
        }
        if (!emailField.getText().contains("@")) {
            JOptionPane.showMessageDialog(this, "Email invalide");
            return false;
        }
        return true;
    }

    private void chargerSelection() {
        int row = table.getSelectedRow();
        if (row < 0) {
            return;
        }
        idField.setText(String.valueOf(model.getValueAt(row, 0)));
        prenomField.setText(String.valueOf(model.getValueAt(row, 1)));
        nomField.setText(String.valueOf(model.getValueAt(row, 2)));
        emailField.setText(String.valueOf(model.getValueAt(row, 3)));
        telephoneField.setText(String.valueOf(model.getValueAt(row, 4)));
    }

    private AppData.CompteAdherent compteSelectionne() {
        int row = table.getSelectedRow();
        if (row < 0) {
            return null;
        }
        String email = String.valueOf(model.getValueAt(row, 3));
        return AppData.trouverParEmail(email);
    }

    private void viderChamps() {
        idField.setText("");
        prenomField.setText("");
        nomField.setText("");
        emailField.setText("");
        telephoneField.setText("");
        table.clearSelection();
    }

    private void retourEspaceAdministrateur() {
        if (retourAction != null) {
            retourAction.run();
        }
    }

}
