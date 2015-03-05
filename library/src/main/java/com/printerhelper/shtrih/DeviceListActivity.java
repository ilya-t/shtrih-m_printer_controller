package com.printerhelper.shtrih;

/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Set;

/**
 * This Activity appears as a dialog. It lists any paired devices and devices
 * detected in the area after discovery. When a device is chosen by the user,
 * the MAC address of the device is sent back to the parent Activity in the
 * result Intent.
 */
public class DeviceListActivity extends Activity {
	// Debugging
	private static final String TAG = "DeviceListActivity";
	private static final boolean D = false;

	public static final int REQUEST_CONNECT_BT_DEVICE = 481;
    // Return Intent extra
    public static final java.lang.String EXTRA_DEVICE_NAME = "device_name";
	public static String EXTRA_DEVICE_ADDRESS = "device_address";
	// public static String DEFAULT_ADDRESS = "";
	// Member fields
	private BluetoothAdapter mBtAdapter;
	private ArrayAdapter<String> mPairedDevicesArrayAdapter;
	private ArrayAdapter<String> mNewDevicesArrayAdapter;
    private HashMap<String, Pair<String, String>> deviceMap = new HashMap<>();
    private Button scanButton;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Setup the window
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.device_list);
		// loadSettings();
		// Set result CANCELED incase the user backs out
		setResult(Activity.RESULT_CANCELED);

		// Initialize the button to perform device discovery
		scanButton = (Button) findViewById(R.id.button_scan);

		scanButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				doDiscovery();
                v.setEnabled(false);
//				v.setVisibility(View.GONE);
			}
		});

		// Initialize array adapters. One for already paired devices and
		// one for newly discovered devices
		mPairedDevicesArrayAdapter = new ArrayAdapter<>(this,
				R.layout.device_name);
		mNewDevicesArrayAdapter = new ArrayAdapter<>(this,
				R.layout.device_name);

		// Find and set up the ListView for paired devices
		ListView pairedListView = (ListView) findViewById(R.id.paired_devices);
		pairedListView.setAdapter(mPairedDevicesArrayAdapter);
		pairedListView.setOnItemClickListener(mDeviceClickListener);

		// Find and set up the ListView for newly discovered devices
		ListView newDevicesListView = (ListView) findViewById(R.id.new_devices);
		newDevicesListView.setAdapter(mNewDevicesArrayAdapter);
		newDevicesListView.setOnItemClickListener(mDeviceClickListener);

		// Register for broadcasts when a device is discovered
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		this.registerReceiver(mReceiver, filter);

		// Register for broadcasts when discovery has finished
		filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		this.registerReceiver(mReceiver, filter);

		// Get the local Bluetooth adapter
		mBtAdapter = BluetoothAdapter.getDefaultAdapter();
		if (mBtAdapter == null) {
			return;
		}

		 // Get a set of currently paired devices Set<BluetoothDevice>
//        mPairedDevicesArrayAdapter.addAll();
        Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            findViewById(R.id.title_paired_devices).setVisibility(View.VISIBLE);
            for (BluetoothDevice device : pairedDevices) {
                String title = device.getName() + "\n" +
                        device.getAddress();
                mPairedDevicesArrayAdapter.add(title);
                deviceMap.put(title, new Pair<>(device.getName(), device.getAddress()));
            }
        } else {
            String noDevices =
                    getResources().getText(R.string.none_paired).toString();
            mPairedDevicesArrayAdapter.add(noDevices);
        }

    }

	@Override
	protected void onDestroy() {
		super.onDestroy();

		// Make sure we're not doing discovery anymore
		if (mBtAdapter != null) {
			mBtAdapter.cancelDiscovery();
		}

		// Unregister broadcast listeners
		this.unregisterReceiver(mReceiver);
		// saveSettings();
	}

	/**
	 * Start device discover with the BluetoothAdapter
	 */
	private void doDiscovery() {
		if (D)
			Log.d(TAG, "doDiscovery()");

		// Indicate scanning in the title
		setProgressBarIndeterminateVisibility(true);
		setTitle(R.string.scanning);

		// Turn on sub-title for new devices
		findViewById(R.id.title_new_devices).setVisibility(View.VISIBLE);

		// If we're already discovering, stop it
		if (mBtAdapter.isDiscovering()) {
			mBtAdapter.cancelDiscovery();
		}
        mNewDevicesArrayAdapter.clear();

		// Request discover from BluetoothAdapter
		mBtAdapter.startDiscovery();
	}

	// The on-click listener for all devices in the ListViews
	private final OnItemClickListener mDeviceClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
			// Cancel discovery because it's costly and we're about to connect
			if (((TextView) v).getText().equals(
					getResources().getText(R.string.none_paired).toString())) {
				return;
			}
			mBtAdapter.cancelDiscovery();

			// Get the device MAC address, which is the last 17 chars in the
			// View
			String title = ((TextView) v).getText().toString();
			if (title.equals(getResources().getText(R.string.none_paired)) || title.equals(getResources()
							.getString(R.string.none_found)) || !deviceMap.containsKey(title)) {
				return;
			}

            Pair<String, String> devInfo = deviceMap.get(title);
			Intent intent = new Intent();
			intent.putExtra(EXTRA_DEVICE_NAME, devInfo.first);
			intent.putExtra(EXTRA_DEVICE_ADDRESS, devInfo.second);

			// Set result and finish this Activity
			setResult(Activity.RESULT_OK, intent);
			finish();
		}
	};

	// The BroadcastReceiver that listens for discovered devices and
	// changes the title when discovery is finished
	private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();

			// When discovery finds a device
			if (BluetoothDevice.ACTION_FOUND.equals(action)) {
				// Get the BluetoothDevice object from the Intent
				BluetoothDevice device = intent
						.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

				Log.d(TAG,
						"BT Device: " + device.getName() + ": "
								+ device.getAddress());
                String title = device.getName() + "\n"
                        + device.getAddress();
                mNewDevicesArrayAdapter.add(title);
                deviceMap.put(title, new Pair<>(device.getName(), device.getAddress()));

				// When discovery is finished, change the Activity title
			} else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED
					.equals(action)) {
				setProgressBarIndeterminateVisibility(false);
				setTitle(R.string.select_device);
                scanButton.setEnabled(true);
				if (mNewDevicesArrayAdapter.getCount() == 0) {
                    String noDevices = getResources().getText(R.string.none_found).toString();
                    mNewDevicesArrayAdapter.add(noDevices);
				}
			}
		}
	};

}