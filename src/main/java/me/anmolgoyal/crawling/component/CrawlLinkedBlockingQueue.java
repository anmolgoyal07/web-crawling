package me.anmolgoyal.crawling.component;

import java.util.concurrent.LinkedBlockingQueue;

public class CrawlLinkedBlockingQueue <E>  extends LinkedBlockingQueue<E> {

	private static final long serialVersionUID = 1L;
	Object mutex = new Object();
	
	@Override
	public boolean add(E e) {
		boolean result;
		synchronized (mutex) {
			result = super.add(e);
			mutex.notify();
		}
		
		return result;
	}
	
	@Override
	public E poll() {
		E elem;
		synchronized (mutex) {
			if(this.isEmpty()) {
				try {
					mutex.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			elem = super.poll();
		}
		return elem;
	}

}
