package util;

import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

public class ImageConvert {

	public static BufferedImage readImage(String filename) {

		BufferedImage img;

		try {
			File file = new File(filename);
			img = ImageIO.read(file);
		} catch (IOException e) {
				img = new BufferedImage(0, 0, 0);
		}
		return img;

	}

	public static BufferedImage readImage(Part part) {

		BufferedImage img;

		try {
			InputStream is = part.getInputStream();
			img = ImageIO.read(is);
		} catch (IOException e) {
				img = new BufferedImage(0, 0, 0);
		}
		return img;

	}

	public static byte[] imageToByte(BufferedImage img) throws IOException {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		BufferedOutputStream bos = new BufferedOutputStream(baos);
		img.flush();
		ImageIO.write(img, "png", bos);
		return baos.toByteArray();

	}

	public static byte[] imageToByte(BufferedImage img, String format) throws IOException {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		BufferedOutputStream bos = new BufferedOutputStream(baos);
		img.flush();
		ImageIO.write(img, format, bos);
		return baos.toByteArray();

	}

	public static BufferedImage byteToImage(byte[] bytes) throws IOException {

		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		BufferedImage img = ImageIO.read(bais);
		return img;

	}

	public static void writeImage(HttpServletResponse response, BufferedImage img) {

		try {
			response.setContentType("image/png");
			OutputStream outputStream = response.getOutputStream();
			ImageIO.write(img, "png", outputStream);
			outputStream.flush();
		}catch(IOException e) {
			System.out.println(e);
		}
	}

	public static void writeImage(HttpServletResponse response, BufferedImage img, String format) {

		try {
			response.setContentType("image/" + format);
			OutputStream outputStream = response.getOutputStream();
			ImageIO.write(img, format, outputStream);
			outputStream.flush();
		}catch(IOException e) {
			System.out.println(e);
		}
	}

	// アウトプットソースのBase64形式を返還
	public static String writeImage(BufferedImage img, HttpServletRequest request ,HttpServletResponse response) {

		try {

			ByteArrayOutputStream output = new ByteArrayOutputStream();
			ImageIO.write(img, "png", output);
			String imageAsBase64 = Base64.getEncoder().encodeToString(output.toByteArray());
			return imageAsBase64;

		}catch(IOException e) {
			System.out.println(e);
		}catch(Exception e) {
			System.out.println(e);
		}
		return null;
	}

	public static byte[] getByteFromResult(ResultSet rs, String column) {

		InputStream is;

		try {

			is = rs.getBinaryStream(column);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] bs = new byte[1024];

			int size = 0;
			while((size = is.read(bs)) != -1 ){
				baos.write(bs, 0, size);
			}

			return baos.toByteArray();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static void changeRGB(BufferedImage img) {

		int w = img.getWidth();
		int h = img.getHeight();

		int r = (int)(Math.random() * 255);
		int g = (int)(Math.random() * 255);
		int b = (int)(Math.random() * 255);
		int rgb =  0xff000000 | r << 16 | g << 8 | b;

		for(int y = 0; y < h; y++){
			for(int x = 0; x < w; x++){

				if(img.getRGB(x, y) != 0xffffffff) {
					img.setRGB(x, y, rgb);
				}

			}
		}

	}


}
