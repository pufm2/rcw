package puf.m2.rcw;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
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

public class MainFrame extends JFrame {

    private static final long serialVersionUID = 2041758932746659616L;
    private JPanel contentPane;
    private JTextField textVerNumber;
    private JTextField textEdges;
    private JTextField textReducedTerm;
    private JTextField textTerm;


    /**
     * Create the frame.
     */
    public MainFrame() {
        setTitle("Relative Clique Width");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 797, 299);
        
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
        sl_contentPane.putConstraint(SpringLayout.NORTH, btnLoad, 10, SpringLayout.NORTH, contentPane);
        sl_contentPane.putConstraint(SpringLayout.WEST, btnLoad, -80, SpringLayout.EAST, contentPane);
        sl_contentPane.putConstraint(SpringLayout.EAST, btnLoad, -10, SpringLayout.EAST, contentPane);
        contentPane.add(btnLoad);
        
        JButton btnSave = new JButton("Save");
        sl_contentPane.putConstraint(SpringLayout.NORTH, btnSave, 7, SpringLayout.SOUTH, btnLoad);
        sl_contentPane.putConstraint(SpringLayout.WEST, btnSave, -80, SpringLayout.EAST, contentPane);
        sl_contentPane.putConstraint(SpringLayout.EAST, btnSave, -10, SpringLayout.EAST, contentPane);
        contentPane.add(btnSave);
        
        JButton btnProcess = new JButton("Process");
        btnProcess.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Term term = Main.constructTerm(textReducedTerm.getText(), textEdges.getText());
                    textTerm.setText(term.toString());
                } catch (RcwException e1) {
                    JOptionPane.showMessageDialog(null, "Invalid Input", "Alert", JOptionPane.ERROR_MESSAGE); 
                }
            }
        });
        sl_contentPane.putConstraint(SpringLayout.WEST, btnProcess, -197, SpringLayout.EAST, contentPane);
        sl_contentPane.putConstraint(SpringLayout.EAST, btnProcess, 0, SpringLayout.EAST, textEdges);
        contentPane.add(btnProcess);
        
        JLabel Term = new JLabel("Term:");
        sl_contentPane.putConstraint(SpringLayout.WEST, Term, 0, SpringLayout.WEST, lblNewLabel);
        sl_contentPane.putConstraint(SpringLayout.SOUTH, Term, -37, SpringLayout.SOUTH, contentPane);
        contentPane.add(Term);
        
        textTerm = new JTextField();
        sl_contentPane.putConstraint(SpringLayout.SOUTH, btnProcess, -15, SpringLayout.NORTH, textTerm);
        sl_contentPane.putConstraint(SpringLayout.NORTH, textTerm, -3, SpringLayout.NORTH, Term);
        sl_contentPane.putConstraint(SpringLayout.WEST, textTerm, 0, SpringLayout.WEST, textVerNumber);
        sl_contentPane.putConstraint(SpringLayout.EAST, textTerm, 0, SpringLayout.EAST, textEdges);
        contentPane.add(textTerm);
        textTerm.setColumns(10);
    }
}
