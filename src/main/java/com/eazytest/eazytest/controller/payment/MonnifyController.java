package com.eazytest.eazytest.controller.payment;

import com.eazytest.eazytest.dto.payment.SubscribeRequestDto;
import com.eazytest.eazytest.payment.dto.AccessTokenResponse;
import com.eazytest.eazytest.payment.dto.InitTransactionRequest;
import com.eazytest.eazytest.payment.dto.InitTransactionResponse;
import com.eazytest.eazytest.payment.service.MonnifyServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/v1/eazytest/monnify")
@AllArgsConstructor
public class MonnifyController {
    private MonnifyServiceImpl monnifyService;

    @PostMapping("/initTransaction")
    public ResponseEntity<InitTransactionResponse> initializeTransaction(@RequestBody InitTransactionRequest request) throws URISyntaxException {
        return monnifyService.initializeTransaction(request);
    }
    @GetMapping("/generateAccessToken")
    public ResponseEntity<AccessTokenResponse> generateAccessToken() throws URISyntaxException {
        return new ResponseEntity<>(monnifyService.generateAccessToken(), HttpStatus.OK);
    }

    @PostMapping("/subscribeForUltimateTier")
    public ResponseEntity<InitTransactionResponse> subscribeToUltimateTier(@RequestBody SubscribeRequestDto subscribeRequestDto) throws URISyntaxException {
        return monnifyService.subscriptionMethod(subscribeRequestDto);
    }
}
