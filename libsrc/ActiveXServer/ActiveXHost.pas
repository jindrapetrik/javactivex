 (******************************************************************************
*                                  rtAXHost                                   *
*                                                                             *
* Here we define the TOleControl descendent that hosts an ActiveX control at  *
* Runtime.                                                                    *
*                                                                             *
*   Based on Chris Bells code on http://www.doogal.co.uk/activex.php          *
*******************************************************************************)
unit ActiveXHost;

interface

uses 
  Windows, ActiveX, Classes, Graphics, OleCtrls, StdVCL, controls, dialogs,
  TypeLibViewer,MyComObj;

type
  TEventEvent = procedure(Sender : TObject; EventName : string;
    EventParams : array of Variant;
    EventParamTypes : array of Variant;
    EventParamTypesStr : array of Variant;
    EventParamNames : array of Variant) of object;




	TActiveXHost = class(TOleControl,IComObj)
  	private
      FOnEvent: TEventEvent;
    	function GetProperties: TStrings;
    	function GetPropertyValue(AName: WideString): OleVariant;
    	procedure SetPropertyValue(AName: WideString; const Value: OleVariant);
      function NameToDispID(Intf : IDispatch; AName : WideString) : integer;
    	function GetMethods: TStrings;
      function GetEvents: TStrings;
      procedure Initialize(AFileName : string);
      procedure TriggerOnEvent(AEventName: string; Params: TDispParams);
      function getDocString():WideString;
   	protected
    	FIntf: IDispatch;
    	FClassID: TGUID;
    	CControlData: TControlData;
      FProps : TStringList;
      FMethods : TStringList;
      FEvents : TStringList;
      FTypeLibViewer : TTypeLibViewer;
      FClassname : string;
    	procedure InitControlData; override;
    	procedure InitControlInterface(const Obj: IUnknown); override;
      procedure StandardEvent(DispID: TDispID; var Params: TDispParams); override;
      procedure InvokeEvent(DispID: TDispID; var Params: TDispParams); override;
      function GetControlClassName:string;
      function GetClassId:TGUID;
   	public
      function GetObj:IUnknown;
      constructor CreateActiveX(AOwner : TComponent; AFileName : string; AClassID : TGUID); overload;
    	constructor CreateActiveX(AOwner : TComponent; AClassID : TGUID); overload;
      destructor Destroy; override;
      function FindFunction(AMethodName: string) : TFunction;
      function FindImplementingIterfaceForFunction(AMethodName: string) : IDispatch;
      function InvokeMethod(AMethodName : WideString) : OleVariant; overload;
      function InvokeMethod(AMethodName : WideString;
        var AParams : array of TVariantArg) : OleVariant; overload;
      class function FileName(AClassID : TGUID) : string;
      // properties
      property Properties : TStrings read GetProperties;
      property Methods : TStrings read GetMethods;
      property Events : TStrings read GetEvents;
      property ClassID : TGUID read FClassID;
      property DocString : WideString read getDocString;
      property PropertyValue[AName : WideString] : OleVariant read GetPropertyValue
      	write SetPropertyValue;
      property ControlClassName :string read FClassName;
      function GetVariable(AVariableName: widestring) : TVariable;
      // events
      property OnEvent : TEventEvent read FOnEvent write FOnEvent;
  end;

////////////////////////////////////////////////////////////////////////////////

implementation

uses
	ComObj, AXCtrls, Registry, SysUtils,Messages,uMain;

////////////////////////////////////////////////////////////////////////////////

constructor TActiveXHost.CreateActiveX(AOwner : TComponent; AFileName : string;
  AClassID : TGUID);
begin
  FClassID := AClassID;
  // make sure it's registered
  RegisterComServer(AFileName);
  inherited Create(AOwner);
  Initialize(AFileName);
end;

////////////////////////////////////////////////////////////////////////////////

constructor TActiveXHost.CreateActiveX(AOwner: TComponent; AClassID: TGUID);
var
  LFileName : string;
begin
  LFileName := FileName(AClassID);
  FClassID := AClassID;
  try
    inherited Create(AOwner);
    Initialize(LFileName);
  except
    if not FileExists(LFileName) then
      raise Exception.Create('The file ' + LFileName + ' specified for the ActiveX control does not exist')
    else
      raise;
  end;
end;

////////////////////////////////////////////////////////////////////////////////

procedure TActiveXHost.Initialize(AFileName : string);
var
LTypeInfo:TTypeInformation;
i:integer;
begin;
  FTypeLibViewer := TTypeLibViewer.Create(FClassID,AFileName);
  LTypeInfo := FTypeLibViewer.FindGUID(FClassID);
  FClassname := LTypeInfo.Name;

  FMethods := TStringList.Create;
  FMethods.Duplicates := dupIgnore;

  FProps := TStringList.Create;
  FProps.Duplicates := dupIgnore;

  FEvents := TStringList.Create;
  FEvents.Duplicates := dupIgnore;
                                  
  for i := 0 to LTypeInfo.PropertyCount - 1 do
    FProps.AddObject(LTypeInfo.GetProperty(i).name,LTypeInfo.GetProperty(i));

  for i := 0 to LTypeInfo.FunctionCount - 1 do
    if LTypeInfo.GetFunction(i).InvokeKind = ikFunction then
      FMethods.Add(LTypeInfo.GetFunction(i).name);

  if assigned(LTypeInfo.DefaultFunctionInterface) then
    for i := 0 to LTypeInfo.DefaultFunctionInterface.FunctionCount - 1 do
      if LTypeInfo.DefaultFunctionInterface.GetFunction(i).InvokeKind=ikFunction then
        FMethods.Add(LTypeInfo.DefaultFunctionInterface.GetFunction(i).name);

  if assigned(LTypeInfo.DefaultEventInterface) then
    begin
      CControlData.EventIID := LTypeInfo.DefaultEventInterface.GUID;
      for i := 0 to LTypeInfo.DefaultEventInterface.FunctionCount - 1 do
        if LTypeInfo.DefaultEventInterface.GetFunction(i).InvokeKind = ikFunction then
          FEvents.Add(LTypeInfo.DefaultEventInterface.GetFunction(i).name);
    end;

end;

////////////////////////////////////////////////////////////////////////////////

destructor TActiveXHost.Destroy;
begin
  FreeAndNil(FTypeLibViewer);
  FreeAndNil(FEvents);
  FreeAndNil(FMethods);
	FreeAndNil(FProps);
  inherited;
end;

////////////////////////////////////////////////////////////////////////////////

procedure TActiveXHost.InitControlData;
begin
  with CControlData do
  begin
    ClassID := FClassID;
    EventCount := 0;
    EventDispIDs := nil;
    LicenseKey := nil;
    Flags := $00000000;
    Version := 300;
  end;

  ControlData := @CControlData;
end;

////////////////////////////////////////////////////////////////////////////////

procedure TActiveXHost.InitControlInterface(const Obj: IUnknown);
begin
  FIntf := Obj as IDispatch;
end;

////////////////////////////////////////////////////////////////////////////////

function TActiveXHost.GetProperties: TStrings;
begin
  Result := FProps;
end;

////////////////////////////////////////////////////////////////////////////////

function TActiveXHost.GetPropertyValue(AName: WideString): OleVariant;
begin
  Result := GetDispatchPropValue(FIntf, NameToDispID(FIntf, AName))
end;

////////////////////////////////////////////////////////////////////////////////

procedure TActiveXHost.SetPropertyValue(AName: WideString;
  const Value: OleVariant);
begin
  SetDispatchPropValue(FIntf, NameToDispID(FIntf, AName), Value);
end;

////////////////////////////////////////////////////////////////////////////////

function TActiveXHost.NameToDispID(Intf : IDispatch; AName: WideString): integer;
begin
	OleCheck(Intf.GetIDsOfNames(GUID_NULL, @AName, 1, GetThreadLocale,
  	@Result));
end;

////////////////////////////////////////////////////////////////////////////////

function TActiveXHost.GetMethods: TStrings;
begin
  Result := FMethods;
end;

function TActiveXHost.GetObj: IUnknown;
begin
  Result := FIntf;
end;

////////////////////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////////////////////

function TActiveXHost.InvokeMethod(AMethodName: WideString) : OleVariant;
var
  dispparamsNoArgs : TDispParams;
  LImplementingIntf : IDispatch;
begin
  LImplementingIntf := FindImplementingIterfaceForFunction(AMethodName);

  dispparamsNoArgs.rgvarg := nil;
  dispparamsNoArgs.rgdispidNamedArgs := nil;
  dispparamsNoArgs.cArgs := 0;
  dispparamsNoArgs.cNamedArgs := 0;
  OleCheck(LImplementingIntf.Invoke(NameToDispID(LImplementingIntf, AMethodName), GUID_NULL, GetThreadLocale,
    DISPATCH_METHOD, dispparamsNoArgs, @Result, nil, nil));
end;

////////////////////////////////////////////////////////////////////////////////

function TActiveXHost.InvokeMethod(AMethodName: WideString;
 var AParams : array of TVariantArg) : OleVariant;
var
  LDispParams : TDispParams;
  Loop : integer;
  LImplementingIntf : IDispatch;

begin
  LImplementingIntf := FindImplementingIterfaceForFunction(AMethodName);

  LDispParams.rgdispidNamedArgs := nil;
  LDispParams.cNamedArgs := 0;
  LDispParams.cArgs := Length(AParams);
  GetMem(LDispParams.rgvarg, LDispParams.cArgs * SizeOf(TVariantArg));
  try
    // the parameters have to be back-to-front
    for Loop := Low(AParams) to High(AParams) do
      LDispParams.rgvarg[High(AParams)-Loop] := AParams[Loop];

    OLECheck(LImplementingIntf.Invoke(NameToDispID(LImplementingIntf, AMethodName),
      GUID_NULL, GetThreadLocale, DISPATCH_METHOD, LDispParams, @Result, nil, nil));

    for Loop := Low(AParams) to High(AParams) do
      AParams[Loop] := LDispParams.rgvarg[High(AParams)-Loop];
  finally
    FreeMem(LDispParams.rgvarg);
  end;
end;

////////////////////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////////////////////

class function TActiveXHost.FileName(AClassID : TGUID) : string;
var
  LReg : TRegistry;
  LFullPath : PChar;

begin
  Result := '';
  LReg := TRegistry.Create(KEY_READ);  //LOCAL_MACHINE classes can'be opened with KEY_ALL_ACCESS
  try
    LReg.RootKey := HKEY_CLASSES_ROOT;
    if LReg.OpenKey('\CLSID\' + GUIDToString(AClassID) + '\InprocServer32', False) then
      Result := LReg.ReadString('');

    // expand out environment variable strings which may be in the regsitry
    LFullPath := StrAlloc(MAX_PATH);
    try
      ExpandEnvironmentStrings(PChar(Result), LFullPath, MAX_PATH);
      Result := LFullPath;
    finally
      StrDispose(LFullPath);
    end;

  finally
    LReg.Free;
  end;
end;

////////////////////////////////////////////////////////////////////////////////

function TActiveXHost.GetEvents: TStrings;
begin
  Result := FEvents;
end;

////////////////////////////////////////////////////////////////////////////////

procedure TActiveXHost.InvokeEvent(DispID: TDispID;
  var Params: TDispParams);
var
  LEventInfo : TTypeInformation;
  Loop : integer;
begin
  inherited;
  if Assigned(FTypeLibViewer) then
  begin
    LEventInfo := FTypeLibViewer.FindGUID(CControlData.EventIID);
    if Assigned(LEventInfo) then
    begin
      for Loop := 0 to LEventInfo.FunctionCount-1 do
      begin
        if LEventInfo.GetFunction(Loop).MemberID = DispID then
          TriggerOnEvent(LEventInfo.GetFunction(Loop).Name, Params);
      end;
    end;
  end;
end;

////////////////////////////////////////////////////////////////////////////////

procedure TActiveXHost.StandardEvent(DispID: TDispID;
  var Params: TDispParams);
var
  LEventName : string;
begin
  inherited;
  case DispID of
    DISPID_CLICK      : LEventName := 'Click';
    DISPID_DBLCLICK   : LEventName := 'DblClick';
    DISPID_KEYDOWN    : LEventName := 'KeyDown';
    DISPID_KEYPRESS   : LEventName := 'KeyPress';
    DISPID_KEYUP      : LEventName := 'KeyUp';
    DISPID_MOUSEDOWN  : LEventName := 'MouseDown';
    DISPID_MOUSEMOVE  : LEventName := 'MouseMove';
    DISPID_MOUSEUP    : LEventName := 'MouseUp';
  else
    LEventName := 'Unknown event';
  end;
  TriggerOnEvent(LEventName, Params);
end;

////////////////////////////////////////////////////////////////////////////////

procedure TActiveXHost.TriggerOnEvent(AEventName : string; Params: TDispParams);
var
  LParams : array of Variant;
  Loop : integer;
  LThisParam : TVariantArg;
  LParamTypes : array of Variant;
  LParamTypesStr : array of Variant;
  LParamnames : array of Variant;
  fun:TFunction;
  pn:integer;
begin
  if Assigned(FOnEvent) then
  begin
    fun := FindFunction(AEventName);

    SetLength(LParams, Params.cArgs);

    pn := 0;
    if assigned(fun) then
    begin
      pn:=fun.ParameterCount;
      if pn<Params.cArgs then pn:=Params.cArgs;

      SetLength(LParamnames, pn);
      SetLength(LParamTypesStr, pn);
      SetLength(LPAramTypes, pn);
    end;

    for Loop := 0 to Params.cArgs-1 do
    begin
      if Loop<pn then
      begin
        LParamnames[Loop] := fun.Parameter(pn-Loop-1).Name;
        LParamTypesStr[Loop] := fun.Parameter(pn-Loop-1).FullType;
        LPAramTypes[Loop] := fun.Parameter(pn-Loop-1).ParamType;
      end;
      LThisParam := Params.rgvarg[Loop];
      case LThisParam.vt of
        VT_UI1:                  LParams[Loop] := LThisParam.bVal;
        VT_I2:                   LParams[Loop] := LThisParam.iVal;
        VT_I4:                   LParams[Loop] := LThisParam.lVal;
        VT_R4:                   LParams[Loop] := LThisParam.fltVal;
        VT_R8:                   LParams[Loop] := LThisParam.dblVal;
        VT_BOOL:                 LParams[Loop] := LThisParam.vbool;
        VT_ERROR:                LParams[Loop] := LThisParam.scode;
        VT_CY:                   LParams[Loop] := LThisParam.cyVal;
        VT_DATE:                 LParams[Loop] := LThisParam.date;
        //VT_BSTR:                 LParams[Loop] := LThisParam.bstrVal;
        //VT_UNKNOWN:              LParams[Loop] := LThisParam.unkVal;
        //VT_DISPATCH:             LParams[Loop] := LThisParam.dispVal;
        //VT_ARRAY:                LParams[Loop] := LThisParam.parray;
        //VT_BYREF or VT_UI1:      LParams[Loop] := LThisParam.pbVal;
        //VT_BYREF or VT_I2:       LParams[Loop] := LThisParam.piVal;
        //VT_BYREF or VT_I4:       LParams[Loop] := LThisParam.plVal;
        {VT_BYREF or VT_R4:       LParams[Loop] := LThisParam.pfltVal;
        VT_BYREF or VT_R8:       LParams[Loop] := LThisParam.pdblVal;
        VT_BYREF or VT_BOOL:     LParams[Loop] := LThisParam.pbool;
        VT_BYREF or VT_ERROR:    LParams[Loop] := LThisParam.pscode: ^HResult);
        VT_BYREF or VT_CY:       LParams[Loop] := LThisParam.pcyVal: ^Currency);
        VT_BYREF or VT_DATE:     LParams[Loop] := LThisParam.pdate: ^TOleDate);
        VT_BYREF or VT_BSTR:     LParams[Loop] := LThisParam.pbstrVal: ^WideString);
        VT_BYREF or VT_UNKNOWN:  LParams[Loop] := LThisParam.punkVal: ^IUnknown);
        VT_BYREF or VT_DISPATCH: LParams[Loop] := LThisParam.pdispVal: ^IDispatch);
        VT_BYREF or VT_ARRAY:    LParams[Loop] := LThisParam.pparray: ^PSafeArray);
        VT_BYREF or VT_VARIANT:  LParams[Loop] := LThisParam.pvarVal: PVariant);
        VT_BYREF:                LParams[Loop] := LThisParam.byRef: Pointer);}
        VT_I1:                   LParams[Loop] := LThisParam.cVal;
        VT_UI2:                  LParams[Loop] := LThisParam.uiVal;
        VT_UI4:                  LParams[Loop] := LThisParam.ulVal;
        VT_INT:                  LParams[Loop] := LThisParam.intVal;
        VT_UINT:                 LParams[Loop] := LThisParam.uintVal;
        {VT_BYREF or VT_DECIMAL:  LParams[Loop] := LThisParam.pdecVal;
        VT_BYREF or VT_I1:       LParams[Loop] := LThisParam.pcVal: PChar);
        VT_BYREF or VT_UI2:      LParams[Loop] := LThisParam.puiVal: PWord);
        VT_BYREF or VT_UI4:      LParams[Loop] := LThisParam.pulVal: PInteger);
        VT_BYREF or VT_INT:      LParams[Loop] := LThisParam.pintVal: PInteger);
        VT_BYREF or VT_UINT:     LParams[Loop] := LThisParam.puintVal: PLongWord);}
      end;
    end;

    FOnEvent(Self, AEventName, LParams,LParamTypes,LParamTypesStr,LParamnames);
  end;
end;

////////////////////////////////////////////////////////////////////////////////

function TActiveXHost.FindFunction(AMethodName: string) : TFunction;
var
  LTypeInfo : TTypeInformation;
  ImplementingType : TTypeInformation;
begin
  Result := nil;
  LTypeInfo := FTypeLibViewer.FindGUID(FClassID);
  if Assigned(LTypeInfo) then
  begin
    Result := LTypeInfo.FindFunction(AMethodName, ImplementingType);
  end;
end;


function TActiveXHost.getDocString():WideString;
var
  LTypeInfo : TTypeInformation;
begin
  LTypeInfo := FTypeLibViewer.FindGUID(FClassID);
  Result := LTypeInfo.DocString;
end;

////////////////////////////////////////////////////////////////////////////////

function TActiveXHost.FindImplementingIterfaceForFunction(AMethodName: string) : IDispatch;
var
  LTypeInfo : TTypeInformation;
  ImplementingType : TTypeInformation;
begin
  Result := nil;
  LTypeInfo := FTypeLibViewer.FindGUID(FClassID);
  if Assigned(LTypeInfo) then
  begin
    LTypeInfo.FindFunction(AMethodName, ImplementingType);
    OleCheck(FIntf.QueryInterface(ImplementingType.GUID, Result));
  end;
end;


function TActiveXHost.GetVariable(AVariableName: widestring) : TVariable;
var
  LTypeInfo : TTypeInformation;
begin
  LTypeInfo := FTypeLibViewer.FindGUID(FClassID);
  Result := nil;
  if Assigned(LTypeInfo) then
  begin
    Result := LTypeInfo.FindVariable(AVariableName);
  end;
end;

////////////////////////////////////////////////////////////////////////////////


function TActiveXHost.GetControlClassName:string;
begin
  Result:=self.FClassname;
end;

function TActiveXHost.GetClassId:TGUID;
begin
  Result:=self.FClassID;
end;



end.
