package minhtvph26873.poly.pnlib.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import minhtvph26873.poly.pnlib.R;

import minhtvph26873.poly.pnlib.dao.LoaiSachDAO;
import minhtvph26873.poly.pnlib.dao.PhieuMuonDAO;
import minhtvph26873.poly.pnlib.dao.SachDAO;
import minhtvph26873.poly.pnlib.model.LoaiSach;
import minhtvph26873.poly.pnlib.model.PhieuMuon;
import minhtvph26873.poly.pnlib.model.Sach;

import java.util.ArrayList;
import java.util.List;

public class SachAdapter extends RecyclerView.Adapter<SachAdapter.SachHolder> {

    private Context context;
    private List<Sach> sachList;

    public SachAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Sach> sachList) {
        this.sachList = sachList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SachHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_sach, parent, false);
        return new SachHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SachHolder holder, int position) {
        Sach sach = sachList.get(position);
        if (sach != null) {
            holder.tv_ma_sach.setText("Mã sách: " + sach.getMaSach());
            holder.tv_ten_sach.setText("Tên sách: " + sach.getTenSach());
            holder.tv_gia_thue.setText("Giá thuê: " + sach.getGiaThue() + "K");
            holder.tv_ma_loai.setText("Mã loại sách: " + sach.getMaLoai());
            //----------------------------------
            holder.btn_xoa_sach.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Xác nhận xóa thông tin sách " + sach.getTenSach() + "?");
                    builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int index = 0;
                            String pos = "";
                            PhieuMuonDAO phieuMuonDAO = new PhieuMuonDAO(context);
                            List<PhieuMuon> phieuMuonList = phieuMuonDAO.getAll();
                            for (int i = 0; i < phieuMuonList.size(); i++) {
                                if (sach.getMaSach() == phieuMuonList.get(i).getMaSach()) {
                                    index++;
                                    pos += phieuMuonList.get(i).getMaPhieuMuon() + " ";
                                }
                            }
                            if (index != 0) {
                                Toast.makeText(v.getContext(), "Sách đang tồn tại trong phiếu mượn " + pos, Toast.LENGTH_SHORT).show();
                            } else {
                                SachDAO sachDAO = new SachDAO(context);
                                sachDAO.delete(String.valueOf(sach.getMaSach()));
                                sachList.remove(sach);
                                notifyDataSetChanged();
                                Toast.makeText(v.getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                    builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });
            //-------------------------------------
            holder.btn_sua_sach.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    View view = LayoutInflater.from(v.getContext()).inflate(R.layout.layout_edit_sach, null);
                    EditText edt_sua_ten_sach = view.findViewById(R.id.edt_sua_ten_sach);
                    Spinner sp_sua_loai_sach = view.findViewById(R.id.sp_sua_loai_sach);
                    EditText edt_sua_gia_thue = view.findViewById(R.id.edt_sua_gia_thue);
                    Button btn_sua_sach = view.findViewById(R.id.btn_sua_sach);
                    LoaiSachDAO loaiSachDAO = new LoaiSachDAO(v.getContext());
                    List<LoaiSach> loaiSachList = loaiSachDAO.getAll();
                    List<Integer> list = new ArrayList<>();
                    int indexSpinner = 0;
                    for (int i = 0; i < loaiSachList.size(); i++) {
                        list.add(loaiSachList.get(i).getMaLoai());
                        if (sach.getMaLoai() == loaiSachList.get(i).getMaLoai()) {
                            indexSpinner = i;
                        }
                    }
                    ArrayAdapter<Integer> adapter = new ArrayAdapter<>(v.getContext(), android.R.layout.simple_spinner_dropdown_item, list);
                    sp_sua_loai_sach.setAdapter(adapter);
                    sp_sua_loai_sach.setSelection(indexSpinner);
                    String tencu = sach.getTenSach();
                    String giacu = String.valueOf(sach.getGiaThue());
                    btn_sua_sach.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String tenmoi = edt_sua_ten_sach.getText().toString().trim();
                            int loaisachmoi = Integer.parseInt(sp_sua_loai_sach.getSelectedItem().toString());
                            String giamoi = edt_sua_gia_thue.getText().toString().trim();
                            if (tenmoi.isEmpty()) {
                                tenmoi = tencu;
                            }
                            if (giamoi.isEmpty()) {
                                giamoi = giacu;
                            }
                            sach.setMaLoai(loaisachmoi);
                            sach.setTenSach(tenmoi);
                            sach.setGiaThue(Integer.parseInt(giamoi));
                            SachDAO sachDAO = new SachDAO(v.getContext());
                            sachDAO.update(sach);
                            notifyDataSetChanged();
                            Toast.makeText(v.getContext(), "Sửa thành công", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.setView(view);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();


                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (sachList.size() > 0) {
            return sachList.size();
        }
        return 0;
    }

    public class SachHolder extends RecyclerView.ViewHolder {
        private TextView tv_ma_sach, tv_ten_sach, tv_gia_thue, tv_ma_loai;
        private ImageButton btn_sua_sach, btn_xoa_sach;

        public SachHolder(@NonNull View itemView) {
            super(itemView);
            tv_ma_sach = itemView.findViewById(R.id.tv_ma_sach);
            tv_ten_sach = itemView.findViewById(R.id.tv_ten_sach);
            tv_gia_thue = itemView.findViewById(R.id.tv_gia_thue);
            tv_ma_loai = itemView.findViewById(R.id.tv_ma_loai);
            btn_sua_sach = itemView.findViewById(R.id.btn_sua_sach);
            btn_xoa_sach = itemView.findViewById(R.id.btn_xoa_sach);
        }
    }
}
