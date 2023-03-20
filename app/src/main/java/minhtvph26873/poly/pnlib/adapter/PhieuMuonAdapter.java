package minhtvph26873.poly.pnlib.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import minhtvph26873.poly.pnlib.R;

import minhtvph26873.poly.pnlib.dao.PhieuMuonDAO;
import minhtvph26873.poly.pnlib.dao.SachDAO;
import minhtvph26873.poly.pnlib.dao.ThanhVienDAO;
import minhtvph26873.poly.pnlib.dao.ThuThuDAO;
import minhtvph26873.poly.pnlib.dao.TrangThaiDAO;
import minhtvph26873.poly.pnlib.model.PhieuMuon;
import minhtvph26873.poly.pnlib.model.Sach;
import minhtvph26873.poly.pnlib.model.ThanhVien;
import minhtvph26873.poly.pnlib.model.ThuThu;
import minhtvph26873.poly.pnlib.model.TrangThai;

import java.util.ArrayList;
import java.util.List;

public class PhieuMuonAdapter extends RecyclerView.Adapter<PhieuMuonAdapter.PhieuMuonHolder> {
    private Context context;
    private List<PhieuMuon> phieuMuonListst;

    public PhieuMuonAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<PhieuMuon> list) {
        this.phieuMuonListst = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PhieuMuonHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_phieu_muon, parent, false);
        return new PhieuMuonHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhieuMuonHolder holder, int position) {
        PhieuMuon phieuMuon1 = phieuMuonListst.get(position);
        if (phieuMuon1 != null) {
            holder.tvMapn.setText("Mã phiếu mượn: " + phieuMuon1.getMaPhieuMuon());
            holder.tvMatt.setText("Mã thủ thư: " + phieuMuon1.getMaTT());
            holder.tvMatv.setText("Mã thành viên: " + phieuMuon1.getMaTV());
            holder.tvMasach.setText("Mã sách: " + phieuMuon1.getMaSach());
            holder.tvNgay.setText("Ngày: " + phieuMuon1.getNgay());
            if (phieuMuon1.getTraSach() == 1) {
                holder.tvTrasach.setText("Trả sách: Đã trả");
            } else {
                holder.tvTrasach.setText("Trả sách: Chưa trả");
            }

            holder.tvTienthue.setText("Tiền thuê: " + phieuMuon1.getTienThue() + "K");
            holder.btnXoa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Bạn muốn xóa dữ liệu phiếu mượn?");
                    builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            PhieuMuonDAO phieuMuonDAO = new PhieuMuonDAO(context);
                            phieuMuonDAO.delete(String.valueOf(phieuMuon1.getMaPhieuMuon()));
                            phieuMuonListst.remove(phieuMuon1);
                            notifyDataSetChanged();
                            Toast.makeText(v.getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
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
            //-----------------------------------------------------------------------------

            holder.btnSua.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    View view = LayoutInflater.from(v.getContext()).inflate(R.layout.layout_edi_phieu_muon, null);
                    Spinner sp_sua_ma_thu_thu = view.findViewById(R.id.sp_sua_ma_thu_thu);
                    Spinner sp_sua_ma_thanh_vien = view.findViewById(R.id.sp_sua_ma_thanh_vien);
                    Spinner sp_sua_tra_sach = view.findViewById(R.id.sp_sua_tra_sach);
                    Spinner sp_sua_ma_sach = view.findViewById(R.id.sp_sua_ma_sach);
                    TextView tv_sua_tien_thue = view.findViewById(R.id.tv_sua_tien_thue);
                    Button btn_sua_phieu_muon = view.findViewById(R.id.btn_sua_phieu_muon);
                    int indexSPmaTT = 0;
                    ThuThuDAO thuThuDAO = new ThuThuDAO(context);
                    List<ThuThu> thuThuList = thuThuDAO.getAll();
                    List<String> listTT = new ArrayList<>();
                    for (int i = 0; i < thuThuList.size(); i++) {
                        listTT.add(thuThuList.get(i).getMaTT());
                        if (phieuMuon1.getMaTT().equals(thuThuList.get(i).getMaTT())) {
                            indexSPmaTT = i;
                        }
                    }
                    ArrayAdapter<String> adapter1 = new ArrayAdapter<>(v.getContext(), android.R.layout.simple_spinner_dropdown_item, listTT);
                    sp_sua_ma_thu_thu.setAdapter(adapter1);
                    sp_sua_ma_thu_thu.setSelection(indexSPmaTT);
                    int indexSPmaTV = 0;
                    ThanhVienDAO thanhVienDAO = new ThanhVienDAO(context);
                    List<ThanhVien> thanhVienList = thanhVienDAO.getAll();
                    List<Integer> listTV = new ArrayList<>();
                    for (int i = 0; i < thanhVienList.size(); i++) {
                        listTV.add(thanhVienList.get(i).getMaTV());
                        if (phieuMuon1.getMaTV() == thanhVienList.get(i).getMaTV()) {
                            indexSPmaTV = i;
                        }
                    }
                    ArrayAdapter<Integer> adapter2 = new ArrayAdapter<>(v.getContext(), android.R.layout.simple_spinner_dropdown_item, listTV);
                    sp_sua_ma_thanh_vien.setAdapter(adapter2);
                    sp_sua_ma_thanh_vien.setSelection(indexSPmaTV);
                    int indexSPmaSach = 0;
                    SachDAO sachDAO = new SachDAO(context);
                    List<Sach> sachList = sachDAO.getAll();
                    List<Integer> listS = new ArrayList<>();
                    for (int i = 0; i < sachList.size(); i++) {
                        listS.add(sachList.get(i).getMaSach());
                        if (phieuMuon1.getMaSach() == sachList.get(i).getMaSach()) {
                            indexSPmaSach = i;
                        }
                    }
                    ArrayAdapter<Integer> adapter3 = new ArrayAdapter<>(v.getContext(), android.R.layout.simple_spinner_dropdown_item, listS);
                    sp_sua_ma_sach.setAdapter(adapter3);
                    sp_sua_ma_sach.setSelection(indexSPmaSach);
                    sp_sua_ma_sach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            tv_sua_tien_thue.setText("Tiền thuê: " + sachList.get(sp_sua_ma_sach.getSelectedItemPosition()).getGiaThue() + "K");
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                    int indexSPtrasach = 0;
                    TrangThaiDAO trangThaiDAO = new TrangThaiDAO(context);
                    List<TrangThai> trangThaiList = trangThaiDAO.getAll();
                    List<Integer> listTrangThai = new ArrayList<>();
                    for (int i = 0; i < trangThaiList.size(); i++) {
                        listTrangThai.add(trangThaiList.get(i).getTrangThai());
                        if (phieuMuon1.getTraSach() == trangThaiList.get(i).getTrangThai()) {
                            indexSPtrasach = i;
                        }
                    }
                    ArrayAdapter<Integer> adapter4 = new ArrayAdapter<>(v.getContext(), android.R.layout.simple_spinner_dropdown_item, listTrangThai);
                    sp_sua_tra_sach.setAdapter(adapter4);
                    sp_sua_tra_sach.setSelection(indexSPtrasach);


                    btn_sua_phieu_muon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String matt = listTT.get(sp_sua_ma_thu_thu.getSelectedItemPosition());
                            int matv = Integer.parseInt(sp_sua_ma_thanh_vien.getSelectedItem().toString());
                            int masach = Integer.parseInt(sp_sua_ma_sach.getSelectedItem().toString());
                            int trasach = Integer.parseInt(sp_sua_tra_sach.getSelectedItem().toString());
                            int tienthue = sachList.get(sp_sua_ma_sach.getSelectedItemPosition()).getGiaThue();
                            phieuMuon1.setMaTT(matt);
                            phieuMuon1.setMaTV(matv);
                            phieuMuon1.setMaSach(masach);
                            phieuMuon1.setTraSach(trasach);
                            phieuMuon1.setTienThue(tienthue);
                            PhieuMuonDAO phieuMuonDAO = new PhieuMuonDAO(v.getContext());
                            phieuMuonDAO.update(phieuMuon1);
                            notifyDataSetChanged();
                            Toast.makeText(v.getContext(), "Sửa thành công ", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.setView(view);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });
            //--------------------------------------------------------------
        }

    }

    @Override
    public int getItemCount() {
        if (phieuMuonListst.size() > 0) {
            return phieuMuonListst.size();
        }
        return 0;
    }

    public class PhieuMuonHolder extends RecyclerView.ViewHolder {
        private TextView tvMapn, tvMatt, tvMatv, tvMasach, tvNgay, tvTrasach, tvTienthue;
        private ImageButton btnSua, btnXoa;

        public PhieuMuonHolder(@NonNull View itemView) {
            super(itemView);
            tvMapn = itemView.findViewById(R.id.tv_mapm);
            tvMatt = itemView.findViewById(R.id.tv_matt);
            tvMatv = itemView.findViewById(R.id.tv_matv);
            tvMasach = itemView.findViewById(R.id.tv_masach);
            tvNgay = itemView.findViewById(R.id.tv_ngay);
            tvTrasach = itemView.findViewById(R.id.tv_trasach);
            tvTienthue = itemView.findViewById(R.id.tv_tienthue);
            btnSua = itemView.findViewById(R.id.btn_sua);
            btnXoa = itemView.findViewById(R.id.btn_xoa);
        }
    }
}
