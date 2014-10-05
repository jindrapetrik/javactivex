package com.jpexs.javactivex.example;

import com.jpexs.javactivex.example.controls.WebBrowser;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Example of using ActiveX components - Flash player
 *
 * @author JPEXS
 */
public class WebBrowserExample extends Frame {

    private WebBrowser browser;
    private final Panel axPanel = new Panel();
    
    public WebBrowserExample() {
        setSize(800, 600);
        setTitle("Sample ActiveX Component in Java - WebBrowser");
        setLayout(new BorderLayout());
        browser = new WebBrowser(axPanel);
        add(axPanel, BorderLayout.CENTER);
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }

        });        

        Panel controlsPanel = new Panel(new BorderLayout());

        Button goButton = new Button("GO!");
        
        final TextField tf=new TextField("http://www.google.com/");
        
        final ActionListener navAction = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                browser.Navigate(tf.getText());
            }
        };
       
        
        tf.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    navAction.actionPerformed(null);
                }
            }
            
});
        controlsPanel.add(tf,BorderLayout.CENTER);
        controlsPanel.add(goButton,BorderLayout.EAST);
        controlsPanel.setBackground(Color.YELLOW);
        add(controlsPanel, BorderLayout.NORTH);
        goButton.addActionListener(navAction);            
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {                        
        new WebBrowserExample().setVisible(true);                      
    }

}
