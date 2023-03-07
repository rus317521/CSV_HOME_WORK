package org.example;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.StringJoiner;

public class ClientLog {
    private Collection<MyLog> logs = new ArrayList<>();

    public ClientLog() {
    }

    public ClientLog(Collection<MyLog> logs) {
        this.logs = logs;
    }

    //метод для сохранения операций пользователя
    public void log(int productNum, int amount) {
        MyLog myLog = new MyLog(productNum, amount);
        logs.add(myLog);

    }

    public Collection<MyLog> getClientLog() {
        return logs;
    }

    // метод для сохранения всего журнала действия в файл в формате csv
    public void exportAsCSV(File txtFile) {

        try (CSVWriter writer = new CSVWriter(new FileWriter(txtFile, true))) {
            if (txtFile.length() == 0) {
                String strArr[] = {"productNum", "amount"};
                writer.writeNext(strArr);
            }
            for (MyLog myLog : logs) {
                StringJoiner logString = new StringJoiner(",")

                        .add(Integer.toString(myLog.getProductNum()))
                        .add(Integer.toString(myLog.getAmount()));

                System.out.println(logString);

                String[] logArrayForCsv = logString.toString().split(",");
                writer.writeNext(logArrayForCsv);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
