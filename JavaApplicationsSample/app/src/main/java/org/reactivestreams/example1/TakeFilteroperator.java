package org.reactivestreams.example1;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.function.Predicate;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class TakeFilteroperator<T> implements Publisher<T> {
	private Publisher<String> source;
	private int take;
	private Predicate<String> predicate;

	public TakeFilteroperator(Publisher<String> source, int count, Predicate<String> predicate) {
		this.source = source;
		this.take = count;
		this.predicate = predicate;
	}

	@Override
	public void subscribe(Subscriber s) {
		source.subscribe(new TakeFilterInner<>(s, take, predicate));

	}

	static final class TakeFilterInner<T> implements Subscriber<T>, Subscription {
		final Subscriber<T> actual;
		final int take;
		final Predicate<T> predicate;
		final Queue<T> queue;
		Subscription current;
		int remaining=50;
		int filtered=0;
		volatile long requested=2;

		public TakeFilterInner(Subscriber<T> actual, int take, Predicate<T> predicate) {
			this.actual = actual;
			this.take = take;
			this.predicate = predicate;
			this.queue = new ArrayBlockingQueue(take);
		}

		@Override
		public void onSubscribe(Subscription current) {
			current.request(take);
		}

		@Override
		public void onNext(T element) {
			long r = requested;
			Subscriber<T> a = actual;
			Subscription s = current;
			if (remaining > 0) {
				boolean isValid = predicate.test(element);
				boolean isEmpty = queue.isEmpty();
				if (isValid && r > 0 && isEmpty) {
					a.onNext(element);
					remaining--;
				} else if (isValid && (r == 0 || !isEmpty)) {
					queue.offer(element);
					remaining--;
				} else if (!isValid) {
					filtered++;
				}
			} else {
				s.cancel();
				onComplete();
			}
			if (filtered > 0 && remaining / filtered < 2) {
				s.request(take);
				filtered = 0;
			}
		}

		@Override
		public void request(long n) {
			// TODO Auto-generated method stub

		}

		@Override
		public void cancel() {
			// TODO Auto-generated method stub

		}

		@Override
		public void onError(Throwable t) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onComplete() {
			// TODO Auto-generated method stub

		}

	}

}
