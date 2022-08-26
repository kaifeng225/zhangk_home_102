package mongo;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.bson.Document;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import com.google.common.collect.Lists;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;

import reactor.core.publisher.Mono;

/**
 * mongo的reactive还有问题 尚待研究
 * @author rzhang2
 *
 */
public class ReactiveMongoApp {
	
	private static ReactiveMongoTemplate mongoOps;
	
	private static Mono<ReactiveMongoTemplate> getTemplate1(Mono<List<String>> settings) {
		return settings.map(list->{
			MongoClient mongoClient = MongoClients.create(list.get(0));	
			ReactiveMongoTemplate template =new ReactiveMongoTemplate(mongoClient, list.get(1));
			 System.out.println("======2=======");
	        return template;	        
		});		
	}
	
	private static Mono<MongoClient> getTemplate2(Mono<List<String>> settings) {
		// Replace the uri string with your MongoDB deployment's connection string.
		return settings.map(list -> {
			ConnectionString connString = new ConnectionString(list.get(0));
			MongoClientSettings clientSettings = MongoClientSettings.builder().applyConnectionString(connString)
					.retryWrites(true).build();
			MongoClient mongoClient = MongoClients.create(clientSettings);
			return mongoClient;
		});
	}

    public static void main(String[] args) throws Exception {
        String url="mongodb://pos_service:RYDrNHGjpf@localhost:27017/pos_service";
        String database="pos_service";
        CountDownLatch latch = new CountDownLatch(1);
        Mono<ReactiveMongoTemplate> mongoOps=getTemplate1(Mono.just(Lists.newArrayList(url,database)));
        Mono<Person> person= insert(mongoOps);
        person.subscribe();
//        person.subscribe();
        latch.await();
    }
    
//    private static void search(Mono<MongoClient> monoClient) {
//    	monoClient.map(client->{
//    		MongoDatabase database = client.getDatabase("reactive").getCollection("person").;
//    		
//    	})
//    	;
//    	
////    	Mono<MongoCollection<Document>> monoCollection=mongoOps.getCollection("person");
////    	monoCollection.flatMap(collection->{
////    		collection.
////    	})
//    	
//			
//    }
    
	private static Mono<Person> insert(Mono<ReactiveMongoTemplate> mongoOps) {
		  return mongoOps.flatMap(ops -> {
			 System.out.println("======5=======");
			 return ops.insert(new Person("reactive", 34)).flatMap(p -> ops.findOne(new Query(where("name").is("reactive")), Person.class))
					 .doOnNext(person -> System.out.println(person.toString()))
//					 .flatMap(person -> ops.dropCollection("person"))
						.doOnSuccess(t -> System.out.println("============" + t.toString()))
//						.doOnComplete(latch::countDown)
						;});		
	}
}
