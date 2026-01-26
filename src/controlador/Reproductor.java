package principal;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ReproductorMP3 {
    public static void main(String[] args) {
        try {
            // Ruta al archivo MP3
            FileInputStream fileInputStream = new FileInputStream("musica.mp3");
            Player player = new Player(fileInputStream);
            System.out.println("Reproduciendo...");
            
            // Iniciar la reproducción
            player.play();
            
        } catch (JavaLayerException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}