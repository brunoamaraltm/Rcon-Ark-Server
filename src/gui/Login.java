package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import connection.ServerConnection;
import net.kronos.rkon.core.Rcon;
import net.kronos.rkon.core.ex.AuthenticationException;

public class Login {

	private JFrame frmRconLogin;
	private JTextField ipText;
	private JTextField rconPortText;
	private JTextField passwordText;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frmRconLogin.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmRconLogin = new JFrame();
		frmRconLogin.setTitle("Rcon Login");
		frmRconLogin.setBounds(100, 100, 272, 185);
		frmRconLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmRconLogin.getContentPane().setLayout(null);
		
		ipText = new JTextField();
		ipText.setBounds(82, 11, 131, 20);
		frmRconLogin.getContentPane().add(ipText);
		ipText.setColumns(10);
		
		rconPortText = new JTextField();
		rconPortText.setBounds(82, 42, 131, 20);
		frmRconLogin.getContentPane().add(rconPortText);
		rconPortText.setColumns(10);
		
		passwordText = new JTextField();
		passwordText.setBounds(82, 73, 131, 20);
		frmRconLogin.getContentPane().add(passwordText);
		passwordText.setColumns(10);
		
		JLabel lblIp = new JLabel("IP:");
		lblIp.setBounds(10, 14, 72, 14);
		frmRconLogin.getContentPane().add(lblIp);
		
		JLabel lblRconPort = new JLabel("Rcon Port:");
		lblRconPort.setBounds(10, 45, 72, 14);
		frmRconLogin.getContentPane().add(lblRconPort);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(10, 76, 72, 14);
		frmRconLogin.getContentPane().add(lblPassword);
		
		JButton btnNewButton = new JButton("Entrar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ip = ipText.getText();
				String port = rconPortText.getText();
				String password = passwordText.getText();
				if (ip.isEmpty() || port.isEmpty() || password.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Preencha todos os campos", "Erro", JOptionPane.ERROR_MESSAGE);
				} else {
					try {
						Rcon rcon = ServerConnection.conectarRcon(ip, Integer.parseInt(port), password);
						
					} catch (NumberFormatException | IOException | AuthenticationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
			}
		});
		btnNewButton.setBounds(10, 106, 237, 34);
		frmRconLogin.getContentPane().add(btnNewButton);
	}
}
