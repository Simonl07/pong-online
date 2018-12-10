package client.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Instantaneous Rate Of Change Queue. A Thread-safe data structure for
 * recording fixed number of Entries and their timestamp in order to calculate
 * the instantaneous rate of change of the past values
 * 
 * @author Simon Lu
 *
 */
public class AverageRateOfChangeQueue<T extends Number> {

	private LinkedList<Entry<Long, T>> queue;
	private final int SIZE;

	public AverageRateOfChangeQueue() {
		this(3);
	}

	public AverageRateOfChangeQueue(int size) {
		this.SIZE = size;
		this.queue = new LinkedList<>();
	}

	public synchronized void add(T t) {
		if (this.queue.size() == this.SIZE) {
			this.queue.removeLast();
		}
		this.queue.addFirst(new IROCEntry(System.currentTimeMillis(), t));
	}

	public double getValueROC() {
		return getValueROC(0);
	}

	public synchronized double getValueROC(long lookback) {
		if (queue.size() < 2) {
			return 0;
		}

		Iterator<Entry<Long, T>> iterator = this.queue.iterator();
		long current = System.currentTimeMillis();
		if (lookback == 0) {
			lookback = current;
		}

		long deadline = current - lookback;
		List<Entry<Long, T>> values = new ArrayList<>();

		while (iterator.hasNext()) {
			Entry<Long, T> e = iterator.next();
			if (e.getKey() >= deadline) {
				values.add(e);
			} else {
				if (!values.isEmpty()) {
					break;
				}
			}
		}

		if (values.size() < 2) {
			return 0;
		}

		T firstValue = values.get(values.size() - 1).getValue();
		Long firstTime = values.get(values.size() - 1).getKey();
		T lastValue = values.get(0).getValue();
		Long lastTime = values.get(0).getKey();

		long deltaT = lastTime - firstTime;
		double deltaV = lastValue.doubleValue() - firstValue.doubleValue();

		double roc = deltaT == 0 ? 0.0 : deltaV / deltaT;
		return roc;
	}

	public int getEntryFrequency() {
		return this.getEntryFrequency(0);
	}

	public synchronized int getEntryFrequency(long lookback) {
		int count = 0;
		Iterator<Entry<Long, T>> iterator = this.queue.iterator();
		long current = System.currentTimeMillis();
		if (lookback == 0) {
			lookback = current;
		}
		long deadline = current - lookback;
		while (iterator.hasNext() && iterator.next().getKey() > deadline) {
			count++;
		}
		return count;
	}

	private class IROCEntry implements Map.Entry<Long, T> {

		private Long key;
		private T value;

		public IROCEntry(Long key, T value) {
			super();
			this.key = key;
			this.value = value;
		}

		@Override
		public Long getKey() {
			return this.key;
		}

		@Override
		public T getValue() {
			return this.value;
		}

		@Override
		public T setValue(T value) {
			T old = this.value;
			this.value = value;
			return old;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "IROCEntry [key=" + key + ", value=" + value + "]";
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AverageRateOfChangeQueue [queue=" + queue + ", SIZE=" + SIZE;
	}

}
