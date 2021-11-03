package hu.user.mkicore.service;

import hu.user.mkicore.config.RestConfig;
import hu.user.mkicore.domain.CommulatedResponseFromEstimator;
import hu.user.mkicore.dto.DateTimeSplitter;
import io.spring.guides.gs_producing_web_service.DetectionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.*;

@Service
public class Prediction {

    private RestConfig restConfig;
    private RestTemplate restTemplate;

    @Value("${estimatorPath}")
    URI estimatorURI;

    @Autowired
    public Prediction(RestConfig restConfig) {
        restTemplate = restConfig.getRestTemplate(new RestTemplateBuilder());
    }

    public CommulatedResponseFromEstimator getPrediction(DetectionRequest detectionRequest) {
        Map<String, Map<String,Object>> requestMap = new HashMap<>();
        Map<String,Object> requestParameters = new HashMap<>();
        DateTimeSplitter dateTimeSplitter = new DateTimeSplitter(detectionRequest.getTimestamp());
        requestParameters.put("card_number",detectionRequest.getCardNumber());
        requestParameters.put("transaction_type",detectionRequest.getTransactionType());
        requestParameters.put("amount",detectionRequest.getAmount());
        requestParameters.put("currency_name",detectionRequest.getCurrencyName());
        requestParameters.put("response_code",detectionRequest.getResponseCode());
        requestParameters.put("country_name",detectionRequest.getCountryName());
        requestParameters.put("vendor_code",detectionRequest.getVendorCode());
        requestParameters.put("year",dateTimeSplitter.getYear());
        requestParameters.put("month",dateTimeSplitter.getMonth());
        
        requestParameters.put("day",dateTimeSplitter.getDay());
        requestParameters.put("hour",dateTimeSplitter.getHour());
        requestParameters.put("min",dateTimeSplitter.getMin());
        requestParameters.put("sec",dateTimeSplitter.getSec());
        requestParameters.put("millis",dateTimeSplitter.getMillisec());
        requestMap.put("values", requestParameters);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String,Map<String,Object>>> httpEntity = new HttpEntity<>(requestMap, httpHeaders);
        ResponseEntity<CommulatedResponseFromEstimator> responseEntity = restTemplate.postForEntity(estimatorURI,  httpEntity, CommulatedResponseFromEstimator.class);
        CommulatedResponseFromEstimator responseFromEstimator = responseEntity.getBody();
        return responseFromEstimator;
    }
}
