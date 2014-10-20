package com.jpexs.javactivex.example;

import com.jpexs.javactivex.ActiveX;
import com.jpexs.javactivex.Reference;
import com.jpexs.javactivex.example.controls.browser.WebBrowser;
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
import javax.swing.Action;

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
        browser = ActiveX.createObject(WebBrowser.class,axPanel);
        add(axPanel, BorderLayout.CENTER);
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }

        });        

        Panel controlsPanel = new Panel(new BorderLayout());

         Button testButton = new Button("Test");
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
        
        testButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Reference<Integer> i1= new Reference<>(800);
                Reference<Integer> i2= new Reference<>(600);
                System.out.println("exec");
                browser.ClientToWindow(i1,i2);
                System.out.println("i1:"+i1);
                System.out.println("i2:"+i2);
            }
        });
        
        controlsPanel.add(tf,BorderLayout.CENTER);
        controlsPanel.add(testButton,BorderLayout.WEST);
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
