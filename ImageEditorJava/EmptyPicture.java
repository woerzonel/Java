import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Name: Gabe Woerishofer
 * Date: 2/15/2016
 * Lab: 9&10
 * Class: SE 1021 032
 */
public class EmptyPicture extends Picture{
    public final int DEFAULT_HEIGHT = 600;
    public final int DEFAULT_WIDTH = 800;

    /**
     * Creates an empty image
     */
    public EmptyPicture(){
        super.buffer = new BufferedImage(DEFAULT_WIDTH,DEFAULT_HEIGHT,BufferedImage.TYPE_INT_RGB);
    }

    /**
     * Creates an empty image used as a defualt until a real image is uploaded
     * @param width Width of the image
     * @param hieght hieght of the image
     */
    public EmptyPicture(int width, int hieght){
        super.buffer = new BufferedImage(width, hieght, BufferedImage.TYPE_INT_RGB);
    }

    public void load(File file){

    }

    public void store(File file){

    }

}
