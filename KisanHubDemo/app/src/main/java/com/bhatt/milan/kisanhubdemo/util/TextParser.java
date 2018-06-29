package com.bhatt.milan.kisanhubdemo.util;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.bhatt.milan.kisanhubdemo.TemperatureFragment;
import com.bhatt.milan.kisanhubdemo.database.TemperatureDAO;
import com.bhatt.milan.kisanhubdemo.model.Temperature;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TextParser {

    private TemperatureDAO temperatureDAO;
    private static int counter = 0;
    private static int arrayIndex = 0;
    private String[] dbvalues = new String[18];
    private TemperatureFragment.OnDownloadCompleteListener onDownloadCompleteListener;

    public TextParser(Context context, TemperatureFragment.OnDownloadCompleteListener onDownloadCompleteListener) {
        temperatureDAO = new TemperatureDAO(context);
        temperatureDAO.open();
        this.onDownloadCompleteListener = onDownloadCompleteListener;
    }

    public void parseUKFiles() {

        parseFile(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "/KisanHub/UK Date Tmax.txt"), "UK", "Tmax");
        parseFile(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "/KisanHub/UK Date Tmin.txt"), "UK", "Tmin");
        parseFile(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "/KisanHub/UK Date Tmean.txt"), "UK", "Tmean");
        parseFile(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "/KisanHub/UK Date Sunshine.txt"), "UK", "Sunshine");
        parseFile(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "/KisanHub/UK Date Rainfall.txt"), "UK", "Rainfall");
        onDownloadCompleteListener.onDownloadComplete();
        temperatureDAO.close();
    }

    public void parseEnglandFiles() {

        parseFile(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "/KisanHub/England Date Tmax.txt"), "England", "Tmax");
        parseFile(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "/KisanHub/England Date Tmin.txt"), "England", "Tmin");
        parseFile(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "/KisanHub/England Date Tmean.txt"), "England", "Tmean");
        parseFile(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "/KisanHub/England Date Sunshine.txt"), "England", "Sunshine");
        parseFile(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "/KisanHub/England Date Rainfall.txt"), "England", "Rainfall");
        onDownloadCompleteListener.onDownloadComplete();
        temperatureDAO.close();
    }

    public void parseWalesFiles() {

        parseFile(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "/KisanHub/Wales Date Tmax.txt"), "Wales", "Tmax");
        parseFile(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "/KisanHub/Wales Date Tmin.txt"), "Wales", "Tmin");
        parseFile(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "/KisanHub/Wales Date Tmean.txt"), "Wales", "Tmean");
        parseFile(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "/KisanHub/Wales Date Sunshine.txt"), "Wales", "Sunshine");
        parseFile(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "/KisanHub/Wales Date Rainfall.txt"), "Wales", "Rainfall");
        onDownloadCompleteListener.onDownloadComplete();
        temperatureDAO.close();
    }

    public void parseScotlandFiles() {

        parseFile(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "/KisanHub/Scotland Date Tmax.txt"), "Scotland", "Tmax");
        parseFile(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "/KisanHub/Scotland Date Tmin.txt"), "Scotland", "Tmin");
        parseFile(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "/KisanHub/Scotland Date Tmean.txt"), "Scotland", "Tmean");
        parseFile(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "/KisanHub/Scotland Date Sunshine.txt"), "Scotland", "Sunshine");
        parseFile(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "/KisanHub/Scotland Date Rainfall.txt"), "Scotland", "Rainfall");
        onDownloadCompleteListener.onDownloadComplete();
        temperatureDAO.close();
    }

    private void parseFile(File file, String country, String type) {
        if (file.exists()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                Scanner scanner = new Scanner(fileInputStream, "UTF-8");
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] words = line.split(" ");
                    if (!TextUtils.isEmpty(words[0]) && TextUtils.isDigitsOnly(words[0])) {
                        counter = 0;
                        arrayIndex = 0;
                        createArrayFromWords(words);
                        saveToDatabase(dbvalues, country, type);
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void createArrayFromWords(String[] words) {
        try {
            if (TextUtils.isEmpty(words[counter])) {
                counter++;
                createArrayFromWords(words);
            }
            if (words[counter].equals("---")) {
                counter++;
                dbvalues[arrayIndex] = "N/A";
                arrayIndex++;
                createArrayFromWords(words);
            }

            dbvalues[arrayIndex] = words[counter];
            counter++;
            arrayIndex++;
            createArrayFromWords(words);
        } catch (ArrayIndexOutOfBoundsException e) {
        }
    }

    private void saveToDatabase(String[] words, String country, String type) {
        Temperature temperature = new Temperature();
        temperature.setCountry(country);
        temperature.setTempType(type);
        temperature.setYear(dbvalues[0]);
        temperature.setJanuary(dbvalues[1]);
        temperature.setFebruary(dbvalues[2]);
        temperature.setMarch(dbvalues[3]);
        temperature.setApril(dbvalues[4]);
        temperature.setMay(dbvalues[5]);
        temperature.setJune(dbvalues[6]);
        temperature.setJuly(dbvalues[7]);
        temperature.setAugust(dbvalues[8]);
        temperature.setSeptember(dbvalues[9]);
        temperature.setOctober(dbvalues[10]);
        temperature.setNovember(dbvalues[11]);
        temperature.setDecember(dbvalues[12]);
        temperature.setWinter(dbvalues[13]);
        temperature.setSpring(dbvalues[14]);
        temperature.setSummer(dbvalues[15]);
        temperature.setAutumn(dbvalues[16]);
        temperature.setAnnual(dbvalues[17]);

        temperatureDAO.addTemperature(temperature);
    }
}
