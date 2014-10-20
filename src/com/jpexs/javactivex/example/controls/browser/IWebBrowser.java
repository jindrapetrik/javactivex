package com.jpexs.javactivex.example.controls.browser;

import com.jpexs.javactivex.*;

/**
 * Web Browser interface
 */
@GUID(value="{EAB22AC1-30C1-11CF-A7EB-0000C05BAE0B}", base="{8856F961-340A-11D0-A96B-00C04FD705A2}")
public interface IWebBrowser {


	/**
	 * Go home/start page.
	 * 
	 */
	public void GoHome();


	/**
	 * Navigates to the next item in the history list.
	 * 
	 */
	public void GoForward();


	/**
	 * Stops opening a file.
	 * 
	 */
	public void Stop();


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
	 * Go Search Page.
	 * 
	 */
	public void GoSearch();


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
}