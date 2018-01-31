/*******************************************************************************
 * Copyright (C) 2018 kienhung
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package mysap;

import java.util.Map;
import mysap.tcode.CheckTcode;


public class Check
{
	public static Map<String, java.util.List> reportData;

	public static void newReport()
	{
		reportData = new java.util.LinkedHashMap();
	}

	public static Boolean ProcessCheck(String customer, String SID, String lang, String tcode, String fromDay, String toDay, int timeout)
	{
		Boolean isSuccess = false;
		CheckTcode myTCODE = null;
		switch (tcode.toLowerCase())
		{
		case "db02": 
			myTCODE = new mysap.tcode.DB02(customer, SID, lang);
			break;
		case "db02old": 
			myTCODE = new mysap.tcode.DB02OLD(customer, SID, lang);
			break;
		case "db12": 
			myTCODE = new mysap.tcode.DB12(customer, SID, lang);
			break;
		case "db13": 
			myTCODE = new mysap.tcode.DB13(customer, SID, lang);
			break;
		case "db16": 
			myTCODE = new mysap.tcode.DB16(customer, SID, lang);
			break;
		case "sm12": 
			myTCODE = new mysap.tcode.SM12(customer, SID, lang);
			break;
		case "sm13": 
			myTCODE = new mysap.tcode.SM13(customer, SID, lang, fromDay, toDay);
			break;
		case "sm21": 
			myTCODE = new mysap.tcode.SM21(customer, SID, lang, fromDay, toDay);
			break;
		case "sm37": 
			myTCODE = new mysap.tcode.SM37(customer, SID, lang, fromDay, toDay);
			break;
		case "sm37n": 
			myTCODE = new mysap.tcode.SM37N(customer, SID, lang, fromDay, toDay);
			break;			
		case "sm50": 
			myTCODE = new mysap.tcode.SM50(customer, SID, lang);
			break;
		case "sm51": 
			myTCODE = new mysap.tcode.SM51(customer, SID, lang);
			break;
		case "sm58": 
			myTCODE = new mysap.tcode.SM58(customer, SID, lang, fromDay, toDay);
			break;
		case "smq1": 
			myTCODE = new mysap.tcode.SMQ1(customer, SID, lang);
			break;
		case "smq2": 
			myTCODE = new mysap.tcode.SMQ2(customer, SID, lang);
			break;
		case "sost": 
			myTCODE = new mysap.tcode.SOST(customer, SID, lang, fromDay, toDay);
			break;
		case "sp01": 
			myTCODE = new mysap.tcode.SP01(customer, SID, lang, fromDay, toDay);
			break;
		case "st02": 
			myTCODE = new mysap.tcode.ST02(customer, SID, lang);
			break;
		case "st03": 
			myTCODE = new mysap.tcode.ST03(customer, SID, lang);
			break;
		case "st03n": 
			myTCODE = new mysap.tcode.ST03N(customer, SID, lang);
			break;
		case "st04": 
			myTCODE = new mysap.tcode.ST04(customer, SID, lang);
			break;
		case "st22": 
			myTCODE = new mysap.tcode.ST22(customer, SID, lang, fromDay, toDay);
			break;
		case "sm35": 
			myTCODE = new mysap.tcode.SM35(customer, SID, lang);
			break;
		case "sm28": 
			myTCODE = new mysap.tcode.SM28(customer, SID, lang);
			break;
		case "st06": 
			myTCODE = new mysap.tcode.ST06(customer, SID, lang);
			break;
		default: 
			isSuccess = false;
		}


		if (myTCODE != null)
		{
			myTCODE.setTimeout(timeout);
			if (myTCODE.Check().booleanValue())
			{
				isSuccess = true;
			}
			else
				isSuccess = false;
			reportData.put(myTCODE.Title(), myTCODE.getScreenShot());
		}


		return isSuccess;
	}
}
