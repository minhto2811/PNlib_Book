package minhtvph26873.poly.pnlib.fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import minhtvph26873.poly.pnlib.R;

import minhtvph26873.poly.pnlib.dao.ThongKeDAO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class DoanhThuFragment extends Fragment {


    public DoanhThuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_doanh_thu, container, false);
    }

    private EditText edt_ngay_bat_dau;
    private EditText edt_ngay_ket_thuc;
    private TextView tv_ket_qua;
    private ThongKeDAO thongKeDAO;

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edt_ngay_bat_dau = view.findViewById(R.id.edt_ngay_bat_dau);
        edt_ngay_ket_thuc = view.findViewById(R.id.edt_ngay_ket_thuc);
        Button btn_tim = view.findViewById(R.id.btn_tim);
        ListView listView = view.findViewById(R.id.lv_lich_su_thong_ke);
        List<String> list = new ArrayList<>();
        tv_ket_qua = view.findViewById(R.id.tv_ket_qua);
        Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DAY_OF_MONTH);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        //-----------------------------------------------------------------
        edt_ngay_bat_dau.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), (view1, year, month, dayOfMonth) -> {
                calendar.set(year, month, dayOfMonth);
                @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                edt_ngay_bat_dau.setText(simpleDateFormat.format(calendar.getTime()));
            }, nam, thang, ngay);
            datePickerDialog.show();
        });
        //------------------------------------------------------------------
        edt_ngay_ket_thuc.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), (view12, year, month, dayOfMonth) -> {
                calendar.set(year, month, dayOfMonth);
                @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                edt_ngay_ket_thuc.setText(simpleDateFormat.format(calendar.getTime()));
            }, nam, thang, ngay);
            datePickerDialog.show();
        });
        //----------------------------------------------------------------------------
        btn_tim.setOnClickListener(v -> {
            String ngaybd = edt_ngay_bat_dau.getText().toString().trim();
            String ngaykt = edt_ngay_ket_thuc.getText().toString().trim();
            String date = "^([0-2][0-9]|3[0-1])/(0[0-9]|1[0-2])/([0-9][0-9])?[0-9][0-9]$";
            if (ngaybd.isEmpty()) {
                edt_ngay_bat_dau.setHint("Hãy nhập ngày bắt đầu");
                edt_ngay_bat_dau.setHintTextColor(Color.RED);
                return;
            }
            if (!ngaybd.matches(date)) {
                edt_ngay_bat_dau.setText("");
                edt_ngay_bat_dau.setHint("Ngày bắt đầu sai định dạng!");
                edt_ngay_bat_dau.setHintTextColor(Color.RED);
                return;
            }
            if (ngaykt.isEmpty()) {
                edt_ngay_ket_thuc.setHint("Hãy nhập ngày kết thúc");
                edt_ngay_ket_thuc.setHintTextColor(Color.RED);
                return;
            }
            if (!ngaykt.matches(date)) {
                edt_ngay_ket_thuc.setText("");
                edt_ngay_ket_thuc.setHint("Ngày kết thúc sai định dạng!");
                edt_ngay_ket_thuc.setHintTextColor(Color.RED);
                return;
            }
            if (ngaybd.compareTo(ngaykt) > 0) {
                Toast.makeText(getContext(), "Ngày bắt đầu phải nhỏ hơn ngày kết thúc", Toast.LENGTH_SHORT).show();
                return;
            }
            thongKeDAO = new ThongKeDAO(getActivity());
            int doanhthu = thongKeDAO.getDoanhThu(ngaybd, ngaykt);
            tv_ket_qua.setText(doanhthu + "K");
            list.add("Từ " + ngaybd + " đến " + ngaykt + " doanh thu: " + doanhthu + "K");
            ArrayList<String> list1 = new ArrayList<>();
            for (int i = list.size() - 1; i >= 0; i--) {
                list1.add(list.get(i));
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, list1);
            listView.setAdapter(adapter);
        });
    }
}