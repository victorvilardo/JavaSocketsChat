package ChatView;



import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
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

public class TelaServidor extends JFrame {

	private JPanel contentPane;
	private JLabel lblEntrada;
	private JTextField textEntrada;
	private JLabel lblSaida;
	private JTextArea textSaida;
	private JButton btnEnviar;
	
	Socket client;
	Scanner sc;
	PrintStream out;

	public TelaServidor() throws Exception {
		Handler ouvinte = new Handler();
		setTitle("CHAT SERVIDOR");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 366, 406);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblEntrada = new JLabel("entrada");
		lblEntrada.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblEntrada.setBounds(23, 199, 70, 52);
		contentPane.add(lblEntrada);
		
		textEntrada = new JTextField();
		textEntrada.setBounds(20, 241, 304, 36);
		contentPane.add(textEntrada);
		
		lblSaida = new JLabel("saida");
		lblSaida.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblSaida.setBounds(23, 11, 70, 22);
		contentPane.add(lblSaida);
		
		textSaida = new JTextArea();
		textSaida.setBounds(20, 37, 304, 177);
		contentPane.add(textSaida);
		
		btnEnviar = new JButton("ENVIAR");
		btnEnviar.setBounds(122, 308, 88, 36);
		btnEnviar.addActionListener(ouvinte);
		contentPane.add(btnEnviar);
		
		setVisible(true);	
		abreConexao();
	}
	
	public void limpar() {
		textEntrada.setText("");
	}
	/**
	 * metodo que ira estabelecer a conexao
	 * @throws Exception
	 */
	public void abreConexao() throws Exception {
		ServerSocket server = new ServerSocket(9000);
		System.out.println("Porta 9000 aberta, aguardando conexão com o cliente");
		
		Socket client = server.accept();
		System.out.println("Conexão com o cliente "+client.getInetAddress().getHostAddress());
		
		Scanner s = new Scanner(client.getInputStream());
		while(s.hasNextLine()) {
			textSaida.append("Cliente: " + s.nextLine() + "\n");
		}
		
		sc.close();
		client.close();
		server.close();
	}
	/**
	 *metodo que escuta a mensagem  
	 *
	 */
	
	public class Handler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {

			if(e.getSource()==btnEnviar) {

				String textE = textEntrada.getText();
				
				if(   (textE.equals(""))  ) {
					JOptionPane.showMessageDialog(getContentPane(), "Preencher todos os campos!!!!!!", "Atenção!",  1);
				}
				
				else {
					
					textSaida.append("Servidor: " + textE + "\n");
					
					try {	
						sc = new Scanner(textE);
						out = new PrintStream(client.getOutputStream());
									
						while(sc.hasNextLine()) {
							out.println(sc.nextLine());
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					
						limpar();
					}
				}
			}
		}
	}
}