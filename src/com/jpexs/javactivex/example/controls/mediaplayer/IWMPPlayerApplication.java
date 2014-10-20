package com.jpexs.javactivex.example.controls.mediaplayer;

import com.jpexs.javactivex.*;

/**
 * IWMPPlayerApplication: Public interface.
 */
@GUID(value="{40897764-CEAB-47BE-AD4A-8E28537F9BBF}", base="{6BF52A52-394A-11D3-B153-00C04F79FAA6}")
public interface IWMPPlayerApplication {


	/**
	 * Switches the display to control
	 * 
	 */
	public void switchToControl();


	/**
	 * Switches the display to player application
	 * 
	 */
	public void switchToPlayerApplication();


	/**
	 * Getter for property playerDocked
	 * 
	 * @return playerDocked value
	 */
	@Getter("playerDocked")
	public boolean getPlayerDocked();

	/**
	 * Getter for property hasDisplay
	 * 
	 * @return hasDisplay value
	 */
	@Getter("hasDisplay")
	public boolean getHasDisplay();
}