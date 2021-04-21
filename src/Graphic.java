import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Graphic extends JFrame {

    private final JFrame f;
    private final JPanel p;
    private int size;
    private ArrayList<JLabel> labelList;
    private Room[][] map;

    public Graphic(Room[][] map)
    {
        f = new JFrame();
        p = new JPanel();
        labelList = new ArrayList<>();

        this.map = map;

        SetNewEnvironnement(15);

        f.setLayout(new BorderLayout());
        f.add(p, BorderLayout.CENTER);

        f.setSize(800,800);
        f.setDefaultCloseOperation( EXIT_ON_CLOSE );
        f.setTitle("Enclos");
        f.setVisible(true);
    }

    /*
    RAZ du panel et de la list de label, initialisation de n² labels
     */
    public void SetNewEnvironnement(int n)
    {
        size = n;
        p.removeAll();
        p.revalidate();
        p.repaint();
        labelList.clear();

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {


                JLabel lab = new JLabel();

                String labText = "";
                Room room = map[x][y];
                if(!room.containsSheep && !room.containsEnclos && !room.containsDog)
                    labText += ".";
                else {
                    if(room.containsEnclos)
                        labText += " Enclos";
                    if(room.containsDog)
                        labText += " Dog";
                    if(room.containsSheep)
                        labText +="Sheep";
                }

                lab.setText(labText);
                if(room.color == DogColor.BLUE)
                    lab.setForeground(Color.BLUE);
                else if(room.color == DogColor.RED)
                    lab.setForeground(Color.RED);

                lab.setHorizontalAlignment(SwingConstants.CENTER);
                lab.setVerticalAlignment(SwingConstants.CENTER);

                lab.setBorder(BorderFactory.createMatteBorder(0,0,1,1, Color.BLACK));

                labelList.add(lab);
                p.add(lab);
            }
        }

        p.setLayout(new GridLayout(n,n));
    }
}
