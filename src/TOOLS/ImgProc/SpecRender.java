package TOOLS.ImgProc;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class SpecRender 
{
	public  void render (int[][] mat , int X , int Y , String titolo)
	{
		final BufferedImage image = new BufferedImage (X , Y ,BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D)image.getGraphics();
		
		for (int y = 1 ; y < Y-1 ; y++)
		{
			for (int x = 1 ; x < X-1 ; x++)
			{			
				g.setColor(new Color (255-mat[x][y] , 255-mat[x][y] , 255-mat[x][y] ));
				g.fillRect(x, y, 1, 1);
			}
		}
		
		String path = "C:\\Users\\gennaro\\SoundSMhoUnd\\imgs\\";
		inFrame (image , X , Y , titolo);
		inFile (image , path + titolo + ".jpg");
	}

	public  void render (float[][] mat , int X , int Y , String titolo)
	{
		final BufferedImage image = new BufferedImage (X , Y ,BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D)image.getGraphics();
		
		float max = 0;

		for (int y = 0 ; y < Y ; y++)
		{
			for (int x = 0 ; x < X ; x++)
			{
				if (mat[x][y] > max)
				{
					max = mat[x][y];
				}
			}
		}
	
		for (int y = 0 ; y < Y ; y++)
		{
			for (int x = 0 ; x < X ; x++)
			{
				mat[x][y] /= max;
			}
		}

		
		for (int y = 1 ; y < Y-1 ; y++)
		{
			for (int x = 1 ; x < X-1 ; x++)
			{			
				g.setColor(new Color (1-mat[x][y] , 1-mat[x][y]  , 1-mat[x][y] ));
				g.fillRect(x, y, 1, 1);
			}
		}
		
		String path = "C:\\Users\\gennaro\\SoundSMhoUnd\\imgs\\";
		inFrame (image , X , Y , titolo);
		inFile (image , path + titolo + ".jpg");
	}
	
	public void render (boolean[][] mat , int X , int Y , String titolo)
	{
		final BufferedImage image = new BufferedImage (X , Y ,BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D)image.getGraphics();
		
		for (int y = 1 ; y < Y-1 ; y++)
		{
			for (int x = 1 ; x < X-1 ; x++)
			{		
				if (mat[x][y] == true)
				{
					g.setColor(new Color (255 , 255 , 255) );
					
				}
				else
				{
					g.setColor(new Color (0 , 0 , 0) );
				}
				g.fillRect(x, y, 1, 1);
			}
		}
		
		inFrame (image , X , Y , titolo);
		
	}
	
	
	private  void inFrame (final BufferedImage image , int X , int Y , String titolo)
	{

		
        JFrame frame = new JFrame(titolo);
        
        JPanel panel = new JPanel()
        {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D)g;
                g2d.clearRect(0, 0, getWidth(), getHeight());
                g2d.setRenderingHint(
                        RenderingHints.KEY_INTERPOLATION,
                        RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                        // Or _BICUBIC
                g2d.scale(2, 2);
                g2d.drawImage(image, 0, 0, this);
            }
        };
        
        panel.setPreferredSize(new Dimension(X*2, Y*2));
        frame.add(new JScrollPane(panel ,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);

	}
	
	public void inFile (BufferedImage img , String path)
	{
		File outputfile = new File(path);
		try
		{

			ImageIO.write(img, "jpg", outputfile);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
}
