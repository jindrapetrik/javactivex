package com.jpexs.javactivex.example.controls.mediaplayer;

import com.jpexs.javactivex.*;

/**
 * IWMPDVD: Public interface.
 */
@GUID(value="{8DA61686-4668-4A5C-AE5D-803193293DBE}", base="{6BF52A52-394A-11D3-B153-00C04F79FAA6}")
public interface IWMPDVD {


	/**
	 * Removes the menu from the screen and returns to playing the DVD
	 * 
	 */
	public void resume();


	/**
	 * Displays the title menu of the current DVD title
	 * 
	 */
	public void titleMenu();


	/**
	 * Navigates back one menu
	 * 
	 */
	public void back();


	/**
	 * Displays the top menu of the DVD
	 * 
	 */
	public void topMenu();


	/**
	 * Getter for property domain
	 * 
	 * @return domain value
	 */
	@Getter("domain")
	public String getDomain();
}