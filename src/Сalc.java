import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Сalc {
    private int activeMemory = 0;
    private int activeFormula = 1;

    private double[] memory = new double[3];
    private JFrame mainFrame = new JFrame();
    private JLabel resultLabel  = new JLabel();
    private JLabel formulaLabel = new JLabel();
    private JLabel memoryLabel = new JLabel("MM");
    private JLabel memoryLabel1 = new JLabel("0");
    private JLabel memoryLabel2 = new JLabel("0");
    private JLabel memoryLabel3 = new JLabel("0");
    private JLabel formulaTextLabel = new JLabel("Formula:");
    private JButton ClearButton = new JButton("Clear");

    Сalc() {
        this.mainFrame.setDefaultCloseOperation(3);
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();
        this.mainFrame.setSize(width, height);
        JRadioButton mem1 = new JRadioButton("MM1");
        JRadioButton mem2 = new JRadioButton("MM2");
        JRadioButton mem3 = new JRadioButton("MM3");
        mem1.setSelected(true);
        mem1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               Сalc.this.activeMemory = 0;
            }
        });
        mem2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Сalc.this.activeMemory = 1;
            }
        });
        mem3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Сalc.this.activeMemory = 2;
            }
        });

        ButtonGroup memButtonGroup = new ButtonGroup();
        memButtonGroup.add(mem1);
        memButtonGroup.add(mem2);
        memButtonGroup.add(mem3);
        JButton buttonMemoryPlus = new JButton("M+");
        buttonMemoryPlus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int active = Сalc.this.activeMemory;
                double[] cur = Сalc.this.memory;
                cur[active] += Double.parseDouble(Сalc.this.resultLabel.getText());
                Сalc.this.updateMemory();
            }
        });
        JButton buttonMemoryMinus = new JButton("M-");
        buttonMemoryMinus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int active = Сalc.this.activeMemory;
                double[] cur = Сalc.this.memory;
                cur[active] -= Double.parseDouble(Сalc.this.resultLabel.getText());
                Сalc.this.updateMemory();
            }
        });
        JButton buttonMemoryClear = new JButton("MC");
        buttonMemoryClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int active = Сalc.this.activeMemory;
                Сalc.this.memory[active] = 0.0;
                Сalc.this.updateMemory();
            }
        });
        JRadioButton form1 = new JRadioButton("1");
        JRadioButton form2 = new JRadioButton("2");
        form1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Сalc.this.activeFormula = 1;
                Сalc.this.drawFormula("resources/formula1.bmp");
            }
        });
        form2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Сalc.this.activeFormula = 2;
                Сalc.this.drawFormula("resources/formula2.bmp");
            }
        });
        form1.setSelected(true);
        ButtonGroup formulaButtonGroup = new ButtonGroup();
        formulaButtonGroup.add(form1);
        formulaButtonGroup.add(form2);
        final JTextField textX = new JTextField("0", 6);
        final JTextField textY = new JTextField("0", 6);
        final JTextField textZ = new JTextField("0", 6);
        JButton solve = new JButton("Solve");
        solve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Double result = null;
                double x = Double.parseDouble(textX.getText());
                double y = Double.parseDouble(textY.getText());
                double z = Double.parseDouble(textZ.getText());
                switch (Сalc.this.activeFormula)
                {
                    case 1 -> result = Сalc.this.calculateFormula1(x, y, z);
                    case 2 -> result = Сalc.this.calculateFormula2(x, y, z);
                }
                Сalc.this.resultLabel.setText(result.toString());
            }
        });

        this.ClearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textX.setText("0");
                textY.setText("0");
                textZ.setText("0");
                Сalc.this.resultLabel.setText("0");
            }
        });
         Box hboxFormulaChoice = Box.createHorizontalBox();
         hboxFormulaChoice.add(Box.createHorizontalGlue());
         hboxFormulaChoice.add(this.formulaTextLabel);
         hboxFormulaChoice.add(form1);
         hboxFormulaChoice.add(form2);
         hboxFormulaChoice.add(Box.createHorizontalGlue());
         Box hboxFormulaImage = Box.createHorizontalBox();
         hboxFormulaImage.add(Box.createHorizontalGlue());
         hboxFormulaImage.add(Box.createHorizontalStrut(80));
         hboxFormulaImage.add(this.formulaLabel);
         hboxFormulaImage.add(Box.createVerticalStrut(80));
         hboxFormulaImage.add(Box.createHorizontalGlue());
         Box hboxMemory = Box.createHorizontalBox();
         hboxMemory.add(Box.createHorizontalGlue());
         hboxMemory.add(this.memoryLabel);
         hboxMemory.add(mem1);
         hboxMemory.add(mem2);
         hboxMemory.add(mem3);
         hboxMemory.add(buttonMemoryPlus);
         hboxMemory.add(buttonMemoryMinus);
         hboxMemory.add(buttonMemoryClear);
         hboxMemory.add(Box.createHorizontalGlue());
         Box hboxMemoryLabels = Box.createHorizontalBox();
         hboxMemoryLabels.add(Box.createHorizontalGlue());
         hboxMemoryLabels.add(this.memoryLabel1);
         hboxMemoryLabels.add(Box.createHorizontalStrut(20));
         hboxMemoryLabels.add(this.memoryLabel2);
         hboxMemoryLabels.add(Box.createHorizontalStrut(20));
         hboxMemoryLabels.add(this.memoryLabel3);
         hboxMemoryLabels.add(Box.createHorizontalGlue());
         hboxMemoryLabels.setMaximumSize(new Dimension(width, 30));
         Box hboxVariables = Box.createHorizontalBox();
         hboxVariables.add(Box.createHorizontalStrut(width / 5));
         Box hboxFunc = Box.createHorizontalBox();
        hboxFunc.add(new JLabel("F( "));
        hboxFunc.add(textX);
        hboxFunc.add(new JLabel(" ; "));
        hboxFunc.add(textY);
        hboxFunc.add(new JLabel(" ; "));
        hboxFunc.add(textZ);
        hboxFunc.add(new JLabel(") = "));
        hboxFunc.setMaximumSize(new Dimension(30, 20));
        hboxVariables.add(hboxFunc);
        hboxVariables.add(Box.createHorizontalStrut(3));
        hboxVariables.add(this.resultLabel);
        hboxVariables.setMaximumSize(new Dimension(1000, 30));
        hboxVariables.add(Box.createHorizontalGlue());
        Box hboxCalculate = Box.createHorizontalBox();
        hboxCalculate.add(this.ClearButton);
        hboxCalculate.add(Box.createHorizontalGlue());
        hboxCalculate.add(solve);
        Box contentBox = Box.createVerticalBox();
        contentBox.add(hboxFormulaChoice);
        contentBox.add(hboxFormulaImage);
        contentBox.add(hboxMemory);
        contentBox.add(hboxMemoryLabels);
        contentBox.add(Box.createVerticalStrut(10));
        contentBox.add(hboxVariables);
        contentBox.add(Box.createVerticalStrut(10));
        contentBox.add(hboxCalculate);
        contentBox.add(Box.createHorizontalGlue());
        this.mainFrame.getContentPane().add(contentBox);
    }

    private Double calculateFormula1(Double x, Double y, Double z) {
        double part1 = Math.sin(Math.log(y) + Math.sin(Math.PI * Math.pow(y, 2)));
        double part2 = Math.pow(x * x + Math.sin(z) + Math.pow(Math.E, Math.cos(z)), 1/4);
        return y > 0 ? part1 * part2 : 0;
    }

    private Double calculateFormula2(Double x, Double y, Double z) {
        double part1 = Math.cos(Math.pow(Math.E, x)) + Math.log(Math.pow(1 + y, 2));
        double part2 = Math.sqrt(Math.pow(Math.E, Math.cos(x)) + Math.pow(Math.sin(Math.PI * z), 2));
        double part3 = Math.sqrt(1/x) + Math.cos(Math.pow(y, 2));
        return x > 0 && y > -1 ? Math.pow(part1 + part2 + part3, Math.sin(z)) : 0;
    }

    private void updateMemory() {
        this.memoryLabel1.setText(Double.toString(this.memory[0]));
        this.memoryLabel2.setText(Double.toString(this.memory[1]));
        this.memoryLabel3.setText(Double.toString(this.memory[2]));
        this.mainFrame.repaint();
    }

    private void updateResult(int memoryCell) {
        this.resultLabel.setText(Double.toString(this.memory[memoryCell]));
    }

    private void drawFormula(String filePath) {
        try {
            BufferedImage image = ImageIO.read(new File(filePath));
            this.formulaLabel.setIcon(new ImageIcon(image));
            this.formulaLabel.revalidate();
            this.formulaLabel.repaint();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(mainFrame, "Ошибка загрузки формулы");
        }
    }
    public void setVisible(boolean state) { this.mainFrame.setVisible(state);}
}
