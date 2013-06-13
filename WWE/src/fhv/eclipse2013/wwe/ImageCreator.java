package fhv.eclipse2013.wwe;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.eclipse.swt.graphics.Rectangle;

import fhv.eclipse2013.wwe.contract.IField;
import fhv.eclipse2013.wwe.contract.scope.ISimulationScope;
import fhv.eclipse2013.wwe.contract.state.FieldState;

public class ImageCreator {
	private static final int width = 50;
	private static final int height = 50;

	public static void createImage(String folder, String filename,
			ISimulationScope scope, boolean max) throws IOException {
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = image.createGraphics();

		Rectangle maxSize = scope.getRect();
		if (max) {
			maxSize = scope.getMinRectangle();
		}

		int img_w = (int) (width / (maxSize.width - maxSize.x));
		int img_h = (int) (height / (maxSize.height - maxSize.y));

		createImage(g, img_w, img_h, maxSize, scope);

		File outputfile = new File(folder + "\\" + filename);
		ImageIO.write(image, "png", outputfile);
	}

	public static void createImage(String filename, ISimulationScope scope,
			int blockWidth) throws IOException {
		Rectangle maxSize = scope.getMinRectangle();

		int width = blockWidth * (maxSize.width - maxSize.x);
		int height = blockWidth * (maxSize.height - maxSize.y);

		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = image.createGraphics();

		createImage(g, blockWidth, blockWidth, maxSize, scope);

		File outputfile = new File(filename);
		ImageIO.write(image, "png", outputfile);
	}

	private static void createImage(Graphics2D g, int img_w, int img_h,
			Rectangle maxSize, ISimulationScope scope) {
		for (int x = maxSize.x; x < maxSize.width; x++) {
			for (int y = maxSize.y; y < maxSize.height; y++) {
				FieldState state = FieldState.none;
				if (scope.fieldExists(x, y)) {
					IField field = scope.getField(x, y);
					state = field.getState();
				}
				int dx = (x - maxSize.x) * img_w;
				int dy = (y - maxSize.y) * img_h;
				g.setColor(getColor(state));
				g.fillRect(dx, dy, img_w, img_h);
				g.setColor(Color.WHITE);
				g.drawRect(dx, dy, img_w, img_h);
			}
		}
	}

	private static Color getColor(FieldState state) {
		if (state.equals(FieldState.none)) {
			return Color.BLACK;
		} else if (state.equals(FieldState.conductor)) {
			return Color.YELLOW;
		} else if (state.equals(FieldState.head)) {
			return Color.BLUE;
		} else if (state.equals(FieldState.tail)) {
			return Color.RED;
		}
		return Color.BLACK;
	}
}
