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
import org.sikuli.script.Key;
import org.sikuli.script.Screen;

public class SOST implements CheckTcode
{
	private final String fromDay;
	private final String toDay;
	private final String customer;
	private final String SID;
	private final List<String> myImages;
	private String lang;
	private int timeout = 10;

	public SOST(String customer, String SID, String lang, String fromDay, String toDay)
	{
		this.fromDay = fromDay;
		this.toDay = toDay;
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

		String textSapConnect = "images/" + this.lang + "/sost/txtSapConnect.png";
		String textSendDate = "images/" + this.lang + "/sost/txtSendDate.png";
		String tabPeriod = "images/" + this.lang + "/sost/tabPeriod.png";
		String btExecute = "images/" + this.lang + "/sost/btExecute.png";
		try
		{
			myScreen.wait(txtTCODE);
			myScreen.click(txtTCODE);
			org.sikuli.basics.Settings.TypeDelay = 0.05;
			myScreen.type(txtTCODE, "/nsost\r");

			myScreen.wait(textSapConnect, this.timeout + 10);
			if (myScreen.exists(textSapConnect) != null)
			{
				if (myScreen.exists(tabPeriod) != null)
				{
					myScreen.click(tabPeriod);
					if (myScreen.exists(textSendDate) != null)
					{
						myScreen.click(textSendDate);
						
						myScreen.type("\t");
						org.sikuli.basics.Settings.TypeDelay = 0.05;
						myScreen.type(this.fromDay);
						
						myScreen.type("\t");
						org.sikuli.basics.Settings.TypeDelay = 0.05;
						myScreen.type(this.toDay);
						
						myScreen.type(Key.ENTER);
						Thread.sleep(5000);
					}
					
				}

				logger.info("--> Capturing image sost");
				String imgPath = screenShot.captureImage(myScreen, this.customer, this.SID, "sost");
				if (imgPath != null)
				{
					this.myImages.add(imgPath);
				}
				logger.info("--> Done capture image sost!");
			}

			return true;
		}
		catch (Exception e) {
			logger.fatal("--> Error checking tcode sost :" + e.getMessage()); 
		}
		return false;
	}


	public String Title()
	{
		return "SOST: Transmission Requests";
	}


	public List<String> getScreenShot()
	{
		return this.myImages;
	}
}
