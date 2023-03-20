package minhtvph26873.poly.pnlib.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import minhtvph26873.poly.pnlib.R;

import minhtvph26873.poly.pnlib.adapter.PhieuMuonAdapter;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class PhieuMuonFragment extends Fragment {


    public PhieuMuonFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_phieu_muon, container, false);
    }

    private List<PhieuMuon> list;
    private PhieuMuonAdapter adapter;

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        PhieuMuonDAO phieuMuonDAO1 = new PhieuMuonDAO(getContext());
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        adapter = new PhieuMuonAdapter(getContext());
        list = phieuMuonDAO1.getAll();
        adapter.setData(list);
        recyclerView.setAdapter(adapter);
        FloatingActionButton btnAdd = view.findViewById(R.id.floatingbtn);
        btnAdd.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("Tạo mới phiếu mượn sách:");
            //-------------------------------------------------------
            View view1 = LayoutInflater.from(v.getContext()).inflate(R.layout.layout_add_phieu_muon, null);
            TextView tv_chon_ma_thu_thu = view1.findViewById(R.id.tv_chon_ma_thu_thu);
            TextView tv_chon_ma_thanh_vien = view1.findViewById(R.id.tv_chon_ma_thanh_vien);
            TextView tv_chon_ma_sach = view1.findViewById(R.id.tv_chon_ma_sach);
            Spinner sp_matt = view1.findViewById(R.id.sp_matt);
            Spinner sp_matv = view1.findViewById(R.id.sp_matv);
            Spinner sp_masach = view1.findViewById(R.id.sp_masach);
            Spinner sp_trangthai = view1.findViewById(R.id.sp_trangthai);
            TextView tvNgay = view1.findViewById(R.id.tv_ngay);
            TextView tv_gia_thue_phieu_muon = view1.findViewById(R.id.tv_gia_thue_phieu_muon);
            Button btnThempm = view1.findViewById(R.id.btn_thempm);

            //------------------------------------------
            ThuThuDAO thuThuDAO = new ThuThuDAO(getContext());
            List<ThuThu> thuThuList = thuThuDAO.getAll();
            if (thuThuList.size() == 0) {
                tv_chon_ma_thu_thu.setText("Hiện tại không có thư thư!\n(Hãy tạo thủ thư trước)");
                tv_chon_ma_thu_thu.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            } else {
                List<String> list1 = new ArrayList<>();
                for (int i = 0; i < thuThuList.size(); i++) {
                    list1.add(thuThuList.get(i).getMaTT());
                }
                ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, list1);
                sp_matt.setAdapter(adapter1);
            }
            //-----------------------------------------------
            ThanhVienDAO thanhVienDAO = new ThanhVienDAO(getContext());
            List<ThanhVien> thanhVienList = thanhVienDAO.getAll();
            if (thanhVienList.size() == 0) {
                tv_chon_ma_thanh_vien.setText("Hiện tại không có thành viên!\n(Hãy thêm thành viên trước)");
                tv_chon_ma_thanh_vien.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            } else {
                List<Integer> list2 = new ArrayList<>();
                for (int i = 0; i < thanhVienList.size(); i++) {
                    list2.add(thanhVienList.get(i).getMaTV());
                }
                ArrayAdapter<Integer> adapter2 = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, list2);
                sp_matv.setAdapter(adapter2);
            }

            //----------------------------------------------------------
            SachDAO sachDAO = new SachDAO(getContext());
            List<Sach> sachList = sachDAO.getAll();
            if (sachList.size() == 0) {
                tv_chon_ma_sach.setText("Hiện tại không có mã sách!\n(Hãy thêm sách trước)");
                tv_chon_ma_sach.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            } else {
                List<Integer> list3 = new ArrayList<>();
                for (int i = 0; i < sachList.size(); i++) {
                    list3.add(sachList.get(i).getMaSach());
                }
                ArrayAdapter<Integer> adapter3 = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, list3);
                sp_masach.setAdapter(adapter3);
            }

            sp_masach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view2, int position, long id) {
                    tv_gia_thue_phieu_muon.setText("Giá thuê: " + sachList.get(position).getGiaThue() + "K");
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            //------------------------------------------------------------
            TrangThaiDAO trangThaiDAO = new TrangThaiDAO(getContext());
            List<TrangThai> trangThaiList = trangThaiDAO.getAll();
            List<Integer> list4 = new ArrayList<>();
            for (int i = 0; i < trangThaiList.size(); i++) {
                list4.add(trangThaiList.get(i).getTrangThai());
            }
            ArrayAdapter<Integer> adapter4 = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, list4);
            sp_trangthai.setAdapter(adapter4);
            //-------------------------------------------------------------
            String currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
            tvNgay.setText("Ngày Tạo: " + currentDate);
            //--------------------------------------------
            btnThempm.setOnClickListener(v1 -> {
                String mes = "Hãy tạo trước ";
                if (thuThuList.size() == 0) {
                    mes += "thủ thư";
                }
                if (thanhVienList.size() == 0) {
                    mes += ", thành viên";
                }
                if (sachList.size() == 0) {
                    mes += " và sách.";
                }
                if (thuThuList.size() == 0 || thanhVienList.size() == 0 || sachList.size() == 0) {
                    Toast.makeText(v1.getContext(), mes, Toast.LENGTH_SHORT).show();
                    return;
                }
                PhieuMuon phieuMuon = new PhieuMuon();
                phieuMuon.setMaTT(sp_matt.getSelectedItem().toString());
                phieuMuon.setMaTV(Integer.parseInt(sp_matv.getSelectedItem().toString()));
                phieuMuon.setMaSach(Integer.parseInt(sp_masach.getSelectedItem().toString()));
                phieuMuon.setNgay(currentDate);
                phieuMuon.setTraSach(Integer.parseInt(sp_trangthai.getSelectedItem().toString()));
                phieuMuon.setTienThue(sachList.get(sp_masach.getSelectedItemPosition()).getGiaThue());
                PhieuMuonDAO phieuMuonDAO = new PhieuMuonDAO(getContext());
                phieuMuonDAO.insert(phieuMuon);
                list = phieuMuonDAO.getAll();
                adapter.setData(list);
                Toast.makeText(getContext(), "Tạo phiếu mượn thành công", Toast.LENGTH_SHORT).show();
                sp_matt.setSelection(0);
                sp_masach.setSelection(0);
                sp_matv.setSelection(0);
                sp_trangthai.setSelection(0);

            });


            //------------------------------------------
            builder.setView(view1);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });

    }
}