package BOUNDARY;

import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import CONTROL.analyzer;

public class serverFrame extends JFrame
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfFile;
	private JTextField tfArtista;
	private JTextField tfTitolo;
	private JTextField tfAnno;
	private JTextField tfYoutube;

	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args)
	{
		Server s = new Server ();
		s.start();

		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					serverFrame frame = new serverFrame();
					frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public serverFrame()
	{
		setTitle("SoundSMhoUnd SERVER");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnApri = new JButton("Sfoglia");
		btnApri.addMouseListener(new MouseAdapter() {
			
			@Override
			
			public void mouseClicked(MouseEvent arg0) 
			{
				//Create a file chooser
				final JFileChooser fc = new JFileChooser();
				
				//In response to a button click:
				int returnVal = fc.showOpenDialog(contentPane);
				
				if (returnVal == JFileChooser.APPROVE_OPTION)
				{
		            File file = fc.getSelectedFile();
		            tfFile.setText(file.getPath());
		        } 				
				
				
			}
		});
		
		btnApri.setBounds(335, 11, 89, 23);
		contentPane.add(btnApri);
		
		tfFile = new JTextField();
		tfFile.setBounds(32, 12, 293, 20);
		contentPane.add(tfFile);
		tfFile.setColumns(10);
		
		tfArtista = new JTextField();
		tfArtista.setText("Artista");
		tfArtista.setBounds(32, 50, 293, 20);
		contentPane.add(tfArtista);
		tfArtista.setColumns(10);
		
		tfTitolo = new JTextField();
		tfTitolo.setText("Titolo");
		tfTitolo.setBounds(32, 86, 293, 20);
		contentPane.add(tfTitolo);
		contentPane.add(tfTitolo);
		tfTitolo.setColumns(10);
		
		tfAnno = new JTextField();
		tfAnno.setText("2013");
		tfAnno.setBounds(32, 123, 86, 20);
		contentPane.add(tfAnno);
		tfAnno.setColumns(10);
		
		tfYoutube = new JTextField();
		tfYoutube.setText("Link Youtube");
		tfYoutube.setBounds(32, 157, 293, 20);
		contentPane.add(tfYoutube);
		tfYoutube.setColumns(10);
		
		JButton btnOk = new JButton("OK");
		btnOk.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent arg0) 
			{
				analyzer a = new analyzer ();
				
				a.analyze(tfFile.getText(), tfArtista.getText(), tfTitolo.getText(), Integer.parseInt(tfAnno.getText()), tfYoutube.getText());
				
			}
		});
		btnOk.setBounds(335, 212, 89, 23);
		contentPane.add(btnOk);
	}
}
