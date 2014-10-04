package com.jpexs.javactivex.jna;

import com.sun.jna.Structure;
import com.jpexs.javactivex.jna.WinDef.DWORD;
import com.jpexs.javactivex.jna.WinDef.HICON;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author JPEXS
 */
public class SHFILEINFO extends Structure {

    public HICON hIcon;
    public int iIcon;
    public DWORD dwAttributes;
    public char[] szDisplayName = new char[260];
    public char[] szTypeName = new char[80];

    @Override
    protected List getFieldOrder() {
        return Arrays.asList("hIcon", "iIcon", "dwAttributes", "szDisplayName", "szTypeName");
    }
}
