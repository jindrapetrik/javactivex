package com.jpexs.javactivex.example.controls.mediaplayer;

import com.jpexs.javactivex.*;

/**
 * IWMPError: Public interface.
 */
@GUID(value="{A12DCF7D-14AB-4C1B-A8CD-63909F06025B}", base="{6BF52A52-394A-11D3-B153-00C04F79FAA6}")
public interface IWMPError {


	/**
	 * Clears the error queue
	 * 
	 */
	public void clearErrorQueue();


	/**
	 * Launches WebHelp
	 * 
	 */
	public void webHelp();


	/**
	 * Getter for property errorCount
	 * 
	 * @return errorCount value
	 */
	@Getter("errorCount")
	public int getErrorCount();
}