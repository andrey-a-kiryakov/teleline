package org.teleline.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class GraficsPanel extends JPanel {

    public GraficsPanel() {
        setOpaque(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        //super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        //g2d.setClip(0,0, getWidth(), getHeight() * 2);
        //g2d.setClip(getWidth() / 4, getHeight() / 4, getWidth() / 2, getHeight() / 2);
        
        Image im = null;
       
        try
          {
          im = ImageIO.read(new File("image.jpg"));
          }
          catch (IOException exception)
          {
           
          }   
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
       
        g2d.drawImage(im, 0, 0, null);
        this.setSize(2000,2000);
        g2d.setColor(Color.blue);
        g2d.fillOval(90, 90, 150,150);
        
     /*   g2d.setColor(Color.red);
        g2d.fillOval(20, 20, getWidth() - 40, getHeight() - 40);
        g2d.setColor(Color.yellow);
        g2d.fillOval(30, 30, getWidth() - 60, getHeight() - 60);
        g2d.setColor(Color.black);
        g2d.fillOval(getWidth()/4 - getWidth()/16, getHeight()/2-getHeight()/8, getWidth()/8, getHeight()/8);
        g2d.fillOval(getWidth()*3/4 - getWidth()/16, getHeight()/2-getHeight()/8, getWidth()/8, getHeight()/8);
        g2d.setStroke(new BasicStroke(10, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.drawArc(getWidth()/4, getHeight()/4, getWidth()/2, getHeight()/2, 225, 90);
    */
    }
}