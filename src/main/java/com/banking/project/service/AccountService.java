package com.banking.project.service;

import com.banking.project.exception.OperationResultException;
import com.banking.project.model.dto.AccountDto;
import com.banking.project.model.dto.CurrencyDto;
import com.banking.project.model.dto.OperationResult;
import com.banking.project.model.dto.PersonDto;
import com.banking.project.model.entity.AccountEntity;
import com.banking.project.model.entity.PersonEntity;
import com.banking.project.model.enums.AccountType;
import com.banking.project.model.enums.ErrorMessageType;
import com.banking.project.model.enums.InformationMessage;
import com.banking.project.model.enums.OperationResultCode;
import com.banking.project.model.mapper.AccountMapper;
import com.banking.project.model.mapper.PersonMapper;
import com.banking.project.model.request.AccountUpdateRequest;
import com.banking.project.model.request.ConvertCurrencyRequest;
import com.banking.project.model.request.MoneyUpdateRequest;
import com.banking.project.repository.AccountRepository;
import com.banking.project.util.BankingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final PersonService personService;
    private final CurrencyService currencyService;

    /*
     * request AccountDto
     * response OperationResult
     * Bu method yeni bir account kaydı yapar. Aynı hesap türünden 1'den fazla kayıt oluşturmaz.
     */
    @Transactional
    public OperationResult createAccount(AccountDto accountDto) {
        AccountEntity accountEntity = AccountMapper.INSTANCE.toAccountEntity(accountDto);
       /* AccountEntity accountEntity = new AccountEntity();
        accountEntity.setDescription(accountDto.getDescription());
        accountEntity.setAccountNumber(accountDto.getAccountNumber());
        accountEntity.setAccountType(accountDto.getAccountType());
        accountEntity.setAccountBalance(accountDto.getAccountBalance());
        accountEntity.setAccountBalanceType(accountDto.getAccountBalanceType());*/

        PersonDto personDto = personService.findPerson(accountDto.getPerson().getEmail());
        PersonEntity personEntity = PersonMapper.INSTANCE.toPersonEntity(personDto);
        accountEntity.setPerson(personEntity);

        List<AccountEntity> accountEntityList = accountRepository.findByPersonId(personDto.getId());
        accountEntityList.stream()
                .filter(account -> account.getAccountType().equals(accountDto.getAccountType()))
                .findAny()
                .ifPresent(accountFilterForSingle ->
                        BankingUtil.throwException(OperationResult.newInstance(OperationResultCode.ERROR,
                                ErrorMessageType.VALIDATE_SAME_TYPE.getErrorMessage())));

        accountRepository.save(accountEntity);

        return OperationResult.successResult();
    }

    /*
     * request AccountUpdateRequest
     * response OperationResult
     * Bu method seçilmiş olan hesabın açıklama alanını günceller.
     */
    @Transactional
    public OperationResult updateAccount(AccountUpdateRequest accountUpdateRequest) {
        AccountEntity accountEntity = accountRepository.findById(accountUpdateRequest.getAccountId())
                .orElseThrow(() -> OperationResultException.builder()
                        .operationResult(OperationResult.newInstance(OperationResultCode.ERROR,
                                ErrorMessageType.VALIDATE_NOT_FOUND.getErrorMessage())).build());

        if (accountUpdateRequest.getDescription() != null) {
            accountEntity.setDescription(accountUpdateRequest.getDescription());
        }

        accountRepository.save(accountEntity);
        return OperationResult.successResult();
    }

    /*
     * request String
     * response OperationResult
     * Bu method kişiye ait tüm hesapları siler.
     */
    @Transactional
    public OperationResult removeAllAccount(String email) {
        PersonDto person = personService.findPerson(email);
        List<AccountEntity> accountEntityList = accountRepository.findByPersonId(person.getId());
        if (CollectionUtils.isEmpty(accountEntityList)) {
            throw OperationResultException.builder()
                    .operationResult(OperationResult.newInstance(OperationResultCode.ERROR,
                            ErrorMessageType.VALIDATE_NOT_FOUND.getErrorMessage())).build();
        }

        accountRepository.deleteAll(accountEntityList);

        return OperationResult.successResult();
    }

    /*
     * request Long
     * response OperationResult
     * Bu method seçilen hesabın silinmesini sağlar.
     */
    @Transactional
    public OperationResult singleRemoveAccount(Long accountId) {
        AccountEntity accountEntity = accountRepository.findById(accountId).
                orElseThrow(() -> OperationResultException.builder()
                        .operationResult(OperationResult.newInstance(OperationResultCode.ERROR,
                                ErrorMessageType.VALIDATE_NOT_FOUND.getErrorMessage())).build());
        accountRepository.delete(accountEntity);

        return OperationResult.successResult();
    }

    /*
     * request ConvertCurrencyRequest
     * response OperationResult
     * Bu method tl hesabındaki paradan döviz alımı gerçekleştirir.
     */
    @Transactional
    public OperationResult takeForeignCurrency(ConvertCurrencyRequest convertCurrencyRequest) {
        List<AccountEntity> accountEntityList = accountRepository.findByPersonId(convertCurrencyRequest.getPersonId());
        AccountEntity tlAccount = accountEntityList.stream()
                .filter(accountEntity -> accountEntity.getAccountType().equals(AccountType.TL.getAccountType()))
                .findAny()
                .orElseThrow(() -> OperationResultException.builder()
                        .operationResult(OperationResult.newInstance(OperationResultCode.ERROR,
                                BankingUtil.replaceMessage(ErrorMessageType.VALIDATE_NOT_FOUND_ACCOUNT_TYPE.getErrorMessage(), AccountType.TL.getAccountType())))
                        .build());

        if (tlAccount.getAccountBalance().compareTo(convertCurrencyRequest.getConvertedAmount()) < 0) {
            BankingUtil.throwException(OperationResult.newInstance(OperationResultCode.ERROR,
                    ErrorMessageType.VALIDATE_AMOUNT.getErrorMessage()));
        }

        // Güncek kur değeri bulma
        CurrencyDto currencyDto = currencyService.getCurrentCurrencyDto(convertCurrencyRequest.getConvertedCurrencyType());
        // Alım yapılmak istenen miktarın farklı döviz cinsine çevrilmesi
        BigDecimal currencyAmount = convertCurrencyRequest.getConvertedAmount()
                .divide(currencyDto.getAmount(), 2, RoundingMode.CEILING);
        // Alım yapılmak istenen döviz cinsinin bulunduğu hesabı bulma
        AccountEntity foreignAccount = accountEntityList.stream()
                .filter(accountEntity -> accountEntity.getAccountType().equals(convertCurrencyRequest.getConvertedCurrencyType()))
                .findAny()
                .orElseThrow(() -> OperationResultException.builder()
                        .operationResult(OperationResult.newInstance(OperationResultCode.ERROR,
                                BankingUtil.replaceMessage(ErrorMessageType.VALIDATE_NOT_FOUND_ACCOUNT_TYPE.getErrorMessage(),
                                        convertCurrencyRequest.getConvertedCurrencyType())))
                        .build());

        List<AccountEntity> willUpdateAccountList = new ArrayList<>();
        foreignAccount.setAccountBalance(foreignAccount.getAccountBalance().add(currencyAmount));
        willUpdateAccountList.add(foreignAccount);
        tlAccount.setAccountBalance(tlAccount.getAccountBalance()
                .add(convertCurrencyRequest.getConvertedAmount().negate()));
        willUpdateAccountList.add(tlAccount);
        accountRepository.saveAll(willUpdateAccountList);

        return OperationResult.newInstance(OperationResultCode.SUCCESS,
                BankingUtil.replaceMessage(InformationMessage.AMOUNT_RECEIVED.getErrorMessage(), currencyAmount + " " + convertCurrencyRequest.getConvertedCurrencyType()));
    }

    /*
     * request
     * response OperationResult
     * Bu method döviz hesabındaki parayı tl hesabına aktarır.
     */
    @Transactional
    public OperationResult takeTlCurrency(ConvertCurrencyRequest convertCurrencyRequest) {
        List<AccountEntity> accountEntityList = accountRepository.findByPersonId(convertCurrencyRequest.getPersonId());

        AccountEntity foreignAccount = accountEntityList.stream()
                .filter(accountEntity -> accountEntity.getAccountType().equals(convertCurrencyRequest.getConvertedCurrencyType()))
                .findAny()
                .orElseThrow(() -> OperationResultException.builder()
                        .operationResult(OperationResult.newInstance(OperationResultCode.ERROR,
                                BankingUtil.replaceMessage(ErrorMessageType.VALIDATE_NOT_FOUND_ACCOUNT_TYPE.getErrorMessage(),
                                        convertCurrencyRequest.getConvertedCurrencyType())))
                        .build());
        if (foreignAccount.getAccountBalance().compareTo(convertCurrencyRequest.getConvertedAmount()) < 0) {
            BankingUtil.throwException(OperationResult.newInstance(OperationResultCode.ERROR,
                    ErrorMessageType.VALIDATE_AMOUNT.getErrorMessage()));
        }
        CurrencyDto currentCurrencyDto = currencyService.getCurrentCurrencyDto(convertCurrencyRequest.getConvertedCurrencyType());
        BigDecimal convertedAmount = convertCurrencyRequest.getConvertedAmount().multiply(currentCurrencyDto.getAmount());
        foreignAccount.setAccountBalance(foreignAccount.getAccountBalance().add(convertCurrencyRequest.getConvertedAmount().negate()));
        AccountEntity tlAccount = accountEntityList.stream()
                .filter(accountEntity -> accountEntity.getAccountType().equals(AccountType.TL.getAccountType()))
                .findAny()
                .orElseThrow(() -> OperationResultException.builder()
                        .operationResult(OperationResult.newInstance(OperationResultCode.ERROR,
                                BankingUtil.replaceMessage(ErrorMessageType.VALIDATE_NOT_FOUND_ACCOUNT_TYPE.getErrorMessage(),
                                        AccountType.TL.getAccountType())))
                        .build());
        tlAccount.setAccountBalance(tlAccount.getAccountBalance().add(convertedAmount));
        List<AccountEntity> saveRecordList = new ArrayList<>();
        saveRecordList.add(foreignAccount);
        saveRecordList.add(tlAccount);
        accountRepository.saveAll(saveRecordList);

        return OperationResult.newInstance(OperationResultCode.SUCCESS,
                BankingUtil.replaceMessage(InformationMessage.AMOUNT_SELLING.getErrorMessage(),
                        convertCurrencyRequest.getConvertedCurrencyType(), convertedAmount.toString()));
    }

    @Transactional
    public OperationResult withdrawalFromTlAccount(MoneyUpdateRequest moneyUpdateRequest) {
        List<AccountEntity> accountEntityList = accountRepository.findByPersonId(moneyUpdateRequest.getPersonId());
        AccountEntity tlAccount = accountEntityList.stream()
                .filter(accountEntity -> accountEntity.getAccountType().equals(moneyUpdateRequest.getUpdateCurrencyType()))
                .findAny()
                .orElseThrow(() -> OperationResultException.builder()
                        .operationResult(OperationResult.newInstance(OperationResultCode.ERROR,
                                BankingUtil.replaceMessage(ErrorMessageType.VALIDATE_NOT_FOUND_ACCOUNT_TYPE.getErrorMessage(),
                                        AccountType.TL.getAccountType())))
                        .build());
        if (tlAccount.getAccountBalance().compareTo(moneyUpdateRequest.getUpdateAmount()) < 0) {
            BankingUtil.throwException(OperationResult.newInstance(OperationResultCode.ERROR,
                    ErrorMessageType.VALIDATE_AMOUNT.getErrorMessage()));
        }
        tlAccount.setAccountBalance(tlAccount.getAccountBalance().add(moneyUpdateRequest.getUpdateAmount().negate()));

        accountRepository.save(tlAccount);
        return OperationResult.newInstance(OperationResultCode.SUCCESS,
                BankingUtil.replaceMessage(InformationMessage.WITHDRAWAL_AMOUNT.getErrorMessage(),
                        AccountType.TL.getAccountType(), moneyUpdateRequest.getUpdateAmount().toString()));

        /*Kisinin hesplarini cek
        tl hesabini bul
        istenen miktari cek
        databasi guncelle
         *
         */

    }

    @Transactional
    public OperationResult addingMoney(MoneyUpdateRequest moneyUpdateRequest) {
        List<AccountEntity> accountEntityList = accountRepository.findByPersonId(moneyUpdateRequest.getPersonId());
        AccountEntity tlAccount = accountEntityList.stream()
                .filter(accountEntity -> accountEntity.getAccountType().equals(moneyUpdateRequest.getUpdateCurrencyType()))
                .findAny()
                .orElseThrow(() -> OperationResultException.builder()
                        .operationResult(OperationResult.newInstance(OperationResultCode.ERROR,
                                BankingUtil.replaceMessage(ErrorMessageType.VALIDATE_NOT_FOUND_ACCOUNT_TYPE.getErrorMessage(),
                                        AccountType.TL.getAccountType())))
                        .build());
        tlAccount.setAccountBalance(tlAccount.getAccountBalance().add(moneyUpdateRequest.getUpdateAmount().plus()));

        accountRepository.save(tlAccount);

        return OperationResult.newInstance(OperationResultCode.SUCCESS,
                BankingUtil.replaceMessage(InformationMessage.ADDING_AMOUNT.getErrorMessage(),
                        AccountType.TL.getAccountType(), moneyUpdateRequest.getUpdateAmount().toString()));


    }

}
