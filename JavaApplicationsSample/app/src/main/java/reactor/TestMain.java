package reactor;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import utils.BigDecimalTest;

@Slf4j
public class TestMain {

	public static void main(String[] args) {
		int coreSize=10;
		int maxSize=200;
		long idleTimeout=1000;
		ExecutorService soapExecutor=new ThreadPoolExecutor(coreSize, maxSize, idleTimeout, TimeUnit.MILLISECONDS,
		          new LinkedBlockingQueue<>(5), new CustomizableThreadFactory("qtp-"));
		Scheduler soapScheduler=Schedulers.fromExecutorService(soapExecutor);
		Mono.just("aaaa").doOnNext(TestMain::doNext)
		.doOnError((e)->log.error("=======11")).subscribeOn(Schedulers.boundedElastic()).subscribeOn(soapScheduler)
		.subscribe(t -> log.debug("orderId: {} save success, order is: {}", "orderId", t),
                error -> log.error("find null order, order id is:" + "ooooo", error));

	}
	
	private static void doNext(String str) {
		log.error("=======2222");		
//		Long.valueOf(str);		
	}

}
