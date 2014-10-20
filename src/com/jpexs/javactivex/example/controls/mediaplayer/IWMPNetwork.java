package com.jpexs.javactivex.example.controls.mediaplayer;

import com.jpexs.javactivex.*;

/**
 * IWMPNetwork: Public interface.
 */
@GUID(value="{EC21B779-EDEF-462D-BBA4-AD9DDE2B29A7}", base="{6BF52A52-394A-11D3-B153-00C04F79FAA6}")
public interface IWMPNetwork {


	/**
	 * Returns the proxy settings for the specified protocol
	 * 
	 * @param bstrProtocol
	 * @return
	 */
	public int getProxySettings(String bstrProtocol);


	/**
	 * Returns the proxy name for the specified protocol
	 * 
	 * @param bstrProtocol
	 * @return
	 */
	public String getProxyName(String bstrProtocol);


	/**
	 * Sets the proxy name for the specified protocol
	 * 
	 * @param bstrProtocol
	 * @param bstrProxyName
	 */
	public void setProxyName(String bstrProtocol, String bstrProxyName);


	/**
	 * Sets the proxy port for the specified protocol
	 * 
	 * @param bstrProtocol
	 * @param lProxyPort
	 */
	public void setProxyPort(String bstrProtocol, int lProxyPort);


	/**
	 * Sets whether or not to by pass the proxy for local addresses
	 * 
	 * @param bstrProtocol
	 * @param fBypassForLocal
	 */
	public void setProxyBypassForLocal(String bstrProtocol, boolean fBypassForLocal);


	/**
	 * Returns the proxy exception list for the specified protocol
	 * 
	 * @param bstrProtocol
	 * @return
	 */
	public String getProxyExceptionList(String bstrProtocol);


	/**
	 * Sets the proxy exception list for the specified protocol
	 * 
	 * @param bstrProtocol
	 * @param pbstrExceptionList
	 */
	public void setProxyExceptionList(String bstrProtocol, String pbstrExceptionList);


	/**
	 * Returns whether or not to bypass the proxy for local addresses
	 * 
	 * @param bstrProtocol
	 * @return
	 */
	public boolean getProxyBypassForLocal(String bstrProtocol);


	/**
	 * Returns the proxy port for the specified protocol
	 * 
	 * @param bstrProtocol
	 * @return
	 */
	public int getProxyPort(String bstrProtocol);


	/**
	 * Sets the proxy settings for the specified protocol
	 * 
	 * @param bstrProtocol
	 * @param lProxySetting
	 */
	public void setProxySettings(String bstrProtocol, int lProxySetting);


	/**
	 * Getter for property bandWidth
	 * 
	 * @return bandWidth value
	 */
	@Getter("bandWidth")
	public int getBandWidth();

	/**
	 * Getter for property recoveredPackets
	 * 
	 * @return recoveredPackets value
	 */
	@Getter("recoveredPackets")
	public int getRecoveredPackets();

	/**
	 * Getter for property sourceProtocol
	 * 
	 * @return sourceProtocol value
	 */
	@Getter("sourceProtocol")
	public String getSourceProtocol();

	/**
	 * Getter for property receivedPackets
	 * 
	 * @return receivedPackets value
	 */
	@Getter("receivedPackets")
	public int getReceivedPackets();

	/**
	 * Getter for property lostPackets
	 * 
	 * @return lostPackets value
	 */
	@Getter("lostPackets")
	public int getLostPackets();

	/**
	 * Getter for property receptionQuality
	 * 
	 * @return receptionQuality value
	 */
	@Getter("receptionQuality")
	public int getReceptionQuality();

	/**
	 * Getter for property bufferingCount
	 * 
	 * @return bufferingCount value
	 */
	@Getter("bufferingCount")
	public int getBufferingCount();

	/**
	 * Getter for property bufferingProgress
	 * 
	 * @return bufferingProgress value
	 */
	@Getter("bufferingProgress")
	public int getBufferingProgress();

	/**
	 * Getter for property bufferingTime
	 * 
	 * @return bufferingTime value
	 */
	@Getter("bufferingTime")
	public int getBufferingTime();

	/**
	 * Setter for property bufferingTime
	 * 
	 * @param value New bufferingTime value
	 */
	@Setter("bufferingTime")
	public void setBufferingTime(int value);

	/**
	 * Getter for property frameRate
	 * 
	 * @return frameRate value
	 */
	@Getter("frameRate")
	public int getFrameRate();

	/**
	 * Getter for property maxBitRate
	 * 
	 * @return maxBitRate value
	 */
	@Getter("maxBitRate")
	public int getMaxBitRate();

	/**
	 * Getter for property bitRate
	 * 
	 * @return bitRate value
	 */
	@Getter("bitRate")
	public int getBitRate();

	/**
	 * Getter for property maxBandwidth
	 * 
	 * @return maxBandwidth value
	 */
	@Getter("maxBandwidth")
	public int getMaxBandwidth();

	/**
	 * Setter for property maxBandwidth
	 * 
	 * @param value New maxBandwidth value
	 */
	@Setter("maxBandwidth")
	public void setMaxBandwidth(int value);

	/**
	 * Getter for property downloadProgress
	 * 
	 * @return downloadProgress value
	 */
	@Getter("downloadProgress")
	public int getDownloadProgress();

	/**
	 * Getter for property encodedFrameRate
	 * 
	 * @return encodedFrameRate value
	 */
	@Getter("encodedFrameRate")
	public int getEncodedFrameRate();

	/**
	 * Getter for property framesSkipped
	 * 
	 * @return framesSkipped value
	 */
	@Getter("framesSkipped")
	public int getFramesSkipped();
}