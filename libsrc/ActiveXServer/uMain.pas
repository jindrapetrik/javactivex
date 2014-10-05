unit uMain;

interface

uses
  Windows, Messages, SysUtils, Variants, Classes, Graphics, Controls, Forms,
  Dialogs,ActiveXHost, ExtCtrls, TypeLibViewer,Registry, RegAxCtrlList;

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
    host:TActiveXHost;
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
  end;

  TPipeThread = class(TThread)
  protected
    pipe:cardinal;
    newguid:WideString;
    newhwnd:HWND;
    nwidth:integer;
    nheight:integer;
    cid:integer;
    propName:WideString;
    newfilename :WideString;
    methodName:WideString;
    propValStr:WideString;
    hosts : Array of THostInstance;

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
    procedure WriteString(val:string);
    procedure WriteParameter(val:TParameter);
    procedure WriteStrings(val:TStrings);
    procedure CheckCid();

    procedure ResizeControl();
    procedure CreateControl();
    procedure DestroyControl();
    procedure DestroyAll();
    procedure SetProperty();
    procedure SetParentWnd();
    procedure GetMethods;
    procedure GetEvents;
    procedure GetProperties;
    procedure CallMethod;
    procedure CancelWatchDog;
    procedure InitWatchDog;
    procedure WriteEvents;
    procedure CheckSizes;
    procedure ActiveXEvent(Sender : TObject; EventName : string;
    EventParams : array of Variant;
    EventParamTypes : array of Variant;
    EventParamTypesStr : array of Variant;
    EventParamNames : array of Variant);

  public
   var events:TStrings;

  end;
  TBuf = array[0..255] of byte;



var
  frmMain: TfrmMain;
  t: TPipeThread;


implementation

{$R *.dfm}



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
begin
  ReadPipe(self.pipe,buf,1);
  len:=buf[0];
  if len = 0 then
  begin
    Result:='';
    exit;
  end;
  ReadPipe(pipe,buf,len);
  SetLength(us,len);
  CopyMemory(@us[1], @buf[0], len);
  Result:=UTF8Decode(us);
end;

function TPipeThread.ReadUI8():byte;
var buf:TBuf;
begin
  ReadPipe(self.pipe,buf,1);
  Result:=buf[0];
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


procedure TPipeThread.WriteString(val: string);
 var a:TBuf;
 len:integer;
 s: UTF8String;
begin
  s := UTF8Encode(val);
  len := Length(s);
  CopyMemory(@a[0], @s[1], len);
  WriteUI8(len);
  WritePipe(self.pipe,a,len);
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
begin
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
begin
  val := Length(hosts);
  SetLength(hosts,val+1);
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
    hosts[val].host := TActiveXHost.CreateActiveX(hosts[val].panel,	StringToGuid(self.newguid));
    hosts[val].guid := self.newguid;
    ok := true;
  end
  else
  begin
    hosts[val].host := TActiveXHost.CreateActiveX(hosts[val].panel,	newfilename, StringToGuid(self.newguid));
    hosts[val].guid := GUIDToString(hosts[val].host.ClassID);
    ok := true;
  end;
  except
    on e:Exception do
     begin
       WriteString('Error');
       WriteString(e.Message);
     end;
  end;
  if ok then
  begin
    WriteString('Boolean');
    WriteString('True');
    hosts[val].docString := hosts[val].host.DocString;

    hosts[val].host.Parent := hosts[val].panel;
    hosts[val].host.Left := 0;
    hosts[val].host.Top := 0;
    hosts[val].host.Width := 500;
    hosts[val].host.Height := 500;
    hosts[val].host.Tag := val;
    hosts[val].width:=500;
    hosts[val].height:=500;

    hosts[val].host.OnEvent := ActiveXEvent;
    hosts[val].active := True;
    WriteUI32(val);

    FRegistry := TRegistry.Create(KEY_READ);
    FRegistry.RootKey := HKEY_CLASSES_ROOT;
    hosts[val].progId := '';
    if FRegistry.OpenKey('\CLSID\' + self.newguid + '\ProgID', False) then
    begin
      hosts[val].progId := FRegistry.ReadString('');
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

procedure TPipeThread.SetParentWnd;
begin
  hosts[cid].hwnd := newhwnd;
  Windows.SetParent(hosts[cid].panel.Handle,hosts[cid].hwnd);
end;


procedure TPipeThread.DestroyControl;
begin
  FreeAndNil(hosts[cid].host);
  FreeAndNil(hosts[cid].panel);
  hosts[cid].active := False;
end;

procedure TPipeThread.ResizeControl;
begin
  hosts[cid].width := nwidth;
  hosts[cid].height := nheight;
  hosts[cid].panel.Top:=0;
  hosts[cid].panel.Left:=0;
  hosts[cid].panel.Width:=nwidth;
  hosts[cid].panel.Height:=nheight;
  hosts[cid].host.Width := nwidth;
  hosts[cid].host.Height := nheight;
end;

procedure TPipeThread.DestroyAll;
var i:integer;
begin
  for i := 0 to length(hosts)-1 do
   begin
     if hosts[i].active then
      begin
        FreeAndNil(hosts[i].host);
        FreeAndNil(hosts[i].panel);
        hosts[i].active := False;
      end;
   end;
end;



procedure TPipeThread.SetProperty;
begin
  if hosts[cid].host.Properties.IndexOf(propName)=-1 then
  begin
      WriteString('Error');
      WriteString('Property does not exist');
  end
  else
  begin
    try
      hosts[cid].host.PropertyValue[propName] := propValStr;
      WriteString('Boolean');
      WriteString('True');
    except
      on e:Exception do
        begin
          WriteString('Error');
          WriteString(e.Message);
        end;
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
begin
  f:=hosts[cid].host.FindFunction(methodName);
  if f = nil then
  begin
    WriteString('Error');
    WriteString('Method not found');
  end
  else
  begin
    WriteString('Boolean');
    WriteString('True');
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
  WriteStrings(hosts[cid].host.Methods);
end;

procedure TPipeThread.GetEvents;
begin
  WriteStrings(hosts[cid].host.Events);
end;

procedure TPipeThread.GetProperties;
begin
  WriteStrings(hosts[cid].host.Properties);
end;

procedure TPipeThread.CheckCid;
var r:Boolean;
begin
  if cid<0 then
  begin
    WriteString('Error');
    WriteString('Component id must not be negative');
    r:=False;
  end
  else if cid>=length(hosts) then
   begin
     WriteString('Error');
     WriteString('Component id is not yet defined');
     r:=False;
   end
  else if not hosts[cid].active then
   begin
     WriteString('Error');
     WriteString('Component already destroyed');
     r:=False;
   end
   else
  begin
    WriteString('Boolean');
    WriteString('True');
    r:=True;
  end;
  if not r then
  begin
    cid:=-1;
  end;
end;


procedure TPipeThread.CheckSizes;
var i:integer;
begin
  for i := 0 to length(hosts) - 1 do
   begin
     if hosts[i].active then
     begin
       if hosts[i].host.Width<>hosts[i].width then hosts[i].host.Width:=hosts[i].width;
       if hosts[i].host.Height<>hosts[i].height then hosts[i].host.Height:=hosts[i].height;
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
  for i := 0 to cnt - 1 do
   begin
     ev:=(events.Objects[0] as TAEvent);
     WriteUI32(ev.cid);
     WriteString(ev.EventName);
     WriteUI16(Length(ev.EventParams));
     for j := 0 to Length(ev.EventParams) - 1 do
       begin
         propVal:=ev.EventParams[j];
         propTypeStr := VarTypeAsText(VarType(propVal));
         if VarType(propVal) = varDispatch then
           propValStr := '-'
         else if VarType(propVal) = varUnknown then
           propValStr := '-'
         else
        	  propValStr := VarToWideStr(propVal);
         WriteString(propTypeStr);
         WriteString(propValStr);
         WriteString(ev.EventParamNames[j]);
         WriteString(ev.EventParamTypesStr[j]);
       end;
     events.Delete(0);
   end;

end;



procedure TPipeThread.CallMethod;
var a:array of OleVariant;
i:integer;
cnt:integer;
name:WideString;
ret:OleVariant;
begin
  name:=ReadString();
  cnt:=ReadUI16();
  SetLength(a,cnt);
  for i := 0 to cnt - 1 do
    begin
      a[i] := ReadString();
    end;

  try
    ret:=hosts[cid].host.InvokeMethod(name,a);
    writeString(VarTypeAsText(VarType(ret)));
    writeString(ret);
  except
    on e:Exception do
     begin
      writeString('Error');
      writeString(e.Message);
     end;
  end;

end;

procedure TPipeThread.Execute();
var
  pipename: PAnsiChar;
  cmd: integer;
  propVal : OleVariant;
  propTypeStr:string;
  i: Integer;
  LTypeLibVwr : TTypeLibViewer;
  ok : boolean;
  regList:TRegAxCtrlList;
  prop : TProperty;
const
  CMD_ECHO = 0;
  CMD_NEW  = 1;
  CMD_DESTROY = 2;
  CMD_DESTROYALL = 3;
  CMD_LIST_PROPERTIES = 4;
  CMD_LIST_METHODS = 5;
  CMD_LIST_EVENTS = 6;
  CMD_RESIZE = 7;
  CMD_GET_PROPERTY = 8;
  CMD_SET_PROPERTY = 9;
  CMD_SET_PARENT = 10;
  CMD_CALL_METHOD = 11;
  CMD_GET_METHOD_PARAMS = 12;
  CMD_GET_OCX_CLASSES = 13;
  CMD_GET_REGISTERED_CLASSES = 14;
  CMD_GET_PROPERTY_TYPE = 15;

begin
  events := TStringList.Create;
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
            CMD_GET_PROPERTY_TYPE:
            begin
              cid := ReadUI32();
              Synchronize(CheckCid);
              if cid<>-1 then
              begin
                propName := ReadString();
                i:=hosts[cid].host.Properties.IndexOf(propName);
                if i=-1 then
                  begin
                    WriteString('Error');
                    WriteString('Property does not exist');
                  end
                  else
                  begin
                    WriteString('Boolean');
                    WriteString('True');
                    prop:=hosts[cid].host.Properties.Objects[i] as TProperty;
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
                LTypeLibVwr := TTypeLibViewer.Create(ReadString());
                ok := true;
              except
                on e:Exception do
                 begin
                   WriteString('Error');
                   WriteString(e.Message);
                 end;
              end;
              if ok then
              begin
              WriteString('Boolean');
              WriteString('True');
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
            CMD_GET_METHOD_PARAMS:
            begin
              cid := ReadUI32();
              Synchronize(CheckCid);
              if cid<>-1 then
              begin
                methodName := ReadString();
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
              newguid := ReadString();
              Synchronize(CreateControl);
            end;
            CMD_DESTROY:
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

            CMD_LIST_PROPERTIES:
            begin
              cid := ReadUI32();
              Synchronize(CheckCid);
              if cid<>-1 then
              begin
              Synchronize(GetProperties);
              end;

            end;

            CMD_LIST_METHODS:
            begin
              cid := ReadUI32();
              Synchronize(CheckCid);
              if cid<>-1 then
              begin
              Synchronize(GetMethods);
              end;
            end;

            CMD_LIST_EVENTS:
            begin
              cid := ReadUI32();
              Synchronize(CheckCid);
              if cid<>-1 then
              begin
                Synchronize(GetEvents);
              end;
            end;

            CMD_RESIZE:
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
            CMD_GET_PROPERTY:
            begin
              cid := ReadUI32();
              Synchronize(CheckCid);
              if cid<>-1 then
              begin
                  propName := ReadString();
                  if hosts[cid].host.Properties.IndexOf(propName)=-1 then
                  begin
                    WriteString('Error');
                    WriteString('Property does not exist');
                  end
                  else
                  begin
                    propVal := hosts[cid].host.PropertyValue[propName];
                    propTypeStr := VarTypeAsText(VarType(propVal));
                    if VarType(propVal) = varDispatch then
                     propValStr := '-'
                    else if VarType(propVal) = varUnknown then
                      propValStr := '-'
                    else
                  	  propValStr := VarToWideStr(propVal);
                    WriteString(propTypeStr);
                    WriteString(propValStr);
                  end;
              end;
            end;
            CMD_SET_PROPERTY:
            begin
              cid := ReadUI32();
              Synchronize(CheckCid);
              if cid<>-1 then
              begin
                propName := ReadString();
                propValStr := ReadString();
                Synchronize(SetProperty);
              end;
            end;
            CMD_SET_PARENT:
            begin
              cid := ReadUI32();
              Synchronize(CheckCid);
              if cid<>-1 then
              begin
                newhwnd := ReadUI32();
                Synchronize(SetParentWnd);
              end;
            end;
            CMD_CALL_METHOD:
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

//     if t.events.Count<50 then
       t.events.AddObject('',ev);
   end;
end;

end.
