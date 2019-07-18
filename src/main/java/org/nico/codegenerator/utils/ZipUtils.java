package org.nico.codegenerator.utils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtils {

	public static byte[] compress(ZipEntity ze) throws IOException {
		byte[] b = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ZipOutputStream zip = new ZipOutputStream(bos);
		addZipEntity(ze, zip);
		zip.close();
		b = bos.toByteArray();
		return b;
	}
	
	public static byte[] compress(List<ZipEntity> zes) throws IOException {
		byte[] b = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ZipOutputStream zip = new ZipOutputStream(bos);
		for(ZipEntity ze: zes) {
			addZipEntity(ze, zip);
		}
		zip.close();
		b = bos.toByteArray();
		return b;
	}

	private static void addZipEntity(ZipEntity ze, ZipOutputStream zip) throws IOException {
		ZipEntry entry = new ZipEntry(ze.getFileName());
		entry.setSize(ze.getData().length);
		zip.putNextEntry(entry);
		zip.write(ze.getData());
		zip.closeEntry();
	}

	public static class ZipEntity{

		private byte[] data;

		private String fileName;

		public byte[] getData() {
			return data;
		}

		public void setData(byte[] data) {
			this.data = data;
		}

		public String getFileName() {
			return fileName;
		}

		public void setFileName(String fileName) {
			this.fileName = fileName;
		}

		public ZipEntity(byte[] data, String fileName) {
			super();
			this.data = data;
			this.fileName = fileName;
		}

	}
}