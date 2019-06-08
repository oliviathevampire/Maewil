package coffeecatteam.theultimatetile.utils;

import org.joml.Vector4f;

public enum ChatColor
{
	WHITE('0', 1f, 1f, 1f, 1f),
	RED('1', 1f, 0f, 0f, 1f),
	GREEN('2', 0f, 1f, 0f, 1f),
	BLUE('3', 0f, 0f, 1f, 1f),
	YELLOW('4', 1f, 1f, 0f, 1f),
	CYAN('5', 0f, 1f, 1f, 1f),
	PURPLE('6', 1f, 0f, 1f, 1f),
	BLACK('7', 0f, 0f, 0f, 1f),
	ORANGE('8', 1f, 0.5f, 0f, 1f),
	GRAY('9', 0.5f, 0.5f, 0.5f, 1f),
	LIGHT_GRAY('a', 0.75f, 0.75f, 0.75f, 1f),
	DARK_GRAY('b', 0.25f, 0.25f, 0.25f, 1f),
	OLIVE('c', 0.5f, 0.5f, 0f, 1f),
	DARK_GREEN('d', 0f, 0.5f, 0f, 1f),
	DARK_RED('e', 0.5f, 0f, 0f, 1f),
	DARK_BLUE('f', 0f, 0f, 0.5f, 1f),
	TEAL('g', 0f, 0.5f, 0.5f, 1f),
	TRANSPARENT('h', 1f, 1f, 1f, 0.5f),
	INDIGO('i', 0.35f, 0f, 0.5f, 1f),
	BROWN('j', 0.7f, 0.25f, 0.25f, 1f),
	PINK('k', 1f, 0.85f, 0.9f, 1f),
	HOT_PINK('l', 1f, 0.45f, 0.75f, 1f);

	public static final char CHAT_COLOR_PREFIX = '¥';

	public static String fixColorWrapping(String text)
	{
		StringBuilder out = new StringBuilder(text.length());
		char[] c = text.toCharArray();
		ChatColor color = ChatColor.WHITE;
		boolean listenForColor = false;
		for (char element : c)
		{
			if (listenForColor)
			{
				listenForColor = false;
				color = getById(element);
				out.append(color);
				continue;
			}
			if (element == '\n')
			{
				out.append(element);
				out.append(color);
				continue;
			}
			if (element == CHAT_COLOR_PREFIX)
			{
				listenForColor = true;
				continue;
			}
			out.append(element);
		}
		return out.toString();
	}

	public static ChatColor getById(char id)
	{
		for (ChatColor cc : values())
			if (cc.id == id)
				return cc;
		return ChatColor.WHITE;
	}

	public static ChatColor getEndingChatColor(String text)
	{
		ChatColor color = ChatColor.WHITE;
		char[] chars = text.toCharArray();
		for (int i = 0; i < chars.length; i++)
		{
			if (chars[i] == CHAT_COLOR_PREFIX && i + 1 < chars.length)
				color = getById(chars[i + 1]);
		}

		return color;
	}

	public static String removeUnusedColors(String text)
	{
		StringBuilder out = new StringBuilder(text.length());
		boolean listenForColor = false;
		ChatColor color = ChatColor.WHITE;
		String sinceLast = "";
		for (char c : text.toCharArray())
		{
			if (listenForColor)
			{
				if (!sinceLast.isEmpty())
				{
					out.append(color);
					out.append(sinceLast);
					sinceLast = "";
				}
				listenForColor = false;
				color = getById(c);
				continue;
			}
			if (c == CHAT_COLOR_PREFIX)
			{
				listenForColor = true;
				continue;
			}
			sinceLast += c;
		}
		if (!sinceLast.isEmpty())
		{
			out.append(color);
			out.append(sinceLast);
		}
		return out.toString();
	}

	public static String replaceColorCharacter(String message, char oldPrefix, char newPrefix)
	{
		return message.replace(oldPrefix, newPrefix);
	}

	public static String stripColor(String message)
	{
		StringBuilder s = new StringBuilder();

		char[] chars = message.toCharArray();
		for (int i = 0; i < chars.length; i++)
		{
			if (chars[i] == CHAT_COLOR_PREFIX)
			{
				i++;
				continue;
			}
			s.append(chars[i]);
		}

		return s.toString();
	}

	private final Vector4f color;
	private final char id;

	private final String fullName;

	private ChatColor(char id, float r, float g, float b, float a)
	{
		color = new Vector4f(r, g, b, a);
		this.id = id;

		fullName = CHAT_COLOR_PREFIX + "" + id;
	}

	public float getAlpha()
	{
		return color.w;
	}

	public float getBlue()
	{
		return color.z;
	}

	public float getGreen()
	{
		return color.y;
	}

	public char getId()
	{
		return id;
	}

	public float getRed()
	{
		return color.x;
	}

	@Override
	public String toString()
	{
		return fullName;
	}
}