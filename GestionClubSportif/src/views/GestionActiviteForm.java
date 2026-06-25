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

public class GestionActiviteForm extends JPanel {

    private final JTextField nomField = new JTextField(18);
    private final JTextField coachField = new JTextField(18);
    private final JTextField tarifField = new JTextField(18);
    private final JTextField placesField = new JTextField(18);
    private final DefaultTableModel model = new DefaultTableModel(
            new Object[]{"Nom", "Coach", "Tarif", "Places"}, 0);
    private final JTable table = new JTable(model);
    private final Runnable retourAction;

    public GestionActiviteForm() {
        this(null);
    }

    public GestionActiviteForm(Runnable retourAction) {
        this.retourAction = retourAction;
        initComponents();
        remplirExemples();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(780, 460));

        JPanel root = new JPanel(new BorderLayout(18, 18));
        root.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
        root.setBackground(Color.WHITE);
        add(root, BorderLayout.CENTER);

        root.add(creerEntete("Gestion des activites"), BorderLayout.NORTH);
        root.add(creerBarreBas(), BorderLayout.SOUTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(new Color(245, 247, 250));
        form.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        root.add(form, BorderLayout.WEST);

        addRow(form, 0, "Nom", nomField);
        addRow(form, 1, "Coach", coachField);
        addRow(form, 2, "Tarif", tarifField);
        addRow(form, 3, "Places", placesField);

        JButton ajouter = new JButton("Ajouter");
        ajouter.addActionListener(e -> ajouterActivite());
        JButton modifier = new JButton("Modifier");
        modifier.addActionListener(e -> modifierActivite());
        JButton supprimer = new JButton("Supprimer");
        supprimer.addActionListener(e -> supprimerLigne());
        JButton vider = new JButton("Vider");
        vider.addActionListener(e -> viderChamps());

        JPanel buttons = new JPanel(new GridBagLayout());
        buttons.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(6, 0, 0, 0);
        gbc.gridx = 0;
        gbc.gridy = 0;
        buttons.add(ajouter, gbc);
        gbc.gridy++;
        buttons.add(modifier, gbc);
        gbc.gridy++;
        buttons.add(supprimer, gbc);
        gbc.gridy++;
        buttons.add(vider, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        buttons.add(new JLabel(), gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        form.add(buttons, gbc);

        table.setRowHeight(26);
        table.getSelectionModel().addListSelectionListener(e -> chargerSelection());
        root.add(new JScrollPane(table), BorderLayout.CENTER);
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
        model.addRow(new Object[]{"Musculation", "Youssef", "250", "20"});
        model.addRow(new Object[]{"Yoga", "Salma", "180", "15"});
        model.addRow(new Object[]{"Boxe", "Amine", "220", "12"});
    }

    private void ajouterActivite() {
        if (!validerChamps()) {
            return;
        }
        model.addRow(new Object[]{
            nomField.getText().trim(),
            coachField.getText().trim(),
            tarifField.getText().trim(),
            placesField.getText().trim()
        });
        viderChamps();
    }

    private void modifierActivite() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Selectionner une activite");
            return;
        }
        if (!validerChamps()) {
            return;
        }
        model.setValueAt(nomField.getText().trim(), row, 0);
        model.setValueAt(coachField.getText().trim(), row, 1);
        model.setValueAt(tarifField.getText().trim(), row, 2);
        model.setValueAt(placesField.getText().trim(), row, 3);
    }

    private void supprimerLigne() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Selectionner une activite");
            return;
        }
        model.removeRow(row);
        viderChamps();
    }

    private boolean validerChamps() {
        if (nomField.getText().trim().isEmpty()
                || coachField.getText().trim().isEmpty()
                || tarifField.getText().trim().isEmpty()
                || placesField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Remplir tous les champs");
            return false;
        }
        try {
            Double.parseDouble(tarifField.getText().trim());
            Integer.parseInt(placesField.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Tarif et places doivent etre numeriques");
            return false;
        }
        return true;
    }

    private void chargerSelection() {
        int row = table.getSelectedRow();
        if (row < 0) {
            return;
        }
        nomField.setText(String.valueOf(model.getValueAt(row, 0)));
        coachField.setText(String.valueOf(model.getValueAt(row, 1)));
        tarifField.setText(String.valueOf(model.getValueAt(row, 2)));
        placesField.setText(String.valueOf(model.getValueAt(row, 3)));
    }

    private void viderChamps() {
        nomField.setText("");
        coachField.setText("");
        tarifField.setText("");
        placesField.setText("");
        table.clearSelection();
    }

}
