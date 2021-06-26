package ChatView;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class TelaCliente extends JFrame {

	private JPanel contentPane;
	private JLabel lblChat;
	private JTextField textEntrada;
	private JLabel lblSaida;
	private JTextArea textSaida;
	private JButton btnEnviar;
	
	Socket client;
	Scanner sc;
	PrintStream out;

	public TelaCliente() throws Exception {
		Handler ouvinte = new Handler();
		setTitle("CHAT CLIENTE");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 366, 395);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblChat = new JLabel("chat");
		lblChat.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblChat.setBounds(23, 199, 70, 52);
		contentPane.add(lblChat);
		
		textEntrada = new JTextField();
		textEntrada.setBounds(20, 241, 304, 36);
		contentPane.add(textEntrada);
		
		lblSaida = new JLabel("Mensagem");
		lblSaida.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblSaida.setBounds(23, 11, 106, 22);
		contentPane.add(lblSaida);
		
		textSaida = new JTextArea();
		textSaida.setBounds(20, 37, 304, 177);
		contentPane.add(textSaida);
		
		btnEnviar = new JButton("ENVIAR");
		btnEnviar.setBounds(118, 311, 89, 34);
		btnEnviar.addActionListener(ouvinte);
		contentPane.add(btnEnviar);
		
		setVisible(true);
		abreConexao();
	}
	
	public void limpar() {
		textEntrada.setText("");
	}
	
	 public void abreConexao() throws Exception {
	 	client = new Socket("127.0.0.1",9000);
		System.out.println("Cliente conectado ao servidor");
		
		try {
			out.close();
			sc.close();	
			client.close();
		}catch(NullPointerException e) {
			
		}
	 }
		
	public class Handler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

			if(e.getSource()==btnEnviar) {
				String textE = textEntrada.getText();
				
				if(   (textE.equals(""))  ) {
					JOptionPane.showMessageDialog(getContentPane(), "Preencher todos os campos!!!!!!", "Atenção!",  1);
				} else {

					textSaida.append("Cliente: " + textE + "\n");	
					
					try {	
						sc = new Scanner(textE);
						out = new PrintStream(client.getOutputStream());
									
						while(sc.hasNextLine()) {
							out.println(sc.nextLine());
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
						
					limpar();
				}

			}

		}
		
	}

}