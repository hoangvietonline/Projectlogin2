package hoangviet.ndhv.projectlogin2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity{
    ImageView imageView;
    Integer REQUEST_CAMERA = 1, SELECT_FILE = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.imageHinhAnh);


        ImageButton btn = findViewById(R.id.btnAddImage);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectImage();
            }
        });
    }

    private void SelectImage(){
        final CharSequence[] items = {"Camera","Gallery","Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Thêm hình ảnh");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (items[i].equals("Camera")){
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent,REQUEST_CAMERA);
                }else if (items[i].equals("Gallery")){
                    Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent.createChooser(intent, "select File"),SELECT_FILE);
                    startActivityForResult(intent,SELECT_FILE);
                }else if (items[i].equals("Cannel")){
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == Activity.RESULT_OK){
            if (requestCode == REQUEST_CAMERA){
//                Bundle bundle = data.getExtras();
//                final Bitmap bmp = (Bitmap) bundle.get("data");
//                imageView.setImageBitmap(bmp);
                Bitmap bmp = (Bitmap)data.getExtras().get("data");
                imageView.setImageBitmap(bmp);

            }else if (requestCode == SELECT_FILE){
                Uri selectedImageUri = data.getData();
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),selectedImageUri);
                    imageView.setImageBitmap(bitmap);
                }catch (IOException e){
                    e.printStackTrace();
                }


            }
        }
//        switch (requestCode){
//            case 0:
//                if (requestCode == RESULT_OK){
//                    Uri selectedImage = data.getData();
//                    imageView.setImageURI(selectedImage);
//                }
//                break;
//            case 1:
//                if (requestCode == RESULT_OK){
//                    Uri selectedImage = data.getData();
//                    imageView.setImageURI(selectedImage);
//                }
//                break;
//        }
    }

}
