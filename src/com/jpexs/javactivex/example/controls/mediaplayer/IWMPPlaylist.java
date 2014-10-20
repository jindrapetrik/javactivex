package com.jpexs.javactivex.example.controls.mediaplayer;

import com.jpexs.javactivex.*;

/**
 * IWMPPlaylist: Public interface.
 */
@GUID(value="{D5F0F4F1-130C-11D3-B14E-00C04F79FAA6}", base="{6BF52A52-394A-11D3-B153-00C04F79FAA6}")
public interface IWMPPlaylist {


	/**
	 * Removes the specified item from the playlist
	 * 
	 * @param pIWMPMedia
	 */
	public void removeItem(IWMPMedia pIWMPMedia);


	/**
	 * Changes the location of an item in the playlist
	 * 
	 * @param lIndexOld
	 * @param lIndexNew
	 */
	public void moveItem(int lIndexOld, int lIndexNew);


	/**
	 * Removes all items from the playlist
	 * 
	 */
	public void clear();


	/**
	 * Returns the value of a playlist attribute
	 * 
	 * @param bstrName
	 * @return
	 */
	public String getItemInfo(String bstrName);


	/**
	 * Adds an item to the end of the playlist
	 * 
	 * @param pIWMPMedia
	 */
	public void appendItem(IWMPMedia pIWMPMedia);


	/**
	 * Inserts an item into the playlist at the specified location
	 * 
	 * @param lIndex
	 * @param pIWMPMedia
	 */
	public void insertItem(int lIndex, IWMPMedia pIWMPMedia);


	/**
	 * Sets the value of a playlist attribute
	 * 
	 * @param bstrName
	 * @param bstrValue
	 */
	public void setItemInfo(String bstrName, String bstrValue);


	/**
	 * Getter for property count
	 * 
	 * @return count value
	 */
	@Getter("count")
	public int getCount();

	/**
	 * Getter for property name
	 * 
	 * @return name value
	 */
	@Getter("name")
	public String getName();

	/**
	 * Setter for property name
	 * 
	 * @param value New name value
	 */
	@Setter("name")
	public void setName(String value);

	/**
	 * Getter for property attributeCount
	 * 
	 * @return attributeCount value
	 */
	@Getter("attributeCount")
	public int getAttributeCount();
}