package com.mghimire.wallpaperchanger.command;

import com.mghimire.wallpaperchanger.model.Wallpaper;
import com.mghimire.wallpaperchanger.util.ProcessUtil;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public final class WinWallpaperSetCommand implements Command
{
    private static final String WALL_CH_COMMAND
            = "reg add \"HKEY_CURRENT_USER\\Control Panel\\Desktop\" /v Wallpaper /t REG_SZ /d %s /f";
    private static final String WALL_EX_COMMAND = "RUNDLL32.EXE user32.dll UpdatePerUserSystemParameters";

    private static final long PROCESS_WAIT_TIME_IN_SECONDS = 8;

    private final Downloader downloader;

    public WinWallpaperSetCommand(Downloader downloader)
    {
        this.downloader = downloader;
    }

    @Override
    public void execute()
    {
        setWallpaper();
    }

    private void setWallpaper()
    {
        // execute set wallpaper command
        Wallpaper wallpaper = downloader.download();
        setWallpaper(wallpaper);
    }

    private void setWallpaper(Wallpaper wallpaper)
    {
        try
        {
            File absFilePath = wallpaper.getLocalFile();

            executeWallChCommand(absFilePath);
            executeWallExCommand();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private void executeWallExCommand() throws IOException
    {
        String[] chCommand = {"cmd", "/c", WALL_EX_COMMAND};
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(chCommand);
        Process process = processBuilder.start();
        ProcessUtil.waitForProcess(process, PROCESS_WAIT_TIME_IN_SECONDS);
    }

    private void executeWallChCommand(File absFilePath) throws IOException
    {
        String[] chCommand = {"cmd", "/c", String.format(WALL_CH_COMMAND, absFilePath)};
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(chCommand);
        Process process = processBuilder.start();
        ProcessUtil.waitForProcess(process, PROCESS_WAIT_TIME_IN_SECONDS);
    }

}
