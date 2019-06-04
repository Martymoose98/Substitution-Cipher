package com.encryption_engine;

import java.util.Scanner;

public class Main
{
	private static EncryptionEngine engine;
	private static Scanner scanner;

	public static void main(String[] args)
	{
		scanner = new Scanner(System.in);
		engine = new EncryptionEngine();
		System.out.println(engine.encrypt("BANANA & PEEL", "ACT"));
		System.out.println(engine.decrypt("BCGAPTPGXL", "ACT"));
		System.out.println(engine.encrypt("I LOVE PROGRAMMING!", "TRICKY"));

		String msg = null;
		String key = null;
		String mode = null;
		boolean bMode = false;

		do
		{
			do
			{
				System.out.println("Enter a message to decrypt/encrypt!");
				msg = scanner.nextLine();
			} while (msg == null);

			do
			{
				System.out.println("Enter a key!");
				key = scanner.nextLine();
			} while (key == null);

			do
			{
				System.out.println("Encrypt or Decrypt? (e/d)");
				mode = scanner.nextLine();
			} while (!mode.equals("e") && !mode.equals("E") && !mode.equals("d") && !mode.equals("D"));

			bMode = (mode.equals("e") || mode.equals("E")) ? true : false;

			System.out.println(bMode ? engine.encrypt(msg, key) : engine.decrypt(msg, key));
		} while (true);
	}
}
