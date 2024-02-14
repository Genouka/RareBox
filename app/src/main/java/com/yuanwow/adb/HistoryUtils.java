package com.yuanwow.adb;
import android.content.SharedPreferences;
import java.util.LinkedList;
import android.content.Context;
import android.view.ContextMenu;
import android.view.Menu;

/**
 * @Author genouka
 * @Date 2023/04/05 10:09
 */
public class HistoryUtils {
    private SharedPreferences prefs;
    private LinkedList<String> previousCommands;
    private int historyLimit;

    public static HistoryUtils loadCommandHistoryFromPrefs(int limit, Context context, String pref) {
        HistoryUtils ch = new HistoryUtils(limit);

        ch.prefs = context.getSharedPreferences(pref, 0);
        int size = ch.prefs.getInt("Size", 0);
        for (int i = 0; i < size; i++) {
            String cmd = ch.prefs.getString(""+i, null);
            if (cmd != null)
                ch.add(cmd);
        }

        return ch;
    }

    private HistoryUtils(int historyLimit) {
        this.previousCommands = new LinkedList<String>();
        this.historyLimit = historyLimit;
    }

    public void add(String command) {
        if (previousCommands.size() > historyLimit)
            previousCommands.removeFirst();

        previousCommands.add(command);
    }

    public void populateMenu(ContextMenu menu) {
        /* We iterate backwards because the first item added is the latest in the command list */
        for (int i = previousCommands.size()-1; i >= 0; i--)
            menu.add(Menu.NONE, 0, Menu.NONE, previousCommands.get(i));
    }

    public void save() {
        SharedPreferences.Editor edit = prefs.edit();
        for (int i = 0; i < previousCommands.size(); i++)
        {
            edit.putString(""+i, previousCommands.get(i));
        }
        edit.putInt("Size", previousCommands.size());
        edit.apply();
    }
}
