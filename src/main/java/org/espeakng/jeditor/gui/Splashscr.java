package org.espeakng.jeditor.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.SplashScreen;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;

import org.apache.log4j.Logger;


public class Splashscr{
	static SplashScreen mySplash = SplashScreen.getSplashScreen();
	static Graphics2D splashGraphics = mySplash.createGraphics();
	static Dimension ssDim = mySplash.getSize();
	static int height = ssDim.height;
	static int width = ssDim.width;
    static Double splashTextArea = new Rectangle2D.Double(15., height*0.88, width * .45, 32.);
    static Double  splashProgressArea = new Rectangle2D.Double(width * .55, height*.92, width*.4, 12 );
    private static final Logger LOGGER = Logger.getRootLogger();

       
static void splashText(String str) 
{
    if (mySplash != null && mySplash.isVisible())
    {   // important to check here so no other methods need to know if there
        // really is a Splash being displayed

        // erase the last status text
        splashGraphics.setPaint(Color.LIGHT_GRAY);
        splashGraphics.fill(splashTextArea);

        // draw the text
        splashGraphics.setPaint(Color.BLACK);
        splashGraphics.drawString(str, (int)(splashTextArea.getX() + 10),(int)(splashTextArea.getY() + 15));

        // make sure it's displayed
        mySplash.update();
    }
    
}
 static void splashProgress(int pct)
{
    if (mySplash != null && mySplash.isVisible())
    {

        // Note: 3 colors are used here to demonstrate steps
        // erase the old one
        splashGraphics.setPaint(Color.LIGHT_GRAY);
        splashGraphics.fill(splashProgressArea);

        // draw an outline
        splashGraphics.setPaint(Color.LIGHT_GRAY);
        splashGraphics.draw(splashProgressArea);
        // Calculate the width corresponding to the correct percentage
        int x = (int) splashProgressArea.getMinX();
        int y = (int) splashProgressArea.getMinY();
        int wid = (int) splashProgressArea.getWidth();
        int hgt = (int) splashProgressArea.getHeight();

        int doneWidth = Math.round(pct*wid/100.f);
        doneWidth = Math.max(0, Math.min(doneWidth, wid-1));  // limit 0-width

        // fill the done part one pixel smaller than the outline
        splashGraphics.setPaint(Color.green);
        splashGraphics.fillRect(x, y+1, doneWidth, hgt-1);

        // make sure it's displayed
        mySplash.update();
    }
}}