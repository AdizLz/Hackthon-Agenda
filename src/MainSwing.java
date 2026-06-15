public class MainSwing {

    public static void main(String[] args) {
        // [CRÍTICO - Hilos de ejecución / Concurrencia]: Instanciar componentes de Swing directamente en el hilo principal (main thread) es una violación a las reglas de diseño de Java.
        // Toda creación, actualización o manipulación de una GUI en Swing debe ejecutarse obligatoriamente dentro del Event Dispatch Thread (EDT).
        // Al instanciarlo directamente con 'new VentanaAgenda();', tu aplicación se vuelve vulnerable a condiciones de carrera (race conditions), bloqueos silenciosos o comportamientos visuales erráticos difíciles de depurar.
        // La forma correcta exige usar 'SwingUtilities.invokeLater(() -> new VentanaAgenda());'.
        new VentanaAgenda();
    }

}