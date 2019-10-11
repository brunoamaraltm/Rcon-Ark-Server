package connection;

import java.io.IOException;

import net.kronos.rkon.core.Rcon;
import net.kronos.rkon.core.ex.AuthenticationException;

public class ServerConnection {
	private static Rcon rcon;

	public ServerConnection() {
		// TODO Auto-generated constructor stub
	}

	public static Rcon conectarRcon(String ip, int port, String senha) throws IOException, AuthenticationException {
		rcon = new Rcon(ip, port, senha.getBytes());
		return rcon;
	}
	public static void desconectarRcon(Rcon rcon) throws IOException, AuthenticationException {
		rcon.disconnect();
	}
}
