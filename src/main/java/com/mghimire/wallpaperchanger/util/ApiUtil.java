package com.mghimire.wallpaperchanger.util;

public class ApiUtil
{
    private static final String API_KEY_TAG = "UNSPLASH_API_KEY";

    private ApiUtil() {}
    public static String getApiKey()
    {
        try
        {
            String apiKey = System.getenv(API_KEY_TAG);
            if (apiKey == null) throw new IllegalArgumentException("Unsplash api key not found in the "
                    + "environment variable UNSPLASH_API_KEY");
            return apiKey;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            throw new RuntimeException("Could not get api key", ex);
        }
    }
}
