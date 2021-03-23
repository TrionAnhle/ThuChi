package com.example.thuchi.model;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.thuchi.R;

import java.util.List;

public class ThuChiAdapter extends BaseAdapter {
    private List<ThuChi> listThuChi;
    public ThuChiAdapter(List<ThuChi> listThuChi){
        this.listThuChi = listThuChi;
    }

    @Override
    public int getCount() {
        return listThuChi.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View viewItemThuChi;
        if(convertView==null){
            viewItemThuChi = View.inflate(parent.getContext(),R.layout.item_thuchi,null);
        }else viewItemThuChi = convertView;
        ((TextView) viewItemThuChi.findViewById(R.id.textviewTenCongViec)).setText(listThuChi.get(position).getTenCongViec());
        ((TextView) viewItemThuChi.findViewById(R.id.textviewNgay)).setText(listThuChi.get(position).getNgay());
        ((TextView) viewItemThuChi.findViewById(R.id.textviewSoTien)).setText(
                listThuChi.get(position).getDau()+String.valueOf(listThuChi.get(position).getSoTien())
        );
        return viewItemThuChi;
    }
}
