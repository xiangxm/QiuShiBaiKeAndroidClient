package com.qiubaiclient.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.graphics.Bitmap;

/**
 * 对文件操作的工具类。
 * 
 * @author
 * 
 */
public class FileUtil {

	/**
	 * 获取文件名称
	 * 
	 * @param str
	 * @return
	 */
	public static String getFileName(String str) {
		// 去除url中的符号作为文件名返回
		str = str.replaceAll("(?i)[^a-zA-Z0-9\u4E00-\u9FA5]", "");
		System.out.println("filename = " + str);
		return str + ".png";
	}

	// /**
	// * 将文件保存至sd卡
	// *
	// * @param fileName
	// * @param inputStream
	// */
	// public static void writeSDcard(String fileName, InputStream inputStream)
	// {
	// try {
	// File file = new File(Constants.DOWNLOAD_PATH);
	// if (!file.exists()) {
	// file.mkdirs();
	// }
	//
	// FileOutputStream fileOutputStream = new FileOutputStream(
	// Constants.DOWNLOAD_PATH + fileName);
	// byte[] buffer = new byte[512];
	// int count = 0;
	// while ((count = inputStream.read(buffer)) > 0) {
	// fileOutputStream.write(buffer, 0, count);
	// }
	// fileOutputStream.flush();
	// fileOutputStream.close();
	// inputStream.close();
	// System.out.println("writeToSD success");
	// } catch (IOException e) {
	// e.printStackTrace();
	// System.out.println("writeToSD fail");
	// }
	// }

	/**
	 * 将文件保存到sd卡
	 * 
	 * @param fileName
	 *            文件完整路径
	 * @param inputStream
	 * @return
	 */
	// public static boolean writeFile2SDcard(String fileName,
	// InputStream inputStream) {
	// try {
	// if (null == inputStream) {
	//
	// return false;
	// }
	//
	// File file = new File(Constants.DOWNLOAD_PATH);
	// if (!file.exists()) {
	// file.mkdirs();
	// }
	//
	// FileOutputStream fileOutputStream = new FileOutputStream(fileName);
	// byte[] buffer = new byte[1024];
	// int count = 0;
	// while ((count = inputStream.read(buffer)) > 0) {
	// fileOutputStream.write(buffer, 0, count);
	// }
	// fileOutputStream.flush();
	// fileOutputStream.close();
	// inputStream.close();
	// System.out.println("writeToSD success");
	// return true;
	// } catch (IOException e) {
	// e.printStackTrace();
	// System.out.println("writeToSD fail");
	// return false;
	// }
	// }

	/**
	 * 保存图片到sd卡
	 * 
	 * @param fileName
	 *            文件名称
	 * @param bmp
	 *            对应的图片Bitmap
	 * @return
	 */
	public static boolean writeBitmap2SDcard(String fileName, Bitmap bmp,
			String path) {
		try {
			File file = new File(path);
			if (!file.exists()) {
				// 判断是否存在下载目录
				file.mkdirs();
			}
			InputStream is = bitmap2InputStream(bmp);

			File imgFile = new File(path + getFileName(fileName));
			if (!imgFile.exists()) {

				imgFile.createNewFile();
			}

			FileOutputStream fileOutputStream = new FileOutputStream(imgFile);
			byte[] buffer = new byte[512];
			int count = 0;
			while ((count = is.read(buffer)) > 0) {
				fileOutputStream.write(buffer, 0, count);
			}
			fileOutputStream.flush();
			fileOutputStream.close();
			is.close();
			System.out.println("writeToSD success");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("writeToSD fail");
			return false;
		}
		return true;
	}

	/**
	 * // Bitmap转换成byte[]
	 * 
	 * @param bm
	 *            目标Bitmap
	 * @return
	 */
	public static byte[] bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

	/**
	 * // 将Bitmap转换成InputStream
	 * 
	 * @param bm
	 * @return
	 */
	public static InputStream bitmap2InputStream(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		InputStream is = new ByteArrayInputStream(baos.toByteArray());
		return is;
	}

	/**
	 * 将输入流转换成字节数组
	 * 
	 * @param is
	 *            输入流
	 * @return
	 */
	public static byte[] changeIs2ByteArr(InputStream is) {
		try {

			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			int len = -1;
			byte b[] = new byte[2048];
			while ((len = is.read(b)) != -1) {

				byteArrayOutputStream.write(b, 0, len);

			}
			return byteArrayOutputStream.toByteArray();
		} catch (IOException e) {

			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 查看文件是否存在
	 * 
	 * @param path
	 *            文件路径
	 * @return
	 */
	public static boolean isFileExist(String path) {

		File file = new File(path);

		if (file.exists()) {

			return true;
		}
		return false;
	}

	/**
	 * 格式化文件大
	 * 
	 * @param volume
	 *            文件大小
	 * @return 格式化的字符
	 */
	public static String getVolume(long volume) {

		float num = 1.0F;

		String str = null;

		if (volume < 1024) {
			str = volume + "B";
		} else if (volume < 1048576) {
			num = num * volume / 1024;
			str = String.format("%.1f", num) + "K";
		} else if (volume < 1073741824) {
			num = num * volume / 1048576;
			str = String.format("%.1f", num) + "M";
		} else if (volume < 1099511627776L) {
			num = num * volume / 1073741824;
			str = String.format("%.1f", num) + "G";
		}

		return str;
	}
}
