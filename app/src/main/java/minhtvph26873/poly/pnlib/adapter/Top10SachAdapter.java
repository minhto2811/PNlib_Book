package minhtvph26873.poly.pnlib.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import minhtvph26873.poly.pnlib.R;

import minhtvph26873.poly.pnlib.model.Top;

import java.util.List;

public class Top10SachAdapter extends RecyclerView.Adapter<Top10SachAdapter.Top10SachHolder> {
    private Context context;
    private List<Top> list;

    public Top10SachAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Top> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Top10SachHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_top10_sach, parent, false);
        return new Top10SachHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Top10SachHolder holder, int position) {
        Top top = list.get(position);
        {
            if (top != null) {
                holder.tv_stt_top10_sach.setText("Top sách: " + (position + 1));
                holder.tv_ten_sach_top10_sach.setText("Tên sách: " + top.getTenSach());
                holder.tv_so_luong_top10_sach.setText("Số lượt mượn: " + top.getSoLuong());
            }
        }
    }

    @Override
    public int getItemCount() {
        if (list.size() > 0) {
            return list.size();
        }
        return 0;
    }


    public class Top10SachHolder extends RecyclerView.ViewHolder {
        private TextView tv_stt_top10_sach, tv_ten_sach_top10_sach, tv_so_luong_top10_sach;

        public Top10SachHolder(@NonNull View itemView) {
            super(itemView);
            tv_stt_top10_sach = itemView.findViewById(R.id.tv_stt_top10_sach);
            tv_ten_sach_top10_sach = itemView.findViewById(R.id.tv_ten_sach_top10_sach);
            tv_so_luong_top10_sach = itemView.findViewById(R.id.tv_so_luong_top10_sach);
        }
    }
}
