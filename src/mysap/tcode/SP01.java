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
import org.sikuli.script.KeyModifier;
import org.sikuli.script.Screen;


public class SP01 implements CheckTcode
{
	private final String fromDay;
	private final String toDay;
	private final String customer;
	private final String SID;
	private final List<String> myImages;
	private String lang;
	private int timeout = 10;

	public SP01(String customer, String SID, String lang, String fromDay, String toDay)
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

		String textSpoolRequests = "images/" + this.lang + "/sp01/textSpoolRequests.png";

		String textCreatedBy = "images/" + this.lang + "/sp01/textCreatedBy.png";
		String textDateCreated = "images/" + this.lang + "/sp01/textDateCreated.png";
		String textClient = "images/" + this.lang + "/sp01/textClient.png";

		String cbWithout = "images/" + this.lang + "/sp01/cbWithout.png";
		String cbProc = "images/" + this.lang + "/sp01/cbProc.png";
		String cbSuccess = "images/" + this.lang + "/sp01/cbSuccess.png";

		String btStart = "images/" + this.lang + "/sp01/btStart.png";

		String textListRequests = "images/" + this.lang + "/sp01/textListRequests.png";

		try
		{
			myScreen.wait(txtTCODE);
			myScreen.click(txtTCODE);
			org.sikuli.basics.Settings.TypeDelay = 0.05;
			myScreen.type(txtTCODE, "/nsp01\r");

			myScreen.wait(textSpoolRequests, this.timeout + 10);
			if (myScreen.exists(textCreatedBy) != null)
			{
				myScreen.click(textCreatedBy);

				org.sikuli.basics.Settings.TypeDelay = 0.05;
				myScreen.type("\t*");

				myScreen.click(textDateCreated);

				org.sikuli.basics.Settings.TypeDelay = 0.05;
				myScreen.type("\t" + this.fromDay);

				org.sikuli.basics.Settings.TypeDelay = 0.05;
				myScreen.type("\t" + this.toDay);

				myScreen.click(textClient);

				org.sikuli.basics.Settings.TypeDelay = 0.05;
				myScreen.type("\t*");
				myScreen.type("\t");

				org.sikuli.basics.Settings.TypeDelay = 0.05;
				myScreen.type(Key.PAGE_DOWN, KeyModifier.SHIFT);

				myScreen.click(cbWithout);
				myScreen.click(cbProc);
				myScreen.click(cbSuccess);

				myScreen.type(Key.F8);

				myScreen.wait(textListRequests, this.timeout + 10);



				logger.info("--> Capturing image sp01");
				String imgPath = screenShot.captureImage(myScreen, this.customer, this.SID, "sp01");
				if (imgPath != null)
				{
					this.myImages.add(imgPath);
				}
				logger.info("--> Done capture image sp01!");
			}



			return true;
		}
		catch (FindFailed e) {
			logger.fatal("--> Error checking tcode sp01 :" + e.getMessage());
		}
		return false;
	}


	public String Title()
	{
		return "SP01: Spool Requests";
	}


	public List<String> getScreenShot()
	{
		return this.myImages;
	}
}

