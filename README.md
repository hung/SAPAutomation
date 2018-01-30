# SAPAutomation
SAP daily check automation with Sikuli

Project using eclipse and some java lib
* freemarker
* log4j
* minimal-json
* sikulixapi

SAPAutomation.jar requires Java JRE 1.8 and SAPGUI 7.40 (SAP Signature Theme) to run.

### Features:

- Auto check tcode list (DB02,DB12,DB13,DB16,SM12,SM13,SM21,SM28,SM35,SM37,SM50,SM51,SM58,SMQ1,SMQ2,SP01,ST02,ST03,ST04,ST06,ST22), can be implemented more
- Support multiple languages (EN and FR), can be implemented more
- Support auto login and logout
- Support auto remote desktop (.rdp file - experimental)
- Support auto VPN (experimental, need specific implement)

### Usage

* First create JSON config file (ex: abc.json)
* Encrypt password of user using for check system

java -jar SAPAutomation.jar /enc KEY TEXT

```sh
java -jar SAPAutomation.jar /enc MY_KEY MY_UNCRYPTED_PASSWORD
```
* Run automation check tool

java -jar SAPAutomation.jar /chk JSONFILE KEY SID

For check all system in Json config file
```sh
java -jar SAPAutomation.java /chk abc.json MY_KEY ALL
```

For check specific system in Json config file
```sh
java -jar SAPAutomation.java /chk abc.json MY_KEY PRO,QAS
```

### Config parameter (abc.json)
- CustomerName : string (Name of the folder under reports folder)
- SapShortCut : boolean (Using sapshcut.exe to login to system. Prefer option)
- AutoLogin : boolean (Or using SAP GUI login form, you need left the login form open on the screen in order to login)
- AutoLogout : boolean (auto close SAP GUI after finish check)
- AutoVPN : boolean (experimental)
- AutoRDP : boolean (experimental)
- OnlyCheck : boolean (Skip checking Login screen. Check TCODE list only after you logged in successfully to SAP GUI and left the GUI on the screen)
- Delay : int (Delay betweeb each tcode)
- Timeout: int (Timeout of sikuli if not find object on the screen)

### Note:
- Need change password to encrypt string in JSON file.
- Need Java JRE 1.8 and SAP GUI 740 to run (can run SAP GUI > 740), SAP Kernel >= 700)
- Report file will be generated in folder .\reports\CustomerName\SID\CustomerName_SID_ddmmyyyy.html

### To do:
