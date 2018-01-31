/*******************************************************************************
 * Copyright (C) 2018 kienhung
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package sapconsole;

import mysap.Check;
import mysap.Report;
import org.apache.logging.log4j.Logger;


public class CheckThread implements Runnable
{
	String[] tcodeArray;
	String fromDay;
	String toDay;
	String SID;
	String lang;
	String customerName;
	Boolean isAutoLogout;
	int delay;
	int timeout;

	public CheckThread(String customerName, String SID, String lang, String[] tcodeArray, String fromDay, String toDay, int delay, int timeout)
	{
		this.tcodeArray = tcodeArray;
		this.fromDay = fromDay;
		this.toDay = toDay;
		this.customerName = customerName;
		this.SID = SID;
		this.lang = lang;
		this.delay = delay;
		this.timeout = timeout;
	}

	public void run() {
		
		Check.newReport();
		
		for (String tcode : this.tcodeArray) {
			try
			{
				SAPConsole.logger.info("Checking tcode " + tcode);


				if (Check.ProcessCheck(this.customerName, this.SID, this.lang, tcode, this.fromDay, this.toDay, this.timeout).booleanValue()) {
					SAPConsole.logger.info("--> Done checking tcode " + tcode);
				} else {
					SAPConsole.logger.fatal("--> Failed checking tcode " + tcode);
					SAPConsole.logger.fatal("--> tcode " + tcode + " maybe not implement yet!");
				}

				Thread.sleep(this.delay * 1000);
			}
			catch (InterruptedException ex) {
				SAPConsole.logger.fatal("--> InterruptedException: " + ex.getMessage());
			}
		}


		SAPConsole.logger.info("Creating report....");

		Report.ProcessReport(this.customerName, this.SID);
		SAPConsole.logger.info("--> Done !!!");
	}
}
