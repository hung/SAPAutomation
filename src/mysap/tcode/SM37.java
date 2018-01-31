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

public class SM37 implements CheckTcode
{
	private final String fromDay;
	private final String toDay;
	private final String customer;
	private final String SID;
	private final List<String> myImages;
	private String lang;
	private int timeout = 10;

	public SM37(String customer, String SID, String lang, String fromDay, String toDay)
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

		String textSimpleJobSelection = "images/" + this.lang + "/sm37/textSimpleJobSelection.png";

		String cbActive = "images/" + this.lang + "/sm37/cbActive.png";
		String cbFinished = "images/" + this.lang + "/sm37/cbFinished.png";
		String cbReleased = "images/" + this.lang + "/sm37/cbReleased.png";
		String cbReady = "images/" + this.lang + "/sm37/cbReady.png";

		String textFrom = "images/" + this.lang + "/sm37/textFrom.png";

		String iconStop = "images/" + this.lang + "/sm37/iconStop.png";
		String textNoJob = "images/" + this.lang + "/sm37/textNoJob.png";
		String textSummary = "images/" + this.lang + "/sm37/textSummary.png";

		String textJobName1 = "images/" + this.lang + "/sm37/textJobName1.png";
		String textJobName0 = "images/" + this.lang + "/sm37/textJobName0.png";
		
		try
		{
			myScreen.wait(txtTCODE);
			myScreen.click(txtTCODE);
			org.sikuli.basics.Settings.TypeDelay = 0.05;
			myScreen.type(txtTCODE, "/nsm37\r");

			myScreen.wait(textSimpleJobSelection, this.timeout + 10);
			if (myScreen.exists(textSimpleJobSelection) != null)
			{
				if (myScreen.exists(textJobName1, this.timeout + 10) != null)
				{
					myScreen.click(textJobName1);
					myScreen.type("\t");
				}
				else if(myScreen.exists(textJobName0, this.timeout + 10) != null)
				{
					myScreen.click(textJobName0);
					myScreen.type("\t");
				}

				org.sikuli.basics.Settings.TypeDelay = 0.05;
				myScreen.type("*");

				org.sikuli.basics.Settings.TypeDelay = 0.05;
				myScreen.type("\t*");

				org.sikuli.basics.Settings.TypeDelay = 0.05;
				myScreen.type("\t");
				
				org.sikuli.basics.Settings.TypeDelay = 0.05;
				myScreen.type("\t ");

				org.sikuli.basics.Settings.TypeDelay = 0.05;
				myScreen.type("\t ");

				org.sikuli.basics.Settings.TypeDelay = 0.05;
				myScreen.type("\t ");

				org.sikuli.basics.Settings.TypeDelay = 0.05;
				myScreen.type("\t ");

				myScreen.click(textFrom);

				org.sikuli.basics.Settings.TypeDelay = 0.05;
				myScreen.type("\t" + this.fromDay);

				org.sikuli.basics.Settings.TypeDelay = 0.05;
				myScreen.type("\t" + this.toDay);

				myScreen.type(Key.F8);

				if (myScreen.exists(textNoJob, this.timeout + 10) == null)
				{

					if (myScreen.exists(iconStop, this.timeout + 10) != null)
					{
						if (myScreen.exists(textSummary, this.timeout + 10) == null)
						{
							org.sikuli.basics.Settings.TypeDelay = 0.05;
							myScreen.type(Key.PAGE_DOWN, KeyModifier.CTRL);
						}
					}
				}

				logger.info("--> Capturing image sm37");
				String imgPath = screenShot.captureImage(myScreen, this.customer, this.SID, "sm37");
				if (imgPath != null)
				{
					this.myImages.add(imgPath);
				}
				logger.info("--> Done capture image sm37!");
			}



			return true;
		}
		catch (FindFailed e) {
			logger.fatal("--> Error checking tcode sm37 :" + e.getMessage()); 
		}
		return false;
	}


	public String Title()
	{
		return "SM37: Jobs Overview";
	}


	public List<String> getScreenShot()
	{
		return this.myImages;
	}
}
