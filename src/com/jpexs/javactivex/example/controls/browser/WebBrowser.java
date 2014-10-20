package com.jpexs.javactivex.example.controls.browser;

import com.jpexs.javactivex.*;

/**
 * WebBrowser Control
 */
@GUID("{8856F961-340A-11D0-A96B-00C04FD705A2}")
public interface WebBrowser {


	/**
	 * Adds StatusTextChange event listener
	 * @param l Event listener
	 */
	@AddListener("StatusTextChange")
	public void addStatusTextChangeListener(ActiveXEventListener l);

	/**
	 * Removes StatusTextChange event listener
	 * @param l Event listener
	 */
	@RemoveListener("StatusTextChange")
	public void removeStatusTextChangeListener(ActiveXEventListener l);

	/**
	 * Adds ProgressChange event listener
	 * @param l Event listener
	 */
	@AddListener("ProgressChange")
	public void addProgressChangeListener(ActiveXEventListener l);

	/**
	 * Removes ProgressChange event listener
	 * @param l Event listener
	 */
	@RemoveListener("ProgressChange")
	public void removeProgressChangeListener(ActiveXEventListener l);

	/**
	 * Adds CommandStateChange event listener
	 * @param l Event listener
	 */
	@AddListener("CommandStateChange")
	public void addCommandStateChangeListener(ActiveXEventListener l);

	/**
	 * Removes CommandStateChange event listener
	 * @param l Event listener
	 */
	@RemoveListener("CommandStateChange")
	public void removeCommandStateChangeListener(ActiveXEventListener l);

	/**
	 * Adds DownloadBegin event listener
	 * @param l Event listener
	 */
	@AddListener("DownloadBegin")
	public void addDownloadBeginListener(ActiveXEventListener l);

	/**
	 * Removes DownloadBegin event listener
	 * @param l Event listener
	 */
	@RemoveListener("DownloadBegin")
	public void removeDownloadBeginListener(ActiveXEventListener l);

	/**
	 * Adds DownloadComplete event listener
	 * @param l Event listener
	 */
	@AddListener("DownloadComplete")
	public void addDownloadCompleteListener(ActiveXEventListener l);

	/**
	 * Removes DownloadComplete event listener
	 * @param l Event listener
	 */
	@RemoveListener("DownloadComplete")
	public void removeDownloadCompleteListener(ActiveXEventListener l);

	/**
	 * Adds TitleChange event listener
	 * @param l Event listener
	 */
	@AddListener("TitleChange")
	public void addTitleChangeListener(ActiveXEventListener l);

	/**
	 * Removes TitleChange event listener
	 * @param l Event listener
	 */
	@RemoveListener("TitleChange")
	public void removeTitleChangeListener(ActiveXEventListener l);

	/**
	 * Adds PropertyChange event listener
	 * @param l Event listener
	 */
	@AddListener("PropertyChange")
	public void addPropertyChangeListener(ActiveXEventListener l);

	/**
	 * Removes PropertyChange event listener
	 * @param l Event listener
	 */
	@RemoveListener("PropertyChange")
	public void removePropertyChangeListener(ActiveXEventListener l);

	/**
	 * Adds BeforeNavigate2 event listener
	 * @param l Event listener
	 */
	@AddListener("BeforeNavigate2")
	public void addBeforeNavigate2Listener(ActiveXEventListener l);

	/**
	 * Removes BeforeNavigate2 event listener
	 * @param l Event listener
	 */
	@RemoveListener("BeforeNavigate2")
	public void removeBeforeNavigate2Listener(ActiveXEventListener l);

	/**
	 * Adds NewWindow2 event listener
	 * @param l Event listener
	 */
	@AddListener("NewWindow2")
	public void addNewWindow2Listener(ActiveXEventListener l);

	/**
	 * Removes NewWindow2 event listener
	 * @param l Event listener
	 */
	@RemoveListener("NewWindow2")
	public void removeNewWindow2Listener(ActiveXEventListener l);

	/**
	 * Adds NavigateComplete2 event listener
	 * @param l Event listener
	 */
	@AddListener("NavigateComplete2")
	public void addNavigateComplete2Listener(ActiveXEventListener l);

	/**
	 * Removes NavigateComplete2 event listener
	 * @param l Event listener
	 */
	@RemoveListener("NavigateComplete2")
	public void removeNavigateComplete2Listener(ActiveXEventListener l);

	/**
	 * Adds DocumentComplete event listener
	 * @param l Event listener
	 */
	@AddListener("DocumentComplete")
	public void addDocumentCompleteListener(ActiveXEventListener l);

	/**
	 * Removes DocumentComplete event listener
	 * @param l Event listener
	 */
	@RemoveListener("DocumentComplete")
	public void removeDocumentCompleteListener(ActiveXEventListener l);

	/**
	 * Adds OnQuit event listener
	 * @param l Event listener
	 */
	@AddListener("OnQuit")
	public void addOnQuitListener(ActiveXEventListener l);

	/**
	 * Removes OnQuit event listener
	 * @param l Event listener
	 */
	@RemoveListener("OnQuit")
	public void removeOnQuitListener(ActiveXEventListener l);

	/**
	 * Adds OnVisible event listener
	 * @param l Event listener
	 */
	@AddListener("OnVisible")
	public void addOnVisibleListener(ActiveXEventListener l);

	/**
	 * Removes OnVisible event listener
	 * @param l Event listener
	 */
	@RemoveListener("OnVisible")
	public void removeOnVisibleListener(ActiveXEventListener l);

	/**
	 * Adds OnToolBar event listener
	 * @param l Event listener
	 */
	@AddListener("OnToolBar")
	public void addOnToolBarListener(ActiveXEventListener l);

	/**
	 * Removes OnToolBar event listener
	 * @param l Event listener
	 */
	@RemoveListener("OnToolBar")
	public void removeOnToolBarListener(ActiveXEventListener l);

	/**
	 * Adds OnMenuBar event listener
	 * @param l Event listener
	 */
	@AddListener("OnMenuBar")
	public void addOnMenuBarListener(ActiveXEventListener l);

	/**
	 * Removes OnMenuBar event listener
	 * @param l Event listener
	 */
	@RemoveListener("OnMenuBar")
	public void removeOnMenuBarListener(ActiveXEventListener l);

	/**
	 * Adds OnStatusBar event listener
	 * @param l Event listener
	 */
	@AddListener("OnStatusBar")
	public void addOnStatusBarListener(ActiveXEventListener l);

	/**
	 * Removes OnStatusBar event listener
	 * @param l Event listener
	 */
	@RemoveListener("OnStatusBar")
	public void removeOnStatusBarListener(ActiveXEventListener l);

	/**
	 * Adds OnFullScreen event listener
	 * @param l Event listener
	 */
	@AddListener("OnFullScreen")
	public void addOnFullScreenListener(ActiveXEventListener l);

	/**
	 * Removes OnFullScreen event listener
	 * @param l Event listener
	 */
	@RemoveListener("OnFullScreen")
	public void removeOnFullScreenListener(ActiveXEventListener l);

	/**
	 * Adds OnTheaterMode event listener
	 * @param l Event listener
	 */
	@AddListener("OnTheaterMode")
	public void addOnTheaterModeListener(ActiveXEventListener l);

	/**
	 * Removes OnTheaterMode event listener
	 * @param l Event listener
	 */
	@RemoveListener("OnTheaterMode")
	public void removeOnTheaterModeListener(ActiveXEventListener l);

	/**
	 * Adds WindowSetResizable event listener
	 * @param l Event listener
	 */
	@AddListener("WindowSetResizable")
	public void addWindowSetResizableListener(ActiveXEventListener l);

	/**
	 * Removes WindowSetResizable event listener
	 * @param l Event listener
	 */
	@RemoveListener("WindowSetResizable")
	public void removeWindowSetResizableListener(ActiveXEventListener l);

	/**
	 * Adds WindowSetLeft event listener
	 * @param l Event listener
	 */
	@AddListener("WindowSetLeft")
	public void addWindowSetLeftListener(ActiveXEventListener l);

	/**
	 * Removes WindowSetLeft event listener
	 * @param l Event listener
	 */
	@RemoveListener("WindowSetLeft")
	public void removeWindowSetLeftListener(ActiveXEventListener l);

	/**
	 * Adds WindowSetTop event listener
	 * @param l Event listener
	 */
	@AddListener("WindowSetTop")
	public void addWindowSetTopListener(ActiveXEventListener l);

	/**
	 * Removes WindowSetTop event listener
	 * @param l Event listener
	 */
	@RemoveListener("WindowSetTop")
	public void removeWindowSetTopListener(ActiveXEventListener l);

	/**
	 * Adds WindowSetWidth event listener
	 * @param l Event listener
	 */
	@AddListener("WindowSetWidth")
	public void addWindowSetWidthListener(ActiveXEventListener l);

	/**
	 * Removes WindowSetWidth event listener
	 * @param l Event listener
	 */
	@RemoveListener("WindowSetWidth")
	public void removeWindowSetWidthListener(ActiveXEventListener l);

	/**
	 * Adds WindowSetHeight event listener
	 * @param l Event listener
	 */
	@AddListener("WindowSetHeight")
	public void addWindowSetHeightListener(ActiveXEventListener l);

	/**
	 * Removes WindowSetHeight event listener
	 * @param l Event listener
	 */
	@RemoveListener("WindowSetHeight")
	public void removeWindowSetHeightListener(ActiveXEventListener l);

	/**
	 * Adds WindowClosing event listener
	 * @param l Event listener
	 */
	@AddListener("WindowClosing")
	public void addWindowClosingListener(ActiveXEventListener l);

	/**
	 * Removes WindowClosing event listener
	 * @param l Event listener
	 */
	@RemoveListener("WindowClosing")
	public void removeWindowClosingListener(ActiveXEventListener l);

	/**
	 * Adds ClientToHostWindow event listener
	 * @param l Event listener
	 */
	@AddListener("ClientToHostWindow")
	public void addClientToHostWindowListener(ActiveXEventListener l);

	/**
	 * Removes ClientToHostWindow event listener
	 * @param l Event listener
	 */
	@RemoveListener("ClientToHostWindow")
	public void removeClientToHostWindowListener(ActiveXEventListener l);

	/**
	 * Adds SetSecureLockIcon event listener
	 * @param l Event listener
	 */
	@AddListener("SetSecureLockIcon")
	public void addSetSecureLockIconListener(ActiveXEventListener l);

	/**
	 * Removes SetSecureLockIcon event listener
	 * @param l Event listener
	 */
	@RemoveListener("SetSecureLockIcon")
	public void removeSetSecureLockIconListener(ActiveXEventListener l);

	/**
	 * Adds FileDownload event listener
	 * @param l Event listener
	 */
	@AddListener("FileDownload")
	public void addFileDownloadListener(ActiveXEventListener l);

	/**
	 * Removes FileDownload event listener
	 * @param l Event listener
	 */
	@RemoveListener("FileDownload")
	public void removeFileDownloadListener(ActiveXEventListener l);

	/**
	 * Adds NavigateError event listener
	 * @param l Event listener
	 */
	@AddListener("NavigateError")
	public void addNavigateErrorListener(ActiveXEventListener l);

	/**
	 * Removes NavigateError event listener
	 * @param l Event listener
	 */
	@RemoveListener("NavigateError")
	public void removeNavigateErrorListener(ActiveXEventListener l);

	/**
	 * Adds PrintTemplateInstantiation event listener
	 * @param l Event listener
	 */
	@AddListener("PrintTemplateInstantiation")
	public void addPrintTemplateInstantiationListener(ActiveXEventListener l);

	/**
	 * Removes PrintTemplateInstantiation event listener
	 * @param l Event listener
	 */
	@RemoveListener("PrintTemplateInstantiation")
	public void removePrintTemplateInstantiationListener(ActiveXEventListener l);

	/**
	 * Adds PrintTemplateTeardown event listener
	 * @param l Event listener
	 */
	@AddListener("PrintTemplateTeardown")
	public void addPrintTemplateTeardownListener(ActiveXEventListener l);

	/**
	 * Removes PrintTemplateTeardown event listener
	 * @param l Event listener
	 */
	@RemoveListener("PrintTemplateTeardown")
	public void removePrintTemplateTeardownListener(ActiveXEventListener l);

	/**
	 * Adds UpdatePageStatus event listener
	 * @param l Event listener
	 */
	@AddListener("UpdatePageStatus")
	public void addUpdatePageStatusListener(ActiveXEventListener l);

	/**
	 * Removes UpdatePageStatus event listener
	 * @param l Event listener
	 */
	@RemoveListener("UpdatePageStatus")
	public void removeUpdatePageStatusListener(ActiveXEventListener l);

	/**
	 * Adds PrivacyImpactedStateChange event listener
	 * @param l Event listener
	 */
	@AddListener("PrivacyImpactedStateChange")
	public void addPrivacyImpactedStateChangeListener(ActiveXEventListener l);

	/**
	 * Removes PrivacyImpactedStateChange event listener
	 * @param l Event listener
	 */
	@RemoveListener("PrivacyImpactedStateChange")
	public void removePrivacyImpactedStateChangeListener(ActiveXEventListener l);

	/**
	 * Adds NewWindow3 event listener
	 * @param l Event listener
	 */
	@AddListener("NewWindow3")
	public void addNewWindow3Listener(ActiveXEventListener l);

	/**
	 * Removes NewWindow3 event listener
	 * @param l Event listener
	 */
	@RemoveListener("NewWindow3")
	public void removeNewWindow3Listener(ActiveXEventListener l);

	/**
	 * Adds SetPhishingFilterStatus event listener
	 * @param l Event listener
	 */
	@AddListener("SetPhishingFilterStatus")
	public void addSetPhishingFilterStatusListener(ActiveXEventListener l);

	/**
	 * Removes SetPhishingFilterStatus event listener
	 * @param l Event listener
	 */
	@RemoveListener("SetPhishingFilterStatus")
	public void removeSetPhishingFilterStatusListener(ActiveXEventListener l);

	/**
	 * Adds WindowStateChanged event listener
	 * @param l Event listener
	 */
	@AddListener("WindowStateChanged")
	public void addWindowStateChangedListener(ActiveXEventListener l);

	/**
	 * Removes WindowStateChanged event listener
	 * @param l Event listener
	 */
	@RemoveListener("WindowStateChanged")
	public void removeWindowStateChangedListener(ActiveXEventListener l);

	/**
	 * Adds NewProcess event listener
	 * @param l Event listener
	 */
	@AddListener("NewProcess")
	public void addNewProcessListener(ActiveXEventListener l);

	/**
	 * Removes NewProcess event listener
	 * @param l Event listener
	 */
	@RemoveListener("NewProcess")
	public void removeNewProcessListener(ActiveXEventListener l);

	/**
	 * Adds ThirdPartyUrlBlocked event listener
	 * @param l Event listener
	 */
	@AddListener("ThirdPartyUrlBlocked")
	public void addThirdPartyUrlBlockedListener(ActiveXEventListener l);

	/**
	 * Removes ThirdPartyUrlBlocked event listener
	 * @param l Event listener
	 */
	@RemoveListener("ThirdPartyUrlBlocked")
	public void removeThirdPartyUrlBlockedListener(ActiveXEventListener l);

	/**
	 * Adds RedirectXDomainBlocked event listener
	 * @param l Event listener
	 */
	@AddListener("RedirectXDomainBlocked")
	public void addRedirectXDomainBlockedListener(ActiveXEventListener l);

	/**
	 * Removes RedirectXDomainBlocked event listener
	 * @param l Event listener
	 */
	@RemoveListener("RedirectXDomainBlocked")
	public void removeRedirectXDomainBlockedListener(ActiveXEventListener l);

	/**
	 * Adds BeforeScriptExecute event listener
	 * @param l Event listener
	 */
	@AddListener("BeforeScriptExecute")
	public void addBeforeScriptExecuteListener(ActiveXEventListener l);

	/**
	 * Removes BeforeScriptExecute event listener
	 * @param l Event listener
	 */
	@RemoveListener("BeforeScriptExecute")
	public void removeBeforeScriptExecuteListener(ActiveXEventListener l);

	/**
	 * Adds WebWorkerStarted event listener
	 * @param l Event listener
	 */
	@AddListener("WebWorkerStarted")
	public void addWebWorkerStartedListener(ActiveXEventListener l);

	/**
	 * Removes WebWorkerStarted event listener
	 * @param l Event listener
	 */
	@RemoveListener("WebWorkerStarted")
	public void removeWebWorkerStartedListener(ActiveXEventListener l);

	/**
	 * Adds WebWorkerFinsihed event listener
	 * @param l Event listener
	 */
	@AddListener("WebWorkerFinsihed")
	public void addWebWorkerFinsihedListener(ActiveXEventListener l);

	/**
	 * Removes WebWorkerFinsihed event listener
	 * @param l Event listener
	 */
	@RemoveListener("WebWorkerFinsihed")
	public void removeWebWorkerFinsihedListener(ActiveXEventListener l);

	/**
	 * Go home/start page.
	 * 
	 */
	public void GoHome();


	/**
	 * Set BrowserBar to Clsid
	 * 
	 * @param pvaClsid
	 * @param pvarShow
	 * @param pvarSize
	 */
	public void ShowBrowserBar(Object pvaClsid, Object pvarShow, Object pvarSize);


	/**
	 * Set BrowserBar to Clsid
	 * 
	 * @param pvaClsid
	 * @param pvarShow
	 */
	public void ShowBrowserBar(Object pvaClsid, Object pvarShow);


	/**
	 * Set BrowserBar to Clsid
	 * 
	 * @param pvaClsid
	 */
	public void ShowBrowserBar(Object pvaClsid);


	/**
	 * Navigates to the next item in the history list.
	 * 
	 */
	public void GoForward();


	/**
	 * IOleCommandTarget::QueryStatus
	 * 
	 * @param cmdID
	 * @return
	 */
	public int QueryStatusWB(int cmdID);


	/**
	 * Stops opening a file.
	 * 
	 */
	public void Stop();


	/**
	 * Exits application and closes the open document.
	 * 
	 */
	public void Quit();


	/**
	 * Navigates to the previous item in the history list.
	 * 
	 */
	public void GoBack();


	/**
	 * Refresh the currently viewed page.
	 * 
	 * @param Level
	 */
	public void Refresh2(Object Level);


	/**
	 * Refresh the currently viewed page.
	 * 
	 */
	public void Refresh2();


	/**
	 * Refresh the currently viewed page.
	 * 
	 */
	public void Refresh();


	/**
	 * Navigates to a URL or file or pidl.
	 * 
	 * @param URL
	 * @param Flags
	 * @param TargetFrameName
	 * @param PostData
	 * @param Headers
	 */
	public void Navigate2(Object URL, Object Flags, Object TargetFrameName, Object PostData, Object Headers);


	/**
	 * Navigates to a URL or file or pidl.
	 * 
	 * @param URL
	 * @param Flags
	 * @param TargetFrameName
	 * @param PostData
	 */
	public void Navigate2(Object URL, Object Flags, Object TargetFrameName, Object PostData);


	/**
	 * Navigates to a URL or file or pidl.
	 * 
	 * @param URL
	 * @param Flags
	 * @param TargetFrameName
	 */
	public void Navigate2(Object URL, Object Flags, Object TargetFrameName);


	/**
	 * Navigates to a URL or file or pidl.
	 * 
	 * @param URL
	 * @param Flags
	 */
	public void Navigate2(Object URL, Object Flags);


	/**
	 * Navigates to a URL or file or pidl.
	 * 
	 * @param URL
	 */
	public void Navigate2(Object URL);


	/**
	 * IOleCommandTarget::Exec
	 * 
	 * @param cmdID
	 * @param cmdexecopt
	 * @param pvaIn
	 * @param pvaOut
	 */
	public void ExecWB(int cmdID, int cmdexecopt, Object pvaIn, Object pvaOut);


	/**
	 * IOleCommandTarget::Exec
	 * 
	 * @param cmdID
	 * @param cmdexecopt
	 * @param pvaIn
	 */
	public void ExecWB(int cmdID, int cmdexecopt, Object pvaIn);


	/**
	 * IOleCommandTarget::Exec
	 * 
	 * @param cmdID
	 * @param cmdexecopt
	 */
	public void ExecWB(int cmdID, int cmdexecopt);


	/**
	 * Go Search Page.
	 * 
	 */
	public void GoSearch();


	/**
	 * Converts client sizes into window sizes.
	 * 
	 * @param pcx
	 * @param pcy
	 */
	public void ClientToWindow(Reference<Integer> pcx, Reference<Integer> pcy);


	/**
	 * Associates vtValue with the name szProperty in the context of the object.
	 * 
	 * @param Property
	 * @param vtValue
	 */
	public void PutProperty(String Property, Object vtValue);


	/**
	 * Navigates to a URL or file.
	 * 
	 * @param URL
	 * @param Flags
	 * @param TargetFrameName
	 * @param PostData
	 * @param Headers
	 */
	public void Navigate(String URL, Object Flags, Object TargetFrameName, Object PostData, Object Headers);


	/**
	 * Navigates to a URL or file.
	 * 
	 * @param URL
	 * @param Flags
	 * @param TargetFrameName
	 * @param PostData
	 */
	public void Navigate(String URL, Object Flags, Object TargetFrameName, Object PostData);


	/**
	 * Navigates to a URL or file.
	 * 
	 * @param URL
	 * @param Flags
	 * @param TargetFrameName
	 */
	public void Navigate(String URL, Object Flags, Object TargetFrameName);


	/**
	 * Navigates to a URL or file.
	 * 
	 * @param URL
	 * @param Flags
	 */
	public void Navigate(String URL, Object Flags);


	/**
	 * Navigates to a URL or file.
	 * 
	 * @param URL
	 */
	public void Navigate(String URL);


	/**
	 * Retrieve the Associated value for the property vtValue in the context of the object.
	 * 
	 * @param Property
	 * @return
	 */
	public Object GetProperty(String Property);


	/**
	 * Getter for property Application
	 * 
	 * @return Application value
	 */
	@Getter("Application")
	public IWebBrowser getApplication();

	/**
	 * Getter for property Parent
	 * 
	 * @return Parent value
	 */
	@Getter("Parent")
	public IWebBrowser getParent();

	/**
	 * Getter for property Container
	 * 
	 * @return Container value
	 */
	@Getter("Container")
	public IWebBrowser getContainer();

	/**
	 * Getter for property Document
	 * 
	 * @return Document value
	 */
	@Getter("Document")
	public IWebBrowser getDocument();

	/**
	 * Getter for property TopLevelContainer
	 * 
	 * @return TopLevelContainer value
	 */
	@Getter("TopLevelContainer")
	public boolean getTopLevelContainer();

	/**
	 * Getter for property Type
	 * 
	 * @return Type value
	 */
	@Getter("Type")
	public String getType();

	/**
	 * Getter for property Left
	 * 
	 * @return Left value
	 */
	@Getter("Left")
	public int getLeft();

	/**
	 * Setter for property Left
	 * 
	 * @param value New Left value
	 */
	@Setter("Left")
	public void setLeft(int value);

	/**
	 * Getter for property Top
	 * 
	 * @return Top value
	 */
	@Getter("Top")
	public int getTop();

	/**
	 * Setter for property Top
	 * 
	 * @param value New Top value
	 */
	@Setter("Top")
	public void setTop(int value);

	/**
	 * Getter for property Width
	 * 
	 * @return Width value
	 */
	@Getter("Width")
	public int getWidth();

	/**
	 * Setter for property Width
	 * 
	 * @param value New Width value
	 */
	@Setter("Width")
	public void setWidth(int value);

	/**
	 * Getter for property Height
	 * 
	 * @return Height value
	 */
	@Getter("Height")
	public int getHeight();

	/**
	 * Setter for property Height
	 * 
	 * @param value New Height value
	 */
	@Setter("Height")
	public void setHeight(int value);

	/**
	 * Getter for property LocationName
	 * 
	 * @return LocationName value
	 */
	@Getter("LocationName")
	public String getLocationName();

	/**
	 * Getter for property LocationURL
	 * 
	 * @return LocationURL value
	 */
	@Getter("LocationURL")
	public String getLocationURL();

	/**
	 * Getter for property Busy
	 * 
	 * @return Busy value
	 */
	@Getter("Busy")
	public boolean getBusy();

	/**
	 * Getter for property Name
	 * 
	 * @return Name value
	 */
	@Getter("Name")
	public String getName();

	/**
	 * Getter for property HWND
	 * 
	 * @return HWND value
	 */
	@Getter("HWND")
	public int getHWND();

	/**
	 * Getter for property FullName
	 * 
	 * @return FullName value
	 */
	@Getter("FullName")
	public String getFullName();

	/**
	 * Getter for property Path
	 * 
	 * @return Path value
	 */
	@Getter("Path")
	public String getPath();

	/**
	 * Getter for property Visible
	 * 
	 * @return Visible value
	 */
	@Getter("Visible")
	public boolean getVisible();

	/**
	 * Setter for property Visible
	 * 
	 * @param value New Visible value
	 */
	@Setter("Visible")
	public void setVisible(boolean value);

	/**
	 * Getter for property StatusBar
	 * 
	 * @return StatusBar value
	 */
	@Getter("StatusBar")
	public boolean getStatusBar();

	/**
	 * Setter for property StatusBar
	 * 
	 * @param value New StatusBar value
	 */
	@Setter("StatusBar")
	public void setStatusBar(boolean value);

	/**
	 * Getter for property StatusText
	 * 
	 * @return StatusText value
	 */
	@Getter("StatusText")
	public String getStatusText();

	/**
	 * Setter for property StatusText
	 * 
	 * @param value New StatusText value
	 */
	@Setter("StatusText")
	public void setStatusText(String value);

	/**
	 * Getter for property ToolBar
	 * 
	 * @return ToolBar value
	 */
	@Getter("ToolBar")
	public int getToolBar();

	/**
	 * Setter for property ToolBar
	 * 
	 * @param value New ToolBar value
	 */
	@Setter("ToolBar")
	public void setToolBar(int value);

	/**
	 * Getter for property MenuBar
	 * 
	 * @return MenuBar value
	 */
	@Getter("MenuBar")
	public boolean getMenuBar();

	/**
	 * Setter for property MenuBar
	 * 
	 * @param value New MenuBar value
	 */
	@Setter("MenuBar")
	public void setMenuBar(boolean value);

	/**
	 * Getter for property FullScreen
	 * 
	 * @return FullScreen value
	 */
	@Getter("FullScreen")
	public boolean getFullScreen();

	/**
	 * Setter for property FullScreen
	 * 
	 * @param value New FullScreen value
	 */
	@Setter("FullScreen")
	public void setFullScreen(boolean value);

	/**
	 * Getter for property ReadyState
	 * 
	 * @return ReadyState value
	 */
	@Getter("ReadyState")
	public int getReadyState();

	/**
	 * Getter for property Offline
	 * 
	 * @return Offline value
	 */
	@Getter("Offline")
	public boolean getOffline();

	/**
	 * Setter for property Offline
	 * 
	 * @param value New Offline value
	 */
	@Setter("Offline")
	public void setOffline(boolean value);

	/**
	 * Getter for property Silent
	 * 
	 * @return Silent value
	 */
	@Getter("Silent")
	public boolean getSilent();

	/**
	 * Setter for property Silent
	 * 
	 * @param value New Silent value
	 */
	@Setter("Silent")
	public void setSilent(boolean value);

	/**
	 * Getter for property RegisterAsBrowser
	 * 
	 * @return RegisterAsBrowser value
	 */
	@Getter("RegisterAsBrowser")
	public boolean getRegisterAsBrowser();

	/**
	 * Setter for property RegisterAsBrowser
	 * 
	 * @param value New RegisterAsBrowser value
	 */
	@Setter("RegisterAsBrowser")
	public void setRegisterAsBrowser(boolean value);

	/**
	 * Getter for property RegisterAsDropTarget
	 * 
	 * @return RegisterAsDropTarget value
	 */
	@Getter("RegisterAsDropTarget")
	public boolean getRegisterAsDropTarget();

	/**
	 * Setter for property RegisterAsDropTarget
	 * 
	 * @param value New RegisterAsDropTarget value
	 */
	@Setter("RegisterAsDropTarget")
	public void setRegisterAsDropTarget(boolean value);

	/**
	 * Getter for property TheaterMode
	 * 
	 * @return TheaterMode value
	 */
	@Getter("TheaterMode")
	public boolean getTheaterMode();

	/**
	 * Setter for property TheaterMode
	 * 
	 * @param value New TheaterMode value
	 */
	@Setter("TheaterMode")
	public void setTheaterMode(boolean value);

	/**
	 * Getter for property AddressBar
	 * 
	 * @return AddressBar value
	 */
	@Getter("AddressBar")
	public boolean getAddressBar();

	/**
	 * Setter for property AddressBar
	 * 
	 * @param value New AddressBar value
	 */
	@Setter("AddressBar")
	public void setAddressBar(boolean value);

	/**
	 * Getter for property Resizable
	 * 
	 * @return Resizable value
	 */
	@Getter("Resizable")
	public boolean getResizable();

	/**
	 * Setter for property Resizable
	 * 
	 * @param value New Resizable value
	 */
	@Setter("Resizable")
	public void setResizable(boolean value);
}