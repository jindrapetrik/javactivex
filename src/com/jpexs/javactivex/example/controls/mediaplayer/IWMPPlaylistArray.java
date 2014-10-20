package com.jpexs.javactivex.example.controls.mediaplayer;

import com.jpexs.javactivex.*;

/**
 * IWMPPlaylistArray: Public interface.
 */
@GUID(value="{679409C0-99F7-11D3-9FB7-00105AA620BB}", base="{6BF52A52-394A-11D3-B153-00C04F79FAA6}")
public interface IWMPPlaylistArray {


	/**
	 * Returns the playlist object at the given index
	 * 
	 * @param lIndex
	 * @return
	 */
	public IWMPPlaylist Item(int lIndex);


	/**
	 * Getter for property count
	 * 
	 * @return count value
	 */
	@Getter("count")
	public int getCount();
}