package ygz.cutiepics;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by yuyuxiao on 2018-02-02.
 */

public class FrameAdapter extends RecyclerView.Adapter<FrameViewHolder> {
    private static final String TAG = FrameAdapter.class.getSimpleName();
    private Context context;
    private List<FrameObject> frameList;

    public FrameAdapter(Context context, List<FrameObject> frameList) {
        this.context = context;
        this.frameList = frameList;
    }

    @Override
    public FrameViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.frame_list, parent, false);
        return new FrameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FrameViewHolder holder, int position) {
        FrameObject FrameObject = frameList.get(position);
        int imageRes = getResourceId(context, FrameObject.getName(), "drawable", context.getPackageName());
        holder.frames.setBackgroundColor(Color.TRANSPARENT);
        holder.frames.setImageResource(imageRes);
    }

    @Override
    public int getItemCount() {
        return frameList.size();
    }

    public static int getResourceId(Context context, String pVariableName, String pResourcename, String pPackageName) throws RuntimeException {
        try {
            return context.getResources().getIdentifier(pVariableName, pResourcename, pPackageName);
        } catch (Exception e) {
            throw new RuntimeException("Error getting Resource ID.", e);
        }
    }
}
