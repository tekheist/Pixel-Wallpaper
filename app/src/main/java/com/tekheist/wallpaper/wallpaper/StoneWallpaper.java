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

package com.tekheist.wallpaper.wallpaper;

import androidx.annotation.NonNull;
import com.tekheist.wallpaper.Constants.WALLPAPER;
import com.tekheist.wallpaper.R;
import com.tekheist.wallpaper.drawable.SvgDrawable;

public class StoneWallpaper extends BaseWallpaper {

  @NonNull
  @Override
  public String getName() {
    return WALLPAPER.STONE;
  }

  @Override
  public int getThumbnailResId() {
    return R.drawable.selection_stone;
  }

  @Override
  public SvgDrawable getPreparedSvg(SvgDrawable svgDrawable, int variant, boolean isNightMode) {
    svgDrawable.requireObjectById("sand").isRotatable = true;
    svgDrawable.requireObjectById("kidney").isRotatable = true;
    svgDrawable.requireObjectById("star").isRotatable = true;
    return svgDrawable;
  }

  @NonNull
  @Override
  public WallpaperVariant[] getVariants() {
    return new WallpaperVariant[]{
        new WallpaperVariant(
            R.raw.wallpaper_stone,
            "#e6efd7",
            "#c4d6e4",
            "#b49e5f",
            new String[]{"#babba0", "#e4e6b4", "#a0a9b2"},
            true,
            false
        )
    };
  }

  @NonNull
  @Override
  public WallpaperVariant[] getDarkVariants() {
    return new WallpaperVariant[]{
        new WallpaperVariant(
            R.raw.wallpaper_stone_dark,
            "#515339",
            "#52482b",
            "#171815",
            new String[]{"#5e656c"},
            false,
            true
        )
    };
  }
}
