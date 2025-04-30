package com.excelfore.test.BankTransaction.repository;

import com.excelfore.test.BankTransaction.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
}
