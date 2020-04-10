package com.vivalnk.sdk.demo.vital.ui.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.vivalnk.sdk.demo.vital.R;

/**
 * Created by JakeMo on 18-5-2.
 */
public class DeviceViewHolder extends RecyclerView.ViewHolder {

  @BindView(R.id.tvDeviceName)
  public TextView tvDeviceName;
  @BindView(R.id.tvRSSI)
  public TextView tvRSSI;
  @BindView(R.id.tvDeviceMac)
  public TextView tvDeviceMac;

  @BindView(R.id.cardView)
  public CardView cardView;

  @BindView(R.id.tvConnected)
  public TextView tvConnected;

  public DeviceViewHolder(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }

  public void bind(final int position, final ScanListAdapter.StatusDevice device,
      final OnItemClickListener listener) {
    tvDeviceName.setText(device.getName());
    tvRSSI.setText(String.valueOf(device.getRssi()));
    tvDeviceMac.setText(device.getId());
    tvConnected.setVisibility(device.connect ? View.VISIBLE : View.GONE);
    cardView.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        listener.onItemClick(v, position, device);
      }
    });
  }
}
