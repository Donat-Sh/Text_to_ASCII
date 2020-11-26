import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.image.*;


//Klasa ne vijim e kryen afishim e mesazhit tekstual i japur nga shfrytezuesi ne formen Asterix
public class DetyraTekstuale extends JApplet implements ActionListener 
{
   private JTextField text; //Kjo vetem e merr tekstin afer butonit
   private JTextArea ta;   //Kjo e perfshine tekstin ne qender


   //Konstruktori i klases DetyraTekstuale
   public DetyraTekstuale()
      {
         JOptionPane.showMessageDialog(null,"Ju lutem japni tekstin deri ne 8 karakter maksimum e jo me teper");

         String s = JOptionPane.showInputDialog("Ju lutem japni fjalen qe doni ta afishoni!");

         String word = analyzeString(s);

         text = new JTextField(word);
         ta = new JTextArea(20, 80);
      }


   //Metoda kryesuese Main e cila e starton ekzekutimin
   public static void main(String[] args) 
   {
      JFrame frame = new JFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      //Krijojme Appletin
      JApplet applet = new DetyraTekstuale();
      frame.getContentPane().add(applet);

      //Kjo eshte Metoda e pare(primare) e cila ekzekutohet ne momentin kur krijohet JApplet
      applet.init();

      //Kjo eshte Metoda e dyte (sekondare) e cila ekzekutohet pas momentit te ekzekutimit te Metodes init()
      applet.start();

      frame.setTitle("Afishimi-Grafik i Tekstit");
      frame.setSize(600,400);
      frame.setVisible(true);
   }
   

   //Metoda ne vijim neve na tregon se Stringu i japur a i permbush kerkesat e Appletit
   public String analyzeString(String s)
      {
         String answer = "";

         String temp = s;
         
         if(temp.length() <=8 && (!(temp.equals("")))) 
            {
               System.out.println("Teksti eshte ne gjendje mire");
               System.out.println();
            }
         else{
               JOptionPane.showMessageDialog(null,"Mesazhi i japur (error) : mbi 8 karakter ose boshe");

               System.out.println("Stringu KEQ");
               
               System.out.println("--------------------");

               System.exit(0);
             }   
         
         answer = s;   
         
         return answer;
      }



   //Ne vijim e kemi Metoden e cila ekzekutohet e para(primare) ne momentin kur e krijojme Applet
   public void init() 
   {  
      //Krijojme fontin per TextArea
      Font myFont = new Font("Courier", Font.PLAIN,10);

      ta.setFont(myFont);

      //Krijojme Panon mbi te cilen ne do te vizatojme
      JPanel p = new JPanel();

      BorderLayout laying = new BorderLayout();

      p.setLayout(laying);
      p.add(text, BorderLayout.CENTER);


      JButton b = new JButton("Printoje");
      b.addActionListener(this);
      p.add(b, BorderLayout.EAST);

      BorderLayout laying2 = new BorderLayout();


      this.getContentPane().setLayout(laying2);
      this.getContentPane().add(p, BorderLayout.NORTH);
      this.getContentPane().add(ta, BorderLayout.CENTER);
   }
 


   //Metoda ne vijim e kryen llogaritjen kryesore ne momentin kur klikohet butoni "Printoje" me qrast afishohet ne Pano permbajtja tekstuale
   // e shprehur nepermjet simbolikave asterix (*)
   public void actionPerformed(ActionEvent e) 
   {
      //poshte marrim tekstin nga TextField
      String s = text.getText();

      //Poshte e kemi loop-in kryesor i cili e kryen veprimin e vizatimin mbi Panon pas marrjes se mesazhit
      if (s != null)
      {
         //Ne vijim ne krijojme Objektin BufferedImage me dimenzione te width = 80, height 20 dhe tip te imazhit Blue-Green-Red (8 bit) me qellim qe nga vizatimi i panos
         //te keni mnundesi per analzim buffer te se dhenave mbi ate Pano ne nivel te pixel-ave me koordinata specifike. Ku pra per secilin prej 80 rreshtave ata individualisht
         // i trajtojme me nga 20 shtyllat e tyre perkatese ne Matricen e krijuar.
         BufferedImage bi = new BufferedImage(80,20,BufferedImage.TYPE_INT_BGR);
         
         //E thirrim objektin e Graphics2D me qellim qe te jemi ne gjendje te kemi me shume kontroll mbi koordinatat (x,y) te sis. 2D per vizatimin mbi panon me von
         Graphics2D g2 = (Graphics2D)bi.getGraphics();

         Font myFont2 = new Font("Serif", Font.BOLD,20);
         g2.setFont(myFont2);

         //Tani duke e perdorur objektin e krijuar pra Graphics2D ne fillojme qe ta bejme 'render' Stringun e japur si input nga shfrytezuesi ne panon karakter per
         //karakter ne dimenzionet e japur (0,16)
         g2.drawString(s, 0, 16);

         //Krijojme objektin Raster i cili eshte nje renditje ne forme te drejtkendeshit te array-it pikselave dhe me rastin e thirrjes se metodes 'getData()' ne jemi 
         //ne gjendje ta marrim si te dhena pra data mbi koordinimin e pikselave te panos nga permbajtja tekstuale te cilen do e vizatojme
         Raster ras = bi.getData();

         ta.setText("");

         //Pasi qe BufferImage i krijuar eshte ne kordinata 80 me 20 kjo e ka kuptimin qe secili rresht do te trajtohet ne matricen dy-dimensionale me nga 20 shtylla
         //ne do shqyrtojme secilin prej tyre per ta pasur te qarte mundesin per ta printuar karakterin ne vijim per asterix.
         for (int rreshti = 0; rreshti < 20; rreshti++) 
         {
            String line = "";

            for (int shtylla = 0; shtylla < 80; shtylla++) 
            {  
               //Urdheri ne vijim kthen nje 'int' i cili do te jete i lokacionuar ne Raster object i cili ne vetvete e permban matricen me krejt te dhenat mbi 
               //pikselet e imazhit te vizatuar mbi Panon deri ne 128 per shkake se kemi BGR ne 8 bit
               if(ras.getSample(shtylla,rreshti,1) > 128) 
               {line += "*";}
               else{line += " ";}
            }

            System.out.println(line);

            //Urdheri ne vijim i shton tekstin final te konvertuar ne asterix nga variabla e Stringut line tek TexArea dhe kete e kryen per secilin rresht
            //ne menyre te atill qe nje rreshte e kryen llogaritjen ne 20 shtyllat pastaj shtyeht ne TextArea dhe pastaj vjen rreshti i dyte e keshtu me radhe
            //deri tek rreshti i 20
            ta.append(line+"\n");
         }
      }
   }
}