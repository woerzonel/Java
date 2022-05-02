import sun.awt.image.ImageCache;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by woerishofergw on 2/9/2016.
 */
public class PPMPicture extends Picture {

    public PPMPicture(){

    }

    /**
     * Saves the buffered Image
     * @param buffer The bufered Image
     */
    public PPMPicture(BufferedImage buffer){
        super.buffer = buffer;
    }

    /**
     * Turns pixels into string
     * @param pixel The pixel
     * @return The string form
     */
    private String pixelToString(Pixel pixel){
        for(int fileHeight = 0; fileHeight < buffer.getHeight(); fileHeight++){

            for(int fileWidth = 0; fileWidth < buffer.getWidth(); fileWidth++){
                pixel = new Pixel(buffer.getRGB(fileWidth,fileHeight));

            }
        }

        return pixel.toString();
    }

    /**
     * Loads the file that is given
     * @param file The PPM file that is given
     * @throws IOException
     */
    public void load(File file)throws IOException{

            buffer = ImageIO.read(file);
        lastFile = file;
    }

    /**
     * Saves the Image image
     * @param file The PPM Image
     * @throws IOException
     */
    public void store(File file)throws IOException{
        ImageIO.write(buffer, "ppm", file);
        PrintWriter ppmWriter = new PrintWriter(file);
        ppmWriter.println("P3");
        ppmWriter.println("# " + file.getName());
        for(int fileHeight = 0; fileHeight < buffer.getHeight(); fileHeight++){

            for(int fileWidth = 0; fileWidth < buffer.getWidth(); fileWidth++){
                ppmWriter.print(buffer.getRGB(fileWidth, fileHeight));
            }

            ppmWriter.print("\n");
        }
        ppmWriter.close();
        }


}
