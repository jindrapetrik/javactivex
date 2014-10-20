javactivex
==========

ActiveX Controls in Java.


Library Compilation
===================
1. Compile server in `libsrc/ActiveXServer` with Turbo Delphi or later
2. Compile client in main directory with Ant or Netbeans. Java 7 is needed.

Library Usage
======================
1. Get the GUID of ActiveX component you want to use. Guid is the string in format<br />"`{xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx}`"
2. Run `com.jpexs.javactivex.ActiveX.generateClassFromTLB(GUID,outdir,pkg)`, it generates neccessary interfaces
3. Create an instance of java.awt.Panel to place control in
4. Create an instance of ActiveX control with `com.jpexs.javactivex.ActiveX.createObject(InterfaceName.class,panel)` to create control of InterfaceName class on the previously created panel

Running example
=======================
In the package `com.jpexs.javactivex.example` are three examples how to use some ActiveX controls - Shockwave Flash, WebBrowser (Internet Explorer) and Windows Media Player.


Warning
======================
It is all *EXPERIMENTAL*. Some of the features are still missing. For example record types (UDT), Array types.

Also it works on Windows only obviously.


License
======================
GNU LGPLv3
