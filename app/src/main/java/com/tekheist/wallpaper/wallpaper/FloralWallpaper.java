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

public class FloralWallpaper extends BaseWallpaper {

  @NonNull
  @Override
  public String getName() {
    return WALLPAPER.FLORAL;
  }

  @Override
  public int getThumbnailResId() {
    return R.drawable.selection_floral;
  }

  @Override
  public SvgDrawable getPreparedSvg(SvgDrawable svgDrawable, int variant, boolean isNightMode) {
    svgDrawable.requireObjectById("circle").isRotatable = true;
    svgDrawable.requireObjectById("quad_top").isRotatable = true;
    svgDrawable.requireObjectById("quad_bottom").isRotatable = true;
    return svgDrawable;
  }

  @NonNull
  @Override
  public WallpaperVariant[] getVariants() {
    return new WallpaperVariant[]{
        new WallpaperVariant(
            R.raw.wallpaper_floral,
            "#fce2e0",
            "#e4254d",
            "#ffce3a",
            new String[]{
                "#f69c95", "#f4453e", "#ff7c2d", "#ffdf3c", "#dad963", "#aaa025", "#2a0f10"
            },
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
            R.raw.wallpaper_floral_dark,
            "#222020",
            "#a6262f",
            "#b4942a",
            new String[]{"#b4a985", "#483838", "#b3b3b3", "#938c30"},
            false,
            true
        )
    };
  }
}
