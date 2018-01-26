# SAPAutomation
SAP daily check automation with Sikuli

Project using eclipse and some java lib
* freemarker
* log4j
* minimal-json
* sikulixapi

SAPAutomation.jar requires Java JRE 1.8 and SAPGUI (default theme) to run.

### Usage

* First create JSON config file (ex: abc.json)
* Encrypt password of user using to check system

java -jar SAPAutomation.jar /enc KEY TEXT

```sh
java -jar SAPAutomation.java /enc MY_KEY MY_UNCRYPTED_PASSWORD
```
* Run automation check tool

java -jar SAPAutomation.jar /chk JSONFILE KEY SID

For check all system in Json config file
```sh
java -jar SAPAutomation.java /chk MY_JSON_FILE MY_KEY ALL
```

For check specific system in Json config file
```sh
java -jar SAPAutomation.java /chk MY_JSON_FILE MY_KEY PRO,QAS
```

### Config parameter

