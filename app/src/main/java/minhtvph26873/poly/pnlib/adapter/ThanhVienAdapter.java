package minhtvph26873.poly.pnlib.adapter;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import minhtvph26873.poly.pnlib.R;

import minhtvph26873.poly.pnlib.dao.PhieuMuonDAO;
import minhtvph26873.poly.pnlib.dao.ThanhVienDAO;
import minhtvph26873.poly.pnlib.model.PhieuMuon;
import minhtvph26873.poly.pnlib.model.ThanhVien;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class ThanhVienAdapter extends RecyclerView.Adapter<ThanhVienAdapter.ThanhVienHolder> {


    private Context context;
    private List<ThanhVien> thanhVienList;

    public ThanhVienAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<ThanhVien> thanhVienList) {
        this.thanhVienList = thanhVienList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ThanhVienHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_thanh_vien, parent, false);
        return new ThanhVienHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThanhVienHolder holder, int position) {
        ThanhVien thanhVien = thanhVienList.get(position);
        if (thanhVien != null) {
            holder.tv_ma_thanh_vien.setText("Mã thành viên: " + thanhVien.getMaTV());
            holder.tv_ten_thanh_vien.setText("Họ tên: " + thanhVien.getHoTen());
            holder.tv_nam_sinh_thanh_vien.setText("Năm sinh: " + thanhVien.getNamSinh());
            if(thanhVien.getGioiTinh().contains("Nam")){
                holder.tv_gioi_tinh_thanh_vien.setTextColor(Color.GREEN);
            }else {
                holder.tv_gioi_tinh_thanh_vien.setTextColor(Color.YELLOW);
            }
            holder.tv_gioi_tinh_thanh_vien.setText("Giới tính: " + thanhVien.getGioiTinh());

            holder.btn_xoa_thanh_vien.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Xác nhận xóa thông tin thành viên " + thanhVien.getHoTen());
                    //------------------------------------------------------
                    builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int index = 0;
                            String pos = "";
                            PhieuMuonDAO phieuMuonDAO = new PhieuMuonDAO(context);
                            List<PhieuMuon> phieuMuonList = phieuMuonDAO.getAll();
                            for (int i = 0; i < phieuMuonList.size(); i++) {
                                if (thanhVien.getMaTV() == phieuMuonList.get(i).getMaTV()) {
                                    index++;
                                    pos += phieuMuonList.get(i).getMaPhieuMuon() + " ";
                                }
                            }
                            if (index != 0) {
                                Toast.makeText(v.getContext(), "Thành viên " + thanhVien.getHoTen() + " đang tồn tài trong phiếu mượn " + pos, Toast.LENGTH_SHORT).show();
                                return;
                            } else {
                                ThanhVienDAO thanhVienDAO = new ThanhVienDAO(context);
                                thanhVienDAO.delete(String.valueOf(thanhVien.getMaTV()));
                                thanhVienList.remove(thanhVien);
                                notifyDataSetChanged();
                                Toast.makeText(v.getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                    //---------------------------------------------------------------------------
                    builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                    //--------------------------------------------------------------------------


                }
            });
            holder.btn_sua_thanh_vien.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String tencu = thanhVien.getHoTen();
                    String namsinhcu = thanhVien.getNamSinh();
                    int ngaycu = Integer.valueOf(namsinhcu.substring(0, 2));
                    int thangcu = Integer.valueOf(namsinhcu.substring(3, 5));
                    int namcu = Integer.valueOf(namsinhcu.substring(6));
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                    View view = LayoutInflater.from(v.getContext()).inflate(R.layout.layout_edit_thanh_vien, null);
                    EditText edt_sua_ten_thanh_vien = view.findViewById(R.id.edt_sua_ten_thanh_vien);
                    EditText edt_sua_nam_sinh_thanh_vien = view.findViewById(R.id.edt_sua_nam_sinh_thanh_vien);
                    Button btn_sua_thanh_vien_1 = view.findViewById(R.id.btn_sua_thanh_vien_1);
                    //------------------------------------------------
                    edt_sua_nam_sinh_thanh_vien.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Calendar calendar = Calendar.getInstance();
                            DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                    calendar.set(year, month, dayOfMonth);
                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                    edt_sua_nam_sinh_thanh_vien.setText(simpleDateFormat.format(calendar.getTime()));
                                }
                            }, namcu, thangcu - 1, ngaycu);
                            datePickerDialog.show();
                        }
                    });
                    //--------------------------------------------------------------------
                    btn_sua_thanh_vien_1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String tenmoi = edt_sua_ten_thanh_vien.getText().toString();
                            String namsinhmoi = edt_sua_nam_sinh_thanh_vien.getText().toString();
                            if (tenmoi.isEmpty()) {
                                tenmoi = tencu;
                            }
                            if (namsinhmoi.isEmpty()) {
                                namsinhmoi = namsinhcu;
                            }
                            String date = "^([0-2][0-9]||3[0-1])/(0[0-9]||1[0-2])/([0-9][0-9])?[0-9][0-9]$";
                            if (!namsinhmoi.matches(date)) {
                                Toast.makeText(v.getContext(), "Năm sinh sai định dạng", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            thanhVien.setHoTen(tenmoi);
                            thanhVien.setNamSinh(namsinhmoi);
                            ThanhVienDAO thanhVienDAO = new ThanhVienDAO(context);
                            thanhVienDAO.update(thanhVien);
                            Toast.makeText(v.getContext(), "Sửa thành công", Toast.LENGTH_SHORT).show();
                            notifyDataSetChanged();
                        }
                    });

                    //--------------------------
                    builder1.setView(view);
                    AlertDialog alertDialog1 = builder1.create();
                    alertDialog1.show();
                }
            });
            holder.rl_onclick_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    View view = LayoutInflater.from(v.getContext()).inflate(R.layout.layout_info_tv,null);
                    @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView tv_ma = view.findViewById(R.id.tv_ma);
                    TextView tv_ht = view.findViewById(R.id.tv_ht);
                    TextView tv_ns = view.findViewById(R.id.tv_ns);
                    TextView tv_gt = view.findViewById(R.id.tv_gt);
                    tv_ma.setText("Mã: "+thanhVien.getMaTV());
                    tv_ht.setText("Tên: "+thanhVien.getHoTen());
                    tv_ns.setText("Năm sinh: "+thanhVien.getNamSinh());
                    tv_gt.setText("Giới tính: "+thanhVien.getGioiTinh());
                    builder.setView(view);
                    AlertDialog alertDialog  = builder.create();
                    alertDialog.show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (thanhVienList.size() > 0) {
            return thanhVienList.size();
        }
        return 0;
    }

    public class ThanhVienHolder extends RecyclerView.ViewHolder {
        private TextView tv_ma_thanh_vien, tv_ten_thanh_vien, tv_nam_sinh_thanh_vien,tv_gioi_tinh_thanh_vien;
        private ImageButton btn_sua_thanh_vien, btn_xoa_thanh_vien;
        private RelativeLayout rl_onclick_tv;

        public ThanhVienHolder(@NonNull View itemView) {
            super(itemView);
            tv_ma_thanh_vien = itemView.findViewById(R.id.tv_ma_thanh_vien);
            tv_ten_thanh_vien = itemView.findViewById(R.id.tv_ten_thanh_vien);
            tv_nam_sinh_thanh_vien = itemView.findViewById(R.id.tv_nam_sinh_thanh_vien);
            btn_sua_thanh_vien = itemView.findViewById(R.id.btn_sua_thanh_vien);
            btn_xoa_thanh_vien = itemView.findViewById(R.id.btn_xoa_thanh_vien);
            tv_gioi_tinh_thanh_vien = itemView.findViewById(R.id.tv_gioi_tinh_thanh_vien);
            rl_onclick_tv = itemView.findViewById(R.id.rl_onclick_tv);
        }
    }
}
