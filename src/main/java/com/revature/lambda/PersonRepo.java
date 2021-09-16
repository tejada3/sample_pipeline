package com.revature.lambda;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PersonRepo {

    private final DynamoDBMapper dbReader;


    public PersonRepo(){
        dbReader = new DynamoDBMapper(AmazonDynamoDBClientBuilder.defaultClient());
    }

    public List<PersonAge> getAllPersons(){
        return dbReader.scan(PersonAge.class, new DynamoDBScanExpression());
    }

    public Optional<PersonAge> getByLastName(String lastName){

        Map<String, AttributeValue> queryinputs = new HashMap<>();
        queryinputs.put(":lastName", new AttributeValue().withS(lastName));


        DynamoDBScanExpression query = new DynamoDBScanExpression()
                .withFilterExpression("lastName = :lastName")
                .withExpressionAttributeValues(queryinputs);

        List<PersonAge> results = dbReader.scan(PersonAge.class, query);

        return results.stream().findFirst();

    }
}
