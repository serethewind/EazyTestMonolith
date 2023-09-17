package com.eazytest.eazytest.payment.service;

import com.eazytest.eazytest.payment.dto.*;
import com.eazytest.eazytest.payment.entity.TransactionEntity;
import com.eazytest.eazytest.payment.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MonnifyServiceImpl {
    private final RestTemplate restTemplate;
    private final TransactionRepository transactionRepository;

    @Value("${monnify.secret.key}")
    private String secretKey;
    @Value("${monnify.api.key}")
    private String apiKey;
    @Value("${monnify.baseurl}")
    private String baseURL;
    @Value("${monnify.contract.code}")
    private String contractCode;

    public AccessTokenResponse generateAccessToken() throws URISyntaxException {
        String fullKey = apiKey + ":" + secretKey;
        byte[] bytesFullKeyEncoded = Base64.encodeBase64(fullKey.getBytes(), false);
        String completeKeyEncoded = new String(bytesFullKeyEncoded);
        AccessTokenResponse paymentTokenResponse = new AccessTokenResponse();


        try {
            String finalUri = baseURL + "api/v1/auth/login";
            System.out.println(finalUri);
            log.info("finalUrl {}", finalUri);
            URI uri = new URI(finalUri);


            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Basic " + completeKeyEncoded);
            log.info("headers" + headers);
            System.out.println(completeKeyEncoded);
            log.info("fullkeyencoded {}", completeKeyEncoded);
            HttpEntity request = new HttpEntity<>(null, headers);
            System.out.println(request);


            ResponseEntity<AccessTokenResponse> result = restTemplate.postForEntity(uri, request, AccessTokenResponse.class);
            System.out.println(result);
            if (result.getStatusCode().is2xxSuccessful()) {
                paymentTokenResponse = result.getBody();
            }
        } catch (Exception e) {
            log.info("Error getting auth token>>" + e.getMessage());
        }

        return paymentTokenResponse;

    }

    public ResponseEntity<InitTransactionResponse> initializeTransaction(InitTransactionRequest request) throws URISyntaxException {
        String paymentReference = UUID.randomUUID().toString();
        String completedUrl = baseURL + "api/v1/merchant/transactions/init-transaction";
        String requestBody = String.format("""
                {
                    "amount": "%s",
                    "customerName": "%s",
                    "customerEmail": "%s",
                    "paymentReference": "%s",
                    "paymentDescription": "%s",
                    "currencyCode": "NGN",
                    "contractCode": "%s",
                    "redirectUrl": "https://my-merchants-page.com/transaction/confirm",
                    "paymentMethods": ["CARD","ACCOUNT_TRANSFER"]        }
                """, request.getAmount(), request.getCustomerName(), request.getCustomerEmail(), paymentReference, request.getPaymentDescription(), contractCode);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + getAccessToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        //what is sent to monnie point
        HttpEntity<String> httpEntity = new HttpEntity<>(requestBody, headers);
        InitTransactionResponse response = restTemplate.postForObject(completedUrl, httpEntity, InitTransactionResponse.class);
        assert response != null;
        TransactionEntity transactionRef = new TransactionEntity();
        // boolean isExists = transactionRepository.existsByCustomerEmail(request.getCustomerEmail());
        boolean doesExist = transactionRepository.existsByCustomerEmail(request.getCustomerEmail());
        //save payment reference

        if (doesExist) {
            transactionRef = transactionRepository.findByCustomerEmail(request.getCustomerEmail()).orElseThrow(() -> new UsernameNotFoundException("Customer with %s email not found" + request.getCustomerEmail()));
            transactionRef.setTransactionRef(response.getResponseBody().getTransactionReference());
            transactionRef.setCheckoutUrl(response.getResponseBody().getCheckoutUrl());
            transactionRef.setPaymentRef(response.getResponseBody().getPaymentReference());
            transactionRepository.save(transactionRef);
        } else {
            transactionRef = TransactionEntity.builder()
                    .transactionRef(response.getResponseBody().getTransactionReference())
                    .customerEmail(request.getCustomerEmail())
                    .status(false)
                    .checkoutUrl(response.getResponseBody().getCheckoutUrl())
                    .paymentRef(response.getResponseBody().getPaymentReference())
                    .build();
            transactionRepository.save(transactionRef);
        }

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    public ResponseEntity<TransactionStatusResponse> getTransactionStatus(String email) throws URISyntaxException {
        TransactionEntity transactionResponse = transactionRepository.findByCustomerEmail(email).orElseThrow(() -> new UsernameNotFoundException("Customer with %s email not found" + email));
        String transactionReference = transactionResponse.getTransactionRef();
        String finalUrl = baseURL + "api/v2/transactions/{transactionReference}";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + getAccessToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);
        TransactionStatusResponse transactionStatusResponse = restTemplate.exchange(finalUrl, HttpMethod.GET, httpEntity, TransactionStatusResponse.class, transactionReference).getBody();
        return new ResponseEntity<>(transactionStatusResponse, HttpStatus.OK);
    }

    private String getAccessToken() throws URISyntaxException {
        AccessTokenResponse response = generateAccessToken();
        log.info(response.toString());
        return response.getResponseBody().getAccessToken();
    }
}


