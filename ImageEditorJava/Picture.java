import com.sun.java.util.jar.pack.*;
import java.io.File;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by woerishofergw on 2/9/2016.
 * An abstract class that other picture images inheret from. Defines the get height, width, and image methods. Classes need to define the Load and Save method.
 */
public abstract class  Picture {

    protected BufferedImage buffer;
    protected File lastFile;

    /**
     * Gets the hieght of the picture
     * @return The height of the picture
     */
    public int getHieght(){

        return buffer.getHeight();
    }

    /**
     * Saves the buffered Image is returned
     * @return The buffered Image
     */
    public BufferedImage getImage(){

        return buffer;
    }

    /**
     * Gets the width of the Image
     * @return The width of the Image
     */
    public int getWidth(){

        return buffer.getWidth();
    }

    public abstract void load(File file) throws IOException;

    /**
     * Saves the buffered Image
     * @param buffer buffered Image
     */
    public Picture(BufferedImage buffer){
        this.buffer = buffer;
    }

    public Picture(){


    }

    public abstract void store(File file)throws IOException;


}
