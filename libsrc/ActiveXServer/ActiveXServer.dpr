program ActiveXServer;

uses
  Forms,
  uMain in 'uMain.pas' {frmMain},
  ActiveXHost in 'ActiveXHost.pas',
  TypeLibViewer in 'TypeLibViewer.pas',
  Windows,
  dialogs,
  RegAxCtrlList in 'RegAxCtrlList.pas',
  UThreadStringList in 'UThreadStringList.pas';

{$R *.res}

begin
  if ParamCount<1 then
   begin
     MessageDlg('Incorrect commandline arguments.',mtError,[mbOK],0);
     Application.Terminate;
   end;
  Application.Initialize;
  Application.Title := 'ActiveX Server';
  Application.CreateForm(TfrmMain, frmMain);
  ShowWindow(Application.Handle, SW_HIDE);
  SetWindowLong(Application.Handle, GWL_EXSTYLE,
  GetWindowLong(Application.Handle, GWL_EXSTYLE) or WS_EX_TOOLWINDOW);
  ShowWindow(Application.Handle, SW_SHOW);
  frmMain.StartThread;
  Application.Run;
end.
