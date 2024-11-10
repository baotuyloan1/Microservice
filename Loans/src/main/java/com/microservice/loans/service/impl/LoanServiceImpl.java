package com.microservice.loans.service.impl;

import com.microservice.loans.constants.LoanConstants;
import com.microservice.loans.dto.LoansDto;
import com.microservice.loans.entity.Loans;
import com.microservice.loans.exception.LoansAlreadyExistException;
import com.microservice.loans.exception.ResourceNotFoundException;
import com.microservice.loans.mapper.LoanMapper;
import com.microservice.loans.repository.LoanRepository;
import com.microservice.loans.service.ILoanService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class LoanServiceImpl implements ILoanService {

    private final LoanRepository loansRepository;

    /**
     * @param mobileNumber - Mobile Number of the Customer
     */
    @Override
    public void createLoan(String mobileNumber) {
        Optional<Loans> optionalLoan = loansRepository.findByMobileNumber(mobileNumber);
        if (optionalLoan.isPresent()) {
            throw new LoansAlreadyExistException("Loan already registered with give mobileNumber " + mobileNumber);
        }
        loansRepository.save(createNewLoan(mobileNumber));
    }

    /**
     * Create a new Loan
     * @param mobileNumber - Mobile Number of the Customer
     * @return the new loan details
     */
    private Loans createNewLoan(String mobileNumber) {
        Loans newLoans = new Loans();
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
        newLoans.setLoanNumber(Long.toString(randomLoanNumber));
        newLoans.setMobileNumber(mobileNumber);
        newLoans.setLoanType(LoanConstants.HOME_LOAN);
        newLoans.setTotalLoan(LoanConstants.NEW_LOAN_LIMIT);
        newLoans.setAmountPaid(0);
        newLoans.setOutstandingAmount(LoanConstants.NEW_LOAN_LIMIT);
        return newLoans;
    }

    /**
     * @param mobileNumber - Input mobile Number
     * @return Loan Details based on a given mobileNumber
     */
    @Override
    public LoansDto fetchLoan(String mobileNumber) {
        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber)
        );
        return LoanMapper.mapToLoansDto(loans, new LoansDto());
    }

    /**
     * @param loansDto - LoanDto Object
     * @return boolean indicating if the update of card details is successful or not
     */
    @Override
    public boolean updateLoan(LoansDto loansDto) {
        Loans loans = loansRepository.findByLoanNumber(loansDto.getLoanNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "LoanNumber", loansDto.getLoanNumber()));
        LoanMapper.mapToLoan(loansDto, loans);
        loansRepository.save(loans);
        return  true;
    }

    /**
     * @param mobileNumber - Input Mobile Number
     * @return boolean indicating if the delete of loan details is successful or not
     */
    @Override
    public boolean deleteLoan(String mobileNumber) {
        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber)
        );
        loansRepository.deleteById(loans.getLoanId());
        return true;
    }
}
