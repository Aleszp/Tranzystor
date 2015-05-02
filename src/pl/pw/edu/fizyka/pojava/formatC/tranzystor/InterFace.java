package pl.pw.edu.fizyka.pojava.formatC.tranzystor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.HeadlessException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class InterFace extends JFrame 
{
	private static final long serialVersionUID = -8683962114940324116L;

	public InterFace() throws HeadlessException 
	{
		super("symulacja tranzystora");
		setSize(640,720);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLayout(new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS));
		
        Dimension sizew = new Dimension(220, 200);		//rozmiarow preferonch wykresow
        Color kolorramek=Color.blue; 					//zmienna koloru ramek
		
		String[] U ={"Uce" ,"Ube" };
		String[] I ={"Ib","Ie","Ic"};

		String[] Uj ={"V" ,"mV"};
		String[] Ij ={"A","mA"};
		
		/*********************************************/

		JPanel wykres1 =new JPanel();					// lewegy panel wykresu
		wykres1.setPreferredSize(sizew);
		wykres1.setBorder(new LineBorder(kolorramek));
		
		JPanel wykres2 =new JPanel();					// prawegy panel wykresu
		wykres2.setPreferredSize(sizew);
		wykres2.setBorder(new LineBorder(kolorramek));
		
		JPanel wykresy =new JPanel();					//elnent okreslajcy polozenie wykrsow
		wykresy.add(wykres1);
		wykresy.add(wykres2);
		
		/*********************************************/	//elemty panelu nastawów lewego wykresu
		
		JComboBox OX1 = new JComboBox(U);
		JPanel OX1p =new JPanel();
		OX1p.add(new JLabel("OX"));
		OX1p.add(OX1);
		
		JComboBox OY1 = new JComboBox(I);
		JPanel OY1p =new JPanel();
		OY1p.add(new JLabel("OY"));
		OY1p.add(OY1);
		
		JLabel par1 =new JLabel(U[1]);					//elmety w wersji koñcowje bêd¹ modyfkowane przez Listener
		JLabel par1w =new JLabel("");
		JLabel par1j =new JLabel(Uj[0]);
		JPanel par1p =new JPanel();
		par1p.add(par1);
		par1p.add(par1w);
		par1p.add(par1j);	
		
		
		JPanel w1nast =new JPanel();					// panel nastawów lewego wykresu
		w1nast.setBorder(new LineBorder(kolorramek));
		w1nast.setLayout(new BoxLayout(w1nast,BoxLayout.Y_AXIS));
		w1nast.add(new JLabel("Wykres1",JLabel.CENTER));
		w1nast.add(OX1p);
		w1nast.add(OY1p);
		w1nast.add(par1p);
		
		/********************/							//elemty panelu nastawów prawego wykresu
		
		JComboBox OX2 = new JComboBox(U);
		JPanel OX2p =new JPanel();
		OX2p.add(new JLabel("OX"));
		OX2p.add(OX2);
		
		JComboBox OY2 = new JComboBox(I);
		JPanel OY2p =new JPanel();
		OY2p.add(new JLabel("OY"));
		OY2p.add(OY2);
		
		JLabel par2 =new JLabel(U[1]);					//elmety w wersji koñcowje bêd¹ modyfkowane przez Listener
		JLabel par2w =new JLabel("");
		JLabel par2j =new JLabel(Uj[0]);
		JPanel par2p =new JPanel();
		par2p.add(par2);
		par2p.add(par2w);
		par2p.add(par2j);
		
		
		JPanel w2nast =new JPanel();					// panel nastawów prawego wykresu
		w2nast.setBorder(new LineBorder(kolorramek));
		w2nast.setLayout(new BoxLayout(w2nast,BoxLayout.Y_AXIS));
		w2nast.add(new JLabel("Wykres2",JLabel.CENTER));
		w2nast.add(OX2p);
		w2nast.add(OY2p);
		w2nast.add(par2p);
		
		/********************/
		
		JPanel wnast =new JPanel();						//elnent okreslajcy polozenie nastawow wykrsow
		wnast.add(w1nast);
		wnast.add(w2nast);
		
		/*********************************************/
		
		JTextField PU= new JTextField(3);
		JComboBox PUj = new JComboBox(Uj);
		JPanel PUp =new JPanel();
		PUp.add(new JLabel("Wartość początkowa"));
		PUp.add(PU);
		PUp.add(PUj);

		JTextField Uk= new JTextField(3);
		JPanel Ukp =new JPanel();
		Ukp.add(new JLabel("Liczba kroków"));
		Ukp.add(Uk);
		
		JTextField KU= new JTextField(3);
		JComboBox KUj = new JComboBox(Uj);
		JPanel KUp =new JPanel();
		KUp.add(new JLabel("Wartość końcowa"));
		KUp.add(KU);
		KUp.add(KUj);
		
		JPanel ust1wp =new JPanel();	
		JComboBox ust1w = new JComboBox(U);
		ust1wp.add(ust1w);
		
		JPanel ust1 =new JPanel();					//lewy panel ustawień
		ust1.setBorder(new LineBorder(kolorramek));
		ust1.setLayout(new BoxLayout(ust1,BoxLayout.Y_AXIS));
		ust1.add(ust1wp);
		ust1.add(PUp);
		ust1.add(Ukp);
		ust1.add(KUp);

		
		/********************/
		
		JTextField Ucem= new JTextField(3);
		JComboBox Ucemj = new JComboBox(Uj);
		JPanel Ucemp =new JPanel();
		Ucemp.add(new JLabel("Ucemax"));
		Ucemp.add(Ucem);
		Ucemp.add(Ucemj);

		JTextField Ubem= new JTextField(3);
		JComboBox Ubemj = new JComboBox(Uj);
		JPanel Ubemp =new JPanel();
		Ubemp.add(new JLabel("Ubemax"));
		Ubemp.add(Ubem);
		Ubemp.add(Ubemj);

		JTextField Ucbm= new JTextField(3);
		JComboBox Ucbmj = new JComboBox(Uj);
		JPanel Ucbmp =new JPanel();
		Ucbmp.add(new JLabel("Ucbmax"));
		Ucbmp.add(Ucbm);
		Ucbmp.add(Ucbmj);
		
		JTextField Icm= new JTextField(3);
		JComboBox Icmj = new JComboBox(Ij);
		JPanel Icmp =new JPanel();
		Icmp.add(new JLabel("Icmax"));
		Icmp.add(Icm);
		Icmp.add(Icmj);
		
		JTextField Ibm= new JTextField(3);
		JComboBox Ibmj = new JComboBox(Ij);
		JPanel Ibmp =new JPanel();
		Ibmp.add(new JLabel("Ibmax"));
		Ibmp.add(Ibm);
		Ibmp.add(Ibmj);
		
		JTextField Iem= new JTextField(3);
		JComboBox Iemj = new JComboBox(Ij);
		JPanel Iemp =new JPanel();
		Iemp.add(new JLabel("Icmax"));
		Iemp.add(Iem);
		Iemp.add(Iemj);
		
		JPanel ust2 =new JPanel();					//prawegy panel ustawien
		ust2.setBorder(new LineBorder(kolorramek));
		ust2.setLayout(new BoxLayout(ust2,BoxLayout.Y_AXIS));
		ust2.add(Ucemp);
		ust2.add(Ubemp);
		ust2.add(Ucbmp);
		ust2.add(Icmp);
		ust2.add(Ibmp);
		ust2.add(Iemp);
		
		/********************/
		
		JPanel ust =new JPanel();						//elnent okreslajcy polozenie ustawien
		ust.add(ust1);
		ust.add(ust2);
		
		/*********************************************/
		

		JTextField h11= new JTextField(3);
		JPanel h11p =new JPanel();
		h11p.add(new JLabel("H11"));
		h11p.add(h11);

		JTextField h21= new JTextField(3);
		JPanel h21p =new JPanel();
		h21p.add(new JLabel("H21"));
		h21p.add(h21);
		
		JPanel m1 =new JPanel();
		m1.setLayout(new BoxLayout(m1,BoxLayout.Y_AXIS));
		m1.add(h11p);
		m1.add(h21p);
		
		/********************/

		JTextField h12= new JTextField(3);
		JPanel h12p =new JPanel();
		h12p.add(new JLabel("H12"));
		h12p.add(h12);
		
		JTextField h22= new JTextField(3);
		JPanel h22p =new JPanel();
		h22p.add(new JLabel("H22"));
		h22p.add(h22);

		JPanel m2 =new JPanel();
		m2.setLayout(new BoxLayout(m2,BoxLayout.Y_AXIS));
		m2.add(h12p);
		m2.add(h22p);
		
		JPanel macierz =new JPanel();
		macierz.setBorder(new LineBorder(kolorramek));
		macierz.add(m1);
		macierz.add(m2);
		
		/*********************************************/
		
		JPanel menu =new JPanel();						//glowna ramka
		menu.setBorder(new LineBorder(kolorramek));
		menu.setLayout(new BoxLayout(menu,BoxLayout.Y_AXIS));
		menu.add(new JLabel("Nastawy:",JLabel.CENTER));
		menu.add(wnast);
		menu.add(ust);
		menu.add(macierz);
		
		/*********************************************/
		
		JButton wczytaj = new JButton("Wczytaj Tranzystor");
		
		JButton zapisz = new JButton("Zapisz tranzystor");
		
		JButton wyniki = new JButton("Zapisz wyniki");
		
		JButton IO = new JButton("I/O");
		
		JPanel przyciski =new JPanel();	
		przyciski.add(wczytaj);
		przyciski.add(zapisz);
		przyciski.add(wyniki);
		przyciski.add(IO);
		
		/*********************************************/

		
		add(wykresy);
		add(menu);
		add(przyciski);
	}
	
	public static void main(String[] args) //Służy do testów
	{
		
		InterFace frame = new InterFace();
		frame.setVisible(true);
	}
}
