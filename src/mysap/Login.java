/*******************************************************************************
 * Copyright (C) 2018 kienhung
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package mysap;

import java.io.File;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.Screen;

public class Login
{
	static final Logger logger = LogManager.getLogger(Login.class.getName());

	private static Boolean x86 = false;
	private static Boolean isSAPShortcut = false;
	private static Boolean isSAPAutoLogin = false;
	private static Boolean isSAPGUI = false;
	private static Boolean isSAPOnlyCheck = false;
	private static String os = "windows10";


	public static Boolean ProcessLogin(String system, String connectionString, String client, String username, String pwd, String desc, String lang)
	{
		logger.info("Process Login to system " + system);

		if (isSAPOnlyCheck.booleanValue())
		{
			logger.info("--> No need process login. Manual Login to SAP system");
			return true;
		}
		if ((isSAPShortcut.booleanValue()) && (CheckSapShortcut().booleanValue()))
		{

			String path = "C:\\\"Program Files\"\\SAP\\FrontEnd\\SAPgui\\sapshcut.exe";
			if (x86.booleanValue())
			{
				path = "C:\\\"Program Files (x86)\"\\SAP\\FrontEnd\\SAPgui\\sapshcut.exe";
			}

			String args2 = String.format("-system=%s -client=%s -user=%s -pw=%s -l=%s -desc=%s", new Object[] { system, client, username, pwd, lang, desc });

			try
			{
				logger.info("Autologin system using sapshcut ");
				Runtime.getRuntime().exec("cmd /c " + path + " " + args2);

				return true;
			}
			catch (Exception ex)
			{
				logger.error("Exception in ProcessLogin: ", ex.getMessage());
			}

		}
		else if (((isSAPGUI) && (CheckSapGUI(lang))) || ((isSAPAutoLogin.booleanValue()) && (CheckSapAutoLogin(connectionString,lang))))
		{

			Screen loginScreen = new Screen();
			String textNewPassword;
			String textClient;

			if (!os.equalsIgnoreCase("windows10"))
			{
				logger.info("--> Using other OS pattern");
				textNewPassword = "images/" + lang + "/login/textNewPassword2.png";
				textClient = "images/" + lang + "/login/textClient2.png";

			}
			else
			{
				logger.info("--> Using OS windows 10 pattern");
				textNewPassword = "images/" + lang + "/login/textNewPassword.png";
				textClient = "images/" + lang + "/login/textClient.png";
				
			}


			if ((loginScreen.exists(textNewPassword, 20) != null))
			{
				try
				{

					if (loginScreen.exists(textClient) != null) {
						loginScreen.click(textClient);
					} 

					org.sikuli.basics.Settings.TypeDelay = 0.05D;
					loginScreen.type("\t" + client + "\t" + username + "\t" + pwd + "\t" + lang);
					loginScreen.type("\r");

					if ((loginScreen.waitVanish(textNewPassword, 20.0D)))
					{
						logger.info("--> Login success.");

						return true;
					}


					logger.fatal("--> Login failed. Please check username / password");
					return false;

				}
				catch (FindFailed ex)
				{

					logger.fatal("Exception in ProcessLogin: ", ex.getMessage());
				}
			}
		}

		return false;
	}

	public static Boolean ProcessLogout(String lang)
	{
		logger.info("Process Auto Logoff");
		Screen LoggedIn = new Screen();

		String btClose = "images/" + lang + "/login/btClose.png";
		String textLogoff = "images/" + lang + "/login/textLogoff.png";
		String btYes = "images/" + lang + "/login/btYes.png";

		if (LoggedIn.exists(btClose) != null) {
			try
			{
				LoggedIn.click(btClose);
				if (LoggedIn.exists(textLogoff) != null)
				{
					LoggedIn.click(btYes);
					if (LoggedIn.waitVanish(textLogoff))
					{
						logger.info("--> System logged off.");
						return true;
					}
				}
				logger.fatal("--> System cannot logoff. cannot find logoff confirm");
				return false;
			} catch (FindFailed ex) {
				logger.fatal("--> System cannot logoff. Error: " + ex.getMessage());
				return false;
			}
		}



		logger.fatal("--> System cannot logoff. cannot find close button");
		return false;
	}




	public static Boolean CheckGUILoggedIn(String lang)
	{
		try
		{
			logger.info("Checking GUI Logged In");
			Screen LoggedIn = new Screen();

			String textSAP = "images/" + lang + "/login/textSAP.png";
			String textShortCutOpen = "images/" + lang + "/login/textShortCutOpen.png";

			LoggedIn.wait(textShortCutOpen);
			if (LoggedIn.exists(textSAP) != null)
			{
				logger.fatal("--> GUI not yet login. Please login first");
				return false;
			}
			if (LoggedIn.exists(textShortCutOpen) != null)
			{
				logger.info("--> GUI Already Logged In");
				return true;
			}


			logger.fatal("--> GUI not yet open. Please open GUI and login first.");
			return false;
		}
		catch (FindFailed ex) {
			logger.fatal("--> GUI not yet open. Please open GUI and login first. Error: " + ex.getMessage()); }
		return false;
	}




	public static Boolean CheckGUIAutoLogin(String lang)
	{
		logger.info("Checking GUI AutoLogin");
		Screen LoggedIn = new Screen();

		String textSAP = "images/" + lang + "/login/textSAP.png";

		if (LoggedIn.exists(textSAP) != null)
		{
			logger.info("--> GUI ready for autologin");

			return false;
		}


		logger.warn("--> GUI not yet open / already logged in");
		return true;
	}




	public static Boolean CheckLogin(String lang)
	{
		logger.info("Checking logged In...");
		Screen LoggedIn = new Screen();


		String textShortCutOpen = "images/" + lang + "/login/textShortCutOpen.png";
		String textEasyAccess = "images/" + lang + "/login/textEasyAccess.png";
		String textEasyAccess2 = "images/" + lang + "/login/textEasyAccess2.png";

		String cbRemember = "images/" + lang + "/login/cbRemember.png";
		String btAllow = "images/" + lang + "/login/btAllow.png";
		
		String copy = "images/" + lang + "/login/copyright.png";
		String info = "images/" + lang + "/login/info.png";
		String btYes2 = "images/" + lang + "/login/btYes2.png";		

		String txtTCODE = "images/" + lang + "/login/txtTCODE.png";

		if ((isSAPAutoLogin.booleanValue()) || (isSAPGUI.booleanValue()) || (isSAPOnlyCheck.booleanValue()))
		{
			try
			{
				LoggedIn.wait(textShortCutOpen, 20.0D);


				LoggedIn.wait(txtTCODE);
				LoggedIn.click(txtTCODE);
				org.sikuli.basics.Settings.TypeDelay = 0.05;
				LoggedIn.type(Key.F3);

				if ((LoggedIn.exists(textEasyAccess, 20) != null) || (LoggedIn.exists(textEasyAccess2, 20.0D) != null))
				{
					logger.error("--> Check login success");
					return true;
				}


				logger.error("--> Cannot check auto login. Not yet login to system!!!");
				return false;
			}
			catch (FindFailed ex) {
				logger.fatal("--> Cannot check auto login exception: " + ex.getMessage());
				return false;
			}
		}
		if (isSAPShortcut.booleanValue())
		{
			try
			{
				if (LoggedIn.exists(cbRemember, 20) != null)
				{
					logger.warn("Allow running short cut for first time");
					LoggedIn.click(cbRemember);
					LoggedIn.click(btAllow);
				}
				
				if (LoggedIn.exists(copy, 10) != null)
				{
					logger.warn("Auto accept copyright");
					LoggedIn.click(btYes2);
				}
				
				if (LoggedIn.exists(info, 10) != null)
				{
					logger.warn("Auto accept info");
					LoggedIn.click(btYes2);
				}				

				LoggedIn.wait(textShortCutOpen, 20.0);


				LoggedIn.click(txtTCODE);
				org.sikuli.basics.Settings.TypeDelay = 0.05;
				LoggedIn.type(Key.F3);


				if ((LoggedIn.exists(textEasyAccess, 20) != null) || (LoggedIn.exists(textEasyAccess2, 20.0D) != null))
				{
					logger.error("--> Check login success using sappshcut");
					return true;
				}


				logger.error("--> Cannot check login using sappshcut. Not yet login to system!!!");
				return false;
			}
			catch (FindFailed ex)
			{
				logger.fatal("--> Cannot check login using shortcut. Exception: " + ex.getMessage());
				return false;
			}
		}

		return false;
	}


	public static void setOS(String myos)
	{
		os = myos;
	}

	public static void setSapShortcut(Boolean b)
	{
		isSAPShortcut = b;
	}

	public static void setSapAutoLogin(Boolean b)
	{
		isSAPAutoLogin = b;
	}

	public static void setSapGUI(Boolean b)
	{
		isSAPGUI = b;
	}

	public static void setOnlyCheck(Boolean b)
	{
		isSAPOnlyCheck = b;
	}

	private static Boolean CheckSapGUI(String lang)
	{
		Screen loginScreen = new Screen();

		String textNewPassword = "images/" + lang + "/login/textNewPassword.png";
		
		if ((loginScreen.exists(textNewPassword, 20) != null) )
		{
			logger.info("--> Check SAPGUI success.");
			return true;
		}


		logger.fatal("--> Check SAPGUI failed.");
		return false;
	}



	private static Boolean CheckSapAutoLogin(String connectionString, String lang)
	{
		Screen loginScreen = new Screen();

		String cbRemember = "images/" + lang + "/login/cbRemember.png";
		String btAllow = "images/" + lang + "/login/btAllow.png";

		String textShortCutOpen = "images/" + lang + "/login/textShortCutOpen.png";

		try
		{
			logger.info("Try to run sapgui.exe..." + connectionString);

			Thread.sleep(10000L);

			loginScreen.type("r", 4);
			loginScreen.type("\b");
			org.sikuli.basics.Settings.TypeDelay = 0.06D;

			String pathx86 = "\"%programfiles(x86)%\\SAP\\FrontEnd\\SAPgui\\sapgui.exe\"  ";
			loginScreen.type(pathx86 + connectionString);

			loginScreen.type("\r");

			logger.info("--> SAP GUI is starting...");
			if (loginScreen.exists(cbRemember, 10.0D) != null)
			{
				logger.warn("Allow running short cut for first time");
				loginScreen.click(cbRemember);
				loginScreen.click(btAllow);
			}

			if (loginScreen.exists(textShortCutOpen) != null)
			{
				logger.info("--> SAP GUI is started.");
				return true;
			}


			logger.fatal("--> Cannot start sapgui.exe. Please check again");
			return false;

		}
		catch (Exception ex)
		{
			logger.fatal("--> Cannot run sapgui.exe. Error Exception: " + ex.getMessage()); }
		return false;
	}



	private static Boolean CheckSapShortcut()
	{
		String pathx86 = "C:\\Program Files (x86)\\SAP\\FrontEnd\\SAPgui\\sapshcut.exe";
		String pathx64 = "C:\\Program Files\\SAP\\FrontEnd\\SAPgui\\sapshcut.exe";
		File sapx86 = new File(pathx86);
		File sapx64 = new File(pathx64);

		if ((sapx86.exists()) || (sapx64.exists()))
		{
			if (sapx86.exists()) x86 = true;
			return true;
		}


		logger.error("Cannot find SAPGUI Shortcut");
		return false;
	}
}

