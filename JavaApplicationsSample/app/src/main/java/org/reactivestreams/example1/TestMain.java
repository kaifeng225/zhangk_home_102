package org.reactivestreams.example1;

import org.reactivestreams.Publisher;

public class TestMain {

	private static AsyncDatabaseClient dbClient = new AsyncDatabaseClient();

	public static void main(String[] args) {

		list(3).subscribe(null);

	}

	private static Publisher<String> list(int count) {
		Publisher<String> source = dbClient.getStreamOfItems();
		TakeFilteroperator<String> takeFilter = new TakeFilteroperator<>(source, count, item -> isValid(item));
		return takeFilter;
	}

	private static boolean isValid(String item) {
		return item.equals(5);
	}

}
