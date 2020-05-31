package com.xing;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.DeleteItemResult;
import com.amazonaws.services.dynamodbv2.model.GetItemResult;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class ProjectController {
    ProfileCredentialsProvider profileCredentialsProvider = new ProfileCredentialsProvider();
    AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000","ap-southeast-1"))
            .withCredentials(profileCredentialsProvider).build();
    private String projectTable = "Project_hongxing";

    @RequestMapping(value = "/project", method = PUT)
    public PutItemResult putItem(@RequestBody Map<String, AttributeValue> item) {
        return client.putItem(projectTable, item);
    }

    @RequestMapping(value = "/project", method = GET)
    public GetItemResult getItemByKey(@RequestBody Map<String, AttributeValue> key) {
       return client.getItem(projectTable, key);
    }

    @RequestMapping(value = "/project", method = DELETE)
    public DeleteItemResult deleteItem(@RequestBody Map<String, AttributeValue> key) {
        return client.deleteItem(projectTable, key);
    }

}
