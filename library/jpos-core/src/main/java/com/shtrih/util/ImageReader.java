package com.shtrih.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

public class ImageReader {

	private final Bitmap bitmap;
	private final byte[][] data;

	public ImageReader(String fileName) throws Exception {
		bitmap = BitmapFactory.decodeFile(fileName);
		data = bitmapToArray(bitmap);
	}

	public int getWidth() {
		return bitmap.getWidth();
	}

	public int getHeight() {
		return bitmap.getHeight();
	}

	public byte[][] getData() {
		return data;
	}

	public byte[][] bitmapToArray(Bitmap bitmap) throws Exception {
		int height = bitmap.getHeight();
		int width = bitmap.getWidth();
		byte[][] bitmapData = new byte[height][width];

		int pixel;
		int B = 0, G = 0, R = 0;
		int k = 0;
		for (int x = 0; x < height; x++) {
			for (int y = 0; y < width; y++, k++) {
				// get one pixel color
				pixel = bitmap.getPixel(y, x);
				// retrieve color of all channels
				R = Color.red(pixel);
				G = Color.green(pixel);
				B = Color.blue(pixel);
				// take conversion up to one single value by calculating
				// pixel intensity.
				R = G = B = (int) (0.299 * R + 0.587 * G + 0.114 * B);
				// set new pixel color to output bitmap
				if (R < 128) {
					bitmapData[x][y] = 0;
				} else {
					bitmapData[x][y] = 1;
				}
			}
		}
		return bitmapData;
	}
}
