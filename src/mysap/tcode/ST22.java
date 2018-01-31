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


public class ST22 implements CheckTcode
{
	private final String fromDay;
	private final String toDay;
	private final String customer;
	private final String SID;
	private final List<String> myImages;
	private String lang;
	private int timeout = 10;

	public ST22(String customer, String SID, String lang, String fromDay, String toDay)
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

		String textAbapRuntime = "images/" + this.lang + "/st22/textAbapRuntime.png";

		String textDate = "images/" + this.lang + "/st22/textDate.png";
		String textUser = "images/" + this.lang + "/st22/textUser.png";

		String cbAffected = "images/" + this.lang + "/st22/cbAffected.png";
		String cbLongRuntime = "images/" + this.lang + "/st22/cbLongRuntime.png";
		String cbCorresponding = "images/" + this.lang + "/st22/cbCorresponding.png";
		String cbCorresponding2 = "images/" + this.lang + "/st22/cbCorresponding2.png";
		String cbShortText = "images/" + this.lang + "/st22/cbShortText.png";

		String btStart = "images/" + this.lang + "/st22/btStart.png";

		String textListRuntime = "images/" + this.lang + "/st22/textListRuntime.png";
		String textNoRuntime = "images/" + this.lang + "/st22/textNoRuntime.png";
		String textSummary = "images/" + this.lang + "/st22/textSummary.png";

		try
		{
			myScreen.wait(txtTCODE);
			myScreen.click(txtTCODE);

			org.sikuli.basics.Settings.TypeDelay = 0.05;
			myScreen.type(txtTCODE, "/nst22\r");

			myScreen.wait(textAbapRuntime, this.timeout + 10);
			if (myScreen.exists(textDate) != null)
			{
				myScreen.mouseMove(textDate);
				myScreen.click(textDate);

				org.sikuli.basics.Settings.TypeDelay = 0.05;
				myScreen.type("\t" + this.fromDay);

				org.sikuli.basics.Settings.TypeDelay = 0.05;
				myScreen.type("\t" + this.toDay);

				myScreen.mouseMove(textUser);
				myScreen.click(textUser);

				org.sikuli.basics.Settings.TypeDelay = 0.05;
				myScreen.type("\t*");

				myScreen.type(Key.PAGE_DOWN);

				if (myScreen.exists(cbCorresponding, 5) != null) {
					myScreen.click(cbCorresponding);
				} else if (myScreen.exists(cbCorresponding2, 5) != null) {
					myScreen.click(cbCorresponding2);
				}
				else {
					if (myScreen.exists(cbAffected, 5) != null) {
						myScreen.click(cbAffected);
					}
					if (myScreen.exists(cbLongRuntime, 5) != null) {
						myScreen.click(cbLongRuntime);
					}
					if (myScreen.exists(cbShortText, 5) != null) {
						myScreen.click(cbShortText);
					}
				}

				myScreen.type(Key.F8);

				if (myScreen.exists(textNoRuntime, this.timeout) == null)
				{
					myScreen.exists(textListRuntime, this.timeout + 10);
				}



				logger.info("--> Capturing image st22");
				String imgPath = screenShot.captureImage(myScreen, this.customer, this.SID, "st22");
				if (imgPath != null)
				{
					this.myImages.add(imgPath);
				}
				logger.info("--> Done capture image st22!");
			}



			return true;
		}
		catch (FindFailed e) {
			logger.fatal("--> Error checking tcode st22 :" + e.getMessage()); 
		}
		return false;
	}


	public String Title()
	{
		return "ST22: ABAP Runtime Error";
	}


	public List<String> getScreenShot()
	{
		return this.myImages;
	}
}
