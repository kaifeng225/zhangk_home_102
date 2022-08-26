package RxJava;

import rx.Observable;

public class TestMain2 {
	
	private static void testZip() {
		Observable.zip(Observable.just("A","B","C"),Observable.just("1","2","3"),(x,y)->x+y).subscribe(System.out::println,System.err::println,()->System.out.println("DoneTestZip"));;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		testZip();
	}

}
