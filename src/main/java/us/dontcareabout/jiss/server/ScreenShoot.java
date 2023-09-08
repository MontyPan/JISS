package us.dontcareabout.jiss.server;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

//從 KingsGame 那邊暫時搬過來的
//目前還沒有打算重整一下 Robot / Salve，所以只先弄螢幕擷圖的部份
public class ScreenShoot {
	/**
	 * 正常高畫質的影片擷圖。
	 * <p>
	 * 擷取環境：Windows 工具列在右邊、寬度 89px，Chrome 佔右半螢幕。
	 */
	public static void netflixHD(File file) {
		netflix(new Rect(920, 350, 900, 490), file);
	}

	/**
	 * 古老畫質的影片擷圖。
	 * <p>
	 * 擷取環境：Windows 工具列在右邊、寬度 89px，Chrome 佔右半螢幕。
	 */
	public static void netflixSD(File file) {
		netflix(new Rect(920, 260, 900, 680), file);
	}

	private static void netflix(Rect rect, File file) {
		try {
			ImageIO.write(
				screenShot(rect),
				"JPG",
				file
			);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private static BufferedImage screenShot(Rect area) throws Exception {
		Robot robot = new Robot();
		Rectangle screenRect = new Rectangle(area.location.x, area.location.y, area.size.x, area.size.y);
		return robot.createScreenCapture(screenRect);
	}

}

//Refactory 以後再想要丟哪裡... [逃]
class Rect {
	public final XY location;
	public final XY size;

	public Rect(int x, int y, int w, int h) {
		this(new XY(x, y), new XY(w, h));
	}

	public Rect(XY location, XY size) {
		this.location = location;
		this.size = size;
	}
}

class XY {
	public final int x;
	public final int y;

	public XY(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
