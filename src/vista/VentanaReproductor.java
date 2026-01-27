package vista;

import controlador.Reproductor;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import javax.swing.*;
import modelo.Cancion;

public class VentanaReproductor extends JFrame {
    
    private JButton btnPlayPausa, btnSiguiente, btnAnterior, btnCargarCarpeta;
    private JList<String> listaCanciones;
    private DefaultListModel<String> modeloLista;
    private JProgressBar barraProgreso;
    private JSlider sliderVolumen;
    private JLabel lblCancionActual, lblEstado;
    
    private ArrayList<Cancion> cancionesLista;
    private int indiceActual = 0;
    private boolean estaReproduciendo = false;
    
    // Paleta de colores NEGRO con MORADOS
    private final Color NEGRO = new Color(15, 15, 20);
    private final Color NEGRO_CLARO = new Color(25, 25, 35);
    private final Color NEGRO_MEDIO = new Color(35, 35, 45);
    private final Color MORADO_OSCURO = new Color(75, 40, 120);      // #4B2878
    private final Color MORADO_MEDIO = new Color(120, 70, 180);      // #7846B4
    private final Color MORADO_CLARO = new Color(160, 100, 230);     // #A064E6
    private final Color MORADO_NEON = new Color(200, 120, 255);      // #C878FF
    private final Color ROSA_MORADO = new Color(220, 100, 200);      // #DC64C8
    
    public VentanaReproductor() {
        configurarVentana();
        crearComponentes();
        organizarLayout();
        configurarEventos();
    }
    
    private void configurarVentana() {
        setTitle("Reproductor de Música");
        setSize(800, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(NEGRO);
    }
    
    private void crearComponentes() {
        // Botones con tonos morados
        btnPlayPausa = crearBoton("PLAY", MORADO_MEDIO);
        btnSiguiente = crearBoton(">>", MORADO_CLARO);
        btnAnterior = crearBoton("<<", MORADO_CLARO);
        btnCargarCarpeta = crearBoton("CARGAR", MORADO_OSCURO);
        
        btnPlayPausa.setEnabled(false);
        btnSiguiente.setEnabled(false);
        btnAnterior.setEnabled(false);
        
        // Lista de canciones elegante
        modeloLista = new DefaultListModel<>();
        listaCanciones = new JList<>(modeloLista);
        listaCanciones.setBackground(NEGRO_MEDIO);
        listaCanciones.setForeground(new Color(230, 230, 240));
        listaCanciones.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        listaCanciones.setSelectionBackground(MORADO_OSCURO);
        listaCanciones.setSelectionForeground(Color.WHITE);
        listaCanciones.setFixedCellHeight(35);
        listaCanciones.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        // Barra de progreso morada
        barraProgreso = new JProgressBar(0, 100);
        barraProgreso.setStringPainted(true);
        barraProgreso.setForeground(MORADO_NEON);
        barraProgreso.setBackground(NEGRO_CLARO);
        barraProgreso.setPreferredSize(new Dimension(0, 8));
        barraProgreso.setBorderPainted(false);
        barraProgreso.setString("0%");
        
        // Slider de volumen
        sliderVolumen = new JSlider(0, 100, 70);
        sliderVolumen.setBackground(NEGRO);
        sliderVolumen.setForeground(MORADO_CLARO);
        sliderVolumen.setPreferredSize(new Dimension(200, 30));
        
        // Etiquetas con estilo
        lblCancionActual = new JLabel("Sin canción", SwingConstants.CENTER);
        lblCancionActual.setForeground(MORADO_NEON);
        lblCancionActual.setFont(new Font("Segoe UI", Font.BOLD, 18));
        
        lblEstado = new JLabel("Selecciona una carpeta para comenzar", SwingConstants.CENTER);
        lblEstado.setForeground(new Color(180, 180, 190));
        lblEstado.setFont(new Font("Segoe UI", Font.PLAIN, 12));
    }
    
    private void organizarLayout() {
        setLayout(new BorderLayout(0, 0));
        
        // Panel superior - Header morado oscuro
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.Y_AXIS));
        panelSuperior.setBackground(NEGRO_CLARO);
        panelSuperior.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, MORADO_OSCURO),
            BorderFactory.createEmptyBorder(25, 20, 25, 20)
        ));
        
        lblCancionActual.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblEstado.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panelSuperior.add(lblCancionActual);
        panelSuperior.add(Box.createVerticalStrut(8));
        panelSuperior.add(lblEstado);
        
        // Panel central - Lista con borde morado
        JPanel panelCentral = new JPanel(new BorderLayout());
        panelCentral.setBackground(NEGRO);
        panelCentral.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));
        
        JLabel lblTitulo = new JLabel("  LISTA DE REPRODUCCIÓN");
        lblTitulo.setForeground(MORADO_CLARO);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
        
        JScrollPane scroll = new JScrollPane(listaCanciones);
        scroll.setBorder(BorderFactory.createLineBorder(MORADO_OSCURO, 2));
        scroll.getViewport().setBackground(NEGRO_MEDIO);
        
        panelCentral.add(lblTitulo, BorderLayout.NORTH);
        panelCentral.add(scroll, BorderLayout.CENTER);
        
        // Panel inferior - Controles
        JPanel panelInferior = new JPanel(new BorderLayout(0, 10));
        panelInferior.setBackground(NEGRO);
        panelInferior.setBorder(BorderFactory.createEmptyBorder(12, 20, 15, 20));
        
        // Barra de progreso
        JPanel panelProgreso = new JPanel(new BorderLayout());
        panelProgreso.setBackground(NEGRO);
        panelProgreso.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));
        panelProgreso.add(barraProgreso, BorderLayout.CENTER);
        
        // Botones de control
        JPanel panelControles = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        panelControles.setBackground(NEGRO);
        panelControles.add(btnCargarCarpeta);
        panelControles.add(btnAnterior);
        panelControles.add(btnPlayPausa);
        panelControles.add(btnSiguiente);
        
        // Control de volumen
        JPanel panelVolumen = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 8));
        panelVolumen.setBackground(NEGRO);
        JLabel lblVol = new JLabel("VOLUMEN");
        lblVol.setForeground(MORADO_CLARO);
        lblVol.setFont(new Font("Segoe UI", Font.BOLD, 10));
        panelVolumen.add(lblVol);
        panelVolumen.add(sliderVolumen);
        
        panelInferior.add(panelProgreso, BorderLayout.NORTH);
        panelInferior.add(panelControles, BorderLayout.CENTER);
        panelInferior.add(panelVolumen, BorderLayout.SOUTH);
        
        add(panelSuperior, BorderLayout.NORTH);
        add(panelCentral, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
    }
    
    private void configurarEventos() {
        btnCargarCarpeta.addActionListener(e -> cargarCarpetaMusica());
        
        btnPlayPausa.addActionListener(e -> {
            if (estaReproduciendo) {
                pausar();
            } else {
                reproducir();
            }
        });
        
        btnSiguiente.addActionListener(e -> siguiente());
        btnAnterior.addActionListener(e -> anterior());
        
        listaCanciones.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    indiceActual = listaCanciones.getSelectedIndex();
                    if (!estaReproduciendo) {
                        reproducir();
                    }
                }
            }
        });
    }
    
    private void cargarCarpetaMusica() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setDialogTitle("Selecciona carpeta con archivos MP3");
        
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File carpeta = chooser.getSelectedFile();
            File[] archivosMusicales = carpeta.listFiles((dir, name) -> 
                name.toLowerCase().endsWith(".mp3")
            );
            
            if (archivosMusicales != null && archivosMusicales.length > 0) {
                cancionesLista = new ArrayList<>();
                modeloLista.clear();
                
                for (int i = 0; i < archivosMusicales.length; i++) {
                    String nombreArchivo = archivosMusicales[i].getName();
                    String titulo = nombreArchivo.replace(".mp3", "");
                    String ruta = archivosMusicales[i].getAbsolutePath();
                    String artista = "Desconocido";
                    
                    Cancion cancion = new Cancion(titulo, artista, ruta);
                    cancionesLista.add(cancion);
                    
                    modeloLista.addElement((i + 1) + ".  " + titulo);
                }
                
                btnPlayPausa.setEnabled(true);
                btnSiguiente.setEnabled(true);
                btnAnterior.setEnabled(true);
                
                lblEstado.setText(cancionesLista.size() + " canciones cargadas");
                lblCancionActual.setText("Lista cargada");
            } else {
                JOptionPane.showMessageDialog(this, 
                    "No se encontraron archivos MP3 en esta carpeta",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private Reproductor reproductor = new Reproductor();

    private void reproducir() {
        if (cancionesLista == null || cancionesLista.isEmpty()) return;
        
        estaReproduciendo = true;
        btnPlayPausa.setText("PAUSA");
        btnPlayPausa.setBackground(ROSA_MORADO);
        
        Cancion cancionActual = cancionesLista.get(indiceActual);
        lblCancionActual.setText(cancionActual.getTitulo() + " - " + cancionActual.getArtista());
        lblEstado.setText("Reproduciendo...");
        listaCanciones.setSelectedIndex(indiceActual);

        reproductor.reproducir(cancionActual);
    }
    
    private void pausar() {
        estaReproduciendo = false;
        btnPlayPausa.setText("PLAY");
        btnPlayPausa.setBackground(MORADO_MEDIO);
        lblEstado.setText("Pausado");
        
        reproductor.pausar();
    }
    
    private void detener() {
        estaReproduciendo = false;
        btnPlayPausa.setText("PLAY");
        btnPlayPausa.setBackground(MORADO_MEDIO);
        lblCancionActual.setText("Sin canción");
        lblEstado.setText("Detenido");
        barraProgreso.setValue(0);
        barraProgreso.setString("0%");
        
        reproductor.detener();
    }
    
    private void siguiente() {
        if (cancionesLista != null && indiceActual < cancionesLista.size() - 1) {
            indiceActual++;
            if (estaReproduciendo) {
                reproducir();
            } else {
                listaCanciones.setSelectedIndex(indiceActual);
            }
        }
    }
    
    private void anterior() {
        if (cancionesLista != null && indiceActual > 0) {
            indiceActual--;
            if (estaReproduciendo) {
                reproducir();
            } else {
                listaCanciones.setSelectedIndex(indiceActual);
            }
        }
    }
    
    private JButton crearBoton(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        
        // Tamaño según el texto
        if (texto.equals("CARGAR")) {
            btn.setPreferredSize(new Dimension(110, 45));
        } else if (texto.equals("PLAY") || texto.equals("PAUSA")) {
            btn.setPreferredSize(new Dimension(95, 45));
        } else {
            btn.setPreferredSize(new Dimension(60, 45));
        }
        
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Efecto hover luminoso
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                if (btn.isEnabled()) {
                    btn.setBackground(color.brighter());
                }
            }
            public void mouseExited(MouseEvent e) {
                btn.setBackground(color);
            }
        });
        
        return btn;
    }
    
    // Métodos públicos para el controlador
    public Cancion getCancionActual() {
        if (cancionesLista != null && indiceActual < cancionesLista.size()) {
            return cancionesLista.get(indiceActual);
        }
        return null;
    }
    
    public ArrayList<Cancion> getCancionesLista() {
        return cancionesLista;
    }
    
    public void setProgreso(int porcentaje) {
        barraProgreso.setValue(porcentaje);
        barraProgreso.setString(porcentaje + "%");
    }
    
    public int getVolumen() {
        return sliderVolumen.getValue();
    }
    
    public boolean isReproduciendo() {
        return estaReproduciendo;
    }
}