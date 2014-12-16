package com.shtrih.tinyjavapostester;

import com.shtrih.util.StaticContext;
import com.shtrih.util.SysUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import jpos.config.JposEntry;
import jpos.config.JposEntryRegistry;
import jpos.loader.JposServiceLoader;
import jpos.util.JposPropertiesConst;

public class JposConfig {

	public static void configure(String deviceName, String portName, String configFileName)
			throws Exception {

        final String activeConfigFileName = "jpos.xml";

        InputStream is = null;
        OutputStream os = null;
        try {
            is = StaticContext.getContext().getAssets().open(configFileName);
            os = new FileOutputStream(new File(SysUtils.getFilesPath() + activeConfigFileName));
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            if (is != null){
                is.close();
            }

            if (os != null){
                os.close();
            }
        }

		String fileURL = "file://" + SysUtils.getFilesPath() + activeConfigFileName;
		System.setProperty(
				JposPropertiesConst.JPOS_POPULATOR_FILE_URL_PROP_NAME, fileURL);

		System.setProperty(
				JposPropertiesConst.JPOS_REG_POPULATOR_CLASS_PROP_NAME,
				"jpos.config.simple.xml.SimpleXmlRegPopulator");

		JposEntryRegistry registry = JposServiceLoader.getManager()
				.getEntryRegistry();
		if (registry.hasJposEntry(deviceName)) {
			JposEntry jposEntry = registry.getJposEntry(deviceName);
			if (jposEntry != null) {
				jposEntry.addProperty("portName", portName);
				jposEntry.modifyPropertyValue("portName", portName);
			}
		}
	}
}
