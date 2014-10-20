package com.jpexs.javactivex.example.controls.flash;

import com.jpexs.javactivex.*;

/**
 * Shockwave Flash
 */
@GUID("{d27cdb6e-ae6d-11cf-96b8-444553540000}")
public interface ShockwaveFlash {


	/**
	 * Adds OnReadyStateChange event listener
	 * @param l Event listener
	 */
	@AddListener("OnReadyStateChange")
	public void addOnReadyStateChangeListener(ActiveXEventListener l);

	/**
	 * Removes OnReadyStateChange event listener
	 * @param l Event listener
	 */
	@RemoveListener("OnReadyStateChange")
	public void removeOnReadyStateChangeListener(ActiveXEventListener l);

	/**
	 * Adds OnProgress event listener
	 * @param l Event listener
	 */
	@AddListener("OnProgress")
	public void addOnProgressListener(ActiveXEventListener l);

	/**
	 * Removes OnProgress event listener
	 * @param l Event listener
	 */
	@RemoveListener("OnProgress")
	public void removeOnProgressListener(ActiveXEventListener l);

	/**
	 * Adds FSCommand event listener
	 * @param l Event listener
	 */
	@AddListener("FSCommand")
	public void addFSCommandListener(ActiveXEventListener l);

	/**
	 * Removes FSCommand event listener
	 * @param l Event listener
	 */
	@RemoveListener("FSCommand")
	public void removeFSCommandListener(ActiveXEventListener l);

	/**
	 * Adds FlashCall event listener
	 * @param l Event listener
	 */
	@AddListener("FlashCall")
	public void addFlashCallListener(ActiveXEventListener l);

	/**
	 * Removes FlashCall event listener
	 * @param l Event listener
	 */
	@RemoveListener("FlashCall")
	public void removeFlashCallListener(ActiveXEventListener l);

	/**
	 * method EnforceLocalSecurity
	 * 
	 */
	public void EnforceLocalSecurity();


	/**
	 * method GotoFrame
	 * 
	 * @param FrameNum
	 */
	public void GotoFrame(int FrameNum);


	/**
	 * method Stop
	 * 
	 */
	public void Stop();


	/**
	 * method SetZoomRect
	 * 
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 */
	public void SetZoomRect(int left, int top, int right, int bottom);


	/**
	 * method TStopPlay
	 * 
	 * @param target
	 */
	public void TStopPlay(String target);


	/**
	 * method DisableLocalSecurity
	 * 
	 */
	public void DisableLocalSecurity();


	/**
	 * method TGetProperty
	 * 
	 * @param target
	 * @param property
	 * @return
	 */
	public String TGetProperty(String target, int property);


	/**
	 * method LoadMovie
	 * 
	 * @param layer
	 * @param url
	 */
	public void LoadMovie(int layer, String url);


	/**
	 * method TSetPropertyNum
	 * 
	 * @param target
	 * @param property
	 * @param value
	 */
	public void TSetPropertyNum(String target, int property, double value);


	/**
	 * method TGotoLabel
	 * 
	 * @param target
	 * @param label
	 */
	public void TGotoLabel(String target, String label);


	/**
	 * method TGotoFrame
	 * 
	 * @param target
	 * @param FrameNum
	 */
	public void TGotoFrame(String target, int FrameNum);


	/**
	 * method Back
	 * 
	 */
	public void Back();


	/**
	 * method Zoom
	 * 
	 * @param factor
	 */
	public void Zoom(int factor);


	/**
	 * method GetVariable
	 * 
	 * @param name
	 * @return
	 */
	public String GetVariable(String name);


	/**
	 * method SetReturnValue
	 * 
	 * @param returnValue
	 */
	public void SetReturnValue(String returnValue);


	/**
	 * method TGetPropertyAsNumber
	 * 
	 * @param target
	 * @param property
	 * @return
	 */
	public double TGetPropertyAsNumber(String target, int property);


	/**
	 * method Call
	 * 
	 * @param request
	 * @return
	 */
	public String CallFunction(String request);


	/**
	 * method TCurrentFrame
	 * 
	 * @param target
	 * @return
	 */
	public int TCurrentFrame(String target);


	/**
	 * method PercentLoaded
	 * 
	 * @return
	 */
	public int PercentLoaded();


	/**
	 * method SetVariable
	 * 
	 * @param name
	 * @param value
	 */
	public void SetVariable(String name, String value);


	/**
	 * method TCallLabel
	 * 
	 * @param target
	 * @param label
	 */
	public void TCallLabel(String target, String label);


	/**
	 * method FlashVersion
	 * 
	 * @return
	 */
	public int FlashVersion();


	/**
	 * method CurrentFrame
	 * 
	 * @return
	 */
	public int CurrentFrame();


	/**
	 * method FrameLoaded
	 * 
	 * @param FrameNum
	 * @return
	 */
	public boolean FrameLoaded(int FrameNum);


	/**
	 * method TGetPropertyNum
	 * 
	 * @param target
	 * @param property
	 * @return
	 */
	public double TGetPropertyNum(String target, int property);


	/**
	 * method TCallFrame
	 * 
	 * @param target
	 * @param FrameNum
	 */
	public void TCallFrame(String target, int FrameNum);


	/**
	 * method TCurrentLabel
	 * 
	 * @param target
	 * @return
	 */
	public String TCurrentLabel(String target);


	/**
	 * method TPlay
	 * 
	 * @param target
	 */
	public void TPlay(String target);


	/**
	 * method Rewind
	 * 
	 */
	public void Rewind();


	/**
	 * method Play
	 * 
	 */
	public void Play();


	/**
	 * method TSetProperty
	 * 
	 * @param target
	 * @param property
	 * @param value
	 */
	public void TSetProperty(String target, int property, String value);


	/**
	 * method StopPlay
	 * 
	 */
	public void StopPlay();


	/**
	 * method Forward
	 * 
	 */
	public void Forward();


	/**
	 * method Pan
	 * 
	 * @param x
	 * @param y
	 * @param mode
	 */
	public void Pan(int x, int y, int mode);


	/**
	 * method IsPlaying
	 * 
	 * @return
	 */
	public boolean IsPlaying();


	/**
	 * Getter for property ReadyState
	 * 
	 * @return ReadyState value
	 */
	@Getter("ReadyState")
	public int getReadyState();

	/**
	 * Getter for property TotalFrames
	 * 
	 * @return TotalFrames value
	 */
	@Getter("TotalFrames")
	public int getTotalFrames();

	/**
	 * Getter for property Playing
	 * 
	 * @return Playing value
	 */
	@Getter("Playing")
	public boolean getPlaying();

	/**
	 * Setter for property Playing
	 * 
	 * @param value New Playing value
	 */
	@Setter("Playing")
	public void setPlaying(boolean value);

	/**
	 * Getter for property Quality
	 * 
	 * @return Quality value
	 */
	@Getter("Quality")
	public int getQuality();

	/**
	 * Setter for property Quality
	 * 
	 * @param value New Quality value
	 */
	@Setter("Quality")
	public void setQuality(int value);

	/**
	 * Getter for property ScaleMode
	 * 
	 * @return ScaleMode value
	 */
	@Getter("ScaleMode")
	public int getScaleMode();

	/**
	 * Setter for property ScaleMode
	 * 
	 * @param value New ScaleMode value
	 */
	@Setter("ScaleMode")
	public void setScaleMode(int value);

	/**
	 * Getter for property AlignMode
	 * 
	 * @return AlignMode value
	 */
	@Getter("AlignMode")
	public int getAlignMode();

	/**
	 * Setter for property AlignMode
	 * 
	 * @param value New AlignMode value
	 */
	@Setter("AlignMode")
	public void setAlignMode(int value);

	/**
	 * Getter for property BackgroundColor
	 * 
	 * @return BackgroundColor value
	 */
	@Getter("BackgroundColor")
	public int getBackgroundColor();

	/**
	 * Setter for property BackgroundColor
	 * 
	 * @param value New BackgroundColor value
	 */
	@Setter("BackgroundColor")
	public void setBackgroundColor(int value);

	/**
	 * Getter for property Loop
	 * 
	 * @return Loop value
	 */
	@Getter("Loop")
	public boolean getLoop();

	/**
	 * Setter for property Loop
	 * 
	 * @param value New Loop value
	 */
	@Setter("Loop")
	public void setLoop(boolean value);

	/**
	 * Getter for property Movie
	 * 
	 * @return Movie value
	 */
	@Getter("Movie")
	public String getMovie();

	/**
	 * Setter for property Movie
	 * 
	 * @param value New Movie value
	 */
	@Setter("Movie")
	public void setMovie(String value);

	/**
	 * Getter for property FrameNum
	 * 
	 * @return FrameNum value
	 */
	@Getter("FrameNum")
	public int getFrameNum();

	/**
	 * Setter for property FrameNum
	 * 
	 * @param value New FrameNum value
	 */
	@Setter("FrameNum")
	public void setFrameNum(int value);

	/**
	 * Getter for property WMode
	 * 
	 * @return WMode value
	 */
	@Getter("WMode")
	public String getWMode();

	/**
	 * Setter for property WMode
	 * 
	 * @param value New WMode value
	 */
	@Setter("WMode")
	public void setWMode(String value);

	/**
	 * Getter for property SAlign
	 * 
	 * @return SAlign value
	 */
	@Getter("SAlign")
	public String getSAlign();

	/**
	 * Setter for property SAlign
	 * 
	 * @param value New SAlign value
	 */
	@Setter("SAlign")
	public void setSAlign(String value);

	/**
	 * Getter for property Menu
	 * 
	 * @return Menu value
	 */
	@Getter("Menu")
	public boolean getMenu();

	/**
	 * Setter for property Menu
	 * 
	 * @param value New Menu value
	 */
	@Setter("Menu")
	public void setMenu(boolean value);

	/**
	 * Getter for property Base
	 * 
	 * @return Base value
	 */
	@Getter("Base")
	public String getBase();

	/**
	 * Setter for property Base
	 * 
	 * @param value New Base value
	 */
	@Setter("Base")
	public void setBase(String value);

	/**
	 * Getter for property Scale
	 * 
	 * @return Scale value
	 */
	@Getter("Scale")
	public String getScale();

	/**
	 * Setter for property Scale
	 * 
	 * @param value New Scale value
	 */
	@Setter("Scale")
	public void setScale(String value);

	/**
	 * Getter for property DeviceFont
	 * 
	 * @return DeviceFont value
	 */
	@Getter("DeviceFont")
	public boolean getDeviceFont();

	/**
	 * Setter for property DeviceFont
	 * 
	 * @param value New DeviceFont value
	 */
	@Setter("DeviceFont")
	public void setDeviceFont(boolean value);

	/**
	 * Getter for property EmbedMovie
	 * 
	 * @return EmbedMovie value
	 */
	@Getter("EmbedMovie")
	public boolean getEmbedMovie();

	/**
	 * Setter for property EmbedMovie
	 * 
	 * @param value New EmbedMovie value
	 */
	@Setter("EmbedMovie")
	public void setEmbedMovie(boolean value);

	/**
	 * Getter for property BGColor
	 * 
	 * @return BGColor value
	 */
	@Getter("BGColor")
	public String getBGColor();

	/**
	 * Setter for property BGColor
	 * 
	 * @param value New BGColor value
	 */
	@Setter("BGColor")
	public void setBGColor(String value);

	/**
	 * Getter for property Quality2
	 * 
	 * @return Quality2 value
	 */
	@Getter("Quality2")
	public String getQuality2();

	/**
	 * Setter for property Quality2
	 * 
	 * @param value New Quality2 value
	 */
	@Setter("Quality2")
	public void setQuality2(String value);

	/**
	 * Getter for property SWRemote
	 * 
	 * @return SWRemote value
	 */
	@Getter("SWRemote")
	public String getSWRemote();

	/**
	 * Setter for property SWRemote
	 * 
	 * @param value New SWRemote value
	 */
	@Setter("SWRemote")
	public void setSWRemote(String value);

	/**
	 * Getter for property FlashVars
	 * 
	 * @return FlashVars value
	 */
	@Getter("FlashVars")
	public String getFlashVars();

	/**
	 * Setter for property FlashVars
	 * 
	 * @param value New FlashVars value
	 */
	@Setter("FlashVars")
	public void setFlashVars(String value);

	/**
	 * Getter for property AllowScriptAccess
	 * 
	 * @return AllowScriptAccess value
	 */
	@Getter("AllowScriptAccess")
	public String getAllowScriptAccess();

	/**
	 * Setter for property AllowScriptAccess
	 * 
	 * @param value New AllowScriptAccess value
	 */
	@Setter("AllowScriptAccess")
	public void setAllowScriptAccess(String value);

	/**
	 * Getter for property MovieData
	 * 
	 * @return MovieData value
	 */
	@Getter("MovieData")
	public String getMovieData();

	/**
	 * Setter for property MovieData
	 * 
	 * @param value New MovieData value
	 */
	@Setter("MovieData")
	public void setMovieData(String value);

	/**
	 * Getter for property InlineData
	 * 
	 * @return (Unknown:IShockwaveFlash:{D27CDB6E-AE6D-11CF-96B8-444553540000}:{D27CDB6C-AE6D-11CF-96B8-444553540000}) InlineData value
	 */
	@Getter("InlineData")
	public Object getInlineData();

	/**
	 * Setter for property InlineData
	 * 
	 * @param value (Unknown:IShockwaveFlash:{D27CDB6E-AE6D-11CF-96B8-444553540000}:{D27CDB6C-AE6D-11CF-96B8-444553540000}) New InlineData value
	 */
	@Setter("InlineData")
	public void setInlineData(Object value);

	/**
	 * Getter for property SeamlessTabbing
	 * 
	 * @return SeamlessTabbing value
	 */
	@Getter("SeamlessTabbing")
	public boolean getSeamlessTabbing();

	/**
	 * Setter for property SeamlessTabbing
	 * 
	 * @param value New SeamlessTabbing value
	 */
	@Setter("SeamlessTabbing")
	public void setSeamlessTabbing(boolean value);

	/**
	 * Getter for property Profile
	 * 
	 * @return Profile value
	 */
	@Getter("Profile")
	public boolean getProfile();

	/**
	 * Setter for property Profile
	 * 
	 * @param value New Profile value
	 */
	@Setter("Profile")
	public void setProfile(boolean value);

	/**
	 * Getter for property ProfileAddress
	 * 
	 * @return ProfileAddress value
	 */
	@Getter("ProfileAddress")
	public String getProfileAddress();

	/**
	 * Setter for property ProfileAddress
	 * 
	 * @param value New ProfileAddress value
	 */
	@Setter("ProfileAddress")
	public void setProfileAddress(String value);

	/**
	 * Getter for property ProfilePort
	 * 
	 * @return ProfilePort value
	 */
	@Getter("ProfilePort")
	public int getProfilePort();

	/**
	 * Setter for property ProfilePort
	 * 
	 * @param value New ProfilePort value
	 */
	@Setter("ProfilePort")
	public void setProfilePort(int value);

	/**
	 * Getter for property AllowNetworking
	 * 
	 * @return AllowNetworking value
	 */
	@Getter("AllowNetworking")
	public String getAllowNetworking();

	/**
	 * Setter for property AllowNetworking
	 * 
	 * @param value New AllowNetworking value
	 */
	@Setter("AllowNetworking")
	public void setAllowNetworking(String value);

	/**
	 * Getter for property AllowFullScreen
	 * 
	 * @return AllowFullScreen value
	 */
	@Getter("AllowFullScreen")
	public String getAllowFullScreen();

	/**
	 * Setter for property AllowFullScreen
	 * 
	 * @param value New AllowFullScreen value
	 */
	@Setter("AllowFullScreen")
	public void setAllowFullScreen(String value);

	/**
	 * Getter for property AllowFullScreenInteractive
	 * 
	 * @return AllowFullScreenInteractive value
	 */
	@Getter("AllowFullScreenInteractive")
	public String getAllowFullScreenInteractive();

	/**
	 * Setter for property AllowFullScreenInteractive
	 * 
	 * @param value New AllowFullScreenInteractive value
	 */
	@Setter("AllowFullScreenInteractive")
	public void setAllowFullScreenInteractive(String value);

	/**
	 * Getter for property IsDependent
	 * 
	 * @return IsDependent value
	 */
	@Getter("IsDependent")
	public boolean getIsDependent();

	/**
	 * Setter for property IsDependent
	 * 
	 * @param value New IsDependent value
	 */
	@Setter("IsDependent")
	public void setIsDependent(boolean value);
}