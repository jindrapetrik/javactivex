(*******************************************************************************
 *   Based on Chris Bells code on http://www.doogal.co.uk/activex.php          *
 *******************************************************************************)
 
unit TypeLibViewer;

interface

uses
  ActiveX, Contnrs, Dialogs, Classes, Registry, Windows;

const
  VT_RECORD = 36;

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
  
  TTypeKind = (tkEnum, tkRecord, tkModule, tkInterface, tkDispatch, tkCoClass,
    tkAlias, tkUnion);

  TInvokeKind = (ikFunction, ikPropertyGetter, ikPropertySetter, ikPropertySetterRef);

  TImplementedTypeInformation = class;

  TVariable = class
    private
      FType : TVarType;
      FFullType : widestring;
      FName : widestring;
     public
      property VariableType : TVarType read FType;
      property FullType : widestring read FFullType;
      property Name : widestring read FName;
  end;

   TProperty = class
  private
     fid:integer;
     fname:string;
     fType:TVarType;
     fFullType:widestring;
     fwritable : Boolean;
     freadable : Boolean;

    public
    property name:string read fname;
    property id:integer read fid;
    property propType:TVarType read fType;
    property propFullType:widestring read fFullType;
    property writable:Boolean read fwritable;
    property readable:Boolean read freadable;

  end;

  TParameter = class
    private
      FType : TVarType;
      FFullType :WideString;
      FName : widestring;
      FPType:word;
    public
      property ParamType : TVarType read FType;
      property FullType : widestring read FFullType;
      property Name : widestring read FName;
      property Ptype:word read FPType;
  end;

  TFunction = class
    private
      FName : Widestring;
      FMemberID : TMemberID;
      FInvokeKind : TInvokeKind;
      FReturnType : TParameter;
      FParameters : TObjectList;
      FDocString : Widestring;
      FOptionalParamCount : Integer;
    public
      constructor Create;
      destructor Destroy; override;
      property OptionalParamCount:Integer read FOptionalParamCount;
      function Name : Widestring;
      function MemberID : TMemberID;
      function InvokeKind : TInvokeKind;
      function ParameterCount : integer;
      function Parameter(Index : integer) : TParameter;
      function ReturnType : TParameter;
      function DocString : Widestring;
  end;

  TTypeInformation = class
    private
      FName : WideString;
      FDocString : WideString;
      FHelpContext : Longint;
      FHelpFile : Widestring;
      FTypeAttr : PTypeAttr;
      FTypeInfo : ITypeInfo;
      FTypeKind : TTypeKind;
      FIntfList : TObjectList;
      FFuncList : TObjectList;
      FPropList : TObjectList;
      FMethods : TStringList;
      FEvents : TStringList;
      FProps : TStringList;
      FBaseGuid :TGUID;
      FDefaultEventInterface : TImplementedTypeInformation;
      FDefaultFunctionInterface : TImplementedTypeInformation;
      function GetImplementedInterface(Index: integer): ITypeInfo;
      procedure GetInterfaces;
      procedure GetFunctions;
      procedure GetProperties(TypeInfo: ITypeInfo;PropType: TGUID; VTCode: Integer);


    public

      class function FileName(AClassID : TGUID) : string;
      constructor Create(ABaseClassId:TGUID; AClassId : TGUID); overload;
      constructor Create(AFileName:string;ABaseClassId:TGUID; AClassId : TGUID); overload;
      constructor Create(ABaseClassId:TGUID;ATypeInfo : ITypeInfo); overload;
      destructor Destroy; override;
      // properties
      property Name : WideString read FName;
      property DocString : WideString read FDocString;
      property HelpContext : Longint read FHelpContext;
      property HelpFile : Widestring read FHelpFile;
      property TypeKind : TTypeKind read FTypeKind;
      property TypeInfo : ITypeInfo read FTypeInfo;
      property DefaultEventInterface : TImplementedTypeInformation read FDefaultEventInterface;
      property DefaultFunctionInterface : TImplementedTypeInformation read FDefaultFunctionInterface;
      // functions

      function FunctionCount : integer;
      function GetFunction(Index : integer) : TFunction;
      function FindFunction(AName : string; var ImplementingType : TTypeInformation) : TFunction;
      function VariableCount : integer;
      function Variable(Index : integer) : TVariable;
      function PropertyCount : integer;
      function GetProperty(Index:integer):TProperty;
      function FindProperty(AName: string):TProperty;
      function ImplementedInterfacesCount : integer;
      function ImplementedInterface(Index : integer) : TImplementedTypeInformation;
      function GUID : TGUID;
      function TypeKindString : string;
      function FindVariable(AName:widestring) : TVariable;
      property PropertyNames : TStringList read FProps;
      property MethodNames : TStringList read FMethods;
      property EventNames : TStringList read FEvents;
  end;



  TTypeFlags = set of (tfDefault, tfSource, tfRestricted, tfDefaultVTable);

  TImplementedTypeInformation = class(TTypeInformation)
    private
      FTypeFlags : TTypeFlags;
    public
      property TypeFlags : TTypeFlags read FTypeFlags;
  end;

  TTypeLibViewer = class
    protected
      FTypeLib : ITypeLib;
      FTypeList : TObjectList;
      FBaseClassId : TGUID;
      procedure Populate;
      function TypeInfo(Index : integer) : ITypeInfo;
    public
      constructor Create(ABaseClassId:TGUID;AFileName : WideString);
      destructor Destroy; override;
      function Count : integer;
      function TypeInformation(Index : integer) : TTypeInformation;
      function FindGUID(AGUID : TGUID) : TTypeInformation;
  end;

  function TypeToString(baseguid:TGUID;ttype:TTypeDesc; tinfo:ITypeInfo):WideString;

  function typedescvt_to_variantvt(	tinfo:ITypeInfo; tdesc:TYPEDESC;var vt:TVARTYPE):Boolean;
  function userdefined_to_variantvt	(tinfo:ITypeInfo;tdesc:TYPEDESC;var vt:TVARTYPE):Boolean;
//////////////////////////////////////////////////////////////////////////////

implementation

uses
  ComObj, SysUtils,TypInfo,uMain;



function GetRecordInfoFromTypeInfo(const TypeInfo: ITypeInfo; 
  out RecordInfo: IRecordInfo): HResult; stdcall; 
  external 'oleaut32.dll' name 'GetRecordInfoFromTypeInfo'; 


function userdefined_to_variantvt	(tinfo:ITypeInfo;tdesc:TYPEDESC;var vt:TVARTYPE):Boolean;
var hr:HRESULT;
tinfo2:ITypeInfo;
tattr:PTYPEATTR;

begin

    tinfo2 := nil;
//    tattr := nil;
     Result := true;

    hr:=tinfo.GetRefTypeInfo(tdesc.hreftype,tinfo2);
    //hr = ITypeInfo_GetRefTypeInfo(tinfo, tdesc->u.hreftype, &tinfo2);
    if (hr>0) then
    begin
        raise Exception.Create('Could not get typeinfo of hreftype '+inttostr(tdesc.hreftype)+' for VT_USERDEFINED.');
    end;
    hr := tinfo2.GetTypeAttr(tattr);
    if (hr>0) then
    begin
        raise Exception.Create('GetTypeAttr failed, hr = 0\n');
    end;


    case (tattr.typekind) of

    TKIND_ENUM: vt :=vt or VT_I4;
    TKIND_ALIAS:
    begin
        tdesc := tattr.tdescAlias;
        Result := typedescvt_to_variantvt(tinfo2, tattr.tdescAlias, vt);
    end;

    TKIND_INTERFACE:
        if ((tattr.wTypeFlags and TYPEFLAG_FDISPATCHABLE) = TYPEFLAG_FDISPATCHABLE) then
           vt := vt or VT_DISPATCH
        else
           vt := vt or VT_UNKNOWN;

    TKIND_DISPATCH: vt := vt or VT_DISPATCH;

    TKIND_COCLASS:  vt := vt or VT_DISPATCH;

    TKIND_RECORD: vt := vt or VT_RECORD;

    TKIND_UNION:  vt := vt or VT_RECORD; //?
    else
        raise Exception.Create('TKIND '+inttostr(tattr.typekind)+' unhandled.');
    end;
    tinfo2.ReleaseTypeAttr(tattr);

end;


function typedescvt_to_variantvt(	tinfo:ITypeInfo; tdesc:TYPEDESC;var vt:TVARTYPE):Boolean;
var hr:Boolean;
mvt_userdefined:TVARTYPE;
tdesc_userdefined:TYPEDESC;
begin
    hr := True;
    if (not((vt and VT_BYREF) = VT_BYREF) and not((vt and VT_ARRAY) = VT_ARRAY) and (tdesc.vt = VT_PTR)) then
    begin
        tdesc := tdesc.ptdesc^;

        (* munch VT_PTR -> VT_USERDEFINED(interface) into VT_UNKNOWN or
         * VT_DISPATCH and VT_PTR -> VT_PTR -> VT_USERDEFINED(interface) into
         * VT_BYREF|VT_DISPATCH or VT_BYREF|VT_UNKNOWN *)
        if ((tdesc.vt = VT_USERDEFINED) or
            ((tdesc.vt = VT_PTR) and (tdesc.ptdesc.vt = VT_USERDEFINED))) then
        begin
            mvt_userdefined := 0;
            tdesc_userdefined := tdesc;
            if (tdesc.vt = VT_PTR) then
            begin
                mvt_userdefined := VT_BYREF;
                tdesc_userdefined := tdesc.ptdesc^;
            end;
            hr := userdefined_to_variantvt(tinfo, tdesc_userdefined, &mvt_userdefined);
            if (hr and
                (((mvt_userdefined and VT_TYPEMASK) = VT_UNKNOWN) or
                 ((mvt_userdefined and VT_TYPEMASK) = VT_DISPATCH))) then
            begin
                vt := vt or mvt_userdefined;
                Result := True;
                Exit;
            end;
        end;
        vt := VT_BYREF;
    end;

    case tdesc.vt of
      VT_HRESULT: vt := vt or VT_ERROR;
      VT_USERDEFINED: hr := userdefined_to_variantvt(tinfo, tdesc, vt);
      VT_VOID,VT_CARRAY,VT_PTR,VT_LPSTR,VT_LPWSTR: raise Exception.Create('cannot convert type '+inttostr(tdesc.vt)+' into variant VT');
      VT_SAFEARRAY:
         begin
           vt :=vt or VT_ARRAY;
           hr := typedescvt_to_variantvt(tinfo, tdesc.ptdesc^, vt);
         end;
      VT_INT: vt := vt or VT_I4;
      VT_UINT: vt := vt or VT_UI4;
      else  vt := vt or tdesc.vt;
    end;
    Result := hr;
end;

////////////////////////////////////////////////////////////////////////////////

constructor TTypeInformation.Create(ABaseClassId:TGUID;ATypeInfo :ITypeInfo);
var
  LTypeLib : ITypeLib;
  LIndex : integer;
  LTypeKind : ActiveX.TTypeKind;
  i:integer;
begin
  inherited Create;
  FBaseGuid := ABaseClassId;

  // get information for this item
  FTypeInfo := ATypeInfo;
  OleCheck(FTypeInfo.GetTypeAttr(FTypeAttr));
  OleCheck(FTypeInfo.GetContainingTypeLib(LTypeLib, LIndex));
  OleCheck(LTypeLib.GetDocumentation(LIndex, @FName,
    @FDocString, @FHelpContext, @FHelpFile));
  OleCheck(LTypeLib.GetTypeInfoType(LIndex, LTypeKind));
  FTypeKind := TTypeKind(LTypeKind);

  // get implemented interfaces, or interface that this inherits from
  FIntfList := TObjectList.Create;
  GetInterfaces;

  FFuncList := TObjectList.Create;
  GetFunctions;
  FPropList := TObjectList.Create;
  GetProperties(FTypeInfo,GUID_NULL, VT_EMPTY);
  if Assigned(DefaultFunctionInterface) then
    GetProperties(DefaultFunctionInterface.FTypeInfo,GUID_NULL,VT_EMPTY);
  


  FMethods := TStringList.Create;
  FMethods.Duplicates := dupIgnore;

  FProps := TStringList.Create;
  FProps.Duplicates := dupIgnore;

  FEvents := TStringList.Create;
  FEvents.Duplicates := dupIgnore;


  for i := 0 to PropertyCount - 1 do
    FProps.AddObject(GetProperty(i).name,GetProperty(i));

  for i := 0 to FunctionCount - 1 do
    if GetFunction(i).InvokeKind = ikFunction then
      FMethods.Add(GetFunction(i).name);

  if assigned(DefaultFunctionInterface) then
    for i := 0 to DefaultFunctionInterface.FunctionCount - 1 do
      if DefaultFunctionInterface.GetFunction(i).InvokeKind = ikFunction then
        FMethods.Add(DefaultFunctionInterface.GetFunction(i).name);



  if assigned(DefaultEventInterface) then
    begin
      for i := 0 to DefaultEventInterface.FunctionCount - 1 do
        if DefaultEventInterface.GetFunction(i).InvokeKind = ikFunction then
          FEvents.Add(DefaultEventInterface.GetFunction(i).name);
    end;

end;

////////////////////////////////////////////////////////////////////////////////

constructor TTypeInformation.Create(ABaseClassId:TGUID; AClassId: TGUID);
begin
   Create(FileName(ABaseClassId),ABaseClassId,AClassId);
end;

class function TTypeInformation.FileName(AClassID : TGUID) : string;
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


constructor TTypeInformation.Create(AFileName: string;ABaseClassId:TGUID; AClassId: TGUID);
var tt:TTypeInformation;
begin
   tt := TTypeLibViewer.Create(ABaseClassId,AFileName).FindGUID(AClassID);
   Create(ABaseClassId,  tt.TypeInfo);
end;

destructor TTypeInformation.Destroy;
begin
 FFuncList.Free;
  FIntfList.Free;
  FPropList.Free;
  FTypeInfo.ReleaseTypeAttr(FTypeAttr);
  FTypeInfo := nil;
  inherited;
end;

////////////////////////////////////////////////////////////////////////////////

procedure TTypeInformation.GetProperties(TypeInfo: ITypeInfo;PropType: TGUID; VTCode: Integer);
const
  INVOKE_PROPERTYSET = INVOKE_PROPERTYPUT or INVOKE_PROPERTYPUTREF;
var
  I: Integer;
  TypeAttr: PTypeAttr;
  FuncDesc: PFuncDesc;
  VarDesc: PVarDesc;

  procedure SaveName(Id: Integer;td:TTypeDesc;readable:Boolean;writable:Boolean);
  var
    Name: WideString;
    v:TProperty;
    k:integer;
  begin

    OleCheck(TypeInfo.GetDocumentation(Id, @Name, nil, nil, nil));
    for k := 0 to FPropList.Count - 1 do
      begin
        if (FPropList[k] as TProperty).fname = Name then
         begin
           if readable then (FPropList[k] as TProperty).freadable := true;
           if writable then (FPropList[k] as TProperty).fwritable := true;
           Exit;
         end;
      end;

      v := TProperty.Create;
      v.fid := Id;
      v.fname := Name;
      v.fType := td.vt;
      v.fFullType := TypeToString(FBaseGuid,td,TypeInfo);
      v.fwritable := writable;
      v.freadable := readable;
      FPropList.Add(v);
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
  OleCheck(TypeInfo.GetTypeAttr(TypeAttr));
  try
    for I := 0 to TypeAttr.cVars - 1 do
    begin
      OleCheck(TypeInfo.GetVarDesc(I, VarDesc));
      try
        if IsPropType(TypeInfo, @VarDesc.elemdescVar.tdesc) then
        begin
          SaveName(VarDesc.memid,VarDesc.elemdescVar.tdesc,true,VarDesc.wVarFlags and VARFLAG_FREADONLY = 0);
        end;
      finally
        TypeInfo.ReleaseVarDesc(VarDesc);
      end;
    end;
    for I := 0 to TypeAttr.cFuncs - 1 do
    begin
      OleCheck(TypeInfo.GetFuncDesc(I, FuncDesc));
      try
        if ((FuncDesc.invkind = INVOKE_PROPERTYGET) and (FuncDesc.cParams < 1) and
          //HasMember(TypeInfo, TypeAttr.cFuncs, FuncDesc.memid, INVOKE_PROPERTYSET) and
          IsPropType(TypeInfo, @FuncDesc.elemdescFunc.tdesc)) then
          begin
            SaveName(FuncDesc.memid,FuncDesc.elemdescFunc.tdesc,true,false);
          end;

          if ((FuncDesc.invkind and INVOKE_PROPERTYSET <> 0) and (FuncDesc.cParams < 2) and
          IsPropType(TypeInfo,
            @FuncDesc.lprgelemdescParam[FuncDesc.cParams - 1].tdesc)) then
            begin
              SaveName(FuncDesc.memid,FuncDesc.lprgelemdescParam[FuncDesc.cParams - 1].tdesc,false,true);
            end;
      finally
        TypeInfo.ReleaseFuncDesc(FuncDesc);
      end;
    end;
  finally
    TypeInfo.ReleaseTypeAttr(TypeAttr);
  end;
end;

function TTypeInformation.GetProperty(Index: integer): TProperty;
begin
  Result := FPropList[Index] as TProperty;
end;

procedure TTypeInformation.GetInterfaces;
var
  Loop : integer;
  LImplIntf : TImplementedTypeInformation;
  LTypeImplFlags : integer;
begin
  for Loop := 0 to ImplementedInterfacesCount-1 do
  begin
    LImplIntf := TImplementedTypeInformation.Create(FBaseGuid,GetImplementedInterface(Loop));
    OleCheck(FTypeInfo.GetImplTypeFlags(Loop, LTypeImplFlags));
    LImplIntf.FTypeFlags := [];
    if (LTypeImplFlags and IMPLTYPEFLAG_FRESTRICTED) = IMPLTYPEFLAG_FRESTRICTED then
    begin
      LImplIntf.FTypeFlags := LImplIntf.FTypeFlags + [tfRestricted];
    end;
    if (LTypeImplFlags and IMPLTYPEFLAG_FSOURCE) = IMPLTYPEFLAG_FSOURCE then
    begin
      LImplIntf.FTypeFlags := LImplIntf.FTypeFlags + [tfSource];
    end;
    if (LTypeImplFlags and IMPLTYPEFLAG_FDEFAULT) = IMPLTYPEFLAG_FDEFAULT then
    begin
      LImplIntf.FTypeFlags := LImplIntf.FTypeFlags + [tfDefault];
    end;
    if (LTypeImplFlags and IMPLTYPEFLAG_FDEFAULTVTABLE) = IMPLTYPEFLAG_FDEFAULTVTABLE then
    begin
      LImplIntf.FTypeFlags := LImplIntf.FTypeFlags + [tfDefaultVTable];
    end;
    if (tfDefault in LImplIntf.FTypeFlags) then
     begin
       if (tfSource in LImplIntf.FTypeFlags) then
         FDefaultEventInterface := LImplIntf
       else
         FDefaultFunctionInterface := LImplIntf;
     end;
    FIntfList.Add(LImplIntf);
  end;
end;

////////////////////////////////////////////////////////////////////////////////

procedure TTypeInformation.GetFunctions;
var
  LThisFunc : TFunction;
  LFuncDesc : PFuncDesc;
  Loop, LParamLoop : integer;
  LThisParam : TParameter;
  LNameList : TBStrList;
  LParamCount : integer;

begin
  for Loop := 0 to FTypeAttr.cFuncs-1 do
  begin
    LThisFunc := TFunction.Create;
    OleCheck(FTypeInfo.GetFuncDesc(Loop, LFuncDesc));

    try
      LThisFunc.FMemberID := LFuncDesc.memid;


      case LFuncDesc.invkind of
        INVOKE_FUNC : LThisFunc.FInvokeKind := ikFunction;
        INVOKE_PROPERTYGET : LThisFunc.FInvokeKind := ikPropertyGetter;
        INVOKE_PROPERTYPUT : LThisFunc.FInvokeKind := ikPropertysetter;
        INVOKE_PROPERTYPUTREF : LThisFunc.FInvokeKind := ikPropertySetterRef;
      end;


      // get the return type
      LThisFunc.FReturnType.FType := LFuncDesc.elemdescFunc.tdesc.vt;
      LThisFunc.FReturnType.FFullType := TypeToString(FBaseGuid,LFuncDesc.elemdescFunc.tdesc,FTypeInfo);

      OleCheck(FTypeInfo.GetNames(LThisFunc.FMemberID, @LNameList,
        Length(LNameList), LParamCount));

      LThisFunc.FOptionalParamCount := LFuncDesc.cParamsOpt;


      // parameters
      for LParamLoop := 0 to LFuncDesc.cParams-1 do
      begin
        LThisParam := TParameter.Create;
        LThisParam.FName := LNameList[LParamLoop + 1];
        LThisParam.FType := LFuncDesc.lprgelemdescParam[LParamLoop].tdesc.vt;
        LThisParam.FPType := LFuncDesc.lprgelemdescParam[LParamLoop].paramdesc.wParamFlags;
        
        LThisParam.FFullType := TypeToString(FBaseGuid,LFuncDesc.lprgelemdescParam[LParamLoop].tdesc,FTypeInfo);
        LThisFunc.FParameters.Add(LThisParam);
      end;



      OleCheck(FTypeInfo.GetDocumentation(LFuncDesc.memid, @LThisFunc.FName,
        @LThisFunc.FDocString, nil, nil));
    finally
      FTypeInfo.ReleaseFuncDesc(LFuncDesc);
    end;

    FFuncList.Add(LThisFunc);
  end;
end;

////////////////////////////////////////////////////////////////////////////////

function TTypeInformation.FunctionCount : integer;
begin
  Result := FFuncList.Count;
end;

////////////////////////////////////////////////////////////////////////////////

function TTypeInformation.GetFunction(Index : integer) : TFunction;
begin
  Result := FFuncList[Index] as TFunction;
end;

////////////////////////////////////////////////////////////////////////////////

function TTypeInformation.FindFunction(AName: string; var ImplementingType : TTypeInformation): TFunction;
var
  Loop : integer;
begin
  Result := nil;

  // loop through these functions
  for Loop := 0 to FunctionCount-1 do
  begin
    if GetFunction(Loop).Name = AName then
    begin
      Result := GetFunction(Loop);
      ImplementingType := Self;
      Exit;
    end;
  end;

  // now all implemented interfaces
  for Loop := 0 to ImplementedInterfacesCount-1 do
  begin
   if tfDefault in ImplementedInterface(Loop).TypeFlags then
    begin
    if ImplementedInterface(Loop).FindFunction(AName, ImplementingType) <> nil then
    begin
      Result := ImplementedInterface(Loop).FindFunction(AName, ImplementingType);
      Exit;
    end;
    end;
  end;
end;

function TTypeInformation.FindProperty(AName: string): TProperty;
var i:integer;
p:TProperty;
begin
  for i := 0 to PropertyCount - 1 do
    begin
      p := GetProperty(i);
      if p.name = AName then
        begin
          Result := p;
          Exit;
        end;

    end;
  Result := nil;
end;

////////////////////////////////////////////////////////////////////////////////

function TTypeInformation.VariableCount : integer;
begin
  Result := FTypeAttr.cVars;
end;

////////////////////////////////////////////////////////////////////////////////

function TTypeInformation.Variable(Index : integer) : TVariable;
var
  LVarDesc : PVarDesc;
begin
  Result:=TVariable.Create;
  OleCheck(FTypeInfo.GetVarDesc(Index, LVarDesc));
  Result.FType := LVarDesc.elemdescVar.tdesc.vt;
  Result.FFullType := TypeToString(FBaseGuid,LVarDesc.elemdescVar.tdesc,FTypeInfo);

  OleCheck(FTypeInfo.GetDocumentation(LVarDesc.memid, @Result.FName, nil, nil, nil));
  FTypeInfo.ReleaseVarDesc(LVarDesc);
end;


function TTypeInformation.FindVariable(AName:widestring) : TVariable;
var i:integer;
cnt:integer;
v:TVariable;
begin
  Result:=nil;
  cnt:=VariableCount;
  for i := 0 to cnt - 1 do
   begin
     v:=Variable(i);
     if v.FName = AName then
     begin
       Result := v;
       Exit;
     end;
     v.Free;
   end;

end;

////////////////////////////////////////////////////////////////////////////////

function TTypeInformation.GUID : TGUID;
begin
  Result := FTypeAttr.guid;
end;

////////////////////////////////////////////////////////////////////////////////

function TTypeInformation.ImplementedInterfacesCount: integer;
begin
  Result := FTypeAttr.cImplTypes;
end;

function TTypeInformation.PropertyCount: integer;
begin
  Result:=FPropList.Count;
end;

////////////////////////////////////////////////////////////////////////////////

function TTypeInformation.ImplementedInterface(Index : integer) : TImplementedTypeInformation;
begin
  Result := FIntfList[Index] as TImplementedTypeInformation;
end;

////////////////////////////////////////////////////////////////////////////////

function TTypeInformation.GetImplementedInterface(Index: integer): ITypeInfo;
var
  LRefType : HRefType;
begin
  OleCheck(FTypeInfo.GetRefTypeOfImplType(Index, LRefType));
  OleCheck(FTypeInfo.GetRefTypeInfo(LRefType, Result));
end;

////////////////////////////////////////////////////////////////////////////////

function TTypeInformation.TypeKindString: string;
begin
  case TypeKind of
    tkEnum : Result := 'Enum';
    tkRecord : Result := 'Record';
    tkModule : Result := 'Module';
    tkInterface : Result := 'Interface';
    tkDispatch : Result := 'Dispatch';
    tkCoClass : Result := 'CoClass';
    tkAlias : Result := 'Alias';
    tkUnion : Result := 'Union';
  else
    Result := 'Unknown';
  end;
end;

////////////////////////////////////////////////////////////////////////////////
// TTypeLibViewer class

constructor TTypeLibViewer.Create(ABaseClassId:TGUID;AFileName : WideString);
begin
  inherited Create;
  FBaseClassId := ABaseClassId;
  OleCheck(LoadTypeLib(PWideChar(AFileName), FTypeLib));
  FTypeList := TObjectList.Create;
  Populate;
end;

////////////////////////////////////////////////////////////////////////////////

destructor TTypeLibViewer.Destroy;
begin
  FTypeList.Free;
  FTypeLib := nil;
  inherited;
end;

////////////////////////////////////////////////////////////////////////////////

function TTypeLibViewer.Count : integer;
begin
  Result := FTypeLib.GetTypeInfoCount;
end;

////////////////////////////////////////////////////////////////////////////////

function TTypeLibViewer.TypeInfo(Index : integer) : ITypeInfo;
begin
  OleCheck(FTypeLib.GetTypeInfo(Index, Result));
end;

////////////////////////////////////////////////////////////////////////////////

function TTypeLibViewer.TypeInformation(Index : integer) : TTypeInformation;
begin
  Result := FTypeList[Index] as  TTypeInformation;
end;

////////////////////////////////////////////////////////////////////////////////

procedure TTypeLibViewer.Populate;
var
  Loop : integer;
  LTypeInfo : TTypeInformation;
begin
  for Loop := 0 to Count-1 do
  begin
    LTypeInfo := TTypeInformation.Create(FBaseClassId,TypeInfo(Loop));
    FTypeList.Add(LTypeInfo);
  end;
end;

////////////////////////////////////////////////////////////////////////////////

function TTypeLibViewer.FindGUID(AGUID: TGUID): TTypeInformation;
var
  Loop : integer;
begin
  Result := nil;
  for Loop := 0 to Count-1 do
  begin
    if GUIDToString(TypeInformation(Loop).GUID) = GUIDToString(AGUID) then
    begin
      Result := TypeInformation(Loop);
      Exit;
    end;
  end;
end;

////////////////////////////////////////////////////////////////////////////////
{ TFunction }

constructor TFunction.Create;
begin
  FReturnType := TParameter.Create;
  FParameters := TObjectList.Create;
end;

////////////////////////////////////////////////////////////////////////////////

destructor TFunction.Destroy;
begin
  FParameters.Free;
  FReturnType.Free;
  inherited;
end;

////////////////////////////////////////////////////////////////////////////////

function TFunction.InvokeKind: TInvokeKind;
begin
  Result := FInvokeKind;
end;

////////////////////////////////////////////////////////////////////////////////

function TFunction.MemberID: TMemberID;
begin
  Result := FMemberID;
end;

////////////////////////////////////////////////////////////////////////////////

function TFunction.Name: Widestring;
begin
  Result := FName;
end;

////////////////////////////////////////////////////////////////////////////////

function TFunction.Parameter(Index: integer): TParameter;
begin
  Result := FParameters[Index] as TParameter;
end;

////////////////////////////////////////////////////////////////////////////////

function TFunction.ParameterCount: integer;
begin
  Result := FParameters.Count;
end;

////////////////////////////////////////////////////////////////////////////////

function TFunction.ReturnType: TParameter;
begin
  Result := FReturnType;
end;

////////////////////////////////////////////////////////////////////////////////

function TFunction.DocString: Widestring;
begin
  Result := FDocString;
end;



function TypeToString(baseguid:TGUID;ttype:TTypeDesc; tinfo:ITypeInfo):WideString;
var t:integer;
  stype : string;
  pt:TVarType;
  subtinfo:ITypeInfo;
  LTypeLib:ITypeLib;
  LIndex:integer;
  hr:HResult;
  tinfo2:ITypeInfo;
  tattr:PTypeAttr;
  subtattr:PTypeAttr;
  tlattr:PTLibAttr;
begin

  pt:=ttype.vt;


  if pt=VT_SAFEARRAY then
         begin
           Result:='Array:'+TypeToString(baseguid,ttype.ptdesc^,tinfo);
           Exit;
         end;

  stype := '';
  if (pt and vt_vector) <> 0 then stype := stype + 'Vector:';
  if (pt and vt_array) <> 0 then stype := stype + 'Array:';
  if (pt and vt_byref) <> 0 then stype := stype + 'ByRef:';
  stype := trim(stype);


  t := pt and VT_TYPEMASK;



  if t=VT_USERDEFINED then
  begin
    t:=0;
      hr:=tinfo.GetRefTypeInfo(ttype.hreftype,tinfo2);
    //hr = ITypeInfo_GetRefTypeInfo(tinfo, tdesc->u.hreftype, &tinfo2);
    if (hr>0) then
    begin
        raise Exception.Create('Could not get typeinfo of hreftype '+inttostr(ttype.hreftype)+' for VT_USERDEFINED.');
    end;
    hr := tinfo2.GetTypeAttr(tattr);
    if (hr>0) then
    begin
        raise Exception.Create('GetTypeAttr failed, hr = 0\n');
    end;


    case (tattr.typekind) of

    TKIND_ENUM: t :=t or VT_I4;
    TKIND_ALIAS:
    begin
        ttype := tattr.tdescAlias;
        Result := stype+TypeToString(baseguid,ttype,tinfo2);//typedescvt_to_variantvt(tinfo2, tattr.tdescAlias, vt);
        Exit;
    end;

    TKIND_INTERFACE:
        if ((tattr.wTypeFlags and TYPEFLAG_FDISPATCHABLE) = TYPEFLAG_FDISPATCHABLE) then
           t := t or VT_DISPATCH
        else
           t := t or VT_UNKNOWN;

    TKIND_DISPATCH: t := t or VT_DISPATCH;

    TKIND_COCLASS:  t := t or VT_DISPATCH;

    TKIND_RECORD: t := t or VT_RECORD;

    TKIND_UNION:  t := t or VT_RECORD; //?
    else
        raise Exception.Create('TKIND '+inttostr(tattr.typekind)+' unhandled.');
    end;
    tinfo2.ReleaseTypeAttr(tattr);
    //tinfo:=tinfo2;
  end;

  OleCheck(tinfo.GetTypeAttr(tattr));

  case t of
    vt_empty : Result := 'Empty';
    vt_null : Result := 'Null';
    vt_i2 : Result := 'Smallint';
    vt_i4 : Result := 'Integer';
    vt_r4 : Result := 'Single';
    vt_r8 : Result := 'Double';
    vt_cy : Result := 'Currency';
    vt_date : Result := 'Date';
    vt_bstr : Result := 'OleStr';

    vt_error : Result := 'Error';
    vt_bool : Result := 'Boolean';
    vt_variant : Result := 'Variant';

    vt_decimal : Result := 'Decimal';
    vt_i1  : Result := 'ShortInt';
    vt_ui1 : Result := 'Byte';
    vt_ui2 : Result := 'Word';
    vt_ui4  : Result := 'LongWord';
    vt_i8 : Result := 'Int64';
    vt_int : Result := 'Int';
    vt_uint : Result := 'UInt';
    vt_void : Result := 'Void';
    vt_hresult : Result := 'HResult';
    vt_ptr :
    begin
      Result := 'Pointer|'+TypeToString(baseguid,ttype.ptdesc^,tinfo);
    end;
    vt_safearray : Result := 'SafeArray';
    vt_carray : Result := 'CArray';
    vt_userdefined,vt_record,vt_dispatch,vt_unknown:
    begin
      OleCheck(tinfo.GetRefTypeInfo(ttype.hreftype,subtinfo));
      OleCheck(subtinfo.GetContainingTypeLib(LTypeLib, LIndex));
      OleCheck(LTypeLib.GetDocumentation(LIndex, @Result, nil, nil, nil));
      OleCheck(LTypeLib.GetLibAttr(tlattr));
      //tlattr.g
      OleCheck(subtinfo.GetTypeAttr(subtattr));
      //typedata:=GetTypeData(PTypeInfo(subtinfo));
      case t of
       VT_USERDEFINED: Result:='UserDefined:'+Result;
       VT_RECORD: Result:='Record:'+Result;
       VT_DISPATCH: Result:='Dispatch:'+Result;
       VT_UNKNOWN: Result:='Unknown:'+Result;
      end;

      if IsEqualGUID(subtattr.guid,GUID_NULL) then
        Result:=Result + ':' + GUIDToString(baseguid) + ':InLib:' + GUIDToString(tlattr.guid)
      else
        Result:=Result +  ':' + GUIDToString(baseguid) + ':'+GUIDToString(subtattr.guid);
      subtinfo.ReleaseTypeAttr(subtattr);
      LTypeLib.ReleaseTLibAttr(tlattr);
    end;
    vt_lpstr : Result := 'LPStr';
    vt_lpwstr : Result := 'LPWStr';
    vt_int_ptr : Result := 'IntPtr';
    vt_uint_ptr : Result := 'UIntPtr';
    vt_filetime : Result := 'FileTime';
    vt_blob : Result := 'Blob';
    vt_stream : Result := 'Stream';
    vt_storage : Result := 'Storage';
    vt_streamed_object : Result := 'StreamedObject';
    vt_blob_object : Result := 'BlobObject';
    vt_cf : Result := 'CF';
    vt_clsid : Result := 'CLSID';
    else
    Result := 'Unknown (' + IntToStr(pt) + ')';
  end;

  Result := trim(stype + Result);
  tinfo.ReleaseTypeAttr(tattr);
end;


end.
