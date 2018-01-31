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

public class DB02OLD implements CheckTcode
{
	private final String customer;
	private final String SID;
	private final List<String> myImages;
	private String lang;
	private int timeout = 10;

	public DB02OLD(String customer, String SID, String lang)
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

		String textTablesIndexes = "images/" + this.lang + "/db02old/textTablesIndexes.png";
		String textTablespaces = "images/" + this.lang + "/db02old/textTablespaces.png";

		String btMissingIndexes = "images/" + this.lang + "/db02old/btMissingIndexes.png";
		String btMissingIndexes2 = "images/" + this.lang + "/db02old/btMissingIndexes2.png";
		String btCurrentSizes = "images/" + this.lang + "/db02old/btCurrentSizes.png";

		String testIcon = "images/" + this.lang + "/db02old/testIcon.png";

		try
		{
			myScreen.wait(txtTCODE);
			myScreen.click(txtTCODE);
			org.sikuli.basics.Settings.TypeDelay = 0.05;
			myScreen.type(txtTCODE, "/ndb02old\r");

			myScreen.wait(btCurrentSizes, this.timeout + 10);
			if (myScreen.exists(btCurrentSizes) != null)
			{

				myScreen.click(btCurrentSizes);

				myScreen.wait(textTablespaces, this.timeout + 10);


				logger.info("--> Capturing image db02old Tablespaces");
				String imgPath = screenShot.captureImage(myScreen, this.customer, this.SID, "db02old_tablespaces");
				if (imgPath != null)
				{
					this.myImages.add(imgPath);
				}
				logger.info("--> Done capture image db02old Tablespaces!");

				myScreen.type(Key.F3);
			}

			myScreen.wait(btCurrentSizes, this.timeout + 10);
			if (myScreen.exists(btCurrentSizes) != null)
			{
				if (myScreen.exists(btMissingIndexes) != null) {
					myScreen.click(btMissingIndexes);
				} else if (myScreen.exists(btMissingIndexes2) != null) {
					myScreen.click(btMissingIndexes2);
				}
				myScreen.wait(testIcon);

				logger.info("--> Capturing image db02old Missing tables and indexes");
				String imgPath = screenShot.captureImage(myScreen, this.customer, this.SID, "db02_indexes");
				if (imgPath != null)
				{
					this.myImages.add(imgPath);
				}
				logger.info("--> Done capture image db02old Missing tables and indexes!");

			}

			return true;
		}
		catch (Exception e) {
			logger.fatal("--> Error checking tcode db02old :" + e.getMessage()); 
		}
		return false;
	}



	public String Title()
	{
		return "DB02OLD: Tables and Indexes";
	}


	public List<String> getScreenShot()
	{
		return this.myImages;
	}
}
