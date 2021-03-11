package cl.lfuentes.lambda2.function;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import cl.lfuentes.lambda2.model.ContactoRequest;
import cl.lfuentes.lambda2.model.ContactoResponse;


public class ObtenerContacto implements RequestHandler<ContactoRequest, ContactoResponse> {

    
    private DynamoDB dynamoDb;
    private String DYNAMODB_TABLE_NAME = "ContactoLFV";
	private Regions REGION = Regions.US_EAST_1;
    

    private void initDynamoDbClient() {
		AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(REGION).build();

		this.dynamoDb = new DynamoDB(client);
	}

	@Override
	public ContactoResponse handleRequest(ContactoRequest input, Context context) {
		this.initDynamoDbClient();
    	
    	Item item = this.dynamoDb.getTable(DYNAMODB_TABLE_NAME).getItem("id",input.getId());
    	
    	
    	return (new ContactoResponse(item.getString("id"),item.getString("firstName"), item.getString("lastName"), item.getString("status"))) ;
	}
}