package com.github.hintofbasil.crabbler.Questions.QuestionExpanders;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.github.hintofbasil.crabbler.R;

import org.json.JSONException;
import org.json.JSONObject;

public class TwoChoiceDate extends Expander {

    CheckBox choiceOneCheckBox;
    CheckBox choiceTwoCheckBox;
    ListView monthListView;
    ListView yearListView;

    int monthSelected = -1;
    int yearSelected = -1;

    public TwoChoiceDate(AppCompatActivity activity) {
        super(activity);
    }

    @Override
    public void expandLayout(JSONObject question) throws JSONException {
        activity.setContentView(R.layout.activity_two_choice_date);

        ImageView imageView = (ImageView) activity.findViewById(R.id.image);
        TextView titleView = (TextView) activity.findViewById(R.id.title);
        choiceOneCheckBox = (CheckBox) activity.findViewById(R.id.choice_one);
        choiceTwoCheckBox = (CheckBox) activity.findViewById(R.id.choice_two);
        monthListView = (ListView) activity.findViewById(R.id.month_list_view);
        yearListView = (ListView) activity.findViewById(R.id.year_list_view);

        imageView.setImageDrawable(getDrawable(question.getString("questionPicture")));
        titleView.setText(question.getString("questionTitle"));
        choiceOneCheckBox.setText(question.getString("choiceOneText"));
        choiceTwoCheckBox.setText(question.getString("choiceTwoText"));

        choiceOneCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choiceTwoCheckBox.setChecked(false);
            }
        });

        choiceTwoCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choiceOneCheckBox.setChecked(false);
            }
        });

        ArrayAdapter<String> monthsAdapter = new ArrayAdapter<String>(
                activity.getBaseContext(),
                android.R.layout.simple_list_item_1,
                activity.getResources().getStringArray(R.array.months));
        monthListView.setAdapter(monthsAdapter);

        ArrayAdapter<String> yearsAdapter = new ArrayAdapter<String>(
                activity.getBaseContext(),
                android.R.layout.simple_list_item_1,
                activity.getResources().getStringArray(R.array.years));
        yearListView.setAdapter(yearsAdapter);

        monthListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Remove previous
                View previous = parent.getChildAt(monthSelected);
                if(previous!=null) {
                    previous.setBackgroundResource(android.R.color.white);
                }
                monthSelected = position;
                view.setBackgroundResource(R.color.questionSelectedBackground);
            }
        });

        yearListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                View previous = parent.getChildAt(yearSelected);
                if(previous!=null) {
                    previous.setBackgroundResource(android.R.color.white);
                }
                yearSelected = position;
                view.setBackgroundResource(R.color.questionSelectedBackground);
            }
        });



    }

    @Override
    protected void setPreviousAnswer(String answer) {
        switch(answer) {
            case "0":
                choiceOneCheckBox.setChecked(true);
                break;
            case "1":
                choiceTwoCheckBox.setChecked(true);
                break;
            default:
                Log.d("TwoChoiceDate", "No previous answer");
                break;
        }
    }

    @Override
    public String getSelectedAnswer() {
        if(choiceOneCheckBox.isChecked()) {
            return "0";
        } else if(choiceTwoCheckBox.isChecked()) {
            return "1";
        } else {
            return null;
        }
    }
}
