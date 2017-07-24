package com.babyscripts.awsplugin;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.auth.AWS4Signer;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.http.AmazonHttpClient;
import com.amazonaws.http.HttpMethodName;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Map;

/**
 * Created by dkopel on 7/13/17.
 */
public class InternalRequest {
    static ObjectMapper mapper = new ObjectMapper();

    public static Request createRequest(HttpMethodName method, String path, Map<String, String> body, DefinedEnvironment env) {
        Request request = new DefaultRequest("execute-api");
        request.setHttpMethod(method);
        request.setEndpoint(URI.create(env.getUrl()));
        request.setResourcePath(path);
        request.addHeader("Content-Type", "application/json");
        byte[] bytes = null;
        try {
            bytes = mapper.writeValueAsString(body).getBytes();
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        request.setContent(new ByteArrayInputStream(bytes));
        return request;
    }

    public static AmazonHttpClient.RequestExecutionBuilder signRequest(Request request, AWSCredentials creds, String cert)  {
        AWS4Signer signer = new AWS4Signer();
        signer.setServiceName("execute-api");
        signer.setRegionName("us-east-1");
        signer.sign(request, creds);

        ClientConfiguration client = new ClientConfiguration();
        try {
            client.getApacheHttpClientConfig().setSslSocketFactory(
                X509CertificateUtils.clientCert(cert)
            );
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

        return new AmazonHttpClient(client)
                .requestExecutionBuilder()
                .request(request);
    }
}
