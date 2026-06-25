package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class UtilisateursEnAttentePanel extends JPanel {

    private final DefaultTableModel model = new DefaultTableModel(
            new Object[]{"ID", "Prenom", "Nom", "Email", "Telephone", "Adresse"}, 0);
    private final JTable table = new JTable(model);
    private final Runnable retourAction;

    public UtilisateursEnAttentePanel(Runnable retourAction) {
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

        JLabel titre = new JLabel("Utilisateurs en attente");
        titre.setFont(new Font("Segoe UI", Font.BOLD, 24));
        root.add(titre, BorderLayout.NORTH);

        table.setRowHeight(26);
        root.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bas = new JPanel(new BorderLayout());
        bas.setBackground(Color.WHITE);

        JButton retour = new JButton("Retour");
        retour.addActionListener(e -> {
            if (retourAction != null) {
                retourAction.run();
            }
        });
        bas.add(retour, BorderLayout.WEST);

        JPanel actions = new JPanel();
        actions.setBackground(Color.WHITE);
        JButton accepter = new JButton("Accepter");
        accepter.addActionListener(e -> changerStatutSelection(AppData.STATUT_ACTIF));
        JButton refuser = new JButton("Refuser");
        refuser.addActionListener(e -> changerStatutSelection(AppData.STATUT_REFUSE));
        JButton archiver = new JButton("Archiver");
        archiver.addActionListener(e -> changerStatutSelection(AppData.STATUT_ARCHIVE));
        actions.add(accepter);
        actions.add(refuser);
        actions.add(archiver);
        bas.add(actions, BorderLayout.EAST);

        root.add(bas, BorderLayout.SOUTH);
    }

    public void remplirTable() {
        model.setRowCount(0);
        for (AppData.CompteAdherent compte : AppData.comptes()) {
            if (AppData.STATUT_ATTENTE.equals(compte.statut)) {
                model.addRow(new Object[]{
                    compte.id,
                    compte.prenom,
                    compte.nom,
                    compte.email,
                    compte.telephone,
                    compte.adresse
                });
            }
        }
    }

    private void changerStatutSelection(String statut) {
        AppData.CompteAdherent compte = compteSelectionne();
        if (compte == null) {
            JOptionPane.showMessageDialog(this, "Selectionner un utilisateur en attente");
            return;
        }
        compte.statut = statut;
        remplirTable();
    }

    private AppData.CompteAdherent compteSelectionne() {
        int row = table.getSelectedRow();
        if (row < 0) {
            return null;
        }
        String email = String.valueOf(model.getValueAt(row, 3));
        return AppData.trouverParEmail(email);
    }
}
