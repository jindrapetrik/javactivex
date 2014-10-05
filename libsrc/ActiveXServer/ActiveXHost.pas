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
  TypeLibViewer;

type
  TEventEvent = procedure(Sender : TObject; EventName : string;
    EventParams : array of Variant) of object;

  TProperty = class
  private
     fid:integer;
     fname:string;
     fType:TVarType;
     fwritable : Boolean;
     freadable : Boolean;
     function PropTypeAsString:string;

    public
    property name:string read fname;
    property id:integer read fid;
    property propType:TVarType read fType;
    property typeAsString:string read propTypeAsString;
    property writable:Boolean read fwritable;
    property readable:Boolean read freadable;

  end;

	TActiveXHost = class(TOleControl)
  	private
      FOnEvent: TEventEvent;
    	function GetProperties: TStrings;
    	function GetPropertyValue(AName: WideString): OleVariant;
    	procedure SetPropertyValue(AName: WideString; const Value: OleVariant);
      function NameToDispID(Intf : IDispatch; AName : WideString) : integer;
    	function GetMethods: TStrings;
      procedure GetMethodsList;
      procedure PopulateEventList;
      function GetEvents: TStrings;
      procedure Initialize(AFileName : string);
      procedure PopulateFunctions(AList: TStrings; AEvents: Boolean);
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
   	public
      constructor CreateActiveX(AOwner : TComponent; AFileName : string; AClassID : TGUID); overload;
    	constructor CreateActiveX(AOwner : TComponent; AClassID : TGUID); overload;
      destructor Destroy; override;
      function FindFunction(AMethodName: string) : TFunction;
      function FindImplementingIterfaceForFunction(AMethodName: string) : IDispatch;
      function InvokeMethod(AMethodName : WideString) : OleVariant; overload;
      function InvokeMethod(AMethodName : WideString;
        AParams : array of OLEVariant) : OleVariant; overload;
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

  procedure EnumDispatchProperties(Dispatch: IDispatch; PropType: TGUID;
    VTCode: Integer; PropList: TStrings);

////////////////////////////////////////////////////////////////////////////////

implementation

uses
	ComObj, AXCtrls, Registry, SysUtils;

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
begin;
  FTypeLibViewer := TTypeLibViewer.Create(AFileName);

  LTypeInfo := FTypeLibViewer.FindGUID(FClassID);
  FClassname := LTypeInfo.Name;

  // get properties
  FProps := TStringList.Create;
  FProps.Duplicates := dupIgnore;
  EnumDispatchProperties(FIntf, GUID_NULL, VT_EMPTY, FProps);

  // get methods
  FMethods := TStringList.Create;
  FMethods.Duplicates := dupIgnore;
  GetMethodsList;

  FEvents := TStringList.Create;
  FEvents.Duplicates := dupIgnore;
  PopulateEventList;
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

////////////////////////////////////////////////////////////////////////////////

procedure TActiveXHost.GetMethodsList;
begin
  PopulateFunctions(FMethods, False);
end;

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
  AParams : array of OLEVariant) : OleVariant;
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
      LDispParams.rgvarg[High(AParams)-Loop] := TVariantArg(AParams[Loop]);

    OLECheck(LImplementingIntf.Invoke(NameToDispID(LImplementingIntf, AMethodName),
      GUID_NULL, GetThreadLocale, DISPATCH_METHOD, LDispParams, @Result, nil, nil));
  finally
    FreeMem(LDispParams.rgvarg);
  end;
end;

////////////////////////////////////////////////////////////////////////////////

procedure TActiveXHost.PopulateEventList;
begin
  PopulateFunctions(FEvents, True);
end;

////////////////////////////////////////////////////////////////////////////////

procedure TActiveXHost.PopulateFunctions(AList : TStrings; AEvents : Boolean);
var
  LTypeInfo : TTypeInformation;
  Loop, LFuncLoop : integer;
  LEvents : TImplementedTypeInformation;
  LInterested : Boolean;
  LThisFunction : TFunction;
begin
  LTypeInfo := FTypeLibViewer.FindGUID(FClassID);
  if Assigned(LTypeInfo) then
  begin
    for Loop := 0 to LTypeInfo.ImplementedInterfacesCount-1 do
    begin



      // are we interested in this interface?
      if (tfSource in LTypeInfo.ImplementedInterface(Loop).TypeFlags) then
        LInterested := AEvents
      else
        LInterested := not AEvents;

      LInterested := LInterested and (tfDefault in LTypeInfo.ImplementedInterface(Loop).TypeFlags);

      if LInterested then
      begin
        // we've found an event sink
        LEvents := LTypeInfo.ImplementedInterface(Loop);
        for LFuncLoop := 0 to LEvents.FunctionCount-1 do
        begin
          LThisFunction := LEvents.GetFunction(LFuncLoop);
          if LThisFunction.InvokeKind = ikFunction then
            AList.Add(LThisFunction.Name);
        end;

        // this ensures we sink the events via the InvokeEvent and StandardEvent methods
        CControlData.EventIID := LEvents.GUID;
      end;
    end;
  end;
end;

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
begin
  if Assigned(FOnEvent) then
  begin
    SetLength(LParams, Params.cArgs);
    for Loop := 0 to Params.cArgs-1 do
    begin
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

    FOnEvent(Self, AEventName, LParams);
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

procedure EnumDispatchProperties(Dispatch: IDispatch; PropType: TGUID;
  VTCode: Integer; PropList: TStrings);
const
  INVOKE_PROPERTYSET = INVOKE_PROPERTYPUT or INVOKE_PROPERTYPUTREF;
var
  I: Integer;
  TypeInfo: ITypeInfo;
  TypeAttr: PTypeAttr;
  FuncDesc: PFuncDesc;
  VarDesc: PVarDesc;

  procedure SaveName(Id: Integer;pt:word;readable:Boolean;writable:Boolean);
  var
    Name: WideString;
    v:TProperty;
    k:integer;
  begin

    for k := 0 to PropList.Count - 1 do
      begin
        if (PropList.Objects[k] as TProperty).fid = Id then
         begin
           if readable then (PropList.Objects[k] as TProperty).freadable := true;
           if writable then (PropList.Objects[k] as TProperty).fwritable := true;
           Exit;
         end;
      end;
    OleCheck(TypeInfo.GetDocumentation(Id, @Name, nil, nil, nil));
      v := TProperty.Create;
      v.fid := Id;
      v.fname := Name;
      v.fType := pt;
      v.fwritable := writable;
      v.freadable := readable;
      PropList.AddObject(Name, v);
  end;

  function IsPropType(const TypeInfo: ITypeInfo; TypeDesc: PTypeDesc): Boolean;
  var
    RefInfo: ITypeInfo;
    RefAttr: PTypeAttr;
    IsNullGuid: Boolean;
  begin
    IsNullGuid := IsEqualGuid(PropType, GUID_NULL);
    Result := IsNullGuid and (VTCode = VT_EMPTY);
    if Result then Exit;
    case TypeDesc.vt of
      VT_PTR: Result := IsPropType(TypeInfo, TypeDesc.ptdesc);
      VT_USERDEFINED:
        begin
          OleCheck(TypeInfo.GetRefTypeInfo(TypeDesc.hreftype, RefInfo));
          OleCheck(RefInfo.GetTypeAttr(RefAttr));
          try
            Result := IsEqualGUID(RefAttr.guid, PropType);
            if not Result and (RefAttr.typekind = TKIND_ALIAS) then
              Result := IsPropType(RefInfo, @RefAttr.tdescAlias);
          finally
            RefInfo.ReleaseTypeAttr(RefAttr);
          end;
        end;
    else
      Result := IsNullGuid and (TypeDesc.vt = VTCode);
    end;
  end;

  function HasMember(const TypeInfo: ITypeInfo; Cnt, MemID, InvKind: Integer): Boolean;
  var
    I: Integer;
    FuncDesc: PFuncDesc;
  begin
    for I := 0 to Cnt - 1 do
    begin
      OleCheck(TypeInfo.GetFuncDesc(I, FuncDesc));
      try
        if (FuncDesc.memid = MemID) and (FuncDesc.invkind and InvKind <> 0) then
        begin
          Result := True;
          Exit;
        end;
      finally
        TypeInfo.ReleaseFuncDesc(FuncDesc);
      end;
    end;
    Result := False;
  end;

begin
  OleCheck(Dispatch.GetTypeInfo(0,0,TypeInfo));
  if TypeInfo = nil then Exit;
  OleCheck(TypeInfo.GetTypeAttr(TypeAttr));
  try
    for I := 0 to TypeAttr.cVars - 1 do
    begin
      OleCheck(TypeInfo.GetVarDesc(I, VarDesc));
      try
        //if (VarDesc.wVarFlags and VARFLAG_FREADONLY <> 0) and
        if IsPropType(TypeInfo, @VarDesc.elemdescVar.tdesc) then
          SaveName(VarDesc.memid,VarDesc.elemdescVar.tdesc.vt,true,VarDesc.wVarFlags and VARFLAG_FREADONLY = 0);
      finally
        TypeInfo.ReleaseVarDesc(VarDesc);
      end;
    end;
    for I := 0 to TypeAttr.cFuncs - 1 do
    begin
      OleCheck(TypeInfo.GetFuncDesc(I, FuncDesc));
      try
        if ((FuncDesc.invkind = INVOKE_PROPERTYGET) and (FuncDesc.cParams < 1) and
          HasMember(TypeInfo, TypeAttr.cFuncs, FuncDesc.memid, INVOKE_PROPERTYSET) and
          IsPropType(TypeInfo, @FuncDesc.elemdescFunc.tdesc)) then
          begin
            SaveName(FuncDesc.memid,FuncDesc.elemdescFunc.tdesc.vt,true,false);
          end;

          if ((FuncDesc.invkind and INVOKE_PROPERTYSET <> 0) and (FuncDesc.cParams < 2) and
          IsPropType(TypeInfo,
            @FuncDesc.lprgelemdescParam[FuncDesc.cParams - 1].tdesc)) then
            begin
              SaveName(FuncDesc.memid,FuncDesc.lprgelemdescParam[FuncDesc.cParams - 1].tdesc.vt,false,true);
            end;
      finally
        TypeInfo.ReleaseFuncDesc(FuncDesc);
      end;
    end;
  finally
    TypeInfo.ReleaseTypeAttr(TypeAttr);
  end;
end;


function TProperty.PropTypeAsString:string;
begin
   Result := TypeToString(Self.ftype);
end;

end.
