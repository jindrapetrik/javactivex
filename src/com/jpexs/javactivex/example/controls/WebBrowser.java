package com.jpexs.javactivex.example.controls;

import com.jpexs.javactivex.ActiveXControl;
import java.awt.Panel;
import java.io.File;

/**
 * WebBrowser Control
 */
public class WebBrowser extends ActiveXControl {

	/**
	 * Constructs class which is already registered
	 * @param panel Target panel to view component in
	 */
	public WebBrowser(Panel panel) {
		this(panel, "");
	}

	/**
	 * Constructs WebBrowser from OCX path
	 * @param panel Target panel to view component in
	 * @param ocxPath Path to OCX file which contains WebBrowser class
	 */
	public WebBrowser(Panel panel,String ocxPath) {
		super(ocxPath,"{8856F961-340A-11D0-A96B-00C04FD705A2}", panel);
	}

	/**
	 * Constructs WebBrowser from OCX file
	 * @param panel Target panel to view component in
	 * @param ocx OCX file which contains WebBrowser class
	 */
	public WebBrowser(Panel panel, File ocx) {
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
	 * Go home/start page.
	 * 
	 */
	public void GoHome() {
		callMethod("GoHome");
	}

	/**
	 * Set BrowserBar to Clsid
	 * 
	 * @param pvaClsid (Pointer|Variant) 
	 * @param pvarShow (Pointer|Variant) 
	 * @param pvarSize (Pointer|Variant) 
	 */
	public void ShowBrowserBar(Object pvaClsid, Object pvarShow, Object pvarSize) {
		callMethod("ShowBrowserBar", pvaClsid, pvarShow, pvarSize);
	}

	/**
	 * Set BrowserBar to Clsid
	 * 
	 * @param pvaClsid (Pointer|Variant) 
	 * @param pvarShow (Pointer|Variant) 
	 */
	public void ShowBrowserBar(Object pvaClsid, Object pvarShow) {
		callMethod("ShowBrowserBar", pvaClsid, pvarShow);
	}

	/**
	 * Set BrowserBar to Clsid
	 * 
	 * @param pvaClsid (Pointer|Variant) 
	 */
	public void ShowBrowserBar(Object pvaClsid) {
		callMethod("ShowBrowserBar", pvaClsid);
	}

	/**
	 * Navigates to the next item in the history list.
	 * 
	 */
	public void GoForward() {
		callMethod("GoForward");
	}

	/**
	 * IOleCommandTarget::QueryStatus
	 * 
	 * @param cmdID (OLECMDID) 
	 * @return (OLECMDF)
	 */
	public Object QueryStatusWB(Object cmdID) {
		return callMethod("QueryStatusWB", cmdID);
	}

	/**
	 * Stops opening a file.
	 * 
	 */
	public void Stop() {
		callMethod("Stop");
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
	 * Exits application and closes the open document.
	 * 
	 */
	public void Quit() {
		callMethod("Quit");
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
	 * Navigates to the previous item in the history list.
	 * 
	 */
	public void GoBack() {
		callMethod("GoBack");
	}

	/**
	 * Refresh the currently viewed page.
	 * 
	 * @param Level (Pointer|Variant) 
	 */
	public void Refresh2(Object Level) {
		callMethod("Refresh2", Level);
	}

	/**
	 * Refresh the currently viewed page.
	 * 
	 */
	public void Refresh2() {
		callMethod("Refresh2");
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
	 * Refresh the currently viewed page.
	 * 
	 */
	public void Refresh() {
		callMethod("Refresh");
	}

	/**
	 * Navigates to a URL or file or pidl.
	 * 
	 * @param URL (Pointer|Variant) 
	 * @param Flags (Pointer|Variant) 
	 * @param TargetFrameName (Pointer|Variant) 
	 * @param PostData (Pointer|Variant) 
	 * @param Headers (Pointer|Variant) 
	 */
	public void Navigate2(Object URL, Object Flags, Object TargetFrameName, Object PostData, Object Headers) {
		callMethod("Navigate2", URL, Flags, TargetFrameName, PostData, Headers);
	}

	/**
	 * Navigates to a URL or file or pidl.
	 * 
	 * @param URL (Pointer|Variant) 
	 * @param Flags (Pointer|Variant) 
	 * @param TargetFrameName (Pointer|Variant) 
	 * @param PostData (Pointer|Variant) 
	 */
	public void Navigate2(Object URL, Object Flags, Object TargetFrameName, Object PostData) {
		callMethod("Navigate2", URL, Flags, TargetFrameName, PostData);
	}

	/**
	 * Navigates to a URL or file or pidl.
	 * 
	 * @param URL (Pointer|Variant) 
	 * @param Flags (Pointer|Variant) 
	 * @param TargetFrameName (Pointer|Variant) 
	 */
	public void Navigate2(Object URL, Object Flags, Object TargetFrameName) {
		callMethod("Navigate2", URL, Flags, TargetFrameName);
	}

	/**
	 * Navigates to a URL or file or pidl.
	 * 
	 * @param URL (Pointer|Variant) 
	 * @param Flags (Pointer|Variant) 
	 */
	public void Navigate2(Object URL, Object Flags) {
		callMethod("Navigate2", URL, Flags);
	}

	/**
	 * Navigates to a URL or file or pidl.
	 * 
	 * @param URL (Pointer|Variant) 
	 */
	public void Navigate2(Object URL) {
		callMethod("Navigate2", URL);
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
	 * IOleCommandTarget::Exec
	 * 
	 * @param cmdID (OLECMDID) 
	 * @param cmdexecopt (OLECMDEXECOPT) 
	 * @param pvaIn (Pointer|Variant) 
	 * @param pvaOut (Pointer|Variant) 
	 */
	public void ExecWB(Object cmdID, Object cmdexecopt, Object pvaIn, Object pvaOut) {
		callMethod("ExecWB", cmdID, cmdexecopt, pvaIn, pvaOut);
	}

	/**
	 * IOleCommandTarget::Exec
	 * 
	 * @param cmdID (OLECMDID) 
	 * @param cmdexecopt (OLECMDEXECOPT) 
	 * @param pvaIn (Pointer|Variant) 
	 */
	public void ExecWB(Object cmdID, Object cmdexecopt, Object pvaIn) {
		callMethod("ExecWB", cmdID, cmdexecopt, pvaIn);
	}

	/**
	 * IOleCommandTarget::Exec
	 * 
	 * @param cmdID (OLECMDID) 
	 * @param cmdexecopt (OLECMDEXECOPT) 
	 */
	public void ExecWB(Object cmdID, Object cmdexecopt) {
		callMethod("ExecWB", cmdID, cmdexecopt);
	}

	/**
	 * Go Search Page.
	 * 
	 */
	public void GoSearch() {
		callMethod("GoSearch");
	}

	/**
	 * Converts client sizes into window sizes.
	 * 
	 * @param pcx (Pointer|Int) 
	 * @param pcy (Pointer|Int) 
	 */
	public void ClientToWindow(Object pcx, Object pcy) {
		callMethod("ClientToWindow", pcx, pcy);
	}

	/**
	 * Associates vtValue with the name szProperty in the context of the object.
	 * 
	 * @param Property
	 * @param vtValue
	 */
	public void PutProperty(String Property, String vtValue) {
		callMethod("PutProperty", Property, vtValue);
	}

	/**
	 * Navigates to a URL or file.
	 * 
	 * @param URL
	 * @param Flags (Pointer|Variant) 
	 * @param TargetFrameName (Pointer|Variant) 
	 * @param PostData (Pointer|Variant) 
	 * @param Headers (Pointer|Variant) 
	 */
	public void Navigate(String URL, Object Flags, Object TargetFrameName, Object PostData, Object Headers) {
		callMethod("Navigate", URL, Flags, TargetFrameName, PostData, Headers);
	}

	/**
	 * Navigates to a URL or file.
	 * 
	 * @param URL
	 * @param Flags (Pointer|Variant) 
	 * @param TargetFrameName (Pointer|Variant) 
	 * @param PostData (Pointer|Variant) 
	 */
	public void Navigate(String URL, Object Flags, Object TargetFrameName, Object PostData) {
		callMethod("Navigate", URL, Flags, TargetFrameName, PostData);
	}

	/**
	 * Navigates to a URL or file.
	 * 
	 * @param URL
	 * @param Flags (Pointer|Variant) 
	 * @param TargetFrameName (Pointer|Variant) 
	 */
	public void Navigate(String URL, Object Flags, Object TargetFrameName) {
		callMethod("Navigate", URL, Flags, TargetFrameName);
	}

	/**
	 * Navigates to a URL or file.
	 * 
	 * @param URL
	 * @param Flags (Pointer|Variant) 
	 */
	public void Navigate(String URL, Object Flags) {
		callMethod("Navigate", URL, Flags);
	}

	/**
	 * Navigates to a URL or file.
	 * 
	 * @param URL
	 */
	public void Navigate(String URL) {
		callMethod("Navigate", URL);
	}

	/**
	 * Retrieve the Associated value for the property vtValue in the context of the object.
	 * 
	 * @param Property
	 * @return
	 */
	public String GetProperty(String Property) {
		return (String)callMethod("GetProperty", Property);
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
	 * Setter for property Offline
	 * 
	 * @param value New Offline value
	 */
	public void setOffline(boolean value) {
		setPropertyValue("Offline",value);
	}

	/**
	 * Setter for property Silent
	 * 
	 * @param value New Silent value
	 */
	public void setSilent(boolean value) {
		setPropertyValue("Silent",value);
	}

	/**
	 * Setter for property RegisterAsBrowser
	 * 
	 * @param value New RegisterAsBrowser value
	 */
	public void setRegisterAsBrowser(boolean value) {
		setPropertyValue("RegisterAsBrowser",value);
	}

	/**
	 * Setter for property RegisterAsDropTarget
	 * 
	 * @param value New RegisterAsDropTarget value
	 */
	public void setRegisterAsDropTarget(boolean value) {
		setPropertyValue("RegisterAsDropTarget",value);
	}

	/**
	 * Setter for property TheaterMode
	 * 
	 * @param value New TheaterMode value
	 */
	public void setTheaterMode(boolean value) {
		setPropertyValue("TheaterMode",value);
	}

	/**
	 * Setter for property AddressBar
	 * 
	 * @param value New AddressBar value
	 */
	public void setAddressBar(boolean value) {
		setPropertyValue("AddressBar",value);
	}

	/**
	 * Setter for property Resizable
	 * 
	 * @param value New Resizable value
	 */
	public void setResizable(boolean value) {
		setPropertyValue("Resizable",value);
	}
}
