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

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import androidx.preference.PreferenceManager;
import java.util.Objects;
import com.tekheist.wallpaper.Constants.DEF;
import com.tekheist.wallpaper.Constants.PREF;
import com.tekheist.wallpaper.drawable.SvgDrawable;

public class PrefsUtil {

  private static final String TAG = PrefsUtil.class.getSimpleName();

  private final Context context;
  private SharedPreferences sharedPrefs;

  public PrefsUtil(Context context) {
    this.context = context;
    migrateToStorageContext();
  }

  private void migrateToStorageContext() {
    Context storageContext;
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      Context deviceContext = context.createDeviceProtectedStorageContext();
      boolean success = deviceContext.moveSharedPreferencesFrom(
          context, context.getPackageName() + "_preferences"
      );
      if (!success) {
        Log.w(TAG, "Failed to migrate shared preferences to storage context");
      }
      storageContext = deviceContext;
    } else {
      storageContext = context;
    }
    sharedPrefs = PreferenceManager.getDefaultSharedPreferences(storageContext);
  }

  public PrefsUtil checkForMigrations() {

    // theme to wallpaper
    migrateString("theme", PREF.WALLPAPER, DEF.WALLPAPER);

    // size is stored in a new way
    if (sharedPrefs.contains(PREF.SCALE)) {
      try {
        sharedPrefs.getFloat(PREF.SCALE, SvgDrawable.getDefaultScale(context));
      } catch (Exception e) {
        removePreference(PREF.SCALE);
      }
    }

    // zoom is stored in a new way
    if (sharedPrefs.contains(PREF.ZOOM)) {
      int zoom = sharedPrefs.getInt(PREF.ZOOM, DEF.ZOOM);
      if (zoom >= 2 && zoom <= 5) {
        sharedPrefs.edit().putInt(PREF.ZOOM, zoom).apply();
      } else {
        removePreference(PREF.ZOOM);
      }
    }

    // damping to damping_tilt
    migrateInteger("damping", PREF.DAMPING_TILT, DEF.DAMPING_TILT);

    // variant is stored in a new way

    if (sharedPrefs.contains(PREF.VARIANT_PIXEL)) {
      try {
        sharedPrefs.getInt(PREF.VARIANT_PIXEL, 1);
      } catch (ClassCastException e) {
        removePreference(PREF.VARIANT_PIXEL);
      }
    }
    if (sharedPrefs.contains(PREF.VARIANT_JOHANNA)) {
      try {
        sharedPrefs.getInt(PREF.VARIANT_JOHANNA, 1);
      } catch (ClassCastException e) {
        removePreference(PREF.VARIANT_JOHANNA);
      }
    }
    if (sharedPrefs.contains(PREF.VARIANT_REIKO)) {
      try {
        sharedPrefs.getInt(PREF.VARIANT_REIKO, 1);
      } catch (ClassCastException e) {
        removePreference(PREF.VARIANT_REIKO);
      }
    }
    if (sharedPrefs.contains(PREF.VARIANT_ANTHONY)) {
      try {
        sharedPrefs.getInt(PREF.VARIANT_ANTHONY, 1);
      } catch (ClassCastException e) {
        removePreference(PREF.VARIANT_ANTHONY);
      }
    }

    // night mode is now stored as integer, follow system no longer needed
    if (sharedPrefs.contains(PREF.NIGHT_MODE)) {
      try {
        sharedPrefs.getInt(PREF.NIGHT_MODE, DEF.NIGHT_MODE);
      } catch (ClassCastException e) {
        removePreference(PREF.NIGHT_MODE);
      }
    }

    // language is now stored with "-" instead of "_"
    if (sharedPrefs.contains(PREF.LANGUAGE)) {
      String language = sharedPrefs.getString(PREF.LANGUAGE, DEF.LANGUAGE);
      if (language != null && language.contains("_")) {
        sharedPrefs.edit()
            .putString(PREF.LANGUAGE, language.replace("_", "-"))
            .apply();
      }
    }

    // random mode is now stored as string with different modes
    if (sharedPrefs.contains(PREF.RANDOM)) {
      try {
        sharedPrefs.getString(PREF.RANDOM, DEF.RANDOM);
      } catch (ClassCastException e) {
        removePreference(PREF.RANDOM);
      }
    }

    return this;
  }

  public SharedPreferences getSharedPrefs() {
    return sharedPrefs;
  }

  private void migrateString(String keyOld, String keyNew, String def) {
    if (sharedPrefs.contains(keyOld)) {
      SharedPreferences.Editor editor = sharedPrefs.edit();
      try {
        String current = sharedPrefs.getString(keyOld, def);
        if (!Objects.equals(current, def)) {
          editor.remove(keyOld);
          editor.putString(keyNew, current);
        }
      } catch (ClassCastException ignored) {
        editor.remove(keyOld);
      }
      editor.apply();
    }
  }

  private void migrateBoolean(String keyOld, String keyNew, boolean def) {
    if (sharedPrefs.contains(keyOld)) {
      SharedPreferences.Editor editor = sharedPrefs.edit();
      try {
        boolean current = sharedPrefs.getBoolean(keyOld, def);
        if (!Objects.equals(current, def)) {
          editor.remove(keyOld);
          editor.putBoolean(keyNew, current);
        }
      } catch (ClassCastException ignored) {
        editor.remove(keyOld);
      }
      editor.apply();
    }
  }

  private void migrateInteger(String keyOld, String keyNew, int def) {
    if (sharedPrefs.contains(keyOld)) {
      SharedPreferences.Editor editor = sharedPrefs.edit();
      try {
        int current = sharedPrefs.getInt(keyOld, def);
        if (!Objects.equals(current, def)) {
          editor.remove(keyOld);
          editor.putInt(keyNew, current);
        }
      } catch (ClassCastException ignored) {
        editor.remove(keyOld);
      }
      editor.apply();
    }
  }

  private void migrateFloat(String keyOld, String keyNew, float def) {
    if (sharedPrefs.contains(keyOld)) {
      SharedPreferences.Editor editor = sharedPrefs.edit();
      try {
        float current = sharedPrefs.getFloat(keyOld, def);
        if (!Objects.equals(current, def)) {
          editor.remove(keyOld);
          editor.putFloat(keyNew, current);
        }
      } catch (ClassCastException ignored) {
        editor.remove(keyOld);
      }
      editor.apply();
    }
  }

  private void removePreference(String key) {
    if (sharedPrefs.contains(key)) {
      sharedPrefs.edit().remove(key).apply();
    }
  }
}
