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

package com.tekheist.wallpaper.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import com.tekheist.wallpaper.R;
import com.tekheist.wallpaper.databinding.RowLanguageBinding;
import com.tekheist.wallpaper.model.Language;
import com.tekheist.wallpaper.util.ViewUtil;

public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.ViewHolder> {

  private static final String TAG = LanguageAdapter.class.getSimpleName();

  private final List<Language> languages;
  private final String selectedCode;
  private final LanguageAdapterListener listener;

  public LanguageAdapter(
      List<Language> languages, String selectedCode, LanguageAdapterListener listener
  ) {
    this.languages = languages;
    this.selectedCode = selectedCode;
    this.listener = listener;
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {

    private final RowLanguageBinding binding;

    public ViewHolder(RowLanguageBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new ViewHolder(
        RowLanguageBinding.inflate(
            LayoutInflater.from(parent.getContext()), parent, false
        )
    );
  }

  @Override
  public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
    holder.binding.linearLanguageContainer.setBackground(
        ViewUtil.getRippleBgListItemSurface(holder.binding.getRoot().getContext())
    );

    if (position == 0) {
      holder.binding.textLanguageName.setText(R.string.other_language_system);
      holder.binding.textLanguageTranslators.setText(R.string.other_language_system_description);

      holder.binding.imageLanguageSelected.setVisibility(
          selectedCode != null ? View.INVISIBLE : View.VISIBLE
      );
      holder.binding.linearLanguageContainer.setOnClickListener(
          view -> listener.onItemRowClicked(null)
      );
      return;
    }

    Language language = languages.get(holder.getAdapterPosition() - 1);
    holder.binding.textLanguageName.setText(language.getName());
    holder.binding.textLanguageTranslators.setText(language.getTranslators());

    // SELECTED

    holder.binding.imageLanguageSelected.setVisibility(
        language.getCode().equals(selectedCode) ? View.VISIBLE : View.INVISIBLE
    );

    // CONTAINER

    holder.binding.linearLanguageContainer.setOnClickListener(
        view -> listener.onItemRowClicked(language)
    );
  }

  @Override
  public int getItemCount() {
    return languages.size() + 1;
  }

  public interface LanguageAdapterListener {

    void onItemRowClicked(@Nullable Language language);
  }
}
