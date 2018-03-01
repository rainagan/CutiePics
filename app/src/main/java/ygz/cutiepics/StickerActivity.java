package ygz.cutiepics;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
    private PopupWindow pw;
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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stickers);

        img = (ImageView) findViewById(R.id.ivImage);

        Bundle captured = getIntent().getExtras();
        this.mCurrentPath = (String) captured.get("image");
        Uri uriFromPath = Uri.fromFile(new File(mCurrentPath));
        img.setImageURI(uriFromPath);

        /*
        Button emoji = ProductViewHolder.getEmoji();
        emoji = (Button)findViewById(R.id.emoji);
        emoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow();
            }
        });
*/

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
        ProductAdapter mAdapter = new ProductAdapter(StickerActivity.this, getProductTestData());
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
//        ScoreTeamAdapter scoreTeamAdapter = new ScoreTeamAdapter(yearList);
//        rv.setAdapter(scoreTeamAdapter);
        rv.setHasFixedSize(true);
        rv.setItemViewCacheSize(24);
        rv.setDrawingCacheEnabled(true);
        rv.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        rv.setNestedScrollingEnabled(false);
        ProductAdapter mAdapter = new ProductAdapter(StickerActivity.this, getProductTestData());
        rv.setAdapter(mAdapter);
        rv.addOnItemTouchListener(
                new RecyclerItemClickListener(this, rv, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        ProductViewHolder pvh = (ProductViewHolder) rv.findViewHolderForAdapterPosition(position);
                        ImageView temp = pvh.getEmoji();

                        Drawable emoji_drawable = temp.getDrawable();
                        Drawable origin_drawable = img.getDrawable();

                        if (position!= RecyclerView.NO_POSITION){
                            Drawable[] array = new Drawable[2];
                            array[0] = origin_drawable;
                            array[1] = emoji_drawable;
                            LayerDrawable layer = new LayerDrawable(array);
                            img.setImageDrawable(layer);
                        }
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {}
                })
        );
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
        featuredProducts.add(new ProductObject("naughty"));
        featuredProducts.add(new ProductObject("hearteyes"));
        featuredProducts.add(new ProductObject("sunglasses"));
        featuredProducts.add(new ProductObject("smile3"));
        featuredProducts.add(new ProductObject("smile2"));
        featuredProducts.add(new ProductObject("smile4"));
        featuredProducts.add(new ProductObject("smile5"));
        /*
        featuredProducts.add(new ProductObject("stickingtongueout"));
        featuredProducts.add(new ProductObject("stickingtongueout2"));
        featuredProducts.add(new ProductObject("stickingtongueout3"));
        featuredProducts.add(new ProductObject("sadface"));
        featuredProducts.add(new ProductObject("sad"));
        featuredProducts.add(new ProductObject("sad2"));
        featuredProducts.add(new ProductObject("sad3"));
        featuredProducts.add(new ProductObject("sad4"));
        featuredProducts.add(new ProductObject("sad5"));
        featuredProducts.add(new ProductObject("smiletocry"));
        featuredProducts.add(new ProductObject("tiltsmiletocry"));
        featuredProducts.add(new ProductObject("supercilious"));
        featuredProducts.add(new ProductObject("upsidedownsmile"));
        featuredProducts.add(new ProductObject("blush"));
        featuredProducts.add(new ProductObject("showteeth"));
        featuredProducts.add(new ProductObject("suprised"));
        featuredProducts.add(new ProductObject("suprised2"));
        featuredProducts.add(new ProductObject("suprised3"));
        featuredProducts.add(new ProductObject("omg"));
        featuredProducts.add(new ProductObject("hush"));
        featuredProducts.add(new ProductObject("upset"));
        featuredProducts.add(new ProductObject("upset2"));
        featuredProducts.add(new ProductObject("upset3"));
        featuredProducts.add(new ProductObject("upset4"));
        featuredProducts.add(new ProductObject("upset5"));
        featuredProducts.add(new ProductObject("upset6"));
        featuredProducts.add(new ProductObject("cry"));
        featuredProducts.add(new ProductObject("cry2"));
        featuredProducts.add(new ProductObject("angry"));
        featuredProducts.add(new ProductObject("angry2"));
        featuredProducts.add(new ProductObject("angry3"));
        featuredProducts.add(new ProductObject("angry4"));
        featuredProducts.add(new ProductObject("angry5"));
        featuredProducts.add(new ProductObject("kiss"));
        featuredProducts.add(new ProductObject("kiss2"));
        featuredProducts.add(new ProductObject("kiss3"));
        featuredProducts.add(new ProductObject("what"));
        featuredProducts.add(new ProductObject("what2"));
        featuredProducts.add(new ProductObject("vomit"));
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
        featuredProducts.add(new ProductObject("nospeaking"));
        featuredProducts.add(new ProductObject("cathappy"));
        featuredProducts.add(new ProductObject("cathappy2"));
        featuredProducts.add(new ProductObject("cathappy3"));
        featuredProducts.add(new ProductObject("cathearteyes"));
        featuredProducts.add(new ProductObject("catcool"));
        featuredProducts.add(new ProductObject("catkiss"));
        featuredProducts.add(new ProductObject("catangry"));
        featuredProducts.add(new ProductObject("catcry"));
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
        featuredProducts.add(new ProductObject("leftpunch1"));
        featuredProducts.add(new ProductObject("leftpunch2"));
        featuredProducts.add(new ProductObject("leftpunch3"));
        featuredProducts.add(new ProductObject("leftpunch4"));
        featuredProducts.add(new ProductObject("leftpunch5"));
        featuredProducts.add(new ProductObject("leftpunch6"));
        featuredProducts.add(new ProductObject("rightpunch1"));
        featuredProducts.add(new ProductObject("rightpunch2"));
        featuredProducts.add(new ProductObject("rightpunch3"));
        featuredProducts.add(new ProductObject("rightpunch4"));
        featuredProducts.add(new ProductObject("rightpunch5"));
        featuredProducts.add(new ProductObject("rightpunch6"));
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
        featuredProducts.add(new ProductObject("crossfinger1"));
        featuredProducts.add(new ProductObject("crossfinger2"));
        featuredProducts.add(new ProductObject("crossfinger3"));
        featuredProducts.add(new ProductObject("crossfinger4"));
        featuredProducts.add(new ProductObject("crossfinger5"));
        featuredProducts.add(new ProductObject("crossfinger6"));
        featuredProducts.add(new ProductObject("peace1"));
        featuredProducts.add(new ProductObject("peace2"));
        featuredProducts.add(new ProductObject("peace3"));
        featuredProducts.add(new ProductObject("peace4"));
        featuredProducts.add(new ProductObject("peace5"));
        featuredProducts.add(new ProductObject("peace6"));
        featuredProducts.add(new ProductObject("poop"));
        featuredProducts.add(new ProductObject("fire"));
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
        featuredProducts.add(new ProductObject("pray1"));
        featuredProducts.add(new ProductObject("pray2"));
        featuredProducts.add(new ProductObject("pray3"));
        featuredProducts.add(new ProductObject("pray4"));
        featuredProducts.add(new ProductObject("pray5"));
        featuredProducts.add(new ProductObject("pray6"));
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
        featuredProducts.add(new ProductObject("fist1"));
        featuredProducts.add(new ProductObject("fist2"));
        featuredProducts.add(new ProductObject("fist3"));
        featuredProducts.add(new ProductObject("fist4"));
        featuredProducts.add(new ProductObject("fist5"));
        featuredProducts.add(new ProductObject("fist6"));
        featuredProducts.add(new ProductObject("wave1"));
        featuredProducts.add(new ProductObject("wave2"));
        featuredProducts.add(new ProductObject("wave3"));
        featuredProducts.add(new ProductObject("wave4"));
        featuredProducts.add(new ProductObject("wave5"));
        featuredProducts.add(new ProductObject("wave6"));
        featuredProducts.add(new ProductObject("ok1"));
        featuredProducts.add(new ProductObject("ok2"));
        featuredProducts.add(new ProductObject("ok3"));
        featuredProducts.add(new ProductObject("ok4"));
        featuredProducts.add(new ProductObject("ok5"));
        featuredProducts.add(new ProductObject("ok6"));
        featuredProducts.add(new ProductObject("thumbup1"));
        featuredProducts.add(new ProductObject("thumbup2"));
        featuredProducts.add(new ProductObject("thumbup3"));
        featuredProducts.add(new ProductObject("thumbup4"));
        featuredProducts.add(new ProductObject("thumbup5"));
        featuredProducts.add(new ProductObject("thumbup6"));
        featuredProducts.add(new ProductObject("thumbdown1"));
        featuredProducts.add(new ProductObject("thumbdown2"));
        featuredProducts.add(new ProductObject("thumbdown3"));
        featuredProducts.add(new ProductObject("thumbdown4"));
        featuredProducts.add(new ProductObject("thumbdown5"));
        featuredProducts.add(new ProductObject("thumbdown6"));
        featuredProducts.add(new ProductObject("clap1"));
        featuredProducts.add(new ProductObject("clap2"));
        featuredProducts.add(new ProductObject("clap3"));
        featuredProducts.add(new ProductObject("clap4"));
        featuredProducts.add(new ProductObject("clap5"));
        featuredProducts.add(new ProductObject("clap6"));
        featuredProducts.add(new ProductObject("middlefinger1"));
        featuredProducts.add(new ProductObject("middlefinger2"));
        featuredProducts.add(new ProductObject("middlefinger3"));
        featuredProducts.add(new ProductObject("middlefinger4"));
        featuredProducts.add(new ProductObject("middlefinger5"));
        featuredProducts.add(new ProductObject("middlefinger6"));
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
        featuredProducts.add(new ProductObject("lipprint"));
        featuredProducts.add(new ProductObject("greyfullmoon"));
        featuredProducts.add(new ProductObject("newmoon"));
        featuredProducts.add(new ProductObject("leftnewmoon"));
        featuredProducts.add(new ProductObject("rightnewmoon"));
        featuredProducts.add(new ProductObject("yellowfullmoon"));
        featuredProducts.add(new ProductObject("sun"));
        featuredProducts.add(new ProductObject("redmask"));
        featuredProducts.add(new ProductObject("ghost"));
        featuredProducts.add(new ProductObject("cupid"));
        featuredProducts.add(new ProductObject("diamondring"));
        featuredProducts.add(new ProductObject("diamond"));
        featuredProducts.add(new ProductObject("thermometer"));
        featuredProducts.add(new ProductObject("pumpkin"));
        featuredProducts.add(new ProductObject("christmastree"));
        featuredProducts.add(new ProductObject("santa"));
        featuredProducts.add(new ProductObject("firework"));
        featuredProducts.add(new ProductObject("firework2"));
        featuredProducts.add(new ProductObject("bloon"));
        featuredProducts.add(new ProductObject("gradhat"));
        featuredProducts.add(new ProductObject("eyes"));
        featuredProducts.add(new ProductObject("eyes2"));
        featuredProducts.add(new ProductObject("eyes3"));
        featuredProducts.add(new ProductObject("lips"));
        featuredProducts.add(new ProductObject("tongue"));
        featuredProducts.add(new ProductObject("indexup"));
        featuredProducts.add(new ProductObject("indexdown"));
        featuredProducts.add(new ProductObject("indexleft"));
        featuredProducts.add(new ProductObject("indexright"));
        featuredProducts.add(new ProductObject("crown"));
        featuredProducts.add(new ProductObject("pinkhat"));
        featuredProducts.add(new ProductObject("pinkdress"));
        featuredProducts.add(new ProductObject("bikini"));
        featuredProducts.add(new ProductObject("baby"));
        featuredProducts.add(new ProductObject("dancinggirl"));
        featuredProducts.add(new ProductObject("lipbalm"));
        featuredProducts.add(new ProductObject("bell"));
        featuredProducts.add(new ProductObject("net"));

        featuredProducts.add(new ProductObject("redheart"));
        featuredProducts.add(new ProductObject("redheart2"));
        featuredProducts.add(new ProductObject("redheart3"));
        featuredProducts.add(new ProductObject("redheart4"));
        featuredProducts.add(new ProductObject("redheart5"));
        featuredProducts.add(new ProductObject("redheart6"));
        featuredProducts.add(new ProductObject("redheart7"));
        featuredProducts.add(new ProductObject("brokenheart"));
        featuredProducts.add(new ProductObject("yellowheart"));
        featuredProducts.add(new ProductObject("greenheart"));
        featuredProducts.add(new ProductObject("blueheart"));
        featuredProducts.add(new ProductObject("purpleheart"));
        featuredProducts.add(new ProductObject("giftheart"));
        featuredProducts.add(new ProductObject("squareheart"));
        featuredProducts.add(new ProductObject("blackheart"));
        featuredProducts.add(new ProductObject("cutesun"));
        featuredProducts.add(new ProductObject("cutesun2"));
        featuredProducts.add(new ProductObject("snow"));
        featuredProducts.add(new ProductObject("rainbow"));
        featuredProducts.add(new ProductObject("raindrop"));
        featuredProducts.add(new ProductObject("raindrops"));
        featuredProducts.add(new ProductObject("storm"));
        featuredProducts.add(new ProductObject("cloud"));
        featuredProducts.add(new ProductObject("cloud2"));
        featuredProducts.add(new ProductObject("cloud3"));
        featuredProducts.add(new ProductObject("cloud4"));
        featuredProducts.add(new ProductObject("cloud5"));
        featuredProducts.add(new ProductObject("cloud6"));
        featuredProducts.add(new ProductObject("cloud7"));
        featuredProducts.add(new ProductObject("tide"));
        featuredProducts.add(new ProductObject("wind"));
        featuredProducts.add(new ProductObject("star"));
        featuredProducts.add(new ProductObject("star2"));
        featuredProducts.add(new ProductObject("lightning"));
        featuredProducts.add(new ProductObject("comet2"));
        featuredProducts.add(new ProductObject("comet"));
        featuredProducts.add(new ProductObject("tornado"));
        featuredProducts.add(new ProductObject("flow"));
        featuredProducts.add(new ProductObject("colorfulstars"));
        featuredProducts.add(new ProductObject("yellowflower"));
        featuredProducts.add(new ProductObject("sunflower"));
        featuredProducts.add(new ProductObject("sakura"));
        featuredProducts.add(new ProductObject("rose"));
        featuredProducts.add(new ProductObject("fallingrose"));
        featuredProducts.add(new ProductObject("mapleleaf"));
        featuredProducts.add(new ProductObject("leaf"));
        featuredProducts.add(new ProductObject("tree"));
        featuredProducts.add(new ProductObject("tree2"));
        featuredProducts.add(new ProductObject("tree3"));
        featuredProducts.add(new ProductObject("cactus"));
        featuredProducts.add(new ProductObject("rwflower"));
        featuredProducts.add(new ProductObject("bow"));
        featuredProducts.add(new ProductObject("gift"));
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
        featuredProducts.add(new ProductObject("prohibit18"));
        featuredProducts.add(new ProductObject("right"));
        featuredProducts.add(new ProductObject("wrong"));
        featuredProducts.add(new ProductObject("redwrong"));
        featuredProducts.add(new ProductObject("redcircle"));
        featuredProducts.add(new ProductObject("reduptriangle"));
        featuredProducts.add(new ProductObject("reddowntriangle"));
        featuredProducts.add(new ProductObject("libra"));
        featuredProducts.add(new ProductObject("anchor"));
        featuredProducts.add(new ProductObject("pill"));
        featuredProducts.add(new ProductObject("letter"));
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
        featuredProducts.add(new ProductObject("clover2"));
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
        featuredProducts.add(new ProductObject("nursingbottle"));
        featuredProducts.add(new ProductObject("carpstreamer"));
        featuredProducts.add(new ProductObject("coloredribbon"));
        featuredProducts.add(new ProductObject("salute"));
        featuredProducts.add(new ProductObject("japdoll"));
        featuredProducts.add(new ProductObject("aeolianbell"));
        featuredProducts.add(new ProductObject("clock"));
        featuredProducts.add(new ProductObject("candle"));

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
        featuredProducts.add(new ProductObject("pinkchurch"));
        featuredProducts.add(new ProductObject("train"));
        featuredProducts.add(new ProductObject("skull"));
        featuredProducts.add(new ProductObject("snowman"));
        featuredProducts.add(new ProductObject("football"));
        featuredProducts.add(new ProductObject("flowerpot"));
        featuredProducts.add(new ProductObject("koalahead"));
        featuredProducts.add(new ProductObject("bunnyhead"));
        featuredProducts.add(new ProductObject("cockhead"));
        featuredProducts.add(new ProductObject("monkeyhead"));
        featuredProducts.add(new ProductObject("doghead"));
        featuredProducts.add(new ProductObject("mousehead"));
        featuredProducts.add(new ProductObject("whalehead"));
        featuredProducts.add(new ProductObject("butterfly"));
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
        featuredProducts.add(new ProductObject("pigeon"));
        featuredProducts.add(new ProductObject("lion"));
        featuredProducts.add(new ProductObject("camel"));
        featuredProducts.add(new ProductObject("mouse"));
        featuredProducts.add(new ProductObject("rabbit"));
        featuredProducts.add(new ProductObject("monkey"));
        featuredProducts.add(new ProductObject("chicken"));
        featuredProducts.add(new ProductObject("chicken2"));
        featuredProducts.add(new ProductObject("chicken3"));
        featuredProducts.add(new ProductObject("bird"));
        featuredProducts.add(new ProductObject("penguin"));
        featuredProducts.add(new ProductObject("dog"));
        featuredProducts.add(new ProductObject("dog2"));
        featuredProducts.add(new ProductObject("pig"));
        featuredProducts.add(new ProductObject("elephant"));
        featuredProducts.add(new ProductObject("fish"));
        featuredProducts.add(new ProductObject("balloonfish"));
        featuredProducts.add(new ProductObject("squid"));
        featuredProducts.add(new ProductObject("cow"));
        featuredProducts.add(new ProductObject("tiger"));
        featuredProducts.add(new ProductObject("wolf"));
        featuredProducts.add(new ProductObject("fox"));
        featuredProducts.add(new ProductObject("deer"));
        featuredProducts.add(new ProductObject("rhino"));
        featuredProducts.add(new ProductObject("bear"));
        featuredProducts.add(new ProductObject("panda"));
        featuredProducts.add(new ProductObject("whale"));
        featuredProducts.add(new ProductObject("dolphin"));
        featuredProducts.add(new ProductObject("snall"));
        featuredProducts.add(new ProductObject("shell"));
        featuredProducts.add(new ProductObject("crab"));
        featuredProducts.add(new ProductObject("pignose"));
        featuredProducts.add(new ProductObject("dogfootprint"));
        featuredProducts.add(new ProductObject("squirrel"));
        featuredProducts.add(new ProductObject("prezel"));
        featuredProducts.add(new ProductObject("pie"));
        featuredProducts.add(new ProductObject("pancake"));
        featuredProducts.add(new ProductObject("softdrink"));
        featuredProducts.add(new ProductObject("bowl"));
        featuredProducts.add(new ProductObject("chopsticks"));
        featuredProducts.add(new ProductObject("chinesetakeout"));
        featuredProducts.add(new ProductObject("fortunecookie"));
        featuredProducts.add(new ProductObject("wrap"));
        featuredProducts.add(new ProductObject("wrap2"));
        featuredProducts.add(new ProductObject("curry"));
        featuredProducts.add(new ProductObject("salad"));
        featuredProducts.add(new ProductObject("baguette"));
        featuredProducts.add(new ProductObject("sandwich"));
        featuredProducts.add(new ProductObject("tacco"));
        featuredProducts.add(new ProductObject("meatballs"));
        featuredProducts.add(new ProductObject("burger"));
        featuredProducts.add(new ProductObject("pizza"));
        featuredProducts.add(new ProductObject("meat2"));
        featuredProducts.add(new ProductObject("drumstick"));
        featuredProducts.add(new ProductObject("fies"));
        featuredProducts.add(new ProductObject("cookie"));
        featuredProducts.add(new ProductObject("cookie2"));
        featuredProducts.add(new ProductObject("onigiri"));
        featuredProducts.add(new ProductObject("bbq"));
        featuredProducts.add(new ProductObject("bbq2"));
        featuredProducts.add(new ProductObject("sushi"));
        featuredProducts.add(new ProductObject("tempura"));
        featuredProducts.add(new ProductObject("fishcake"));
        featuredProducts.add(new ProductObject("bendo"));
        featuredProducts.add(new ProductObject("koreanpot"));
        featuredProducts.add(new ProductObject("friedegg"));
        featuredProducts.add(new ProductObject("icecream"));
        featuredProducts.add(new ProductObject("icecream2"));
        featuredProducts.add(new ProductObject("icecream3"));
        featuredProducts.add(new ProductObject("donut"));
        featuredProducts.add(new ProductObject("cake"));
        featuredProducts.add(new ProductObject("birthdaycake"));
        featuredProducts.add(new ProductObject("greentea"));
        featuredProducts.add(new ProductObject("grape"));
        featuredProducts.add(new ProductObject("melon"));
        featuredProducts.add(new ProductObject("watermelon"));
        featuredProducts.add(new ProductObject("pear"));
        featuredProducts.add(new ProductObject("peach"));
        featuredProducts.add(new ProductObject("cherry"));
        featuredProducts.add(new ProductObject("strawberry"));
        featuredProducts.add(new ProductObject("banana"));
        featuredProducts.add(new ProductObject("pineapple"));
        featuredProducts.add(new ProductObject("kiwi"));
        featuredProducts.add(new ProductObject("broccoli"));
        featuredProducts.add(new ProductObject("coconut"));
        featuredProducts.add(new ProductObject("carrot"));
        featuredProducts.add(new ProductObject("mushroom"));
        featuredProducts.add(new ProductObject("tomato"));
        featuredProducts.add(new ProductObject("eggplant"));
        featuredProducts.add(new ProductObject("potato"));
        featuredProducts.add(new ProductObject("sweetpotato"));
        featuredProducts.add(new ProductObject("chestnut"));
        featuredProducts.add(new ProductObject("chili"));
        featuredProducts.add(new ProductObject("bacon"));
        featuredProducts.add(new ProductObject("avacado"));
        featuredProducts.add(new ProductObject("croissant"));
        featuredProducts.add(new ProductObject("beer"));
        featuredProducts.add(new ProductObject("beer2"));
        featuredProducts.add(new ProductObject("champagne"));
        featuredProducts.add(new ProductObject("champagne2"));
        featuredProducts.add(new ProductObject("wine"));
        featuredProducts.add(new ProductObject("wiskey"));
        featuredProducts.add(new ProductObject("cocktail"));
        featuredProducts.add(new ProductObject("sake"));
        featuredProducts.add(new ProductObject("meat"));
        featuredProducts.add(new ProductObject("chocolate"));
        featuredProducts.add(new ProductObject("candy"));
        featuredProducts.add(new ProductObject("lolipop"));
        featuredProducts.add(new ProductObject("pudding"));
        featuredProducts.add(new ProductObject("honey"));
        featuredProducts.add(new ProductObject("popcorn"));
        */
        return featuredProducts;
    }

    @Override
    protected void onStop() {
        pw.dismiss();
        super.onStop();
    }
}

