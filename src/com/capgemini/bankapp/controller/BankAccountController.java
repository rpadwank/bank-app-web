package com.capgemini.bankapp.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.capgemini.bankapp.exception.AccountNotFoundException;
import com.capgemini.bankapp.exception.LowBalanceException;
import com.capgemini.bankapp.model.BankAccount;
import com.capgemini.bankapp.service.BankAccountService;
import com.capgemini.bankapp.service.impl.BankAccountServiceImpl;

@WebServlet(urlPatterns = { "*.do" }, loadOnStartup = 1)
public class BankAccountController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BankAccountService bankService;
	private static final Logger logger = Logger.getLogger(BankAccountController.class);

	public BankAccountController() {
		bankService = new BankAccountServiceImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getServletPath();
		RequestDispatcher dispatcher = null;
		response.setContentType("text/html");
		if (path.equals("/DisplayAllAccountDetails.do")) {
			List<BankAccount> accounts = bankService.findAllBankAccounts();
			request.setAttribute("accountsList", accounts);
			dispatcher = request.getRequestDispatcher("/displayTable.jsp");
			dispatcher.forward(request, response);
			
		}
		else if(path.equals("/displayAccount.do"))
		{
			long accountId = Long.parseLong(request.getParameter("AccNo"));
				try {
					BankAccount account = bankService.searchAccount(accountId);
					request.setAttribute("accountDetails", account);
					dispatcher = request.getRequestDispatcher("/displayAccountDetails.jsp");
					dispatcher.forward(request, response);
				} catch (AccountNotFoundException e) {
					dispatcher = request.getRequestDispatcher("/displayAccountNotFoundError.jsp");
					dispatcher.forward(request, response);
				}
			
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		String path = request.getServletPath();
		System.out.println(path);

		if (path.equals("/addNewBankAccount.do")) {
			String accountHolderName = request.getParameter("name1");
			String accountType = request.getParameter("account_type");
			double balance = Double.parseDouble(request.getParameter("account_balance"));

			BankAccount account = new BankAccount(accountHolderName, accountType, balance);
			if (bankService.addNewBankAccount(account)) {
				out.println("<h2>BankAccount is created successfully...</h2>");
				out.println("<h2> <a href = 'index.html'>|Home|</h2>");
				out.close();
			}
		} else if (path.equals("/withdraw.do")) {
			long accountId = Long.parseLong(request.getParameter("AccNo"));
			double amount = Double.parseDouble(request.getParameter("Amt"));

			try {
				double balance = bankService.withdraw(accountId, amount);
				out.println("<h2>Withdraw successful...</h2>");
				out.println("<h2>Remaining balance = " + balance);
			} catch (LowBalanceException e) {
				// TODO Auto-generated catch block
				out.println("<h2>You have insufficient fund...</h2>");
			} catch (AccountNotFoundException e) {
				out.println("<h2>Account doesn't exist...</h2>");
			} finally {
				out.close();
			}

		}

		else if (path.equals("/deposit.do")) {
			long accountId = Long.parseLong(request.getParameter("AccNo"));
			double amount = Double.parseDouble(request.getParameter("Amt"));
			double balance;
			try {
				balance = bankService.deposit(accountId, amount);
				out.println("<h2>Deposit Successful...</h2>");
				out.println("<h2>updated balance = " + balance);
			} catch (AccountNotFoundException e) {
				out.println("<h2>Account doesn't exist...</h2>");
			} finally {
				out.close();
			}

		}

		else if (path.equals("/checkBalance.do")) {
			long accountId = Long.parseLong(request.getParameter("AccNo"));
			double balance;
			try {
				balance = bankService.checkBalance(accountId);
				out.println("<h2>Updated Balance = " + balance);
			} catch (AccountNotFoundException e) {
				out.println("<h2>Account doesn't exist...</h2>");
			} finally {
				out.close();
			}

		}

		else if (path.equals("/fundTransfer.do")) {
			long senderId = Long.parseLong(request.getParameter("FAccNo"));
			long receiverId = Long.parseLong(request.getParameter("TAccNo"));
			double amount = Double.parseDouble(request.getParameter("Amt"));
			double balance;
			try {
				balance = bankService.fundTransfer(senderId, receiverId, amount);
				out.println("<h2>Updated Balance = " + balance);
			} catch (AccountNotFoundException e) {
				out.println("<h2>Account doesn't exist...</h2>");
			} catch (LowBalanceException e) {
				out.println("<h2>You have insufficient fund...</h2>");
			} finally {
				out.close();
			}

		}

		else if (path.equals("/deleteAccount.do")) {
			long accountId = Long.parseLong(request.getParameter("AccNo"));
			try {
				bankService.deleteBankAccount(accountId);
				out.println("<h2>Account deleted successfully....");
			} catch (AccountNotFoundException e) {
				out.println("<h2>Account doesn't exist...</h2>");
			} finally {
				out.close();
			}

		}

	}

}
