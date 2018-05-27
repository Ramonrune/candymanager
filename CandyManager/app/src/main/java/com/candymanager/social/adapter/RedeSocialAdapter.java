package com.candymanager.social.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.candymanager.R;
import com.candymanager.cliente.ClienteModel;
import com.candymanager.social.RedeSocialModel;
import com.candymanager.util.BitmapUtil;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Usuario on 17/05/2018.
 */

public class RedeSocialAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<RedeSocialModel> redeSocialModelArrayList;

    public RedeSocialAdapter(Context context, ArrayList<RedeSocialModel> lista) {
        this.context = context;
        System.out.println("A lista possui " + lista.size());
        redeSocialModelArrayList = lista;


    }

    @Override
    public int getCount() {
        return redeSocialModelArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return redeSocialModelArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.model_rede_social, viewGroup, false);
        TextView descricaoTextView = (TextView) row.findViewById(R.id.descricaoTextView);
        TextView dataTextView = (TextView) row.findViewById(R.id.dataTextView);
        TextView curtidasTextView = (TextView) row.findViewById(R.id.curtidasTextView);
        TextView comentariosTextView = (TextView) row.findViewById(R.id.comentariosTextView);
        ImageView likesImageView = (ImageView) row.findViewById(R.id.likeImageView);
        ImageView commentImageView = (ImageView) row.findViewById(R.id.commentImageView);

        new DownloadImageTask((ImageView) row.findViewById(R.id.fotoImageView))
                .execute(redeSocialModelArrayList.get(i).getFullPicture());

        descricaoTextView.setText(redeSocialModelArrayList.get(i).getDescription());
        dataTextView.setText(redeSocialModelArrayList.get(i).getCreatedTime());

        curtidasTextView.setText(String.valueOf(redeSocialModelArrayList.get(i).getLikes()));
        comentariosTextView.setText(String.valueOf(redeSocialModelArrayList.get(i).getComments()));

        if(redeSocialModelArrayList.get(i).getType() == 1) {
            likesImageView.setImageResource(R.drawable.like);
        }else{
            likesImageView.setImageResource(R.drawable.heart);
        }

        if(redeSocialModelArrayList.get(i).getType() == 3){
            commentImageView.setImageResource(R.drawable.retweet);
        }
        return row;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            if (result != null && bmImage != null) {
                result = Bitmap.createScaledBitmap(result, 300, 300, true);
                result = BitmapUtil.getCroppedBitmap(result);
                bmImage.setImageBitmap(result);
            }
        }
    }
}
