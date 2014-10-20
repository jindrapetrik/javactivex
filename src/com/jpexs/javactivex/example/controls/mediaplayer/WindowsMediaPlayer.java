package com.jpexs.javactivex.example.controls.mediaplayer;

import com.jpexs.javactivex.*;

/**
 * Windows Media Player ActiveX Control
 */
@GUID("{6BF52A52-394A-11d3-B153-00C04F79FAA6}")
public interface WindowsMediaPlayer {


	/**
	 * Adds OpenStateChange event listener
	 * @param l Event listener
	 */
	@AddListener("OpenStateChange")
	public void addOpenStateChangeListener(ActiveXEventListener l);

	/**
	 * Removes OpenStateChange event listener
	 * @param l Event listener
	 */
	@RemoveListener("OpenStateChange")
	public void removeOpenStateChangeListener(ActiveXEventListener l);

	/**
	 * Adds PlayStateChange event listener
	 * @param l Event listener
	 */
	@AddListener("PlayStateChange")
	public void addPlayStateChangeListener(ActiveXEventListener l);

	/**
	 * Removes PlayStateChange event listener
	 * @param l Event listener
	 */
	@RemoveListener("PlayStateChange")
	public void removePlayStateChangeListener(ActiveXEventListener l);

	/**
	 * Adds AudioLanguageChange event listener
	 * @param l Event listener
	 */
	@AddListener("AudioLanguageChange")
	public void addAudioLanguageChangeListener(ActiveXEventListener l);

	/**
	 * Removes AudioLanguageChange event listener
	 * @param l Event listener
	 */
	@RemoveListener("AudioLanguageChange")
	public void removeAudioLanguageChangeListener(ActiveXEventListener l);

	/**
	 * Adds StatusChange event listener
	 * @param l Event listener
	 */
	@AddListener("StatusChange")
	public void addStatusChangeListener(ActiveXEventListener l);

	/**
	 * Removes StatusChange event listener
	 * @param l Event listener
	 */
	@RemoveListener("StatusChange")
	public void removeStatusChangeListener(ActiveXEventListener l);

	/**
	 * Adds ScriptCommand event listener
	 * @param l Event listener
	 */
	@AddListener("ScriptCommand")
	public void addScriptCommandListener(ActiveXEventListener l);

	/**
	 * Removes ScriptCommand event listener
	 * @param l Event listener
	 */
	@RemoveListener("ScriptCommand")
	public void removeScriptCommandListener(ActiveXEventListener l);

	/**
	 * Adds NewStream event listener
	 * @param l Event listener
	 */
	@AddListener("NewStream")
	public void addNewStreamListener(ActiveXEventListener l);

	/**
	 * Removes NewStream event listener
	 * @param l Event listener
	 */
	@RemoveListener("NewStream")
	public void removeNewStreamListener(ActiveXEventListener l);

	/**
	 * Adds Disconnect event listener
	 * @param l Event listener
	 */
	@AddListener("Disconnect")
	public void addDisconnectListener(ActiveXEventListener l);

	/**
	 * Removes Disconnect event listener
	 * @param l Event listener
	 */
	@RemoveListener("Disconnect")
	public void removeDisconnectListener(ActiveXEventListener l);

	/**
	 * Adds Buffering event listener
	 * @param l Event listener
	 */
	@AddListener("Buffering")
	public void addBufferingListener(ActiveXEventListener l);

	/**
	 * Removes Buffering event listener
	 * @param l Event listener
	 */
	@RemoveListener("Buffering")
	public void removeBufferingListener(ActiveXEventListener l);

	/**
	 * Adds Error event listener
	 * @param l Event listener
	 */
	@AddListener("Error")
	public void addErrorListener(ActiveXEventListener l);

	/**
	 * Removes Error event listener
	 * @param l Event listener
	 */
	@RemoveListener("Error")
	public void removeErrorListener(ActiveXEventListener l);

	/**
	 * Adds Warning event listener
	 * @param l Event listener
	 */
	@AddListener("Warning")
	public void addWarningListener(ActiveXEventListener l);

	/**
	 * Removes Warning event listener
	 * @param l Event listener
	 */
	@RemoveListener("Warning")
	public void removeWarningListener(ActiveXEventListener l);

	/**
	 * Adds EndOfStream event listener
	 * @param l Event listener
	 */
	@AddListener("EndOfStream")
	public void addEndOfStreamListener(ActiveXEventListener l);

	/**
	 * Removes EndOfStream event listener
	 * @param l Event listener
	 */
	@RemoveListener("EndOfStream")
	public void removeEndOfStreamListener(ActiveXEventListener l);

	/**
	 * Adds PositionChange event listener
	 * @param l Event listener
	 */
	@AddListener("PositionChange")
	public void addPositionChangeListener(ActiveXEventListener l);

	/**
	 * Removes PositionChange event listener
	 * @param l Event listener
	 */
	@RemoveListener("PositionChange")
	public void removePositionChangeListener(ActiveXEventListener l);

	/**
	 * Adds MarkerHit event listener
	 * @param l Event listener
	 */
	@AddListener("MarkerHit")
	public void addMarkerHitListener(ActiveXEventListener l);

	/**
	 * Removes MarkerHit event listener
	 * @param l Event listener
	 */
	@RemoveListener("MarkerHit")
	public void removeMarkerHitListener(ActiveXEventListener l);

	/**
	 * Adds DurationUnitChange event listener
	 * @param l Event listener
	 */
	@AddListener("DurationUnitChange")
	public void addDurationUnitChangeListener(ActiveXEventListener l);

	/**
	 * Removes DurationUnitChange event listener
	 * @param l Event listener
	 */
	@RemoveListener("DurationUnitChange")
	public void removeDurationUnitChangeListener(ActiveXEventListener l);

	/**
	 * Adds CdromMediaChange event listener
	 * @param l Event listener
	 */
	@AddListener("CdromMediaChange")
	public void addCdromMediaChangeListener(ActiveXEventListener l);

	/**
	 * Removes CdromMediaChange event listener
	 * @param l Event listener
	 */
	@RemoveListener("CdromMediaChange")
	public void removeCdromMediaChangeListener(ActiveXEventListener l);

	/**
	 * Adds PlaylistChange event listener
	 * @param l Event listener
	 */
	@AddListener("PlaylistChange")
	public void addPlaylistChangeListener(ActiveXEventListener l);

	/**
	 * Removes PlaylistChange event listener
	 * @param l Event listener
	 */
	@RemoveListener("PlaylistChange")
	public void removePlaylistChangeListener(ActiveXEventListener l);

	/**
	 * Adds CurrentPlaylistChange event listener
	 * @param l Event listener
	 */
	@AddListener("CurrentPlaylistChange")
	public void addCurrentPlaylistChangeListener(ActiveXEventListener l);

	/**
	 * Removes CurrentPlaylistChange event listener
	 * @param l Event listener
	 */
	@RemoveListener("CurrentPlaylistChange")
	public void removeCurrentPlaylistChangeListener(ActiveXEventListener l);

	/**
	 * Adds CurrentPlaylistItemAvailable event listener
	 * @param l Event listener
	 */
	@AddListener("CurrentPlaylistItemAvailable")
	public void addCurrentPlaylistItemAvailableListener(ActiveXEventListener l);

	/**
	 * Removes CurrentPlaylistItemAvailable event listener
	 * @param l Event listener
	 */
	@RemoveListener("CurrentPlaylistItemAvailable")
	public void removeCurrentPlaylistItemAvailableListener(ActiveXEventListener l);

	/**
	 * Adds MediaChange event listener
	 * @param l Event listener
	 */
	@AddListener("MediaChange")
	public void addMediaChangeListener(ActiveXEventListener l);

	/**
	 * Removes MediaChange event listener
	 * @param l Event listener
	 */
	@RemoveListener("MediaChange")
	public void removeMediaChangeListener(ActiveXEventListener l);

	/**
	 * Adds CurrentMediaItemAvailable event listener
	 * @param l Event listener
	 */
	@AddListener("CurrentMediaItemAvailable")
	public void addCurrentMediaItemAvailableListener(ActiveXEventListener l);

	/**
	 * Removes CurrentMediaItemAvailable event listener
	 * @param l Event listener
	 */
	@RemoveListener("CurrentMediaItemAvailable")
	public void removeCurrentMediaItemAvailableListener(ActiveXEventListener l);

	/**
	 * Adds CurrentItemChange event listener
	 * @param l Event listener
	 */
	@AddListener("CurrentItemChange")
	public void addCurrentItemChangeListener(ActiveXEventListener l);

	/**
	 * Removes CurrentItemChange event listener
	 * @param l Event listener
	 */
	@RemoveListener("CurrentItemChange")
	public void removeCurrentItemChangeListener(ActiveXEventListener l);

	/**
	 * Adds MediaCollectionChange event listener
	 * @param l Event listener
	 */
	@AddListener("MediaCollectionChange")
	public void addMediaCollectionChangeListener(ActiveXEventListener l);

	/**
	 * Removes MediaCollectionChange event listener
	 * @param l Event listener
	 */
	@RemoveListener("MediaCollectionChange")
	public void removeMediaCollectionChangeListener(ActiveXEventListener l);

	/**
	 * Adds MediaCollectionAttributeStringAdded event listener
	 * @param l Event listener
	 */
	@AddListener("MediaCollectionAttributeStringAdded")
	public void addMediaCollectionAttributeStringAddedListener(ActiveXEventListener l);

	/**
	 * Removes MediaCollectionAttributeStringAdded event listener
	 * @param l Event listener
	 */
	@RemoveListener("MediaCollectionAttributeStringAdded")
	public void removeMediaCollectionAttributeStringAddedListener(ActiveXEventListener l);

	/**
	 * Adds MediaCollectionAttributeStringRemoved event listener
	 * @param l Event listener
	 */
	@AddListener("MediaCollectionAttributeStringRemoved")
	public void addMediaCollectionAttributeStringRemovedListener(ActiveXEventListener l);

	/**
	 * Removes MediaCollectionAttributeStringRemoved event listener
	 * @param l Event listener
	 */
	@RemoveListener("MediaCollectionAttributeStringRemoved")
	public void removeMediaCollectionAttributeStringRemovedListener(ActiveXEventListener l);

	/**
	 * Adds MediaCollectionAttributeStringChanged event listener
	 * @param l Event listener
	 */
	@AddListener("MediaCollectionAttributeStringChanged")
	public void addMediaCollectionAttributeStringChangedListener(ActiveXEventListener l);

	/**
	 * Removes MediaCollectionAttributeStringChanged event listener
	 * @param l Event listener
	 */
	@RemoveListener("MediaCollectionAttributeStringChanged")
	public void removeMediaCollectionAttributeStringChangedListener(ActiveXEventListener l);

	/**
	 * Adds PlaylistCollectionChange event listener
	 * @param l Event listener
	 */
	@AddListener("PlaylistCollectionChange")
	public void addPlaylistCollectionChangeListener(ActiveXEventListener l);

	/**
	 * Removes PlaylistCollectionChange event listener
	 * @param l Event listener
	 */
	@RemoveListener("PlaylistCollectionChange")
	public void removePlaylistCollectionChangeListener(ActiveXEventListener l);

	/**
	 * Adds PlaylistCollectionPlaylistAdded event listener
	 * @param l Event listener
	 */
	@AddListener("PlaylistCollectionPlaylistAdded")
	public void addPlaylistCollectionPlaylistAddedListener(ActiveXEventListener l);

	/**
	 * Removes PlaylistCollectionPlaylistAdded event listener
	 * @param l Event listener
	 */
	@RemoveListener("PlaylistCollectionPlaylistAdded")
	public void removePlaylistCollectionPlaylistAddedListener(ActiveXEventListener l);

	/**
	 * Adds PlaylistCollectionPlaylistRemoved event listener
	 * @param l Event listener
	 */
	@AddListener("PlaylistCollectionPlaylistRemoved")
	public void addPlaylistCollectionPlaylistRemovedListener(ActiveXEventListener l);

	/**
	 * Removes PlaylistCollectionPlaylistRemoved event listener
	 * @param l Event listener
	 */
	@RemoveListener("PlaylistCollectionPlaylistRemoved")
	public void removePlaylistCollectionPlaylistRemovedListener(ActiveXEventListener l);

	/**
	 * Adds PlaylistCollectionPlaylistSetAsDeleted event listener
	 * @param l Event listener
	 */
	@AddListener("PlaylistCollectionPlaylistSetAsDeleted")
	public void addPlaylistCollectionPlaylistSetAsDeletedListener(ActiveXEventListener l);

	/**
	 * Removes PlaylistCollectionPlaylistSetAsDeleted event listener
	 * @param l Event listener
	 */
	@RemoveListener("PlaylistCollectionPlaylistSetAsDeleted")
	public void removePlaylistCollectionPlaylistSetAsDeletedListener(ActiveXEventListener l);

	/**
	 * Adds ModeChange event listener
	 * @param l Event listener
	 */
	@AddListener("ModeChange")
	public void addModeChangeListener(ActiveXEventListener l);

	/**
	 * Removes ModeChange event listener
	 * @param l Event listener
	 */
	@RemoveListener("ModeChange")
	public void removeModeChangeListener(ActiveXEventListener l);

	/**
	 * Adds MediaError event listener
	 * @param l Event listener
	 */
	@AddListener("MediaError")
	public void addMediaErrorListener(ActiveXEventListener l);

	/**
	 * Removes MediaError event listener
	 * @param l Event listener
	 */
	@RemoveListener("MediaError")
	public void removeMediaErrorListener(ActiveXEventListener l);

	/**
	 * Adds OpenPlaylistSwitch event listener
	 * @param l Event listener
	 */
	@AddListener("OpenPlaylistSwitch")
	public void addOpenPlaylistSwitchListener(ActiveXEventListener l);

	/**
	 * Removes OpenPlaylistSwitch event listener
	 * @param l Event listener
	 */
	@RemoveListener("OpenPlaylistSwitch")
	public void removeOpenPlaylistSwitchListener(ActiveXEventListener l);

	/**
	 * Adds DomainChange event listener
	 * @param l Event listener
	 */
	@AddListener("DomainChange")
	public void addDomainChangeListener(ActiveXEventListener l);

	/**
	 * Removes DomainChange event listener
	 * @param l Event listener
	 */
	@RemoveListener("DomainChange")
	public void removeDomainChangeListener(ActiveXEventListener l);

	/**
	 * Adds SwitchedToPlayerApplication event listener
	 * @param l Event listener
	 */
	@AddListener("SwitchedToPlayerApplication")
	public void addSwitchedToPlayerApplicationListener(ActiveXEventListener l);

	/**
	 * Removes SwitchedToPlayerApplication event listener
	 * @param l Event listener
	 */
	@RemoveListener("SwitchedToPlayerApplication")
	public void removeSwitchedToPlayerApplicationListener(ActiveXEventListener l);

	/**
	 * Adds SwitchedToControl event listener
	 * @param l Event listener
	 */
	@AddListener("SwitchedToControl")
	public void addSwitchedToControlListener(ActiveXEventListener l);

	/**
	 * Removes SwitchedToControl event listener
	 * @param l Event listener
	 */
	@RemoveListener("SwitchedToControl")
	public void removeSwitchedToControlListener(ActiveXEventListener l);

	/**
	 * Adds PlayerDockedStateChange event listener
	 * @param l Event listener
	 */
	@AddListener("PlayerDockedStateChange")
	public void addPlayerDockedStateChangeListener(ActiveXEventListener l);

	/**
	 * Removes PlayerDockedStateChange event listener
	 * @param l Event listener
	 */
	@RemoveListener("PlayerDockedStateChange")
	public void removePlayerDockedStateChangeListener(ActiveXEventListener l);

	/**
	 * Adds PlayerReconnect event listener
	 * @param l Event listener
	 */
	@AddListener("PlayerReconnect")
	public void addPlayerReconnectListener(ActiveXEventListener l);

	/**
	 * Removes PlayerReconnect event listener
	 * @param l Event listener
	 */
	@RemoveListener("PlayerReconnect")
	public void removePlayerReconnectListener(ActiveXEventListener l);

	/**
	 * Adds Click event listener
	 * @param l Event listener
	 */
	@AddListener("Click")
	public void addClickListener(ActiveXEventListener l);

	/**
	 * Removes Click event listener
	 * @param l Event listener
	 */
	@RemoveListener("Click")
	public void removeClickListener(ActiveXEventListener l);

	/**
	 * Adds DoubleClick event listener
	 * @param l Event listener
	 */
	@AddListener("DoubleClick")
	public void addDoubleClickListener(ActiveXEventListener l);

	/**
	 * Removes DoubleClick event listener
	 * @param l Event listener
	 */
	@RemoveListener("DoubleClick")
	public void removeDoubleClickListener(ActiveXEventListener l);

	/**
	 * Adds KeyDown event listener
	 * @param l Event listener
	 */
	@AddListener("KeyDown")
	public void addKeyDownListener(ActiveXEventListener l);

	/**
	 * Removes KeyDown event listener
	 * @param l Event listener
	 */
	@RemoveListener("KeyDown")
	public void removeKeyDownListener(ActiveXEventListener l);

	/**
	 * Adds KeyPress event listener
	 * @param l Event listener
	 */
	@AddListener("KeyPress")
	public void addKeyPressListener(ActiveXEventListener l);

	/**
	 * Removes KeyPress event listener
	 * @param l Event listener
	 */
	@RemoveListener("KeyPress")
	public void removeKeyPressListener(ActiveXEventListener l);

	/**
	 * Adds KeyUp event listener
	 * @param l Event listener
	 */
	@AddListener("KeyUp")
	public void addKeyUpListener(ActiveXEventListener l);

	/**
	 * Removes KeyUp event listener
	 * @param l Event listener
	 */
	@RemoveListener("KeyUp")
	public void removeKeyUpListener(ActiveXEventListener l);

	/**
	 * Adds MouseDown event listener
	 * @param l Event listener
	 */
	@AddListener("MouseDown")
	public void addMouseDownListener(ActiveXEventListener l);

	/**
	 * Removes MouseDown event listener
	 * @param l Event listener
	 */
	@RemoveListener("MouseDown")
	public void removeMouseDownListener(ActiveXEventListener l);

	/**
	 * Adds MouseMove event listener
	 * @param l Event listener
	 */
	@AddListener("MouseMove")
	public void addMouseMoveListener(ActiveXEventListener l);

	/**
	 * Removes MouseMove event listener
	 * @param l Event listener
	 */
	@RemoveListener("MouseMove")
	public void removeMouseMoveListener(ActiveXEventListener l);

	/**
	 * Adds MouseUp event listener
	 * @param l Event listener
	 */
	@AddListener("MouseUp")
	public void addMouseUpListener(ActiveXEventListener l);

	/**
	 * Removes MouseUp event listener
	 * @param l Event listener
	 */
	@RemoveListener("MouseUp")
	public void removeMouseUpListener(ActiveXEventListener l);

	/**
	 * Adds DeviceConnect event listener
	 * @param l Event listener
	 */
	@AddListener("DeviceConnect")
	public void addDeviceConnectListener(ActiveXEventListener l);

	/**
	 * Removes DeviceConnect event listener
	 * @param l Event listener
	 */
	@RemoveListener("DeviceConnect")
	public void removeDeviceConnectListener(ActiveXEventListener l);

	/**
	 * Adds DeviceDisconnect event listener
	 * @param l Event listener
	 */
	@AddListener("DeviceDisconnect")
	public void addDeviceDisconnectListener(ActiveXEventListener l);

	/**
	 * Removes DeviceDisconnect event listener
	 * @param l Event listener
	 */
	@RemoveListener("DeviceDisconnect")
	public void removeDeviceDisconnectListener(ActiveXEventListener l);

	/**
	 * Adds DeviceStatusChange event listener
	 * @param l Event listener
	 */
	@AddListener("DeviceStatusChange")
	public void addDeviceStatusChangeListener(ActiveXEventListener l);

	/**
	 * Removes DeviceStatusChange event listener
	 * @param l Event listener
	 */
	@RemoveListener("DeviceStatusChange")
	public void removeDeviceStatusChangeListener(ActiveXEventListener l);

	/**
	 * Adds DeviceSyncStateChange event listener
	 * @param l Event listener
	 */
	@AddListener("DeviceSyncStateChange")
	public void addDeviceSyncStateChangeListener(ActiveXEventListener l);

	/**
	 * Removes DeviceSyncStateChange event listener
	 * @param l Event listener
	 */
	@RemoveListener("DeviceSyncStateChange")
	public void removeDeviceSyncStateChangeListener(ActiveXEventListener l);

	/**
	 * Adds DeviceSyncError event listener
	 * @param l Event listener
	 */
	@AddListener("DeviceSyncError")
	public void addDeviceSyncErrorListener(ActiveXEventListener l);

	/**
	 * Removes DeviceSyncError event listener
	 * @param l Event listener
	 */
	@RemoveListener("DeviceSyncError")
	public void removeDeviceSyncErrorListener(ActiveXEventListener l);

	/**
	 * Adds CreatePartnershipComplete event listener
	 * @param l Event listener
	 */
	@AddListener("CreatePartnershipComplete")
	public void addCreatePartnershipCompleteListener(ActiveXEventListener l);

	/**
	 * Removes CreatePartnershipComplete event listener
	 * @param l Event listener
	 */
	@RemoveListener("CreatePartnershipComplete")
	public void removeCreatePartnershipCompleteListener(ActiveXEventListener l);

	/**
	 * Adds DeviceEstimation event listener
	 * @param l Event listener
	 */
	@AddListener("DeviceEstimation")
	public void addDeviceEstimationListener(ActiveXEventListener l);

	/**
	 * Removes DeviceEstimation event listener
	 * @param l Event listener
	 */
	@RemoveListener("DeviceEstimation")
	public void removeDeviceEstimationListener(ActiveXEventListener l);

	/**
	 * Adds CdromRipStateChange event listener
	 * @param l Event listener
	 */
	@AddListener("CdromRipStateChange")
	public void addCdromRipStateChangeListener(ActiveXEventListener l);

	/**
	 * Removes CdromRipStateChange event listener
	 * @param l Event listener
	 */
	@RemoveListener("CdromRipStateChange")
	public void removeCdromRipStateChangeListener(ActiveXEventListener l);

	/**
	 * Adds CdromRipMediaError event listener
	 * @param l Event listener
	 */
	@AddListener("CdromRipMediaError")
	public void addCdromRipMediaErrorListener(ActiveXEventListener l);

	/**
	 * Removes CdromRipMediaError event listener
	 * @param l Event listener
	 */
	@RemoveListener("CdromRipMediaError")
	public void removeCdromRipMediaErrorListener(ActiveXEventListener l);

	/**
	 * Adds CdromBurnStateChange event listener
	 * @param l Event listener
	 */
	@AddListener("CdromBurnStateChange")
	public void addCdromBurnStateChangeListener(ActiveXEventListener l);

	/**
	 * Removes CdromBurnStateChange event listener
	 * @param l Event listener
	 */
	@RemoveListener("CdromBurnStateChange")
	public void removeCdromBurnStateChangeListener(ActiveXEventListener l);

	/**
	 * Adds CdromBurnMediaError event listener
	 * @param l Event listener
	 */
	@AddListener("CdromBurnMediaError")
	public void addCdromBurnMediaErrorListener(ActiveXEventListener l);

	/**
	 * Removes CdromBurnMediaError event listener
	 * @param l Event listener
	 */
	@RemoveListener("CdromBurnMediaError")
	public void removeCdromBurnMediaErrorListener(ActiveXEventListener l);

	/**
	 * Adds CdromBurnError event listener
	 * @param l Event listener
	 */
	@AddListener("CdromBurnError")
	public void addCdromBurnErrorListener(ActiveXEventListener l);

	/**
	 * Removes CdromBurnError event listener
	 * @param l Event listener
	 */
	@RemoveListener("CdromBurnError")
	public void removeCdromBurnErrorListener(ActiveXEventListener l);

	/**
	 * Adds LibraryConnect event listener
	 * @param l Event listener
	 */
	@AddListener("LibraryConnect")
	public void addLibraryConnectListener(ActiveXEventListener l);

	/**
	 * Removes LibraryConnect event listener
	 * @param l Event listener
	 */
	@RemoveListener("LibraryConnect")
	public void removeLibraryConnectListener(ActiveXEventListener l);

	/**
	 * Adds LibraryDisconnect event listener
	 * @param l Event listener
	 */
	@AddListener("LibraryDisconnect")
	public void addLibraryDisconnectListener(ActiveXEventListener l);

	/**
	 * Removes LibraryDisconnect event listener
	 * @param l Event listener
	 */
	@RemoveListener("LibraryDisconnect")
	public void removeLibraryDisconnectListener(ActiveXEventListener l);

	/**
	 * Adds FolderScanStateChange event listener
	 * @param l Event listener
	 */
	@AddListener("FolderScanStateChange")
	public void addFolderScanStateChangeListener(ActiveXEventListener l);

	/**
	 * Removes FolderScanStateChange event listener
	 * @param l Event listener
	 */
	@RemoveListener("FolderScanStateChange")
	public void removeFolderScanStateChangeListener(ActiveXEventListener l);

	/**
	 * Adds StringCollectionChange event listener
	 * @param l Event listener
	 */
	@AddListener("StringCollectionChange")
	public void addStringCollectionChangeListener(ActiveXEventListener l);

	/**
	 * Removes StringCollectionChange event listener
	 * @param l Event listener
	 */
	@RemoveListener("StringCollectionChange")
	public void removeStringCollectionChangeListener(ActiveXEventListener l);

	/**
	 * Adds MediaCollectionMediaAdded event listener
	 * @param l Event listener
	 */
	@AddListener("MediaCollectionMediaAdded")
	public void addMediaCollectionMediaAddedListener(ActiveXEventListener l);

	/**
	 * Removes MediaCollectionMediaAdded event listener
	 * @param l Event listener
	 */
	@RemoveListener("MediaCollectionMediaAdded")
	public void removeMediaCollectionMediaAddedListener(ActiveXEventListener l);

	/**
	 * Adds MediaCollectionMediaRemoved event listener
	 * @param l Event listener
	 */
	@AddListener("MediaCollectionMediaRemoved")
	public void addMediaCollectionMediaRemovedListener(ActiveXEventListener l);

	/**
	 * Removes MediaCollectionMediaRemoved event listener
	 * @param l Event listener
	 */
	@RemoveListener("MediaCollectionMediaRemoved")
	public void removeMediaCollectionMediaRemovedListener(ActiveXEventListener l);

	/**
	 * Creates a new media object
	 * 
	 * @param bstrURL
	 * @return
	 */
	public IWMPMedia newMedia(String bstrURL);


	/**
	 * Opens the player with the specified URL
	 * 
	 * @param bstrURL
	 */
	public void openPlayer(String bstrURL);


	/**
	 * Creates a new playlist object
	 * 
	 * @param bstrName
	 * @param bstrURL
	 * @return
	 */
	public IWMPPlaylist newPlaylist(String bstrName, String bstrURL);


	/**
	 * Closes the media
	 * 
	 */
	public void close();


	/**
	 * Method launchURL
	 * 
	 * @param bstrURL
	 */
	public void launchURL(String bstrURL);


	/**
	 * Getter for property URL
	 * 
	 * @return URL value
	 */
	@Getter("URL")
	public String getURL();

	/**
	 * Setter for property URL
	 * 
	 * @param value New URL value
	 */
	@Setter("URL")
	public void setURL(String value);

	/**
	 * Getter for property openState
	 * 
	 * @return openState value
	 */
	@Getter("openState")
	public int getOpenState();

	/**
	 * Getter for property playState
	 * 
	 * @return playState value
	 */
	@Getter("playState")
	public int getPlayState();

	/**
	 * Getter for property controls
	 * 
	 * @return controls value
	 */
	@Getter("controls")
	public IWMPControls getControls();

	/**
	 * Getter for property settings
	 * 
	 * @return settings value
	 */
	@Getter("settings")
	public IWMPSettings getSettings();

	/**
	 * Getter for property currentMedia
	 * 
	 * @return currentMedia value
	 */
	@Getter("currentMedia")
	public IWMPMedia getCurrentMedia();

	/**
	 * Setter for property currentMedia
	 * 
	 * @param value New currentMedia value
	 */
	@Setter("currentMedia")
	public void setCurrentMedia(IWMPMedia value);

	/**
	 * Getter for property mediaCollection
	 * 
	 * @return mediaCollection value
	 */
	@Getter("mediaCollection")
	public IWMPMediaCollection getMediaCollection();

	/**
	 * Getter for property playlistCollection
	 * 
	 * @return playlistCollection value
	 */
	@Getter("playlistCollection")
	public IWMPPlaylistCollection getPlaylistCollection();

	/**
	 * Getter for property versionInfo
	 * 
	 * @return versionInfo value
	 */
	@Getter("versionInfo")
	public String getVersionInfo();

	/**
	 * Getter for property network
	 * 
	 * @return network value
	 */
	@Getter("network")
	public IWMPNetwork getNetwork();

	/**
	 * Getter for property currentPlaylist
	 * 
	 * @return currentPlaylist value
	 */
	@Getter("currentPlaylist")
	public IWMPPlaylist getCurrentPlaylist();

	/**
	 * Setter for property currentPlaylist
	 * 
	 * @param value New currentPlaylist value
	 */
	@Setter("currentPlaylist")
	public void setCurrentPlaylist(IWMPPlaylist value);

	/**
	 * Getter for property cdromCollection
	 * 
	 * @return cdromCollection value
	 */
	@Getter("cdromCollection")
	public IWMPCdromCollection getCdromCollection();

	/**
	 * Getter for property closedCaption
	 * 
	 * @return closedCaption value
	 */
	@Getter("closedCaption")
	public IWMPClosedCaption getClosedCaption();

	/**
	 * Getter for property isOnline
	 * 
	 * @return isOnline value
	 */
	@Getter("isOnline")
	public boolean getIsOnline();

	/**
	 * Getter for property Error
	 * 
	 * @return Error value
	 */
	@Getter("Error")
	public IWMPError getError();

	/**
	 * Getter for property status
	 * 
	 * @return status value
	 */
	@Getter("status")
	public String getStatus();

	/**
	 * Getter for property dvd
	 * 
	 * @return dvd value
	 */
	@Getter("dvd")
	public IWMPDVD getDvd();

	/**
	 * Getter for property enabled
	 * 
	 * @return enabled value
	 */
	@Getter("enabled")
	public boolean getEnabled();

	/**
	 * Setter for property enabled
	 * 
	 * @param value New enabled value
	 */
	@Setter("enabled")
	public void setEnabled(boolean value);

	/**
	 * Getter for property fullScreen
	 * 
	 * @return fullScreen value
	 */
	@Getter("fullScreen")
	public boolean getFullScreen();

	/**
	 * Setter for property fullScreen
	 * 
	 * @param value New fullScreen value
	 */
	@Setter("fullScreen")
	public void setFullScreen(boolean value);

	/**
	 * Getter for property enableContextMenu
	 * 
	 * @return enableContextMenu value
	 */
	@Getter("enableContextMenu")
	public boolean getEnableContextMenu();

	/**
	 * Setter for property enableContextMenu
	 * 
	 * @param value New enableContextMenu value
	 */
	@Setter("enableContextMenu")
	public void setEnableContextMenu(boolean value);

	/**
	 * Getter for property uiMode
	 * 
	 * @return uiMode value
	 */
	@Getter("uiMode")
	public String getUiMode();

	/**
	 * Setter for property uiMode
	 * 
	 * @param value New uiMode value
	 */
	@Setter("uiMode")
	public void setUiMode(String value);

	/**
	 * Getter for property stretchToFit
	 * 
	 * @return stretchToFit value
	 */
	@Getter("stretchToFit")
	public boolean getStretchToFit();

	/**
	 * Setter for property stretchToFit
	 * 
	 * @param value New stretchToFit value
	 */
	@Setter("stretchToFit")
	public void setStretchToFit(boolean value);

	/**
	 * Getter for property windowlessVideo
	 * 
	 * @return windowlessVideo value
	 */
	@Getter("windowlessVideo")
	public boolean getWindowlessVideo();

	/**
	 * Setter for property windowlessVideo
	 * 
	 * @param value New windowlessVideo value
	 */
	@Setter("windowlessVideo")
	public void setWindowlessVideo(boolean value);

	/**
	 * Getter for property isRemote
	 * 
	 * @return isRemote value
	 */
	@Getter("isRemote")
	public boolean getIsRemote();

	/**
	 * Getter for property playerApplication
	 * 
	 * @return playerApplication value
	 */
	@Getter("playerApplication")
	public IWMPPlayerApplication getPlayerApplication();
}