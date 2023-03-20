package minhtvph26873.poly.pnlib.adapter;

import android.app.AlertDialog;
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
import androidx.recyclerview.widget.RecyclerView;

import minhtvph26873.poly.pnlib.R;

import minhtvph26873.poly.pnlib.dao.PhieuMuonDAO;
import minhtvph26873.poly.pnlib.dao.ThuThuDAO;
import minhtvph26873.poly.pnlib.model.PhieuMuon;
import minhtvph26873.poly.pnlib.model.ThuThu;

import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class ThuThuAdapter extends RecyclerView.Adapter<ThuThuAdapter.ThuThuHolder> {
    private Context context;
    private List<ThuThu> list;

    public ThuThuAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<ThuThu> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ThuThuHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_thu_thu, parent, false);
        return new ThuThuHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThuThuHolder holder, int position) {
        ThuThu thuThu = list.get(position);
        if (thuThu != null) {
            holder.tv_ma_thu_thu.setText("Mã thủ thư: " + thuThu.getMaTT());
            holder.tv_ten_thu_thu.setText(thuThu.getHoTen());
            holder.tv_mat_khau_thu_thu.setText("Pass: " + thuThu.getMatKhau());
            //----------------------------------
            holder.btn_xoa_thu_thu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle("Xác nhận xóa thủ thư " + thuThu.getHoTen() + "?");
                    builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            PhieuMuonDAO phieuMuonDAO = new PhieuMuonDAO(context);
                            List<PhieuMuon> phieuMuonList = phieuMuonDAO.getAll();
                            int o = 0;
                            String a = "";
                            for (int i = 0; i < phieuMuonList.size(); i++) {
                                if (thuThu.getMaTT().equals(phieuMuonList.get(i).getMaTT())) {
                                    o++;
                                    a += phieuMuonList.get(i).getMaPhieuMuon() + " ";
                                }
                            }
                            if (o == 0) {
                                ThuThuDAO thuThuDAO = new ThuThuDAO(context);
                                thuThuDAO.delete(thuThu.getMaTT());
                                notifyDataSetChanged();
                                Toast.makeText(context, "Đã xóa thủ thư " + thuThu.getHoTen(), Toast.LENGTH_SHORT).show();
                                list.remove(thuThu);
                            } else {
                                Toast.makeText(context, "Thủ thư " + thuThu.getHoTen() + " đang tồn tại trong phiếu mượn " + a, Toast.LENGTH_SHORT).show();
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
            //-----------------------------
            String tencu = thuThu.getHoTen();
            String matkhaucu = thuThu.getMatKhau();

            holder.btn_sua_thu_thu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    View view = LayoutInflater.from(v.getContext()).inflate(R.layout.layout_edit_thu_thu, null);
                    //------------------------------------
                    TextInputLayout til_sua_ten_thu_thu = view.findViewById(R.id.til_sua_ten_thu_thu);
                    TextInputLayout til_sua_mat_khau_thu_thu = view.findViewById(R.id.til_sua_mat_khau_thu_thu);
                    EditText edt_sua_ten_thu_thu = view.findViewById(R.id.edt_sua_ten_thu_thu);
                    EditText edt_sua_mat_khau_thu_thu = view.findViewById(R.id.edt_sua_mat_khau_thu_thu);
                    Button btn_sua_thu_thu_1 = view.findViewById(R.id.btn_sua_thu_thu_1);
                    btn_sua_thu_thu_1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String tenmoi = edt_sua_ten_thu_thu.getText().toString();
                            String matkhaumoi = edt_sua_mat_khau_thu_thu.getText().toString();
                            if (tenmoi.isEmpty()) {
                                tenmoi = tencu;
                            }
                            if (matkhaumoi.isEmpty()) {
                                matkhaumoi = matkhaucu;
                            }
                            if (matkhaumoi.length() < 5) {
                                til_sua_mat_khau_thu_thu.setError("Mật khẩu mới chứa ít nhất 5 kí tự!");
                                edt_sua_mat_khau_thu_thu.setSelection(matkhaumoi.length());
                                return;
                            }
                            til_sua_mat_khau_thu_thu.setError("");
                            thuThu.setHoTen(tenmoi);
                            thuThu.setMatKhau(matkhaumoi);
                            ThuThuDAO thuThuDAO = new ThuThuDAO(v.getContext());
                            thuThuDAO.update(thuThu);
                            notifyDataSetChanged();
                            Toast.makeText(v.getContext(), "Sửa thành công", Toast.LENGTH_SHORT).show();
                        }
                    });

                    //------------------------------------
                    builder.setView(view);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (list.size() > 0) {
            return list.size();
        }
        return 0;
    }

    public class ThuThuHolder extends RecyclerView.ViewHolder {
        private TextView tv_ma_thu_thu, tv_ten_thu_thu, tv_mat_khau_thu_thu;
        private ImageButton btn_sua_thu_thu, btn_xoa_thu_thu;

        public ThuThuHolder(@NonNull View itemView) {
            super(itemView);
            tv_ma_thu_thu = itemView.findViewById(R.id.tv_ma_thu_thu);
            tv_ten_thu_thu = itemView.findViewById(R.id.tv_ten_thu_thu);
            tv_mat_khau_thu_thu = itemView.findViewById(R.id.tv_mat_khau_thu_thu);
            btn_sua_thu_thu = itemView.findViewById(R.id.btn_sua_thu_thu);
            btn_xoa_thu_thu = itemView.findViewById(R.id.btn_xoa_thu_thu);
        }
    }
}
