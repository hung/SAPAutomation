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

import mysap.report.GenerateReport;


public class Report
{
	public static Boolean ProcessReport(String customer, String SID)
	{
		GenerateReport re = new GenerateReport(customer, SID);
		re.MakeReport();
		return true;
	}
}
