package minhtvph26873.poly.pnlib.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import minhtvph26873.poly.pnlib.R;

import minhtvph26873.poly.pnlib.DataLocalManager;
import minhtvph26873.poly.pnlib.dao.AdminDAO;
import minhtvph26873.poly.pnlib.dao.ThuThuDAO;
import minhtvph26873.poly.pnlib.model.Admin;
import minhtvph26873.poly.pnlib.model.ThuThu;

import com.google.android.material.textfield.TextInputLayout;

import java.util.List;


public class MatKhauFragment extends Fragment {

    public MatKhauFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mat_khau, container, false);
    }

    private EditText edt_nhap_mat_khau_cu, edt_nhap_mat_khau_moi, edt_nhap_lai_mat_khau_moi;
    private TextInputLayout til_mat_khau_cu, til_mat_khau_moi, til_mat_khau_moi2;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edt_nhap_mat_khau_cu = view.findViewById(R.id.edt_nhap_mat_khau_cu);
        edt_nhap_mat_khau_moi = view.findViewById(R.id.edt_nhap_mat_khau_moi);
        edt_nhap_lai_mat_khau_moi = view.findViewById(R.id.edt_nhap_lai_mat_khau_moi);
        til_mat_khau_cu = view.findViewById(R.id.til_mat_khau_cu);
        til_mat_khau_moi = view.findViewById(R.id.til_mat_khau_moi);
        til_mat_khau_moi2 = view.findViewById(R.id.til_mat_khau_moi2);
        Button btn_thay_doi_mat_khau = view.findViewById(R.id.btn_thay_doi_mat_khau);

        btn_thay_doi_mat_khau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdminDAO adminDAO = new AdminDAO(v.getContext());
                ThuThuDAO thuThuDAO = new ThuThuDAO(v.getContext());
                String mkcu = edt_nhap_mat_khau_cu.getText().toString().trim();
                String mkmoi = edt_nhap_mat_khau_moi.getText().toString().trim();
                String mkmoi2 = edt_nhap_lai_mat_khau_moi.getText().toString().trim();
                if (mkcu.isEmpty()) {
                    til_mat_khau_cu.setError("Không được để trống trường này!");
                    edt_nhap_mat_khau_cu.requestFocus();
                    return;
                }
                til_mat_khau_cu.setError("");
                if (mkmoi.isEmpty()) {
                    til_mat_khau_moi.setError("Không được để trống trường này!");
                    edt_nhap_mat_khau_moi.requestFocus();
                    return;
                }
                til_mat_khau_moi.setError("");
                if (mkmoi2.isEmpty()) {
                    til_mat_khau_moi2.setError("Không được để trống trường này!");
                    edt_nhap_lai_mat_khau_moi.requestFocus();
                    return;
                }
                til_mat_khau_moi2.setError("");

                Intent intent = getActivity().getIntent();
                String pl1 = intent.getStringExtra("admin");
                String pl2 = intent.getStringExtra("thuthu");
                if (pl1 != null) {
                    List<Admin> list = adminDAO.getAll();
                    Admin admin = list.get(0);
                    if (!mkcu.equals(admin.getPass())) {
                        til_mat_khau_cu.setError("Mật khẩu cũ không khớp!");
                        edt_nhap_mat_khau_cu.setTransformationMethod(null);
                        edt_nhap_mat_khau_cu.setSelection(mkcu.length());
                        return;
                    }
                    edt_nhap_mat_khau_cu.setTransformationMethod(new PasswordTransformationMethod());
                    til_mat_khau_cu.setError("");
                    if (mkmoi.equals(mkcu)) {
                        til_mat_khau_cu.setError("Mật khẩu không được giống nhau");
                        til_mat_khau_moi.setError("Mật khẩu không được giống nhau");
                        edt_nhap_mat_khau_cu.setTransformationMethod(null);
                        edt_nhap_mat_khau_moi.setTransformationMethod(null);
                        edt_nhap_mat_khau_moi.setSelection(mkmoi.length());
                        return;
                    }
                    til_mat_khau_cu.setError("");
                    til_mat_khau_moi.setError("");
                    edt_nhap_mat_khau_cu.setTransformationMethod(new PasswordTransformationMethod());
                    edt_nhap_mat_khau_moi.setTransformationMethod(new PasswordTransformationMethod());
                    if (!mkmoi.equals(mkmoi2)) {
                        til_mat_khau_moi.setError("Mật khẩu mới không trùng khớp!");
                        til_mat_khau_moi2.setError("Mật khẩu mới không trùng khớp!");
                        edt_nhap_mat_khau_moi.setTransformationMethod(null);
                        edt_nhap_lai_mat_khau_moi.setTransformationMethod(null);
                        edt_nhap_lai_mat_khau_moi.setSelection(mkmoi2.length());
                        return;
                    }
                    til_mat_khau_moi.setError("");
                    til_mat_khau_moi2.setError("");

                    admin.setPass(mkmoi2);
                    adminDAO.update(admin);
                    Toast.makeText(v.getContext(), "Thay đổi mật khẩu mới thành công", Toast.LENGTH_SHORT).show();
                    lammoi();
                } else if (pl2 != null) {
                    List<ThuThu> thuThuList = thuThuDAO.getAll();
                    int check = 0;
                    int index = 0;
                    for (int i = 0; i < thuThuList.size(); i++) {
                        if (DataLocalManager.getFirstInstall1().getMaTT().equals(thuThuList.get(i).getMaTT()) && edt_nhap_mat_khau_cu.getText().toString().equals(thuThuList.get(i).getMatKhau())) {
                            check++;
                            index = i;
                        }
                    }
                    if (check == 0) {
                        til_mat_khau_cu.setError("Mật khẩu cũ không khớp!");
                        edt_nhap_mat_khau_cu.setTransformationMethod(null);
                        edt_nhap_mat_khau_cu.setSelection(mkcu.length());
                        return;
                    }
                    edt_nhap_mat_khau_cu.setTransformationMethod(new PasswordTransformationMethod());
                    til_mat_khau_cu.setError("");
                    if (mkmoi.equals(mkcu)) {
                        til_mat_khau_cu.setError("Mật khẩu không được giống nhau");
                        til_mat_khau_moi.setError("Mật khẩu không được giống nhau");
                        edt_nhap_mat_khau_cu.setTransformationMethod(null);
                        edt_nhap_mat_khau_moi.setTransformationMethod(null);
                        edt_nhap_mat_khau_moi.setSelection(mkmoi.length());
                        return;
                    }
                    til_mat_khau_cu.setError("");
                    til_mat_khau_moi.setError("");
                    edt_nhap_mat_khau_cu.setTransformationMethod(new PasswordTransformationMethod());
                    edt_nhap_mat_khau_moi.setTransformationMethod(new PasswordTransformationMethod());

                    if (!mkmoi.equals(mkmoi2)) {
                        til_mat_khau_moi.setError("Mật khẩu mới không trùng khớp!");
                        til_mat_khau_moi2.setError("Mật khẩu mới không trùng khớp!");
                        edt_nhap_mat_khau_moi.setTransformationMethod(null);
                        edt_nhap_lai_mat_khau_moi.setTransformationMethod(null);
                        edt_nhap_lai_mat_khau_moi.setSelection(mkmoi2.length());
                        return;
                    }
                    til_mat_khau_moi.setError("");
                    til_mat_khau_moi2.setError("");
                    ThuThu thuThu = thuThuList.get(index);
                    thuThu.setMatKhau(mkmoi2);
                    thuThuDAO.update(thuThu);
                    Toast.makeText(v.getContext(), "Thay đổi mật khẩu mới thành công", Toast.LENGTH_SHORT).show();
                    DataLocalManager.setFirstInstall1(thuThu);
                    lammoi();
                }


            }

            private void lammoi() {
                edt_nhap_mat_khau_cu.setText("");
                edt_nhap_mat_khau_moi.setText("");
                edt_nhap_lai_mat_khau_moi.setText("");
                edt_nhap_lai_mat_khau_moi.clearFocus();
            }

        });


    }


}