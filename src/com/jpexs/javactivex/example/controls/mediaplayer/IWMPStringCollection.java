package com.jpexs.javactivex.example.controls.mediaplayer;

import com.jpexs.javactivex.*;

/**
 * IWMPStringCollection: Public interface.
 */
@GUID(value="{4A976298-8C0D-11D3-B389-00C04F68574B}", base="{6BF52A52-394A-11D3-B153-00C04F79FAA6}")
public interface IWMPStringCollection {


	/**
	 * Returns the string at the given index
	 * 
	 * @param lIndex
	 * @return
	 */
	public String Item(int lIndex);


	/**
	 * Getter for property count
	 * 
	 * @return count value
	 */
	@Getter("count")
	public int getCount();
}