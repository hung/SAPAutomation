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

import mysap.vpn.*;

public class ConnectVPN
{
	private static Connection myConnection = null;

	public static Boolean ProcessConnect(String customer, String os)
	{
		Boolean isSuccess = false;

		switch (customer.toLowerCase())
		{
		case "abc": 
			//myConnection = new ABC();
			myConnection.setTimeout(20);
			break;
		default: 
			isSuccess = false;
		}


		if (myConnection != null)
		{
			if (myConnection.Connect().booleanValue())
			{
				isSuccess = true;
			}
		}

		return isSuccess;
	}


	public static Boolean ProcessDisconnect()
	{
		return myConnection.Disconnect();
	}
}
