package com.shtrih.util;

import android.content.Context;

import java.io.File;

public class SysUtils {
	public static String getFilesPath() throws Exception {
		Context context = StaticContext.getContext();
		return context.getFilesDir().getAbsolutePath() + File.separator;
	}

}
