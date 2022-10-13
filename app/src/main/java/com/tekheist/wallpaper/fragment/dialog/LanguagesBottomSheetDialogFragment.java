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

package com.tekheist.wallpaper.fragment.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.LocaleListCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.tekheist.wallpaper.activity.MainActivity;
import com.tekheist.wallpaper.adapter.LanguageAdapter;
import com.tekheist.wallpaper.databinding.FragmentBottomsheetLanguagesBinding;
import com.tekheist.wallpaper.model.Language;
import com.tekheist.wallpaper.util.LocaleUtil;
import com.tekheist.wallpaper.util.UiUtil;

public class LanguagesBottomSheetDialogFragment extends BaseBottomSheetDialogFragment
    implements LanguageAdapter.LanguageAdapterListener {

  private static final String TAG = "LanguagesBottomSheet";

  private FragmentBottomsheetLanguagesBinding binding;

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater,
      ViewGroup container,
      Bundle savedInstanceState) {
    binding = FragmentBottomsheetLanguagesBinding.inflate(inflater, container, false);

    MainActivity activity = (MainActivity) requireActivity();

    binding.recyclerLanguages.setLayoutManager(
        new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
    );
    binding.recyclerLanguages.setAdapter(
        new LanguageAdapter(
            LocaleUtil.getLanguages(activity),
            LocaleUtil.getLanguageCode(AppCompatDelegate.getApplicationLocales()),
            this
        )
    );

    return binding.getRoot();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    binding = null;
  }

  @Override
  public void onItemRowClicked(@Nullable Language language) {
    String code = language != null ? language.getCode() : null;
    LocaleListCompat previous = AppCompatDelegate.getApplicationLocales();
    LocaleListCompat selected = LocaleListCompat.forLanguageTags(code);
    if (!previous.equals(selected)) {
      performHapticClick();
      dismiss();
      AppCompatDelegate.setApplicationLocales(selected);
    }
  }

  @Override
  public void applyBottomInset(int bottom) {
    binding.recyclerLanguages.setPadding(
        0, UiUtil.dpToPx(requireContext(), 8),
        0, UiUtil.dpToPx(requireContext(), 8) + bottom
    );
  }

  @NonNull
  @Override
  public String toString() {
    return TAG;
  }
}
