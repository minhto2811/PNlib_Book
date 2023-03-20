package minhtvph26873.poly.pnlib.fragment;

import static java.util.Objects.*;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import minhtvph26873.poly.pnlib.R;

import minhtvph26873.poly.pnlib.adapter.ThanhVienAdapter;
import minhtvph26873.poly.pnlib.dao.ThanhVienDAO;
import minhtvph26873.poly.pnlib.model.ThanhVien;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class ThanhVienFragment extends Fragment {


    public ThanhVienFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_thanh_vien, container, false);
    }

    private ThanhVienDAO thanhVienDAO;
    private List<ThanhVien> list;
    private ThanhVienAdapter adapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView_thanh_vien = view.findViewById(R.id.recyclerView_thanh_vien);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView_thanh_vien.setLayoutManager(manager);
        recyclerView_thanh_vien.setHasFixedSize(true);
        thanhVienDAO = new ThanhVienDAO(getActivity());
        adapter = new ThanhVienAdapter(getActivity());
        list = thanhVienDAO.getAll();
        adapter.setData(list);
        recyclerView_thanh_vien.setAdapter(adapter);
        FloatingActionButton button = view.findViewById(R.id.floatingbtntv);
        button.setOnClickListener(v -> {
            @SuppressLint("UseRequireInsteadOfGet") AlertDialog.Builder builder = new AlertDialog.Builder(requireNonNull(getContext()));
            View view1 = LayoutInflater.from(v.getContext()).inflate(R.layout.layout_add_thanh_vien, null);
            EditText edt_ten_thanh_vien = view1.findViewById(R.id.edt_ten_thanh_vien);
            EditText edt_nam_sinh_thanh_vien = view1.findViewById(R.id.edt_nam_sinh_thanh_vien);
            Spinner sp_gioi_tinh = view1.findViewById(R.id.sp_gioi_tinh);
            Button btn_them_thanh_vien = view1.findViewById(R.id.btn_them_thanh_vien);
            //-----------------------------------------
            Calendar calendar = Calendar.getInstance();
            int ngay = calendar.get(Calendar.DAY_OF_MONTH);
            int thang = calendar.get(Calendar.MONTH);
            int nam = calendar.get(Calendar.YEAR);
            edt_nam_sinh_thanh_vien.setOnClickListener(v1 -> {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), (view2, year, month, dayOfMonth) -> {
                    calendar.set(year, month, dayOfMonth);
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    edt_nam_sinh_thanh_vien.setText(simpleDateFormat.format(calendar.getTime()));
                }, nam, thang, ngay);
                datePickerDialog.show();
            });
            //----------------------------------------------------------
            List<String> gioitinhlist = new ArrayList<>();
            gioitinhlist.add("Nam");
            gioitinhlist.add("Nữ");
            ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,gioitinhlist);
            sp_gioi_tinh.setAdapter(adapter1);
            //---------------------------------------------------------------------------------
            btn_them_thanh_vien.setOnClickListener(v12 -> {
                String tenmoi = edt_ten_thanh_vien.getText().toString().trim();
                String namsinhmoi = edt_nam_sinh_thanh_vien.getText().toString().trim();
                if (tenmoi.isEmpty()) {
                    edt_ten_thanh_vien.requestFocus();
                    Toast.makeText(getActivity(), "Tên thành viên trống", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (namsinhmoi.isEmpty()) {
                    edt_nam_sinh_thanh_vien.requestFocus();
                    Toast.makeText(getActivity(), "Năm sinh thành viên trống", Toast.LENGTH_SHORT).show();
                    return;
                }
                String date = "^([0-2][0-9]|3[0-1])/(0[0-9]|1[0-2])/([0-9][0-9])?[0-9][0-9]$";
                if (!namsinhmoi.matches(date)) {
                    edt_nam_sinh_thanh_vien.requestFocus();
                    Toast.makeText(getActivity(), "Năm sinh sai định dạng ngày/tháng/năm", Toast.LENGTH_SHORT).show();
                    return;
                }
                ThanhVien thanhVien = new ThanhVien();
                thanhVien.setHoTen(tenmoi);
                thanhVien.setNamSinh(namsinhmoi);
                thanhVien.setGioiTinh(sp_gioi_tinh.getSelectedItem().toString());
                thanhVienDAO.insert(thanhVien);
                list = thanhVienDAO.getAll();
                adapter.setData(list);
                Toast.makeText(getActivity(), "Thêm thành công thành viên mới", Toast.LENGTH_SHORT).show();
                edt_ten_thanh_vien.setText("");
                edt_ten_thanh_vien.requestFocus();
                edt_nam_sinh_thanh_vien.setText("");
            });
            builder.setView(view1);
            //------------------------------------------------
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        list = thanhVienDAO.getAll();
        adapter.setData(list);
    }
}