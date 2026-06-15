import javax.swing.SwingUtilities;

public class MainSwing {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaAgenda(new Agenda()));
    }
}