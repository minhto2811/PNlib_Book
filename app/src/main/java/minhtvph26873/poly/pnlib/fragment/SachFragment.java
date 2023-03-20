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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import minhtvph26873.poly.pnlib.R;

import minhtvph26873.poly.pnlib.adapter.SachAdapter;
import minhtvph26873.poly.pnlib.dao.LoaiSachDAO;
import minhtvph26873.poly.pnlib.dao.SachDAO;
import minhtvph26873.poly.pnlib.model.LoaiSach;
import minhtvph26873.poly.pnlib.model.Sach;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;


public class SachFragment extends Fragment {

    public SachFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sach, container, false);
    }

    private SachDAO sachDAO;
    private List<Sach> list;
    private SachAdapter adapter;
    private TextInputLayout til_search;
    private EditText edt_search;
    private Button btn_search;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViews);
        til_search = view.findViewById(R.id.til_search);
        edt_search = view.findViewById(R.id.edt_search);
        btn_search = view.findViewById(R.id.btn_search);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        sachDAO = new SachDAO(getContext());
        adapter = new SachAdapter(getContext());
        list = sachDAO.getAll();
        adapter.setData(list);
        recyclerView.setAdapter(adapter);
        FloatingActionButton button = view.findViewById(R.id.floatingbtns);
        button.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("Thêm thông tin sách mới:");
            View view1 = LayoutInflater.from(v.getContext()).inflate(R.layout.layout_add_sach, null);
            TextView tv_chon_ma_loai_sach = view1.findViewById(R.id.tv_chon_ma_loai_sach);
            EditText edt_ten_sach = view1.findViewById(R.id.edt_ten_sach);
            EditText edt_gia_thue = view1.findViewById(R.id.edt_gia_thue);
            Spinner sp_ma_loai = view1.findViewById(R.id.sp_ma_loai);
            Button btn_them_sach = view1.findViewById(R.id.btn_them_sach);
            LoaiSachDAO loaiSachDAO = new LoaiSachDAO(v.getContext());
            List<LoaiSach> loaiSachList = loaiSachDAO.getAll();
            if (loaiSachList.size() == 0) {
                tv_chon_ma_loai_sach.setText("Hiện tại không có loại sách!\n(Hãy thêm loại sách trước)");
                tv_chon_ma_loai_sach.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            } else {
                List<Integer> list1 = new ArrayList<>();
                for (int i = 0; i < loaiSachList.size(); i++) {
                    list1.add(loaiSachList.get(i).getMaLoai());
                }
                ArrayAdapter<Integer> adapter1 = new ArrayAdapter<>(v.getContext(), android.R.layout.simple_spinner_dropdown_item, list1);
                sp_ma_loai.setAdapter(adapter1);
            }


            btn_them_sach.setOnClickListener(v1 -> {
                if (loaiSachList.size() == 0) {
                    Toast.makeText(v1.getContext(), "Hãy thêm loại sách trước", Toast.LENGTH_SHORT).show();
                    return;
                }
                String tensach = edt_ten_sach.getText().toString().trim();
                String giathue = edt_gia_thue.getText().toString().trim();
                if (tensach.isEmpty()) {
                    edt_ten_sach.requestFocus();
                    Toast.makeText(v1.getContext(), "Tên sách trống", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (giathue.isEmpty()) {
                    edt_gia_thue.requestFocus();
                    Toast.makeText(v1.getContext(), "Giá thuê sách trống", Toast.LENGTH_SHORT).show();
                    return;
                }
                Sach sach = new Sach();
                sach.setTenSach(tensach);
                sach.setGiaThue(Integer.parseInt(giathue));
                sach.setMaLoai(Integer.parseInt(sp_ma_loai.getSelectedItem().toString()));
                sachDAO.insert(sach);
                list = sachDAO.getAll();
                adapter.setData(list);
                Toast.makeText(v1.getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                edt_gia_thue.setText("");
                edt_ten_sach.setText("");
                sp_ma_loai.setSelection(0);
                edt_ten_sach.requestFocus();
            });
            builder.setView(view1);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        });
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(list.size()==0){
                    Toast.makeText(getContext(),"Danh sách trông",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(edt_search.getText().toString().isEmpty()){
                    til_search.setError("Không được để trống trường này!");
                    edt_search.requestFocus();
                    return;
                }
                til_search.setError("");
                ArrayList<Sach> list1 = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    if(list.get(i).getTenSach().toLowerCase().contains(edt_search.getText().toString().toLowerCase())){
                        list1.add(list.get(i));
                    }
                }
                adapter.setData(list1);
                if(list1.size()==0){
                    Toast.makeText(getContext(),"Không tìm thấy bất cứ tên sách nào thỏa mãn!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        list = sachDAO.getAll();
        adapter.setData(list);
    }
}