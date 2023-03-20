package minhtvph26873.poly.pnlib.fragment;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import minhtvph26873.poly.pnlib.adapter.ThuThuAdapter;
import minhtvph26873.poly.pnlib.dao.ThuThuDAO;
import minhtvph26873.poly.pnlib.model.ThuThu;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;


public class ThuThuFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_thu_thu, container, false);
    }

    private ThuThuDAO thuThuDAO;
    private ThuThuAdapter adapter;
    private List<ThuThu> list;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_thu_thu);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        thuThuDAO = new ThuThuDAO(getContext());
        adapter = new ThuThuAdapter(getContext());
        list = thuThuDAO.getAll();
        adapter.setData(list);
        recyclerView.setAdapter(adapter);
        FloatingActionButton button = view.findViewById(R.id.floatingbtntt);
        button.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            View view1 = LayoutInflater.from(v.getContext()).inflate(R.layout.layout_add_thu_thu, null);
            //--------------------------------
            EditText edt_nhap_ma_thu_thu = view1.findViewById(R.id.edt_nhap_ma_thu_thu);
            EditText edt_nhap_ten_thu_thu = view1.findViewById(R.id.edt_nhap_ten_thu_thu);
            EditText edt_nhap_mat_khau_thu_thu = view1.findViewById(R.id.edt_nhap_mat_khau_thu_thu);
            TextInputLayout til_ma_thu_thu = view1.findViewById(R.id.til_ma_thu_thu);
            TextInputLayout til_ten_thu_thu = view1.findViewById(R.id.til_ten_thu_thu);
            TextInputLayout til_mat_khau_thu_thu = view1.findViewById(R.id.til_mat_khau_thu_thu);
            Button btn_them_thu_thu = view1.findViewById(R.id.btn_them_thu_thu);
            //---------------------------------------------------
            btn_them_thu_thu.setOnClickListener(v1 -> {
                String ma = edt_nhap_ma_thu_thu.getText().toString();
                String ten = edt_nhap_ten_thu_thu.getText().toString();
                String matkhau = edt_nhap_mat_khau_thu_thu.getText().toString();
                if (ma.isEmpty()) {
                    til_ma_thu_thu.setError("Không được để trống trường này!");
                    edt_nhap_ma_thu_thu.requestFocus();
                    return;
                }
                if (ma.length() < 5) {
                    til_ma_thu_thu.setError("Mã thủ thư chứa ít nhất 5 kí tự!");
                    edt_nhap_ma_thu_thu.setSelection(ma.length());
                    return;
                }
                int index = 0;
                for (int i = 0; i < list.size(); i++) {
                    if (ma.equals(list.get(i).getMaTT())) {
                        index++;
                    }
                }
                if (index != 0) {
                    til_ma_thu_thu.setError("Mã thủ thư đã tồn tại!");
                    edt_nhap_ma_thu_thu.setSelection(ma.length());
                    return;
                }
                til_ma_thu_thu.setError("");
                if (ten.isEmpty()) {
                    til_ten_thu_thu.setError("Không được để trống trường này!");
                    edt_nhap_ten_thu_thu.requestFocus();
                    return;
                }
                til_ten_thu_thu.setError("");
                if (matkhau.isEmpty()) {
                    til_mat_khau_thu_thu.setError("Không được để trống trường này!");
                    edt_nhap_mat_khau_thu_thu.requestFocus();
                    return;
                }
                if (matkhau.length() < 5) {
                    til_mat_khau_thu_thu.setError("Mật khẩu phải nhiều hơn 4 kí tự!");
                    edt_nhap_mat_khau_thu_thu.setSelection(matkhau.length());
                    return;
                }
                til_mat_khau_thu_thu.setError("");
                thuThuDAO.insert(new ThuThu(ma, ten, matkhau));
                list = thuThuDAO.getAll();
                adapter.setData(list);
                Toast.makeText(v1.getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                edt_nhap_ma_thu_thu.setText("");
                edt_nhap_ma_thu_thu.requestFocus();
                edt_nhap_ten_thu_thu.setText("");
                edt_nhap_mat_khau_thu_thu.setText("");
            });


            //-------------------------------
            builder.setView(view1);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        list = thuThuDAO.getAll();
        adapter.setData(list);
    }
}