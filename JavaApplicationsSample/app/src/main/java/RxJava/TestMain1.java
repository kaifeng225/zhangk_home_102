package RxJava;

import java.util.Collections;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;


public class TestMain1 {
	
	private static void test1() {
		Observable<String> observer=Observable.create(sub->{
			sub.onNext("Hello, reactive workd111!");
			sub.onCompleted();		
		}
				);
		
		Subscriber<String> subscriber = new Subscriber<String>() {

			@Override
			public void onCompleted() {
				System.out.println("Done1!");
				
			}

			@Override
			public void onError(Throwable e) {
				System.err.println(e);
				
			}

			@Override
			public void onNext(String t) {
				System.out.println(t);
				
			}
			
		};
		
		observer.subscribe(subscriber);
		observer.subscribe(subscriber);
	}
	
	private static void test2() {
		Observable.create(sub->{
			sub.onNext("Hello, reactive workd22!");
			sub.onCompleted();		
		}).subscribe(System.out::println,System.err::println,()->System.out.println("Done2"));
		
	}
	
	private static void test3() {
		Observable.just("1","2","3","4","ttt").subscribe(va->System.out.println(Integer.parseInt(va)),System.err::println,()->System.out.println("Done3"));
		Observable.from(new String[] {"A","B","C"}).subscribe(System.out::println,System.err::println,()->System.out.println("Done3"));
		Observable.from(Collections.emptyList()).subscribe(System.out::println,System.err::println,()->System.out.println("Done3"));
	}
	
	private static void test4() {
		Observable<String> hello =Observable.fromCallable(()->"Hello ");
		Future<String> future = Executors.newCachedThreadPool().submit(()->"Word");
		Observable<String> world=Observable.from(future);
		hello.subscribe(System.out::println,System.err::println,()->System.out.println("Done4"));
//		Observable.concat(hello,world,Observable.just("!")).forEach(System.out::print);
		Observable.concat(hello,world,Observable.just("!")).subscribe(System.out::println,System.err::println,()->System.out.println("Done4"));
	}
	
	private static void test5() {
		Observable.interval(1, TimeUnit.SECONDS).subscribe(e->System.out.println("Received: "+e));
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public static void main(String[] args) {
//		test1();
//		test2();
//		test3();
//		test4();
		test5();
	}

}
