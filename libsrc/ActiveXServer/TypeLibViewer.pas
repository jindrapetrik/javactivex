(*******************************************************************************
 *   Based on Chris Bells code on http://www.doogal.co.uk/activex.php          *
 *******************************************************************************)
 
unit TypeLibViewer;

interface

uses
  ActiveX, Contnrs, Dialogs;

type
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

  TParameter = class
    private
      FType : TVarType;
      FFullType :WideString;
      FName : widestring;
    public
      property ParamType : TVarType read FType;
      property FullType : widestring read FFullType;
      property Name : widestring read FName;
  end;

  TFunction = class
    private
      FName : Widestring;
      FMemberID : TMemberID;
      FInvokeKind : TInvokeKind;
      FReturnType : TParameter;
      FParameters : TObjectList;
      FDocString : Widestring;
    public
      constructor Create;
      destructor Destroy; override;
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
      function GetImplementedInterface(Index: integer): ITypeInfo;
      procedure GetInterfaces;
      procedure GetFunctions;
    public
      constructor Create(ATypeInfo : ITypeInfo);
      destructor Destroy; override;
      // properties
      property Name : WideString read FName;
      property DocString : WideString read FDocString;
      property HelpContext : Longint read FHelpContext;
      property HelpFile : Widestring read FHelpFile;
      property TypeKind : TTypeKind read FTypeKind;
      property TypeInfo : ITypeInfo read FTypeInfo;
      // functions
      function FunctionCount : integer;
      function GetFunction(Index : integer) : TFunction;
      function FindFunction(AName : string; var ImplementingType : TTypeInformation) : TFunction;
      function VariableCount : integer;
      function Variable(Index : integer) : TVariable;
      function ImplementedInterfacesCount : integer;
      function ImplementedInterface(Index : integer) : TImplementedTypeInformation;
      function GUID : TGUID;
      function TypeKindString : string;
      function FindVariable(AName:widestring) : TVariable;
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
      procedure Populate;
      function TypeInfo(Index : integer) : ITypeInfo;
    public
      constructor Create(AFileName : WideString);
      destructor Destroy; override;
      function Count : integer;
      function TypeInformation(Index : integer) : TTypeInformation;
      function FindGUID(AGUID : TGUID) : TTypeInformation;
  end;

  function TypeToString(ttype:tagTYPEDESC):WideString;

////////////////////////////////////////////////////////////////////////////////

implementation

uses
  ComObj, SysUtils;

////////////////////////////////////////////////////////////////////////////////

constructor TTypeInformation.Create(ATypeInfo :ITypeInfo);
var
  LTypeLib : ITypeLib;
  LIndex : integer;
  LTypeKind : ActiveX.TTypeKind;
begin
  inherited Create;

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
end;

////////////////////////////////////////////////////////////////////////////////

destructor TTypeInformation.Destroy;
begin
  FFuncList.Free;
  FIntfList.Free;
  FTypeInfo.ReleaseTypeAttr(FTypeAttr);
  FTypeInfo := nil;
  inherited;
end;

////////////////////////////////////////////////////////////////////////////////

procedure TTypeInformation.GetInterfaces;
var
  Loop : integer;
  LImplIntf : TImplementedTypeInformation;
  LTypeImplFlags : integer;
begin
  for Loop := 0 to ImplementedInterfacesCount-1 do
  begin
    LImplIntf := TImplementedTypeInformation.Create(GetImplementedInterface(Loop));
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
      LThisFunc.FReturnType.FFullType := TypeToString(LFuncDesc.elemdescFunc.tdesc);

      OleCheck(FTypeInfo.GetNames(LThisFunc.FMemberID, @LNameList,
        Length(LNameList), LParamCount));

      // parameters
      for LParamLoop := 0 to LFuncDesc.cParams-1 do
      begin
        LThisParam := TParameter.Create;
        LThisParam.FName := LNameList[LParamLoop + 1];
        LThisParam.FType := LFuncDesc.lprgelemdescParam[LParamLoop].tdesc.vt;
        LThisParam.FFullType := TypeToString(LFuncDesc.lprgelemdescParam[LParamLoop].tdesc);
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
  Result.FFullType := TypeToString(LVarDesc.elemdescVar.tdesc);

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

constructor TTypeLibViewer.Create(AFileName : WideString);
begin
  inherited Create;
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
    LTypeInfo := TTypeInformation.Create(TypeInfo(Loop));
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



function TypeToString(ttype:TTypeDesc):WideString;
var t:integer;
  stype : string;
  pt:TVarType;
begin

  stype := '';
  pt:=ttype.vt;
  if (pt and vt_vector) <> 0 then stype := stype + 'Vector:';
  if (pt and vt_array) <> 0 then stype := stype + 'Array:';
  if (pt and vt_byref) <> 0 then stype := stype + 'ByRef:';
  stype := trim(stype);


  t := pt and VT_TYPEMASK;
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
    vt_dispatch : Result := 'Dispatch';
    vt_error : Result := 'Error';
    vt_bool : Result := 'Boolean';
    vt_variant : Result := 'Variant';
    vt_unknown : Result := 'Unknown';
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
      Result := 'Pointer|'+TypeToString(ttype.ptdesc^);
    end;
    vt_safearray : Result := 'SafeArray';
    vt_carray : Result := 'CArray';
    vt_userdefined : Result := 'UserDefined';
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
end;


end.
