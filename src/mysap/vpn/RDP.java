/*******************************************************************************
 * Copyright (C) 2018 kienhung
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package mysap.vpn;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sikuli.basics.Settings;
import org.sikuli.script.ImagePath;
import org.sikuli.script.Screen;

public class RDP
{
	private int timeout = 10;

	String customer;
	private String os;
	private Logger logger = LogManager.getLogger(RDP.class.getName());
	private Command cmd = new Command();

	public RDP(String customer, String os)
	{
		this.customer = customer;
		this.os = os;
	}


	public void setTimeout(int timeout)
	{
		this.timeout = timeout;
	}

	public Boolean Connect()
	{
		this.logger.info("Connecting to RDP....." + this.customer);

		Screen myScreen = new Screen();
		myScreen.setAutoWaitTimeout(this.timeout);
		String btCancel;

		if (Settings.getOSVersion().equalsIgnoreCase("10.0"))
		{
			btCancel = "images/vpn/rdp/btCancel.png";
		}
		else
		{
			btCancel = "images/vpn/rdp/btCancel2.png";
		}

		String btOKLogged = "images/vpn/rdp/btOKLogged.png";

		String shorcut = String.format("%s%s", new Object[] { ImagePath.getBundlePath(), "/shortcut/" + this.customer + ".rdp" });

		this.logger.info("--> Shortcut path: " + shorcut);

		try
		{
			this.logger.info("--> Run RDP Shortcut command");
			this.cmd.run(shorcut, "");



			Thread.sleep(this.timeout * 1000);

			if (myScreen.waitVanish(btCancel, this.timeout + 20))
			{
				this.logger.info("--> Connected to RDP " + this.customer);

				if (myScreen.exists(btOKLogged, this.timeout) != null)
				{
					myScreen.click(btOKLogged);
				}

				return true;
			}


			this.logger.fatal("--> Please login and remember password for RDP [" + this.customer + "] first.");
			this.logger.fatal("--> Please set RDP screen resolution to 1280x800 or 1366x768. Cannot use full screen");
			this.logger.fatal("--> Also please check \"Don't ask me again for connection this computer\"");
			return false;

		}
		catch (Exception e)
		{

			this.logger.fatal("--> Exception Error login RDP " + this.customer + " :" + e.getMessage()); }
		return false;
	}


	public Boolean Disconnect()
	{
		this.logger.info("Disconnecting RDP....." + this.customer);

		Screen myScreen = new Screen();
		myScreen.setAutoWaitTimeout(this.timeout);

		String btOK;
		String btClose;
		String textRemoteDesktop;
		String textConfirm;

		if (Settings.getOSVersion().equalsIgnoreCase("10.0"))
		{
			btOK = "images/vpn/rdp/btOK.png";
			btClose = "images/vpn/rdp/btClose.png";
			textRemoteDesktop = "images/vpn/rdp/textRemoteDesktop.png";
			textConfirm = "images/vpn/rdp/textConfirm.png";
		}
		else
		{
			btOK = "images/vpn/rdp/btOK2.png";
			btClose = "images/vpn/rdp/btClose2.png";
			textRemoteDesktop = "images/vpn/rdp/textRemoteDesktop2.png";
			textConfirm = "images/vpn/rdp/textConfirm2.png";
		}

		try
		{
			if (myScreen.exists(textRemoteDesktop, this.timeout) != null)
			{
				myScreen.rightClick(textRemoteDesktop);
				if (myScreen.exists(btClose, this.timeout) != null)
				{
					myScreen.click(btClose);
					myScreen.wait(textConfirm);
					myScreen.click(btOK);
				}

				this.logger.info("RDP disconnected for " + this.customer);
				return true;
			}



			this.logger.fatal("--> Cannot find RDP on the screen to close");
			return false;

		}
		catch (Exception e)
		{

			this.logger.fatal("--> Exception Error disconnecting RDP " + this.customer + " :" + e.getMessage()); }
		return false;
	}


	public String toString()
	{
		return "RDP Auto connect to " + this.customer;
	}
}
