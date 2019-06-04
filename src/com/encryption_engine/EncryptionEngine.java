package com.encryption_engine;

public class EncryptionEngine
{
	private char[][] grid;
	private String key;

	public EncryptionEngine()
	{
	}

	public EncryptionEngine(String key)
	{
		this.key = key.trim().toUpperCase();
	}

	public EncryptionEngine(String message, String key)
	{
		this.key = key.trim().toUpperCase();
		message = encrypt(message);
	}

	public String encrypt(String message)
	{
		message = message.trim().toUpperCase();
		String out = "";

		for (int index = 0; index < key.length();)
		{
			if (!isAlpha(this.key.charAt(index)))
			{
				this.key = this.key.substring(0, index).concat(this.key.substring(index + 1, this.key.length()));
				continue;
			}
			++index;
		}

		for (int index = 0; index < message.length();)
		{
			if (!isAlpha(message.charAt(index)))
			{
				message = message.substring(0, index).concat(message.substring(index + 1, message.length()));
				continue;
			}
			++index;
		}

		this.grid = new char[(message.length() == key.length()) ? message.length() : ((int) Math.ceil((float) message.length() / (float) this.key.length())) + (int) Math.ceil((float) (message.length() % this.key.length()) / (float) this.key.length())][this.key.length()];

		for (int index = 0; index < this.key.length(); ++index)
			this.grid[0][index] = this.key.charAt(index);

		for (int index = 0; index < message.length(); ++index)
			this.grid[index / this.grid[index % this.grid.length].length % this.grid.length + 1][index % this.grid[index % this.grid.length].length] = message.charAt(index);

		for (int index = 0; index < message.length(); ++index)
			out += transpose(this.grid[0][index % this.grid[index % this.grid.length].length], this.grid[index / this.grid[index % this.grid.length].length % this.grid.length + 1][index % this.grid[index % this.grid.length].length]);

		return out;
	}

	public String encrypt(String message, String key)
	{
		key = key.trim().toUpperCase();
		message = message.trim().toUpperCase();
		String out = "";

		message = filterInput(message);
		key = filterInput(key);

		char[][] grid = new char[(message.length() == key.length()) ? message.length() : ((int) Math.ceil((float) message.length() / (float) key.length())) + (int) Math.ceil((float) (message.length() % key.length()) / (float) key.length())][key.length()];

		for (int index = 0; index < key.length(); ++index)
			grid[0][index] = key.charAt(index);

		for (int index = 0; index < message.length(); ++index)
			grid[index / grid[index % grid.length].length % grid.length + 1][index % grid[index % grid.length].length] = message.charAt(index);

		for (int index = 0; index < message.length(); ++index)
			out += transpose(grid[0][index % grid[index % grid.length].length], grid[index / grid[index % grid.length].length % grid.length + 1][index % grid[index % grid.length].length]);

		return out;
	}

	public String decrypt(String message, String key)
	{
		key = key.trim().toUpperCase();
		message = message.trim().toUpperCase();
		String out = "";

		for (int index = 0; index < key.length();)
		{
			if (!isAlpha(key.charAt(index)))
			{
				key = key.substring(0, index).concat(key.substring(index + 1, key.length()));
				continue;
			}
			++index;
		}

		for (int index = 0; index < message.length();)
		{
			if (!isAlpha(message.charAt(index)))
			{
				message = message.substring(0, index).concat(message.substring(index + 1, message.length()));
				continue;
			}
			++index;
		}

		char[][] grid = new char[(message.length() == key.length()) ? message.length() : ((int) Math.ceil((float) message.length() / (float) key.length())) + (int) Math.ceil((float) (message.length() % key.length()) / (float) key.length())][key.length()];

		for (int index = 0; index < key.length(); ++index)
			grid[0][index] = key.charAt(index);

		for (int index = 0; index < message.length(); ++index)
			grid[index / grid[index % grid.length].length % grid.length + 1][index % grid[index % grid.length].length] = message.charAt(index);

		for (int index = 0; index < message.length(); ++index)
			out += untranspose(grid[0][index % grid[index % grid.length].length], grid[index / grid[index % grid.length].length % grid.length + 1][index % grid[index % grid.length].length]);

		return out;
	}

	private void printArray()
	{
		for (char[] c : this.grid)
		{
			for (char cc : c)
				System.out.print(cc + " ");

			System.out.println();
		}
		System.out.println();
	}

	private void printArray(char[][] grid)
	{
		for (char[] c : grid)
		{
			for (char cc : c)
				System.out.print(cc + " ");

			System.out.println();
		}
		System.out.println();
	}

	private String filterInput(String in)
	{
		for (int index = 0; index < in.length();)
		{
			if (!isAlpha(in.charAt(index)))
			{
				in = in.substring(0, index).concat(in.substring(index + 1, in.length()));
				continue;
			}
			++index;
		}
		return in;
	}

	private char transpose(char keych, char c)
	{
		short nShift = (short) ((byte) keych - 0x41);
		return shift(c, nShift);
	}

	private char untranspose(char keych, char c)
	{
		short nShift = (short) (0x41 - (byte) keych);
		return unshift(c, nShift);
	}

	private char shift(char c, short n)
	{
		return (char) ((c + n > 0x5A) ? c - (0x1A - n) : c + n);
	}

	private char unshift(char c, short n)
	{
		return (char) ((c + n < 0x41) ? c + (0x1A + n) : c + n);
	}

	private byte rotl(byte b, short n)
	{
		return (byte) ((b >> n) | (b << (8 - n)));
	}

	private byte rotr(byte b, short n)
	{
		return (byte) ((b << n) | (b >> (8 - n)));
	}

	private boolean isAlpha(char c)
	{
		return !((byte) c < 0x41 || (byte) c > 0x7A || ((byte) c > 0x5A && (byte) c < 0x61));
	}
}
