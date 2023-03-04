package org.example;

import java.io.File;
import com.opencsv.*;
import com.opencsv.bean.*;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.opencsv.exceptions.CsvValidationException;
import org.apache.commons.logging.Log;


import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.StringJoiner;

public class ClientLog {
     private Collection<MyLog> logs = new ArrayList<>();
   //  private  int id;

    public ClientLog(Collection<MyLog> logs)
    {this.logs = logs;}
    //метод для сохранения операций пользователя
    public void log(int productNum, int amount)
    {   MyLog myLog = new MyLog(productNum, amount);
        logs.add(myLog);
       // System.out.println(logs.);

    }

    public Collection<MyLog> getClientLog()
    {return logs;}
    // метод для сохранения всего журнала действия в файл в формате csv
    public void exportAsCSV(File txtFile)
    {
       // ClientLog clientLog = new ClientLog().
       // System.out.println(phoneBook);
        int j = 1;
        try(CSVWriter writer = new CSVWriter(new FileWriter(txtFile)))
        {
            for (MyLog myLog : logs)
            {
                StringJoiner logString = new StringJoiner(",")
                        .add(Integer.toString(j))
                        .add(Integer.toString(myLog.getProductNum()))
                        .add(Integer.toString(myLog.getAmount()));

                System.out.println(logString);

                String[] logArrayForCsv = logString.toString().split(",");

                writer.writeNext(logArrayForCsv);

                j++;
            }
        } catch (IOException e)
        {e.printStackTrace();}

    }
}
