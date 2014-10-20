package com.jpexs.javactivex.example.controls.mediaplayer;

import com.jpexs.javactivex.*;

/**
 * IWMPSettings: Public interface.
 */
@GUID(value="{9104D1AB-80C9-4FED-ABF0-2E6417A6DF14}", base="{6BF52A52-394A-11D3-B153-00C04F79FAA6}")
public interface IWMPSettings {


	/**
	 * Sets the mode of the playlist
	 * 
	 * @param bstrMode
	 * @param varfMode
	 */
	public void setMode(String bstrMode, boolean varfMode);


	/**
	 * Returns the mode of the playlist
	 * 
	 * @param bstrMode
	 * @return
	 */
	public boolean getMode(String bstrMode);


	/**
	 * Getter for property autoStart
	 * 
	 * @return autoStart value
	 */
	@Getter("autoStart")
	public boolean getAutoStart();

	/**
	 * Setter for property autoStart
	 * 
	 * @param value New autoStart value
	 */
	@Setter("autoStart")
	public void setAutoStart(boolean value);

	/**
	 * Getter for property baseURL
	 * 
	 * @return baseURL value
	 */
	@Getter("baseURL")
	public String getBaseURL();

	/**
	 * Setter for property baseURL
	 * 
	 * @param value New baseURL value
	 */
	@Setter("baseURL")
	public void setBaseURL(String value);

	/**
	 * Getter for property defaultFrame
	 * 
	 * @return defaultFrame value
	 */
	@Getter("defaultFrame")
	public String getDefaultFrame();

	/**
	 * Setter for property defaultFrame
	 * 
	 * @param value New defaultFrame value
	 */
	@Setter("defaultFrame")
	public void setDefaultFrame(String value);

	/**
	 * Getter for property invokeURLs
	 * 
	 * @return invokeURLs value
	 */
	@Getter("invokeURLs")
	public boolean getInvokeURLs();

	/**
	 * Setter for property invokeURLs
	 * 
	 * @param value New invokeURLs value
	 */
	@Setter("invokeURLs")
	public void setInvokeURLs(boolean value);

	/**
	 * Getter for property mute
	 * 
	 * @return mute value
	 */
	@Getter("mute")
	public boolean getMute();

	/**
	 * Setter for property mute
	 * 
	 * @param value New mute value
	 */
	@Setter("mute")
	public void setMute(boolean value);

	/**
	 * Getter for property playCount
	 * 
	 * @return playCount value
	 */
	@Getter("playCount")
	public int getPlayCount();

	/**
	 * Setter for property playCount
	 * 
	 * @param value New playCount value
	 */
	@Setter("playCount")
	public void setPlayCount(int value);

	/**
	 * Getter for property rate
	 * 
	 * @return rate value
	 */
	@Getter("rate")
	public double getRate();

	/**
	 * Setter for property rate
	 * 
	 * @param value New rate value
	 */
	@Setter("rate")
	public void setRate(double value);

	/**
	 * Getter for property balance
	 * 
	 * @return balance value
	 */
	@Getter("balance")
	public int getBalance();

	/**
	 * Setter for property balance
	 * 
	 * @param value New balance value
	 */
	@Setter("balance")
	public void setBalance(int value);

	/**
	 * Getter for property volume
	 * 
	 * @return volume value
	 */
	@Getter("volume")
	public int getVolume();

	/**
	 * Setter for property volume
	 * 
	 * @param value New volume value
	 */
	@Setter("volume")
	public void setVolume(int value);

	/**
	 * Getter for property enableErrorDialogs
	 * 
	 * @return enableErrorDialogs value
	 */
	@Getter("enableErrorDialogs")
	public boolean getEnableErrorDialogs();

	/**
	 * Setter for property enableErrorDialogs
	 * 
	 * @param value New enableErrorDialogs value
	 */
	@Setter("enableErrorDialogs")
	public void setEnableErrorDialogs(boolean value);
}