unit MyComObj;

interface

uses Classes,TypeLibViewer,Sysutils,ComObj,ActiveX,Windows,Registry;

type


 IRecordInfo = interface(IUnknown)
    ['{0000002F-0000-0000-C000-000000000046}']
    function RecordInit(out pvNew: Pointer): HResult; stdcall;
    function RecordClear(var pvExisting: Pointer): HResult; stdcall;
    function RecordCopy(var pvExisting: Pointer; out pvNew: Pointer): HResult; stdcall;
    function GetGuid(out pguid: TGUID): HResult; stdcall;
    function GetName(out pbstrName: WideString): HResult; stdcall;
    function GetSize(out pcbSize: LongWord): HResult; stdcall;
    function GetTypeInfo(out ppTypeInfo: ITypeInfo): HResult; stdcall;
    function GetField(var pvData: Pointer; szFieldName: PWideChar; out pvarField: OleVariant): HResult; stdcall;
    function GetFieldNoCopy(var pvData: Pointer; szFieldName: PWideChar; out pvarField: OleVariant;
                            out ppvDataCArray: Pointer): HResult; stdcall;
    function PutField(wFlags: LongWord; var pvData: Pointer; szFieldName: PWideChar;
                      var pvarField: OleVariant): HResult; stdcall;
    function PutFieldNoCopy(wFlags: LongWord; var pvData: Pointer; szFieldName: PWideChar;
                            var pvarField: OleVariant): HResult; stdcall;
    function GetFieldNames(var pcNames: LongWord; out rgBstrNames: WideString): HResult; stdcall;
    function IsMatchingType(const pRecordInfo: IRecordInfo): Integer; stdcall;
    function RecordCreate: Pointer; stdcall;
    function RecordCreateCopy(var pvSource: Pointer; out ppvDest: Pointer): HResult; stdcall;
    function RecordDestroy(var pvRecord: Pointer): HResult; stdcall;
  end;



  IComObj = interface
     function GetObj:IUnknown;
     function GetDocString:WideString;
     function GetPropertyValue(AName: WideString): OleVariant;
    	procedure SetPropertyValue(AName: WideString; const Value: OleVariant);

      function GetControlClassName:string;
      function GetClassId:TGUID;
      	function GetMethods: TStrings;
        function GetProperties: TStrings;

     function FindFunction(AMethodName: string) : TFunction;
      function FindImplementingIterfaceForFunction(AMethodName: string) : IDispatch;
      function InvokeMethod(AMethodName : WideString) : OleVariant; overload;
      function InvokeMethod(AMethodName : WideString;
        var AParams : array of TVariantArg) : OleVariant; overload;
      property Properties : TStrings read GetProperties;
      property Methods : TStrings read GetMethods;
      property ClassID : TGUID read GetClassId;
      property DocString : WideString read GetDocString;
      property PropertyValue[AName : WideString] : OleVariant read GetPropertyValue
      	write SetPropertyValue;
      property ControlClassName :string read GetControlClassName;
      function GetVariable(AVariableName: widestring) : TVariable;
  end;

   TComObj = class(TInterfacedObject,IComObj)
   private
      function GetProperties: TStrings;
    	function GetPropertyValue(AName: WideString): OleVariant;
    	procedure SetPropertyValue(AName: WideString; const Value: OleVariant);
      function NameToDispID(Intf : IDispatch; AName : WideString) : integer;
    	function GetMethods: TStrings;
      //procedure GetMethodsList;
      procedure Initialize(AFileName : string;AUnkIntf:IUnknown);
      function getDocString():WideString;
      function GetControlClassName:string;
      function GetClassId:TGUID;
   protected
      FDispIntf: IDispatch;
      FUnkIntf : IUnknown;
      FRecordIntf : IRecordInfo;
    	FClassID: TGUID;
      FProps : TStringList;
      FMethods : TStringList;
      FTypeLibViewer : TTypeLibViewer;
      FClassname : string;
      FBaseGuid : TGUID;
   public

      function GetObj:IUnknown;
      constructor Create(AFileName : string; ABaseClassId:TGUID; AClassID : TGUID); overload;
    	constructor Create(ABaseClassId:TGUID; AClassID : TGUID); overload;

       constructor Create(AFileName : string; ABaseClassId:TGUID; AClassID : TGUID; AUnkIntf:IUnknown); overload;
    	constructor Create(ABaseClassId:TGUID; AClassID : TGUID; AUnkIntf:IUnknown); overload;

      destructor Destroy; override;
      function FindFunction(AMethodName: string) : TFunction;
      function FindImplementingIterfaceForFunction(AMethodName: string) : IDispatch;
      function InvokeMethod(AMethodName : WideString) : OleVariant; overload;
      function InvokeMethod(AMethodName : WideString; var AParams : array of TVariantArg) : OleVariant; overload;
      class function FileName(AClassID : TGUID) : string;
      // properties
      property Properties : TStrings read GetProperties;
      property Methods : TStrings read GetMethods;
      property ClassID : TGUID read FClassID;
      property DocString : WideString read getDocString;
      property PropertyValue[AName : WideString] : OleVariant read GetPropertyValue
      	write SetPropertyValue;
      property ControlClassName :string read GetControlClassName;
      function GetVariable(AVariableName: widestring) : TVariable;
   end;
implementation



constructor TComObj.Create(AFileName : string;
  ABaseClassId:TGUID; AClassID : TGUID);
begin
  Create(AFileName,ABaseClassId,AClassID,nil);
end;

constructor TComObj.Create(AFileName : string;
  ABaseClassId:TGUID; AClassID : TGUID; AUnkIntf:IUnknown);
begin
  FClassID := AClassID;
  FBaseGuid := ABaseClassId;
  // make sure it's registered
  RegisterComServer(AFileName);
  inherited Create();
  Initialize(AFileName,AUnkIntf);
end;

////////////////////////////////////////////////////////////////////////////////

constructor TComObj.Create(ABaseClassId:TGUID; AClassID: TGUID);
begin
  Create(ABaseClassId,AClassID,nil);
end;

constructor TComObj.Create(ABaseClassId:TGUID; AClassID: TGUID; AUnkIntf:IUnknown);
var
  LFileName : string;
begin
  LFileName := FileName(ABaseClassId);
  FClassID := AClassID;
  FBaseGuid := ABaseClassId;
  try
    inherited Create();
    Initialize(LFileName,AUnkIntf);
  except
    if not FileExists(LFileName) then
      raise Exception.Create('The file ' + LFileName + ' specified for the ActiveX control does not exist')
    else
      raise;
  end;
end;

////////////////////////////////////////////////////////////////////////////////

procedure TComObj.Initialize(AFileName : string; AUnkIntf:IUnknown);
var
LTypeInfo:TTypeInformation;
i:integer;
begin;
  FTypeLibViewer := TTypeLibViewer.Create(FBaseGuid,AFileName);

  LTypeInfo := FTypeLibViewer.FindGUID(FClassID);
  FClassname := LTypeInfo.Name;

  FMethods := TStringList.Create;
  FMethods.Duplicates := dupIgnore;

  FProps := TStringList.Create;
  FProps.Duplicates := dupIgnore;


  for i := 0 to LTypeInfo.PropertyCount - 1 do
    FProps.Add(LTypeInfo.GetProperty(i).name);

  for i := 0 to LTypeInfo.FunctionCount - 1 do
   if LTypeInfo.GetFunction(i).InvokeKind = ikFunction then
   begin
    FMethods.Add(LTypeInfo.GetFunction(i).name);
   end;

  if assigned(LTypeInfo.DefaultFunctionInterface) then
    for i := 0 to LTypeInfo.DefaultFunctionInterface.FunctionCount - 1 do
      if LTypeInfo.DefaultFunctionInterface.GetFunction(i).InvokeKind = ikFunction then
        FMethods.Add(LTypeInfo.DefaultFunctionInterface.GetFunction(i).name);


  if not assigned(AUnkIntf) then
  begin
    if not Succeeded(CoCreateInstance(FClassID,nil,CLSCTX_INPROC_SERVER or CLSCTX_LOCAL_SERVER,IUnknown,FUnkIntf)) then
    begin
      raise Exception.Create('Cannot create instance');
    end;
  end
  else
  begin
    FUnkIntf := AUnkIntf;
  end;

  FUnkIntf.QueryInterface(IDispatch,FDispIntf);
end;

////////////////////////////////////////////////////////////////////////////////

destructor TComObj.Destroy;
begin
  FreeAndNil(FTypeLibViewer);
  FreeAndNil(FMethods);
	FreeAndNil(FProps);
  inherited;
end;


////////////////////////////////////////////////////////////////////////////////

function TComObj.GetProperties: TStrings;
begin
  Result := FProps;
end;

////////////////////////////////////////////////////////////////////////////////

function TComObj.GetPropertyValue(AName: WideString): OleVariant;
begin
  if not assigned(FDispIntf) then
  begin
    Result:=0;
    Exit;
  end;

  Result := GetDispatchPropValue(FDispIntf, NameToDispID(FDispIntf, AName))
end;

////////////////////////////////////////////////////////////////////////////////

procedure TComObj.SetPropertyValue(AName: WideString;
  const Value: OleVariant);
begin
  if not assigned(FDispIntf) then
  begin
    Exit;
  end;
  SetDispatchPropValue(FDispIntf, NameToDispID(FDispIntf, AName), Value);
end;

////////////////////////////////////////////////////////////////////////////////

function TComObj.NameToDispID(Intf : IDispatch; AName: WideString): integer;
begin
	OleCheck(Intf.GetIDsOfNames(GUID_NULL, @AName, 1, GetThreadLocale,
  	@Result));
end;

////////////////////////////////////////////////////////////////////////////////

function TComObj.GetMethods: TStrings;
begin
  Result := FMethods;
end;

function TComObj.GetObj: IUnknown;
begin
  if assigned(FDispIntf) then
    Result:=FDispIntf
  else
    Result:=FUnkIntf;
end;

////////////////////////////////////////////////////////////////////////////////


////////////////////////////////////////////////////////////////////////////////

function TComObj.InvokeMethod(AMethodName: WideString) : OleVariant;
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


function TComObj.InvokeMethod(AMethodName : WideString;
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

class function TComObj.FileName(AClassID : TGUID) : string;
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


function TComObj.FindFunction(AMethodName: string) : TFunction;
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


function TComObj.getDocString():WideString;
var
  LTypeInfo : TTypeInformation;
begin
  LTypeInfo := FTypeLibViewer.FindGUID(FClassID);
  Result := LTypeInfo.DocString;
end;

////////////////////////////////////////////////////////////////////////////////

function TComObj.FindImplementingIterfaceForFunction(AMethodName: string) : IDispatch;
var
  LTypeInfo : TTypeInformation;
  ImplementingType : TTypeInformation;
begin
  Result := nil;
  LTypeInfo := FTypeLibViewer.FindGUID(FClassID);
  if Assigned(LTypeInfo) then
  begin
    LTypeInfo.FindFunction(AMethodName, ImplementingType);
    OleCheck(FUnkIntf.QueryInterface(ImplementingType.GUID, Result));
  end;
end;


function TComObj.GetVariable(AVariableName: widestring) : TVariable;
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



function TComObj.GetControlClassName:string;
begin
  Result:=self.FClassname;
end;

function TComObj.GetClassId:TGUID;
begin
  Result:=self.FClassID;
end;

end.
