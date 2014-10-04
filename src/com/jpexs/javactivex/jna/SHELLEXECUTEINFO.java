package com.jpexs.javactivex.jna;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.WString;
import com.jpexs.javactivex.jna.WinDef.HINSTANCE;
import com.jpexs.javactivex.jna.WinDef.HWND;
import com.jpexs.javactivex.jna.WinNT.HANDLE;
import com.jpexs.javactivex.jna.WinReg.HKEY;
import java.util.Arrays;
import java.util.List;

public class SHELLEXECUTEINFO extends Structure {

    public int cbSize = size();
    public int fMask;
    public HWND hwnd;
    public WString lpVerb;
    public WString lpFile;
    public WString lpParameters;
    public WString lpDirectory;
    public int nShow;
    public HINSTANCE hInstApp;
    public Pointer lpIDList;
    public WString lpClass;
    public HKEY hKeyClass;
    public int dwHotKey;
    public HANDLE hMonitor;
    public HANDLE hProcess;

    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList(new String[]{"cbSize", "fMask", "hwnd", "lpVerb", "lpFile", "lpParameters", "lpDirectory", "nShow", "hInstApp", "lpIDList",
            "lpClass", "hKeyClass", "dwHotKey", "hMonitor", "hProcess"});
    }
}
