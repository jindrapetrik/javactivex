package com.jpexs.javactivex.example;

import com.jpexs.javactivex.ActiveX;
import com.jpexs.javactivex.ActiveXEvent;
import com.jpexs.javactivex.ActiveXEventListener;
import com.jpexs.javactivex.example.controls.flash.ShockwaveFlash;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Example of using ActiveX components - Flash player
 *
 * @author JPEXS
 */
public class FlashExample extends Frame {

    private ShockwaveFlash swfPlayer;
    private final Panel axPanel=new Panel();
    

    public FlashExample() {
        setSize(800, 600);
        setTitle("Sample ActiveX Component in Java - Flash");
        setLayout(new BorderLayout());
        swfPlayer = ActiveX.createObject(ShockwaveFlash.class, axPanel);
        add(axPanel, BorderLayout.CENTER);
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }

        });        

        Panel buttonsPanel = new Panel(new FlowLayout());

        Button playButton = new Button("Play SWF...");
        Button zoomInButton = new Button("Zoom in");
        Button zoomOutButton = new Button("Zoom out");
        buttonsPanel.add(playButton);
        buttonsPanel.add(zoomInButton);
        buttonsPanel.add(zoomOutButton);
        buttonsPanel.setBackground(Color.YELLOW);
        add(buttonsPanel, BorderLayout.NORTH);
        playButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                FileDialog fd = new FileDialog(FlashExample.this, "Open SWF");
                fd.setMultipleMode(false);
                fd.setVisible(true);
                String file = fd.getDirectory() + "/" + fd.getFile();
                if (file != null) {
                    swfPlayer.setMovie(file);
                }
            }
        });
        zoomInButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                swfPlayer.Zoom((int) Math.round(100 / 1.1));
            }
        });

        zoomOutButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                swfPlayer.Zoom((int) Math.round(100 / (1 / 1.1)));
            }
        });          
        swfPlayer.addOnReadyStateChangeListener(new ActiveXEventListener() {

            @Override
            public void onEvent(ActiveXEvent ev) {
                System.out.println("Readystate changed:"+swfPlayer.getReadyState());
            }
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {                        
       new FlashExample().setVisible(true);              
    }

}
