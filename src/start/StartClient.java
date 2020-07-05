package start;

import java.io.IOException;
import java.net.UnknownHostException;

import Interface.SignInterface;
import client.Client;

public class StartClient {
		public static void main(String[] args) throws UnknownHostException, IOException {
			Client client = new Client();
			client.setSign();
		}
}
