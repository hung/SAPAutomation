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

public class SM21 implements CheckTcode
{
	private final String fromDay;
	private final String toDay;
	private final String customer;
	private final String SID;
	private final List<String> myImages;
	private String lang;
	private int timeout = 10;

	public SM21(String customer, String SID, String lang, String fromDay, String toDay)
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

		String textSystemLog = "images/" + this.lang + "/sm21/textSystemLog.png";
		String textRemoteInstances = "images/" + this.lang + "/sm21/textRemoteInstances.png";
		String textFromDate = "images/" + this.lang + "/sm21/textFromDate.png";
		String textFromDate2 = "images/" + this.lang + "/sm21/textFromDate2.png";
		String textOldSyslog = "images/" + this.lang + "/sm21/textOldSyslog.png";

		String cbProblemsWarnings = "images/" + this.lang + "/sm21/cbProblemsWarnings.png";
		String cbProblemsWarnings2 = "images/" + this.lang + "/sm21/cbProblemsWarnings2.png";

		String textChooseMenu = "images/" + this.lang + "/sm21/textChooseMenu.png";
		String iconTest = "images/" + this.lang + "/sm21/iconTest.png";
		
		try
		{
			myScreen.wait(txtTCODE);
			myScreen.click(txtTCODE);
			org.sikuli.basics.Settings.TypeDelay = 0.05;
			myScreen.type(txtTCODE, "/nsm21\r");

			if (myScreen.exists(textOldSyslog) != null)
			{
				myScreen.click(textOldSyslog);
			}

			myScreen.wait(textSystemLog, this.timeout + 10);
			if ((myScreen.exists(textFromDate) != null) || (myScreen.exists(textFromDate2) != null))
			{
				if (myScreen.exists(textFromDate) != null) {
					myScreen.click(textFromDate);
				} else if (myScreen.exists(textFromDate2) != null) {
					myScreen.click(textFromDate2);
				}
				org.sikuli.basics.Settings.TypeDelay = 0.05;
				myScreen.type("\t");

				org.sikuli.basics.Settings.TypeDelay = 0.05;
				myScreen.type(this.fromDay);

				org.sikuli.basics.Settings.TypeDelay = 0.05;
				myScreen.type("\t");

				org.sikuli.basics.Settings.TypeDelay = 0.05;
				myScreen.type("00:00:00");


				if (myScreen.exists(cbProblemsWarnings) != null)
				{
					myScreen.click(cbProblemsWarnings);
				}
				else if (myScreen.exists(cbProblemsWarnings2) != null)
				{
					myScreen.click(cbProblemsWarnings2);
				}

				org.sikuli.basics.Settings.TypeDelay = 0.05;
				myScreen.type("l", KeyModifier.ALT);
				
				if (lang.equals("EN"))
				{
					org.sikuli.basics.Settings.TypeDelay = 0.05;
					myScreen.type("ca");
				}
				else
				{
					myScreen.click(textChooseMenu);
					org.sikuli.basics.Settings.TypeDelay = 0.05;
					
					if (lang.equals("FR"))
						myScreen.type("t",KeyModifier.SHIFT);
					else if (lang.equals("DE"))
						myScreen.type("a",KeyModifier.SHIFT);					
				}

				myScreen.wait(textRemoteInstances, this.timeout + 10);

				org.sikuli.basics.Settings.TypeDelay = 0.05;
				myScreen.type(Key.F8);

				myScreen.wait(iconTest, 20);


				logger.info("--> Capturing image sm21 first page");
				String imgPath = screenShot.captureImage(myScreen, this.customer, this.SID, "sm21_first");
				if (imgPath != null)
				{
					this.myImages.add(imgPath);
				}
				logger.info("--> Done capture image sm21 first page!");

				myScreen.type(Key.PAGE_DOWN, KeyModifier.CTRL);
				logger.info("--> Capturing image sm21 last page");
				imgPath = screenShot.captureImage(myScreen, this.customer, this.SID, "sm21_last");
				if (imgPath != null)
				{
					this.myImages.add(imgPath);
				}
				logger.info("--> Done capture image sm21 last page!");
			}

			return true;
		}
		catch (FindFailed e) {
			logger.fatal("--> Error checking tcode sm21 :" + e.getMessage()); 
		}
		return false;
	}


	public String Title()
	{
		return "SM21: System Log";
	}


	public List<String> getScreenShot()
	{
		return this.myImages;
	}
}

