package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Home {

	public JFrame frame;
	private JTextField restartMessage;
	private JTextField chatLog;
	private JTable table;
	private JTextField commandText;
	private int contador;
	private JButton btnRestart = new JButton("Restart");

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
		restartMessage.setText("The server will restart in %s minutes");
		String mensagemRestart = restartMessage.getText();
		
		
		JLabel lblRestartMessage = new JLabel("Restart Message:");
		lblRestartMessage.setBounds(10, 14, 110, 14);
		frame.getContentPane().add(lblRestartMessage);
		
		chatLog = new JTextField();
		chatLog.setBounds(10, 42, 405, 250);
		Login login = new Login();
		/*try {
			chatLog.setText(login.rcon.command("getchat"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		frame.getContentPane().add(chatLog);
		chatLog.setColumns(10);
		
		JSpinner restartTime = new JSpinner();
		restartTime.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				String replaced = mensagemRestart.replace("%s", restartTime.getValue().toString());
				restartMessage.setText(replaced);
			}
		});
		
		String replaced = mensagemRestart.replace("%s", restartTime.getValue().toString());
		restartMessage.setText(replaced);
		
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
		
		btnRestart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int rightTime = (Integer) restartTime.getValue()+1;
				try {
					useTime(rightTime);
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnRestart.setBounds(425, 10, 103, 23);
		frame.getContentPane().add(btnRestart);
	}
	private void useTime(int rightTime) {
		Timer timer = new Timer();
		contador = 1;
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				btnRestart.setEnabled(false);
				btnRestart.setText("Left: "+(rightTime - contador));
				if (contador == rightTime) {
					System.out.println("The server will restart now");
					btnRestart.setText("Restart");
					btnRestart.setEnabled(true);
					timer.cancel();
				} else {
					System.out.println("The server will restart in " + (rightTime - contador) + " minutes");
				}
				contador++;
			}
		}, 0, 1*1000);
	}
}
