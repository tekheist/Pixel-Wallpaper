/*
 * This file is part of Doodle Android.
 *
 * Doodle Android is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Doodle Android is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Doodle Android. If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright (c) 2019-2022 by Patrick Zedler
 */

package com.tekheist.wallpaper.util;

import android.app.AlarmManager;
import android.os.SystemClock;
import androidx.annotation.NonNull;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import com.tekheist.wallpaper.Constants.DEF;

public class RandomUtil {

  private static final String TAG = RandomUtil.class.getSimpleName();

  private long period;
  private long lastChange;
  private final Action action;
  private Timer timer;

  public interface Action {

    void execute(boolean force);
  }

  public RandomUtil(@NonNull Action action) {
    this.action = action;
  }

  public void scheduleDaily(String time) {
    if (time == null) {
      time = DEF.DAILY_TIME;
    }

    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(System.currentTimeMillis());

    String[] parts = time.split(":");
    calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(parts[0]));
    calendar.set(Calendar.MINUTE, Integer.parseInt(parts[1]));
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
      calendar.add(Calendar.DAY_OF_YEAR, 1);
    }

    if (timer != null) {
      timer.cancel();
      timer = null;
    }
    timer = new Timer();
    timer.scheduleAtFixedRate(
        new TimerTask() {
          @Override
          public void run() {
            changeIfRequired(false);
          }
        },
        calendar.getTime(),
        AlarmManager.INTERVAL_DAY
    );

    this.period = AlarmManager.INTERVAL_DAY;
    calendar.add(Calendar.DAY_OF_YEAR, -1);
    long difference = System.currentTimeMillis() - calendar.getTimeInMillis();
    lastChange = SystemClock.elapsedRealtime() - difference;
  }

  public void scheduleInterval(long period) {
    this.period = period;
    lastChange = SystemClock.elapsedRealtime();

    if (timer != null) {
      timer.cancel();
      timer = null;
    }
    timer = new Timer();
    timer.scheduleAtFixedRate(
        new TimerTask() {
          @Override
          public void run() {
            changeIfRequired(false);
          }
        },
        period,
        period
    );
  }

  public void changeIfRequired(boolean force) {
    long currentTime = SystemClock.elapsedRealtime(); // 1h      | 1h25m |
    long nextChange = lastChange + period;            // 0 + 15m | 1h15m | 1h30m

    if (currentTime >= nextChange) {
      action.execute(force);
      // next change
      long difference = currentTime - nextChange;     // 45m     | 10m
      int multiple = (int) (difference / period);     // 3       | 0
      lastChange += period + multiple * period;       // 1h      | 1h15m
    }
  }

  public void cancel() {
    if (timer != null) {
      timer.cancel();
      timer = null;
    }
  }
}
