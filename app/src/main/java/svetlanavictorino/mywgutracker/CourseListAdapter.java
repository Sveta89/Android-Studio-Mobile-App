package svetlanavictorino.mywgutracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CourseListAdapter extends ArrayAdapter<Course> {

    private List<Course> courses;

    public CourseListAdapter(Context context, int resource, List<Course> objects) {
        super(context, resource, objects);
        courses = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).
                    inflate(R.layout.list_item_courses, parent, false);
        }

        Course course = courses.get(position);

        TextView codeCourse = (TextView) convertView.findViewById(R.id.codeCourse);
        codeCourse.setText(course.getCode());

        TextView titleCourse = (TextView) convertView.findViewById(R.id.titleCourse);
        titleCourse.setText(course.getTitle());

        return convertView;
    }

}
