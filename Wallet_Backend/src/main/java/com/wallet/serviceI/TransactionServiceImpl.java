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


@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	TransactionRepo tr;

	@Autowired
	AccountRepo ar;

	@Override
	public int transfer(TransactionDto tranReq) {
		// TODO Auto-generated method stub
		Account frmAcc = ar.findByAccountNumber(tranReq.getFromAccountNumber());
		Account toAcc = ar.findByAccountNumber(tranReq.getAccountNumber());

		frmAcc.setOpeningBalance(frmAcc.getOpeningBalance() - tranReq.getAmount());
		toAcc.setOpeningBalance(toAcc.getOpeningBalance() + tranReq.getAmount());
		Transaction credTrans = Transaction.builder().amount(tranReq.getAmount()).description(tranReq.getDescription())
				.frmaccfk(frmAcc).toaccfk(toAcc).transaction_date(LocalDate.now())
				.transaction_type(TransactionType.CREDIT).build();
		Transaction debTrans = Transaction.builder().amount(tranReq.getAmount()).description(tranReq.getDescription())
				.frmaccfk(toAcc).toaccfk(frmAcc).transaction_date(LocalDate.now())
				.transaction_type(TransactionType.DEBIT).build();

		tr.save(debTrans);

		return tr.save(credTrans).getTransaction_id();
	}

	@Override
	public int withdraw(TransactionDto tranReq) {
		// TODO Auto-generated method stub
		
		Account frmAcc = ar.findByAccountNumber(tranReq.fromAccountNumber);
		
		frmAcc.setOpeningBalance(frmAcc.getOpeningBalance()-tranReq.getAmount());
		Transaction credTrans = Transaction.builder().amount(tranReq.getAmount()).description(tranReq.getDescription())
				.frmaccfk(frmAcc).toaccfk(null).transaction_date(LocalDate.now())
				.transaction_type(TransactionType.CREDIT).build();
		return tr.save(credTrans).getTransaction_id();
	}

	@Override
	public int deposite(TransactionDto tranReq) {
		// TODO Auto-generated method stub
		Account frmAcc = ar.findByAccountNumber(tranReq.fromAccountNumber);
		
		frmAcc.setOpeningBalance(frmAcc.getOpeningBalance()+tranReq.getAmount());
		Transaction credTrans = Transaction.builder().amount(tranReq.getAmount()).description(tranReq.getDescription())
				.frmaccfk(frmAcc).toaccfk(null).transaction_date(LocalDate.now())
				.transaction_type(TransactionType.DEBIT).build();
		return tr.save(credTrans).getTransaction_id();
	}

}
