package ygz.cutiepics;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raina on 2018-01-29.
 */

public class StickerActivity extends Activity {
    private ImageView img;
    private String mCurrentPath;
    private Button emoji;
    private PopupWindow pw;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stickers);

        img = (ImageView) findViewById(R.id.ivImage);

        Bundle captured = getIntent().getExtras();
        this.mCurrentPath = (String) captured.get("image");
        Uri uriFromPath = Uri.fromFile(new File(mCurrentPath));
        img.setImageURI(uriFromPath);

        emoji = (Button) findViewById(R.id.sticker_emoji);
        emoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow();
            }
        });

//        final RecyclerView rv = (RecyclerView) findViewById(R.id.pop_sticker);
//        GridLayoutManager mGrid = new GridLayoutManager(this, 8);
//        rv.setLayoutManager(mGrid);
//        rv.setHasFixedSize(true);
//        rv.setItemViewCacheSize(32);
//        rv.setDrawingCacheEnabled(true);
//        rv.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
//        rv.setNestedScrollingEnabled(false);
//        ProductAdapter mAdapter = new ProductAdapter(StickerActivity.this, getProductTestData());
//        rv.setAdapter(mAdapter);
//        rv.addOnItemTouchListener(
//                new RecyclerItemClickListener(this, rv ,new RecyclerItemClickListener.OnItemClickListener() {
//                    @Override public void onItemClick(View view, int position) {
//                        if(position != RecyclerView.NO_POSITION){
////                            FrameLayout stickerPhoto = (FrameLayout) findViewById(R.id.stickerPhoto);
//
//                            ProductViewHolder pvh = (ProductViewHolder) rv.findViewHolderForAdapterPosition(position);
//                            ImageView temp = pvh.getEmoji();
//                            Drawable drawable = temp.getDrawable();
//
//                            ImageView image = new ImageView(StickerActivity.this);
//                            LinearLayout.LayoutParams centerParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
//                            centerParams.gravity=Gravity.CENTER;
//                            image.setLayoutParams(centerParams);
//                            image.setBackground(drawable);
////                            stickerPhoto.addView(image);
//                        }
//                    }
//
//                    @Override public void onLongItemClick(View view, int position) {
//                        // do whatever
//                    }
//                })
//        );
    }

    private void showPopupWindow() {
        View view = LayoutInflater.from(StickerActivity.this).inflate(R.layout.sticker_popup, null);
        pw = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, 450);
        pw.setFocusable(true);
        pw.setBackgroundDrawable(new ColorDrawable(0x969b9b));
        pw.setOutsideTouchable(true);
        pw.setAnimationStyle(R.style.Animation);
        pw.showAtLocation(view, Gravity.BOTTOM, 0, 0);

        RecyclerView rv = (RecyclerView) view.findViewById(R.id.pop_sticker);
        GridLayoutManager mGrid = new GridLayoutManager(this, 8);
        rv.setLayoutManager(mGrid);
//        ScoreTeamAdapter scoreTeamAdapter = new ScoreTeamAdapter(yearList);
//        rv.setAdapter(scoreTeamAdapter);
        rv.setHasFixedSize(true);
        rv.setItemViewCacheSize(32);
        rv.setDrawingCacheEnabled(true);
        rv.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        rv.setNestedScrollingEnabled(false);
        ProductAdapter mAdapter = new ProductAdapter(StickerActivity.this, getProductTestData());
        rv.setAdapter(mAdapter);
    }

    private ArrayList<ProductObject> getProductTestData() {
        ArrayList<ProductObject> featuredProducts = new ArrayList<ProductObject>();
        featuredProducts.add(new ProductObject("happyface"));
        featuredProducts.add(new ProductObject("happy"));
        featuredProducts.add(new ProductObject("politesmile"));
        featuredProducts.add(new ProductObject("bigsmile"));
        featuredProducts.add(new ProductObject("bigsmile2"));
        featuredProducts.add(new ProductObject("bigsmile3"));
        featuredProducts.add(new ProductObject("bigsmile4"));
        featuredProducts.add(new ProductObject("bigsmile5"));
        featuredProducts.add(new ProductObject("blink"));
        featuredProducts.add(new ProductObject("sadface"));
        featuredProducts.add(new ProductObject("sad"));
        featuredProducts.add(new ProductObject("smiletocry"));
        featuredProducts.add(new ProductObject("tiltsmiletocry"));
        featuredProducts.add(new ProductObject("supercilious"));
        featuredProducts.add(new ProductObject("upsidedownsmile"));
        featuredProducts.add(new ProductObject("blush"));
        featuredProducts.add(new ProductObject("suprised"));
        featuredProducts.add(new ProductObject("omg"));
        featuredProducts.add(new ProductObject("upset"));
        featuredProducts.add(new ProductObject("upset2"));
        featuredProducts.add(new ProductObject("upset3"));
        featuredProducts.add(new ProductObject("upset4"));
        featuredProducts.add(new ProductObject("upset5"));
        featuredProducts.add(new ProductObject("upset6"));
        featuredProducts.add(new ProductObject("cry"));
        featuredProducts.add(new ProductObject("angry"));
        featuredProducts.add(new ProductObject("angry2"));
        featuredProducts.add(new ProductObject("angry3"));
        featuredProducts.add(new ProductObject("kiss"));
        featuredProducts.add(new ProductObject("kiss2"));
        featuredProducts.add(new ProductObject("kiss3"));
        featuredProducts.add(new ProductObject("what"));
        featuredProducts.add(new ProductObject("what2"));
        featuredProducts.add(new ProductObject("wideopeneyes"));
        featuredProducts.add(new ProductObject("wideopenmouth"));
        featuredProducts.add(new ProductObject("speechless"));
        featuredProducts.add(new ProductObject("speechless2"));
        featuredProducts.add(new ProductObject("speechless3"));
        featuredProducts.add(new ProductObject("mask"));
        featuredProducts.add(new ProductObject("bluehead"));
        featuredProducts.add(new ProductObject("nomouth"));
        featuredProducts.add(new ProductObject("sleepy"));
        featuredProducts.add(new ProductObject("dizzy"));
        featuredProducts.add(new ProductObject("stareyes"));
        featuredProducts.add(new ProductObject("raiseeyebrow"));
        featuredProducts.add(new ProductObject("think"));
        featuredProducts.add(new ProductObject("blownose"));
        featuredProducts.add(new ProductObject("drool"));
        featuredProducts.add(new ProductObject("pinocchio"));
        featuredProducts.add(new ProductObject("hug"));
        featuredProducts.add(new ProductObject("disgusting"));
        featuredProducts.add(new ProductObject("clown"));
        featuredProducts.add(new ProductObject("cowboy"));
        featuredProducts.add(new ProductObject("robot"));
        featuredProducts.add(new ProductObject("injury"));
        featuredProducts.add(new ProductObject("bunnyteeth"));
        featuredProducts.add(new ProductObject("fever"));
        featuredProducts.add(new ProductObject("pinocchio"));
        featuredProducts.add(new ProductObject("zip"));
        featuredProducts.add(new ProductObject("greedy"));
        featuredProducts.add(new ProductObject("evil"));
        featuredProducts.add(new ProductObject("angel"));
        featuredProducts.add(new ProductObject("nohearing"));
        featuredProducts.add(new ProductObject("nowatching"));
        featuredProducts.add(new ProductObject("cathappy"));
        featuredProducts.add(new ProductObject("catomg"));
        featuredProducts.add(new ProductObject("catsmiletocry"));
        featuredProducts.add(new ProductObject("muscle1"));
        featuredProducts.add(new ProductObject("muscle2"));
        featuredProducts.add(new ProductObject("muscle3"));
        featuredProducts.add(new ProductObject("muscle4"));
        featuredProducts.add(new ProductObject("muscle5"));
        featuredProducts.add(new ProductObject("muscle6"));
        featuredProducts.add(new ProductObject("yah"));
        featuredProducts.add(new ProductObject("nogesture1"));
        featuredProducts.add(new ProductObject("nogesture2"));
        featuredProducts.add(new ProductObject("nogesture3"));
        featuredProducts.add(new ProductObject("nogesture4"));
        featuredProducts.add(new ProductObject("nogesture5"));
        featuredProducts.add(new ProductObject("nogesture6"));
        featuredProducts.add(new ProductObject("rock1"));
        featuredProducts.add(new ProductObject("rock2"));
        featuredProducts.add(new ProductObject("rock3"));
        featuredProducts.add(new ProductObject("rock4"));
        featuredProducts.add(new ProductObject("rock5"));
        featuredProducts.add(new ProductObject("rock6"));
        featuredProducts.add(new ProductObject("liu1"));
        featuredProducts.add(new ProductObject("liu2"));
        featuredProducts.add(new ProductObject("liu3"));
        featuredProducts.add(new ProductObject("liu4"));
        featuredProducts.add(new ProductObject("liu5"));
        featuredProducts.add(new ProductObject("liu6"));
        featuredProducts.add(new ProductObject("poop"));
        featuredProducts.add(new ProductObject("whateverfem1"));
        featuredProducts.add(new ProductObject("coverfacefem1"));
        featuredProducts.add(new ProductObject("coverfacefem2"));
        featuredProducts.add(new ProductObject("coverfacefem3"));
        featuredProducts.add(new ProductObject("coverfacefem4"));
        featuredProducts.add(new ProductObject("coverfacefem5"));
        featuredProducts.add(new ProductObject("coverfacefem6"));
        featuredProducts.add(new ProductObject("coverfaceman1"));
        featuredProducts.add(new ProductObject("coverfaceman2"));
        featuredProducts.add(new ProductObject("coverfaceman3"));
        featuredProducts.add(new ProductObject("coverfaceman4"));
        featuredProducts.add(new ProductObject("coverfaceman5"));
        featuredProducts.add(new ProductObject("coverfaceman6"));
        featuredProducts.add(new ProductObject("ofem1"));
        featuredProducts.add(new ProductObject("ofem2"));
        featuredProducts.add(new ProductObject("ofem3"));
        featuredProducts.add(new ProductObject("ofem4"));
        featuredProducts.add(new ProductObject("ofem5"));
        featuredProducts.add(new ProductObject("ofem6"));
        featuredProducts.add(new ProductObject("oman1"));
        featuredProducts.add(new ProductObject("oman2"));
        featuredProducts.add(new ProductObject("oman3"));
        featuredProducts.add(new ProductObject("oman4"));
        featuredProducts.add(new ProductObject("oman5"));
        featuredProducts.add(new ProductObject("oman6"));
        featuredProducts.add(new ProductObject("xfem1"));
        featuredProducts.add(new ProductObject("xfem2"));
        featuredProducts.add(new ProductObject("xfem3"));
        featuredProducts.add(new ProductObject("xfem4"));
        featuredProducts.add(new ProductObject("xfem5"));
        featuredProducts.add(new ProductObject("xfem6"));
        featuredProducts.add(new ProductObject("xman1"));
        featuredProducts.add(new ProductObject("xman2"));
        featuredProducts.add(new ProductObject("xman3"));
        featuredProducts.add(new ProductObject("xman4"));
        featuredProducts.add(new ProductObject("xman5"));
        featuredProducts.add(new ProductObject("xman6"));
        featuredProducts.add(new ProductObject("pregnant1"));
        featuredProducts.add(new ProductObject("pregnant2"));
        featuredProducts.add(new ProductObject("pregnant3"));
        featuredProducts.add(new ProductObject("pregnant4"));
        featuredProducts.add(new ProductObject("pregnant5"));
        featuredProducts.add(new ProductObject("pregnant6"));
        featuredProducts.add(new ProductObject("feeding1"));
        featuredProducts.add(new ProductObject("feeding2"));
        featuredProducts.add(new ProductObject("feeding3"));
        featuredProducts.add(new ProductObject("feeding4"));
        featuredProducts.add(new ProductObject("feeding5"));
        featuredProducts.add(new ProductObject("feeding6"));
        featuredProducts.add(new ProductObject("mermaid"));
        featuredProducts.add(new ProductObject("malemermaid"));
        featuredProducts.add(new ProductObject("fairy"));
        featuredProducts.add(new ProductObject("malefairy"));
        featuredProducts.add(new ProductObject("unicorn"));
        featuredProducts.add(new ProductObject("unicorn2"));
        featuredProducts.add(new ProductObject("fallingrose"));

        featuredProducts.add(new ProductObject("yellowheart"));
        featuredProducts.add(new ProductObject("redheart"));
        featuredProducts.add(new ProductObject("redheart2"));
        featuredProducts.add(new ProductObject("blackheart"));
        featuredProducts.add(new ProductObject("snow"));
        featuredProducts.add(new ProductObject("raindrop"));
        featuredProducts.add(new ProductObject("raindrops"));
        featuredProducts.add(new ProductObject("storm"));
        featuredProducts.add(new ProductObject("cloud"));
        featuredProducts.add(new ProductObject("tide"));
        featuredProducts.add(new ProductObject("wind"));
        featuredProducts.add(new ProductObject("star"));
        featuredProducts.add(new ProductObject("lightning"));
        featuredProducts.add(new ProductObject("comet"));
        featuredProducts.add(new ProductObject("colorfulstars"));
        featuredProducts.add(new ProductObject("yellowflower"));
        featuredProducts.add(new ProductObject("rwflower"));
        featuredProducts.add(new ProductObject("drawingpalette"));
        featuredProducts.add(new ProductObject("magicianhat"));
        featuredProducts.add(new ProductObject("rainbowflag"));
        featuredProducts.add(new ProductObject("scarf"));
        featuredProducts.add(new ProductObject("sleepymark"));
        featuredProducts.add(new ProductObject("stave"));
        featuredProducts.add(new ProductObject("musicalnote"));
        featuredProducts.add(new ProductObject("dollarmark"));
        featuredProducts.add(new ProductObject("wu"));
        featuredProducts.add(new ProductObject("vs"));
        featuredProducts.add(new ProductObject("angrymark"));
        featuredProducts.add(new ProductObject("explosionmark"));
        featuredProducts.add(new ProductObject("entering"));
        featuredProducts.add(new ProductObject("textbubble"));
        featuredProducts.add(new ProductObject("explosiontextbubble"));
        featuredProducts.add(new ProductObject("fullmark"));
        featuredProducts.add(new ProductObject("noenter"));
        featuredProducts.add(new ProductObject("noenter2"));
        featuredProducts.add(new ProductObject("nophone"));
        featuredProducts.add(new ProductObject("warning"));
        featuredProducts.add(new ProductObject("secret"));
        featuredProducts.add(new ProductObject("congrats"));
        featuredProducts.add(new ProductObject("tilde"));
        featuredProducts.add(new ProductObject("redexclamation"));
        featuredProducts.add(new ProductObject("silverexclamation"));
        featuredProducts.add(new ProductObject("redquestionmark"));
        featuredProducts.add(new ProductObject("silverquestionmark"));
        featuredProducts.add(new ProductObject("questionnexclamation"));
        featuredProducts.add(new ProductObject("doubleexclamation"));
        featuredProducts.add(new ProductObject("right"));
        featuredProducts.add(new ProductObject("wrong"));
        featuredProducts.add(new ProductObject("redwrong"));
        featuredProducts.add(new ProductObject("redcircle"));
        featuredProducts.add(new ProductObject("libra"));
        featuredProducts.add(new ProductObject("anchor"));

        featuredProducts.add(new ProductObject("key"));
        featuredProducts.add(new ProductObject("yellowbook"));
        featuredProducts.add(new ProductObject("redbook"));
        featuredProducts.add(new ProductObject("bluebook"));
        featuredProducts.add(new ProductObject("greenbook"));
        featuredProducts.add(new ProductObject("cardspade"));
        featuredProducts.add(new ProductObject("cardclub"));
        featuredProducts.add(new ProductObject("cardheart"));
        featuredProducts.add(new ProductObject("carddiamond"));
        featuredProducts.add(new ProductObject("clover"));
        featuredProducts.add(new ProductObject("coffee"));
        featuredProducts.add(new ProductObject("umbrella"));
        featuredProducts.add(new ProductObject("guitar"));
        featuredProducts.add(new ProductObject("camera"));
        featuredProducts.add(new ProductObject("action"));
        featuredProducts.add(new ProductObject("microphone"));
        featuredProducts.add(new ProductObject("headphone"));
        featuredProducts.add(new ProductObject("telephone"));
        featuredProducts.add(new ProductObject("female"));
        featuredProducts.add(new ProductObject("male"));
        featuredProducts.add(new ProductObject("baby"));
        featuredProducts.add(new ProductObject("bothgender"));
        featuredProducts.add(new ProductObject("malesign"));
        featuredProducts.add(new ProductObject("femalesign"));
        featuredProducts.add(new ProductObject("basketball"));
        featuredProducts.add(new ProductObject("rugby"));
        featuredProducts.add(new ProductObject("box"));
        featuredProducts.add(new ProductObject("bulb"));
        featuredProducts.add(new ProductObject("bumb"));
        featuredProducts.add(new ProductObject("cigarette"));
        featuredProducts.add(new ProductObject("dart"));
        featuredProducts.add(new ProductObject("dice"));
        featuredProducts.add(new ProductObject("poll"));
        featuredProducts.add(new ProductObject("mahjong"));
        featuredProducts.add(new ProductObject("gamingbundle"));
        featuredProducts.add(new ProductObject("godenmetal"));
        featuredProducts.add(new ProductObject("trophy"));
        featuredProducts.add(new ProductObject("mailbox"));
        featuredProducts.add(new ProductObject("pencil"));
        featuredProducts.add(new ProductObject("java"));
        featuredProducts.add(new ProductObject("drum"));

        featuredProducts.add(new ProductObject("beach"));
        featuredProducts.add(new ProductObject("beach2"));
        featuredProducts.add(new ProductObject("dessert"));
        featuredProducts.add(new ProductObject("island"));
        featuredProducts.add(new ProductObject("starrysky"));
        featuredProducts.add(new ProductObject("vocano"));
        featuredProducts.add(new ProductObject("castle"));
        featuredProducts.add(new ProductObject("cinema"));
        featuredProducts.add(new ProductObject("drawing"));
        featuredProducts.add(new ProductObject("thestatueofliberty"));
        featuredProducts.add(new ProductObject("eiffeltower"));
        featuredProducts.add(new ProductObject("ferriswheel"));
        featuredProducts.add(new ProductObject("figureinstone"));
        featuredProducts.add(new ProductObject("fishing"));
        featuredProducts.add(new ProductObject("pinkbuilding"));
        featuredProducts.add(new ProductObject("train"));
        featuredProducts.add(new ProductObject("skull"));
        featuredProducts.add(new ProductObject("snowman"));
        featuredProducts.add(new ProductObject("football"));
        featuredProducts.add(new ProductObject("flowerpot"));
        featuredProducts.add(new ProductObject("hedgehog"));
        featuredProducts.add(new ProductObject("zebra"));
        featuredProducts.add(new ProductObject("giraffe"));
        featuredProducts.add(new ProductObject("shrimp"));
        featuredProducts.add(new ProductObject("owl"));
        featuredProducts.add(new ProductObject("shark"));
        featuredProducts.add(new ProductObject("bat"));
        featuredProducts.add(new ProductObject("duck"));
        featuredProducts.add(new ProductObject("eagle"));
        featuredProducts.add(new ProductObject("turkey"));
        featuredProducts.add(new ProductObject("lion"));
        featuredProducts.add(new ProductObject("crab"));
        featuredProducts.add(new ProductObject("prezel"));
        featuredProducts.add(new ProductObject("pie"));
        featuredProducts.add(new ProductObject("broccoli"));
        featuredProducts.add(new ProductObject("coconut"));
        featuredProducts.add(new ProductObject("softdrink"));
        featuredProducts.add(new ProductObject("meat"));
        featuredProducts.add(new ProductObject("bowl"));
        featuredProducts.add(new ProductObject("chopsticks"));
        featuredProducts.add(new ProductObject("chinesetakeout"));
        featuredProducts.add(new ProductObject("fortunecookie"));
        featuredProducts.add(new ProductObject("wrap"));
        featuredProducts.add(new ProductObject("curry"));
        featuredProducts.add(new ProductObject("salad"));
        featuredProducts.add(new ProductObject("baguette"));
        featuredProducts.add(new ProductObject("carrot"));
        featuredProducts.add(new ProductObject("potato"));
        featuredProducts.add(new ProductObject("bacon"));
        featuredProducts.add(new ProductObject("avacado"));
        featuredProducts.add(new ProductObject("croissant"));
        featuredProducts.add(new ProductObject("beer"));
        featuredProducts.add(new ProductObject("champagne"));

        return featuredProducts;
    }

    @Override
    protected void onStop() {
        pw.dismiss();
        super.onStop();
    }
}

