package com.bhatt.milan.kisanhubdemo.util;

import android.app.DownloadManager;
import android.net.Uri;
import android.os.Environment;

import java.util.ArrayList;
import java.util.List;

public class DownloadFileManager {

    private DownloadManager downloadManager;
    private long refID;
    public static List<Long> pendingList = new ArrayList<>();
    public static String type;

    public DownloadFileManager(DownloadManager downloadManager) {
        this.downloadManager = downloadManager;
    }

    public void downloadMultipleFiles(String[] links, String[] titles, String type) {

        if (links != null) {
            pendingList.clear();
            for (int i = 0; i < links.length; i++) {
                createDownloadRequest(links[i], titles[i]);
            }
            this.type = type;
        }
    }

    private void createDownloadRequest(String link, String title) {

        DownloadManager.Request downloadRequest = new DownloadManager.Request(Uri.parse(link));
        downloadRequest.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        downloadRequest.setAllowedOverRoaming(true);
        downloadRequest.setTitle(title);
        downloadRequest.setDescription("Downloading " + title + " file");
        downloadRequest.setVisibleInDownloadsUi(true);
        downloadRequest.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/KisanHub/" + title + ".txt");

        refID = downloadManager.enqueue(downloadRequest);
        pendingList.add(refID);
    }
}
