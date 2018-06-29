package com.bhatt.milan.kisanhubdemo;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.bhatt.milan.kisanhubdemo.adapter.TemperatureAdapter;
import com.bhatt.milan.kisanhubdemo.model.Temperature;
import com.bhatt.milan.kisanhubdemo.util.DownloadFileManager;
import com.bhatt.milan.kisanhubdemo.util.HTMLParser;
import com.bhatt.milan.kisanhubdemo.util.PreferenceManager;
import com.bhatt.milan.kisanhubdemo.util.SimpleDividerItemDecoration;
import com.bhatt.milan.kisanhubdemo.database.TemperatureDAO;
import com.bhatt.milan.kisanhubdemo.util.TextParser;

import java.util.ArrayList;
import java.util.List;

public class TemperatureFragment extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener, View.OnClickListener {

    public static final int PERMISSION_REQUEST = 1;

    private Spinner regionSpinner;
    private Spinner parameterSpinner;
    private Spinner filterSpinner;

    private Button downloadData;
    private RecyclerView temperatureListView;

    private List<String> regionList;
    private List<String> parameterList;
    private List<String> filterList;

    private String selectedRegion;
    private String selectedParameter;
    private String selectedFilter;

    private TemperatureDAO temperatureDAO;
    private DownloadManager downloadManager;
    private boolean isPermissionApproved;

    private boolean isScrolling = true;
    private LinearLayoutManager layoutManager;
    private int limit = 10;

    private TemperatureAdapter temperatureAdapter;

    public interface OnDownloadCompleteListener {
        void onDownloadComplete();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.temp_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        regionSpinner = view.findViewById(R.id.region);
        parameterSpinner = view.findViewById(R.id.parameter);
        filterSpinner = view.findViewById(R.id.filter);

        downloadData = view.findViewById(R.id.downloadData);
        temperatureListView = view.findViewById(R.id.tempList);
        temperatureListView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int currentItems = layoutManager.getChildCount();
                int totalItems = layoutManager.getItemCount();
                int scrolledItems = layoutManager.findFirstVisibleItemPosition();

                if (isScrolling = (currentItems + scrolledItems == totalItems)) {
                    isScrolling = false;
                    limit += 10;
                    displayData();
                }
            }
        });

        downloadData.setOnClickListener(this);

        regionSpinner.setOnItemSelectedListener(this);
        parameterSpinner.setOnItemSelectedListener(this);
        filterSpinner.setOnItemSelectedListener(this);

        addItemInSpinner();
        init(view.getContext());
    }

    private void init(Context context) {
        temperatureDAO = new TemperatureDAO(context);
        temperatureDAO.open();
        HTMLParser.FetchHTMLFromURL();

        downloadManager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);

        getActivity().registerReceiver(downloadCompleteReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

        isPermissionApproved = checkForPermission();
    }

    private boolean checkForPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    displayAlert(getString(R.string.permission_alert_title), getString(R.string.permission_alert_description));
                    return false;
                } else {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST);
                    return false;
                }
            }
        }

        return true;
    }

    private void displayAlert(String title, String description) {
        new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(description)
                .setPositiveButton(getString(R.string.button_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST);
                    }
                })
                .setNegativeButton(getString(R.string.button_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            isPermissionApproved = true;
        }
    }

    private void addItemInSpinner() {

        regionList = new ArrayList<>();
        regionList.add(getString(R.string.uk_text));
        regionList.add(getString(R.string.england_text));
        regionList.add(getString(R.string.wales_text));
        regionList.add(getString(R.string.scotland_text));

        regionSpinner.setAdapter(getAdapter(regionList));
        regionSpinner.setSelection(0);

        parameterList = new ArrayList<>();
        parameterList.add(getString(R.string.max_temp));
        parameterList.add(getString(R.string.min_temp));
        parameterList.add(getString(R.string.mean_temp));
        parameterList.add(getString(R.string.sunshine_temp));
        parameterList.add(getString(R.string.rainfall_temp));

        parameterSpinner.setAdapter(getAdapter(parameterList));
        parameterSpinner.setSelection(0);

        filterList = new ArrayList<>();
        filterList.add(getString(R.string.winter));
        filterList.add(getString(R.string.spring));
        filterList.add(getString(R.string.summer));
        filterList.add(getString(R.string.autumn));
        filterList.add(getString(R.string.annual));

        filterSpinner.setAdapter(getAdapter(filterList));
        filterSpinner.setSelection(0);
    }

    private ArrayAdapter<String> getAdapter(List<String> list) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent == regionSpinner) {
            selectedRegion = regionList.get(position);
            displayData();
        } else if (parent == parameterSpinner) {
            selectedParameter = parameterList.get(position);
            displayData();
        } else if (parent == filterSpinner) {
            selectedFilter = filterList.get(position);
            displayData();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void displayData() {
        if (!TextUtils.isEmpty(selectedRegion) && !TextUtils.isEmpty(selectedParameter) && !TextUtils.isEmpty(selectedFilter)) {
            List<Temperature> temperatureList = temperatureDAO.getTemperatureBaseOnAllCriteria(selectedRegion, selectedParameter, limit);
            if (!temperatureList.isEmpty()) {
                downloadData.setVisibility(View.GONE);
                setList(temperatureList, selectedFilter);
            } else {
                downloadData.setText(getString(R.string.button_download,selectedRegion));
                downloadData.setVisibility(View.VISIBLE);
                temperatureListView.setVisibility(View.GONE);
            }
        }
    }

    private void setList(List<Temperature> temperatureList, String filter) {
        if (temperatureAdapter == null) {
            temperatureAdapter = new TemperatureAdapter(temperatureList, filter);
            layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            temperatureListView.setLayoutManager(layoutManager);
            temperatureListView.setAdapter(temperatureAdapter);
            temperatureListView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
            temperatureListView.setVisibility(View.VISIBLE);
        } else {
            temperatureAdapter.updateData(temperatureList, filter);
            temperatureListView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        if (isPermissionApproved) {
            if (selectedRegion.equalsIgnoreCase(getString(R.string.uk_text))) {
                HTMLParser.queueFilesForDownload(HTMLParser.DOWNLOAD_UK, downloadManager);
            } else if (selectedRegion.equalsIgnoreCase(getString(R.string.england_text))) {
                HTMLParser.queueFilesForDownload(HTMLParser.DOWNLOAD_ENGLAND, downloadManager);
            } else if (selectedRegion.equalsIgnoreCase(getString(R.string.wales_text))) {
                HTMLParser.queueFilesForDownload(HTMLParser.DOWNLOAD_WALES, downloadManager);
            } else if (selectedRegion.equalsIgnoreCase(getString(R.string.scotland_text))) {
                HTMLParser.queueFilesForDownload(HTMLParser.DOWNLOAD_SCOTLAND, downloadManager);
            }
        } else {
            Toast.makeText(getActivity(), getString(R.string.permission_denied), Toast.LENGTH_SHORT).show();
        }
    }

    BroadcastReceiver downloadCompleteReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            long refID = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);

            DownloadFileManager.pendingList.remove(refID);

            if (DownloadFileManager.pendingList.isEmpty()) {
                if (DownloadFileManager.type.equalsIgnoreCase(getString(R.string.uk_text))) {
//                    new PreferenceManager(getActivity()).saveBooleanPreference(PreferenceManager.PREFERENCE_UK_SAVED, true);
                    new TextParser(getActivity(), onDownloadCompleteListener).parseUKFiles();
                } else if (DownloadFileManager.type.equalsIgnoreCase(getString(R.string.england_text))) {
//                    new PreferenceManager(getActivity()).saveBooleanPreference(PreferenceManager.PREFERENCE_ENGLAND_SAVED, true);
                    new TextParser(getActivity(), onDownloadCompleteListener).parseEnglandFiles();
                } else if (DownloadFileManager.type.equalsIgnoreCase(getString(R.string.wales_text))) {
//                    new PreferenceManager(getActivity()).saveBooleanPreference(PreferenceManager.PREFERENCE_WALES_SAVED, true);
                    new TextParser(getActivity(), onDownloadCompleteListener).parseWalesFiles();
                } else if (DownloadFileManager.type.equalsIgnoreCase(getString(R.string.scotland_text))) {
//                    new PreferenceManager(getActivity()).saveBooleanPreference(PreferenceManager.PREFERENCE_SCOTLAND_SAVED, true);
                    new TextParser(getActivity(), onDownloadCompleteListener).parseScotlandFiles();
                }
            }
        }
    };

    OnDownloadCompleteListener onDownloadCompleteListener = new OnDownloadCompleteListener() {
        @Override
        public void onDownloadComplete() {
            displayData();
        }
    };

    @Override
    public void onStop() {
        super.onStop();
        temperatureDAO.close();
    }
}
