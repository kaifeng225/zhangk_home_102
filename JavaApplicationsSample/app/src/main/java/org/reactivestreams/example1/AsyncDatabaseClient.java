package org.reactivestreams.example1;

import java.util.Random;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

public class AsyncDatabaseClient {
	
	private final Random rnd = new Random();

	public String store(String aaa) {
		String message="store "+aaa;
		System.out.println(message);
		return message;
	}

	public Publisher<String> getStreamOfItems() {
		// TODO Auto-generated method stub
		return new Publisher<String>(){

			@Override
			public void subscribe(Subscriber<? super String> s) {
				s.onNext(rnd.nextInt(10)+"");				
			}
			
		};
	}
}
