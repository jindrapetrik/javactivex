package com.jpexs.javactivex.example.controls;

import com.jpexs.javactivex.ActiveXControl;
import java.io.File;

/**
 * Shockwave Flash
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
	 * @param ocxPath Path to OCX file which contains ShockwaveFlash class
	 */
	public ShockwaveFlash(String ocxPath) {
		super(ocxPath,"{d27cdb6e-ae6d-11cf-96b8-444553540000}");
	}

	/**
	 * Constructs ShockwaveFlash from OCX file
	 * @param ocx OCX file which contains ShockwaveFlash class
	 */
	public ShockwaveFlash(File ocx) {
		this(ocx.getAbsolutePath());
	}

	/**
	 * Method GetTypeInfoCount
	 * @param pctinfo
	 */
	public void GetTypeInfoCount(Object pctinfo) {
		callMethod("GetTypeInfoCount", pctinfo);
	}

	/**
	 * method EnforceLocalSecurity
	 */
	public void EnforceLocalSecurity() {
		callMethod("EnforceLocalSecurity");
	}

	/**
	 * method GotoFrame
	 * @param FrameNum
	 */
	public void GotoFrame(int FrameNum) {
		callMethod("GotoFrame", FrameNum);
	}

	/**
	 * method Stop
	 */
	public void Stop() {
		callMethod("Stop");
	}

	/**
	 * Method AddRef
	 * @return
	 */
	public long AddRef() {
		return (Long)callMethod("AddRef");
	}

	/**
	 * method SetZoomRect
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 */
	public void SetZoomRect(int left, int top, int right, int bottom) {
		callMethod("SetZoomRect", left, top, right, bottom);
	}

	/**
	 * method TStopPlay
	 * @param target
	 */
	public void TStopPlay(String target) {
		callMethod("TStopPlay", target);
	}

	/**
	 * method DisableLocalSecurity
	 */
	public void DisableLocalSecurity() {
		callMethod("DisableLocalSecurity");
	}

	/**
	 * method TGetProperty
	 * @param target
	 * @param property
	 * @return
	 */
	public String TGetProperty(String target, int property) {
		return (String)callMethod("TGetProperty", target, property);
	}

	/**
	 * Method Invoke
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
	 * method LoadMovie
	 * @param layer
	 * @param url
	 */
	public void LoadMovie(int layer, String url) {
		callMethod("LoadMovie", layer, url);
	}

	/**
	 * method TSetPropertyNum
	 * @param target
	 * @param property
	 * @param value
	 */
	public void TSetPropertyNum(String target, int property, double value) {
		callMethod("TSetPropertyNum", target, property, value);
	}

	/**
	 * method TGotoLabel
	 * @param target
	 * @param label
	 */
	public void TGotoLabel(String target, String label) {
		callMethod("TGotoLabel", target, label);
	}

	/**
	 * method TGotoFrame
	 * @param target
	 * @param FrameNum
	 */
	public void TGotoFrame(String target, int FrameNum) {
		callMethod("TGotoFrame", target, FrameNum);
	}

	/**
	 * method Back
	 */
	public void Back() {
		callMethod("Back");
	}

	/**
	 * method Zoom
	 * @param factor
	 */
	public void Zoom(int factor) {
		callMethod("Zoom", factor);
	}

	/**
	 * method GetVariable
	 * @param name
	 * @return
	 */
	public String GetVariable(String name) {
		return (String)callMethod("GetVariable", name);
	}

	/**
	 * method SetReturnValue
	 * @param returnValue
	 */
	public void SetReturnValue(String returnValue) {
		callMethod("SetReturnValue", returnValue);
	}

	/**
	 * method TGetPropertyAsNumber
	 * @param target
	 * @param property
	 * @return
	 */
	public double TGetPropertyAsNumber(String target, int property) {
		return (Double)callMethod("TGetPropertyAsNumber", target, property);
	}

	/**
	 * method Call
	 * @param request
	 * @return
	 */
	public String CallFunction(String request) {
		return (String)callMethod("CallFunction", request);
	}

	/**
	 * method TCurrentFrame
	 * @param target
	 * @return
	 */
	public int TCurrentFrame(String target) {
		return (Integer)callMethod("TCurrentFrame", target);
	}

	/**
	 * Method Release
	 * @return
	 */
	public long Release() {
		return (Long)callMethod("Release");
	}

	/**
	 * method PercentLoaded
	 * @return
	 */
	public int PercentLoaded() {
		return (Integer)callMethod("PercentLoaded");
	}

	/**
	 * method SetVariable
	 * @param name
	 * @param value
	 */
	public void SetVariable(String name, String value) {
		callMethod("SetVariable", name, value);
	}

	/**
	 * method TCallLabel
	 * @param target
	 * @param label
	 */
	public void TCallLabel(String target, String label) {
		callMethod("TCallLabel", target, label);
	}

	/**
	 * method FlashVersion
	 * @return
	 */
	public int FlashVersion() {
		return (Integer)callMethod("FlashVersion");
	}

	/**
	 * method CurrentFrame
	 * @return
	 */
	public int CurrentFrame() {
		return (Integer)callMethod("CurrentFrame");
	}

	/**
	 * Method GetIDsOfNames
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
	 * method FrameLoaded
	 * @param FrameNum
	 * @return
	 */
	public boolean FrameLoaded(int FrameNum) {
		return (Boolean)callMethod("FrameLoaded", FrameNum);
	}

	/**
	 * method TGetPropertyNum
	 * @param target
	 * @param property
	 * @return
	 */
	public double TGetPropertyNum(String target, int property) {
		return (Double)callMethod("TGetPropertyNum", target, property);
	}

	/**
	 * Method QueryInterface
	 * @param riid
	 * @param ppvObj
	 */
	public void QueryInterface(Object riid, Object ppvObj) {
		callMethod("QueryInterface", riid, ppvObj);
	}

	/**
	 * method TCallFrame
	 * @param target
	 * @param FrameNum
	 */
	public void TCallFrame(String target, int FrameNum) {
		callMethod("TCallFrame", target, FrameNum);
	}

	/**
	 * method TCurrentLabel
	 * @param target
	 * @return
	 */
	public String TCurrentLabel(String target) {
		return (String)callMethod("TCurrentLabel", target);
	}

	/**
	 * method TPlay
	 * @param target
	 */
	public void TPlay(String target) {
		callMethod("TPlay", target);
	}

	/**
	 * method Rewind
	 */
	public void Rewind() {
		callMethod("Rewind");
	}

	/**
	 * Method GetTypeInfo
	 * @param itinfo
	 * @param lcid
	 * @param pptinfo
	 */
	public void GetTypeInfo(int itinfo, long lcid, Object pptinfo) {
		callMethod("GetTypeInfo", itinfo, lcid, pptinfo);
	}

	/**
	 * method Play
	 */
	public void Play() {
		callMethod("Play");
	}

	/**
	 * method TSetProperty
	 * @param target
	 * @param property
	 * @param value
	 */
	public void TSetProperty(String target, int property, String value) {
		callMethod("TSetProperty", target, property, value);
	}

	/**
	 * method StopPlay
	 */
	public void StopPlay() {
		callMethod("StopPlay");
	}

	/**
	 * method Forward
	 */
	public void Forward() {
		callMethod("Forward");
	}

	/**
	 * method Pan
	 * @param x
	 * @param y
	 * @param mode
	 */
	public void Pan(int x, int y, int mode) {
		callMethod("Pan", x, y, mode);
	}

	/**
	 * method IsPlaying
	 * @return
	 */
	public boolean IsPlaying() {
		return (Boolean)callMethod("IsPlaying");
	}

	/**
	 * Getter for property Playing
	 * @return Playing value
	 */
	public boolean getPlaying() {
		return (Boolean)getPropertyValue("Playing");
	}

	/**
	 * Setter for property Playing
	 * @param value New Playing value
	 */
	public void setPlaying(boolean value) {
		setPropertyValue("Playing",value);
	}

	/**
	 * Getter for property Quality
	 * @return Quality value
	 */
	public int getQuality() {
		return (Integer)getPropertyValue("Quality");
	}

	/**
	 * Setter for property Quality
	 * @param value New Quality value
	 */
	public void setQuality(int value) {
		setPropertyValue("Quality",value);
	}

	/**
	 * Getter for property ScaleMode
	 * @return ScaleMode value
	 */
	public int getScaleMode() {
		return (Integer)getPropertyValue("ScaleMode");
	}

	/**
	 * Setter for property ScaleMode
	 * @param value New ScaleMode value
	 */
	public void setScaleMode(int value) {
		setPropertyValue("ScaleMode",value);
	}

	/**
	 * Getter for property AlignMode
	 * @return AlignMode value
	 */
	public int getAlignMode() {
		return (Integer)getPropertyValue("AlignMode");
	}

	/**
	 * Setter for property AlignMode
	 * @param value New AlignMode value
	 */
	public void setAlignMode(int value) {
		setPropertyValue("AlignMode",value);
	}

	/**
	 * Getter for property BackgroundColor
	 * @return BackgroundColor value
	 */
	public int getBackgroundColor() {
		return (Integer)getPropertyValue("BackgroundColor");
	}

	/**
	 * Setter for property BackgroundColor
	 * @param value New BackgroundColor value
	 */
	public void setBackgroundColor(int value) {
		setPropertyValue("BackgroundColor",value);
	}

	/**
	 * Getter for property Loop
	 * @return Loop value
	 */
	public boolean getLoop() {
		return (Boolean)getPropertyValue("Loop");
	}

	/**
	 * Setter for property Loop
	 * @param value New Loop value
	 */
	public void setLoop(boolean value) {
		setPropertyValue("Loop",value);
	}

	/**
	 * Getter for property Movie
	 * @return Movie value
	 */
	public String getMovie() {
		return (String)getPropertyValue("Movie");
	}

	/**
	 * Setter for property Movie
	 * @param value New Movie value
	 */
	public void setMovie(String value) {
		setPropertyValue("Movie",value);
	}

	/**
	 * Getter for property FrameNum
	 * @return FrameNum value
	 */
	public int getFrameNum() {
		return (Integer)getPropertyValue("FrameNum");
	}

	/**
	 * Setter for property FrameNum
	 * @param value New FrameNum value
	 */
	public void setFrameNum(int value) {
		setPropertyValue("FrameNum",value);
	}

	/**
	 * Getter for property WMode
	 * @return WMode value
	 */
	public String getWMode() {
		return (String)getPropertyValue("WMode");
	}

	/**
	 * Setter for property WMode
	 * @param value New WMode value
	 */
	public void setWMode(String value) {
		setPropertyValue("WMode",value);
	}

	/**
	 * Getter for property SAlign
	 * @return SAlign value
	 */
	public String getSAlign() {
		return (String)getPropertyValue("SAlign");
	}

	/**
	 * Setter for property SAlign
	 * @param value New SAlign value
	 */
	public void setSAlign(String value) {
		setPropertyValue("SAlign",value);
	}

	/**
	 * Getter for property Menu
	 * @return Menu value
	 */
	public boolean getMenu() {
		return (Boolean)getPropertyValue("Menu");
	}

	/**
	 * Setter for property Menu
	 * @param value New Menu value
	 */
	public void setMenu(boolean value) {
		setPropertyValue("Menu",value);
	}

	/**
	 * Getter for property Base
	 * @return Base value
	 */
	public String getBase() {
		return (String)getPropertyValue("Base");
	}

	/**
	 * Setter for property Base
	 * @param value New Base value
	 */
	public void setBase(String value) {
		setPropertyValue("Base",value);
	}

	/**
	 * Getter for property Scale
	 * @return Scale value
	 */
	public String getScale() {
		return (String)getPropertyValue("Scale");
	}

	/**
	 * Setter for property Scale
	 * @param value New Scale value
	 */
	public void setScale(String value) {
		setPropertyValue("Scale",value);
	}

	/**
	 * Getter for property DeviceFont
	 * @return DeviceFont value
	 */
	public boolean getDeviceFont() {
		return (Boolean)getPropertyValue("DeviceFont");
	}

	/**
	 * Setter for property DeviceFont
	 * @param value New DeviceFont value
	 */
	public void setDeviceFont(boolean value) {
		setPropertyValue("DeviceFont",value);
	}

	/**
	 * Getter for property EmbedMovie
	 * @return EmbedMovie value
	 */
	public boolean getEmbedMovie() {
		return (Boolean)getPropertyValue("EmbedMovie");
	}

	/**
	 * Setter for property EmbedMovie
	 * @param value New EmbedMovie value
	 */
	public void setEmbedMovie(boolean value) {
		setPropertyValue("EmbedMovie",value);
	}

	/**
	 * Getter for property BGColor
	 * @return BGColor value
	 */
	public String getBGColor() {
		return (String)getPropertyValue("BGColor");
	}

	/**
	 * Setter for property BGColor
	 * @param value New BGColor value
	 */
	public void setBGColor(String value) {
		setPropertyValue("BGColor",value);
	}

	/**
	 * Getter for property Quality2
	 * @return Quality2 value
	 */
	public String getQuality2() {
		return (String)getPropertyValue("Quality2");
	}

	/**
	 * Setter for property Quality2
	 * @param value New Quality2 value
	 */
	public void setQuality2(String value) {
		setPropertyValue("Quality2",value);
	}

	/**
	 * Getter for property SWRemote
	 * @return SWRemote value
	 */
	public String getSWRemote() {
		return (String)getPropertyValue("SWRemote");
	}

	/**
	 * Setter for property SWRemote
	 * @param value New SWRemote value
	 */
	public void setSWRemote(String value) {
		setPropertyValue("SWRemote",value);
	}

	/**
	 * Getter for property FlashVars
	 * @return FlashVars value
	 */
	public String getFlashVars() {
		return (String)getPropertyValue("FlashVars");
	}

	/**
	 * Setter for property FlashVars
	 * @param value New FlashVars value
	 */
	public void setFlashVars(String value) {
		setPropertyValue("FlashVars",value);
	}

	/**
	 * Getter for property AllowScriptAccess
	 * @return AllowScriptAccess value
	 */
	public String getAllowScriptAccess() {
		return (String)getPropertyValue("AllowScriptAccess");
	}

	/**
	 * Setter for property AllowScriptAccess
	 * @param value New AllowScriptAccess value
	 */
	public void setAllowScriptAccess(String value) {
		setPropertyValue("AllowScriptAccess",value);
	}

	/**
	 * Getter for property MovieData
	 * @return MovieData value
	 */
	public String getMovieData() {
		return (String)getPropertyValue("MovieData");
	}

	/**
	 * Setter for property MovieData
	 * @param value New MovieData value
	 */
	public void setMovieData(String value) {
		setPropertyValue("MovieData",value);
	}

	/**
	 * Getter for property InlineData
	 * @return InlineData value
	 */
	public Object getInlineData() {
		return (Object)getPropertyValue("InlineData");
	}

	/**
	 * Setter for property InlineData
	 * @param value New InlineData value
	 */
	public void setInlineData(Object value) {
		setPropertyValue("InlineData",value);
	}

	/**
	 * Getter for property SeamlessTabbing
	 * @return SeamlessTabbing value
	 */
	public boolean getSeamlessTabbing() {
		return (Boolean)getPropertyValue("SeamlessTabbing");
	}

	/**
	 * Setter for property SeamlessTabbing
	 * @param value New SeamlessTabbing value
	 */
	public void setSeamlessTabbing(boolean value) {
		setPropertyValue("SeamlessTabbing",value);
	}

	/**
	 * Getter for property Profile
	 * @return Profile value
	 */
	public boolean getProfile() {
		return (Boolean)getPropertyValue("Profile");
	}

	/**
	 * Setter for property Profile
	 * @param value New Profile value
	 */
	public void setProfile(boolean value) {
		setPropertyValue("Profile",value);
	}

	/**
	 * Getter for property ProfileAddress
	 * @return ProfileAddress value
	 */
	public String getProfileAddress() {
		return (String)getPropertyValue("ProfileAddress");
	}

	/**
	 * Setter for property ProfileAddress
	 * @param value New ProfileAddress value
	 */
	public void setProfileAddress(String value) {
		setPropertyValue("ProfileAddress",value);
	}

	/**
	 * Getter for property ProfilePort
	 * @return ProfilePort value
	 */
	public int getProfilePort() {
		return (Integer)getPropertyValue("ProfilePort");
	}

	/**
	 * Setter for property ProfilePort
	 * @param value New ProfilePort value
	 */
	public void setProfilePort(int value) {
		setPropertyValue("ProfilePort",value);
	}

	/**
	 * Getter for property AllowNetworking
	 * @return AllowNetworking value
	 */
	public String getAllowNetworking() {
		return (String)getPropertyValue("AllowNetworking");
	}

	/**
	 * Setter for property AllowNetworking
	 * @param value New AllowNetworking value
	 */
	public void setAllowNetworking(String value) {
		setPropertyValue("AllowNetworking",value);
	}

	/**
	 * Getter for property AllowFullScreen
	 * @return AllowFullScreen value
	 */
	public String getAllowFullScreen() {
		return (String)getPropertyValue("AllowFullScreen");
	}

	/**
	 * Setter for property AllowFullScreen
	 * @param value New AllowFullScreen value
	 */
	public void setAllowFullScreen(String value) {
		setPropertyValue("AllowFullScreen",value);
	}

	/**
	 * Getter for property AllowFullScreenInteractive
	 * @return AllowFullScreenInteractive value
	 */
	public String getAllowFullScreenInteractive() {
		return (String)getPropertyValue("AllowFullScreenInteractive");
	}

	/**
	 * Setter for property AllowFullScreenInteractive
	 * @param value New AllowFullScreenInteractive value
	 */
	public void setAllowFullScreenInteractive(String value) {
		setPropertyValue("AllowFullScreenInteractive",value);
	}

	/**
	 * Getter for property IsDependent
	 * @return IsDependent value
	 */
	public boolean getIsDependent() {
		return (Boolean)getPropertyValue("IsDependent");
	}

	/**
	 * Setter for property IsDependent
	 * @param value New IsDependent value
	 */
	public void setIsDependent(boolean value) {
		setPropertyValue("IsDependent",value);
	}
}