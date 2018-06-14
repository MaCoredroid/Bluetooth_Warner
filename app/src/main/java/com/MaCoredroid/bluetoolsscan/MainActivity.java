package com.MaCoredroid.bluetoolsscan;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.lichenglin.bluetoolsscan.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //蓝牙适配器
    private BluetoothAdapter mBluetoothAdapter;
    //搜索BUTTON
    private Button scan;
    //搜索结果List
    private ListView resultList;
    //搜索状态的标示
    private boolean mScanning;
    //扫描时长
    private static final long SCAN_PERIOD = 5000;
    //请求启用蓝牙请求码
    private static final int REQUEST_ENABLE_BT = 1;
    //蓝牙适配器
    private BlueToothDeviceAdapter mBlueToothDeviceAdapter;
    //蓝牙适配器List
    private List<BluetoothDevice> mBlueList = new ArrayList<>();
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        //初始化蓝牙适配器
        final BluetoothManager bluetoothManager =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
        //初始化适配器
        mBlueToothDeviceAdapter = new BlueToothDeviceAdapter(mBlueList,context);

        inint();
        handler.post(task);
    }




    private void inint() {

        resultList = (ListView) findViewById(R.id.result);

//      mBluetoothAdapter.startLeScan(mLeScanCallback)
        scanLeDevice(true);


    }

    /**
     * 设备搜索
     *
     * @param enable 是否正在搜索的标示
     */
    private void scanLeDevice(final boolean enable) {
        if (enable) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mScanning) {
                        mScanning = false;
                        mBluetoothAdapter.stopLeScan(mLeScanCallback);
                        warn();
                    }
                }
            }, SCAN_PERIOD);

            mScanning = true;
            mHandler.sendEmptyMessage(1);
            mBluetoothAdapter.startLeScan(mLeScanCallback);
        }
    }

    // Hander
    public final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1: // Notify change
                    mBlueToothDeviceAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };
    // Device scan callback.
    private BluetoothAdapter.LeScanCallback mLeScanCallback =
            new BluetoothAdapter.LeScanCallback() {

                @Override
                public void onLeScan(final BluetoothDevice device, final int rssi, final byte[] scanRecord) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //获取到蓝牙设备
                            if (!mBlueList.contains(device)){
                                mBlueList.add(device);
                                Log.e("tag", "mLeScanCallback 搜索结果   " + device.getAddress());
                            }
                            //List加载适配器
                            if (mBlueToothDeviceAdapter.isEmpty()) {
                                Log.e("tag", "mLeDeviceListAdapter为空");

                            } else {
                                resultList.setAdapter(mBlueToothDeviceAdapter);

                            }

                            mHandler.sendEmptyMessage(1);
                        }
                    });
                }
            };




    public void warn() {
        if(!MyData.geta())
        {Vibrator vibrator = (Vibrator)this.getSystemService(this.VIBRATOR_SERVICE);
        vibrator.vibrate(1000);
            Toast.makeText(MainActivity.this, "Thing1 lost!", Toast.LENGTH_LONG).show();
            }
        if(!MyData.getb())
        {Vibrator vibrator = (Vibrator)this.getSystemService(this.VIBRATOR_SERVICE);
            vibrator.vibrate(1000);
            Toast.makeText(MainActivity.this, "Thing2 lost!", Toast.LENGTH_LONG).show();
           }
        if(!MyData.getc())
        {Vibrator vibrator = (Vibrator)this.getSystemService(this.VIBRATOR_SERVICE);
            vibrator.vibrate(1000);
            Toast.makeText(MainActivity.this, "Thing3 lost!", Toast.LENGTH_LONG).show();
          }


    }
    private Handler handler = new Handler();
    private Runnable task =new Runnable() {
        public void run() {

            handler.postDelayed(this,5*1000);//设置延迟时间，此处是5秒
              /*
                所需要执行的任务
              */
            inint();
        }
    };
}





