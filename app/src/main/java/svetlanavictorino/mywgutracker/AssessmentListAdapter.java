package svetlanavictorino.mywgutracker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class AssessmentListAdapter extends ArrayAdapter<Assessment> {

    private List<Assessment> assessments;

    public AssessmentListAdapter(Context context, int resource, List<Assessment> objects) {
        super(context, resource, objects);
        assessments = objects;
    }

     @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).
                    inflate(R.layout.list_item_assessments, parent, false);
        }

        Assessment assessment = assessments.get(position);

        TextView assessmentTitle = (TextView) convertView.findViewById(R.id.assessmentTitle);
         assessmentTitle.setText(assessment.getTitle());

        TextView assessmentType = (TextView) convertView.findViewById(R.id.assessmentType);
         assessmentType.setText(assessment.getType());

        return convertView;
    }


}
