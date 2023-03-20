package minhtvph26873.poly.pnlib.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import minhtvph26873.poly.pnlib.R;

import minhtvph26873.poly.pnlib.dao.LoaiSachDAO;
import minhtvph26873.poly.pnlib.dao.SachDAO;
import minhtvph26873.poly.pnlib.model.LoaiSach;
import minhtvph26873.poly.pnlib.model.Sach;

import java.util.List;

public class LoaiSachAdapter extends RecyclerView.Adapter<LoaiSachAdapter.LoaiSachHolder> {
    private Context context;
    private List<LoaiSach> loaiSachListst;

    public void setData(List<LoaiSach> loaiSachListst) {
        this.loaiSachListst = loaiSachListst;
        notifyDataSetChanged();
    }

    public LoaiSachAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public LoaiSachHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_loai_sach, parent, false);
        return new LoaiSachHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoaiSachHolder holder, int position) {
        LoaiSach loaiSach = loaiSachListst.get(position);
        if (loaiSach != null) {
            holder.tvTenloaisach.setText(String.valueOf(loaiSach.getTenLoai()));
            holder.tvMaloaisach.setText("Mã loại sách: " + loaiSach.getMaLoai());
            holder.btn_xoa_loai_sach.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Xác nhận xóa loại sách " + loaiSach.getTenLoai() + "?");
                    builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int index = 0;
                            String pos = "";
                            SachDAO sachDAO = new SachDAO(context);
                            List<Sach> sachList = sachDAO.getAll();
                            for (int i = 0; i < sachList.size(); i++) {
                                if (loaiSach.getMaLoai() == sachList.get(i).getMaLoai()) {
                                    index++;
                                    pos += sachList.get(i).getMaSach() + " ";
                                }
                            }
                            if (index != 0) {
                                Toast.makeText(v.getContext(), "Loại sách đang tồn tại trong mục sách mã " + pos, Toast.LENGTH_SHORT).show();
                            } else {
                                LoaiSachDAO loaiSachDAO = new LoaiSachDAO(context);
                                loaiSachDAO.delete(String.valueOf(loaiSach.getMaLoai()));
                                loaiSachListst.remove(loaiSach);
                                notifyDataSetChanged();
                                Toast.makeText(v.getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                    builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //huy
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });

            holder.btn_sua_loai_sach.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                    builder1.setTitle("\nSửa thông tin loại sách:\n");
                    View view = LayoutInflater.from(v.getContext()).inflate(R.layout.layout_edit_loai_sach, null);
                    EditText edt_ten_loai_sach_1 = view.findViewById(R.id.edt_ten_loai_sach_1);
                    Button btn_sua_ten_loai_sach_1 = view.findViewById(R.id.btn_sua_ten_loai_sach_1);
                    btn_sua_ten_loai_sach_1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String tenmoi = edt_ten_loai_sach_1.getText().toString().trim();
                            if (tenmoi.isEmpty()) {
                                edt_ten_loai_sach_1.requestFocus();
                                Toast.makeText(context, "Không được để trống!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            loaiSach.setTenLoai(tenmoi);
                            LoaiSachDAO loaiSachDAO = new LoaiSachDAO(context);
                            loaiSachDAO.update(loaiSach);
                            notifyDataSetChanged();
                            Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                        }
                    });
                    //-----------------------------------------
                    builder1.setView(view);
                    AlertDialog alertDialog = builder1.create();
                    alertDialog.show();
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        if (loaiSachListst.size() > 0) {
            return loaiSachListst.size();
        }
        return 0;
    }

    public class LoaiSachHolder extends RecyclerView.ViewHolder {
        private TextView tvMaloaisach, tvTenloaisach;
        private ImageButton btn_sua_loai_sach, btn_xoa_loai_sach;

        public LoaiSachHolder(@NonNull View itemView) {
            super(itemView);
            tvMaloaisach = itemView.findViewById(R.id.tv_ma_loai_sach);
            tvTenloaisach = itemView.findViewById(R.id.tv_ten_loai_sach);
            btn_sua_loai_sach = itemView.findViewById(R.id.bnt_sua_loai_sach);
            btn_xoa_loai_sach = itemView.findViewById(R.id.btn_xoa_loai_sach);

        }
    }
}
