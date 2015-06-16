package pl.pw.edu.fizyka.pojava.formatC.tranzystor.lang;

import java.util.Locale;
import java.util.ResourceBundle;

public class Localization 
{
	static ResourceBundle texts;
	public Localization(Locale currentLocale)
	{
		texts = ResourceBundle.getBundle("pl.pw.edu.fizyka.pojava.formatC.tranzystor.lang.Texts", currentLocale);
	}
	public static String getString(String title)
	{
		return texts.getString(title);
	}
}
