import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.*;

class PhoneBook extends JFrame implements ActionListener, FocusListener
{
	static HashMap<String, Long> hmap;
	JTabbedPane tabbedPane;
	JPanel addPanel, tabPanel, searchPanel, deletePanel;

	JLabel addNotf=new JLabel("");
	JLabel searchNotf=new JLabel("");
	JLabel deleteNotf=new JLabel("");

	JTextField addName, addNo, searchName, searchNo, deleteName, deleteNo;

	JButton addIt, searchIt, deleteIt, savenExit, exit;

	JTable tab=new JTable();
	DefaultTableModel dtm=new DefaultTableModel(0, 0);
	
	boolean added=false;
	static File f;
	static Main m;
	static PhoneBook mainFrame;
	
	int v=ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
	int h=ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
	
	String head[]={"Name", "Phone No."};
	String temp[]=new String[2];
	
	/*To start the JFrame*/
	public PhoneBook()
	{
		
		setTitle("Phonebook");
		setSize( 700, 400 );
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout( new BorderLayout() );
		getContentPane().add( topPanel );

		createPage1();
		createPage2();
		createPage3();
		createPage4();
	
		
		if(!added)
			tabbedPane = new JTabbedPane();
		tabbedPane.addTab( "ADD", addPanel );
		tabbedPane.addTab( "SEARCH", searchPanel );
		tabbedPane.addTab( "DELETE", deletePanel );
		topPanel.add( tabbedPane, BorderLayout.CENTER );
	}

	
	/*Add tab*/
	public void createPage1()
	{
		addPanel = new JPanel();
		addPanel.setLayout( null );
		addPanel.setBackground(Color.decode("#4CAF50"));

		JLabel labelName = new JLabel( "Name:" );
		labelName.setBounds( 100, 15, 150, 20 );
		addPanel.add(labelName);

		addName = new JTextField();
		addName.setBounds( 100, 35, 150, 20 );
		addPanel.add( addName );
		addName.addFocusListener(this);

		JLabel labelNo = new JLabel( "Phone Number:" );
		labelNo.setBounds( 100, 60, 150, 20 );
		addPanel.add( labelNo );

		addNo=new JTextField();
		addNo.setBounds( 100, 80, 150, 20 );
		addPanel.add(addNo);
		addNo.addFocusListener(this);

		addIt=new JButton("ADD");
		addIt.setBounds(100, 120, 150, 20);
		addPanel.add(addIt);
		addIt.addActionListener(this);
		
		addNotf.setBounds(100, 160, 150, 20);
		addNotf.setSize(200, 20);
		addPanel.add(addNotf);
		
		savenExit=new JButton("SAVE & EXIT");
		savenExit.setBounds(100, 270, 150, 20);
		addPanel.add(savenExit);
		savenExit.addActionListener(this);

		exit=new JButton("EXIT");
		exit.setBounds(100, 300, 150, 20);
		addPanel.add(exit);
		exit.addActionListener(this);
	
	}

	/*Contacts tab*/
	public void createPage2()
	{
		tabPanel = new JPanel();
		tabPanel.setLayout(new BorderLayout());
		
		dtm.setColumnIdentifiers(head);
		if(hmap.size()>0)
		{
			Set<Map.Entry<String, Long>> set=hmap.entrySet();
			for(Map.Entry<String,Long> ss : set)
			{
				temp[0]=ss.getKey();
				temp[1]=ss.getValue().toString();
				dtm.addRow(temp);
			}
			
			tab.setModel(dtm);
			tabbedPane=new JTabbedPane();
			JScrollPane jsp=new JScrollPane(tab,v,h);
			tabPanel.add(jsp, BorderLayout.CENTER);
			tabbedPane.addTab( "CONTACTS", tabPanel );
			added=true;
		}
	}
	
	/*Search Tab*/
	public void createPage3()
	{
		searchPanel = new JPanel();
		searchPanel.setLayout(null);
		searchPanel.setBackground(Color.decode("#FB861A"));
		
		JLabel labelName = new JLabel( "Name:" );
		labelName.setBounds( 100, 15, 150, 20 );
		searchPanel.add( labelName );

		searchName = new JTextField();
		searchName.setBounds( 100, 35, 150, 20 );
		searchPanel.add( searchName );
		searchName.addFocusListener(this);

		JLabel labelOr = new JLabel( "OR" );
		labelOr.setBounds( 100, 70, 150, 20 );
		searchPanel.add( labelOr );
		
		JLabel labelNo = new JLabel( "Phone Number:" );
		labelNo.setBounds( 100, 100, 150, 20 );
		searchPanel.add( labelNo );
		
		searchNo=new JTextField();
		searchNo.setBounds( 100, 120, 150, 20 );
		searchPanel.add(searchNo);
		searchNo.addFocusListener(this);

		searchIt=new JButton("SEARCH");
		searchIt.setBounds(100, 150, 150, 20);
		searchPanel.add(searchIt);
		searchIt.addActionListener(this);
		
		searchNotf.setBounds(100, 200, 150, 20);
		searchPanel.add(searchNotf);
	}
	
	
	/*Delete Tab*/
	public void createPage4()
	{
		deletePanel=new JPanel();
		deletePanel.setLayout(null);
		deletePanel.setBackground(Color.decode("#FFA114"));
		
		JLabel labelName = new JLabel( "Name:" );
		labelName.setBounds( 100, 15, 150, 20 );
		deletePanel.add( labelName );

		deleteName = new JTextField();
		deleteName.setBounds( 100, 35, 150, 20 );
		deletePanel.add( deleteName );
		deleteName.addFocusListener(this);

		JLabel labelOr = new JLabel( "OR" );
		labelOr.setBounds( 100, 70, 150, 20 );
		deletePanel.add( labelOr );
		
		JLabel labelNo = new JLabel( "Phone Number:" );
		labelNo.setBounds( 100, 100, 150, 20 );
		deletePanel.add( labelNo );
		
		deleteNo=new JTextField();
		deleteNo.setBounds( 100, 120, 150, 20 );
		deletePanel.add(deleteNo);
		deleteNo.addFocusListener(this);

		deleteIt=new JButton("DELETE");
		deleteIt.setBounds(100, 150, 150, 20);
		deletePanel.add(deleteIt);
		deleteIt.addActionListener(this);
		
		deleteNotf.setBounds(100, 200, 150, 20);
		deletePanel.add(deleteNotf);
	}

	public void actionPerformed(ActionEvent e)
	{
		try
		{
			oo:if(e.getSource()==addIt)
			{
				if(addName.getText().equals(""))
				{
					addNotf.setText("Enter the details.");
					break oo;
				}
				if(addNo.getText().length()!=10)
				{
					addNotf.setText("Enter 10-digit Number.");
					break oo;
				}
				
				for (int i =dtm.getRowCount()-1; i >= 0; i--)
					dtm.removeRow(i);
				
				if(hmap.containsKey(addName.getText()))
				{
					addNotf.setText(addName.getText()+" already exists.");
					break oo;
				}
				
				if(hmap.containsValue(Long.parseLong(addNo.getText())))
				{
					addNotf.setText(addNo.getText()+" already exists.");
					break oo;
				}
				hmap.put(addName.getText(), Long.parseLong(addNo.getText()));
				addNotf.setText("Added "+addName.getText()+".");
				addName.setText("");
				addNo.setText("");	
				
				Set<Map.Entry<String, Long>> set=hmap.entrySet();
				for(Map.Entry<String,Long> ss : set)
				{
					temp[0]=ss.getKey();
					temp[1]=ss.getValue().toString();
					dtm.addRow(temp);
					//tabbedPane.addTab( "CONTACTS", tabPanel );
				}
				
				if(!added)
				{
					tab.setModel(dtm);
					JScrollPane jsp=new JScrollPane(tab,v,h);
					tabPanel.add(jsp, BorderLayout.CENTER);
					tabbedPane.addTab( "CONTACTS", tabPanel );
				}
			}

			else if(e.getSource()==searchIt)
			{
				String name=searchName.getText();
				String num=searchNo.getText();
				if(name.equals("") && num.equals(""))
				{
					searchNotf.setText("Enter the details.");
					break oo;
				}
				if(name.length()>0 && num.length()>0)
				{
					searchNotf.setText("Enter only one detail.");
					break oo;
				}
				if(hmap.containsKey(name))
				{
					searchNotf.setText(name+" : "+hmap.get(name).toString());
					searchName.setText("");
					searchNo.setText("");
					break oo;
				}
				else
					searchNotf.setText(name+" not Found.");
				if(num.length()!=10 && num.length()>0)
				{
					searchNotf.setText("Enter 10-digit number.");
					break oo;
				}
				if(hmap.containsValue(Long.parseLong(searchNo.getText())))
				{
					Set<Map.Entry<String, Long>> set=hmap.entrySet();
					for(Map.Entry<String,Long> ss : set)
					{
						if(ss.getValue()==Long.parseLong(num))
							searchNotf.setText(ss.getKey()+" : "+ss.getValue());
					}
					searchName.setText("");
					searchNo.setText("");
					break oo;
				}
				else
					searchNotf.setText(num+" not Found.");
			}
			
			else if(e.getSource()==deleteIt)
			{
				int r=dtm.getRowCount();
				int c=dtm.getColumnCount();
				String name=deleteName.getText();
				String num=deleteNo.getText();
				if(name.equals("") && num.equals(""))
				{
					deleteNotf.setText("Enter the details.");
					break oo;
				}
				if(name.length()>0 && num.length()>0)
				{
					deleteNotf.setText("Enter only one detail.");
					break oo;
				}
				for(int x=0;x<r;x++)
					for(int y=0;y<c;y++)
					{
						if(dtm.getValueAt(x,y).toString().equals(name))
						{	
							dtm.removeRow(x);
							deleteNotf.setText("Deleted "+hmap.get(name)+".");
							hmap.remove(name);
							deleteName.setText("");
							deleteNo.setText("");
							break oo;
						}
						else if(dtm.getValueAt(x,y).toString().equals(num))
						{
							dtm.removeRow(x);
							deleteNotf.setText("Deleted "+num+".");
							Set<Map.Entry<String, Long>> set=hmap.entrySet();
							for(Map.Entry<String,Long> ss : set)
							{
								if(ss.getValue()==Long.parseLong(num))
									hmap.remove(ss.getKey());
							}
							deleteName.setText("");
							deleteNo.setText("");
							break oo;
						}
					}
				deleteNotf.setText("No such Contact.");
			}

			else if(e.getSource()==savenExit)
			{
				try
				{
					ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(f));
					oos.writeObject(m);
					oos.flush();
					mainFrame.dispose();
				}
				catch(Exception exc)
				{
					exc.printStackTrace();
				}
			}
			
			else if(e.getSource()==exit)
				mainFrame.dispose();
		}
		catch(Exception ex){}
	}
	public void focusGained(FocusEvent ee)
	{
		searchNotf.setText("");
		addNotf.setText("");
		deleteNotf.setText("");
	}
	
	public void focusLost(FocusEvent ee){}
	
    public static void main(String args[])
	{
		try
		{
			f=new File("sample.txt");
			ObjectInputStream ois=new ObjectInputStream(new FileInputStream(f));
			m=(Main)ois.readObject();
			hmap=m.hmap;
			mainFrame = new PhoneBook();
			mainFrame.setVisible(true);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
