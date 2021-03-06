/*******************************************************************************
 * Copyright (C) 2018 kienhung
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package mysap.vpn;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Ping
{
	public Boolean checkPing(String ipaddr)
	{
		Boolean isReachable = false;
		try
		{
			Process p = Runtime.getRuntime().exec("ping " + ipaddr);
			BufferedReader inputStream = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String s = "";

			while ((s = inputStream.readLine()) != null)
			{
				if (s.indexOf("Average") != -1)
				{
					isReachable = true;
				}
			}

			p.waitFor();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return isReachable;
	}
}
