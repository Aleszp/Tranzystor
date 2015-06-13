package pl.pw.edu.fizyka.pojava.formatC.tranzystor;

import java.util.Locale;
import java.util.ResourceBundle;

public class Localization 
{
	static ResourceBundle texts;
	static String version;
	public Localization(Locale currentLocale)
	{
		texts = ResourceBundle.getBundle("Lang.Labels", currentLocale);
		version = "Alfa 1.3";
	}
	public static String getString(String title)
	{
		return texts.getString(title);
	}
}
