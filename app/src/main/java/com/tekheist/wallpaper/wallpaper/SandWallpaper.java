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
import com.tekheist.wallpaper.drawable.SvgDrawable.SvgObject;

public class SandWallpaper extends BaseWallpaper {

  @NonNull
  @Override
  public String getName() {
    return WALLPAPER.SAND;
  }

  @Override
  public int getThumbnailResId() {
    return R.drawable.selection_sand;
  }

  @Override
  public SvgDrawable getPreparedSvg(SvgDrawable svgDrawable, int variant, boolean isNightMode) {
    SvgObject leaves = svgDrawable.requireObjectById("leaves");
    leaves.isRotatable = true;
    leaves.pivotOffsetX = 600;
    leaves.pivotOffsetY = 100;

    svgDrawable.requireObjectById("star").isRotatable = true;
    svgDrawable.requireObjectById("quad").isRotatable = true;
    return svgDrawable;
  }

  @NonNull
  @Override
  public WallpaperVariant[] getVariants() {
    return new WallpaperVariant[]{
        new WallpaperVariant(
            R.raw.wallpaper_sand,
            "#f4f5f4",
            "#8e8b89",
            "#ded7cc",
            new String[]{"#d4d1cb", "#ebeae3"},
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
            R.raw.wallpaper_sand_dark,
            "#212221",
            "#cccecd",
            "#545350",
            new String[]{},
            false,
            true
        )
    };
  }
}
