/*******************************************************************************
 * Copyright (C) 2018 kienhung
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package sapconsole;

import java.io.File;
import java.io.PrintStream;
import java.util.Map;
import mysap.AES;
import mysap.ConnectRDP;
import mysap.ConnectVPN;
import mysap.Login;
import mysap.SapSystem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sikuli.script.ImagePath;

public class SAPConsole
{
	static final Logger logger = LogManager.getLogger(SAPConsole.class.getName());

	static SapSystem sapsystem = new SapSystem();

	public static void main(String[] args)
	{
		if ((args.length == 0) || (args.length < 3) || (args.length > 4))
		{
			System.out.println("Usage : java -jar SAPAutomation.jar /chk JSONFILE KEY SID");
			System.out.println("   OR : java -jar SAPAutomation.jar /enc KEY TEXT");
			System.out.println();
			System.out.println("-------- Example --------");
			System.out.println("Check all system : java -jar SAPAutomation.jar /chk JSONFILE KEY ALL");
			System.out.println("Check one or more systems : java -jar SAPAutomation.jar /chk JSONFILE KEY SID,SID2");
			System.out.println("Encrypt text : java -jar SAPAutomation.jar /enc \"KEY\" \"Hello World\"");

		}
		else
		{
			String act = args[0];
			if (act.equals("/chk") && args.length == 4 )
			{
				String settingFile = args[1];
				String stringKey = args[2];
				String SID = args[3].toUpperCase();

				logger.info("Reading setting file....");
				try
				{
					File f = new File(System.getProperty("java.class.path"));
					
					File dir = f.getAbsoluteFile().getParentFile();
					String path = dir.toString();
					
					sapsystem.readConfig(path + "/" + settingFile, stringKey);
					logger.info("Setting load for customer: " + sapsystem.getCustomerName());

					ImagePath.setBundlePath(path);

					logger.info("Success set image path");

					if (sapsystem.isAutoVPN().booleanValue())
					{
						String customer = sapsystem.getCustomerName();
						logger.warn("Connecting to VPN");
						logger.info("--> Process connect to [" + customer + "] VPN");
						if (ConnectVPN.ProcessConnect(customer, sapsystem.getOS()).booleanValue())
						{
							logger.info("--> Connected to [" + customer + "] VPN network!!!");
						}
						else
						{
							logger.fatal("--> Cannot connect to [" + customer + "] VPN network");
							logger.fatal("--> VPN not implement yet"); return;
						}
					}

					String customer;
					if (sapsystem.isAutoRDP().booleanValue())
					{
						customer = sapsystem.getCustomerName();
						logger.warn("Connecting to RDP");
						logger.info("--> Process connect to [" + customer + "] RDP computer");
						if (ConnectRDP.ProcessConnect(customer, sapsystem.getOS()).booleanValue())
						{
							logger.info("--> Connected to [" + customer + "] RDP computer!!!");
						}
						else
						{
							logger.fatal("--> Cannot connect to [" + customer + "] RDP computer");
							return;
						}
					}

					logger.info("--------- Checking " + SID + " System ---------");

					if (SID.equals("ALL"))
					{
						for (String s : sapsystem.getSID())
						{
							processCheck(s);
						}
					}
					else
					{
						String[] systemArray = SID.split(",");
						
						for (String s : systemArray)
						{
							processCheck(s.trim());
							
						}

					}

					if (sapsystem.isAutoVPN().booleanValue())
					{
						logger.info("Process disconnect VPN ...");
						ConnectVPN.ProcessDisconnect();
					}

					if (sapsystem.isAutoRDP().booleanValue())
					{
						logger.info("Process disconnect RDP ...");
						ConnectRDP.ProcessDisconnect();
					}
				}
				catch (Exception ex) {
					logger.fatal("Cannot load setting file: " + ex.getMessage());
				}
			}
			else if (act.equals("/enc"))
			{
				String key = args[1];
				String text = args[2];

				System.out.println("Encrypted Text: " + AES.encrypt(text, key));
			}
			else
			{
				System.out.println("Usage : java -jar SAPAutomation.jar /chk JSONFILE KEY SID");
				System.out.println("   OR : java -jar SAPAutomation.jar /enc KEY TEXT");
				System.out.println();
			}
		}
	}





	private static void processCheck(String SID) throws InterruptedException
	{
		logger.warn("Checking GUI");

		Map<String, Object> sys1 = sapsystem.getSystemBySID(SID);

		if (sys1 == null)
		{
			logger.fatal("SAP System SID " + SID + " does not exist in JSON file. Please check SID!!!");
			return;
		}

		logger.info("Success get system info");



		logger.info("--> Processing auto login system " + SID);

		if (sapsystem.isAutoLogin().booleanValue()) {
			Login.setSapAutoLogin(true);
		} else if (sapsystem.isSapShortCut().booleanValue()) {
			Login.setSapShortcut(true);
		} else if (sapsystem.isOnlyCheck().booleanValue()) {
			Login.setOnlyCheck(true);
		} else {
			//auto login with username and password input on GUI
			Login.setSapGUI(true);
		}

		Login.setOS(sapsystem.getOS());

		Boolean LoggedIn = Login.ProcessLogin(SID, (String)sys1.get("ConnectionString"), (String)sys1.get("Client"), (String)sys1.get("Username"), (String)sys1.get("Password"), (String)sys1.get("Desc"), (String)sys1.get("Lang"));
		if (LoggedIn)
		{
			String lang = (String)sys1.get("Lang");
			
			logger.info("--> LoggedIn to system " + SID);
			if (Login.CheckLogin(lang))
			{
				logger.info("--> Everything OK! Processing auto check system " + SID);
				String[] tcodeArray = (String[])sys1.get("Tcode");
				String fromDay = (String)sys1.get("Fromday");
				String toDay = (String)sys1.get("Today");
				

				logger.info("Checking tcode in the list....");

				CheckThread check1 = new CheckThread(sapsystem.getCustomerName(), SID, lang, tcodeArray, fromDay, toDay, sapsystem.getDelay(), sapsystem.getTimeout());

				Thread thread1 = new Thread(check1, SID);
				thread1.start();
				thread1.join();
			}
			else
			{
				logger.fatal("--> Cannot check login");
			}
			
			if (sapsystem.isAutoLogout())
			{
				logger.info("Process logout....");
				Login.ProcessLogout(lang);
			}

		}
		else
		{
			logger.fatal("--> Cannot process login.");
			return;
		}
	}
}
