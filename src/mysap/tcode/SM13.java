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


public class SM13 implements CheckTcode
{
	private final String fromDay;
	private final String toDay;
	private final String customer;
	private final String SID;
	private final List<String> myImages;
	private String lang;
	private int timeout = 10;

	public SM13(String customer, String SID, String lang, String fromDay, String toDay)
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

		String textInitialScreen = "images/" + this.lang + "/sm13/textInitialScreen.png";
		String cbCanceled = "images/" + this.lang + "/sm13/cbCanceled.png";
		String textFromDate = "images/" + this.lang + "/sm13/textFromDate.png";

		String btRefresh = "images/" + this.lang + "/sm13/btRefresh.png";

		try
		{
			myScreen.wait(txtTCODE);
			myScreen.click(txtTCODE);
			org.sikuli.basics.Settings.TypeDelay = 0.05;
			myScreen.type(txtTCODE, "/nsm13\r");

			myScreen.wait(textInitialScreen, this.timeout + 10);
			if (myScreen.exists(textInitialScreen) != null)
			{
				myScreen.click(cbCanceled);

				org.sikuli.basics.Settings.TypeDelay = 0.05;
				myScreen.type("\t");
				org.sikuli.basics.Settings.TypeDelay = 0.05;
				myScreen.type("\t");
				org.sikuli.basics.Settings.TypeDelay = 0.05;
				myScreen.type(this.fromDay);
				myScreen.type("\r");

				myScreen.wait(btRefresh);


				logger.info("--> Capturing image sm13");
				String imgPath = screenShot.captureImage(myScreen, this.customer, this.SID, "sm13");
				if (imgPath != null)
				{
					this.myImages.add(imgPath);
				}
				logger.info("--> Done capture image sm13!");
			}



			return true;
		}
		catch (FindFailed e) {
			logger.fatal("--> Error checking tcode sm13 :" + e.getMessage()); 
		}
		return false;
	}


	public String Title()
	{
		return "SM13: Update Requests";
	}


	public List<String> getScreenShot()
	{
		return this.myImages;
	}
}
