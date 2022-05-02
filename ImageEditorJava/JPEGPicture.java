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
public class JPEGPicture extends Picture {

    public JPEGPicture(){

    }

    /**
     * Saves the buffered Image
     * @param buffer Buffered Image
     */
    public JPEGPicture(BufferedImage buffer){
            super.buffer = buffer;
    }

    /**
     * Loads the file given to the method it is a JPEG
     * @param file The file pased to the method
     * @throws IOException
     */
    public void load(File file)throws IOException{
            buffer = ImageIO.read(file);
            lastFile = file;
}

    /**
     * Saves the file given to it
     * @param file Saves the File
     * @throws IOException
     */
    public void store(File file)throws IOException{
        ImageIO.write(buffer , "png", file);
    }

}
