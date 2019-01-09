package collectifyamoukoudji.projetseg;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ResaFragment extends Fragment{

    View myView;

    private Button search;
    private String iduser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.four_layout, container, false);

        search = (Button) myView.findViewById(R.id.btnRes);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            iduser = bundle.getString("iduser");
        }

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ViewResActivity.class);
                intent.putExtra("iduser", iduser);
                startActivity(intent);
            }
        });

        return myView;
    }


}