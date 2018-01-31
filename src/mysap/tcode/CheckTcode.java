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

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public abstract interface CheckTcode
{
	public static final Logger logger = LogManager.getLogger(CheckTcode.class.getName());

	public static final ScreenShot screenShot = new ScreenShot();

	public abstract void setTimeout(int paramInt);

	public abstract Boolean Check();

	public abstract String Title();

	public abstract List<String> getScreenShot();
}
