package com.krohgsolutions.clankscoretracker;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity
{
    private final static ID[] ids = {new ID(R.id.p1VPLabel, R.id.p1VPValue, 1, true),
                                    new ID(R.id.p1CLabel, R.id.p1CValue, 1, false),
                                    new ID(R.id.p2VPLabel, R.id.p2VPValue, 2, true),
                                    new ID(R.id.p2CLabel, R.id.p2CValue, 2, false),
                                    new ID(R.id.p3VPLabel, R.id.p3VPValue, 3, true),
                                    new ID(R.id.p3CLabel, R.id.p3CValue, 3, false),
                                    new ID(R.id.p4VPLabel, R.id.p4VPValue, 4, true),
                                    new ID(R.id.p4CLabel, R.id.p4CValue, 4, false),
                                    new ID(R.id.bLabel, R.id.bValue, 0, true),
                                    new ID(R.id.bhLabel, R.id.bhValue, 0, false)};

    private static Player p1;
    private static Player p2;
    private static Player p3;
    private static Player p4;

    private static int blackCount;
    private static int bhCount;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addListeners();

        p1 = new Player(1);
        p2 = new Player(2);
        p3 = new Player(3);
        p4 = new Player(4);

        blackCount = 24;
        bhCount = 0;

        ((TextView)findViewById(R.id.p1VPValue)).setText(String.valueOf(p1.vp));
        ((TextView)findViewById(R.id.p2VPValue)).setText(String.valueOf(p2.vp));
        ((TextView)findViewById(R.id.p3VPValue)).setText(String.valueOf(p3.vp));
        ((TextView)findViewById(R.id.p4VPValue)).setText(String.valueOf(p4.vp));
        ((TextView)findViewById(R.id.p1CValue)).setText(String.valueOf(p1.clank));
        ((TextView)findViewById(R.id.p2CValue)).setText(String.valueOf(p2.clank));
        ((TextView)findViewById(R.id.p3CValue)).setText(String.valueOf(p3.clank));
        ((TextView)findViewById(R.id.p4CValue)).setText(String.valueOf(p4.clank));
        ((TextView)findViewById(R.id.bValue)).setText(String.valueOf(blackCount));
        ((TextView)findViewById(R.id.bhValue)).setText(String.valueOf(bhCount));

        updatePercents();
    }

    public void increaseValue(View view)
    {
        ID selectedId = findSelectedId();
        if (selectedId == null)
        {
            return;
        }

        int pID = selectedId.playerID;

        Player p = pID == 1 ? p1 : pID == 2 ? p2 : pID == 3 ? p3 : pID == 4 ? p4 : null;

        TextView tv = findViewById(selectedId.valueID);

        if (p == null)
        {
            if (selectedId.isVP)
            {
                if (blackCount == 24) return;

                blackCount++;
                tv.setText(String.valueOf(blackCount));
            }
            else
            {
                if (bhCount == 4) return;

                bhCount++;
                tv.setText(String.valueOf(bhCount));
            }

            updatePercents();
        }
        else if (selectedId.isVP)
        {
            p.vp++;
            tv.setText(String.valueOf(p.vp));
        }
        else
        {
            if (p.clank == 30) return;

            p.clank++;
            tv.setText(String.valueOf(p.clank));
            updatePercents();
        }
    }

    public void decreaseValue(View view)
    {
        ID selectedId = findSelectedId();

        if (selectedId == null)
        {
            return;
        }

        int pID = selectedId.playerID;

        Player p = pID == 1 ? p1 : pID == 2 ? p2 : pID == 3 ? p3 : pID == 4 ? p4 : null;

        TextView tv = findViewById(selectedId.valueID);

        if (p == null)
        {
            if (selectedId.isVP)
            {
                if (blackCount == 0) return;

                blackCount--;
                tv.setText(String.valueOf(blackCount));
            }
            else
            {
                if (bhCount == 0) return;

                bhCount--;
                tv.setText(String.valueOf(bhCount));
            }

            updatePercents();
        }
        else if (selectedId.isVP)
        {
            if (p.vp == 0) return;

            p.vp--;
            tv.setText(String.valueOf(p.vp));
        }
        else
        {
            if (p.clank == 0) return;

            p.clank--;
            tv.setText(String.valueOf(p.clank));
            updatePercents();
        }
    }

    private ID findSelectedId()
    {
        for (ID id : ids)
        {
            ToggleButton tb = findViewById(id.labelID);

            if (tb.isChecked())
            {
                return id;
            }
        }

        return null;
    }

    private void addListeners()
    {
        for (final ID id : ids)
        {
            ToggleButton tb = findViewById(id.labelID);

            if (id.labelID == R.id.bLabel) tb.setBackgroundColor(Color.BLACK);
            else if (id.labelID == R.id.bhLabel) tb.setBackgroundColor(Color.RED);
            else tb.setBackgroundColor(Color.LTGRAY);

            tb.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (((ToggleButton)v).isChecked())
                    {
                        unCheckAllOtherButtons(id.labelID);

                        v.setBackgroundColor(Color.GREEN);
                    }
                    else
                    {
                        if (v.getId() == R.id.bLabel) v.setBackgroundColor(Color.BLACK);
                        else if (v.getId() == R.id.bhLabel) v.setBackgroundColor(Color.RED);
                        else v.setBackgroundColor(Color.LTGRAY);
                    }
                }
            });
        }
    }

    private void unCheckAllOtherButtons(int checkedID)
    {
        for (ID id : ids)
        {
            if (id.labelID == checkedID)
            {
                continue;
            }

            ToggleButton tb = findViewById(id.labelID);

            if (tb.isChecked())
            {
                tb.setChecked(false);

                if (tb.getId() == R.id.bLabel) tb.setBackgroundColor(Color.BLACK);
                else if (tb.getId() == R.id.bhLabel) tb.setBackgroundColor(Color.RED);
                else tb.setBackgroundColor(Color.LTGRAY);
                return;
            }
        }
    }

    private void updatePercents()
    {
        float sum = p1.clank + p2.clank + p3.clank + p4.clank + blackCount + bhCount;

        float p1Fraction = p1.clank / sum;
        float p2Fraction = p2.clank / sum;
        float p3Fraction = p3.clank / sum;
        float p4Fraction = p4.clank / sum;
        float blackFraction = blackCount / sum;
        float bhFraction = bhCount / sum;

        int p1Percent = Math.round(p1Fraction*100);
        int p2Percent = Math.round(p2Fraction*100);
        int p3Percent = Math.round(p3Fraction*100);
        int p4Percent = Math.round(p4Fraction*100);
        int blackPercent = Math.round(blackFraction*100);
        int bhPercent = Math.round(bhFraction*100);

        ((TextView)findViewById(R.id.p1Percent)).setText(String.valueOf(p1Percent).concat("%"));
        setColor(R.id.p1Percent, p1Percent);
        ((TextView)findViewById(R.id.p2Percent)).setText(String.valueOf(p2Percent).concat("%"));
        setColor(R.id.p2Percent, p2Percent);
        ((TextView)findViewById(R.id.p3Percent)).setText(String.valueOf(p3Percent).concat("%"));
        setColor(R.id.p3Percent, p3Percent);
        ((TextView)findViewById(R.id.p4Percent)).setText(String.valueOf(p4Percent).concat("%"));
        setColor(R.id.p4Percent, p4Percent);
        ((TextView)findViewById(R.id.bPercent)).setText(String.valueOf(blackPercent).concat("%"));
        ((TextView)findViewById(R.id.bhPercent)).setText(String.valueOf(bhPercent).concat("%"));
    }

    private void setColor(int viewID, int percent)
    {
        ((TextView)findViewById(viewID)).setTextColor(percent <= 20 ? Color.rgb(39, 153, 7) :
                percent <= 40 ? Color.rgb(135, 142, 31) : Color.rgb(140, 7, 7));
    }

    private static class ID
    {
        private int labelID;
        private int valueID;
        private int playerID;
        private boolean isVP;

        private ID(int labelID, int valueID, int playerID, boolean isVP)
        {
            this.labelID = labelID;
            this.valueID = valueID;
            this.playerID = playerID;
            this.isVP = isVP;
        }
    }
}