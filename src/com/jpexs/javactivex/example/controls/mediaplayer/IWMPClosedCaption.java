package com.jpexs.javactivex.example.controls.mediaplayer;

import com.jpexs.javactivex.*;

/**
 * IWMPClosedCaption: Public interface.
 */
@GUID(value="{4F2DF574-C588-11D3-9ED0-00C04FB6E937}", base="{6BF52A52-394A-11D3-B153-00C04F79FAA6}")
public interface IWMPClosedCaption {


	/**
	 * Getter for property SAMIStyle
	 * 
	 * @return SAMIStyle value
	 */
	@Getter("SAMIStyle")
	public String getSAMIStyle();

	/**
	 * Setter for property SAMIStyle
	 * 
	 * @param value New SAMIStyle value
	 */
	@Setter("SAMIStyle")
	public void setSAMIStyle(String value);

	/**
	 * Getter for property SAMILang
	 * 
	 * @return SAMILang value
	 */
	@Getter("SAMILang")
	public String getSAMILang();

	/**
	 * Setter for property SAMILang
	 * 
	 * @param value New SAMILang value
	 */
	@Setter("SAMILang")
	public void setSAMILang(String value);

	/**
	 * Getter for property SAMIFileName
	 * 
	 * @return SAMIFileName value
	 */
	@Getter("SAMIFileName")
	public String getSAMIFileName();

	/**
	 * Setter for property SAMIFileName
	 * 
	 * @param value New SAMIFileName value
	 */
	@Setter("SAMIFileName")
	public void setSAMIFileName(String value);

	/**
	 * Getter for property captioningId
	 * 
	 * @return captioningId value
	 */
	@Getter("captioningId")
	public String getCaptioningId();

	/**
	 * Setter for property captioningId
	 * 
	 * @param value New captioningId value
	 */
	@Setter("captioningId")
	public void setCaptioningId(String value);
}