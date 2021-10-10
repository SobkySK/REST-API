package me.patriksobol.bank.repository;

import me.patriksobol.bank.domain.entity.BankCardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankCardRepository extends JpaRepository<BankCardEntity, Long> {

    boolean existsBankCardEntityByCardNumber(String cardNumber);
}
