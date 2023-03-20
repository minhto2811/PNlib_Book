package minhtvph26873.poly.pnlib;

import static androidx.core.content.FileProvider.getUriForFile;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import minhtvph26873.poly.pnlib.database.DataBase;
import minhtvph26873.poly.pnlib.fragment.DoanhThuFragment;
import minhtvph26873.poly.pnlib.fragment.LoaiSachFragment;
import minhtvph26873.poly.pnlib.fragment.MatKhauFragment;
import minhtvph26873.poly.pnlib.fragment.PhieuMuonFragment;
import minhtvph26873.poly.pnlib.fragment.SachFragment;
import minhtvph26873.poly.pnlib.fragment.ThanhVienFragment;
import minhtvph26873.poly.pnlib.fragment.ThuThuFragment;
import minhtvph26873.poly.pnlib.fragment.Top10Fragment;

public class MainActivity2 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private TextView textView;
    private ImageView imageView;
    private static final int FRAGMENT_PM = 0;
    private static final int FRAGMENT_LS = 1;
    private static final int FRAGMENT_S = 2;
    private static final int FRAGMENT_TV = 3;
    private static final int FRAGMENT_TT = 4;
    private static final int FRAGMENT_10 = 5;
    private static final int FRAGMENT_DT = 6;
    private static final int FRAGMENT_MK = 7;
    private static int mFRAGMENT_CURRENT = FRAGMENT_PM;
    private String currentPhotoPath;
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        anhxa();
        checkUser();
        toolbar.setTitle("Quản lí phiếu");
        toolbarAddNav();
        replaceFragment(new PhieuMuonFragment());
        navigationView.getMenu().findItem(R.id.item_pm).setChecked(true);
        Intent intent = getIntent();
        String abc = intent.getStringExtra("admin");
        DataBase dataBase = new DataBase(MainActivity2.this);
        String pathAvatar;
        if (abc == null) {
            navigationView.getMenu().findItem(R.id.item_tt).setVisible(false);
            navigationView.getMenu().findItem(R.id.item_dt).setVisible(false);
            if(dataBase.duLieuAvatar(DataLocalManager.getFirstInstall1().getMaTT()) != null){
                pathAvatar = dataBase.duLieuAvatar(DataLocalManager.getFirstInstall1().getMaTT());
                Bitmap bitmap = BitmapFactory.decodeFile(pathAvatar);
                imageView.setImageBitmap(bitmap);
            }

        }else{
            if(dataBase.duLieuAvatar(DataLocalManager.getFirstInstall().getUser()) != null){
                pathAvatar = dataBase.duLieuAvatar(DataLocalManager.getFirstInstall().getUser());
                Bitmap bitmap = BitmapFactory.decodeFile(pathAvatar);
                imageView.setImageBitmap(bitmap);
            }

        }


        navigationView.getHeaderView(0).findViewById(R.id.imv_users).setOnClickListener(v -> {
            PermissionListener permissionlistener = new PermissionListener() {
                @Override
                public void onPermissionGranted() {
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    // Ensure that there's a camera activity to handle the intent
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        // Create the File where the photo should go
                        File photoFile = null;
                        try {
                            photoFile = createImageFile();
                        } catch (IOException ex) {
                            // Error occurred while creating the File

                        }
                        // Continue only if the File was successfully created
                        if (photoFile != null) {
                            Uri photoURI = getUriForFile(MainActivity2.this,
                                    "minhtvph26873.poly.pnlib.MyApplication.provider",
                                    photoFile);


                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                        }
                    }
                }

                @Override
                public void onPermissionDenied(List<String> deniedPermissions) {
                    Toast.makeText(MainActivity2.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
                }
            };
            TedPermission.create()
                    .setPermissionListener(permissionlistener)
                    .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                    .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                    .check();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Gọi lại
            setPic();
            Intent intent = getIntent();
            String abc = intent.getStringExtra("admin");
            DataBase dataBase = new DataBase(MainActivity2.this);
            if (abc != null) {
                dataBase.suaDulieuAvatar(DataLocalManager.getFirstInstall().getUser(),currentPhotoPath);
            }else{
                dataBase.suaDulieuAvatar(DataLocalManager.getFirstInstall1().getMaTT(),currentPhotoPath);
            }
        }
    }

    private void setPic() {
        // Get the dimensions of the View
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(currentPhotoPath, bmOptions);

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.max(1, Math.min(photoW / targetW, photoH / targetH));

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        //noinspection deprecation
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
        imageView.setImageBitmap(bitmap);
        imageView.setRotation(0);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }


    @SuppressLint("SetTextI18n")
    private void checkUser() {
        Intent intent = getIntent();
        String thutu = intent.getStringExtra("thuthu");
        String admin = intent.getStringExtra("admin");
        if (admin != null) {
            textView.setText("Hi, Admin");
            imageView.setImageResource(R.drawable.admin);
            navigationView.getHeaderView(0).setBackgroundResource(R.drawable.background_admin);
        }
        if (thutu != null) {
            String tentt = intent.getStringExtra("tentt");
            textView.setText("Hi, Librarian\n" + tentt);
            imageView.setImageResource(R.drawable.thuthu);
            navigationView.getHeaderView(0).setBackgroundResource(R.drawable.background_thuthu);
        }
    }

    private void toolbarAddNav() {
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(MainActivity2.this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void anhxa() {
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navgation_view);
        textView = navigationView.getHeaderView(0).findViewById(R.id.tv_ifo_login);
        imageView = navigationView.getHeaderView(0).findViewById(R.id.imv_users);

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item_pm) {
            if (mFRAGMENT_CURRENT != FRAGMENT_PM) {
                toolbar.setTitle("Quản lí phiếu");
                replaceFragment(new PhieuMuonFragment());
                mFRAGMENT_CURRENT = FRAGMENT_PM;
                navigationView.getMenu().findItem(id).setChecked(true);
                hieuUngSelected(id);
            }
        } else if (id == R.id.item_ls) {
            if (mFRAGMENT_CURRENT != FRAGMENT_LS) {
                toolbar.setTitle("Quản lí loại sách");
                replaceFragment(new LoaiSachFragment());
                mFRAGMENT_CURRENT = FRAGMENT_LS;
                hieuUngSelected(id);
            }
        } else if (id == R.id.item_s) {
            if (mFRAGMENT_CURRENT != FRAGMENT_S) {
                toolbar.setTitle("Quản lí sách");
                replaceFragment(new SachFragment());
                mFRAGMENT_CURRENT = FRAGMENT_S;
                hieuUngSelected(id);
            }
        } else if (id == R.id.item_tv) {
            if (mFRAGMENT_CURRENT != FRAGMENT_TV) {
                toolbar.setTitle("Quản lí thành viên");
                replaceFragment(new ThanhVienFragment());
                mFRAGMENT_CURRENT = FRAGMENT_TV;
                hieuUngSelected(id);
            }
        } else if (id == R.id.item_tt) {
            if (mFRAGMENT_CURRENT != FRAGMENT_TT) {
                toolbar.setTitle("Quản lí thủ thư");
                replaceFragment(new ThuThuFragment());
                mFRAGMENT_CURRENT = FRAGMENT_TT;
                hieuUngSelected(id);
            }


        } else if (id == R.id.item_10) {
            if (mFRAGMENT_CURRENT != FRAGMENT_10) {
                toolbar.setTitle("Top 10 sách mượn nhiều nhất");
                replaceFragment(new Top10Fragment());
                mFRAGMENT_CURRENT = FRAGMENT_10;
                hieuUngSelected(id);
            }
        } else if (id == R.id.item_dt) {
            if (mFRAGMENT_CURRENT != FRAGMENT_DT) {
                toolbar.setTitle("Thống kê doanh thu");
                replaceFragment(new DoanhThuFragment());
                mFRAGMENT_CURRENT = FRAGMENT_DT;
                hieuUngSelected(id);
            }
        } else if (id == R.id.item_mk) {
            if (mFRAGMENT_CURRENT != FRAGMENT_MK) {
                toolbar.setTitle("Thay đổi mật khẩu");
                replaceFragment(new MatKhauFragment());
                mFRAGMENT_CURRENT = FRAGMENT_MK;
                hieuUngSelected(id);
            }
        } else if (id == R.id.item_dx) {
            DataLocalManager.setFirstInstall1(null);
            DataLocalManager.setFirstInstall(null);
            Intent intentCheck = getIntent();
            String adc = intentCheck.getStringExtra("admin");
            if(adc != null){
                Intent intent = new Intent(MainActivity2.this,LoginAdminActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_enter_to_right,R.anim.anim_exit_to_right);
            }else {
                Intent intent = new Intent(MainActivity2.this,LoginGuestActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_enter_to_right,R.anim.anim_exit_to_right);
            }

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void hieuUngSelected(int id) {
        int[] mang = {R.id.item_pm, R.id.item_ls, R.id.item_s, R.id.item_tv, R.id.item_10, R.id.item_dt, R.id.item_mk, R.id.item_dx};
        for (int j : mang) {
            navigationView.getMenu().findItem(j).setChecked(id == j);
        }
    }

    private int count = 0;

    @Override
    public void onBackPressed() {
        count++;
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
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

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.commit();
    }
}