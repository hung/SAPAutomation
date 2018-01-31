/*******************************************************************************
 * Copyright (C) 2018 kienhung
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package mysap.tcode;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.Logger;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.Screen;

public class DB12 implements CheckTcode
{
	private final String customer;
	private final String SID;
	private final List<String> myImages;
	private String lang;
	private int timeout = 10;

	public DB12(String customer, String SID, String lang)
	{
		this.customer = customer;
		this.SID = SID;
		this.lang = lang;
		this.myImages = new ArrayList();
	}


	public void setTimeout(int timeout)
	{
		this.timeout = timeout;
	}


	public Boolean Check()
	{
		Screen myScreen = new Screen();
		myScreen.setAutoWaitTimeout(this.timeout);

		String txtTCODE = "images/" + this.lang + "/login/txtTCODE.png";

		String btOverview = "images/" + this.lang + "/db12/btOverview.png";
		String btOverview2 = "images/" + this.lang + "/db12/btOverview2.png";

		String btOverviewRedo = "images/" + this.lang + "/db12/btOverviewRedo.png";

		String iconTest = "images/" + this.lang + "/db12/iconTest.png";
		String textBackupLogs = "images/" + this.lang + "/db12/textBackupLogs.png";
		try
		{
			myScreen.wait(txtTCODE);
			myScreen.click(txtTCODE);
			org.sikuli.basics.Settings.TypeDelay = 0.05;
			myScreen.type(txtTCODE, "/ndb12\r");
			myScreen.type("\r");

			myScreen.wait(textBackupLogs, this.timeout + 10);

			logger.info("--> Capturing image db12 backup overview");
			String imgPath = screenShot.captureImage(myScreen, this.customer, this.SID, "db12_overview");
			if (imgPath != null)
			{
				this.myImages.add(imgPath);
			}
			logger.info("--> Done capture image db12 backup overview!");


			if ((myScreen.exists(btOverview) != null) || (myScreen.exists(btOverview2) != null))
			{
				if (myScreen.exists(btOverview) != null) {
					myScreen.click(btOverview);
				} else if (myScreen.exists(btOverview2) != null) {
					myScreen.click(btOverview2);
				}
				myScreen.wait(iconTest);

				logger.info("--> Capturing image db12 backup");
				imgPath = screenShot.captureImage(myScreen, this.customer, this.SID, "db12_backup");
				if (imgPath != null)
				{
					this.myImages.add(imgPath);
				}
				logger.info("--> Done capture image db12 backup!");

				myScreen.click(iconTest);
				myScreen.type(Key.F3);
			}

			myScreen.wait(btOverviewRedo, this.timeout + 10);
			if (myScreen.exists(btOverviewRedo) != null)
			{
				myScreen.click(btOverviewRedo);
				myScreen.wait(iconTest);

				logger.info("--> Capturing image db12 redo");
				imgPath = screenShot.captureImage(myScreen, this.customer, this.SID, "db12_redo");
				if (imgPath != null)
				{
					this.myImages.add(imgPath);
				}
				logger.info("--> Done capture image db12 redo!");
			}


			return true;
		}
		catch (FindFailed e)
		{
			logger.fatal("--> Error checking tcode db12 :" + e.getMessage()); 
		}
		return false;
	}



	public String Title()
	{
		return "DB12: Backups and Logs";
	}


	public List<String> getScreenShot()
	{
		return this.myImages;
	}
}
