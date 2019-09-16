package org.nico.codegenerator.utils.tests;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.google.common.util.concurrent.RateLimiter;

public class RateLimiterTest {

	private ThreadPoolExecutor tpe = new ThreadPoolExecutor(10, 10, 0, TimeUnit.MINUTES, new LinkedBlockingQueue<>());
	
	@Test
	public void testAcquire() throws InterruptedException {
		RateLimiter limiter = RateLimiter.create(1);

		for(int i = 1; i < 10; i = i + 2 ) {
			double waitTime = limiter.acquire(2);
			System.out.println("cutTime=" + System.currentTimeMillis() + " acq:" + i + " waitTime:" + waitTime);
		}

		for(int i = 1; i < 10; i = i + 2 ) {
			double waitTime = limiter.acquire(1);
			Thread.sleep(500);
			System.out.println("Sleep 500ms, cutTime=" + System.currentTimeMillis() + " acq:" + i + " waitTime:" + waitTime);
		}
	}
	
	@Test
	public void testLimit() throws InterruptedException {
		Limiter limiter = new Limiter(1); 

		for(int i = 1; i < 10; i = i + 2 ) {
			double waitTime = limiter.acquire(1);
			System.out.println("cutTime=" + System.currentTimeMillis() + " acq:" + i + " waitTime:" + waitTime);
		}

		for(int i = 1; i < 10; i = i + 2 ) {
			double waitTime = limiter.acquire(1);
			Thread.sleep(500);
			System.out.println("Sleep 500ms, cutTime=" + System.currentTimeMillis() + " acq:" + i + " waitTime:" + waitTime);
		}
	}
	
	@Test
	public void testPool(){
		tpe.execute(() -> {
			System.out.println("abc");
		});
	}

	public static class Limiter{

		private LinkedBlockingQueue<Object> blockQueue;

		private Thread producer;

		private int limit;

		private volatile boolean end;

		private Object empty;

		public Limiter(final int limit) {
			this.end = false;
			this.limit = limit;
			this.blockQueue = new LinkedBlockingQueue<>();
			this.empty = new Object();
			producer = new Thread(() -> {
				while(! end) {
					try {
						Thread.sleep(this.limit * 1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					blockQueue.add(this.empty);
				}
			}) ;
			producer.start();
		}

		public void clear() {
			this.end = true;
		}
		
		public double acquire(int count) {
			long beginPollNanoTime = System.nanoTime();
			for(int index = 0; index < count; index ++) {
				try {
					this.blockQueue.take();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}	
			}
			long timeConsuming = (System.nanoTime() - beginPollNanoTime) / (1000 * 1000);
			return timeConsuming / 1000d;
		}

	}
}


