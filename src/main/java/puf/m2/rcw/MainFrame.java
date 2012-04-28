package puf.m2.rcw;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.SpringLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import puf.m2.rcw.exception.RcwException;
import puf.m2.rcw.term.Term;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainFrame extends JFrame {

    private static final long serialVersionUID = 2041758932746659616L;
    private JPanel contentPane;
    private JTextField textVerNumber;
    private JTextField textEdges;
    private JTextField textReducedTerm;
    private JTextField textTerm;
    private JTextField textUsedLabels;

    private final JFileChooser fc = new JFileChooser();
    private final MainFrame thisFrame = this;
    /**
     * Create the frame.
     */
    public MainFrame() {
        setTitle("Relative Clique Width");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 797, 336);
        
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        
        JMenu mnFile = new JMenu("File");
        menuBar.add(mnFile);
        
        JMenuItem mntmOpen = new JMenuItem("Open");
        mnFile.add(mntmOpen);
        
        JMenuItem mntmExit = new JMenuItem("Exit");
        mnFile.add(mntmExit);
        
        JMenu mnHelp = new JMenu("Help");
        menuBar.add(mnHelp);
        
        JMenuItem mntmAbout = new JMenuItem("About");
        mnHelp.add(mntmAbout);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        SpringLayout sl_contentPane = new SpringLayout();
        contentPane.setLayout(sl_contentPane);
        
        JLabel lblNewLabel = new JLabel("Number of vertices:");
        sl_contentPane.putConstraint(SpringLayout.NORTH, lblNewLabel, 32, SpringLayout.NORTH, contentPane);
        sl_contentPane.putConstraint(SpringLayout.WEST, lblNewLabel, 0, SpringLayout.WEST, contentPane);
        contentPane.add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("Edges:");
        sl_contentPane.putConstraint(SpringLayout.NORTH, lblNewLabel_1, 21, SpringLayout.SOUTH, lblNewLabel);
        sl_contentPane.putConstraint(SpringLayout.WEST, lblNewLabel_1, 0, SpringLayout.WEST, contentPane);
        contentPane.add(lblNewLabel_1);
        
        textVerNumber = new JTextField();
        sl_contentPane.putConstraint(SpringLayout.NORTH, textVerNumber, -3, SpringLayout.NORTH, lblNewLabel);
        sl_contentPane.putConstraint(SpringLayout.WEST, textVerNumber, 17, SpringLayout.EAST, lblNewLabel);
        sl_contentPane.putConstraint(SpringLayout.SOUTH, textVerNumber, 3, SpringLayout.SOUTH, lblNewLabel);
        sl_contentPane.putConstraint(SpringLayout.EAST, textVerNumber, 65, SpringLayout.EAST, lblNewLabel);
        contentPane.add(textVerNumber);
        textVerNumber.setColumns(10);
        
        JLabel lblNewLabel_2 = new JLabel("Linear reduced term:");
        sl_contentPane.putConstraint(SpringLayout.NORTH, lblNewLabel_2, 22, SpringLayout.SOUTH, lblNewLabel_1);
        sl_contentPane.putConstraint(SpringLayout.WEST, lblNewLabel_2, 0, SpringLayout.WEST, lblNewLabel);
        contentPane.add(lblNewLabel_2);
        
        textEdges = new JTextField();
        sl_contentPane.putConstraint(SpringLayout.NORTH, textEdges, -3, SpringLayout.NORTH, lblNewLabel_1);
        sl_contentPane.putConstraint(SpringLayout.WEST, textEdges, 0, SpringLayout.WEST, textVerNumber);
        sl_contentPane.putConstraint(SpringLayout.EAST, textEdges, 496, SpringLayout.WEST, textVerNumber);
        sl_contentPane.putConstraint(SpringLayout.EAST, textEdges, -108, SpringLayout.EAST, contentPane);
        contentPane.add(textEdges);
        textEdges.setColumns(10);
        
        textReducedTerm = new JTextField();
        sl_contentPane.putConstraint(SpringLayout.NORTH, textReducedTerm, -3, SpringLayout.NORTH, lblNewLabel_2);
        sl_contentPane.putConstraint(SpringLayout.WEST, textReducedTerm, 0, SpringLayout.WEST, textVerNumber);
        sl_contentPane.putConstraint(SpringLayout.EAST, textReducedTerm, -108, SpringLayout.EAST, contentPane);
        contentPane.add(textReducedTerm);
        textReducedTerm.setColumns(10);
        
        JButton btnLoad = new JButton("Load");
        btnLoad.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int result = fc.showOpenDialog(thisFrame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    DataInputStream dis;
                    try {
                        dis = new DataInputStream(new FileInputStream(fc.getSelectedFile()));
                    
                        BufferedReader br = new BufferedReader(new InputStreamReader(dis));
    
                        textVerNumber.setText(br.readLine());
                        textReducedTerm.setText(br.readLine());
                        textEdges.setText(br.readLine());
                        
                        dis.close();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Invalid File", "Alert", JOptionPane.ERROR_MESSAGE); 
                    }
                }
            }
        });
        sl_contentPane.putConstraint(SpringLayout.NORTH, btnLoad, 10, SpringLayout.NORTH, contentPane);
        sl_contentPane.putConstraint(SpringLayout.WEST, btnLoad, -80, SpringLayout.EAST, contentPane);
        sl_contentPane.putConstraint(SpringLayout.EAST, btnLoad, -10, SpringLayout.EAST, contentPane);
        contentPane.add(btnLoad);
        
        JButton btnSave = new JButton("Save");
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int result = fc.showSaveDialog(thisFrame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    DataOutputStream dos;
                    try {
                        dos = new DataOutputStream(new FileOutputStream(fc.getSelectedFile()));
                        
                        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(dos));
                        bw.write(textVerNumber.getText() + "\n");
                        bw.write(textReducedTerm.getText() + "\n");
                        bw.write(textEdges.getText() + "\n");
                        bw.write(textTerm.getText() + "\n");
                        
                        bw.close();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Invalid File", "Alert", JOptionPane.ERROR_MESSAGE); 
                    }
                }
            }
        });
        sl_contentPane.putConstraint(SpringLayout.NORTH, btnSave, 7, SpringLayout.SOUTH, btnLoad);
        sl_contentPane.putConstraint(SpringLayout.WEST, btnSave, -80, SpringLayout.EAST, contentPane);
        sl_contentPane.putConstraint(SpringLayout.EAST, btnSave, -10, SpringLayout.EAST, contentPane);
        contentPane.add(btnSave);
        
        JButton btnProcess = new JButton("Process");
        sl_contentPane.putConstraint(SpringLayout.NORTH, btnProcess, 24, SpringLayout.SOUTH, textReducedTerm);
        sl_contentPane.putConstraint(SpringLayout.WEST, btnProcess, -197, SpringLayout.EAST, contentPane);
        sl_contentPane.putConstraint(SpringLayout.EAST, btnProcess, -108, SpringLayout.EAST, contentPane);
        btnProcess.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Term term = Main.constructTerm(textReducedTerm.getText(), textEdges.getText());
                    textTerm.setText(term.toString());
                    textUsedLabels.setText(Integer.toString(term.getUsedLabels().size()));
                } catch (RcwException e1) {
                    JOptionPane.showMessageDialog(null, "Invalid Input", "Alert", JOptionPane.ERROR_MESSAGE); 
                }
            }
        });
        contentPane.add(btnProcess);
        
        JLabel Term = new JLabel("Term:");
        sl_contentPane.putConstraint(SpringLayout.WEST, Term, 0, SpringLayout.WEST, lblNewLabel);
        contentPane.add(Term);
        
        textTerm = new JTextField();
        sl_contentPane.putConstraint(SpringLayout.NORTH, Term, 3, SpringLayout.NORTH, textTerm);
        sl_contentPane.putConstraint(SpringLayout.NORTH, textTerm, 17, SpringLayout.SOUTH, btnProcess);
        sl_contentPane.putConstraint(SpringLayout.WEST, textTerm, 0, SpringLayout.WEST, textVerNumber);
        sl_contentPane.putConstraint(SpringLayout.EAST, textTerm, 0, SpringLayout.EAST, textEdges);
        contentPane.add(textTerm);
        textTerm.setColumns(10);
        
        JLabel lblNewLabel_3 = new JLabel("Number of labels");
        sl_contentPane.putConstraint(SpringLayout.NORTH, lblNewLabel_3, 30, SpringLayout.SOUTH, Term);
        sl_contentPane.putConstraint(SpringLayout.WEST, lblNewLabel_3, 0, SpringLayout.WEST, lblNewLabel);
        contentPane.add(lblNewLabel_3);
        
        textUsedLabels = new JTextField();
        sl_contentPane.putConstraint(SpringLayout.NORTH, textUsedLabels, -3, SpringLayout.NORTH, lblNewLabel_3);
        sl_contentPane.putConstraint(SpringLayout.WEST, textUsedLabels, 0, SpringLayout.WEST, textVerNumber);
        sl_contentPane.putConstraint(SpringLayout.SOUTH, textUsedLabels, -12, SpringLayout.SOUTH, contentPane);
        sl_contentPane.putConstraint(SpringLayout.EAST, textUsedLabels, 0 , SpringLayout.EAST, textEdges);
        contentPane.add(textUsedLabels);
        textUsedLabels.setColumns(10);
    }
}
