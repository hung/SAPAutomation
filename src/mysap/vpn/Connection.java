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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract interface Connection
{
	public static final Logger logger = LogManager.getLogger(Connection.class.getName());

	public static final Ping ping = new Ping();
	public static final Command cmd = new Command();

	public abstract Boolean Connect();

	public abstract Boolean Disconnect();

	public abstract void setTimeout(int paramInt);

	public abstract void setOS(String paramString);

	public abstract String toString();
}
