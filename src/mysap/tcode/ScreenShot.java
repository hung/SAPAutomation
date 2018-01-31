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

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;
import org.apache.logging.log4j.Logger;
import org.sikuli.script.ImagePath;
import org.sikuli.script.Match;
import org.sikuli.script.Screen;
import org.sikuli.script.ScreenImage;



public class ScreenShot
{
	public String captureImage(Screen myScreen, String customer, String SID, String tcode)
	{
		try
		{
			String topLeft = "images/other/topLeft.png";
			String bottomRight = "images/other/bottomRight.png";

			int x = myScreen.find(topLeft).x;
			int y = myScreen.find(topLeft).y;
			int width = myScreen.find(bottomRight).x + 30 - x;
			int height = myScreen.find(bottomRight).y + 30 - y;

			String today = new SimpleDateFormat("ddMMyyyy").format(new Date());

			String path = String.format("%s/%s/%s/%s/%s/", new Object[] {

					ImagePath.getBundlePath(), "reports", customer, SID, today });

			File dirs = new File(path);
			if (!dirs.exists())
			{
				dirs.mkdirs();
			}


			ScreenImage sm13 = myScreen.capture(x, y, width, height);
			File f = new File(path + tcode + ".png");
			ImageIO.write(sm13.getImage(), "png", f);
			CheckTcode.logger.info("---> Image Saved for tcode " + tcode);
			if (f.exists())
			{
				return tcode + ".png";
			}

			return null;

		}
		catch (Exception ex)
		{
			CheckTcode.logger.fatal("---> Cannot capture image tcode " + tcode);
			CheckTcode.logger.fatal("---> Exception: " + ex.getMessage()); }
		return null;
	}
}

