package com.jpexs.javactivex.example.controls.mediaplayer;

import com.jpexs.javactivex.*;

/**
 * IWMPCdromCollection: Public interface.
 */
@GUID(value="{EE4C8FE2-34B2-11D3-A3BF-006097C9B344}", base="{6BF52A52-394A-11D3-B153-00C04F79FAA6}")
public interface IWMPCdromCollection {


	/**
	 * Returns the CDROM object at the given index
	 * 
	 * @param lIndex
	 * @return
	 */
	public IWMPCdrom Item(int lIndex);


	/**
	 * Returns the CDROM object associated with a particular drive specifier, e.g. F:
	 * 
	 * @param bstrDriveSpecifier
	 * @return
	 */
	public IWMPCdrom getByDriveSpecifier(String bstrDriveSpecifier);


	/**
	 * Getter for property count
	 * 
	 * @return count value
	 */
	@Getter("count")
	public int getCount();
}