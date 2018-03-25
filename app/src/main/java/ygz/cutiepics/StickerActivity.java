package ygz.cutiepics;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.view.View.OnTouchListener;

import java.io.File;
import java.util.ArrayList;

import ygz.cutiepics.models.PhotoModel;
import ygz.cutiepics.models.StickerObject;

/**
 * Created by Raina on 2018-01-29.
 */

public class StickerActivity extends Activity {
    private ImageView img;
    private PopupWindow pw;
    private int add_pos = -1;
    private String mCurrentPath;
//    private ProductViewHolder ProductViewHolder;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_emoji:
                    showPopupWindow();
                    return true;
                case R.id.nav_sticker:
                    return true;
                case R.id.nav_label:
                    return true;
                case R.id.nav_text:
                    return true;
            }
            return false;
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stickers);
        img = (ImageView) findViewById(R.id.ivImage);
        /*
        Bundle captured = getIntent().getExtras();
        this.mCurrentPath = (String) captured.get("image");
        Uri uriFromPath = Uri.fromFile(new File(mCurrentPath));
        img.setImageURI(uriFromPath);
        */
        img.setImageURI(PhotoModel.getmUri());

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.sticker_navigation);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        /*
        final RecyclerView rv = (RecyclerView) findViewById(R.id.pop_sticker);
        GridLayoutManager mGrid = new GridLayoutManager(this, 8);
        rv.setLayoutManager(mGrid);
        rv.setHasFixedSize(true);
        rv.setItemViewCacheSize(32);
        rv.setDrawingCacheEnabled(true);
        rv.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        rv.setNestedScrollingEnabled(false);
        StickerAdapter mAdapter = new StickerAdapter(StickerActivity.this, getProductTestData());
        rv.setAdapter(mAdapter);
        rv.addOnItemTouchListener(
                new RecyclerItemClickListener(this, rv ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        if(position != RecyclerView.NO_POSITION){
                            FrameLayout stickerPhoto = (FrameLayout) findViewById(R.id.stickerPhoto);

                            ProductViewHolder pvh = (ProductViewHolder) rv.findViewHolderForAdapterPosition(position);
                            ImageView temp = pvh.getEmoji();
                            Drawable drawable = temp.getDrawable();

                            ImageView image = new ImageView(StickerActivity.this);
                            LinearLayout.LayoutParams centerParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
                            centerParams.gravity=Gravity.CENTER;
                            image.setLayoutParams(centerParams);
                            image.setBackground(drawable);
                            stickerPhoto.addView(image);
                        }
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
        */
    }
/*
    private final class TouchListener implements OnTouchListener {
        private PointF startPoint = new PointF();
        private Matrix matrix = new Matrix();
        private Matrix currentMatrix = new Matrix();
        private int mode = 0;
        private static final int DRAG = 1;
        private static final int ZOOM = 2;
        private float startDis;
        private PointF midPoint;
        private float left;
        private float top;
        private float right;
        private float bottom;
        Rect rect;

        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    mode = DRAG;
                    currentMatrix.set(img.getImageMatrix());
                    startPoint.set(event.getX(), event.getY());

                    float[] values = new float[9];
                    currentMatrix.getValues(values);

                    rect = ((ImageView)v).getDrawable().getBounds();

                    left = values[Matrix.MTRANS_X];
                    top = values[Matrix.MTRANS_Y];
                    right = left + rect.width() * values[Matrix.MSCALE_X];
                    bottom = top + rect.height() * values[Matrix.MSCALE_Y];

                    break;

                case MotionEvent.ACTION_MOVE:
                    if (mode == DRAG) {
                        float dx = event.getX() - startPoint.x;
                        float dy = event.getY() - startPoint.y;
                        matrix.set(currentMatrix);

                        if(right - left < v.getWidth()) {
                            dx = 0;
                        } else if (left + dx > 0 && dx > 0)
                            dx = -left;
                        else if (right + dx < v.getRight() && dx < 0)
                            dx =  v.getRight() - right;



                        if(bottom - top < v.getHeight()) {
                            dy =  0;
                        }else if (top + dy > 0 && dy > 0)
                            dy = -top;
                        else if (bottom + dy < v.getBottom() && dy < 0)
                            dy = v.getBottom() - bottom;

                        matrix.postTranslate(dx, dy);

                    } else if (mode == ZOOM) {
                        float endDis = distance(event);
                        if (endDis > 10f) {
                            float scale = endDis / startDis;
                            matrix.set(currentMatrix);
                            matrix.postScale(scale, scale, midPoint.x, midPoint.y);
                        }
                    }
                    break;

                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_POINTER_UP:
                    mode = 0;
                    break;

                case MotionEvent.ACTION_POINTER_DOWN:
                    mode = ZOOM;
                    startDis = distance(event);
                    if (startDis > 10f) {
                        midPoint = mid(event);
                        currentMatrix.set(img.getImageMatrix());
                    }
                    break;
            }
            img.setImageMatrix(matrix);
            return true;
        }

    }


    public static float distance(MotionEvent event) {
        float dx = event.getX(1) - event.getX(0);
        float dy = event.getY(1) - event.getY(0);
        return (float)Math.sqrt(dx * dx + dy * dy);
        //return FloatMath.sqrt(dx * dx + dy * dy); not work with current API
    }


    public static PointF mid(MotionEvent event) {
        float midX = (event.getX(1) + event.getX(0)) / 2;
        float midY = (event.getY(1) + event.getY(0)) / 2;
        return new PointF(midX, midY);
    }

*/
    private void showPopupWindow() {
        View view = LayoutInflater.from(StickerActivity.this).inflate(R.layout.sticker_popup, null);
        pw = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, 480);
        pw.setFocusable(true);
        pw.setBackgroundDrawable(new ColorDrawable(0xffffffff));
        pw.setOutsideTouchable(true);
        pw.setAnimationStyle(R.style.Animation);
        pw.showAtLocation(view, Gravity.BOTTOM, 0, 0);

        final RecyclerView rv = (RecyclerView) view.findViewById(R.id.pop_sticker);
        GridLayoutManager mGrid = new GridLayoutManager(this, 8);
        rv.setLayoutManager(mGrid);
        rv.setHasFixedSize(true);
        rv.setItemViewCacheSize(24);
        rv.setDrawingCacheEnabled(true);
        rv.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        rv.setNestedScrollingEnabled(false);
        StickerAdapter mAdapter = new StickerAdapter(StickerActivity.this, getProductTestData());
        rv.setAdapter(mAdapter);
        rv.addOnItemTouchListener(
                new RecyclerItemClickListener(this, rv, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        ProductViewHolder pvh = (ProductViewHolder) rv.findViewHolderForAdapterPosition(position);

                        //copy image from emoji to upper screen
                        ImageView emoji_IV = pvh.getEmoji();
                        Bitmap emoji_Bitmap = ((BitmapDrawable) emoji_IV.getDrawable()).getBitmap();
                        //Bitmap emoji_Bitmap1 = BitmapFactory.decodeResource(emoji_IV.getResources(), R.id.sticker_navigation);
                        Bitmap bmp2 = emoji_Bitmap.copy(emoji_Bitmap.getConfig(), true);

                        Bitmap origin_bitmap = ((BitmapDrawable)img.getDrawable()).getBitmap();
                        //Bitmap emoji_bitmap_copy = ((BitmapDrawable)getResources().getDrawable(R.drawable.ivImage)).getBitmap();

                        Bmp bmp_emoji = new Bmp (bmp2);

                        final DrawEmoji drawemoji =new DrawEmoji(getApplicationContext(), bmp_emoji);
                        drawemoji.setOnTouchListener(new OnTouchListener() {
                            @Override
                            public boolean onTouch(View view, MotionEvent motionEvent) {
                                drawemoji.onTouchEvent(motionEvent);
                                return true;
                            }
                        });

                        //ImageView emoji_copy = new ImageView(getApplicationContext());
                        //ImageView emoji_copy =(ImageView)findViewById(R.id.ivImage);
                        //emoji_copy.setImageBitmap(bmp2);

                        RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.sticker_layout);

                        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                                RelativeLayout.LayoutParams.WRAP_CONTENT,
                                RelativeLayout.LayoutParams.WRAP_CONTENT
                        );

                        //layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        //((ViewGroup)drawemoji.getParent()).removeView(drawemoji);
                        relativeLayout.addView(drawemoji, layoutParams);
                        //emoji_copy.setOnTouchListener(new TouchListener());

                        //final ImageView iv = (ImageView) findViewById(R.id.ivImage);
                        //iv.setImageBitmap(emoji_Bitmap);

                        /*
                        //Create a new image bitmap and attach a brand new canvas to it
                        Bitmap tempBitmap = Bitmap.createBitmap(origin_bitmap.getWidth(), origin_bitmap.getHeight(), Bitmap.Config.RGB_565);
                        Canvas tempCanvas = new Canvas(tempBitmap);
                        //Draw the image bitmap into the canvas
                        tempCanvas.drawBitmap(origin_bitmap, 0, 0, null);
                        tempCanvas.drawBitmap(bmp2, 10, 10, null);
                        //Attach the canvas to the ImageView
                        img.setImageDrawable(new BitmapDrawable(getResources(), tempBitmap));
                        */
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {}
                })
        );
    }


    // This is a helper function copied from on line
    public Bitmap resize(Bitmap bm, int w, int h)
    {

        Bitmap BitmapOrg = bm;

        int width = BitmapOrg.getWidth();
        int height = BitmapOrg.getHeight();
        int newWidth = w;
        int newHeight = h;

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        // if you want to rotate the Bitmap
        // matrix.postRotate(45);

        // recreate the new Bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width,
                height, matrix, true);
        return resizedBitmap;

    }

    private ArrayList<StickerObject> getProductTestData() {
        ArrayList<StickerObject> featuredProducts = new ArrayList<StickerObject>();
        featuredProducts.add(new StickerObject("strawberry"));
        featuredProducts.add(new StickerObject("sweetpotato"));
        featuredProducts.add(new StickerObject("donut"));
        featuredProducts.add(new StickerObject("drumstick"));
        featuredProducts.add(new StickerObject("blueheart"));
        featuredProducts.add(new StickerObject("cardheart"));

        featuredProducts.add(new StickerObject("happyface"));
        featuredProducts.add(new StickerObject("happy"));
        featuredProducts.add(new StickerObject("politesmile"));
        featuredProducts.add(new StickerObject("bigsmile"));
        featuredProducts.add(new StickerObject("bigsmile2"));
        featuredProducts.add(new StickerObject("bigsmile3"));
        featuredProducts.add(new StickerObject("bigsmile4"));
        featuredProducts.add(new StickerObject("bigsmile5"));
        featuredProducts.add(new StickerObject("blink"));
        featuredProducts.add(new StickerObject("naughty"));
        featuredProducts.add(new StickerObject("hearteyes"));
        featuredProducts.add(new StickerObject("sunglasses"));
        featuredProducts.add(new StickerObject("smile3"));
        featuredProducts.add(new StickerObject("smile2"));
        featuredProducts.add(new StickerObject("smile4"));
        featuredProducts.add(new StickerObject("smile5"));
        featuredProducts.add(new StickerObject("stickingtongueout"));
        featuredProducts.add(new StickerObject("stickingtongueout2"));
        featuredProducts.add(new StickerObject("stickingtongueout3"));
        featuredProducts.add(new StickerObject("sadface"));
        featuredProducts.add(new StickerObject("sad"));
        featuredProducts.add(new StickerObject("sad2"));
        featuredProducts.add(new StickerObject("sad3"));
        featuredProducts.add(new StickerObject("sad4"));
        featuredProducts.add(new StickerObject("sad5"));
        featuredProducts.add(new StickerObject("smiletocry"));
        featuredProducts.add(new StickerObject("tiltsmiletocry"));
        featuredProducts.add(new StickerObject("supercilious"));
        featuredProducts.add(new StickerObject("upsidedownsmile"));
        featuredProducts.add(new StickerObject("blush"));
        featuredProducts.add(new StickerObject("showteeth"));
        featuredProducts.add(new StickerObject("suprised"));
        featuredProducts.add(new StickerObject("suprised2"));
        featuredProducts.add(new StickerObject("suprised3"));
        featuredProducts.add(new StickerObject("omg"));
        featuredProducts.add(new StickerObject("hush"));
        featuredProducts.add(new StickerObject("upset"));
        featuredProducts.add(new StickerObject("upset2"));
        featuredProducts.add(new StickerObject("upset3"));
        featuredProducts.add(new StickerObject("upset4"));
        featuredProducts.add(new StickerObject("upset5"));
        featuredProducts.add(new StickerObject("upset6"));
        featuredProducts.add(new StickerObject("cry"));
        featuredProducts.add(new StickerObject("cry2"));
        featuredProducts.add(new StickerObject("angry"));
        featuredProducts.add(new StickerObject("angry2"));
        featuredProducts.add(new StickerObject("angry3"));
        featuredProducts.add(new StickerObject("angry4"));
        featuredProducts.add(new StickerObject("angry5"));
        featuredProducts.add(new StickerObject("kiss"));
        featuredProducts.add(new StickerObject("kiss2"));
        featuredProducts.add(new StickerObject("kiss3"));
        featuredProducts.add(new StickerObject("what"));
        featuredProducts.add(new StickerObject("what2"));
        featuredProducts.add(new StickerObject("vomit"));
        featuredProducts.add(new StickerObject("wideopeneyes"));
        featuredProducts.add(new StickerObject("wideopenmouth"));
        featuredProducts.add(new StickerObject("speechless"));
        featuredProducts.add(new StickerObject("speechless2"));
        featuredProducts.add(new StickerObject("speechless3"));
        featuredProducts.add(new StickerObject("mask"));
        featuredProducts.add(new StickerObject("bluehead"));
        featuredProducts.add(new StickerObject("nomouth"));
        featuredProducts.add(new StickerObject("sleepy"));
        featuredProducts.add(new StickerObject("dizzy"));
        featuredProducts.add(new StickerObject("stareyes"));
        featuredProducts.add(new StickerObject("raiseeyebrow"));
        featuredProducts.add(new StickerObject("think"));
        featuredProducts.add(new StickerObject("blownose"));
        featuredProducts.add(new StickerObject("drool"));
        featuredProducts.add(new StickerObject("pinocchio"));
        featuredProducts.add(new StickerObject("hug"));
        featuredProducts.add(new StickerObject("disgusting"));
        featuredProducts.add(new StickerObject("clown"));
        featuredProducts.add(new StickerObject("cowboy"));
        featuredProducts.add(new StickerObject("robot"));
        featuredProducts.add(new StickerObject("injury"));
        featuredProducts.add(new StickerObject("bunnyteeth"));
        featuredProducts.add(new StickerObject("fever"));
        featuredProducts.add(new StickerObject("pinocchio"));
        featuredProducts.add(new StickerObject("zip"));
        featuredProducts.add(new StickerObject("greedy"));
        featuredProducts.add(new StickerObject("evil"));
        featuredProducts.add(new StickerObject("angel"));
        featuredProducts.add(new StickerObject("nohearing"));
        featuredProducts.add(new StickerObject("nowatching"));
        featuredProducts.add(new StickerObject("nospeaking"));
        featuredProducts.add(new StickerObject("cathappy"));
        featuredProducts.add(new StickerObject("cathappy2"));
        featuredProducts.add(new StickerObject("cathappy3"));
        featuredProducts.add(new StickerObject("cathearteyes"));
        featuredProducts.add(new StickerObject("catcool"));
        featuredProducts.add(new StickerObject("catkiss"));
        featuredProducts.add(new StickerObject("catangry"));
        featuredProducts.add(new StickerObject("catcry"));
        featuredProducts.add(new StickerObject("catomg"));
        featuredProducts.add(new StickerObject("catsmiletocry"));
        featuredProducts.add(new StickerObject("muscle1"));
        featuredProducts.add(new StickerObject("muscle2"));
        featuredProducts.add(new StickerObject("muscle3"));
        featuredProducts.add(new StickerObject("muscle4"));
        featuredProducts.add(new StickerObject("muscle5"));
        featuredProducts.add(new StickerObject("muscle6"));
        featuredProducts.add(new StickerObject("yah"));
        featuredProducts.add(new StickerObject("nogesture1"));
        featuredProducts.add(new StickerObject("nogesture2"));
        featuredProducts.add(new StickerObject("nogesture3"));
        featuredProducts.add(new StickerObject("nogesture4"));
        featuredProducts.add(new StickerObject("nogesture5"));
        featuredProducts.add(new StickerObject("nogesture6"));
        featuredProducts.add(new StickerObject("leftpunch1"));
        featuredProducts.add(new StickerObject("leftpunch2"));
        featuredProducts.add(new StickerObject("leftpunch3"));
        featuredProducts.add(new StickerObject("leftpunch4"));
        featuredProducts.add(new StickerObject("leftpunch5"));
        featuredProducts.add(new StickerObject("leftpunch6"));
        featuredProducts.add(new StickerObject("rightpunch1"));
        featuredProducts.add(new StickerObject("rightpunch2"));
        featuredProducts.add(new StickerObject("rightpunch3"));
        featuredProducts.add(new StickerObject("rightpunch4"));
        featuredProducts.add(new StickerObject("rightpunch5"));
        featuredProducts.add(new StickerObject("rightpunch6"));
        /*
        featuredProducts.add(new StickerObject("rock1"));
        featuredProducts.add(new StickerObject("rock2"));
        featuredProducts.add(new StickerObject("rock3"));
        featuredProducts.add(new StickerObject("rock4"));
        featuredProducts.add(new StickerObject("rock5"));
        featuredProducts.add(new StickerObject("rock6"));
        featuredProducts.add(new StickerObject("liu1"));
        featuredProducts.add(new StickerObject("liu2"));
        featuredProducts.add(new StickerObject("liu3"));
        featuredProducts.add(new StickerObject("liu4"));
        featuredProducts.add(new StickerObject("liu5"));
        featuredProducts.add(new StickerObject("liu6"));
        featuredProducts.add(new StickerObject("crossfinger1"));
        featuredProducts.add(new StickerObject("crossfinger2"));
        featuredProducts.add(new StickerObject("crossfinger3"));
        featuredProducts.add(new StickerObject("crossfinger4"));
        featuredProducts.add(new StickerObject("crossfinger5"));
        featuredProducts.add(new StickerObject("crossfinger6"));
        featuredProducts.add(new StickerObject("peace1"));
        featuredProducts.add(new StickerObject("peace2"));
        featuredProducts.add(new StickerObject("peace3"));
        featuredProducts.add(new StickerObject("peace4"));
        featuredProducts.add(new StickerObject("peace5"));
        featuredProducts.add(new StickerObject("peace6"));
        featuredProducts.add(new StickerObject("poop"));
        featuredProducts.add(new StickerObject("fire"));
        featuredProducts.add(new StickerObject("whateverfem1"));
        featuredProducts.add(new StickerObject("coverfacefem1"));
        featuredProducts.add(new StickerObject("coverfacefem2"));
        featuredProducts.add(new StickerObject("coverfacefem3"));
        featuredProducts.add(new StickerObject("coverfacefem4"));
        featuredProducts.add(new StickerObject("coverfacefem5"));
        featuredProducts.add(new StickerObject("coverfacefem6"));
        featuredProducts.add(new StickerObject("coverfaceman1"));
        featuredProducts.add(new StickerObject("coverfaceman2"));
        featuredProducts.add(new StickerObject("coverfaceman3"));
        featuredProducts.add(new StickerObject("coverfaceman4"));
        featuredProducts.add(new StickerObject("coverfaceman5"));
        featuredProducts.add(new StickerObject("coverfaceman6"));
        featuredProducts.add(new StickerObject("pray1"));
        featuredProducts.add(new StickerObject("pray2"));
        featuredProducts.add(new StickerObject("pray3"));
        featuredProducts.add(new StickerObject("pray4"));
        featuredProducts.add(new StickerObject("pray5"));
        featuredProducts.add(new StickerObject("pray6"));
        featuredProducts.add(new StickerObject("ofem1"));
        featuredProducts.add(new StickerObject("ofem2"));
        featuredProducts.add(new StickerObject("ofem3"));
        featuredProducts.add(new StickerObject("ofem4"));
        featuredProducts.add(new StickerObject("ofem5"));
        featuredProducts.add(new StickerObject("ofem6"));
        featuredProducts.add(new StickerObject("oman1"));
        featuredProducts.add(new StickerObject("oman2"));
        featuredProducts.add(new StickerObject("oman3"));
        featuredProducts.add(new StickerObject("oman4"));
        featuredProducts.add(new StickerObject("oman5"));
        featuredProducts.add(new StickerObject("oman6"));
        featuredProducts.add(new StickerObject("xfem1"));
        featuredProducts.add(new StickerObject("xfem2"));
        featuredProducts.add(new StickerObject("xfem3"));
        featuredProducts.add(new StickerObject("xfem4"));
        featuredProducts.add(new StickerObject("xfem5"));
        featuredProducts.add(new StickerObject("xfem6"));
        featuredProducts.add(new StickerObject("xman1"));
        featuredProducts.add(new StickerObject("xman2"));
        featuredProducts.add(new StickerObject("xman3"));
        featuredProducts.add(new StickerObject("xman4"));
        featuredProducts.add(new StickerObject("xman5"));
        featuredProducts.add(new StickerObject("xman6"));
        featuredProducts.add(new StickerObject("fist1"));
        featuredProducts.add(new StickerObject("fist2"));
        featuredProducts.add(new StickerObject("fist3"));
        featuredProducts.add(new StickerObject("fist4"));
        featuredProducts.add(new StickerObject("fist5"));
        featuredProducts.add(new StickerObject("fist6"));
        featuredProducts.add(new StickerObject("wave1"));
        featuredProducts.add(new StickerObject("wave2"));
        featuredProducts.add(new StickerObject("wave3"));
        featuredProducts.add(new StickerObject("wave4"));
        featuredProducts.add(new StickerObject("wave5"));
        featuredProducts.add(new StickerObject("wave6"));
        featuredProducts.add(new StickerObject("ok1"));
        featuredProducts.add(new StickerObject("ok2"));
        featuredProducts.add(new StickerObject("ok3"));
        featuredProducts.add(new StickerObject("ok4"));
        featuredProducts.add(new StickerObject("ok5"));
        featuredProducts.add(new StickerObject("ok6"));
        featuredProducts.add(new StickerObject("thumbup1"));
        featuredProducts.add(new StickerObject("thumbup2"));
        featuredProducts.add(new StickerObject("thumbup3"));
        featuredProducts.add(new StickerObject("thumbup4"));
        featuredProducts.add(new StickerObject("thumbup5"));
        featuredProducts.add(new StickerObject("thumbup6"));
        featuredProducts.add(new StickerObject("thumbdown1"));
        featuredProducts.add(new StickerObject("thumbdown2"));
        featuredProducts.add(new StickerObject("thumbdown3"));
        featuredProducts.add(new StickerObject("thumbdown4"));
        featuredProducts.add(new StickerObject("thumbdown5"));
        featuredProducts.add(new StickerObject("thumbdown6"));
        featuredProducts.add(new StickerObject("clap1"));
        featuredProducts.add(new StickerObject("clap2"));
        featuredProducts.add(new StickerObject("clap3"));
        featuredProducts.add(new StickerObject("clap4"));
        featuredProducts.add(new StickerObject("clap5"));
        featuredProducts.add(new StickerObject("clap6"));
        featuredProducts.add(new StickerObject("middlefinger1"));
        featuredProducts.add(new StickerObject("middlefinger2"));
        featuredProducts.add(new StickerObject("middlefinger3"));
        featuredProducts.add(new StickerObject("middlefinger4"));
        featuredProducts.add(new StickerObject("middlefinger5"));
        featuredProducts.add(new StickerObject("middlefinger6"));
        featuredProducts.add(new StickerObject("pregnant1"));
        featuredProducts.add(new StickerObject("pregnant2"));
        featuredProducts.add(new StickerObject("pregnant3"));
        featuredProducts.add(new StickerObject("pregnant4"));
        featuredProducts.add(new StickerObject("pregnant5"));
        featuredProducts.add(new StickerObject("pregnant6"));
        featuredProducts.add(new StickerObject("feeding1"));
        featuredProducts.add(new StickerObject("feeding2"));
        featuredProducts.add(new StickerObject("feeding3"));
        featuredProducts.add(new StickerObject("feeding4"));
        featuredProducts.add(new StickerObject("feeding5"));
        featuredProducts.add(new StickerObject("feeding6"));
        featuredProducts.add(new StickerObject("mermaid"));
        featuredProducts.add(new StickerObject("malemermaid"));
        featuredProducts.add(new StickerObject("fairy"));
        featuredProducts.add(new StickerObject("malefairy"));
        featuredProducts.add(new StickerObject("unicorn"));
        featuredProducts.add(new StickerObject("unicorn2"));
        featuredProducts.add(new StickerObject("lipprint"));
        featuredProducts.add(new StickerObject("greyfullmoon"));
        featuredProducts.add(new StickerObject("newmoon"));
        featuredProducts.add(new StickerObject("leftnewmoon"));
        featuredProducts.add(new StickerObject("rightnewmoon"));
        featuredProducts.add(new StickerObject("yellowfullmoon"));
        featuredProducts.add(new StickerObject("sun"));
        featuredProducts.add(new StickerObject("redmask"));
        featuredProducts.add(new StickerObject("ghost"));
        featuredProducts.add(new StickerObject("cupid"));
        featuredProducts.add(new StickerObject("diamondring"));
        featuredProducts.add(new StickerObject("diamond"));
        featuredProducts.add(new StickerObject("thermometer"));
        featuredProducts.add(new StickerObject("pumpkin"));
        featuredProducts.add(new StickerObject("christmastree"));
        featuredProducts.add(new StickerObject("santa"));
        featuredProducts.add(new StickerObject("firework"));
        featuredProducts.add(new StickerObject("firework2"));
        featuredProducts.add(new StickerObject("bloon"));
        featuredProducts.add(new StickerObject("gradhat"));
        featuredProducts.add(new StickerObject("eyes"));
        featuredProducts.add(new StickerObject("eyes2"));
        featuredProducts.add(new StickerObject("eyes3"));
        featuredProducts.add(new StickerObject("lips"));
        featuredProducts.add(new StickerObject("tongue"));
        featuredProducts.add(new StickerObject("indexup"));
        featuredProducts.add(new StickerObject("indexdown"));
        featuredProducts.add(new StickerObject("indexleft"));
        featuredProducts.add(new StickerObject("indexright"));
        featuredProducts.add(new StickerObject("crown"));
        featuredProducts.add(new StickerObject("pinkhat"));
        featuredProducts.add(new StickerObject("pinkdress"));
        featuredProducts.add(new StickerObject("bikini"));
        featuredProducts.add(new StickerObject("baby"));
        featuredProducts.add(new StickerObject("dancinggirl"));
        featuredProducts.add(new StickerObject("lipbalm"));
        featuredProducts.add(new StickerObject("bell"));
        featuredProducts.add(new StickerObject("net"));

        featuredProducts.add(new StickerObject("redheart"));
        featuredProducts.add(new StickerObject("redheart2"));
        featuredProducts.add(new StickerObject("redheart3"));
        featuredProducts.add(new StickerObject("redheart4"));
        featuredProducts.add(new StickerObject("redheart5"));
        featuredProducts.add(new StickerObject("redheart6"));
        featuredProducts.add(new StickerObject("redheart7"));
        featuredProducts.add(new StickerObject("brokenheart"));
        featuredProducts.add(new StickerObject("yellowheart"));
        featuredProducts.add(new StickerObject("greenheart"));
        featuredProducts.add(new StickerObject("blueheart"));
        featuredProducts.add(new StickerObject("purpleheart"));
        featuredProducts.add(new StickerObject("giftheart"));
        featuredProducts.add(new StickerObject("squareheart"));
        featuredProducts.add(new StickerObject("blackheart"));
        featuredProducts.add(new StickerObject("cutesun"));
        featuredProducts.add(new StickerObject("cutesun2"));
        featuredProducts.add(new StickerObject("snow"));
        featuredProducts.add(new StickerObject("rainbow"));
        featuredProducts.add(new StickerObject("raindrop"));
        featuredProducts.add(new StickerObject("raindrops"));
        featuredProducts.add(new StickerObject("storm"));
        featuredProducts.add(new StickerObject("cloud"));
        featuredProducts.add(new StickerObject("cloud2"));
        featuredProducts.add(new StickerObject("cloud3"));
        featuredProducts.add(new StickerObject("cloud4"));
        featuredProducts.add(new StickerObject("cloud5"));
        featuredProducts.add(new StickerObject("cloud6"));
        featuredProducts.add(new StickerObject("cloud7"));
        featuredProducts.add(new StickerObject("tide"));
        featuredProducts.add(new StickerObject("wind"));
        featuredProducts.add(new StickerObject("star"));
        featuredProducts.add(new StickerObject("star2"));
        featuredProducts.add(new StickerObject("lightning"));
        featuredProducts.add(new StickerObject("comet2"));
        featuredProducts.add(new StickerObject("comet"));
        featuredProducts.add(new StickerObject("tornado"));
        featuredProducts.add(new StickerObject("flow"));
        featuredProducts.add(new StickerObject("colorfulstars"));
        featuredProducts.add(new StickerObject("yellowflower"));
        featuredProducts.add(new StickerObject("sunflower"));
        featuredProducts.add(new StickerObject("sakura"));
        featuredProducts.add(new StickerObject("rose"));
        featuredProducts.add(new StickerObject("fallingrose"));
        featuredProducts.add(new StickerObject("mapleleaf"));
        featuredProducts.add(new StickerObject("leaf"));
        featuredProducts.add(new StickerObject("tree"));
        featuredProducts.add(new StickerObject("tree2"));
        featuredProducts.add(new StickerObject("tree3"));
        featuredProducts.add(new StickerObject("cactus"));
        featuredProducts.add(new StickerObject("rwflower"));
        featuredProducts.add(new StickerObject("bow"));
        featuredProducts.add(new StickerObject("gift"));
        featuredProducts.add(new StickerObject("drawingpalette"));
        featuredProducts.add(new StickerObject("magicianhat"));
        featuredProducts.add(new StickerObject("rainbowflag"));
        featuredProducts.add(new StickerObject("scarf"));
        featuredProducts.add(new StickerObject("sleepymark"));
        featuredProducts.add(new StickerObject("stave"));
        featuredProducts.add(new StickerObject("musicalnote"));
        featuredProducts.add(new StickerObject("dollarmark"));
        featuredProducts.add(new StickerObject("wu"));
        featuredProducts.add(new StickerObject("vs"));
        featuredProducts.add(new StickerObject("angrymark"));
        featuredProducts.add(new StickerObject("explosionmark"));
        featuredProducts.add(new StickerObject("entering"));
        featuredProducts.add(new StickerObject("textbubble"));
        featuredProducts.add(new StickerObject("explosiontextbubble"));
        featuredProducts.add(new StickerObject("fullmark"));
        featuredProducts.add(new StickerObject("noenter"));
        featuredProducts.add(new StickerObject("noenter2"));
        featuredProducts.add(new StickerObject("nophone"));
        featuredProducts.add(new StickerObject("warning"));
        featuredProducts.add(new StickerObject("secret"));
        featuredProducts.add(new StickerObject("congrats"));
        featuredProducts.add(new StickerObject("tilde"));
        featuredProducts.add(new StickerObject("redexclamation"));
        featuredProducts.add(new StickerObject("silverexclamation"));
        featuredProducts.add(new StickerObject("redquestionmark"));
        featuredProducts.add(new StickerObject("silverquestionmark"));
        featuredProducts.add(new StickerObject("questionnexclamation"));
        featuredProducts.add(new StickerObject("doubleexclamation"));
        featuredProducts.add(new StickerObject("prohibit18"));
        featuredProducts.add(new StickerObject("right"));
        featuredProducts.add(new StickerObject("wrong"));
        featuredProducts.add(new StickerObject("redwrong"));
        featuredProducts.add(new StickerObject("redcircle"));
        featuredProducts.add(new StickerObject("reduptriangle"));
        featuredProducts.add(new StickerObject("reddowntriangle"));
        featuredProducts.add(new StickerObject("libra"));
        featuredProducts.add(new StickerObject("anchor"));
        featuredProducts.add(new StickerObject("pill"));
        featuredProducts.add(new StickerObject("letter"));
        featuredProducts.add(new StickerObject("key"));
        featuredProducts.add(new StickerObject("yellowbook"));
        featuredProducts.add(new StickerObject("redbook"));
        featuredProducts.add(new StickerObject("bluebook"));
        featuredProducts.add(new StickerObject("greenbook"));
        featuredProducts.add(new StickerObject("cardspade"));
        featuredProducts.add(new StickerObject("cardclub"));
        featuredProducts.add(new StickerObject("cardheart"));
        featuredProducts.add(new StickerObject("carddiamond"));
        featuredProducts.add(new StickerObject("clover"));
        featuredProducts.add(new StickerObject("clover2"));
        featuredProducts.add(new StickerObject("coffee"));
        featuredProducts.add(new StickerObject("umbrella"));
        featuredProducts.add(new StickerObject("guitar"));
        featuredProducts.add(new StickerObject("camera"));
        featuredProducts.add(new StickerObject("action"));
        featuredProducts.add(new StickerObject("microphone"));
        featuredProducts.add(new StickerObject("headphone"));
        featuredProducts.add(new StickerObject("telephone"));
        featuredProducts.add(new StickerObject("female"));
        featuredProducts.add(new StickerObject("male"));
        featuredProducts.add(new StickerObject("baby"));
        featuredProducts.add(new StickerObject("bothgender"));
        featuredProducts.add(new StickerObject("malesign"));
        featuredProducts.add(new StickerObject("femalesign"));
        featuredProducts.add(new StickerObject("basketball"));
        featuredProducts.add(new StickerObject("rugby"));
        featuredProducts.add(new StickerObject("box"));
        featuredProducts.add(new StickerObject("bulb"));
        featuredProducts.add(new StickerObject("bumb"));
        featuredProducts.add(new StickerObject("cigarette"));
        featuredProducts.add(new StickerObject("dart"));
        featuredProducts.add(new StickerObject("dice"));
        featuredProducts.add(new StickerObject("poll"));
        featuredProducts.add(new StickerObject("mahjong"));
        featuredProducts.add(new StickerObject("gamingbundle"));
        featuredProducts.add(new StickerObject("godenmetal"));
        featuredProducts.add(new StickerObject("trophy"));
        featuredProducts.add(new StickerObject("mailbox"));
        featuredProducts.add(new StickerObject("pencil"));
        featuredProducts.add(new StickerObject("java"));
        featuredProducts.add(new StickerObject("drum"));
        featuredProducts.add(new StickerObject("nursingbottle"));
        featuredProducts.add(new StickerObject("carpstreamer"));
        featuredProducts.add(new StickerObject("coloredribbon"));
        featuredProducts.add(new StickerObject("salute"));
        featuredProducts.add(new StickerObject("japdoll"));
        featuredProducts.add(new StickerObject("aeolianbell"));
        featuredProducts.add(new StickerObject("clock"));
        featuredProducts.add(new StickerObject("candle"));

        featuredProducts.add(new StickerObject("beach"));
        featuredProducts.add(new StickerObject("beach2"));
        featuredProducts.add(new StickerObject("dessert"));
        featuredProducts.add(new StickerObject("island"));
        featuredProducts.add(new StickerObject("starrysky"));
        featuredProducts.add(new StickerObject("vocano"));
        featuredProducts.add(new StickerObject("castle"));
        featuredProducts.add(new StickerObject("cinema"));
        featuredProducts.add(new StickerObject("drawing"));
        featuredProducts.add(new StickerObject("thestatueofliberty"));
        featuredProducts.add(new StickerObject("eiffeltower"));
        featuredProducts.add(new StickerObject("ferriswheel"));
        featuredProducts.add(new StickerObject("figureinstone"));
        featuredProducts.add(new StickerObject("fishing"));
        featuredProducts.add(new StickerObject("pinkbuilding"));
        featuredProducts.add(new StickerObject("pinkchurch"));
        featuredProducts.add(new StickerObject("train"));
        featuredProducts.add(new StickerObject("skull"));
        featuredProducts.add(new StickerObject("snowman"));
        featuredProducts.add(new StickerObject("football"));
        featuredProducts.add(new StickerObject("flowerpot"));
        featuredProducts.add(new StickerObject("koalahead"));
        featuredProducts.add(new StickerObject("bunnyhead"));
        featuredProducts.add(new StickerObject("cockhead"));
        featuredProducts.add(new StickerObject("monkeyhead"));
        featuredProducts.add(new StickerObject("doghead"));
        featuredProducts.add(new StickerObject("mousehead"));
        featuredProducts.add(new StickerObject("whalehead"));
        featuredProducts.add(new StickerObject("butterfly"));
        featuredProducts.add(new StickerObject("hedgehog"));
        featuredProducts.add(new StickerObject("zebra"));
        featuredProducts.add(new StickerObject("giraffe"));
        featuredProducts.add(new StickerObject("shrimp"));
        featuredProducts.add(new StickerObject("owl"));
        featuredProducts.add(new StickerObject("shark"));
        featuredProducts.add(new StickerObject("bat"));
        featuredProducts.add(new StickerObject("duck"));
        featuredProducts.add(new StickerObject("eagle"));
        featuredProducts.add(new StickerObject("turkey"));
        featuredProducts.add(new StickerObject("pigeon"));
        featuredProducts.add(new StickerObject("lion"));
        featuredProducts.add(new StickerObject("camel"));
        featuredProducts.add(new StickerObject("mouse"));
        featuredProducts.add(new StickerObject("rabbit"));
        featuredProducts.add(new StickerObject("monkey"));
        featuredProducts.add(new StickerObject("chicken"));
        featuredProducts.add(new StickerObject("chicken2"));
        featuredProducts.add(new StickerObject("chicken3"));
        featuredProducts.add(new StickerObject("bird"));
        featuredProducts.add(new StickerObject("penguin"));
        featuredProducts.add(new StickerObject("dog"));
        featuredProducts.add(new StickerObject("dog2"));
        featuredProducts.add(new StickerObject("pig"));
        featuredProducts.add(new StickerObject("elephant"));
        featuredProducts.add(new StickerObject("fish"));
        featuredProducts.add(new StickerObject("balloonfish"));
        featuredProducts.add(new StickerObject("squid"));
        featuredProducts.add(new StickerObject("cow"));
        featuredProducts.add(new StickerObject("tiger"));
        featuredProducts.add(new StickerObject("wolf"));
        featuredProducts.add(new StickerObject("fox"));
        featuredProducts.add(new StickerObject("deer"));
        featuredProducts.add(new StickerObject("rhino"));
        featuredProducts.add(new StickerObject("bear"));
        featuredProducts.add(new StickerObject("panda"));
        featuredProducts.add(new StickerObject("whale"));
        featuredProducts.add(new StickerObject("dolphin"));
        featuredProducts.add(new StickerObject("snall"));
        featuredProducts.add(new StickerObject("shell"));
        featuredProducts.add(new StickerObject("crab"));
        featuredProducts.add(new StickerObject("pignose"));
        featuredProducts.add(new StickerObject("dogfootprint"));
        featuredProducts.add(new StickerObject("squirrel"));
        featuredProducts.add(new StickerObject("prezel"));
        featuredProducts.add(new StickerObject("pie"));
        featuredProducts.add(new StickerObject("pancake"));
        featuredProducts.add(new StickerObject("softdrink"));
        featuredProducts.add(new StickerObject("bowl"));
        featuredProducts.add(new StickerObject("chopsticks"));
        featuredProducts.add(new StickerObject("chinesetakeout"));
        featuredProducts.add(new StickerObject("fortunecookie"));
        featuredProducts.add(new StickerObject("wrap"));
        featuredProducts.add(new StickerObject("wrap2"));
        featuredProducts.add(new StickerObject("curry"));
        featuredProducts.add(new StickerObject("salad"));
        featuredProducts.add(new StickerObject("baguette"));
        featuredProducts.add(new StickerObject("sandwich"));
        featuredProducts.add(new StickerObject("tacco"));
        featuredProducts.add(new StickerObject("meatballs"));
        featuredProducts.add(new StickerObject("burger"));
        featuredProducts.add(new StickerObject("pizza"));
        featuredProducts.add(new StickerObject("meat2"));
        featuredProducts.add(new StickerObject("drumstick"));
        featuredProducts.add(new StickerObject("fies"));
        featuredProducts.add(new StickerObject("cookie"));
        featuredProducts.add(new StickerObject("cookie2"));
        featuredProducts.add(new StickerObject("onigiri"));
        featuredProducts.add(new StickerObject("bbq"));
        featuredProducts.add(new StickerObject("bbq2"));
        featuredProducts.add(new StickerObject("sushi"));
        featuredProducts.add(new StickerObject("tempura"));
        featuredProducts.add(new StickerObject("fishcake"));
        featuredProducts.add(new StickerObject("bendo"));
        featuredProducts.add(new StickerObject("koreanpot"));
        featuredProducts.add(new StickerObject("friedegg"));
        featuredProducts.add(new StickerObject("icecream"));
        featuredProducts.add(new StickerObject("icecream2"));
        featuredProducts.add(new StickerObject("icecream3"));
        featuredProducts.add(new StickerObject("donut"));
        featuredProducts.add(new StickerObject("cake"));
        featuredProducts.add(new StickerObject("birthdaycake"));
        featuredProducts.add(new StickerObject("greentea"));
        featuredProducts.add(new StickerObject("grape"));
        featuredProducts.add(new StickerObject("melon"));
        featuredProducts.add(new StickerObject("watermelon"));
        featuredProducts.add(new StickerObject("pear"));
        featuredProducts.add(new StickerObject("peach"));
        featuredProducts.add(new StickerObject("cherry"));
        featuredProducts.add(new StickerObject("strawberry"));
        featuredProducts.add(new StickerObject("banana"));
        featuredProducts.add(new StickerObject("pineapple"));
        featuredProducts.add(new StickerObject("kiwi"));
        featuredProducts.add(new StickerObject("broccoli"));
        featuredProducts.add(new StickerObject("coconut"));
        featuredProducts.add(new StickerObject("carrot"));
        featuredProducts.add(new StickerObject("mushroom"));
        featuredProducts.add(new StickerObject("tomato"));
        featuredProducts.add(new StickerObject("eggplant"));
        featuredProducts.add(new StickerObject("potato"));
        featuredProducts.add(new StickerObject("sweetpotato"));
        featuredProducts.add(new StickerObject("chestnut"));
        featuredProducts.add(new StickerObject("chili"));
        featuredProducts.add(new StickerObject("bacon"));
        featuredProducts.add(new StickerObject("avacado"));
        featuredProducts.add(new StickerObject("croissant"));
        featuredProducts.add(new StickerObject("beer"));
        featuredProducts.add(new StickerObject("beer2"));
        featuredProducts.add(new StickerObject("champagne"));
        featuredProducts.add(new StickerObject("champagne2"));
        featuredProducts.add(new StickerObject("wine"));
        featuredProducts.add(new StickerObject("wiskey"));
        featuredProducts.add(new StickerObject("cocktail"));
        featuredProducts.add(new StickerObject("sake"));
        featuredProducts.add(new StickerObject("meat"));
        featuredProducts.add(new StickerObject("chocolate"));
        featuredProducts.add(new StickerObject("candy"));
        featuredProducts.add(new StickerObject("lolipop"));
        featuredProducts.add(new StickerObject("pudding"));
        featuredProducts.add(new StickerObject("honey"));
        featuredProducts.add(new StickerObject("popcorn"));
        */
        return featuredProducts;
    }

    @Override
    protected void onStop() {
        pw.dismiss();
        super.onStop();
    }
}

