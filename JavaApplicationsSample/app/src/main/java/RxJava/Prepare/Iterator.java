package RxJava.Prepare;

/**
 * 迭代器模式
 * @author rzhang2
 *
 * @param <T>
 */
public interface Iterator<T> {
	T next();

	boolean hasNext();
}
