package com.bhatt.milan.kisanhubdemo.util;

import android.app.DownloadManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class HTMLParser {

    public static final String DOWNLOAD_UK = "UK";
    public static final String DOWNLOAD_ENGLAND = "England";
    public static final String DOWNLOAD_WALES = "Wales";
    public static final String DOWNLOAD_SCOTLAND = "Scotland";

    private static final String URL = "https://www.metoffice.gov.uk/climate/uk/summaries/datasets#yearOrdered";
    public static Document htmlDocument;

    public static void queueFilesForDownload(String type, DownloadManager downloadManager) {
        if (htmlDocument != null) {
            String[] downloadLinks = new String[5];
            String[] titles = new String[5];
            Element yearOrderedHeaderElement = htmlDocument.getElementById("yearOrdered").parent();
            Element yearOrderedTableElement = yearOrderedHeaderElement.nextElementSibling();
            Elements trElements = yearOrderedTableElement.select("tr");
            for (Element tr : trElements) {
                Elements tdElements = tr.select("td");
                if (tdElements != null && tdElements.size() > 0 && tdElements.first().select("strong").html().equalsIgnoreCase(type)) {
                    for (int i = 1; i < 6; i++) {
                        Elements hrefElements = tdElements.get(i).select("a[href]");
                        downloadLinks[i - 1] = hrefElements.attr("href");
                        titles[i - 1] = hrefElements.attr("title");
                    }
                    new DownloadFileManager(downloadManager).downloadMultipleFiles(downloadLinks, titles, type);
                }
            }
        }
    }

    public static void FetchHTMLFromURL() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    htmlDocument = Jsoup.connect(URL).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
