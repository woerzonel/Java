import sun.java2d.pipe.DrawImage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.function.BiFunction;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Name: Gabe Woerishofer
 * Date: 2/15/2016
 * Lab: 9&10
 * Class: SE 1021 032
 */

public class ImageViewer extends JFrame{
    private ImageIcon displayImage;
    private Picture picture;
    private long serialVersionUID = 1L;
    private JLabel img = new JLabel();
    private static Logger logger= Logger.getLogger(ImageViewer.class.getName());

    /**]
     *  Creates all buttons that are used int the file and outputs them to be used in the program
     * @param lable Name of the button
     * @param listener The action listener of the button
     * @return The created button for use
     */
    private JButton createButton(String lable, ActionListener listener){
        JButton createdBTN = new JButton(lable);
        createdBTN.addActionListener(listener);

        return createdBTN;
    }

    /**
     * Gets what type of file it is to be used on the program
     * @param file The file that was selected in the load file method
     * @return The name of the type of file
     */
    private String getFileExtension(File file){
        String[] fileName = file.getName().split("\\.");
        return fileName[1];
    }

    /**
     * Creates the GUI for the lab and runs/ loads all of the compotents
     */
    public ImageViewer(){

        setTitle("Image Manipulator");
        setSize(800, 600);
        setResizable(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel imgPanel = new JPanel();
        JPanel btnPanel = new JPanel(new GridLayout(9,1));

        try {
            displayImage = new ImageIcon(picture.getImage());
        }catch(NullPointerException e){
            logger.warning(e.getMessage());
            logger.log(Level.CONFIG, "IOExecption", e);
        }

        this.img.setIcon(displayImage);

        imgPanel.setBackground(Color.lightGray);
        imgPanel.add(img);

        btnPanel.add(createButton("Load", ae -> {
            loadImage();
        }));

        btnPanel.add(createButton("Save", ae -> {
            saveImage();
        }));

        btnPanel.add(createButton("Reload", ae -> {
           try {
               picture.load(picture.lastFile);
               updateImage();
           }catch (IOException e){
               logger.warning(e.getMessage());
               logger.log(Level.CONFIG, "IOExecption", e);
           }
            updateImage();
        }));

        btnPanel.add(createButton("Grayscale", ae ->
        {
          PixelManipulator greyScale = new GrayscaleManipulator();
            transformImage(greyScale);
        }));

        btnPanel.add(createButton("Red", ae -> {
            PixelManipulator redScale = new ReadOnlyManipulator();
            transformImage(redScale);
        }));

        btnPanel.add(createButton("Red-Gray", ae -> {
            PixelManipulator redGrayScale = new RedGrayManiplualtor();
            transformImage(redGrayScale);
        }));

        btnPanel.add(createButton("Negative", ae -> {
            PixelManipulator negativeScale = new NegativeManipualtor();
            transformImage(negativeScale);
        }));

        add(imgPanel, BorderLayout.CENTER);
        add(btnPanel,BorderLayout.EAST);
    }

    /**
     * Loads the images that are selected for the program to change and save.
     */
    private void loadImage(){
        final String PNG = "png";
        final String PPM = "ppm";
        final String JPEG = "jpg";

        JFileChooser chooser = new JFileChooser(".");
        int fileOfChoice = chooser.showOpenDialog(null);

        if(fileOfChoice == JFileChooser.APPROVE_OPTION) {
            File loadedfile = chooser.getSelectedFile();

            switch(getFileExtension(loadedfile)){
                case PNG:
                    picture= new PNGPicture();
                    try{
                        picture.load(loadedfile);
                    }catch (IOException e){
                        logger.warning(e.getMessage());
                        logger.log(Level.CONFIG, "IOExecption", e);
                    }
                    updateImage();
                    break;
                case PPM:
                    picture = new PPMPicture();
                    try{
                        picture.load(loadedfile);
                    }catch (IOException e){
                        logger.warning(e.getMessage());
                        logger.log(Level.CONFIG, "IOExecption", e);
                    }
                    updateImage();
                    break;
                case JPEG:
                    picture = new JPEGPicture();
                    try{
                        picture.load(loadedfile);
                    }catch (IOException e){
                        logger.warning(e.getMessage());
                        logger.log(Level.CONFIG, "IOExecption", e);
                    }
                    updateImage();


                    break;
                default:
                    picture = new EmptyPicture();
                    updateImage();
                    break;
            }

        }

    }


    /**
     *  Creates an instance of the ImageViewer class to be used
     * @param args
     */
    public static void main(String[] args) {
        ImageViewer lab = new ImageViewer();
        lab.setVisible(true);
    }

    /**
     * Saves the image for viewing later
     */
    private void saveImage(){
        final String PNG = "png";
        final String PPM = "ppm";
        final String JPEG = "jpg";

        JFileChooser chooser = new JFileChooser(".");
        int fileOfChoice = chooser.showOpenDialog(null);

        if(fileOfChoice == JFileChooser.APPROVE_OPTION) {
            File savedfile = chooser.getSelectedFile();

            switch(getFileExtension(savedfile)){
                case PNG:
                    picture= new PNGPicture();
                    try{
                        picture.store(savedfile);
                    }catch (IOException e){
                        logger.warning(e.getMessage());
                        logger.log(Level.CONFIG, "IOExecption", e);
                    }

                    break;
                case PPM:
                    picture = new PPMPicture();
                    try{
                        picture.store(savedfile);
                    }catch (IOException e){
                        logger.warning(e.getMessage());
                        logger.log(Level.CONFIG, "IOExecption", e);
                    }

                    break;
                case JPEG:
                    picture = new JPEGPicture();
                    try{
                        picture.store(savedfile);
                    }catch (IOException e){
                        logger.warning(e.getMessage());
                        logger.log(Level.CONFIG, "IOExecption", e);
                    }



                    break;
                default:
                    picture = new EmptyPicture();

                    break;
            }

        }
    }


    /**
     *  Transforms the image as desired by the user
     * @param manipulator
     */
    private void transformImage(PixelManipulator manipulator){
            ImageTransform transformedImage = new ImageTransform(picture.buffer,manipulator);
            transformedImage.transformImage();

        try {
            displayImage = new ImageIcon(transformedImage.buffer);
        }catch(NullPointerException e){
            logger.warning(e.getMessage());
            logger.log(Level.CONFIG, "NullPointer", e);
        }

        this.img.setIcon(displayImage);
        this.repaint();
    }

    /**
     * Updates the image to be displayed
     */
    public void updateImage(){
        try {
            displayImage = new ImageIcon(picture.getImage());
        }catch(NullPointerException e){
            logger.warning(e.getMessage());
            logger.log(Level.CONFIG, "NullPointer", e);
        }

        this.img.setIcon(displayImage);
        this.repaint();



    }
}
