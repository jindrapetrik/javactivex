unit uMain;

interface

uses
  Windows, Messages, SysUtils, Variants, Classes, Graphics, Controls, Forms,
  Dialogs,ActiveXHost, ExtCtrls, TypeLibViewer,Registry, RegAxCtrlList,ActiveX,ComObj,MyComObj;

const
  MAX_EVENT_COUNT = 1000;
  BLOCK_SIZE = 255;
  dolog = false;

type
  TfrmMain = class(TForm)
    tmrWatchDog: TTimer;
    procedure FormActivate(Sender: TObject);
    procedure FormDestroy(Sender: TObject);
    procedure StartThread();
    procedure tmrWatchDogTimer(Sender: TObject);
  private
    { Private declarations }
  public
    { Public declarations }
  end;

  THostInstance = record
    isGraphical : Boolean;
    host:IComObj;
    hoststandard:TComObj;
    hostgraphic:TActiveXHost;
    guid : widestring;
    panel:TPanel;
    hwnd:HWND;
    docString:WideString;
    active:Boolean;
    progId:widestring;
    classId:widestring;
    width:integer;
    height:integer;
  end;

  TAEvent = class
     public
     var
     cid:Integer;
     EventName : string;
     EventParams : array of Variant;
     EventParamTypes : array of Variant;
     EventParamTypesStr : array of Variant;
     EventParamNames : array of Variant;
     destructor Destroy; override;
  end;

  TEventList = class
    private
       start:integer;
        FLock: TRTLCriticalSection;
       _count:integer;
       _events:array[0..MAX_EVENT_COUNT-1] of TAEvent;
       function GetCount():integer;
    public
      constructor Create();
      destructor Destroy();override;
      procedure Add(e:TAEvent);
      function Pop():TAEvent;
      property Count:Integer read GetCount;

  end;

  TPipeThread = class(TThread)
  protected
    pipe:cardinal;
    newguid:WideString;
    newbaseguid:WideString;
    newhwnd:HWND;
    nwidth:integer;
    nheight:integer;
    cid:integer;
    propName:WideString;
    newfilename :WideString;
    methodName:WideString;
    propVal:OleVariant;
    hosts : Array of THostInstance;
    ttype:TTypeInformation;

    types: TStringList;
    procedure Execute; override;
    procedure GetMethodParams;
    function ReadUI8():byte;
    function ReadUI16():word;
    function ReadSI16():SmallInt;
    function ReadUI32():cardinal;
    function ReadSI32():integer;
    function ReadSI64():Int64;
    function ReadString():WideString;
    procedure WriteUI8(val:byte);
    procedure WriteUI16(val:word);
    procedure WriteUI32(val:cardinal);
    procedure WriteString(val:widestring);
    procedure WriteParameter(val:TParameter);
    procedure WriteStrings(val:TStrings);
    procedure CheckCid();
    procedure WriteOK();
    procedure WriteFail(cause:string); overload;
    procedure WriteFail(cause:Exception); overload;
    procedure WriteValue(v:OleVariant;baseguid:TGUID;guid:TGUID);

    function ReadValue(var ref:Boolean;var baseguid:TGUID;var guid:TGUID):Variant;

    procedure ResizeControl();
    procedure CreateControl();
    procedure DestroyControl();
    procedure DestroyAll();
    procedure SetProperty();
    procedure SetParentWnd();
    procedure GetMethods;
    procedure GetEvents;
    procedure GetProperties;
    procedure GetProperty;
    procedure CallMethod;
    procedure CancelWatchDog;
    procedure InitWatchDog;
    procedure WriteEvents;
    procedure CheckSizes;
    procedure CreateWnd();
    procedure ActiveXEvent(Sender : TObject; EventName : string;
    EventParams : array of Variant;
    EventParamTypes : array of Variant;
    EventParamTypesStr : array of Variant;
    EventParamNames : array of Variant);

    function FindType(baseguid:widestring;guid:widestring):TTypeInformation;

  public

  end;



  TBuf = array[0..BLOCK_SIZE-1] of byte;



var
  frmMain: TfrmMain;
  t: TPipeThread;
  events:TEventList;

  logfile:TextFile;

  procedure log(s:string);

implementation

{$R *.dfm}



function myvartostr(v:Variant):widestring;
begin
  DecimalSeparator:='.';
  case VarType(v) of
    varDouble,varSingle,varCurrency: Result:=FloatToStr(v);
    varDispatch,varUnknown: Result:='-';
    else Result:=v;
  end;
end;

procedure log(s:string);
begin
  if not dolog then
   exit;
  WriteLn(logfile, s);
  Flush(logfile);
end;



procedure TPipeThread.CancelWatchDog;
begin
  frmMain.tmrWatchDog.Enabled := False;
end;

procedure TPipeThread.InitWatchDog;
begin
  frmMain.tmrWatchDog.Enabled := False;
  frmMain.tmrWatchDog.Enabled := True;
end;


procedure ReadPipe(pipe: cardinal; var buffer: TBuf; bytesToRead: cardinal);
var
  numBytesRead: DWORD;
  readResult: longbool;
begin
  numBytesRead := 0;
  readResult := ReadFile(pipe, buffer, bytesToRead, numBytesRead, nil);
  if (not readResult) or (numBytesRead <> bytesToRead) then
  begin
    t.Terminate;
    Application.Terminate;
  end;
end;

procedure WritePipe(pipe: cardinal; var buffer: TBuf; bytesToWrite: cardinal);
var
  written: cardinal;
  writeResult: longbool;
begin
  written := 0;
  writeResult := WriteFile(pipe, buffer, bytesToWrite, written, nil);
  if (not writeResult) or (written <> bytesToWrite) then
  begin
    t.Terminate;
    Application.Terminate;
  end;
end;


function TPipeThread.ReadString():WideString;
var buf:TBuf;
var len:integer;
var us:UTF8String;
var pos:integer;
blklen:integer;
begin
  len := ReadUI32();
  if len = 0 then
  begin
    Result:='';
    exit;
  end;
  pos := 0;
  SetLength(us,len);

  while pos < len do
  begin
    if pos + BLOCK_SIZE <= len then
      blklen := BLOCK_SIZE
    else
      blklen := len - pos;
    ReadPipe(pipe,buf,blklen);
    CopyMemory(@us[1+pos], @buf[0], blklen);
    pos := pos + blklen;
  end;
  Result:=UTF8Decode(us);
end;

function TPipeThread.ReadUI8():byte;
var buf:TBuf;
begin
  ReadPipe(self.pipe,buf,1);
  Result:=buf[0];
end;

function TPipeThread.ReadValue(var ref:Boolean;var baseguid:TGUID;var guid:TGUID): Variant;
var ecid:cardinal;
t:widestring;
//vd:TVarData;
begin
  t := ReadString();
      ref:=false;
      if t='Reference' then
       begin
         ref:=true;
         t := ReadString();
       end;
      if t='Object' then
       begin
         ecid := ReadUI32;
         baseguid := StringToGUID(ReadString());
         guid := StringToGUID(ReadString());
         Result := hosts[ecid].host.GetObj;
       end
       else
       begin
         Result := ReadString();
       end;

end;

function TPipeThread.ReadUI16():word;
var buf:TBuf;
begin
  ReadPipe(self.pipe,buf,2);
  Result:=(buf[0] shl 8) + buf[1];
end;

function TPipeThread.ReadSI16(): SmallInt;
begin
  Result:=ReadUI16();
end;

function TPipeThread.ReadUI32():cardinal;
var buf:TBuf;
begin
  ReadPipe(pipe,buf,4);
  Result:=(buf[0] shl 24)+(buf[1] shl 16)+(buf[2] shl 8) + buf[3];
end;

function TPipeThread.ReadSI32():integer;
begin
  Result:=ReadUI32();
end;

function TPipeThread.ReadSI64():Int64;
begin
  Result := (ReadUI32() shl 32) + ReadUI32();
end;



procedure TPipeThread.WriteUI8(val:byte);
var buf:TBuf;
begin
  buf[0] := val;
  WritePipe(self.pipe,buf,1);
end;

procedure TPipeThread.WriteUI16(val:word);
var buf:TBuf;
begin
  buf[0] := (val shr 8) mod 256;
  buf[1] := val mod 256;
  WritePipe(self.pipe,buf,2);
end;

procedure TPipeThread.WriteUI32(val:cardinal);
var buf:TBuf;
begin
  buf[0] := (val shr 24) mod 256;
  buf[1] := (val shr 16) mod 256;
  buf[2] := (val shr 8) mod 256;
  buf[3] := val mod 256;
  WritePipe(self.pipe,buf,4);
end;


procedure TPipeThread.WriteString(val: widestring);
 var a:TBuf;
 len:integer;
 s: UTF8String;
 blklen : integer;
 pos :integer;
begin
  s := UTF8Encode(val);
  len := Length(s);
  WriteUI32(len);
  pos := 0;
  while pos < len  do
  begin
    if pos + BLOCK_SIZE <= len then
      blklen := BLOCK_SIZE
    else
      blklen := len - pos;
    CopyMemory(@a[0], @s[1+pos], blklen);
    WritePipe(self.pipe,a,blklen);
    pos := pos + blklen;
  end;
end;

procedure TPipeThread.WriteStrings(val: TStrings);
var i:integer;
begin
  writeUI16(val.Count);
  for i := 0 to val.Count - 1 do
  begin
    WriteString(val[i]);
  end;
end;




procedure TfrmMain.StartThread();
var i:integer;
begin
  if dolog then
  begin

    i:=0;
    while FileExists('log'+inttostr(i)+'.txt') do
     i:=i+1;

    AssignFile(logfile, 'log'+inttostr(i)+'.txt');
    ReWrite(logfile);
  end;
  events:=TEventList.Create;
  t := TPipeThread.Create(True);
  t.Resume;
end;


procedure TfrmMain.tmrWatchDogTimer(Sender: TObject);
begin
 if not tmrWatchDog.Enabled then exit;
 if t<>nil then
 begin
   try
    CloseHandle(t.pipe);
   except

   end;
   try
    t.Terminate;
   except

   end;
   Application.Terminate;
 end;
 tmrWatchDog.Enabled := false;
end;

procedure TfrmMain.FormActivate(Sender: TObject);
begin
  ShowWindow(frmMain.Handle, SW_HIDE);
end;

procedure TPipeThread.CreateControl();
var val:integer;
 FRegistry:TRegistry;
 ok:boolean;
 FOleObject: IOleObject;
// co:TComObj;
begin



  val := Length(hosts);
  SetLength(hosts,val+1);

  hosts[val].isGraphical:=false;
   if Succeeded(CoCreateInstance(StringToGuid(self.newguid),nil,CLSCTX_INPROC_SERVER or CLSCTX_LOCAL_SERVER,IOleObject,FOleObject)) then
   begin
      hosts[val].isGraphical:=true;
      FOleObject := nil;
   end;



  if hosts[val].isGraphical then
  begin
  hosts[val].panel := TPanel.Create(frmMain);
  hosts[val].panel.BevelOuter := bvNone;
  hosts[val].panel.Parent:=frmMain;
  hosts[val].panel.Left:=0;
  hosts[val].panel.Top:=0;
  hosts[val].classId := self.newguid;
  ok:=false;
  try

  if newfilename='' then
  begin
    hosts[val].hostgraphic := TActiveXHost.CreateActiveX(hosts[val].panel,	StringToGuid(self.newguid));
    hosts[val].guid := self.newguid;
    hosts[val].host:=hosts[val].hostgraphic;
    ok := true;
  end
  else
  begin
    hosts[val].hostgraphic := TActiveXHost.CreateActiveX(hosts[val].panel,	newfilename, StringToGuid(self.newguid));
    hosts[val].guid := GUIDToString(hosts[val].host.ClassID);
    hosts[val].host:=hosts[val].hostgraphic;
    ok := true;
  end;
  except
    on e:Exception do WriteFail(e);
  end;
  end
  else
  begin
    try
     if newfilename='' then
  begin
    //co:=;
    hosts[val].hoststandard := TComObj.Create(StringToGuid(self.newbaseguid),StringToGuid(self.newguid));
    hosts[val].host:=hosts[val].hoststandard;
    hosts[val].guid := self.newguid;
    ok := true;
  end
  else
  begin
    hosts[val].hoststandard:=TComObj.Create(newfilename, StringToGuid(self.newbaseguid),StringToGuid(self.newguid));;
    hosts[val].host:=hosts[val].hoststandard;
    hosts[val].guid := GUIDToString(hosts[val].host.ClassID);
    ok := true;
  end;
    except
      on e:Exception do
      begin
        WriteFail(e);
        ok:=false;
      end;
    end;

  end;
  if ok then
  begin
    writeOK;


    hosts[val].docString := hosts[val].host.DocString;
    if hosts[val].isGraphical then
    begin
     hosts[val].hostgraphic.Parent := hosts[val].panel;
     hosts[val].hostgraphic.Left := 0;
     hosts[val].hostgraphic.Top := 0;
     hosts[val].hostgraphic.Width := 500;
     hosts[val].hostgraphic.Height := 500;
     hosts[val].hostgraphic.Tag := val;
     hosts[val].width:=500;
     hosts[val].height:=500;
     hosts[val].hostgraphic.OnEvent := ActiveXEvent;
    end;
    hosts[val].active := True;
    WriteUI32(val);

    FRegistry := TRegistry.Create(KEY_READ);
    FRegistry.RootKey := HKEY_CLASSES_ROOT;
    hosts[val].progId := '';
    if FRegistry.OpenKey('\CLSID\' + self.newguid + '\ProgID', False) then
    begin
      hosts[val].progId := Widestring(FRegistry.ReadString(''));
    end;
    WriteString(hosts[val].guid);
    WriteString(hosts[val].progId);
    WriteString(hosts[val].host.ControlClassName);
    WriteString(hosts[val].docString);
  end
  else
  begin
     FreeAndNil(hosts[val].panel);
     SetLength(hosts,val);
  end;

end;

procedure TPipeThread.CreateWnd;
begin
  if hosts[cid].isGraphical then
  begin
    hosts[cid].hostgraphic.CreateWnd;
  end;
end;

procedure TPipeThread.SetParentWnd;
begin
  if hosts[cid].isGraphical then
  begin
    hosts[cid].hwnd := newhwnd;
    Windows.SetParent(hosts[cid].panel.Handle,hosts[cid].hwnd);
  end;
end;


procedure TPipeThread.DestroyControl;
begin
  if assigned(hosts[cid].host) then
   FreeAndNil(hosts[cid].host);
   if assigned(hosts[cid].hostgraphic) then
   FreeAndNil(hosts[cid].hostgraphic);
  if(assigned(hosts[cid].panel)) then
   FreeAndNil(hosts[cid].panel);
  hosts[cid].active := False;
end;

procedure TPipeThread.ResizeControl;
begin
  hosts[cid].width := nwidth;
  hosts[cid].height := nheight;

  if hosts[cid].isGraphical then
  begin
  hosts[cid].panel.Top:=0;
  hosts[cid].panel.Left:=0;
  hosts[cid].panel.Width:=nwidth;
  hosts[cid].panel.Height:=nheight;
  hosts[cid].hostgraphic.Width := nwidth;
  hosts[cid].hostgraphic.Height := nheight;
  hosts[cid].hostgraphic.CreateWnd;
  end;
end;

procedure TPipeThread.DestroyAll;
var i:integer;
begin
  for i := 0 to length(hosts)-1 do
   begin
     if hosts[i].active then
      begin
        FreeAndNil(hosts[i].host);
        FreeAndNil(hosts[i].hostgraphic);
        FreeAndNil(hosts[i].panel);
        hosts[i].active := False;
      end;
   end;
end;



procedure TPipeThread.SetProperty;
begin
  if hosts[cid].host.Properties.IndexOf(propName)=-1 then
  begin
      WriteFail('Property does not exist');
  end
  else
  begin
    try
      hosts[cid].host.PropertyValue[propName] := propVal;
      writeOK;
    except
      on e:Exception do WriteFail(e);
    end;
  end;
end;



procedure TPipeThread.WriteParameter(val:TParameter);
begin
  WriteString(val.Name);
  WriteString(val.FullType);
end;

procedure TPipeThread.GetMethodParams;
var f:TFunction;
i:integer;
it:TTypeInformation;
begin
  f:=ttype.FindFunction(methodName,it);
  if f = nil then
  begin
    WriteFail('Method not found');
  end
  else
  begin
    writeOK;
  end;
  WriteString(f.Name);
  WriteParameter(f.ReturnType);
  WriteUI16(f.ParameterCount);
  WriteUI16(f.OptionalParamCount);
  for i:=0 to f.ParameterCount-1 do
   begin
     writeParameter(f.Parameter(i));
   end;
  WriteString(f.DocString);
end;

procedure TPipeThread.GetMethods;
begin
  WriteOK;
  WriteStrings(ttype.MethodNames);
end;

procedure TPipeThread.GetEvents;
begin
  WriteOK;
  WriteStrings(ttype.EventNames);
end;

procedure TPipeThread.GetProperties;
begin
  WriteOK;
  WriteStrings(ttype.PropertyNames);
end;

procedure TPipeThread.CheckCid;
var r:Boolean;
begin
  if cid<0 then
  begin
    WriteFail('Component id must not be negative');
    r:=False;
  end
  else if cid>=length(hosts) then
   begin
     WriteFail('Component id is not yet defined');
     r:=False;
   end
  else if not hosts[cid].active then
   begin
     WriteFail('Component already destroyed');
     r:=False;
   end
   else
  begin
    writeOK;
    r:=True;
  end;
  if not r then
  begin
    cid:=-1;
  end;
end;


procedure TPipeThread.CheckSizes;
var i:integer;u:Boolean;
begin
  for i := 0 to length(hosts) - 1 do
   begin
     if hosts[i].active then
     begin
       if hosts[i].isGraphical then
       begin
         u:=false;
         if hosts[i].hostgraphic.Width<>hosts[i].width then
         begin
          u:=true;
          hosts[i].hostgraphic.Width:=hosts[i].width;
         end;
         if hosts[i].hostgraphic.Height<>hosts[i].height then
         begin
           u:=true;
           hosts[i].hostgraphic.Height:=hosts[i].height;
         end;
         if u then
           hosts[i].hostgraphic.CreateWnd;
       end;
     end;
   end;
end;

procedure TPipeThread.WriteEvents;
var cnt:integer;
  i: Integer;
  j:integer;
  ev:TAEvent;
  propTypeStr:WideString;
  propVal:Variant;
  begin
  cnt:=events.Count;
  if cnt>65535 then
   cnt := 65535;
  WriteUI16(cnt);
  log('sending events count:'+inttostr(cnt));
  for i := 0 to cnt - 1 do
   begin
     ev:=events.pop();
     log(inttostr(i)+') event:'+ev.EventName);
     WriteUI32(ev.cid);
     WriteString(ev.EventName);
     WriteUI16(Length(ev.EventParams));
     log(' param len:'+inttostr(Length(ev.EventParams)));
     for j := 0 to Length(ev.EventParams) - 1 do
       begin
         log(' write param '+inttostr(j));
         propVal:=ev.EventParams[j];
         propTypeStr := VarTypeAsText(VarType(propVal));
         WriteString(propTypeStr);
         WriteString(myvartostr(propVal));

         if length(ev.EventParamNames)>j then
           WriteString(ev.EventParamNames[j])
         else
           WriteString('Param'+inttostr(j));
         if length(ev.EventParamTypesStr)>j then
           WriteString(ev.EventParamTypesStr[j])
         else
           WriteString(propTypeStr);
       end;
     ev.Free;
   end;
end;



procedure TPipeThread.WriteFail(cause: Exception);
begin
  WriteFail(cause.Message);
end;

procedure TPipeThread.WriteFail(cause: string);
begin
  WriteString('Error');
  WriteString(cause);
end;

procedure TPipeThread.WriteOK;
begin
  WriteString('Boolean');
  WriteString('True');
end;


function ObjectMatch(o1:IUnknown;o2:IUnknown):Boolean;
var lo1,lo2:IUnknown;
begin
  o1.QueryInterface(IUnknown,lo1);
  o2.QueryInterface(IUnknown,lo2);
  Result := lo1 = lo2;
end;

procedure TPipeThread.WriteValue(v:OleVariant;baseguid:TGUID;guid:TGUID);
var vtype:string;
i:integer;
cid:integer;
begin
  vtype:=VarTypeAsText(VarType(v));
  writeString(vtype);
  if vtype='Dispatch' then
  begin
    cid := -1;
    for i := 0 to length(hosts) - 1 do
      begin
        if hosts[i].active then
        begin
          if ObjectMatch(hosts[i].host.GetObj,v) then
           begin
             cid:=i;
             break;
           end;
        end;
      end;
    if cid = -1 then
    begin
      cid:=length(hosts);
      setlength(hosts,cid+1);
      hosts[cid].isGraphical := false;
      hosts[cid].guid := GUIDToString(guid);
      hosts[cid].active := true;
      hosts[cid].hoststandard := TComObj.Create(baseguid,guid,IDispatch(v));
      hosts[cid].host := hosts[cid].hoststandard;
    end;
    WriteUI32(cid);
    Exit;
  end;
  writeString(v);
end;


function ws2wpch(TheWidestring:widestring):pwidechar;
  begin
    Result := AllocMem( Succ(Length(TheWidestring))*Sizeof(Widechar));
    Move(TheWidestring[1],  Result^, Length(TheWidestring)*Sizeof(Widechar));
end;


function GetInvokeArgument(var FParamValue:OleVariant;avt:word): TVariantArg;
var FInvokeArgument:TVariantArg;
LParamValue:OleVariant;
begin
 { Convert the ParamValue to the right type }
  OleCheck(VariantChangeType(LParamValue,FParamValue,0,avt and VT_TYPEMASK));// ));
  FParamValue:=LParamValue;


  log('avt:'+inttostr(avt));

  FInvokeArgument.vt := avt;
  case avt  of
    VT_UI1:
     FInvokeArgument.bVal := FParamValue;
    VT_I2:
     FInvokeArgument.iVal := FParamValue;
    VT_I4:
     FInvokeArgument.lVal := FParamValue;
    VT_R4:
     FInvokeArgument.fltVal := FParamValue;
    VT_R8:
     FInvokeArgument.dblVal := FParamValue;
    VT_BOOL:
     FInvokeArgument.vbool := FParamValue;
    VT_ERROR:
     FInvokeArgument.scode := FParamValue;
    VT_CY:
     FInvokeArgument.cyVal := FParamValue;
    VT_DATE:
     FInvokeArgument.date := FParamValue;
    VT_BSTR:
     FInvokeArgument.bstrVal := ws2wpch(VarToWideStr(FParamValue));
    VT_UNKNOWN:
     FInvokeArgument.unkVal := @FParamValue;
    VT_DISPATCH:
     FInvokeArgument.dispVal := @FParamValue;
    VT_ARRAY:
     FInvokeArgument.parray := @FParamValue;
    { Cannot do Character Type with variants?
    VT_I1:
     FInvokeArgument.cVal := FParamValue;  }
    VT_UI2:
     FInvokeArgument.uiVal := FParamValue;
    VT_UI4:
     FInvokeArgument.ulVal := FParamValue;
    VT_INT:
     FInvokeArgument.intVal := FParamValue;
    VT_UINT:
     FInvokeArgument.uintVal := FParamValue;
    VT_BYREF or VT_UI1:
     FInvokeArgument.pbVal := @FParamValue;
    VT_BYREF or VT_I2:
     FInvokeArgument.piVal := @FParamValue;
    VT_BYREF or VT_I4:
     FInvokeArgument.plVal := @FParamValue;
    VT_BYREF or VT_R4:
     FInvokeArgument.pfltVal := @FParamValue;
    VT_BYREF or VT_R8:
     FInvokeArgument.pdblVal := @FParamValue;
    VT_BYREF or VT_BOOL:
     FInvokeArgument.pbool := @FParamValue;
    VT_BYREF or VT_ERROR:
     FInvokeArgument.pscode := @FParamValue;
    VT_BYREF or VT_CY:
     FInvokeArgument.pcyVal := @FParamValue;
    VT_BYREF or VT_DATE:
     FInvokeArgument.pdate := @FParamValue;
    VT_BYREF or VT_BSTR:
     FInvokeArgument.pbstrVal := @FParamValue;
    VT_BYREF or VT_UNKNOWN:
     FInvokeArgument.punkVal := @FParamValue;
    VT_BYREF or VT_DISPATCH:
     FInvokeArgument.pdispVal := @FParamValue;
    VT_BYREF or VT_ARRAY:
     FInvokeArgument.pparray := @FParamValue;
    VT_BYREF or VT_VARIANT:
     FInvokeArgument.pvarVal := @FParamValue;
    VT_BYREF or VT_DECIMAL:
     FInvokeArgument.pdecVal := @FParamValue;
    VT_BYREF or VT_I1:
     FInvokeArgument.pcVal := @FParamValue;
    VT_BYREF or VT_UI2:
     FInvokeArgument.puiVal := @FParamValue;
    VT_BYREF or VT_UI4:
     FInvokeArgument.pulVal := @FParamValue;
    VT_BYREF or VT_INT:
     FInvokeArgument.pintVal := @FParamValue;
    VT_BYREF or VT_UINT:
     FInvokeArgument.puintVal := @FParamValue;
    VT_BYREF:
     FInvokeArgument.byRef := @FParamValue;
  end; { case }
  result := FInvokeArgument;
end;

procedure TPipeThread.GetProperty;
var
propTypeStr:string;
g,g2:TGUID;
begin
  propName := ReadString();
                  propTypeStr := ReadString();
                  if propTypeStr='Dispatch' then
                  begin
                    g := StringToGUID(ReadString());
                    g2 := StringToGUID(ReadString());
                  end;
                  if hosts[cid].host.Properties.IndexOf(propName)=-1 then
                  begin
                    WriteFail('Property does not exist');
                  end
                  else
                  begin
                    try
                     propVal := hosts[cid].host.PropertyValue[propName];
                     writeOK;
                     WriteValue(propVal,g,g2);
                    except
                     on e:Exception do WriteFail(e);
                    end;
                   end;
end;

procedure TPipeThread.CallMethod;
var a:array of TVariantArg;
i:integer;
cnt:integer;
name:WideString;
ret:OleVariant;
t:string;
refs:array of boolean;
baseguids:array of TGUID;
guids:array of TGUID;
//retref : boolean;
retbaseguid,retguid:TGUID;
aref:array of OleVariant;
v:Variant;
f:TFunction;
p:TParameter;
begin
  name:=ReadString();
  f:=hosts[cid].host.FindFunction(name);
  cnt:=ReadUI16();
  SetLength(a,cnt);
  SetLength(refs,cnt);
  SetLength(baseguids,cnt);
  SetLength(guids,cnt);
  try
  SetLength(aref,cnt);
  for i := 0 to cnt - 1 do
    begin
      p:=f.Parameter(i);

      v := ReadValue(refs[i],baseguids[i],guids[i]);
      aref[i]:=v;
      if refs[i] then
        a[i] := GetInvokeArgument(aref[i],VT_BYREF or p.Ptype)
      else
       a[i] := GetInvokeArgument(aref[i],p.ParamType);
    end;

 t:=ReadString;
 //retref:=false;
 if t = 'Reference' then
  begin
    //retref:=true;
    t:=ReadString;
  end;
 if t='Object' then
  begin
    retbaseguid := StringToGUID(readString());
    retguid := StringToGUID(readString());
  end;


    if cnt=0 then
     ret:=hosts[cid].host.InvokeMethod(name)
    else
     ret:=hosts[cid].host.InvokeMethod(name,a);

    writeOK;
    for i := 0 to cnt - 1 do
      begin
        if refs[i] then
         begin
            WriteValue(
            Variant(a[i])
            ,baseguids[i],guids[i]);
         end;
      end;
   WriteValue(ret,retbaseguid,retguid);
  except
    on e:Exception do WriteFail(e);
  end;

end;


procedure TPipeThread.Execute();
var
  pipename: PAnsiChar;
  cmd: integer;
  i: Integer;
  LTypeLibVwr : TTypeLibViewer;
  ok : boolean;
  regList:TRegAxCtrlList;
  prop : TProperty;
  sguid:string;
  baseguid:string;
  ref:boolean;
  g,g2:TGUID;
const
  CMD_ECHO = 0;
  CMD_NEW  = 1;
  CMD_OBJ_DESTROY = 2;
  CMD_DESTROYALL = 3;
  CMD_TYPE_LIST_PROPERTIES = 4;
  CMD_TYPE_LIST_METHODS = 5;
  CMD_TYPE_LIST_EVENTS = 6;
  CMD_OBJ_RESIZE = 7;
  CMD_OBJ_GET_PROPERTY = 8;
  CMD_OBJ_SET_PROPERTY = 9;
  CMD_OBJ_SET_PARENT = 10;
  CMD_OBJ_CALL_METHOD = 11;
  CMD_TYPE_GET_METHOD_PARAMS = 12;
  CMD_GET_OCX_CLASSES = 13;
  CMD_GET_REGISTERED_CLASSES = 14;
  CMD_TYPE_GET_PROPERTY_TYPE = 15;
  CMD_TYPE_GET_INFO = 16;
begin
  types := TStringList.Create;
  try
    pipename := PAnsiChar('\\.\\pipe\activex_server_' + ParamStr(1));
    begin
      pipe := CreateFile(pipename, GENERIC_READ or GENERIC_WRITE,
        FILE_SHARE_READ or FILE_SHARE_WRITE, nil, OPEN_EXISTING, FILE_ATTRIBUTE_NORMAL, 0);

      repeat
          Synchronize(InitWatchDog);
          cmd := ReadUI8();
          if self.Terminated then
          begin
            Exit;
          end;
          Synchronize(CancelWatchDog);
          case cmd of
            CMD_TYPE_GET_INFO:
            begin
              baseguid := ReadString();
              sguid := ReadString();
              ttype := FindType(baseguid,sguid);
              if ttype<>nil then
              begin
                WriteOK;
                WriteString(ttype.Name);
                WriteString(ttype.DocString);
              end;
            end;
            CMD_TYPE_GET_PROPERTY_TYPE:
            begin
              baseguid := ReadString();
              sguid := ReadString();
              propName := ReadString();
              ttype := FindType(baseguid,sguid);
              if ttype<>nil then
              begin
                prop := ttype.FindProperty(propName);
                if prop = nil then
                  begin
                    WriteFail('Property does not exist');
                  end
                  else
                  begin
                    writeOK;
                    WriteString(prop.PropFullType);
                    WriteString('Boolean');
                    if prop.readable then WriteString('True') else WriteString('False');
                    WriteString('Boolean');
                    if prop.writable then WriteString('True') else WriteString('False');
                  end;
              end;
            end;
            CMD_GET_REGISTERED_CLASSES:
            begin
              regList := TRegAxCtrlList.Create;
              WriteUI16(regList.ClassIDs.Count);
              for i := 0 to regList.ClassIDs.Count -1 do
               begin
                 WriteString(regList.ProgIDs[i]);
                 WriteString(regList.Descriptions[i]);
                 WriteString(regList.ClassIDs[i]);
                 WriteString(regList.FileNames[i]);
               end;
              FreeAndNil(regList);
            end;
            CMD_GET_OCX_CLASSES:
            begin
              LTypeLibVwr := nil;
              ok:=false;
              try
                LTypeLibVwr := TTypeLibViewer.Create(GUID_NULL,ReadString());
                ok := true;
              except
                on e:Exception do WriteFail(e);
              end;
              if ok then
              begin
              writeOK;
              WriteUI16(LTypeLibVwr.Count);
              for i := 0 to LTypeLibVwr.Count-1 do
              begin
                WriteString(LTypeLibVwr.TypeInformation(i).Name);
                WriteString(LTypeLibVwr.TypeInformation(i).DocString);
                WriteString(WideString(GUIDToString(LTypeLibVwr.TypeInformation(i).GUID)));
              end;
              LTypeLibVwr.Free;
              end;
            end;
            CMD_TYPE_GET_METHOD_PARAMS:
            begin
              baseguid := ReadString();
              sguid := ReadString();
              methodName := ReadString();
              ttype := FindType(baseguid,sguid);
              if ttype<>nil then
              begin
                Synchronize(GetMethodParams);
              end;
            end;
            CMD_ECHO:
            begin
              Synchronize(WriteEvents);
              Synchronize(CheckSizes);
            end;
            CMD_NEW:
            begin
              newfilename := ReadString();
              newbaseguid := ReadString();
              newguid := ReadString();
              Synchronize(CreateControl);
            end;
            CMD_OBJ_DESTROY:
            begin
              cid := ReadUI32();
              Synchronize(CheckCid);
              if cid<>-1 then
              begin
                Synchronize(DestroyControl);
              end;
            end;

            CMD_DESTROYALL:
            begin
              Synchronize(DestroyAll);
              break;
            end;

            CMD_TYPE_LIST_PROPERTIES:
            begin
              baseguid := ReadString();
              sguid := ReadString();
              ttype := FindType(baseguid,sguid);
              if ttype<>nil then
              begin
              Synchronize(GetProperties);
              end;

            end;

            CMD_TYPE_LIST_METHODS:
            begin
              baseguid := ReadString();
              sguid := ReadString();
              ttype := FindType(baseguid,sguid);
              if ttype<>nil then
              begin
              Synchronize(GetMethods);
              end;
            end;

            CMD_TYPE_LIST_EVENTS:
            begin
              baseguid := ReadString();
              sguid := ReadString();
              ttype := FindType(baseguid,sguid);
              if ttype<>nil then
              begin
                Synchronize(GetEvents);
              end;
            end;

            CMD_OBJ_RESIZE:
            begin
              cid := ReadUI32();
              Synchronize(CheckCid);
              if cid<>-1 then
              begin
                nwidth := ReadUI16();
                nheight := ReadUI16();
                Synchronize(ResizeControl);
              end;
            end;
            CMD_OBJ_GET_PROPERTY:
            begin
              cid := ReadUI32();
              Synchronize(CheckCid);
              if cid<>-1 then
              begin
                  Synchronize(GetProperty);
              end;
            end;
            CMD_OBJ_SET_PROPERTY:
            begin
              cid := ReadUI32();
              Synchronize(CheckCid);
              if cid<>-1 then
              begin
                propName := ReadString();
                propVal := ReadValue(ref,g,g2);
                Synchronize(SetProperty);
              end;
            end;
            CMD_OBJ_SET_PARENT:
            begin
              cid := ReadUI32();
              Synchronize(CheckCid);
              if cid<>-1 then
              begin
                newhwnd := ReadUI32();
                Synchronize(SetParentWnd);
              end;
            end;
            CMD_OBJ_CALL_METHOD:
            begin
              cid := ReadUI32();
              Synchronize(CheckCid);
              if cid<>-1 then
              begin
                Synchronize(CallMethod);
              end;
            end;
          end;
      until False;

      CloseHandle(pipe);
    end;
  except
    on E: Exception do
    begin
       
    end;
  end;
end;



function TPipeThread.FindType(baseguid:widestring;guid: widestring): TTypeInformation;
var i:integer;
g:TGUID;
g2:TGUID;
begin
  try
  guid := GUIDToString(StringToGuid(guid)); //make uniform - case etc.
  baseguid := GUIDToString(StringToGuid(baseguid));
  for i := 0 to types.Count - 1 do
    begin
      if types[i] = baseguid+':'+guid then
       begin
         Result := types.Objects[i] as TTypeInformation;
         Exit;
       end;
    end;


    //showMessage(baseguid+':'+guid);
  g := StringToGUID(baseguid);
  g2:=StringToGUID(guid);
// showmessage('A3');

  Result := TTypeInformation.Create(g,g2);
  //showmessage('B');

  types.AddObject(baseguid+':'+guid,Result);
//  showmessage('C');
  
  except
    on e:Exception do
     begin
       WriteFail(e); //'Cannot find type');
       Result := nil;
     end;

  end;
end;

procedure TfrmMain.FormDestroy(Sender: TObject);
begin
  t.Free;
end;

procedure TPipeThread.ActiveXEvent(Sender : TObject; EventName : string;
    EventParams : array of Variant;
    EventParamTypes : array of Variant;
    EventParamTypesStr : array of Variant;
    EventParamNames : array of Variant);
var ev:TAEvent;
i:integer;
begin
  if Assigned(t) then
   begin
     ev:=TAEvent.Create;
     ev.cid := (Sender as TComponent).Tag;
     ev.EventName := EventName;
     setlength(ev.EventParams,length(eventparams));
     setlength(ev.EventParamTypes,length(EventParamTypes));
     setlength(ev.EventParamTypesStr,length(EventParamTypesStr));
     setlength(ev.EventParamNames,length(EventParamNames));
     for i := 0 to length(eventparams) - 1 do
         ev.EventParams[i] := EventParams[i];
     for i := 0 to length(EventParamTypes) - 1 do
         ev.EventParamTypes[i] := EventParamTypes[i];
     for i := 0 to length(EventParamTypesStr) - 1 do
         ev.EventParamTypesStr[i] := EventParamTypesStr[i];
     for i := 0 to length(EventParamNames) - 1 do
         ev.EventParamNames[i] := EventParamNames[i];
       events.Add(ev);
   end;
end;


destructor TAEvent.Destroy;
begin

end;

constructor TEventList.Create();
begin
  inherited Create;
  start:=0;
  _count:=0;
  InitializeCriticalSection(FLock);
end;

function TEventList.Pop():TAEvent;
begin
  EnterCriticalSection(FLock);
  try
  if _count=0 then
  begin
    Result:=nil;
    Exit;
  end;

  Result := _events[start];
  _events[start]:=nil;
  start:=(start+1)mod MAX_EVENT_COUNT;
  _count:=_count-1;
  finally
    LeaveCriticalSection(FLock);
  end;
end;
procedure TEventList.Add(e:TAEvent);
begin
  EnterCriticalSection(FLock);
  _events[(start+_count)mod MAX_EVENT_COUNT]:=e;
  if _count<MAX_EVENT_COUNT then
    _count:=_count+1;

  LeaveCriticalSection(FLock);
end;

destructor TEventList.Destroy;
begin
  DeleteCriticalSection(FLock);
end;

function TEventList.GetCount():integer;
begin
   EnterCriticalSection(FLock);
   Result:=_count;
   LeaveCriticalSection(FLock);
end;

end.
