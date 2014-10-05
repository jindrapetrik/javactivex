/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jpexs.javactivex;

import java.util.List;
import java.util.Map;

/**
 *
 * @author JPEXS
 */
public class ActiveXEvent {
    public ActiveXControl source;
    public String name;
    public Map<String,Object> args;
    public Map<String,String> argTypes;

    public ActiveXEvent(ActiveXControl source, String name, Map<String, Object> args, Map<String, String> argTypes) {
        this.source = source;
        this.name = name;
        this.args = args;
        this.argTypes = argTypes;
    }
   

    

    
    
    
    
}
