package org.nico.byteanimation.frame.tests;

import java.io.File;

import org.bytedeco.javacv.FFmpegFrameFilter;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameFilter;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.FrameRecorder;

public class FilterTest {

    private FFmpegFrameGrabber VIDEO_GRABBER;
    private FFmpegFrameRecorder videoRecorder;
    private FFmpegFrameFilter filter;

    public FilterTest() {
        VIDEO_GRABBER = new FFmpegFrameGrabber("C:\\Users\\Administrator\\Desktop\\demo.mp4");
    }


    protected Object doInBackground() {
        Frame tempVideoFrame;
        Frame tempAudioFrame;
        try {
            VIDEO_GRABBER.start();
            initVideoRecorder();
            filter.start();
            while (VIDEO_GRABBER.grab() != null) {
                tempVideoFrame = VIDEO_GRABBER.grabImage();
                if (tempVideoFrame != null) {
                    filter.push(tempVideoFrame);
                    tempVideoFrame = filter.pull();
                    videoRecorder.record(tempVideoFrame);
                }
//                tempAudioFrame = VIDEO_GRABBER.grabSamples();
//                if(tempAudioFrame != null) {
//                    videoRecorder.recordSamples(tempAudioFrame.samples);
//                }
            }
            filter.stop();
            videoRecorder.stop();
            videoRecorder.release();
            VIDEO_GRABBER.stop();
            VIDEO_GRABBER.release();
        } catch (FrameGrabber.Exception e) {
            e.printStackTrace();
        } catch (FrameRecorder.Exception e) {
            e.printStackTrace();
        } catch (FrameFilter.Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void initVideoRecorder() {
        try {
            //FFmpeg effect/filter that will be applied
            filter = new FFmpegFrameFilter("colorchannelmixer=.393:.769:.189:0:.349:.686:.168:0:.272:.534:.131", VIDEO_GRABBER.getImageWidth(), VIDEO_GRABBER.getImageHeight());
            videoRecorder = FFmpegFrameRecorder.createDefault("C:\\Users\\Administrator\\Desktop\\demo4.mp4", VIDEO_GRABBER.getImageWidth(), VIDEO_GRABBER.getImageHeight());
            videoRecorder.start();
        } catch (FrameRecorder.Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        new FilterTest().doInBackground();
    }
}
