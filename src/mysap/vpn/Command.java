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

public class Command
{
	public String run(String cmd, String args)
	{
		String s = "";
		try
		{
			Process p = Runtime.getRuntime().exec("cmd /c \"" + cmd + "\" " + args);

			BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(p.getInputStream()));

		}
		catch (Exception e)
		{

			e.printStackTrace();
		}

		return s;
	}
}

