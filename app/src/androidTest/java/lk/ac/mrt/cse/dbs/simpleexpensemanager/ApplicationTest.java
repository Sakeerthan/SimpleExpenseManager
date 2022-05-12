/*
 * Copyright 2015 Department of Computer Science and Engineering, University of Moratuwa.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *                  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package lk.ac.mrt.cse.dbs.simpleexpensemanager;


import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.assertEquals;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import org.junit.BeforeClass;
import org.junit.Test;

import java.text.ParseException;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.ExpenseManager;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.PersistentExpenseManager;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.exception.ExpenseManagerException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;

public class ApplicationTest  {
    private static ExpenseManager testExpenceManager;


    @BeforeClass
    public static void setupClient() {
        Context cont = ApplicationProvider.getApplicationContext();
        testExpenceManager = new PersistentExpenseManager(cont);
    }

    @Test
    public void testAddAccount(){
        testExpenceManager.addAccount("8002999", "Commercial Bank", "Sakeerthan", 1040.0);
        assertTrue(testExpenceManager.getAccountNumbersList().contains("8002999"));

    }


    @Test
    public void testAddExpence(){
          try{
            int sizeOfLogsArr = testExpenceManager.getTransactionLogs().size();
            testExpenceManager.updateAccountBalance("12345A",10, 5, 2022, ExpenseType.EXPENSE, "500");
            assertEquals(testExpenceManager.getTransactionLogs().size() , sizeOfLogsArr + 1);
        }catch (InvalidAccountException | ParseException e){

        }

    }
}