package com.jpexs.javactivex.example.controls.mediaplayer;

import com.jpexs.javactivex.*;

/**
 * IWMPMediaCollection: Public interface.
 */
@GUID(value="{8363BC22-B4B4-4B19-989D-1CD765749DD1}", base="{6BF52A52-394A-11D3-B153-00C04F79FAA6}")
public interface IWMPMediaCollection {


	/**
	 * Creates a new media object
	 * 
	 * @param bstrURL
	 * @return
	 */
	public IWMPMedia add(String bstrURL);


	/**
	 * Returns the string collection associated with an attribute
	 * 
	 * @param bstrAttribute
	 * @param bstrMediaType
	 * @return
	 */
	public IWMPStringCollection getAttributeStringCollection(String bstrAttribute, String bstrMediaType);


	/**
	 * Returns a collection of all the items
	 * 
	 * @return
	 */
	public IWMPPlaylist getAll();


	/**
	 * Gets an atom associated with an item name which can be requested from an IWMPMedia out of this collection via getItemInfoByAtom
	 * 
	 * @param bstrItemName
	 * @return
	 */
	public int getMediaAtom(String bstrItemName);


	/**
	 * Returns a collection of items by a given author
	 * 
	 * @param bstrAuthor
	 * @return
	 */
	public IWMPPlaylist getByAuthor(String bstrAuthor);


	/**
	 * Removes an item from the media collection
	 * 
	 * @param pItem
	 * @param varfDeleteFile
	 */
	public void remove(IWMPMedia pItem, boolean varfDeleteFile);


	/**
	 * Returns a collection of items from the given album
	 * 
	 * @param bstrAlbum
	 * @return
	 */
	public IWMPPlaylist getByAlbum(String bstrAlbum);


	/**
	 * Returns a collection of items with the given genre
	 * 
	 * @param bstrGenre
	 * @return
	 */
	public IWMPPlaylist getByGenre(String bstrGenre);


	/**
	 * Gets the deleted flag on a media object
	 * 
	 * @param pItem
	 * @return
	 */
	public boolean isDeleted(IWMPMedia pItem);


	/**
	 * Returns a collection of items with the given name
	 * 
	 * @param bstrName
	 * @return
	 */
	public IWMPPlaylist getByName(String bstrName);


	/**
	 * Returns a collection of items with the given attribute
	 * 
	 * @param bstrAttribute
	 * @param bstrValue
	 * @return
	 */
	public IWMPPlaylist getByAttribute(String bstrAttribute, String bstrValue);


	/**
	 * Sets the deleted flag on a media object
	 * 
	 * @param pItem
	 * @param varfIsDeleted
	 */
	public void setDeleted(IWMPMedia pItem, boolean varfIsDeleted);

}