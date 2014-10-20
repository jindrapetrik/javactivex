package com.jpexs.javactivex.example;

import com.jpexs.javactivex.ActiveX;
import com.jpexs.javactivex.ActiveXEvent;
import com.jpexs.javactivex.ActiveXEventListener;
import com.jpexs.javactivex.example.controls.mediaplayer.IWMPMedia;
import com.jpexs.javactivex.example.controls.mediaplayer.WindowsMediaPlayer;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Example of using ActiveX components - Flash player
 *
 * @author JPEXS
 */
public class MediaPlayerExample extends Frame {

    private WindowsMediaPlayer wmPlayer;
    private final Panel axPanel = new Panel();
    
    public MediaPlayerExample() {
        setSize(800, 600);
        setTitle("Sample ActiveX Component in Java - Media Player");
        setLayout(new BorderLayout());
        wmPlayer = ActiveX.createObject(WindowsMediaPlayer.class, axPanel); 
        add(axPanel, BorderLayout.CENTER);
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }

        });        

        Panel buttonsPanel = new Panel(new FlowLayout());

        Button playButton = new Button("Play Media...");
       
        Button infoButton = new Button("Info");
        
        buttonsPanel.add(playButton);
        buttonsPanel.add(infoButton);
        buttonsPanel.setBackground(Color.YELLOW);
        add(buttonsPanel, BorderLayout.NORTH);
        playButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                FileDialog fd = new FileDialog(MediaPlayerExample.this, "Open SWF");
                fd.setMultipleMode(false);
                fd.setVisible(true);
                if (fd.getFile()!=null) {
                    String file = "file:///"+fd.getDirectory().replace("\\", "/") + "/" + fd.getFile();                
                    wmPlayer.setURL(file);
                }
            }
        });
        
        infoButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                IWMPMedia m = wmPlayer.getCurrentMedia();                
                System.out.println("Duration:"+m.getDuration());
            }
        });
        
        //Try some events...
        axPanel.addMouseMotionListener(new MouseMotionAdapter() {

            @Override
            public void mouseMoved(MouseEvent e) {
                //System.out.println("Moved "+e.getX()+", "+e.getY());
            }            
        });
        
        axPanel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Click");
            }                               
        });
        wmPlayer.addPositionChangeListener(new ActiveXEventListener() {
            @Override
            public void onEvent(ActiveXEvent ev) {
                System.out.println("Pos change:"+wmPlayer.getControls().getCurrentPositionString());
            }
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {                        
        new MediaPlayerExample().setVisible(true);                        
    }

}
