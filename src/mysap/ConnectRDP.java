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

import mysap.vpn.RDP;

public class ConnectRDP
{
	private static RDP rdp = null;

	public static Boolean ProcessConnect(String customer, String os)
	{
		Boolean isSuccess = false;

		rdp = new RDP(customer, os);
		rdp.setTimeout(20);

		if (rdp.Connect().booleanValue())
		{
			isSuccess = true;
		}
		return isSuccess;
	}


	public static Boolean ProcessDisconnect()
	{
		return rdp.Disconnect();
	}
}

