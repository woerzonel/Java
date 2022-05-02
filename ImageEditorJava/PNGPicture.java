import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Name: Gabe Woerishofer
 * Date: 2/15/2016
 * Lab: 9&10
 * Class: SE 1021 032
 */
public class PNGPicture extends Picture {
    public PNGPicture(){

    }

    /**
     * Saves the buffered Image
     * @param buffer The buffered Image
     */
    public PNGPicture(BufferedImage buffer){
        super.buffer = buffer;
    }

    /**
     * Loads the file given to it
     * @param file The file passed in as a PNG
     * @throws IOException
     */
    public void load(File file)throws IOException{
            buffer = ImageIO.read(file);
           lastFile = file;

    }

    /**
     * Saves a chosen file as a PNG
     * @param file The chosen file
     * @throws IOException
     */
    public void store(File file)throws IOException{
        ImageIO.write(buffer , "png", file);

    }

}
