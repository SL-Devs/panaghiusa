package com.sldevs.panaghiusa.ContributionSteps_Plastic;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.zxing.WriterException;
import com.shuhart.stepview.StepView;
import com.sldevs.panaghiusa.Cart;
import com.sldevs.panaghiusa.Home_Screen;
import com.sldevs.panaghiusa.R;
import com.sldevs.panaghiusa.Sign_Up;
import com.sldevs.panaghiusa.databinding.ActivityPs1Binding;
import com.sldevs.panaghiusa.ml.Model;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

public class P_S1 extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    public StepView stepView;
    Button btnUpload,btnCapture,btnCategory,btnNextS1,btnAddCart,btnShowCart,btnAddToCart,btnNo,btnDone;
    ImageView btnBackS1,ivScanned,ivPopScanned,ivClose,ivCloseCont;
    TextView tvType,tvConfidence,tvAccurateness;
    Spinner sPlasticCategory;
    public static final int GET_FROM_GALLERY = 3;
    int imageSize = 224;
    int imageID[];
    public ArrayList<Bitmap> bitmapArray = new ArrayList<Bitmap>();
    public ArrayList<String> plasticType = new ArrayList<String>();
    int cartValue = 0;
    String items = "";

    String selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ps1);

        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        btnCapture = findViewById(R.id.btnCapture);
        btnUpload = findViewById(R.id.btnUpload);
        btnNextS1 = findViewById(R.id.btnNextS1);
        btnCategory = findViewById(R.id.btnCategory);
        btnAddCart = findViewById(R.id.btnAddCart);
        btnShowCart = findViewById(R.id.btnShowCart);
        tvAccurateness = findViewById(R.id.tvAccurateness);
        tvConfidence = findViewById(R.id.tvConfidence);
        tvType = findViewById(R.id.tvType);
        stepView = findViewById(R.id.step_view);
        btnBackS1 = findViewById(R.id.btnBackS1);
        ivScanned = findViewById(R.id.ivScanned);
        ivClose = findViewById(R.id.ivClose);
        stepView.getState()
                .animationType(StepView.ANIMATION_ALL)
                .steps(new ArrayList<String>() {{
                    add("First Step");
                    add("Second Step");
                    add("Third Step");
                }})
                .stepsNumber(3)
                .animationDuration(getResources().getInteger(android.R.integer.config_shortAnimTime))
                .commit();

        setCartDefault();


        btnBackS1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if(checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                        Intent openCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(openCamera,1);
                    }else{
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
                    }
                }

            }
        });
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
            }
        });

        btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartPop();

            }
        });
        btnShowCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(P_S1.this, C.class);
                i.putStringArrayListExtra("plasticTypeList",plasticType);
                startActivity(i);
            }
        });
        btnNextS1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(P_S1.this,P_S2.class);
                i.putExtra("plasticURL", cartValue);
                i.putExtra("items", items);
                i.putExtra("itemNo", String.valueOf(cartValue));
                i.putStringArrayListExtra("plasticTypeList",plasticType);
                startActivity(i);

            }
        });





        btnCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dialog builder = new Dialog(P_S1.this);
                builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
                builder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                builder.setContentView(R.layout.plastic_category);
                builder.show();

                String[] plasticList = getResources().getStringArray(R.array.plasticTypes);
                Arrays.sort(plasticList);

                ArrayAdapter<String> plasticlistAdaptor = new ArrayAdapter<String>(P_S1.this, android.R.layout.simple_spinner_item,plasticList);
                plasticlistAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sPlasticCategory = builder.findViewById(R.id.sPlasticCategory);
                sPlasticCategory.setAdapter(plasticlistAdaptor);

                btnDone = builder.findViewById(R.id.btnDone);
                ivCloseCont = builder.findViewById(R.id.ivCloseCont);


                sPlasticCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        String selectedItem = adapterView.getItemAtPosition(i).toString();
                        selected = selectedItem;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                btnDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tvType.setText(selected);
                        builder.dismiss();
                    }
                });

                ivCloseCont.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        builder.dismiss();
                    }
                });


            }
        });

//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.steps_frame,new S1()).commit();

    }
    public void cartPop(){
//        BitmapDrawable drawable = (BitmapDrawable) ivScanned.getDrawable();
//        Bitmap bitmap = drawable.getBitmap();
//        bitmapArray.add(cartValue, bitmap);
//        plasticType.add(cartValue, tvType.getText().toString());
        Dialog builder = new Dialog(P_S1.this);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.setContentView(R.layout.add_to_cart);
        builder.setCancelable(false);
        builder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        builder.show();

//        Bitmap image = bitmapArray.get(0);
//        int dimension = Math.min(image.getWidth(), image.getHeight());
//        image = ThumbnailUtils.extractThumbnail(image,dimension, dimension);

        ivClose = builder.findViewById(R.id.ivClose);
        btnAddToCart = builder.findViewById(R.id.btnAddToCart);
        btnNo = builder.findViewById(R.id.btnNo);


        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String id = FirebaseAuth.getInstance().getUid();
                StorageReference tempPlasticImage = storageReference.child(id + ".png");
                StorageReference tempPlasticImageRef = storageReference.child("UsersCartTempLog/" + id + "_"+cartValue + "/" + id + "cartItem_" + cartValue + ".png");
                tempPlasticImage.getName().equals(tempPlasticImageRef.getName());
                tempPlasticImage.getPath().equals(tempPlasticImageRef.getPath());


                BitmapDrawable drawable = (BitmapDrawable) ivScanned.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] data = baos.toByteArray();

                if(cartValue == 0){
                    tempPlasticImageRef.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            FirebaseDatabase.getInstance().getReference("UsersCartTempLog/" + id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });

                }

                UploadTask upload = tempPlasticImageRef.putBytes(data);
                upload.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        String id = FirebaseAuth.getInstance().getUid();

                        Cart c = new Cart(tvType.getText().toString(),"https://firebasestorage.googleapis.com/v0/b/panaghiusa-28480.appspot.com/o/UsersCartTempLog%2F"+ id+ "_" +cartValue +"%2F"+id+"cartItem_" + cartValue+ ".png?alt=media&token=57cf74b7-5dbc-4bdf-aaf3-69b6176b6ecd");
                        FirebaseDatabase.getInstance().getReference("UsersCartTempLog")
                                .child(id + "/" + id + "_" + cartValue)
                                .setValue(c).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(P_S1.this,"Added to cart successfully!",Toast.LENGTH_SHORT).show();
                                    if(cartValue == 0){
                                        items += tvType.getText().toString();
                                    }
                                    if(cartValue > 0){
                                        items += "\n" + tvType.getText().toString();
                                    }
                                    plasticType.add(cartValue, tvType.getText().toString());
                                    ivScanned.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.upload));
                                    System.out.println(plasticType.get(cartValue));
                                    cartValue = cartValue + 1;
                                    tvType.setText("Type of Plastic");
                                    tvConfidence.setText("");
                                    btnCategory.setVisibility(View.GONE);
                                    tvAccurateness.setVisibility(View.GONE);
                                    btnNextS1.setVisibility(View.VISIBLE);
                                    btnAddCart.setVisibility(View.GONE);
                                    builder.dismiss();
                                }else{

                                }
                            }
                        });



                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(P_S1.this,e.toString(),Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.dismiss();
            }
        });

    }
    private void classifyImage(Bitmap image) {
        try {
            Model model = Model.newInstance(getApplicationContext());

            // Creates inputs for reference.
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.FLOAT32);
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4*imageSize*imageSize*3);
            byteBuffer.order(ByteOrder.nativeOrder());

            int [] intValues = new int[imageSize * imageSize];
            image.getPixels(intValues, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());
            int pixel = 0;
            for(int i = 0; i < imageSize;i++){
                for(int j = 0; j< imageSize; j++){
                    int val = intValues[pixel++];
                    byteBuffer.putFloat(((val >> 16) & 0xFF) * (1.f / 255.f));
                    byteBuffer.putFloat(((val >> 8) & 0xFF) * (1.f / 255.f));
                    byteBuffer.putFloat((val & 0xFF) * (1.f / 255.f));
                }
            }
            inputFeature0.loadBuffer(byteBuffer);

            // Runs model inference and gets result.
            Model.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

            float[] confidences = outputFeature0.getFloatArray();
            // find the index of the class with the biggest confidence.

            int maxPos = 0;
            float maxConfidence = 0;
            for(int i = 0; i < confidences.length; i++){
                if(confidences[i] > maxConfidence){
                    maxConfidence = confidences[i];
                    maxPos = i;
                }
            }
            String[] classes = {"Plastic Bottle", "CD","Perfume Bottle"};
            tvType.setText(classes[maxPos]);

            String s = "";
            for(int i = 0; i < classes.length; i++){
                s += String.format("%s: %.1f%%\n", classes[i], confidences[i] * 100);
            }
            tvConfidence.setText(s);

            // Releases model resources if no longer used.
            model.close();
        } catch (IOException e) {
            // TODO Handle the exception
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == 1 && resultCode== RESULT_OK){
            Bitmap image = (Bitmap) data.getExtras().get("data");
            int dimension = Math.min(image.getWidth(), image.getHeight());
            image = ThumbnailUtils.extractThumbnail(image,dimension, dimension);
            ivScanned.setImageBitmap(image);

            image = Bitmap.createScaledBitmap(image, imageSize, imageSize,false);
            classifyImage(image);
            tvAccurateness.setVisibility(View.VISIBLE);
            btnCategory.setVisibility(View.VISIBLE);

            btnAddCart.setVisibility(View.VISIBLE);
            btnShowCart.setVisibility(View.VISIBLE);
        }
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap image = null;
            try {
                image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                int dimension = Math.min(image.getWidth(), image.getHeight());
                image = ThumbnailUtils.extractThumbnail(image,dimension, dimension);
                ivScanned.setImageBitmap(image);

                image = Bitmap.createScaledBitmap(image, imageSize, imageSize,false);
                classifyImage(image);
                tvAccurateness.setVisibility(View.VISIBLE);
                btnCategory.setVisibility(View.VISIBLE);

                btnAddCart.setVisibility(View.VISIBLE);
                btnShowCart.setVisibility(View.VISIBLE);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }


    }
    public void setCartDefault(){
        String id = FirebaseAuth.getInstance().getUid();
        StorageReference tempPlasticImageRef = storageReference.child("UsersCartTempLog/" + id + "_"+cartValue + "/" + id + "cartItem_" + cartValue + ".png");
        if(cartValue == 0){
            tempPlasticImageRef.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    FirebaseDatabase.getInstance().getReference("UsersCartTempLog/" + id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

        }
    }
}