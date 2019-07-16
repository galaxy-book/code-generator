package org.nico.byteanimation.frame.tests;

import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.Field;

import javax.imageio.ImageIO;

import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.ffmpeg.global.avutil;
import org.bytedeco.javacv.FFmpegFrameFilter;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.nico.codegenerator.utils.RenderUtils;


public class FrameTest2 {


	private static Java2DFrameConverter converter = new Java2DFrameConverter();

	private String fileName;
	private FFmpegFrameRecorder recorder;
	private static final double FRAME_RATE = 25.0;
	private static final double MOTION_FACTOR = 1;

	public static void fetchFrame(String url)
			throws Exception {
		FFmpegFrameGrabber ff = new FFmpegFrameGrabber(url); 
		
		
		try {
			ff.start();
			
			File file = new File("C:\\Users\\Administrator\\Desktop\\demo1.mp4");
			
			
			FFmpegFrameFilter filter = new FFmpegFrameFilter("atempo=0.5", ff.getAudioChannels());
			filter.start();
			
			System.out.println(ff.getImageWidth() + "-" + ff.getImageHeight());
			FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(file, ff.getImageWidth(), ff.getImageHeight(), ff.getAudioChannels());
			Field field = recorder.getClass().getDeclaredField("filename");
			field.setAccessible(true);
			field.set(recorder, "test.mp4");
			
			recorder.setSampleRate(ff.getSampleRate());
//            recorder.setSampleFormat(ff.getSampleFormat());
			
			recorder.setVideoBitrate(ff.getVideoBitrate());
			recorder.setVideoCodec(ff.getVideoCodec());
			recorder.setVideoCodecName(ff.getVideoCodecName());
			recorder.setVideoMetadata(ff.getVideoMetadata());
			recorder.setVideoOptions(ff.getVideoOptions());
			
//			recorder.setAudioOption("atempo", "2.0");
			recorder.setAspectRatio(ff.getAspectRatio());
			recorder.setAudioBitrate(ff.getAudioBitrate());
			recorder.setAudioChannels(ff.getAudioChannels());
			recorder.setAudioQuality(0);
			recorder.setAudioCodec(ff.getAudioCodec());
			recorder.setAudioCodecName(ff.getAudioCodecName());
			
			recorder.setMaxDelay(ff.getMaxDelay());
			
//			recorder.setPixelFormat(avutil.AV_PIX_FMT_YUV420P);
			recorder.setFrameRate(ff.getFrameRate());
			
			recorder.start();
			
//			int index = 0;
//			Frame f = null;
//			while (ff.grab() != null) {
//				f = ff.grab();
//				if(f != null && f.image != null) {
//					long t = f.timestamp;
//					if (t > recorder.getTimestamp()) {
//						recorder.setTimestamp(t);
//					}
//					recorder.record(f);
//				}
//				try {
//					if(f != null && f.samples != null) {
//						recorder.recordSamples(f.sampleRate, f.audioChannels, f.samples);
//					}
//				}catch(Exception e) {}
//				System.out.println("video: " + index ++);
//			}
			
			int index = 0;
			Frame f = null;
			while (ff.grab() != null) {
				f = ff.grab();
				if(f != null && f.image != null) {
					long t = f.timestamp;
					if (t > recorder.getTimestamp()) {
						recorder.setTimestamp(t);
					}
					String str = RenderUtils.renderChar(converter.convert(f));
					BufferedImage image = RenderUtils.renderImage(str, ff.getImageWidth(), ff.getImageHeight());
					recorder.record(converter.convert(image));
					System.out.println(str);
				}
				try {
					if(f != null) {
					    filter.push(f);
		                f = filter.pull();
					    recorder.recordSamples(f.samples);
					}
				}catch(Exception e) {}
//				System.out.println("video: " + index ++);
			}
			recorder.stop();
			recorder.release();
			recorder.close();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			ff.stop();
			ff.close();
		}
	}
	
	public static void filter() {
	}

	public static void record() throws Exception {
		BufferedImage image = ImageIO.read(new File("C:\\Users\\ainil\\Desktop\\test.jpg"));

		int width = image.getWidth();
		int height = image.getHeight();

		FFmpegFrameRecorder recorder = new FFmpegFrameRecorder("C:\\Users\\ainil\\Desktop\\90081130-1-7.mp4", width, height);
		recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);

		recorder.setFrameRate(FRAME_RATE);
		recorder.setVideoBitrate((int)((width*height*FRAME_RATE)*MOTION_FACTOR*0.07));

		recorder.setPixelFormat(avutil.AV_PIX_FMT_YUV420P);
		recorder.setFormat("mp4");

		recorder.start();

		recorder.record(converter.convert(image));

		recorder.stop();
		recorder.close();
	}

	public static void main(String[] args) throws Exception {
		fetchFrame("C:\\Users\\Administrator\\Desktop\\demo.mp4");
		//		record();
	}

}
