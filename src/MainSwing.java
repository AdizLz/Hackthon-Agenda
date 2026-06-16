import javax.swing.SwingUtilities;

/**
 * Punto de entrada de la versión gráfica (Swing) de la aplicación.
 * Crea la ventana principal pasándole una nueva Agenda.
 */
public class MainSwing {
    public static void main(String[] args) {
        // Las interfaces Swing deben crearse y actualizarse en el
        // Event Dispatch Thread (EDT), no en el hilo principal.
        // invokeLater encola la creación de la ventana en ese hilo.
        SwingUtilities.invokeLater(() -> new VentanaAgenda(new Agenda()));
    }
}