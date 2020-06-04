package com.mghimire.wallpaperchanger.tasks;

import com.mghimire.wallpaperchanger.model.Wallpaper;

public interface TaskCompleteCallback
{
    void onTaskComplete(Wallpaper wallpaper);
}
