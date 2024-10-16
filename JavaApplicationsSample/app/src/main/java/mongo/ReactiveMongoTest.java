package mongo;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gt;
import static com.mongodb.client.model.Filters.in;
import static com.mongodb.client.model.Filters.lt;
import static com.mongodb.client.model.Filters.lte;
import static com.mongodb.client.model.Updates.set;
import static com.mongodb.client.model.Updates.inc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;

import mongo.SubscriberHelpers.ObservableSubscriber;
import mongo.SubscriberHelpers.OperationSubscriber;
import mongo.SubscriberHelpers.PrintDocumentSubscriber;
import mongo.SubscriberHelpers.PrintSubscriber;

public class ReactiveMongoTest {

    public static void main(String[] args) {
        MongoCollection<Document> coll = normalConnect();
        insert(coll);
        update(coll);
        query(coll);
        delete(coll);

    }

    private static MongoCollection<Document> normalConnect() {
        ConnectionString connString = new ConnectionString(
          "mongodb://pos_service:zhangk225@localhost:27018/posService?w=majority"
        );
        MongoClientSettings settings = MongoClientSettings.builder()
          .applyConnectionString(connString)
          .retryWrites(true)
          .build();
        MongoClient mongoClient = MongoClients.create(settings);
        /**
         *  If a database does not exist,
         *  MongoDB creates the database when you first store data for that database
         */
        MongoDatabase database = mongoClient.getDatabase("posService");
        /**
         * If a collection does not exist,
         * MongoDB creates a new collection when you first store data for the collection
         * MongoCollection instances are immutable
         */
        return database.getCollection("rayTestCollection");
    }

    /**
     * The Create operation could be executed when other Subscriber's .await() is called
     */
    private static void insert(MongoCollection<Document> coll) {
        /**
         * Insert One Document
         * {"name":"MongoDB","type":"database","count":1,"versions":["v3.2","v3.0","v2.6"],"info":{x:203,y:102}}
         *
         * To create the document using the Java driver, instantiate a Document object with a field and value,
         * and use its append() method to include additional fields and values to the document object.
         * The value can be another Document object to specify an embedded document:
         */
        Document doc = new Document("name", "MongoDB")
          .append("type", "database")
          .append("count", 1)
          .append("versions", Arrays.asList("v3.2", "v3.0", "v2.6"))
          .append("info", new Document("x", 203).append("y", 102));
        /**
         * If no top-level _id field is specified in the document,
         * MongoDB automatically adds the _id field to the inserted document.
         */
        ObservableSubscriber<InsertOneResult> insertOneSubscriber = new OperationSubscriber<>();
        coll.insertOne(doc).subscribe(insertOneSubscriber);
//		insertOneSubscriber.await();

//		 Insert Multiple Documents
        List<Document> documents = new ArrayList<Document>();
        for (int i = 0; i < 100; i++) {
            documents.add(new Document("i", i));
        }
        /**
         * To insert these documents into the collection, pass the list of documents to the insertMany() method.
         * Here we block on the Publisher to finish so that when we call the next operation we know the data has been inserted into the database!
         */
        ObservableSubscriber<InsertManyResult> insertManySubscriber = new OperationSubscriber<>();
        coll.insertMany(documents).subscribe(insertManySubscriber);
        // if not call this method the update operation will not take effect as there has no record for it
        insertOneSubscriber.await();
    }

    /**
     * the Update operation could be executed when other Subscriber's .awais() is called
     */
    private static void update(MongoCollection<Document> coll) {
        // Update a Single Document
        coll.updateOne(eq("i", 10), set("i", 210)).subscribe(new PrintSubscriber<UpdateResult>("Update Result: %s"));
//		// Update Multiple Documents
        ObservableSubscriber<UpdateResult> updateSubscriber = new PrintSubscriber<>("Update complete: %s");
        //inc: Creates an update that increments the value of the field with the given name by the given value.
        coll.updateMany(lt("i", 100), inc("i", 200)).subscribe(updateSubscriber);
//		updateSubscriber.await();
    }

    private static void query(MongoCollection<Document> coll) {
        ObservableSubscriber<Long> printCount = new PrintSubscriber<Long>("total # of documents after inserting  100 small ones (should be 101): %s");
        coll.countDocuments().subscribe(printCount);
        ObservableSubscriber<Document> printQuery1 = new PrintDocumentSubscriber();
        // Find the First Document in a Collection
        coll.find().first().subscribe(printQuery1);
        printQuery1.await();
        ObservableSubscriber<Document> printQuery2 = new PrintDocumentSubscriber();
        // Find All Documents in a Collection
        coll.find().subscribe(printQuery2);
        printQuery2.await();
        ObservableSubscriber<Document> printQuery3 = new PrintDocumentSubscriber();
        // Get A Single Document That Matches a Filter
        coll.find(eq("i", 71)).first().subscribe(printQuery3);
        printQuery3.await();
        //Get All Documents That Match a Filter
        ObservableSubscriber<Document> printQuery4 = new PrintDocumentSubscriber();
        coll.find(gt("i", 50)).subscribe(printQuery4);
        printQuery4.await();
        ObservableSubscriber<Document> printQuery5 = new PrintDocumentSubscriber();
        coll.find(and(gt("i", 50), lte("i", 100))).subscribe(printQuery5);
        printQuery5.await();
    }

    /**
     * The Delete operation could not be executed when other Subscriber's .await() be called
     *
     * @param coll
     */
    private static void delete(MongoCollection<Document> coll) {
        // Delete a Single Document That Match a Filter
        ObservableSubscriber<DeleteResult> deleteSubscriber = new PrintSubscriber<>("Delete complete: %s");
        coll.deleteOne(eq("i", 10)).subscribe(deleteSubscriber);
        deleteSubscriber.await();
//		// Delete All Documents That Match a Filter
        ObservableSubscriber<DeleteResult> cleanSubscriber = new PrintSubscriber<>("Delete complete: %s");
        coll.deleteMany(new Document()).subscribe(cleanSubscriber);
        cleanSubscriber.await();
        ObservableSubscriber<Void> successSubscriber = new OperationSubscriber<>();
        coll.drop().subscribe(successSubscriber);
        successSubscriber.await();

    }
}
