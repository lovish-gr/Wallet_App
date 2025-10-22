package com.wallet.serviceI;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wallet.dto.request.TransactionDto;
import com.wallet.model.Account;
import com.wallet.model.Transaction;
import com.wallet.model.TransactionType;
import com.wallet.repo.AccountRepo;
import com.wallet.repo.TransactionRepo;
import com.wallet.service.TransactionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	TransactionRepo tr;
	
	private final AccountRepo ar;
	
	@Override
	public int transfer(TransactionDto tranReq) {
		// TODO Auto-generated method stub
		Account frmAcc = ar.findByAccountNumber(tranReq.fromAccountNumber);
		Account toAcc = ar.findByAccountNumber(tranReq.accountNumber);
		
		frmAcc.setOpeningBalance(frmAcc.getOpeningBalance()-tranReq.amount);
		toAcc.setOpeningBalance(toAcc.getOpeningBalance()+tranReq.amount);
		Transaction credTrans = Transaction.builder().amount(tranReq.amount).description(tranReq.description).frmaccfk(frmAcc).toaccfk(toAcc).transaction_date(LocalDate.now()).transaction_type(TransactionType.CREDIT).build();
		Transaction debTrans = Transaction.builder().amount(tranReq.amount).description(tranReq.description).frmaccfk(toAcc).toaccfk(frmAcc).transaction_date(LocalDate.now()).transaction_type(TransactionType.DEBIT).build();
		
		
		
		tr.save(debTrans);
		
		return tr.save(credTrans).getTransaction_id();
	}

	@Override
	public int withdraw(TransactionDto tranReq) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deposite(TransactionDto tranReq) {
		// TODO Auto-generated method stub
		return 0;
	}

}
