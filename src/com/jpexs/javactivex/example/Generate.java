package com.jpexs.javactivex.example;

import com.jpexs.javactivex.ActiveX;
import java.util.HashSet;
import java.util.Set;

/**
 * Generation of sample classes
 * @author JPEXS
 */
public class Generate {
    
    
    
    
    public static void main(String[] args) {
        
        String basePath = "src/com/jpexs/javactivex/example/controls/";
        String basePkg = "com.jpexs.javactivex.example.controls.";
        
        //Flash        
        ActiveX.generateClassFromTLB("{d27cdb6e-ae6d-11cf-96b8-444553540000}",basePath+"flash",basePkg+"flash");
        
        //Browser
        ActiveX.generateClassFromTLB("{8856F961-340A-11D0-A96B-00C04FD705A2}",basePath+"browser",basePkg+"browser");
        
        //Media Player
        ActiveX.generateClassFromTLB("{6BF52A52-394A-11d3-B153-00C04F79FAA6}",basePath+"mediaplayer",basePkg+"mediaplayer");
        
        System.exit(0);
    }
}
