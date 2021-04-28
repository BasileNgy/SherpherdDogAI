import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Graphic extends JFrame {

    private final JFrame f;
    private final JPanel p;
    private final JButton btn;
    private int size;
    private ArrayList<JLabel> labelList;

    public Graphic(Environnement envir, Agents multiAgents)
    {
        f = new JFrame();
        p = new JPanel();
        btn = new JButton("Action");
        labelList = new ArrayList<>();

        btn.addActionListener(e -> multiAgents.ResolutionAlgorithms());

        SetNewEnvironnement(envir);

        f.setLayout(new BorderLayout());
        f.add(p, BorderLayout.CENTER);
        f.add(btn, BorderLayout.SOUTH);

        f.setSize(800,800);
        f.setDefaultCloseOperation( EXIT_ON_CLOSE );
        f.setTitle("Enclos");
        f.setVisible(true);
    }

    /*
    RAZ du panel et de la list de label, initialisation de n² labels
     */
    public void SetNewEnvironnement(Environnement envir)
    {
        size = envir.size;
        p.removeAll();
        p.revalidate();
        p.repaint();
        labelList.clear();

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {

                JLabel lab = SetLabelTextColor(envir.map[x][y], new JLabel());

                lab.setHorizontalAlignment(SwingConstants.CENTER);
                lab.setVerticalAlignment(SwingConstants.CENTER);

                lab.setBorder(BorderFactory.createMatteBorder(0,0,1,1, Color.BLACK));

                labelList.add(lab);
                p.add(lab);
            }
        }
        p.setLayout(new GridLayout(size,size));
    }

    public void UpdateGraphic(Environnement envir)
    {
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {

                SetLabelTextColor(envir.map[x][y], labelList.get(y * size + x));
            }
        }
    }

    private JLabel SetLabelTextColor(Room room, JLabel lab)
    {
        String labText = "";

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
        else lab.setForeground(Color.BLACK);

        return lab;
    }
}
