package mongo;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

public class MongoApp {
    public static void main(String[] args) throws Exception {
        MongoClient mongoClient = MongoClients.create("mongodb://pos_service:RYDrNHGjpf@localhost:27018/pos_service");
        MongoOperations mongoOps = new MongoTemplate(mongoClient, "pos_service");
        mongoOps.insert(new Person("zhangk", 34));

        System.out.println(mongoOps.findOne(new Query(where("name").is("Joe")), Person.class));

//        mongoOps.dropCollection("person");
    }
}
