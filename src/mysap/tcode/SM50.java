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

public class SM50 implements CheckTcode
{
	private final String customer;
	private final String SID;
	private final List<String> myImages;
	private String lang;
	private int timeout = 10;

	public SM50(String customer, String SID, String lang)
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

		String textProcessOverview = "images/" + this.lang + "/sm50/textProcessOverview.png";
		String textTime = "images/" + this.lang + "/sm50/textTime.png";
		String iconDesc = "images/" + this.lang + "/sm50/iconDesc.png";
		String iconDown = "images/" + this.lang + "/sm50/iconDown.png";
		String iconSelect = "images/" + this.lang + "/sm50/iconSelect.png";
		
		try
		{
			myScreen.wait(txtTCODE);
			myScreen.click(txtTCODE);
			org.sikuli.basics.Settings.TypeDelay = 0.05;
			myScreen.type(txtTCODE, "/nsm50\r");

			myScreen.wait(iconSelect, this.timeout + 10);
			if (myScreen.exists(iconSelect) != null)
			{
				if (myScreen.exists(textTime) != null)
				{
					myScreen.click(textTime);
					myScreen.click(iconDesc);

					myScreen.wait(iconDown);
					myScreen.mouseMove(txtTCODE);
				}
				
				logger.info("--> Capturing image sm50");
				String imgPath = screenShot.captureImage(myScreen, this.customer, this.SID, "sm50");
				if (imgPath != null)
				{
					this.myImages.add(imgPath);
				}
				logger.info("--> Done capture image sm50!");				
			}

			return true;
		}
		catch (FindFailed e) {
			logger.fatal("--> Error checking tcode sm50 :" + e.getMessage()); 
		}
		
		return false;
	}


	public String Title()
	{
		return "SM50: Process Overview";
	}


	public List<String> getScreenShot()
	{
		return this.myImages;
	}
}
