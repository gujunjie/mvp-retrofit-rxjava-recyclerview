package view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.abc.myapplication29.R;

import java.util.List;

import model.BaiduImage;
import util.NetworkUtil;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {

    private  List<BaiduImage.ImgsBean> list;
    private Context context;

    static class ViewHolder extends RecyclerView.ViewHolder
    {   View photoView;
        ImageView photoImage;
        TextView photoText;

        public ViewHolder(View view)
        {
            super(view);
            photoView=view;
            photoImage=(ImageView)view.findViewById(R.id.iv_image);
            photoText=(TextView)view.findViewById(R.id.tv_text);

        }
    }

    public PhotoAdapter(List<BaiduImage.ImgsBean> list, Context context)
    {
        list.remove(list.size()-1);
        this.list=list;
        this.context=context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.photos_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"click"+holder.getAdapterPosition(),Toast.LENGTH_SHORT).show();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BaiduImage.ImgsBean photo=list.get(position);
        holder.photoText.setText(photo.getImageHeight()+"x"+photo.getImageWidth());


        LinearLayout.LayoutParams layoutParams=(LinearLayout.LayoutParams)holder.photoImage.getLayoutParams();
        float itemWidth=(NetworkUtil.getScreenWidth(holder.photoView.getContext())-3*4)/2;
        layoutParams.width=(int)itemWidth;
        float scale=(itemWidth+0f)/photo.getImageWidth();
        layoutParams.height=(int)(photo.getImageHeight()*scale);
        holder.photoImage.setLayoutParams(layoutParams);
        Glide.with(context).load(photo.getImageUrl()).override(layoutParams.width,layoutParams.height).into(holder.photoImage);




    }



    @Override
    public int getItemCount() {
        return list.size();
    }
}
