package com.jpexs.javactivex.example.controls.mediaplayer;

import com.jpexs.javactivex.*;

/**
 * IWMPControls: Public interface.
 */
@GUID(value="{74C09E02-F828-11D2-A74B-00A0C905F36E}", base="{6BF52A52-394A-11D3-B153-00C04F79FAA6}")
public interface IWMPControls {


	/**
	 * Begins playing media
	 * 
	 */
	public void play();


	/**
	 * Sets the current item to the next item in the playlist
	 * 
	 */
	public void next();


	/**
	 * Sets the current item to the previous item in the playlist
	 * 
	 */
	public void previous();


	/**
	 * Fast play of media in reverse direction
	 * 
	 */
	public void fastReverse();


	/**
	 * Pauses play of media
	 * 
	 */
	public void pause();


	/**
	 * Sets the current item and plays it
	 * 
	 * @param pIWMPMedia
	 */
	public void playItem(IWMPMedia pIWMPMedia);


	/**
	 * Stops play of media
	 * 
	 */
	public void stop();


	/**
	 * Fast play of media in forward direction
	 * 
	 */
	public void fastForward();


	/**
	 * Getter for property currentPosition
	 * 
	 * @return currentPosition value
	 */
	@Getter("currentPosition")
	public double getCurrentPosition();

	/**
	 * Setter for property currentPosition
	 * 
	 * @param value New currentPosition value
	 */
	@Setter("currentPosition")
	public void setCurrentPosition(double value);

	/**
	 * Getter for property currentPositionString
	 * 
	 * @return currentPositionString value
	 */
	@Getter("currentPositionString")
	public String getCurrentPositionString();

	/**
	 * Getter for property currentItem
	 * 
	 * @return currentItem value
	 */
	@Getter("currentItem")
	public IWMPMedia getCurrentItem();

	/**
	 * Setter for property currentItem
	 * 
	 * @param value New currentItem value
	 */
	@Setter("currentItem")
	public void setCurrentItem(IWMPMedia value);

	/**
	 * Getter for property currentMarker
	 * 
	 * @return currentMarker value
	 */
	@Getter("currentMarker")
	public int getCurrentMarker();

	/**
	 * Setter for property currentMarker
	 * 
	 * @param value New currentMarker value
	 */
	@Setter("currentMarker")
	public void setCurrentMarker(int value);
}