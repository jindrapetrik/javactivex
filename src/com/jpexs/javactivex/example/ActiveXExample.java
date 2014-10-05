package com.jpexs.javactivex.example;

import com.jpexs.javactivex.example.controls.ShockwaveFlash;
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
public class ActiveXExample extends Frame {

    private ShockwaveFlash swfPlayer;
    
    //TODO: WMP - {6BF52A52-394A-11d3-B153-00C04F79FAA6}

    public ActiveXExample() {
        setSize(800, 600);
        setTitle("Sample ActiveX Component in Java");
        setLayout(new BorderLayout());
        swfPlayer = new ShockwaveFlash();
        add(swfPlayer, BorderLayout.CENTER);
        System.out.println(""+swfPlayer.getJavaDefinition());
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
                FileDialog fd = new FileDialog(ActiveXExample.this, "Open SWF");
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
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {                        
        new ActiveXExample().setVisible(true);
    }

}
