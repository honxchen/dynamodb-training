package com.xing;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class ProjectController {
    ProfileCredentialsProvider profileCredentialsProvider = new ProfileCredentialsProvider();
    AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000","ap-southeast-1"))
            .withCredentials(profileCredentialsProvider).build();
    private String projectTable = "Project_hongxing";

    @RequestMapping(value = "/project", method = PUT)
    public BatchWriteItemResult putItem(@RequestBody List<Map<String, AttributeValue>> items) {
        List<WriteRequest> writeRequests = items.stream()
                .map(item -> new PutRequest(item))
                .map(putRequest -> new WriteRequest(putRequest))
                .collect(Collectors.toList());
        BatchWriteItemRequest request = new BatchWriteItemRequest().addRequestItemsEntry(projectTable, writeRequests);
        return client.batchWriteItem(request);
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
