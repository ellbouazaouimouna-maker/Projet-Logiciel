package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Paiement extends JPanel {

    private final JTextField adherentField = new JTextField(18);
    private final JTextField montantField = new JTextField(18);
    private final JTextField dateField = new JTextField(18);
    private final JComboBox<String> methodeBox = new JComboBox<>(new String[]{"Especes", "Carte", "Virement"});
    private final JComboBox<String> statutBox = new JComboBox<>(new String[]{"Paye", "En attente", "Refuse"});
    private final DefaultTableModel model = new DefaultTableModel(
            new Object[]{"Adherent", "Montant", "Date", "Methode", "Statut"}, 0);
    private final JTable table = new JTable(model);
    private final Runnable retourAction;

    public Paiement() {
        this(null);
    }

    public Paiement(Runnable retourAction) {
        this.retourAction = retourAction;
        initComponents();
        remplirExemples();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(820, 480));

        JPanel root = new JPanel(new BorderLayout(18, 18));
        root.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
        root.setBackground(Color.WHITE);
        add(root, BorderLayout.CENTER);

        root.add(creerEntete("Gestion des paiements"), BorderLayout.NORTH);
        root.add(creerBarreBas(), BorderLayout.SOUTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(new Color(245, 247, 250));
        form.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        root.add(form, BorderLayout.WEST);

        addRow(form, 0, "Adherent", adherentField);
        addRow(form, 1, "Montant", montantField);
        dateField.setText(LocalDate.now().toString());
        addRow(form, 2, "Date (AAAA-MM-JJ)", dateField);
        addRow(form, 3, "Methode", methodeBox);
        addRow(form, 4, "Statut", statutBox);

        JButton ajouter = new JButton("Enregistrer");
        ajouter.addActionListener(e -> ajouterPaiement());
        JButton modifier = new JButton("Modifier");
        modifier.addActionListener(e -> modifierPaiement());
        JButton valider = new JButton("Marquer paye");
        valider.addActionListener(e -> marquerPaye());
        JButton supprimer = new JButton("Supprimer");
        supprimer.addActionListener(e -> supprimerPaiement());

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
        buttons.add(valider, gbc);
        gbc.gridy = 3;
        buttons.add(supprimer, gbc);

        gbc.gridy = 5;
        gbc.gridwidth = 2;
        form.add(buttons, gbc);

        table.setRowHeight(26);
        table.getSelectionModel().addListSelectionListener(e -> chargerSelection());
        root.add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void addRow(JPanel panel, int row, String label, java.awt.Component field) {
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

    private JPanel creerEntete(String titre) {
        JPanel entete = new JPanel(new BorderLayout());
        entete.setBackground(Color.WHITE);

        JLabel title = new JLabel(titre);
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

    private void retourEspaceAdministrateur() {
        if (retourAction != null) {
            retourAction.run();
        }
    }

    private void remplirExemples() {
        model.addRow(new Object[]{"Mouna El Bouazaoui", "250", LocalDate.now().toString(), "Carte", "Paye"});
        model.addRow(new Object[]{"Karim Alaoui", "180", LocalDate.now().toString(), "Especes", "En attente"});
    }

    private void ajouterPaiement() {
        if (!validerChamps()) {
            return;
        }
        model.addRow(new Object[]{
            adherentField.getText().trim(),
            montantField.getText().trim(),
            dateField.getText().trim(),
            methodeBox.getSelectedItem(),
            statutBox.getSelectedItem()
        });
        viderChamps();
    }

    private void modifierPaiement() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Selectionner un paiement");
            return;
        }
        if (!validerChamps()) {
            return;
        }
        model.setValueAt(adherentField.getText().trim(), row, 0);
        model.setValueAt(montantField.getText().trim(), row, 1);
        model.setValueAt(dateField.getText().trim(), row, 2);
        model.setValueAt(methodeBox.getSelectedItem(), row, 3);
        model.setValueAt(statutBox.getSelectedItem(), row, 4);
    }

    private void marquerPaye() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Selectionner un paiement");
            return;
        }
        model.setValueAt("Paye", row, 4);
    }

    private void supprimerPaiement() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Selectionner un paiement");
            return;
        }
        model.removeRow(row);
        viderChamps();
    }

    private boolean validerChamps() {
        if (adherentField.getText().trim().isEmpty()
                || montantField.getText().trim().isEmpty()
                || dateField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Remplir tous les champs");
            return false;
        }
        try {
            Double.parseDouble(montantField.getText().trim());
            LocalDate.parse(dateField.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Montant invalide");
            return false;
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Date invalide. Exemple : 2026-06-24");
            return false;
        }
        return true;
    }

    private void chargerSelection() {
        int row = table.getSelectedRow();
        if (row < 0) {
            return;
        }
        adherentField.setText(String.valueOf(model.getValueAt(row, 0)));
        montantField.setText(String.valueOf(model.getValueAt(row, 1)));
        dateField.setText(String.valueOf(model.getValueAt(row, 2)));
        methodeBox.setSelectedItem(model.getValueAt(row, 3));
        statutBox.setSelectedItem(model.getValueAt(row, 4));
    }

    private void viderChamps() {
        adherentField.setText("");
        montantField.setText("");
        dateField.setText(LocalDate.now().toString());
        methodeBox.setSelectedIndex(0);
        statutBox.setSelectedIndex(0);
        table.clearSelection();
    }

}
