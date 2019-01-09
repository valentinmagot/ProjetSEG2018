package collectifyamoukoudji.projetseg;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class UsersList extends ArrayAdapter<Users> {

    /**
     * Contains a Activity type
     * that is assigned for context.
     */
    private Activity context;
    /**
     * Contains a List type
     * that is assigned for the list of users.
     */
    private List<Users> users;
    /**
     * Constructor
     */
    public UsersList( Activity context, List<Users> users) {
        super(context, R.layout.layout_user_list, users);
        this.context =  context;
        this.users = users;
    }
    @Override

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_user_list, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewPrice = (TextView) listViewItem.findViewById(R.id.textViewPrice);

        Users user = users.get(position);
        textViewName.setText(user.get_firstname()+" "+user.get_lastname());
        textViewPrice.setText(user.get_email()+" "+user.get_type());
        return listViewItem;
    }
}
