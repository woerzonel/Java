Image Editor Project:

A java class that takes in an image and allows the user to change the colors of the image. There are 4 options grey scale, red, red and grey, and negative. This is done by minipulating the pixels of an image. The image is shown to the user after being transformed. The user can then choose to save the image.

This was done using JFrame and JFileChooser in Java SE7 created in 2016. Uses both abstract and interface classes. This was among the first java project I created. The coding has room for improvement and the GUI structure used is very outdated. 

Files Created by me:
* GrayscaleManipulator.java (Has a transform method that ulters and returns the pixel colors)
* EmptyPicture.java (Handles image saves and loads for this image type)
* ImageTransform.java (Takes in a image minipulation class and a image buffer and appilies the transform to it)
* ImageViewer.java (The main class handles GUI and calls the other classes)
* JPEGPicture.java (Handles image saves and loads for this image type)
* NegativeManulator.java (Has a transform method that ulters and returns the pixel colors)
* Picture.java (Abstract class that defines the methods for all picture classes)
* PixelManipulator.java (Interface class that all minipulator classes implement)
* PNGPicture.java (Handles image saves and loads for this image type)
* ReadonlyManipulator.java (Has a transform method that ulters and returns the pixel colors)
* RedGreyManipulator.java (Has a transform method that ulters and returns the pixel colors)

Given Files:
*BMPPicture.java (Handling bit map file saving and loading.)
*Pixel.java (A pixel class to save all the information in a pixel and allow it to be edited easily)
