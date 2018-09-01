package com.balyshyn.app.utils.asciiart;

import javax.inject.Singleton;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.StringJoiner;

@Singleton
public class ASCIIArtImpl implements ASCIIArt {

	@Override
	public String printTextLogo(String textLogo, int width, int height) {

		StringJoiner stringJoiner = new StringJoiner("\n");

		BufferedImage image = prepareCanvas(textLogo, width, height);
		fillString(width, height, stringJoiner, image);
		return stringJoiner.toString();
	}

	private void fillString(int width, int height, StringJoiner stringJoiner, BufferedImage image) {
		for (int y = 0; y < height; y++) {
			StringBuilder sb = new StringBuilder();

			for (int x = 0; x < width; x++) {

				if (x == 0 || x == width - 1 || y == 0 || y == height - 1) {
					sb.append("#");
				} else {
					sb.append(image.getRGB(x, y) == -16777216 ? " " : "x");
				}
			}
			if (sb.toString().trim().isEmpty()) {
				continue;
			}

			stringJoiner.add(sb.toString());

		}
	}

	private BufferedImage prepareCanvas(String text, int width, int height) {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		g.setFont(new Font("SansSerif", Font.PLAIN, 16));

		Graphics2D graphics = (Graphics2D) g;
		graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
						RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		graphics.drawString(text, (int) (width * 0.05), (int) (height * 0.915));
		return image;
	}
}
