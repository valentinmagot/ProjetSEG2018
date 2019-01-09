package collectifyamoukoudji.projetseg;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    Context context;
    ArrayList<String> fullNameList;
    ArrayList<String> userNameList;
    ArrayList<String> userTimesList;
    ArrayList<String> userIdList;

    class SearchViewHolder extends RecyclerView.ViewHolder {
        TextView full_name, user_name;

        public SearchViewHolder(View itemView) {
            super(itemView);
            full_name = (TextView) itemView.findViewById(R.id.full_name);
            user_name = (TextView) itemView.findViewById(R.id.user_name);
//            user_time = (TextView) itemView.findViewById(R.id.user_time);
        }
    }

    public SearchAdapter(Context context, ArrayList<String> fullNameList, ArrayList<String> userNameList, ArrayList<String> userTimeList, ArrayList<String> userIdList ) {
        this.context = context;
        this.fullNameList = fullNameList;
        this.userNameList = userNameList;
        this.userTimesList = userTimeList;
        this.userIdList = userIdList;
    }

    @Override
    public SearchAdapter.SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_list_items, parent, false);
        return new SearchAdapter.SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, final int position) {
        holder.full_name.setText(fullNameList.get(position));
        holder.user_name.setText(String.valueOf(userNameList.get(position)));
//        holder.user_time.setText(userTimesList.get(position));

        holder.full_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, AfficherFourActivity.class);
                i.putExtra("idUser",userIdList.get(position) );
                i.putExtra( "list" ,userTimesList.toArray(new String[userTimesList.size()]));
                context.startActivity(i);

            }
        });
    }

    @Override
    public int getItemCount() {
        return fullNameList.size();
    }
}
