package collectifyamoukoudji.projetseg;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class ResaList extends ArrayAdapter<Reservation> {

    /**
     * Contains a Activity type
     * that is assigned for context.
     */
    private Activity context;
    /**
     * Contains a List type
     * that is assigned for the list of users.
     */
    private List<Reservation> users;
    /**
     * Constructor
     */
    public ResaList(Activity context, List<Reservation> users) {
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

        DateFormat day = new SimpleDateFormat("dd/mm/yyyy");



        Reservation user = users.get(position);
        String dayName = day.format(user.getrDate());
        textViewName.setText("Client : "+ user.get_clientname());
        textViewPrice.setText("Date : "+ user.getType_service() + " -> " + dayName);
        return listViewItem;
    }
}
