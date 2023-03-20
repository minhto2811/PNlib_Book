package minhtvph26873.poly.pnlib.fragment;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import minhtvph26873.poly.pnlib.R;

import minhtvph26873.poly.pnlib.adapter.LoaiSachAdapter;
import minhtvph26873.poly.pnlib.dao.LoaiSachDAO;
import minhtvph26873.poly.pnlib.model.LoaiSach;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class LoaiSachFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_loai_sach, container, false);
    }

    private LoaiSachDAO loaiSachDAO;
    private LoaiSachAdapter adapter;
    private List<LoaiSach> list;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewls);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        list = new ArrayList<>();
        loaiSachDAO = new LoaiSachDAO(getActivity());
        adapter = new LoaiSachAdapter(getActivity());
        list = loaiSachDAO.getAll();
        adapter.setData(list);
        recyclerView.setAdapter(adapter);
        FloatingActionButton floatingbtnls = view.findViewById(R.id.floatingbtnls);
        floatingbtnls.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("Thêm loại sách mới:");
            View view1 = LayoutInflater.from(v.getContext()).inflate(R.layout.layout_add_loai_sach, null);
            EditText edt_ten_loai_sach = view1.findViewById(R.id.edt_ten_loai_sach);
            Button btn_them_ten_loai_sach = view1.findViewById(R.id.btn_them_ten_loai_sach);
            btn_them_ten_loai_sach.setOnClickListener(v1 -> {
                String tenmoi = edt_ten_loai_sach.getText().toString();
                if (tenmoi.isEmpty()) {
                    edt_ten_loai_sach.requestFocus();
                } else {
                    LoaiSach loaiSach = new LoaiSach();
                    loaiSach.setTenLoai(tenmoi);
                    loaiSachDAO.insert(loaiSach);
                    Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    list = loaiSachDAO.getAll();
                    adapter.setData(list);
                    edt_ten_loai_sach.setText("");
                }
            });
            builder.setView(view1);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        });
    }

    @Override
    public void onResume() {
        super.onResume();
        list = loaiSachDAO.getAll();
        adapter.setData(list);
    }
}