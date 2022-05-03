package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.DBHelper;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;

/**
 * This is an In-Memory implementation of the AccountDAO interface. This is not a persistent storage. A HashMap is
 * used to store the account details temporarily in the memory.
 */

public class PersistentAccountDAO implements AccountDAO{

    private final DBHelper db;
    public PersistentAccountDAO(DBHelper db) {

        this.db = db;
    }

    @Override
    public List<String> getAccountNumbersList() {
        Cursor cur = db.getAccountNum();
        List<String> accList = new ArrayList<String>();
        if(cur.getCount()==0){
            return accList;
        }
        while(cur.moveToNext()){
            String accNum = cur.getString(0);
            accList.add(accNum);

        }
        return accList;
    }


    @Override
    public void addAccount(Account account) {
        String accNum =   account.getAccountNo();
        String accHolderName = account.getAccountHolderName();
        String bankName = account.getBankName();
        Double balance = account.getBalance();
        db.addAccount(accNum,bankName,accHolderName,balance);

    }

    @Override
    public void removeAccount(String accNum) throws InvalidAccountException {

        db.deleteAcc(accNum);
    }

    @Override
    public List<Account> getAccountsList() {

        Cursor cur = db.getAccounts();
        List<Account> accList = new ArrayList<Account>();
        if(cur.getCount()==0){
            return accList;
        }
        while(cur.moveToNext()){
            String accNum = cur.getString(0);
            String bankName=cur.getString(1);;
            String accHolerName= cur.getString(2);;
            Double balance = cur.getDouble(3);
            Account account = new Account(accNum,bankName,accHolerName,balance);
            accList.add(account);

        }
        return accList;
    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {
        Cursor cur = db.getAccount(accountNo);
        if (cur.getCount()>0) {
            String accNum = cur.getString(0);
            String bankName=cur.getString(1);;
            String accHolerName= cur.getString(2);;
            Double balance = cur.getDouble(3);
            Account account = new Account(accNum,bankName,accHolerName,balance);
            return account;
        }
        String invalidMsg = accountNo + " is invalid account.";
        throw new InvalidAccountException(invalidMsg);
    }

    @Override
    public void updateBalance(String accNum, ExpenseType expenseType, double amount) throws InvalidAccountException {



        Account account = getAccount(accNum);
        if(!(account instanceof Account)){
            String invalidMsg = accNum + " is invalid account";
            throw new InvalidAccountException(invalidMsg);

        }


        double bal = account.getBalance();
        switch (expenseType) {
            case INCOME:
                bal = account.getBalance() + amount;
                account.setBalance(bal);
                break;
            case EXPENSE:
                bal = account.getBalance() - amount;
                account.setBalance(bal);
                break;
        }

        db.updateBalance(accNum,bal);
    }
}
