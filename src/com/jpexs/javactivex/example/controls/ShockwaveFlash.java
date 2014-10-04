package com.jpexs.javactivex.example.controls;

import com.jpexs.javactivex.ActiveXControl;
import java.io.File;

/**
 * Class ShockwaveFlash.ShockwaveFlash.11 Shockwave Flash
 */
public class ShockwaveFlash extends ActiveXControl {

    /**
     * Constructs class which is already registered
     */
    public ShockwaveFlash() {
        this("");
    }

    /**
     * Constructs ShockwaveFlash from OCX path
     *
     * @param ocxPath Path to OCX file which contains ShockwaveFlash class
     */
    public ShockwaveFlash(String ocxPath) {
        super(ocxPath, "{d27cdb6e-ae6d-11cf-96b8-444553540000}");
    }

    /**
     * Constructs ShockwaveFlash from OCX file
     *
     * @param ocx OCX file which contains ShockwaveFlash class
     */
    public ShockwaveFlash(File ocx) {
        this(ocx.getAbsolutePath());
    }

    /**
     * Method QueryInterface
     *
     * @param riid
     * @param ppvObj
     */
    public void QueryInterface(Object riid, Object ppvObj) {
        callMethod("QueryInterface", riid, ppvObj);
    }

    /**
     * Method AddRef
     *
     * @return
     */
    public long AddRef() {
        return (Long) callMethod("AddRef");
    }

    /**
     * Method Release
     *
     * @return
     */
    public long Release() {
        return (Long) callMethod("Release");
    }

    /**
     * Method GetTypeInfoCount
     *
     * @param pctinfo
     */
    public void GetTypeInfoCount(Object pctinfo) {
        callMethod("GetTypeInfoCount", pctinfo);
    }

    /**
     * Method GetTypeInfo
     *
     * @param itinfo
     * @param lcid
     * @param pptinfo
     */
    public void GetTypeInfo(int itinfo, long lcid, Object pptinfo) {
        callMethod("GetTypeInfo", itinfo, lcid, pptinfo);
    }

    /**
     * Method GetIDsOfNames
     *
     * @param riid
     * @param rgszNames
     * @param cNames
     * @param lcid
     * @param rgdispid
     */
    public void GetIDsOfNames(Object riid, Object rgszNames, int cNames, long lcid, Object rgdispid) {
        callMethod("GetIDsOfNames", riid, rgszNames, cNames, lcid, rgdispid);
    }

    /**
     * Method Invoke
     *
     * @param dispidMember
     * @param riid
     * @param lcid
     * @param wFlags
     * @param pdispparams
     * @param pvarResult
     * @param pexcepinfo
     * @param puArgErr
     */
    public void Invoke(int dispidMember, Object riid, long lcid, Integer wFlags, Object pdispparams, Object pvarResult, Object pexcepinfo, Object puArgErr) {
        callMethod("Invoke", dispidMember, riid, lcid, wFlags, pdispparams, pvarResult, pexcepinfo, puArgErr);
    }

    /**
     * method SetZoomRect
     *
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    public void SetZoomRect(int left, int top, int right, int bottom) {
        callMethod("SetZoomRect", left, top, right, bottom);
    }

    /**
     * method Zoom
     *
     * @param factor
     */
    public void Zoom(int factor) {
        callMethod("Zoom", factor);
    }

    /**
     * method Pan
     *
     * @param x
     * @param y
     * @param mode
     */
    public void Pan(int x, int y, int mode) {
        callMethod("Pan", x, y, mode);
    }

    /**
     * method Play
     */
    public void Play() {
        callMethod("Play");
    }

    /**
     * method Stop
     */
    public void Stop() {
        callMethod("Stop");
    }

    /**
     * method Back
     */
    public void Back() {
        callMethod("Back");
    }

    /**
     * method Forward
     */
    public void Forward() {
        callMethod("Forward");
    }

    /**
     * method Rewind
     */
    public void Rewind() {
        callMethod("Rewind");
    }

    /**
     * method StopPlay
     */
    public void StopPlay() {
        callMethod("StopPlay");
    }

    /**
     * method GotoFrame
     *
     * @param FrameNum
     */
    public void GotoFrame(int FrameNum) {
        callMethod("GotoFrame", FrameNum);
    }

    /**
     * method CurrentFrame
     *
     * @return
     */
    public int CurrentFrame() {
        return (Integer) callMethod("CurrentFrame");
    }

    /**
     * method IsPlaying
     *
     * @return
     */
    public boolean IsPlaying() {
        return (Boolean) callMethod("IsPlaying");
    }

    /**
     * method PercentLoaded
     *
     * @return
     */
    public int PercentLoaded() {
        return (Integer) callMethod("PercentLoaded");
    }

    /**
     * method FrameLoaded
     *
     * @param FrameNum
     * @return
     */
    public boolean FrameLoaded(int FrameNum) {
        return (Boolean) callMethod("FrameLoaded", FrameNum);
    }

    /**
     * method FlashVersion
     *
     * @return
     */
    public int FlashVersion() {
        return (Integer) callMethod("FlashVersion");
    }

    /**
     * method LoadMovie
     *
     * @param layer
     * @param url
     */
    public void LoadMovie(int layer, String url) {
        callMethod("LoadMovie", layer, url);
    }

    /**
     * method TGotoFrame
     *
     * @param target
     * @param FrameNum
     */
    public void TGotoFrame(String target, int FrameNum) {
        callMethod("TGotoFrame", target, FrameNum);
    }

    /**
     * method TGotoLabel
     *
     * @param target
     * @param label
     */
    public void TGotoLabel(String target, String label) {
        callMethod("TGotoLabel", target, label);
    }

    /**
     * method TCurrentFrame
     *
     * @param target
     * @return
     */
    public int TCurrentFrame(String target) {
        return (Integer) callMethod("TCurrentFrame", target);
    }

    /**
     * method TCurrentLabel
     *
     * @param target
     * @return
     */
    public String TCurrentLabel(String target) {
        return (String) callMethod("TCurrentLabel", target);
    }

    /**
     * method TPlay
     *
     * @param target
     */
    public void TPlay(String target) {
        callMethod("TPlay", target);
    }

    /**
     * method TStopPlay
     *
     * @param target
     */
    public void TStopPlay(String target) {
        callMethod("TStopPlay", target);
    }

    /**
     * method SetVariable
     *
     * @param name
     * @param value
     */
    public void SetVariable(String name, String value) {
        callMethod("SetVariable", name, value);
    }

    /**
     * method GetVariable
     *
     * @param name
     * @return
     */
    public String GetVariable(String name) {
        return (String) callMethod("GetVariable", name);
    }

    /**
     * method TSetProperty
     *
     * @param target
     * @param property
     * @param value
     */
    public void TSetProperty(String target, int property, String value) {
        callMethod("TSetProperty", target, property, value);
    }

    /**
     * method TGetProperty
     *
     * @param target
     * @param property
     * @return
     */
    public String TGetProperty(String target, int property) {
        return (String) callMethod("TGetProperty", target, property);
    }

    /**
     * method TCallFrame
     *
     * @param target
     * @param FrameNum
     */
    public void TCallFrame(String target, int FrameNum) {
        callMethod("TCallFrame", target, FrameNum);
    }

    /**
     * method TCallLabel
     *
     * @param target
     * @param label
     */
    public void TCallLabel(String target, String label) {
        callMethod("TCallLabel", target, label);
    }

    /**
     * method TSetPropertyNum
     *
     * @param target
     * @param property
     * @param value
     */
    public void TSetPropertyNum(String target, int property, double value) {
        callMethod("TSetPropertyNum", target, property, value);
    }

    /**
     * method TGetPropertyNum
     *
     * @param target
     * @param property
     * @return
     */
    public double TGetPropertyNum(String target, int property) {
        return (Double) callMethod("TGetPropertyNum", target, property);
    }

    /**
     * method TGetPropertyAsNumber
     *
     * @param target
     * @param property
     * @return
     */
    public double TGetPropertyAsNumber(String target, int property) {
        return (Double) callMethod("TGetPropertyAsNumber", target, property);
    }

    /**
     * method EnforceLocalSecurity
     */
    public void EnforceLocalSecurity() {
        callMethod("EnforceLocalSecurity");
    }

    /**
     * method Call
     *
     * @param request
     * @return
     */
    public String CallFunction(String request) {
        return (String) callMethod("CallFunction", request);
    }

    /**
     * method SetReturnValue
     *
     * @param returnValue
     */
    public void SetReturnValue(String returnValue) {
        callMethod("SetReturnValue", returnValue);
    }

    /**
     * method DisableLocalSecurity
     */
    public void DisableLocalSecurity() {
        callMethod("DisableLocalSecurity");
    }

    /**
     * Getter for property Playing
     *
     * @return Playing value
     */
    public boolean getPlaying() {
        return (Boolean) getProperty("Playing");
    }

    /**
     * Setter for property Playing
     *
     * @param value New Playing value
     */
    public void setPlaying(boolean value) {
        setProperty("Playing", value);
    }

    /**
     * Getter for property Quality
     *
     * @return Quality value
     */
    public int getQuality() {
        return (Integer) getProperty("Quality");
    }

    /**
     * Setter for property Quality
     *
     * @param value New Quality value
     */
    public void setQuality(int value) {
        setProperty("Quality", value);
    }

    /**
     * Getter for property ScaleMode
     *
     * @return ScaleMode value
     */
    public int getScaleMode() {
        return (Integer) getProperty("ScaleMode");
    }

    /**
     * Setter for property ScaleMode
     *
     * @param value New ScaleMode value
     */
    public void setScaleMode(int value) {
        setProperty("ScaleMode", value);
    }

    /**
     * Getter for property AlignMode
     *
     * @return AlignMode value
     */
    public int getAlignMode() {
        return (Integer) getProperty("AlignMode");
    }

    /**
     * Setter for property AlignMode
     *
     * @param value New AlignMode value
     */
    public void setAlignMode(int value) {
        setProperty("AlignMode", value);
    }

    /**
     * Getter for property BackgroundColor
     *
     * @return BackgroundColor value
     */
    public int getBackgroundColor() {
        return (Integer) getProperty("BackgroundColor");
    }

    /**
     * Setter for property BackgroundColor
     *
     * @param value New BackgroundColor value
     */
    public void setBackgroundColor(int value) {
        setProperty("BackgroundColor", value);
    }

    /**
     * Getter for property Loop
     *
     * @return Loop value
     */
    public boolean getLoop() {
        return (Boolean) getProperty("Loop");
    }

    /**
     * Setter for property Loop
     *
     * @param value New Loop value
     */
    public void setLoop(boolean value) {
        setProperty("Loop", value);
    }

    /**
     * Getter for property Movie
     *
     * @return Movie value
     */
    public String getMovie() {
        return (String) getProperty("Movie");
    }

    /**
     * Setter for property Movie
     *
     * @param value New Movie value
     */
    public void setMovie(String value) {
        setProperty("Movie", value);
    }

    /**
     * Getter for property FrameNum
     *
     * @return FrameNum value
     */
    public int getFrameNum() {
        return (Integer) getProperty("FrameNum");
    }

    /**
     * Setter for property FrameNum
     *
     * @param value New FrameNum value
     */
    public void setFrameNum(int value) {
        setProperty("FrameNum", value);
    }

    /**
     * Getter for property WMode
     *
     * @return WMode value
     */
    public String getWMode() {
        return (String) getProperty("WMode");
    }

    /**
     * Setter for property WMode
     *
     * @param value New WMode value
     */
    public void setWMode(String value) {
        setProperty("WMode", value);
    }

    /**
     * Getter for property SAlign
     *
     * @return SAlign value
     */
    public String getSAlign() {
        return (String) getProperty("SAlign");
    }

    /**
     * Setter for property SAlign
     *
     * @param value New SAlign value
     */
    public void setSAlign(String value) {
        setProperty("SAlign", value);
    }

    /**
     * Getter for property Menu
     *
     * @return Menu value
     */
    public boolean getMenu() {
        return (Boolean) getProperty("Menu");
    }

    /**
     * Setter for property Menu
     *
     * @param value New Menu value
     */
    public void setMenu(boolean value) {
        setProperty("Menu", value);
    }

    /**
     * Getter for property Base
     *
     * @return Base value
     */
    public String getBase() {
        return (String) getProperty("Base");
    }

    /**
     * Setter for property Base
     *
     * @param value New Base value
     */
    public void setBase(String value) {
        setProperty("Base", value);
    }

    /**
     * Getter for property Scale
     *
     * @return Scale value
     */
    public String getScale() {
        return (String) getProperty("Scale");
    }

    /**
     * Setter for property Scale
     *
     * @param value New Scale value
     */
    public void setScale(String value) {
        setProperty("Scale", value);
    }

    /**
     * Getter for property DeviceFont
     *
     * @return DeviceFont value
     */
    public boolean getDeviceFont() {
        return (Boolean) getProperty("DeviceFont");
    }

    /**
     * Setter for property DeviceFont
     *
     * @param value New DeviceFont value
     */
    public void setDeviceFont(boolean value) {
        setProperty("DeviceFont", value);
    }

    /**
     * Getter for property EmbedMovie
     *
     * @return EmbedMovie value
     */
    public boolean getEmbedMovie() {
        return (Boolean) getProperty("EmbedMovie");
    }

    /**
     * Setter for property EmbedMovie
     *
     * @param value New EmbedMovie value
     */
    public void setEmbedMovie(boolean value) {
        setProperty("EmbedMovie", value);
    }

    /**
     * Getter for property BGColor
     *
     * @return BGColor value
     */
    public String getBGColor() {
        return (String) getProperty("BGColor");
    }

    /**
     * Setter for property BGColor
     *
     * @param value New BGColor value
     */
    public void setBGColor(String value) {
        setProperty("BGColor", value);
    }

    /**
     * Getter for property Quality2
     *
     * @return Quality2 value
     */
    public String getQuality2() {
        return (String) getProperty("Quality2");
    }

    /**
     * Setter for property Quality2
     *
     * @param value New Quality2 value
     */
    public void setQuality2(String value) {
        setProperty("Quality2", value);
    }

    /**
     * Getter for property SWRemote
     *
     * @return SWRemote value
     */
    public String getSWRemote() {
        return (String) getProperty("SWRemote");
    }

    /**
     * Setter for property SWRemote
     *
     * @param value New SWRemote value
     */
    public void setSWRemote(String value) {
        setProperty("SWRemote", value);
    }

    /**
     * Getter for property FlashVars
     *
     * @return FlashVars value
     */
    public String getFlashVars() {
        return (String) getProperty("FlashVars");
    }

    /**
     * Setter for property FlashVars
     *
     * @param value New FlashVars value
     */
    public void setFlashVars(String value) {
        setProperty("FlashVars", value);
    }

    /**
     * Getter for property AllowScriptAccess
     *
     * @return AllowScriptAccess value
     */
    public String getAllowScriptAccess() {
        return (String) getProperty("AllowScriptAccess");
    }

    /**
     * Setter for property AllowScriptAccess
     *
     * @param value New AllowScriptAccess value
     */
    public void setAllowScriptAccess(String value) {
        setProperty("AllowScriptAccess", value);
    }

    /**
     * Getter for property MovieData
     *
     * @return MovieData value
     */
    public String getMovieData() {
        return (String) getProperty("MovieData");
    }

    /**
     * Setter for property MovieData
     *
     * @param value New MovieData value
     */
    public void setMovieData(String value) {
        setProperty("MovieData", value);
    }

    /**
     * Getter for property InlineData
     *
     * @return InlineData value
     */
    public Object getInlineData() {
        return (Object) getProperty("InlineData");
    }

    /**
     * Setter for property InlineData
     *
     * @param value New InlineData value
     */
    public void setInlineData(Object value) {
        setProperty("InlineData", value);
    }

    /**
     * Getter for property SeamlessTabbing
     *
     * @return SeamlessTabbing value
     */
    public boolean getSeamlessTabbing() {
        return (Boolean) getProperty("SeamlessTabbing");
    }

    /**
     * Setter for property SeamlessTabbing
     *
     * @param value New SeamlessTabbing value
     */
    public void setSeamlessTabbing(boolean value) {
        setProperty("SeamlessTabbing", value);
    }

    /**
     * Getter for property Profile
     *
     * @return Profile value
     */
    public boolean getProfile() {
        return (Boolean) getProperty("Profile");
    }

    /**
     * Setter for property Profile
     *
     * @param value New Profile value
     */
    public void setProfile(boolean value) {
        setProperty("Profile", value);
    }

    /**
     * Getter for property ProfileAddress
     *
     * @return ProfileAddress value
     */
    public String getProfileAddress() {
        return (String) getProperty("ProfileAddress");
    }

    /**
     * Setter for property ProfileAddress
     *
     * @param value New ProfileAddress value
     */
    public void setProfileAddress(String value) {
        setProperty("ProfileAddress", value);
    }

    /**
     * Getter for property ProfilePort
     *
     * @return ProfilePort value
     */
    public int getProfilePort() {
        return (Integer) getProperty("ProfilePort");
    }

    /**
     * Setter for property ProfilePort
     *
     * @param value New ProfilePort value
     */
    public void setProfilePort(int value) {
        setProperty("ProfilePort", value);
    }

    /**
     * Getter for property AllowNetworking
     *
     * @return AllowNetworking value
     */
    public String getAllowNetworking() {
        return (String) getProperty("AllowNetworking");
    }

    /**
     * Setter for property AllowNetworking
     *
     * @param value New AllowNetworking value
     */
    public void setAllowNetworking(String value) {
        setProperty("AllowNetworking", value);
    }

    /**
     * Getter for property AllowFullScreen
     *
     * @return AllowFullScreen value
     */
    public String getAllowFullScreen() {
        return (String) getProperty("AllowFullScreen");
    }

    /**
     * Setter for property AllowFullScreen
     *
     * @param value New AllowFullScreen value
     */
    public void setAllowFullScreen(String value) {
        setProperty("AllowFullScreen", value);
    }

    /**
     * Getter for property AllowFullScreenInteractive
     *
     * @return AllowFullScreenInteractive value
     */
    public String getAllowFullScreenInteractive() {
        return (String) getProperty("AllowFullScreenInteractive");
    }

    /**
     * Setter for property AllowFullScreenInteractive
     *
     * @param value New AllowFullScreenInteractive value
     */
    public void setAllowFullScreenInteractive(String value) {
        setProperty("AllowFullScreenInteractive", value);
    }

    /**
     * Getter for property IsDependent
     *
     * @return IsDependent value
     */
    public boolean getIsDependent() {
        return (Boolean) getProperty("IsDependent");
    }

    /**
     * Setter for property IsDependent
     *
     * @param value New IsDependent value
     */
    public void setIsDependent(boolean value) {
        setProperty("IsDependent", value);
    }
}
