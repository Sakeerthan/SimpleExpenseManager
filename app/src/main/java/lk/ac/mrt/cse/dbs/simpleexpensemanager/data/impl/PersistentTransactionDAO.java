package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;
import android.database.Cursor;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.DBHelper;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

public class PersistentTransactionDAO implements TransactionDAO {
    private DBHelper db;

    public PersistentTransactionDAO(DBHelper db) {
        this.db = db;

    }


    @Override
    public List<Transaction> getAllTransactionLogs() throws ParseException {

        Cursor cur = db.getTransactions();
        List<Transaction> transList = new ArrayList<Transaction>();
        if(cur.getCount()==0){
            return transList;
        }
        while(cur.moveToNext()){
            ExpenseType type;
            String accNum=cur.getString(1);;
            String date1 = cur.getString(2);
            SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
            Date date =sdf.parse(date1);
            Double amount= cur.getDouble(3);
            String type1 = cur.getString(4);
            Log.d("myTag", type1);

            type = ExpenseType.valueOf(type1);
            Transaction t = new Transaction(date,accNum,type,amount);
            transList.add(t);

        }
        return transList;
    }
    
    @Override
    public void logTransaction(Date date, String accNum, ExpenseType exType, double amount) {
        DateFormat df = new SimpleDateFormat("dd-mm-yyyy");
        String dateStr = df.format(date);
        db.insertTransaction(dateStr,accNum,exType,amount);
    }


    @Override
    public List<Transaction> getPaginatedTransactionLogs(int lim) throws ParseException {

        Cursor cur = db.getLimitedTransactions(lim);
        List<Transaction> transList = new ArrayList<Transaction>();
        if(cur.getCount()==0){
            return transList;
        }
        while(cur.moveToNext()){
            ExpenseType type;
            String accNum=cur.getString(1);;
            String date1 = cur.getString(2);
            SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
            Date date =sdf.parse(date1);
            Double amount= cur.getDouble(3);
            String type1 = cur.getString(4);
            type = ExpenseType.valueOf(type1);
            Transaction t = new Transaction(date,accNum,type,amount);
            transList.add(t);

        }
        return transList;
       
    }
}
