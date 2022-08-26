package RxJava.Prepare;

/**
 * 虽然RxObserver非常类似于Iterator,但是他不是调用Iterator的next()方法,而是通过onNext()回调将一个新值通知到RxObserver.
 * @author rzhang2
 *
 * @param <T>
 */
public interface RxObserver<T> {

	void onNext(T next);
	
	void onComplete();
	
	void onError(Exception e);
}
