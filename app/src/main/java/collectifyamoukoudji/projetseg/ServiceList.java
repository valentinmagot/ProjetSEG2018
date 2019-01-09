package collectifyamoukoudji.projetseg;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ServiceList extends ArrayAdapter<Service> {
    /**
     * Contains a Activity type
     * that is assigned for context.
     */
    private Activity context;
    /**
     * Contains a List type
     * that is assigned for the list of services.
     */
    List<Service> services;
    /**
     * Constructor
     */
    public ServiceList(Activity context, List<Service> services) {
        super(context, R.layout.layout_service_list, services);
        this.context = context;
        this.services = services;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_service_list, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewPrice = (TextView) listViewItem.findViewById(R.id.textViewPrice);

        Service service = services.get(position);
        textViewName.setText(service.getServiceName());
        textViewPrice.setText(String.valueOf(service.getRate()));
        return listViewItem;
    }
}