package de.paraair.ardmix;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by onkel on 15.11.16.
 */
public class FileSelectLayout extends ListView {

    private final Context context;
    public OnClickListener onClickListener;

    public FileSelectLayout(Context context) {
        super(context);
        this.context = context;

    }

    public void setFileNames(ArrayList<String> strings) {

        ArrayAdapter<String> adapter = new FileAdapter(context, strings);

        this.setAdapter(adapter);
    }
    private class FileAdapter extends ArrayAdapter<String> {
        public FileAdapter(Context context, ArrayList<String> tracks ) {
            super(context, 0, tracks);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            // Get the data item for this position
            String file = getItem(position);


            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.file_select_item, parent, false);
            }
            // Lookup view for data population
            TextView tvName = (TextView) convertView.findViewById(R.id.filename);

            // Populate the data into the template view using the data object
            tvName.setText(file);
            tvName.setTag(file);
            tvName.setClickable(true);
//            tvName.setChecked(track.enabled);
            tvName.setOnClickListener(onClickListener);
            // Return the completed view to render on screen
            return convertView;
        }
    }

}
