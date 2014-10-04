(*******************************************************************************
 *   Based on Chris Bells code on http://www.doogal.co.uk/activex.php          *
 *******************************************************************************)

 unit RegAxCtrlList;

interface

uses
  Classes, Registry;

type
  TRegAxCtrlList = class
  private
    FControls : TStrings;
    FClassIDs : TStrings;
    FFileNames : TStrings;
    FProgIDs : TStrings;
    Fregistry : TRegistry;
    procedure Sort;
  protected
    procedure GetControlEntries;
    procedure FilterControls;
  public
    constructor Create;virtual;
    destructor Destroy;override;
    procedure Refresh;
    property Descriptions : TStrings read FControls;
    property ClassIDs : TStrings read FClassIDs;
    property FileNames : TStrings read FFileNames;
    property ProgIDs : TStrings read FProgIDs;
  end;

////////////////////////////////////////////////////////////////////////////////

implementation

uses
  Windows, SysUtils, COMObj, ActiveXHost;

////////////////////////////////////////////////////////////////////////////////
{ TMetRegAxCtrlList }

constructor TRegAxCtrlList.Create;
begin
  FControls := TStringList.Create;
  FClassIDs := TStringList.Create;
  FFileNames := TStringList.Create;
  FProgIDs := TStringList.Create;
  FRegistry := TRegistry.Create(KEY_READ);  //LOCAL_MACHINE classes can'be opened with KEY_ALL_ACCESS
  FRegistry.RootKey := HKEY_CLASSES_ROOT;
  GetControlEntries;
end;

////////////////////////////////////////////////////////////////////////////////

destructor TRegAxCtrlList.Destroy;
begin
  FRegistry.Free;
  FProgIDs.Free;
  FFileNames.Free;
  FControls.Free;
  FClassIDs.Free;
  inherited;
end;

////////////////////////////////////////////////////////////////////////////////

procedure TRegAxCtrlList.FilterControls;
var
  LIndex : integer;
  LTempList : TStringList;
  i:integer;
begin



  LIndex := FClassIDs.IndexOf('CLSID');
  if LIndex > -1 then
    FClassIDs.Delete(LIndex);
  LTempList := TStringList.Create;
  try
    for LIndex := FClassIDs.Count -1 downto 0 do
    begin
      LTempList.Clear;

      FRegistry.OpenKey('\CLSID\' + FClassIDs[Lindex], False);
      FRegistry.GetKeyNames(LTempList);

      if FClassIds[Lindex]='{D27CDB6E-AE6D-11cf-96B8-444553540000}' then
      begin
        for i := 0 to LTempList.Count - 1 do
        begin
          OutputDebugString(PChar(LTempList[i])) ;
        end;
      end;

      if (LTempList.IndexOf('Control') = -1) then
      	FClassIDs.Delete(LIndex)
      else
      begin
        // get the description
        FControls.Insert(0, FRegistry.ReadString(''));

        // get the filename
        FFileNames.Insert(0, TActiveXHost.FileName(StringToGUID(FClassIDs[Lindex])));

        // get the ProgID
        if FRegistry.OpenKey('\CLSID\' + FClassIDs[Lindex] + '\ProgID', False) then
          FProgIDs.Insert(0, FRegistry.ReadString(''))
        else
          FProgIDs.Insert(0, '');
      end;
    end;
  finally
    LTempList.Free;
  end;
end;

////////////////////////////////////////////////////////////////////////////////

procedure TRegAxCtrlList.GetControlEntries;
begin
  FControls.Clear;
  FClassIDs.Clear;
  FRegistry.OpenKey('\CLSID',False);
  Fregistry.GetKeyNames(FClassIDs);
  FilterControls;
  Sort;
end;

////////////////////////////////////////////////////////////////////////////////

procedure TRegAxCtrlList.Sort;
var
  I, J : integer;
begin
  // just a simple bubble sort
  for I := FControls.Count-1 downto 0 do
  begin
    for J := 0 to FControls.Count-2 do
    begin
      if (CompareText(FControls[J], FControls[J + 1]) > 0) then
      begin
        FControls.Exchange(J, J + 1);
        FClassIDs.Exchange(J, J + 1);
        FFileNames.Exchange(J, J + 1);
        FProgIDs.Exchange(J, J + 1);
      end;
    end;
  end;
end;

////////////////////////////////////////////////////////////////////////////////

procedure TRegAxCtrlList.Refresh;
begin
  GetControlEntries;
end;

end.
