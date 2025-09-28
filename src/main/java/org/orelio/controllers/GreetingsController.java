package org.orelio.controllers;

import org.orelio.facade.ChallengeCoreBankingFacade;
import org.orelio.model.Account;
import org.orelio.model.Constants;
import org.orelio.model.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *
 * A sample greetings controller to return greeting text
 */
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class GreetingsController {

    @Autowired
    private ChallengeCoreBankingFacade challengeCoreBankingFacade;

    @GetMapping("/reset")
    @ResponseBody
    public ResponseEntity<Void> restBalance(){
            challengeCoreBankingFacade.resetAccount();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/balance")
    @ResponseBody
    public ResponseEntity<String> getAccount(@RequestParam("account_id") String accountId){
        Account account = challengeCoreBankingFacade.getAccount(accountId);
        if(account != null){
            return new ResponseEntity<String>(account.getBalance().toString(), HttpStatus.OK);
        }
        return new ResponseEntity<String>("0", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/event")
    @ResponseBody
    public ResponseEntity<String> deposit(@RequestBody Operation operation){
        String resultBalance = challengeCoreBankingFacade.operationEvent(operation);
        if(!resultBalance.equals(Constants.ZERO)){
            return new ResponseEntity<String>(resultBalance,  HttpStatus.OK);
        }
        return new ResponseEntity<String>(resultBalance, HttpStatus.NOT_FOUND);
    }

}
