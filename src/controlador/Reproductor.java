package controlador;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import modelo.Cancion;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Reproductor {
    private Player player;
    private Thread hiloReproduccion;
    private boolean reproduciendo = false;

    public void reproducir (Cancion cancion){
        if (cancion == null) return;

        if(reproduciendo){
            detener();
        }

        hiloReproduccion = new Thread(()->{
            try {
                FileInputStream fis = new FileInputStream(cancion.getRutaArchivo());
                player = new Player(fis);
                reproduciendo = true;
                System.out.Println("Reproduciendo: " +cancion.getTitulo());
                player.play();
                reproduciendo = false;
            } catch (FileNotFoundException e){
                System.out.println("Archivo no encontrado"+cancion.getRutaArchivo());
                e.printStackTrace();
            } catch (JavaLayerException e) {
                System.out.println("❌ Error al reproducir: " + e.getMessage());
                e.printStackTrace();
            }
        })

        hiloReproduccion.start();
    }
    public void pausar(){
        if(player != null && reproduciendo){
            player.close();
            reproduciendo = false;
            System.out.println("Pausado");
        }
    }
    public void detener(){
        if (player != null){
            player.close();
            reproduciendo = false;
            System.out.println("Detenido");
        }
    }
    public boolean isReproduciendo() {
        return reproduciendo;
    }
}