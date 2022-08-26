package org.reactivestreams;

public interface Processor<T, R> extends Publisher<T>, Subscriber<R> {

}
