package gui;

import java.awt.EventQueue;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;

import net.kronos.rkon.core.Rcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Home {

	public JFrame frame;
	private JTextField restartMessage;
	private JTextField chatLog;
	private JTable table;
	private JTextField commandText;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Home window = new Home();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Home() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 554, 369);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		restartMessage = new JTextField();
		restartMessage.setBounds(130, 11, 243, 20);
		frame.getContentPane().add(restartMessage);
		restartMessage.setColumns(10);
		String mensagemRestart = restartMessage.getText();
		
		JLabel lblRestartMessage = new JLabel("Restart Message:");
		lblRestartMessage.setBounds(10, 14, 110, 14);
		frame.getContentPane().add(lblRestartMessage);
		
		chatLog = new JTextField();
		chatLog.setBounds(10, 42, 405, 250);
		Login login = new Login();
		try {
			chatLog.setText(login.rcon.command("getchat"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		frame.getContentPane().add(chatLog);
		chatLog.setColumns(10);
		
		JSpinner restartTime = new JSpinner();
		restartTime.setBounds(372, 11, 43, 20);
		frame.getContentPane().add(restartTime);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(425, 42, 103, 250);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setColumnHeaderView(table);
		
		commandText = new JTextField();
		commandText.setBounds(126, 303, 289, 20);
		frame.getContentPane().add(commandText);
		commandText.setColumns(10);
		String comando = commandText.getText(); 
		
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setBounds(10, 303, 103, 20);
		comboBox.addItem("ServerChat");
		comboBox.addItem("Broadcast");
		comboBox.addItem("Saveworld");
		comboBox.addItem("Destroy Dinos");
		comboBox.addItem("BanPlayer");
		comboBox.addItem("KickPlayer");
		frame.getContentPane().add(comboBox);
		
		JButton btnSendCommand = new JButton("Enviar");
		btnSendCommand.setBounds(425, 302, 103, 23);
		frame.getContentPane().add(btnSendCommand);
		
		JButton btnRestart = new JButton("Restart");
		btnRestart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Thread.sleep((Integer) restartTime.getValue()*1000);
					System.out.println("OK");
				} catch (NumberFormatException | InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnRestart.setBounds(425, 10, 103, 23);
		frame.getContentPane().add(btnRestart);
	}
}
