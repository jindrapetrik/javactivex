package com.jpexs.javactivex.example.controls.mediaplayer;

import com.jpexs.javactivex.*;

/**
 * IWMPPlaylistCollection: Public interface.
 */
@GUID(value="{10A13217-23A7-439B-B1C0-D847C79B7774}", base="{6BF52A52-394A-11D3-B153-00C04F79FAA6}")
public interface IWMPPlaylistCollection {


	/**
	 * Returns a playlist array with all the playlists
	 * 
	 * @return
	 */
	public IWMPPlaylistArray getAll();


	/**
	 * Imports a playlist object into the library
	 * 
	 * @param pItem
	 * @return
	 */
	public IWMPPlaylist importPlaylist(IWMPPlaylist pItem);


	/**
	 * Removes an item from the playlist collection
	 * 
	 * @param pItem
	 */
	public void remove(IWMPPlaylist pItem);


	/**
	 * Gets the deleted flag on a playlist object
	 * 
	 * @param pItem
	 * @return
	 */
	public boolean isDeleted(IWMPPlaylist pItem);


	/**
	 * Returns a playlist array with playlists matching the given name
	 * 
	 * @param bstrName
	 * @return
	 */
	public IWMPPlaylistArray getByName(String bstrName);


	/**
	 * Creates a new playlist object
	 * 
	 * @param bstrName
	 * @return
	 */
	public IWMPPlaylist newPlaylist(String bstrName);


	/**
	 * Sets the deleted flag on a playlist object
	 * 
	 * @param pItem
	 * @param varfIsDeleted
	 */
	public void setDeleted(IWMPPlaylist pItem, boolean varfIsDeleted);

}