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


public class SM58 implements CheckTcode
{
	private final String customer;
	private final String SID;
	private final String fromDay;
	private final String toDay;
	private final List<String> myImages;
	private String lang;
	private int timeout = 10;

	public SM58(String customer, String SID, String lang, String fromDay, String toDay)
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

		String textRFC = "images/" + this.lang + "/sm58/textRFC.png";
		String textDisplayPeriod = "images/" + this.lang + "/sm58/textDisplayPeriod.png";
		String textUsername = "images/" + this.lang + "/sm58/textUsername.png";

		String iconTest = "images/" + this.lang + "/sm58/iconTest.png";
		try
		{
			myScreen.wait(txtTCODE);
			myScreen.click(txtTCODE);
			org.sikuli.basics.Settings.TypeDelay = 0.05D;
			myScreen.type(txtTCODE, "/nsm58\r");

			myScreen.wait(textRFC, this.timeout + 10);
			if (myScreen.exists(textRFC) != null)
			{
				org.sikuli.basics.Settings.TypeDelay = 0.05D;
				myScreen.type(this.fromDay);

				org.sikuli.basics.Settings.TypeDelay = 0.05D;
				myScreen.type("\t");

				org.sikuli.basics.Settings.TypeDelay = 0.05D;
				myScreen.type(this.toDay);

				myScreen.click(textUsername);

				org.sikuli.basics.Settings.TypeDelay = 0.05D;
				myScreen.type("\t*");

				myScreen.type(Key.F8);

				myScreen.wait(iconTest, this.timeout + 10);

				logger.info("--> Capturing image sm58");
				String imgPath = screenShot.captureImage(myScreen, this.customer, this.SID, "sm58");
				if (imgPath != null)
				{
					this.myImages.add(imgPath);
				}
				logger.info("--> Done capture image sm58!");
			}

			return true;
		}
		catch (FindFailed e) {
			logger.fatal("--> Error checking tcode sm58 :" + e.getMessage()); 
		}
		
		return false;
	}


	public String Title()
	{
		return "SM58: tRFCs";
	}


	public List<String> getScreenShot()
	{
		return this.myImages;
	}
}
