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


public class SMQ2 implements CheckTcode
{
	private final String customer;
	private final String SID;
	private final List<String> myImages;
	private String lang;
	private int timeout = 10;

	public SMQ2(String customer, String SID, String lang)
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

		String textInboundQueue = "images/" + this.lang + "/smq2/textInboundQueue.png";
		String textClient = "images/" + this.lang + "/smq2/textClient.png";
		String iconTest = "images/" + this.lang + "/smq2/iconTest.png";
		try
		{
			myScreen.wait(txtTCODE);
			myScreen.click(txtTCODE);
			org.sikuli.basics.Settings.TypeDelay = 0.05;
			myScreen.type(txtTCODE, "/nsmq2\r");

			myScreen.wait(textInboundQueue, this.timeout + 10);
			if (myScreen.exists(textInboundQueue) != null)
			{
				myScreen.click(textClient);

				org.sikuli.basics.Settings.TypeDelay = 0.05;
				myScreen.type("\t*");

				myScreen.type(Key.F8);

				myScreen.wait(iconTest);


				logger.info("--> Capturing image smq2");
				String imgPath = screenShot.captureImage(myScreen, this.customer, this.SID, "smq2");
				if (imgPath != null)
				{
					this.myImages.add(imgPath);
				}
				logger.info("--> Done capture image smq2!");
			}

			return true;
		}
		catch (FindFailed e) {
			logger.fatal("--> Error checking tcode smq2 :" + e.getMessage()); 
		}
		return false;
	}


	public String Title()
	{
		return "SMQ2: qRFC (inbound queue)";
	}


	public List<String> getScreenShot()
	{
		return this.myImages;
	}
}
