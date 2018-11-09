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
                                    new ID(R.id.p4CLabel, R.id.p4CValue, 4, false)};

    private static Player p1;
    private static Player p2;
    private static Player p3;
    private static Player p4;

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

        ((TextView)findViewById(R.id.p1VPValue)).setText(String.valueOf(p1.vp));
        ((TextView)findViewById(R.id.p2VPValue)).setText(String.valueOf(p2.vp));
        ((TextView)findViewById(R.id.p3VPValue)).setText(String.valueOf(p3.vp));
        ((TextView)findViewById(R.id.p4VPValue)).setText(String.valueOf(p4.vp));
        ((TextView)findViewById(R.id.p1CValue)).setText(String.valueOf(p1.clank));
        ((TextView)findViewById(R.id.p2CValue)).setText(String.valueOf(p2.clank));
        ((TextView)findViewById(R.id.p3CValue)).setText(String.valueOf(p3.clank));
        ((TextView)findViewById(R.id.p4CValue)).setText(String.valueOf(p4.clank));
    }

    public void increaseValue(View view)
    {
        ID selectedId = findSelectedId();
        if (selectedId == null)
        {
            return;
        }

        int pID = selectedId.playerID;

        Player p = pID == 1 ? p1 : pID == 2 ? p2 : pID == 3 ? p3 : p4;

        TextView tv = findViewById(selectedId.valueID);

        if (selectedId.isVP)
        {
            p.vp++;
            tv.setText(String.valueOf(p.vp));
        }
        else
        {
            p.clank++;
            tv.setText(String.valueOf(p.clank));
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

        Player p = pID == 1 ? p1 : pID == 2 ? p2 : pID == 3 ? p3 : p4;

        TextView tv = findViewById(selectedId.valueID);

        if (selectedId.isVP)
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
            tb.setBackgroundColor(Color.LTGRAY);
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
                        v.setBackgroundColor(Color.LTGRAY);
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
                tb.setBackgroundColor(Color.LTGRAY);
                return;
            }
        }
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