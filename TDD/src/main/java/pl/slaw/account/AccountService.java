package pl.slaw.account;

import java.util.List;
import java.util.stream.Collectors;

public class AccountService {

    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    // to bedzie testowana metoda
    List<Account> getAllActiveAccounts(){
        return accountRepository.getAllAccounts().stream()
                .filter(Account::isActive) // wyciagniecie aktywnych kont
                .collect(Collectors.toList());// upakowanie tego w liste
    }
}
