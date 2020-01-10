package io.github.vampirestudios.tdg.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils
{
	public static String arrayToDeepString(Object[] data, int maxSize)
	{
		StringBuilder sb = new StringBuilder();

		sb.append('[');
		for (int i = 0; i < Math.min(maxSize, data.length); i++)
		{
			if (i > 0)
				sb.append(", ");
			if (data[i] instanceof Object[])
				sb.append(arrayToDeepString((Object[]) data[i], maxSize));
			else if (data[i] instanceof boolean[])
				sb.append(arrayToString((boolean[]) data[i], maxSize));
			else if (data[i] instanceof byte[])
				sb.append(arrayToString((byte[]) data[i], maxSize));
			else if (data[i] instanceof short[])
				sb.append(arrayToString((short[]) data[i], maxSize));
			else if (data[i] instanceof int[])
				sb.append(arrayToString((int[]) data[i], maxSize));
			else if (data[i] instanceof long[])
				sb.append(arrayToString((long[]) data[i], maxSize));
			else if (data[i] instanceof float[])
				sb.append(arrayToString((float[]) data[i], maxSize));
			else if (data[i] instanceof double[])
				sb.append(arrayToString((double[]) data[i], maxSize));
			else
				sb.append(data[i]);
		}

		if (data.length > maxSize)
			sb.append(", ..." + (data.length - maxSize) + " more");
		sb.append(']');

		return sb.toString();
	}

	public static String arrayToString(boolean[] array, int maxSize)
	{
		StringBuilder sb = new StringBuilder();

		sb.append('[');
		for (int i = 0; i < Math.min(maxSize, array.length); i++)
		{
			if (i > 0)
				sb.append(", ");
			sb.append(array[i]);
		}

		if (array.length > maxSize)
			sb.append(", ..." + (array.length - maxSize) + " more");
		sb.append(']');

		return sb.toString();
	}

	public static String arrayToString(byte[] array, int maxSize)
	{
		StringBuilder sb = new StringBuilder();

		sb.append('[');
		for (int i = 0; i < Math.min(maxSize, array.length); i++)
		{
			if (i > 0)
				sb.append(", ");
			sb.append(array[i]);
		}

		if (array.length > maxSize)
			sb.append(", ..." + (array.length - maxSize) + " more");
		sb.append(']');

		return sb.toString();
	}

	public static String arrayToString(double[] array, int maxSize)
	{
		StringBuilder sb = new StringBuilder();

		sb.append('[');
		for (int i = 0; i < Math.min(maxSize, array.length); i++)
		{
			if (i > 0)
				sb.append(", ");
			sb.append(array[i]);
		}

		if (array.length > maxSize)
			sb.append(", ..." + (array.length - maxSize) + " more");
		sb.append(']');

		return sb.toString();
	}

	public static String arrayToString(float[] array, int maxSize)
	{
		StringBuilder sb = new StringBuilder();

		sb.append('[');
		for (int i = 0; i < Math.min(maxSize, array.length); i++)
		{
			if (i > 0)
				sb.append(", ");
			sb.append(array[i]);
		}

		if (array.length > maxSize)
			sb.append(", ..." + (array.length - maxSize) + " more");
		sb.append(']');

		return sb.toString();
	}

	public static String arrayToString(int[] array, int maxSize)
	{
		StringBuilder sb = new StringBuilder();

		sb.append('[');
		for (int i = 0; i < Math.min(maxSize, array.length); i++)
		{
			if (i > 0)
				sb.append(", ");
			sb.append(array[i]);
		}

		if (array.length > maxSize)
			sb.append(", ..." + (array.length - maxSize) + " more");
		sb.append(']');

		return sb.toString();
	}

	public static String arrayToString(long[] array, int maxSize)
	{
		StringBuilder sb = new StringBuilder();

		sb.append('[');
		for (int i = 0; i < Math.min(maxSize, array.length); i++)
		{
			if (i > 0)
				sb.append(", ");
			sb.append(array[i]);
		}

		if (array.length > maxSize)
			sb.append(", ..." + (array.length - maxSize) + " more");
		sb.append(']');

		return sb.toString();
	}

	public static String arrayToString(short[] array, int maxSize)
	{
		StringBuilder sb = new StringBuilder();

		sb.append('[');
		for (int i = 0; i < Math.min(maxSize, array.length); i++)
		{
			if (i > 0)
				sb.append(", ");
			sb.append(array[i]);
		}

		if (array.length > maxSize)
			sb.append(", ..." + (array.length - maxSize) + " more");
		sb.append(']');

		return sb.toString();
	}

	public static String fileDateTimestamp()
	{
		return new SimpleDateFormat("yyyy_MM_dd HH_mm_ss").format(new Date());
	}
}