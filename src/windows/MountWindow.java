package src.windows;

import java.awt.Container;

import javax.swing.*;

public class MountWindow extends JFrame{
	
	private static final long serialVersionUID = 42;
	public Container mwFrame = this.getContentPane();
	public int position4[][];
	public int position31[][];
	public int position32[][];
	public int position21[][];
	public int position22[][];
	
	public JLabel lbPosition = new JLabel("Digite a Coluna e Linha (1 a 10), onde quer posicionar suas peças:");
	public JLabel lb1 = new JLabel("1:");
	public JLabel lb2 = new JLabel("2:");
	public JLabel lb3 = new JLabel("3:");
	public JLabel lb4 = new JLabel("4:");
	public JLabel lbRow = new JLabel("Linha");
	public JLabel lbColumn = new JLabel("Coluna");
	
	public JTextField tf1r = new JTextField();
	public JTextField tf2r = new JTextField();
	public JTextField tf3r = new JTextField();
	public JTextField tf4r = new JTextField();
	
	public JTextField tf1c = new JTextField();
	public JTextField tf2c = new JTextField();
	public JTextField tf3c = new JTextField();
	public JTextField tf4c = new JTextField();
	
	public JButton btOk;
	
	
	public MountWindow(JTable table){
		
		super("Montando Tabuleiro");
		
		mwFrame.setLayout(null);
		
		lbPosition.setBounds(30, 10, 500, 30);
		
		lb1.setBounds(70, 70, 50, 30);
		lb2.setBounds(70, 100, 50, 30);
		lb3.setBounds(70, 130, 50, 30);
		lb4.setBounds(70, 160, 50, 30);
		
		lbColumn.setBounds(115, 40, 50, 30);
		lbRow.setBounds(205, 40, 50, 30);
		
		tf1c.setBounds(110, 70, 50, 30);
		tf2c.setBounds(110, 100, 50, 30);
		tf3c.setBounds(110, 130, 50, 30);
		tf4c.setBounds(110, 160, 50, 30);
		
		tf1r.setBounds(200, 70, 50, 30);
		tf2r.setBounds(200, 100, 50, 30);
		tf3r.setBounds(200, 130, 50, 30);
		tf4r.setBounds(200, 160, 50, 30);
		
		mount2(position21);
		// Se position21 estiver preenchido, vai pro próximo, senão volta pro 21
			mount2(position22);
			// Se position22 estiver preenchido, vai pro próximo, senão volta pro 22
				mount3(position31);
				// Se position31 estiver preenchido, vai pro próximo, senão volta pro 31	
					mount3(position32);
					// Se position32 estiver preenchido, vai pro próximo, senão volta pro 32
						mount4(position4);
						// Se position4 estiver preenchido, vai pro próximo, senão volta pro 4	
		
	}
	
	public void mount4(int position[][]){
		
		mwFrame.add(lbPosition);
		
		mwFrame.add(lb1);
		mwFrame.add(lbRow);
		mwFrame.add(lbColumn);
		mwFrame.add(tf1r);
		mwFrame.add(tf1c);
		
		mwFrame.add(lb2);
		mwFrame.add(lbRow);
		mwFrame.add(lbColumn);
		mwFrame.add(tf2r);
		mwFrame.add(tf2c);
		
		mwFrame.add(lb3);
		mwFrame.add(lbRow);
		mwFrame.add(lbColumn);
		mwFrame.add(tf3r);
		mwFrame.add(tf3c);
		
		mwFrame.add(lb4);
		mwFrame.add(lbRow);
		mwFrame.add(lbColumn);
		mwFrame.add(tf4r);
		mwFrame.add(tf4c);
		
		// Configura detalhes do Frame
		// 1. Encerrar Applicação ao Fechar
		// 2. Setar as dimensões do Frame
		// 3. Setar Frame como Visível
		this.setSize(400, 600);
		this.setVisible(true);
	}
	
	public void mount3(int position[][]){
		
mwFrame.add(lbPosition);
		
		mwFrame.add(lb1);
		mwFrame.add(lbRow);
		mwFrame.add(lbColumn);
		mwFrame.add(tf1r);
		mwFrame.add(tf1c);
		
		mwFrame.add(lb2);
		mwFrame.add(lbRow);
		mwFrame.add(lbColumn);
		mwFrame.add(tf2r);
		mwFrame.add(tf2c);
		
		mwFrame.add(lb3);
		mwFrame.add(lbRow);
		mwFrame.add(lbColumn);
		mwFrame.add(tf3r);
		mwFrame.add(tf3c);
				
		// Configura detalhes do Frame
		// 1. Encerrar Applicação ao Fechar
		// 2. Setar as dimensões do Frame
		// 3. Setar Frame como Visível
		this.setSize(400, 600);
		this.setVisible(true);
	}
	
	public void mount2(int position[][]){
		
		mwFrame.add(lbPosition);
		
		mwFrame.add(lb1);
		mwFrame.add(lbRow);
		mwFrame.add(lbColumn);
		mwFrame.add(tf1r);
		mwFrame.add(tf1c);
		
		mwFrame.add(lb2);
		mwFrame.add(lbRow);
		mwFrame.add(lbColumn);
		mwFrame.add(tf2r);
		mwFrame.add(tf2c);
				
		// Configura detalhes do Frame
		// 1. Encerrar Applicação ao Fechar
		// 2. Setar as dimensões do Frame
		// 3. Setar Frame como Visível
		this.setSize(400, 600);
		this.setVisible(true);
	}
}
