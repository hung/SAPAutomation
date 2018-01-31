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
import org.sikuli.script.Screen;

public class DB02 implements CheckTcode
{
	private final String customer;
	private final String SID;	
	private final List<String> myImages;
	private int timeout = 10;
	private String lang;

	public DB02(String customer, String SID, String lang)
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
		String textSpaceOverview = "images/" + this.lang + "/db02/textSpaceOverview.png";
		String textTablespaces = "images/" + this.lang + "/db02/textTablespaces.png";
		String textOverview = "images/" + this.lang + "/db02/textOverview.png";
		String textOverview2 = "images/" + this.lang + "/db02/textOverview2.png";
		String textDetailed = "images/" + this.lang + "/db02/textDetailed.png";

		String iconTestTablespaces = "images/" + this.lang + "/db02/iconTestTablespaces.png";

		String textDiagnostics = "images/" + this.lang + "/db02/textDiagnostics.png";
		String textMissing = "images/" + this.lang + "/db02/textMissing.png";
		String textMissing2 = "images/" + this.lang + "/db02/textMissing2.png";
		String textTestMissing = "images/" + this.lang + "/db02/textTestMissing.png";
		try
		{
			myScreen.wait(txtTCODE);
			myScreen.click(txtTCODE);
			org.sikuli.basics.Settings.TypeDelay = 0.05;
			myScreen.type(txtTCODE, "/ndb02\r");

			myScreen.wait(textSpaceOverview, this.timeout + 10);
			if (myScreen.exists(textDiagnostics) != null)
			{

				myScreen.doubleClick(textDiagnostics);

				if (myScreen.exists(textMissing) != null)
				{
					myScreen.doubleClick(textMissing);
				}
				else if (myScreen.exists(textMissing2) != null)
				{
					myScreen.doubleClick(textMissing2);
				}


				if (myScreen.exists(textTestMissing, this.timeout + 10) != null)
				{
					logger.info("--> Capturing image db02 Missing tables and indexes");
					String imgPath = screenShot.captureImage(myScreen, this.customer, this.SID, "db02_missing");
					if (imgPath != null)
					{
						this.myImages.add(imgPath);
					}
					logger.info("--> Done capture image db02 Missing tables and indexes!");
				}
			}


			myScreen.wait(textTestMissing, this.timeout + 10);
			if (myScreen.exists(textTablespaces) != null)
			{
				myScreen.doubleClick(textTablespaces);

				myScreen.mouseMove(textTablespaces);

				if (myScreen.exists(textOverview) != null)
				{
					myScreen.doubleClick(textOverview);
				}
				else if (myScreen.exists(textOverview2) != null)
				{
					myScreen.doubleClick(textOverview2);
				}


				if (myScreen.exists(iconTestTablespaces, 20) != null)
				{

					logger.info("--> Capturing image db02 Tablespaces");
					String imgPath = screenShot.captureImage(myScreen, this.customer, this.SID, "db02_tablespace");
					if (imgPath != null)
					{
						this.myImages.add(imgPath);
					}
					logger.info("--> Done capture image db02 Tablespaces!");
				}
			}


			return true;
		}
		catch (Exception e) {
			logger.fatal("--> Error checking tcode db02 :" + e.getMessage()); 
		}
		return false;
	}



	public String Title()
	{
		return "DB02: Tables and Indexes";
	}

	public List<String> getScreenShot()
	{
		return this.myImages;
	}
}

