package com.jpexs.javactivex.example.controls.mediaplayer;

import com.jpexs.javactivex.*;

/**
 * IWMPErrorItem: Public interface.
 */
@GUID(value="{3614C646-3B3B-4DE7-A81E-930E3F2127B3}", base="{6BF52A52-394A-11D3-B153-00C04F79FAA6}")
public interface IWMPErrorItem {


	/**
	 * Returns context information for the error
	 * 
	 * @return
	 */
	public Object errorContext();


	/**
	 * Returns a description of the error
	 * 
	 * @return
	 */
	public String errorDescription();


	/**
	 * Returns the error code
	 * 
	 * @return
	 */
	public int errorCode();


	/**
	 * Returns remedy code for the error
	 * 
	 * @return
	 */
	public int remedy();


	/**
	 * Returns a custom url for this error (if avail)
	 * 
	 * @return
	 */
	public String customUrl();

}