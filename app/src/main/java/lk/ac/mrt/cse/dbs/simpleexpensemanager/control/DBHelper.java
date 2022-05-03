package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.method.NumberKeyListener;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "190541R.db";
    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "CREATE table ACCOUNT (acc_no VARCHAR(20) PRIMARY KEY, bank VARCHAR(20), owner varchar(20), balance DECIMAL(10,2))"
        );
        db.execSQL(
                "CREATE table Trans (tr_id INTEGER PRIMARY KEY AUTOINCREMENT , acc_no VARCHAR(20) , date VARCHAR(20), amount  DECIMAL(10,2), type VARCHAR(20) )"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS ACCOUNT");
        db.execSQL("DROP TABLE IF EXISTS Trans");
        onCreate(db);
    }

    public Cursor getAccount(String acc_no){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM ACCOUNT WHERE acc_no = ?", new String[]{acc_no});
        cur.moveToFirst();
        return cur;
    }

    public Cursor getAccounts(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM ACCOUNT", null);
        return cur;
    }


    public boolean addAccount (String accNum, String bankName, String accHolderName, double initialBal) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentVals = new ContentValues();
        contentVals.put("acc_no", accNum);
        contentVals.put("bank", bankName);
        contentVals.put("owner", accHolderName);
        contentVals.put("balance", initialBal);
        Cursor cur = db.rawQuery("SELECT * FROM ACCOUNT WHERE acc_no = ?", new String[]{accNum});

        if (cur.getCount() == 0) {
            long output = db.insert("ACCOUNT", null, contentVals);
            if(output==-1){
                return false;
            }
            return true;
        }
        return false;
    }

    public boolean updateBalance(String accNum,  double amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentVals = new ContentValues();
        contentVals.put("balance", amount);
        Cursor cur = db.rawQuery("SELECT * FROM ACCOUNT WHERE acc_no = ?", new String[]{accNum});

        if (cur.getCount() > 0) {
            long output = db.update("ACCOUNT", contentVals, "acc_no=?", new String[]{accNum});
            if (output == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public boolean deleteAcc(String accNum) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM ACCOUNT WHERE acc_no = ?", new String[]{accNum});
        if (cur.getCount() > 0) {
            long output = db.delete("ACCOUNT", "acc_no=?", new String[]{accNum});
            if (output == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Cursor getAccountNum(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery("SELECT acc_no FROM ACCOUNT", null);
        return cur;
    }


    public boolean insertTransaction(String date, String accNum, ExpenseType expenseType, double amount){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentVals = new ContentValues();
        contentVals.put("acc_no", accNum);
        contentVals.put("date", date);
        contentVals.put("amount", amount );
        contentVals.put("type", expenseType.toString());
        long output = db.insert("Trans",null,contentVals);
        if(output==-1){
            return false;
        }
        return true;
    }

    public Cursor getTransactions(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM Trans", null);
        return cur;
    }
    public Cursor getLimitedTransactions(int limit){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM Trans ORDER BY tr_id DESC LIMIT " + Integer.toString(limit) , null);
        return cur;
    }


}
