package com.app.pointme.pointme;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.pointme.pointme.action.PmBaseAction;
import com.app.pointme.pointme.databackend.DbHelper;
import com.app.pointme.pointme.networktask.PmServer;
import com.app.pointme.pointme.utils.ValidationForm;

import butterknife.ButterKnife;

/**
 * Created by goparties on 29/1/16.
 */
abstract public class PmBaseActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_IMAGE = 100;
    public boolean visible;
    protected PmBaseActivity pmBaseActivity;
    private Toast toast;
    Bundle bundle;
    View progress;
    Object progressTag;
    ValidationForm validationForm;

    protected ImageView imageView;
    public static final int PICK_PICTURE_GALLERY = 1919;

    public static final int HOME = 1;
    public static final int PROFILE = 2;
    public static final int POINTME = 3;
    public static final int SETTINGS = 4;
    public PmBaseAction baseAction;

    public int getCurrentMode() {
        return currentMode;
    }

    public void setCurrentMode(int currentMode) {
        this.currentMode = currentMode;
    }

    public int currentMode = 0;

    public static final String BASE_URL_USER_IMAGE = "http://gopartiesstatic.s3.amazonaws.com/images/user/profile/";
    public static final String BASE_URL_USER_COVER = "http://gopartiesstatic.s3.amazonaws.com/images/user/cover/thumb/";
    public static final String BASE_URL_USER_PARTY = "http://gopartiesstatic.s3.amazonaws.com/images/user/cover/thumb/";
    public static final String BASE_URL_USER_POST = "http://gopartiesstatic.s3.amazonaws.com/images/post/thumb/";

    //    @Nullable
//    @Bind(R.id.toolbar)
//    Toolbar toolbar;
    protected DbHelper dbHelper;

    /**
     * function used to pick image from gallery so first check read permission at runtime.
     *
     * @param imageView
     * @see this.checkForPermission()
     */
    public void pickGalleryImage(ImageView imageView) {
        this.imageView = imageView;
        if (checkForPermission()) {
            Intent pickGalleryImage = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            if (pickGalleryImage.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(pickGalleryImage, PICK_PICTURE_GALLERY);
            } else {
                showToast("No Gallery Application found..!!");
            }
        }
    }

    /**
     * function used to check the permission for read image from gallery
     * is permission not granted then showing dialog.
     *
     * @return
     */
    @SuppressWarnings("JavaDoc")
    private boolean checkForPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(pmBaseActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_IMAGE);
            return false;
        }
        return true;
    }

    /**
     * function used to handel response according user
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @SuppressWarnings("JavaDoc")
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_IMAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickGalleryImage(imageView);
                } else {
                    showApologies();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void setPic(ImageView imageView, String path) {
        // Get the dimensions of the View
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor << 1;

        Bitmap bitmap = BitmapFactory.decodeFile(path, bmOptions);

        Matrix mtx = new Matrix();
        // mtx.postRotate(90);

        try {
            ExifInterface ei = new ExifInterface(path);
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    mtx.postRotate(90);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    mtx.postRotate(180);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    mtx.postRotate(270);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Rotating Bitmap
        Bitmap rotatedBMP =
                Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), mtx, true);

        if (rotatedBMP != bitmap) {
            bitmap.recycle();
        }
        Bitmap rotatedBMP1 = Bitmap.createScaledBitmap(rotatedBMP,
                imageView.getWidth(),
                imageView.getHeight(),
                true);
        imageView.setImageBitmap(rotatedBMP1);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
    }

//    public boolean checkUserLogin() {
//        if (getCurrentUser() == null) {
//            LoginPopupFragment loginPopupFragment = new LoginPopupFragment();
//            loginPopupFragment.show(getSupportFragmentManager(), "");
////            showToast("please login first");
//            return false;
//        } else return true;
//    }

    protected void setOnClick() {
        baseAction = PmBaseAction.getInstance();
        baseAction.setContext(pmBaseActivity);
    }

    public boolean isFullScreen() {
        return false;
    }

    public boolean hasParent() {
        return false;
    }

    public String getHeading() {
        return getString(R.string.app_name);
    }

    final public String getBundleString(String key, String def) {
        if (bundle == null)
            return def;
        String value = bundle.getString(key);
        return (value == null) ? def : value;
    }

    final public long getBundleLong(String key, long def) {
        if (bundle == null)
            return def;
        return bundle.getLong(key);
    }


    final public String getBundleString(String key) {
        return getBundleString(key, "");
    }

    final public long getBundleLong(String key) {
        return getBundleLong(key, 0);
    }

    public void handleBundle() {

    }

    public String getTag() {
        return getClass().getSimpleName();
    }

    public int getOrientation() {
        return ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    }


    @SuppressWarnings("ResourceType")
    @Override
    public void setContentView(int layoutResID) {
        setRequestedOrientation(getOrientation());
        if (getSupportActionBar() != null) {
            if (isFullScreen()) {
                getSupportActionBar().hide();
            }
            if (hasParent()) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        setHeading(getHeading());
//        setOnClick();
    }

    public void setHeading(String heading) {
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(heading);
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(heading);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (super.onOptionsItemSelected(item))
            return true;
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pmBaseActivity = this;
        PmServer.initialize(getApplicationContext());
        bundle = getIntent().getExtras();
        if (bundle != null) {
            handleBundle();
        }

        // check device details API
        // hitDeviceDetailsAPI();
    }

    protected abstract void onBackendConnected(DbHelper dbHelper);

   /* public void hitDeviceDetailsAPI() {
        if (GpData.getBooleanData("devicedetails"))
            return;
        GpRequestFactory.requestDeviceDetails(new GpResponse.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response, Object tag) {
                GpData.putBooleanData("devicedetails", true);
            }

            @Override
            public void onError(GpException e, Object tag) {
            }

            @Override
            public void onFinish(Object tag) {
            }
        }, "", gpBaseActivity).execute();
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        visible = true;
        dbHelper = DbHelper.getInstance(pmBaseActivity);
        onBackendConnected(dbHelper);
    }

    @Override
    protected void onStop() {
        super.onStop();
        PmServer.getInstance().cancel(getTag());
        /*GpLocationManager.cancelLocationRequest();
        GpEventManager.getInstance().unSubscribe(this);*/
        System.gc();
        visible = false;
    }

    public void showApologies() {
        showToast("Something went wrong, Please try later..!!");
    }

    public void showToast(Object object) {
        if (object == null)
            return;
        if (toast != null)
            toast.cancel();
        toast = Toast.makeText(pmBaseActivity, object.toString(), Toast.LENGTH_SHORT);
        toast.show();
    }

    public void showProgress(Object progressTag) {
        if (progress == null) {
            progress = View.inflate(this, R.layout.progress, null);
            addContentView(progress
                    , new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                    , ViewGroup.LayoutParams.MATCH_PARENT));
            progress.setOnClickListener(null);
        } else {
            progress.setVisibility(View.VISIBLE);
        }
        this.progressTag = progressTag;
    }

    public boolean isProgressing() {
        return progress != null && progress.getVisibility() == View.VISIBLE;
    }

    @Override
    public void onBackPressed() {
        if (!hideProgress())
            super.onBackPressed();
    }

    public boolean hideProgress() {
        if (isProgressing()) {
            PmServer.getInstance().cancel(progressTag);
            progress.setVisibility(View.GONE);
            return true;
        } else
            return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        GpLocationManager.onActivityResult(requestCode, resultCode);
        if (resultCode != RESULT_OK)
            return;
        switch (requestCode) {
            case PICK_PICTURE_GALLERY:
                // find the path
                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = getContentResolver().query(selectedImage,
                        filePath,
                        null,
                        null,
                        null);
                if (c != null)
                    c.moveToFirst();
                else {

                    return;
                }
                int columnIndex = c.getColumnIndex(MediaStore.Images.Media.DATA);
                String path = c.getString(columnIndex);
                c.close();
                imageView.setTag(path);
                setPic(imageView, path);
                break;
        }
    }

    public GpLauncher launch(Class<? extends PmBaseActivity> cls) {
        return new GpLauncher(this, cls);
    }

    public class GpLauncher {
        private Intent intent;
        private Bundle bundle;
        private boolean finishThis;
        private boolean clearStack;
        int requestCode = -1;

        public void launchActivity() {
            if (bundle != null)
                intent.putExtras(bundle);
            if (clearStack) {
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            if (requestCode == -1)
                startActivity(intent);
            else
                startActivityForResult(intent, requestCode);
            if (finishThis)
                finish();
        }

        private GpLauncher(Context context, Class<? extends PmBaseActivity> cls) {
            intent = new Intent(context, cls);
        }

        public GpLauncher finishThis() {
            finishThis = true;
            return this;
        }

        public GpLauncher setRequestCode(int requestCode) {
            this.requestCode = requestCode;
            return this;
        }

        public GpLauncher clearStack() {
            clearStack = true;
            return this;
        }

        public GpLauncher addString(String key, String value) {
            if (bundle == null)
                bundle = new Bundle();
            bundle.putString(key, value);
            return this;
        }

        public GpLauncher addInt(String key, int value) {
            if (bundle == null)
                bundle = new Bundle();
            bundle.putInt(key, value);
            return this;
        }

        public GpLauncher addLong(String key, long value) {
            if (bundle == null)
                bundle = new Bundle();
            bundle.putLong(key, value);
            return this;
        }


    }


}
