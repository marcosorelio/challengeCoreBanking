package org.orelio.facade;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.orelio.model.Account;
import org.orelio.model.Constants;
import org.orelio.model.Operation;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class ChallengeCoreBankingFacade {
    private static final Map<String, Account> accountMap = new HashMap<>();

    public Account createAccount(Account account){
        accountMap.put(account.getId(),account);
        return account;
    }

    public Account updateAccount(Account account, String accountId){
        accountMap.put(accountId,account);
        return account;
    }

    public Account getAccount(String accountId){
        return accountMap.get(accountId);
    }

    public void resetAccount(){
        accountMap.clear();
    }


    public String operationEvent(Operation operation) {
        String optTypeStr = operation.getType().toLowerCase();
        String resultOrigin = operation.getOrigin();
        String resultDestin = operation.getDestination();
        //GET ACCOUNT
        //Long resultBalance = getAccount(resultId);
        Account accountReturn = getAccount(operation.getDestination());
        Account accountOrigin = getAccount(operation.getOrigin());
        Account account = new Account();
        switch (optTypeStr) {
            case Constants.DEPOSIT:

                if(accountReturn == null){
                    account.setId(operation.getDestination());
                    account.setBalance(Long.valueOf(operation.getAmount()));
                } else {
                    account.setId(accountReturn.getId());
                    account.setBalance(accountReturn.getBalance() + Long.parseLong(operation.getAmount()));
                }
                return resultJsonAPI(createAccount(account), Constants.fmtDestination) ;

            //break;
            case Constants.TRANSFER:

                if (accountReturn != null && accountOrigin != null) {

                    //destination
                    account.setId(accountReturn.getId());
                    account.setBalance(accountReturn.getBalance() + Long.parseLong(operation.getAmount()));
                    //updateAccount(account, accountReturn.getId());
                    String jsonResult = resultJsonAPI(updateAccount(account, accountReturn.getId()));

                    //origin
                    account.setId(accountOrigin.getId());
                    account.setBalance(accountOrigin.getBalance() - Long.parseLong(operation.getAmount()));
                    updateAccount(account, accountReturn.getId());

                    return String.format(Constants.fmtOriginDestin, resultJsonAPI(updateAccount(account, accountReturn.getId())), jsonResult);

                }
                return Constants.ZERO;

            //break;
            case Constants.WITHDRAW:

                if (accountOrigin != null) {
                    account.setId(accountOrigin.getId());
                    account.setBalance(accountOrigin.getBalance() - Long.parseLong(operation.getAmount()));
                    //return String.format(fmtDestination,  updateAccount(account, resultId));

                    return resultJsonAPI(updateAccount(account, account.getId()), Constants.fmtDestination) ;

                }
                return Constants.ZERO;

            default:
                return Constants.ZERO;

        }
    }

    public String resultJsonAPI(Account account, String fmt){
        try {
            ObjectMapper objMapper = new ObjectMapper();
            String jsonStr = objMapper.writeValueAsString(account);
            return String.format(fmt, jsonStr);

        } catch (Exception e) {
            return Constants.ZERO;
        }

    }
    public String resultJsonAPI(Account account){
        try {
            ObjectMapper objMapper = new ObjectMapper();
            return objMapper.writeValueAsString(account);

        } catch (Exception e) {
            return Constants.ZERO;
        }

    }



}
