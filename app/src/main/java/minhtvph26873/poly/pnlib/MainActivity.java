package minhtvph26873.poly.pnlib;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import minhtvph26873.poly.pnlib.R;

import minhtvph26873.poly.pnlib.model.Admin;
import minhtvph26873.poly.pnlib.model.ThuThu;

public class MainActivity extends AppCompatActivity {
    private Button btn_lgAdmin, btn_lgGuest;
    private Intent intent;
    private LinearLayout layout_hello;
    private ImageView imv_logo;
    private TextView tv_mess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhxa();
        manhinhchao();
        luachon();
    }

    private void kiemtrataikhoan() {
        Admin admin = DataLocalManager.getFirstInstall();
        ThuThu thuThu = DataLocalManager.getFirstInstall1();
        if (admin != null|| thuThu != null) {
            if (!admin.getUser().equals("null") && admin.getUser().length() > 0) {
                Toast.makeText(this,"Đã đăng nhập tài khoản "+admin.getUser(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                intent.putExtra("admin", "admin");
                startActivity(intent);
                return;
            }
            if(!thuThu.getMaTT().equals("null") && thuThu.getMaTT().length() > 0){
                Toast.makeText(this,"Đã đăng nhập tài khoản "+thuThu.getMaTT(),Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(MainActivity.this,MainActivity2.class);
                intent1.putExtra("thuthu", "thuthu");
                intent1.putExtra("tentt", thuThu.getHoTen());
                intent1.putExtra("passtt", thuThu.getMatKhau());
                return;
            }
        }else{
            layout_hello.setVisibility(View.GONE);
        }
    }

    private void manhinhchao() {
        Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_logo);
        imv_logo.setAnimation(animation);
       imv_logo.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
//               layout_hello.setVisibility(View.INVISIBLE);
               intent = new Intent(MainActivity.this, LoginAdminActivity.class);
               startActivity(intent);
               overridePendingTransition(R.anim.anim_enter_to_left, R.anim.anim_exit_to_left);
           }
       });
    }

    private void luachon() {
        btn_lgAdmin.setOnClickListener(v -> {
            intent = new Intent(MainActivity.this, LoginAdminActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.anim_enter_to_left, R.anim.anim_exit_to_left);
        });
        btn_lgGuest.setOnClickListener(v -> {
            intent = new Intent(MainActivity.this, LoginGuestActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.anim_enter_to_left, R.anim.anim_exit_to_left);
        });
    }

    private void anhxa() {
        btn_lgAdmin = findViewById(R.id.btn_lgAdmin);
        btn_lgGuest = findViewById(R.id.btn_lgGuest);
        layout_hello = findViewById(R.id.layout_hello);
        imv_logo = findViewById(R.id.imv_logo);
    }

    private int count = 0;

    @Override
    public void onBackPressed() {
        count++;
        if (count < 2) {
            Toast.makeText(getApplicationContext(), "Nhấn 2 lần để thoát", Toast.LENGTH_SHORT).show();
            Handler handler = new Handler();
            handler.postDelayed(() -> count = 0, 500);
        } else {
            finishAffinity();
            System.exit(0);
            super.onBackPressed();
        }

    }
}