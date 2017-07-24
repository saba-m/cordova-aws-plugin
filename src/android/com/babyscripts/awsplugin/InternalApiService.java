package com.babyscripts.awsplugin;

import com.amazonaws.Request;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.http.AmazonHttpClient;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.http.HttpResponse;
import com.amazonaws.http.HttpResponseHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.Map;
import java.util.Properties;

/**
 * Created by dkopel on 7/13/17.
 */
public class InternalApiService {
    private ObjectMapper mapper = new ObjectMapper();
    private Properties properties = new Properties();

    public InternalApiService() {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("application.properties");
        try {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getAccessKey() {
        return properties.getProperty("accessKey");
    }

    private String getSecretKey() {
        return properties.getProperty("secretKey");
    }

    private String getCert() {
        return properties.getProperty("cert");
    }

    private Map toMap(BloodPressurePublisher bp) {
        return mapper.convertValue(bp, Map.class);
    }

    public void publishBloodPressure(BloodPressurePublisher body, DefinedEnvironment env) {
        Map mapBody = toMap(body);
        Request request = InternalRequest.createRequest(
                HttpMethodName.POST,
                "blood-pressure",
                mapBody,
                env
        );

        AmazonHttpClient.RequestExecutionBuilder req = InternalRequest.signRequest(
                request,
                new BasicAWSCredentials(getAccessKey(), getSecretKey()),
                getCert()
        );

        req.execute(new HttpResponseHandler<Object>() {
            public Object handle(HttpResponse httpResponse) throws Exception {
                System.out.println(mapper.readValue(httpResponse.getContent(), Object.class));
                return null;
            }

            public boolean needsConnectionLeftOpen() {
                return false;
            }
        });
    }
}
