package gui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

public class Home {

	public JFrame frame;
	private JTextField restartMessage;
	private JTextField chatLog;
	private JTable table;
	private JTextField commandText;
	private int contador;
	private JButton btnRestart = new JButton("Restart");
	private JScrollPane scrollPane = new JScrollPane();
	private JButton btnSendCommand = new JButton("Enviar");
	private JSpinner restartTime = new JSpinner();
	private JComboBox<String> comboBox = new JComboBox<String>();

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
		String token = "";
		DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();
		
		api.addMessageCreateListener(event -> {
			if (event.getMessageContent().equalsIgnoreCase("!ping")) {
				event.getChannel().sendMessage("Pong!");
			}
		});
		frame = new JFrame();
		frame.setBounds(100, 100, 554, 369);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize(new Dimension(554, 369));
		frame.getContentPane().setLayout(null);
		frame.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent arg0) {
				//table.setAlignmentX(frame.getWidth());
				scrollPane.setBounds(frame.getWidth()-130, 42, 105, frame.getHeight()-120);
				btnRestart.setBounds(frame.getWidth()-130, 10, 103, 23);
				btnSendCommand.setBounds(frame.getWidth()-130, frame.getHeight()-70, 103, 23);
				commandText.setBounds(126, frame.getHeight()-67, frame.getWidth()-265, 20);
				chatLog.setBounds(10, 42, frame.getWidth()-147, frame.getHeight()-120);
				restartTime.setBounds(frame.getWidth()-180, 11, 43, 20);
				restartMessage.setBounds(130, 11, frame.getWidth()-307, 20);
				comboBox.setBounds(10, frame.getHeight()-67, 103, 20);
			}
		});

		restartMessage = new JTextField();
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
		/*
		 * try { chatLog.setText(login.rcon.command("getchat")); } catch (IOException e)
		 * { // TODO Auto-generated catch block e.printStackTrace(); }
		 */
		frame.getContentPane().add(chatLog);
		chatLog.setColumns(10);

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

		frame.getContentPane().add(scrollPane);

		DefaultTableModel model = new DefaultTableModel() {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		table = new JTable(model);
		model.addColumn("Players");
		//String players = Login.rcon.command("listplayers");
		String players = "0. Escanor, 7676576756576576756\n1. sS<Salvatory>Ss, 76665465465644646546\n2. mancha negra BR, 766565465466465465";
		String[] playerSeparator = players.split("\n");
		for (String player : playerSeparator) {
			String playerName = player.split(",")[0];
			String firstChar = String.valueOf(playerName.charAt(0));
			String playerNames = playerName.replace(firstChar+". ", "");
			String steamID = player.split(",")[1].replace(" ", "");
			System.out.println(steamID);
			model.addRow(new Object[] {playerNames});
		}
		
		scrollPane.setColumnHeaderView(table);
		

		commandText = new JTextField();
		commandText.setBounds(126, 303, 289, 20);
		frame.getContentPane().add(commandText);
		commandText.setColumns(10);
		String comando = commandText.getText();
		
		frame.setLocationRelativeTo(null);

		comboBox.addItem("ServerChat");
		comboBox.addItem("Broadcast");
		comboBox.addItem("Saveworld");
		comboBox.addItem("Destroy Dinos");
		comboBox.addItem("BanPlayer");
		comboBox.addItem("KickPlayer");
		frame.getContentPane().add(comboBox);
		btnSendCommand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selected = comboBox.getSelectedIndex();
				try {
					if (selected == 0) {
						Login.rcon.command("ServerChat" + commandText.getText());
					}
					if (selected == 1) {
						Login.rcon.command("Broadcast" + commandText.getText());
					}
					if (selected == 2) {
						Login.rcon.command("SaveWorld");
					}
					if (selected == 3) {
						Login.rcon.command("DestroyWildDinos");
					}
				} catch (IOException e1) {
					
				}
				
			}
		});

		btnSendCommand.setBounds(425, 302, 103, 23);
		frame.getContentPane().add(btnSendCommand);

		btnRestart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int rightTime = (Integer) restartTime.getValue() + 1;
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
				try {
					btnRestart.setEnabled(false);
					btnRestart.setText("Left: " + (rightTime - contador));
					if (contador == rightTime) {
						Login.rcon.command("broadcast The server will restart now!");
						Login.rcon.command("SaveWorld");
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						Login.rcon.command("DoExit");
						System.out.println("The server will restart now!");
						btnRestart.setText("Restart");
						btnRestart.setEnabled(true);
						timer.cancel();
					} else {
						String result = Login.rcon.command("broadcast The server will restart in " + (rightTime - contador) + " minutes");
						System.out.println(result);
						System.out.println("The server will restart in " + (rightTime - contador) + " minutes");
					}
					contador++;
				} catch (IOException e) {
					System.out.println("O comando não foi executado");
				}
			}
		}, 0, 1 * 60000);
	}
}
