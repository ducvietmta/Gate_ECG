package com.vivalnk.sdk.demo.vital.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.vivalnk.sdk.demo.vital.R;
import com.vivalnk.sdk.model.Device;
import java.util.List;

/**
 * Created by JakeMo on 18-5-2.
 */
public class ScanListAdapter extends RecyclerView.Adapter<DeviceViewHolder> {

  private List<StatusDevice> mDeviceArrayList;
  private OnItemClickListener listener;

  public ScanListAdapter(List<StatusDevice> deviceArrayList,
      OnItemClickListener listener) {
    mDeviceArrayList = deviceArrayList;
    this.listener = listener;
  }

  public void updateConnectStatus(boolean connect, Device device) {
    for (StatusDevice temp : mDeviceArrayList) {
      if (device.getId().equalsIgnoreCase(temp.getId())) {
        temp.connect = connect;
      }
    }
    notifyDataSetChanged();
  }

  @NonNull
  @Override
  public DeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
    View itemView = mInflater.inflate(R.layout.item_scan_result, parent, false);
    return new DeviceViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(@NonNull DeviceViewHolder holder, int position) {
    holder.bind(position, mDeviceArrayList.get(position), listener);
  }

  @Override
  public int getItemCount() {
    return mDeviceArrayList.size();
  }

  public static class StatusDevice extends Device {

    public boolean connect;

    public StatusDevice(Device device) {
      super(device);
    }
  }
}
