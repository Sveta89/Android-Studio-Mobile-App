package svetlanavictorino.mywgutracker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class TermListAdaptor extends ArrayAdapter<Term> {
    private List<Term> terms;

    public TermListAdaptor(@NonNull Context context, int resource, @NonNull List<Term> objects) {
        super(context, resource, objects);
        terms = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).
                    inflate(R.layout.list_item_term, parent, false);
        }

        Term term = terms.get(position);

        TextView nameTerm = (TextView) convertView.findViewById(R.id.nameTerm);
        nameTerm.setText(term.getTitle());

        TextView startDataTerm = (TextView) convertView.findViewById(R.id.startData);
        startDataTerm.setText(term.getStart());

        TextView endDataTerm = (TextView) convertView.findViewById(R.id.endData);
        endDataTerm.setText(term.getEnd());

        return convertView;
    }
}
