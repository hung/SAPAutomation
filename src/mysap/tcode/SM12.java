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
import org.sikuli.script.Screen;


public class SM12 implements CheckTcode
{
	private final String customer;
	private final String SID;
	private final List<String> myImages;
	private String lang;
	private int timeout = 10;

	public SM12(String customer, String SID, String lang)
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

		String textSelectLockEntries = "images/" + this.lang + "/sm12/textSelectLockEntries.png";
		String textTableName = "images/" + this.lang + "/sm12/textTableName.png";
		String btRefresh = "images/" + this.lang + "/sm12/btRefresh.png";
		try
		{
			myScreen.wait(txtTCODE);
			myScreen.click(txtTCODE);
			org.sikuli.basics.Settings.TypeDelay = 0.05;
			myScreen.type(txtTCODE, "/nsm12\r");

			myScreen.wait(textSelectLockEntries, this.timeout + 10);
			if (myScreen.exists(textSelectLockEntries) != null)
			{

				myScreen.click(textTableName);

				myScreen.type("\t*\t*\t*\t*");
				myScreen.type("\r");

				myScreen.wait(btRefresh);


				logger.info("--> Capturing image sm12");
				String imgPath = screenShot.captureImage(myScreen, this.customer, this.SID, "sm12");
				if (imgPath != null)
				{
					this.myImages.add(imgPath);
				}
				logger.info("--> Done capture image sm12!");
			}

			return true;
		}
		catch (FindFailed e) {
			logger.fatal("--> Error checking tcode sm12 :" + e.getMessage()); 
		}
		return false;
	}


	public String Title()
	{
		return "SM12: Locked Entries";
	}


	public List<String> getScreenShot()
	{
		return this.myImages;
	}
}

