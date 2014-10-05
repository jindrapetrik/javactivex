package com.jpexs.javactivex.example.controls;


import com.jpexs.javactivex.ActiveXControl;
import java.io.File;
import java.awt.Panel;

/**
 * Windows Media Player ActiveX Control
 */
public class WindowsMediaPlayer extends ActiveXControl {

	/**
	 * Constructs class which is already registered
	 * @param panel Target panel to view component in
	 */
	public WindowsMediaPlayer(Panel panel) {
		this(panel, "");
	}

	/**
	 * Constructs WindowsMediaPlayer from OCX path
	 * @param panel Target panel to view component in
	 * @param ocxPath Path to OCX file which contains WindowsMediaPlayer class
	 */
	public WindowsMediaPlayer(Panel panel,String ocxPath) {
		super(ocxPath,"{6BF52A52-394A-11d3-B153-00C04F79FAA6}", panel);
	}

	/**
	 * Constructs WindowsMediaPlayer from OCX file
	 * @param panel Target panel to view component in
	 * @param ocx OCX file which contains WindowsMediaPlayer class
	 */
	public WindowsMediaPlayer(Panel panel, File ocx) {
		this(panel, ocx.getAbsolutePath());
	}

	/**
	 * Method GetTypeInfoCount
	 * 
	 * @param pctinfo (Pointer|UInt) 
	 */
	public void GetTypeInfoCount(Object pctinfo) {
		callMethod("GetTypeInfoCount", pctinfo);
	}

	/**
	 * Method AddRef
	 * 
	 * @return
	 */
	public long AddRef() {
		return (Long)callMethod("AddRef");
	}

	/**
	 * Method GetIDsOfNames
	 * 
	 * @param riid (Pointer|GUID) 
	 * @param rgszNames (Pointer|Pointer|ShortInt) 
	 * @param cNames
	 * @param lcid
	 * @param rgdispid (Pointer|Integer) 
	 */
	public void GetIDsOfNames(Object riid, Object rgszNames, int cNames, long lcid, Object rgdispid) {
		callMethod("GetIDsOfNames", riid, rgszNames, cNames, lcid, rgdispid);
	}

	/**
	 * Method QueryInterface
	 * 
	 * @param riid (Pointer|GUID) 
	 * @param ppvObj (Pointer|Pointer|Void) 
	 */
	public void QueryInterface(Object riid, Object ppvObj) {
		callMethod("QueryInterface", riid, ppvObj);
	}

	/**
	 * Method Invoke
	 * 
	 * @param dispidMember
	 * @param riid (Pointer|GUID) 
	 * @param lcid
	 * @param wFlags
	 * @param pdispparams (Pointer|DISPPARAMS) 
	 * @param pvarResult (Pointer|Variant) 
	 * @param pexcepinfo (Pointer|EXCEPINFO) 
	 * @param puArgErr (Pointer|UInt) 
	 */
	public void Invoke(int dispidMember, Object riid, long lcid, int wFlags, Object pdispparams, Object pvarResult, Object pexcepinfo, Object puArgErr) {
		callMethod("Invoke", dispidMember, riid, lcid, wFlags, pdispparams, pvarResult, pexcepinfo, puArgErr);
	}

	/**
	 * Method GetTypeInfo
	 * 
	 * @param itinfo
	 * @param lcid
	 * @param pptinfo (Pointer|Pointer|Void) 
	 */
	public void GetTypeInfo(int itinfo, long lcid, Object pptinfo) {
		callMethod("GetTypeInfo", itinfo, lcid, pptinfo);
	}

	/**
	 * Creates a new media object
	 * 
	 * @param bstrURL
	 * @return (Pointer|IWMPMedia)
	 */
	public Object newMedia(String bstrURL) {
		return callMethod("newMedia", bstrURL);
	}

	/**
	 * Opens the player with the specified URL
	 * 
	 * @param bstrURL
	 */
	public void openPlayer(String bstrURL) {
		callMethod("openPlayer", bstrURL);
	}

	/**
	 * Creates a new playlist object
	 * 
	 * @param bstrName
	 * @param bstrURL
	 * @return (Pointer|IWMPPlaylist)
	 */
	public Object newPlaylist(String bstrName, String bstrURL) {
		return callMethod("newPlaylist", bstrName, bstrURL);
	}

	/**
	 * Closes the media
	 * 
	 */
	public void close() {
		callMethod("close");
	}

	/**
	 * Method Release
	 * 
	 * @return
	 */
	public long Release() {
		return (Long)callMethod("Release");
	}

	/**
	 * Method launchURL
	 * 
	 * @param bstrURL
	 */
	public void launchURL(String bstrURL) {
		callMethod("launchURL", bstrURL);
	}

	/**
	 * Getter for property URL
	 * 
	 * @return URL value
	 */
	public String getURL() {
		return (String)getPropertyValue("URL");
	}

	/**
	 * Setter for property URL
	 * 
	 * @param value New URL value
	 */
	public void setURL(String value) {
		setPropertyValue("URL",value);
	}

	/**
	 * Getter for property currentMedia
	 * 
	 * @return (Pointer|IWMPMedia) currentMedia value
	 */
	public Object getCurrentMedia() {
		return (Object)getPropertyValue("currentMedia");
	}

	/**
	 * Setter for property currentMedia
	 * 
	 * @param value (Pointer|IWMPMedia) New currentMedia value
	 */
	public void setCurrentMedia(Object value) {
		setPropertyValue("currentMedia",value);
	}

	/**
	 * Getter for property currentPlaylist
	 * 
	 * @return (Pointer|IWMPPlaylist) currentPlaylist value
	 */
	public Object getCurrentPlaylist() {
		return (Object)getPropertyValue("currentPlaylist");
	}

	/**
	 * Setter for property currentPlaylist
	 * 
	 * @param value (Pointer|IWMPPlaylist) New currentPlaylist value
	 */
	public void setCurrentPlaylist(Object value) {
		setPropertyValue("currentPlaylist",value);
	}

	/**
	 * Getter for property enabled
	 * 
	 * @return enabled value
	 */
	public boolean getEnabled() {
		return (Boolean)getPropertyValue("enabled");
	}

	/**
	 * Setter for property enabled
	 * 
	 * @param value New enabled value
	 */
	public void setEnabled(boolean value) {
		setPropertyValue("enabled",value);
	}

	/**
	 * Getter for property fullScreen
	 * 
	 * @return fullScreen value
	 */
	public boolean getFullScreen() {
		return (Boolean)getPropertyValue("fullScreen");
	}

	/**
	 * Setter for property fullScreen
	 * 
	 * @param value New fullScreen value
	 */
	public void setFullScreen(boolean value) {
		setPropertyValue("fullScreen",value);
	}

	/**
	 * Getter for property enableContextMenu
	 * 
	 * @return enableContextMenu value
	 */
	public boolean getEnableContextMenu() {
		return (Boolean)getPropertyValue("enableContextMenu");
	}

	/**
	 * Setter for property enableContextMenu
	 * 
	 * @param value New enableContextMenu value
	 */
	public void setEnableContextMenu(boolean value) {
		setPropertyValue("enableContextMenu",value);
	}

	/**
	 * Getter for property uiMode
	 * 
	 * @return uiMode value
	 */
	public String getUiMode() {
		return (String)getPropertyValue("uiMode");
	}

	/**
	 * Setter for property uiMode
	 * 
	 * @param value New uiMode value
	 */
	public void setUiMode(String value) {
		setPropertyValue("uiMode",value);
	}

	/**
	 * Getter for property stretchToFit
	 * 
	 * @return stretchToFit value
	 */
	public boolean getStretchToFit() {
		return (Boolean)getPropertyValue("stretchToFit");
	}

	/**
	 * Setter for property stretchToFit
	 * 
	 * @param value New stretchToFit value
	 */
	public void setStretchToFit(boolean value) {
		setPropertyValue("stretchToFit",value);
	}

	/**
	 * Getter for property windowlessVideo
	 * 
	 * @return windowlessVideo value
	 */
	public boolean getWindowlessVideo() {
		return (Boolean)getPropertyValue("windowlessVideo");
	}

	/**
	 * Setter for property windowlessVideo
	 * 
	 * @param value New windowlessVideo value
	 */
	public void setWindowlessVideo(boolean value) {
		setPropertyValue("windowlessVideo",value);
	}
}
