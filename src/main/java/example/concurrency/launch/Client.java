package example.concurrency.launch;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client {

	private static final String ADDRESS = "10.1.1.109";//"10.1.1.109";
	private static final String EXIT = "exit";

	public static void main(String[] args) {
		Socket connection = null;
		final BufferedReader input;
		final BufferedWriter output;
		final BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
		
		try {

			connection = new Socket(ADDRESS, Server.PORT);
			input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			output = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));

			output.write("Hello there friend!\n");
			output.flush();

			while (!connection.isClosed()) {
				
				System.out.println("Server responded --> " + input.readLine());

				System.out.println("Send A Message: ");

				String message = consoleInput.readLine();
				
				if(!EXIT.equals(message)) {
					System.out.println("Sending message");
					output.write(message + "\n");
					output.flush();
				}
				else
					output.write("Goodbye!");
					output.flush();
					output.close();
			}

		} catch (Exception e) {
			
			e.printStackTrace();
			
		} finally {
			
			try {
				connection.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}

}
