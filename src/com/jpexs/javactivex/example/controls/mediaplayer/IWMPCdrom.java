package com.jpexs.javactivex.example.controls.mediaplayer;

import com.jpexs.javactivex.*;

/**
 * IWMPCdrom: Public interface.
 */
@GUID(value="{CFAB6E98-8730-11D3-B388-00C04F68574B}", base="{6BF52A52-394A-11D3-B153-00C04F79FAA6}")
public interface IWMPCdrom {


	/**
	 * Eject the CD in the CDROM drive
	 * 
	 */
	public void eject();


	/**
	 * Getter for property driveSpecifier
	 * 
	 * @return driveSpecifier value
	 */
	@Getter("driveSpecifier")
	public String getDriveSpecifier();

	/**
	 * Getter for property Playlist
	 * 
	 * @return Playlist value
	 */
	@Getter("Playlist")
	public IWMPPlaylist getPlaylist();
}