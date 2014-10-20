package com.jpexs.javactivex.example.controls.mediaplayer;

import com.jpexs.javactivex.*;

/**
 * IWMPMedia: Public interface.
 */
@GUID(value="{94D55E95-3FAC-11D3-B155-00C04F79FAA6}", base="{6BF52A52-394A-11D3-B153-00C04F79FAA6}")
public interface IWMPMedia {


	/**
	 * Returns the time of a marker
	 * 
	 * @param MarkerNum
	 * @return
	 */
	public double getMarkerTime(int MarkerNum);


	/**
	 * Returns the name of a marker
	 * 
	 * @param MarkerNum
	 * @return
	 */
	public String getMarkerName(int MarkerNum);


	/**
	 * Returns the name of the attribute whose index has been specified
	 * 
	 * @param lIndex
	 * @return
	 */
	public String getAttributeName(int lIndex);


	/**
	 * Gets an item info by atom
	 * 
	 * @param lAtom
	 * @return
	 */
	public String getItemInfoByAtom(int lAtom);


	/**
	 * Returns the value of specified attribute for this media
	 * 
	 * @param bstrItemName
	 * @return
	 */
	public String getItemInfo(String bstrItemName);


	/**
	 * Is the attribute read only
	 * 
	 * @param bstrItemName
	 * @return
	 */
	public boolean isReadOnlyItem(String bstrItemName);


	/**
	 * Is the media a member of the given playlist
	 * 
	 * @param pPlaylist
	 * @return
	 */
	public boolean isMemberOf(IWMPPlaylist pPlaylist);


	/**
	 * Sets the value of specified attribute for this media
	 * 
	 * @param bstrItemName
	 * @param bstrVal
	 */
	public void setItemInfo(String bstrItemName, String bstrVal);


	/**
	 * Getter for property sourceURL
	 * 
	 * @return sourceURL value
	 */
	@Getter("sourceURL")
	public String getSourceURL();

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
	 * Getter for property imageSourceWidth
	 * 
	 * @return imageSourceWidth value
	 */
	@Getter("imageSourceWidth")
	public int getImageSourceWidth();

	/**
	 * Getter for property imageSourceHeight
	 * 
	 * @return imageSourceHeight value
	 */
	@Getter("imageSourceHeight")
	public int getImageSourceHeight();

	/**
	 * Getter for property markerCount
	 * 
	 * @return markerCount value
	 */
	@Getter("markerCount")
	public int getMarkerCount();

	/**
	 * Getter for property duration
	 * 
	 * @return duration value
	 */
	@Getter("duration")
	public double getDuration();

	/**
	 * Getter for property durationString
	 * 
	 * @return durationString value
	 */
	@Getter("durationString")
	public String getDurationString();

	/**
	 * Getter for property attributeCount
	 * 
	 * @return attributeCount value
	 */
	@Getter("attributeCount")
	public int getAttributeCount();
}