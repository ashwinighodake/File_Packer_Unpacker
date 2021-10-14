import java.lang.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.plaf.basic.BasicBorders;
import java.awt.event.*;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader ;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.List;
import java.lang.System;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Class Name:Login
//function:ClockHome,pack,validate
//Description:In this class GUI of Login frame is designed
//Parent class:Template
//interface:ActionListener,Runnable
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

class Login extends Template implements ActionListener,Runnable
{
    JButton SUBMIT;
    JLabel label1,label2,label3,TopLabel;
    final JTextField text1,text2;
    private static int attempt=3;

    Login()
    {
        TopLabel=new JLabel();
        TopLabel.setHorizontalAlignment(SwingConstants.CENTER);
        TopLabel.setText("Packer Unpacker : Login");
        TopLabel.setForeground(Color.BLUE);
        TopLabel.setFont(new Font("Serif",Font.BOLD,18));

        Dimension topsize=TopLabel.getPreferredSize();
        TopLabel.setBounds(50,40,topsize.width,topsize.height);
        _header.add(TopLabel);

        label1=new JLabel();
        label1.setText("Username:");
        label1.setForeground(Color.white);

        Dimension size=label1.getPreferredSize();
        
        label1.setBounds(50,135,size.width,size.height);
        label1.setHorizontalAlignment(SwingConstants.CENTER);

        text1=new JTextField(15);
        Dimension tsize=text1.getPreferredSize();
        text1.setBounds(200,135,tsize.width,tsize.height);

        text1.setToolTipText("Enter Username");

        label2=new JLabel();
        label2.setText("Password:");
        label2.setBounds(50,175,size.width,size.height);
        label2.setForeground(Color.white);
        label2.setHorizontalAlignment(SwingConstants.CENTER);

        text2=new JPasswordField(15);
        text2.setBounds(200,175,tsize.width,tsize.height);
        text2.setToolTipText("Enter Password");

        text2.addFocusListener(new FocusListener()
        {
            public void focusGained(FocusEvent e)
            {

            }
            public void focusLost(FocusEvent e)
            {
                label3.setText("");
            }
        });
        
        SUBMIT=new JButton("SUBMIT");
        SUBMIT.setHorizontalAlignment(SwingConstants.CENTER);

        Dimension ssize=SUBMIT.getPreferredSize();
        SUBMIT.setBounds(50,220,ssize.width,ssize.height);

        label3=new JLabel();
        label3.setText("");

        Dimension l3size=label3.getPreferredSize();

        label3.setForeground(Color.RED);
        label3.setBounds(50,250,l3size.width,l3size.height);

        Thread t=new Thread(this);
        t.start();

        _content.add(label1);
        _content.add(text1);

        _content.add(label2);
        _content.add(text2);

        _content.add(label3);
        _content.add(SUBMIT);

        pack();
        validate();

        ClockHome();
        setVisible(true);
        this.setSize(600,600);
     //   this.setLayout(null);
        this.setResizable(false);
        setLocationRelativeTo(null);
        SUBMIT.addActionListener(this);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Function name:Validate
    // Description: I this function we are validating whether the password
    // Input parameter:String,String
    // output:boolean
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    public boolean Validate(String Username,String Password)
    {
        if((Username.length()<8)||(Password.length()<8))
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////
    // function name:actionperformed
    // Description : This function is for Action listening after button click of exit,minimize,submit
    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    
    public void actionPerformed(ActionEvent e)
    {
        String value1=text1.getText();
        String value2=text2.getText();

        if(e.getSource()==exit)
        {
            this.setVisible(false);
            System.exit(0);
        }
        if(e.getSource()==minimize)
        {
            this.setState(this.ICONIFIED);
        }
        if(e.getSource()==SUBMIT)
        {
            if(Validate(value1,value2)==false)
            {
                text1.setText("");
                text2.setText("");
                JOptionPane.showMessageDialog(this,"Short username","Packer Unpacker",JOptionPane.ERROR_MESSAGE);
            }
            if(value1.equals("Ashwini_Ghodake") && value2.equals("Ashwini_Ghodake"))
            {
                NextPage page=new NextPage(value1);
                this.setVisible(false);
                page.pack();
                page.setVisible(true);
                page.setSize(500,500);                
            }
            else
            {
                attempt--;

                if(attempt == 0 )
                {
                    JOptionPane.showMessageDialog(this,"Number of attempts finished","Packer Unpacker",JOptionPane.ERROR_MESSAGE);
                    this.dispose();
                    System.exit(0);
                }
                JOptionPane.showMessageDialog(this,"Incorrect login or password","ERROR",JOptionPane.ERROR_MESSAGE);

            }
        }
        
    }
    public void run()
    {           
        for(;;)
        {
            if(text2.isFocusOwner())
            {
                if(Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK))
                {
                    text2.setToolTipText("Warning:CAPS LOCK is on");
                }
                else
                {
                    text2.setToolTipText("");
                }

                if((text2.getText()).length() < 8 )
                {
                    label3.setText("Weak Password");
                }
                else
                {
                    label3.setText("");
                }
            }
        }
    }

}
////////////////////////////////////////////////////////////////////////////////
// Class name: Main
// Description : this is the main class from where our project start
////////////////////////////////////////////////////////////////////////////////

class Main 
{
    public static void main(String args[])
    {
        System.out.println("Jay Ganesh");
        try 
        {
            Login frame=new Login();
            frame.setVisible(true);
            
        } 
        catch (Exception e) 
        {
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
    }    
}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Class name: Clocklabel
// Function: constructor ,actionperformed
// Description: This class is for displaying the date ,time & day
/////////////////////////////////////////////////////////////////////////////////////////////////////////
class ClockLabel extends JLabel implements ActionListener
{
    String type;
    SimpleDateFormat sdf;

    public ClockLabel(String type)
    {
        this.type=type;
        setForeground(Color.green);

        switch(type)
        {
            case "date": sdf = new SimpleDateFormat("MMMM dd YYYY");
                         setFont(new Font("sans-serif",Font.PLAIN,12));
                         setHorizontalAlignment(SwingConstants.LEFT);
                         break;

            case "time": sdf = new SimpleDateFormat("hh:mm:ss a");
                         setFont(new Font("sans-serif",Font.PLAIN,40));
                         setHorizontalAlignment(SwingConstants.CENTER);
                         break;
            
            case "day": sdf = new SimpleDateFormat("EEEE ");
                         setFont(new Font("sans-serif",Font.PLAIN,16));
                         setHorizontalAlignment(SwingConstants.RIGHT);
                         break;
            default: sdf= new SimpleDateFormat();
                     break;

        }
        Timer t=new Timer(1000,this);
        t.start();
    }
    public void actionPerformed(ActionEvent ae)
    {
        Date d=new Date();
        setText(sdf.format(d));
    }
}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Class Name: Template
// parent Class: JFrame
// interface :Serializable,Actionlistener
// Description : This class creates basic template of our project
// funnction: Constructor,Clock,CloseAndMin,ClockHome,actionperformed
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
class Template extends JFrame implements Serializable,ActionListener
{
    JPanel _header;
    JPanel _content;
    JPanel _top;

    ClockLabel dayLabel;
    ClockLabel timeLabel;
    ClockLabel dateLabel;

    JButton minimize,exit;
    public Template()
    {
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        GridBagLayout grid=new GridBagLayout();
        setLayout(grid);

        _top=new JPanel();
        _top.setBackground(Color.LIGHT_GRAY);
        _top.setLayout(null);
        getContentPane().add(_top,new GridBagConstraints(0,0,1,1,1,5,GridBagConstraints.BASELINE,GridBagConstraints.BOTH,new Insets(0,0,0,0),0,0));

        _header=new JPanel();
        _header.setBackground(Color.white);
        _header.setLayout(null);
        getContentPane().add(_header,new GridBagConstraints(0,1,1,1,1,20,GridBagConstraints.BASELINE,GridBagConstraints.BOTH,new Insets(0,0,0,0),0,0));

        _content=new JPanel();
        _content.setBackground(new Color(0,50,120));
        _content.setLayout(null);

        JScrollPane jsp=new JScrollPane(_content,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        getContentPane().add(jsp,new GridBagConstraints(0,2,1,1,1,75,GridBagConstraints.BASELINE,GridBagConstraints.BOTH,new Insets(0,0,0,0),0,0));

        setTitle("Packer-Unpacker");
        Clock();
        CloseAndMin();
    }
    void CloseAndMin()
    {
        minimize=new JButton("-");
        minimize.setBackground(Color.LIGHT_GRAY);
        minimize.setBounds(MAXIMIZED_HORIZ,0,45,20);

        exit=new JButton("x");
        exit.setHorizontalAlignment(SwingConstants.CENTER);
        exit.setBackground(Color.LIGHT_GRAY);
        exit.setHorizontalTextPosition(0);
        exit.setBounds(MAXIMIZED_HORIZ+45,0,45,20);

        _top.add(minimize);
        _top.add(exit);

        exit.addActionListener(this);
        minimize.addActionListener(this);
    }
    public void actionPerformed(ActionEvent ae)
    {
        if(ae.getSource() == exit)
        {
            this.setVisible(false);
            System.exit(0);
        }
        if(ae.getSource()==minimize)
        {
            setState(JFrame.ICONIFIED);
        }
    }
    void Clock()
    {
        dateLabel=new ClockLabel("date");
        timeLabel=new ClockLabel("time");
        dayLabel=new ClockLabel("day");

        dateLabel.setForeground(Color.blue);
        timeLabel.setForeground(Color.blue);
        dayLabel.setForeground(Color.blue);
 
        dateLabel.setFont(new Font("Century",Font.BOLD,15));
        dateLabel.setBounds(800,-40,200,100);

    
        timeLabel.setFont(new Font("Century",Font.BOLD,15));   
        timeLabel.setBounds(760,-15,200,100);
        dayLabel.setFont(new Font("Century",Font.BOLD,15));   
        dayLabel.setBounds(700,10,200,100);
        
        _header.add(dateLabel);
        _header.add(timeLabel);
        _header.add(dayLabel);
    }
    void ClockHome()
    {
        dateLabel=new ClockLabel("date");
        timeLabel=new ClockLabel("time");
        dayLabel=new ClockLabel("day");

        dateLabel.setForeground(Color.BLUE);
        timeLabel.setForeground(Color.BLUE);
        dayLabel.setForeground(Color.BLUE);

        dateLabel.setFont(new Font("Century",Font.BOLD,15));
        dateLabel.setBounds(300,-40,200,100);

        timeLabel.setFont(new Font("Century",Font.BOLD,15));
        timeLabel.setBounds(260,-10,200,100);

        dayLabel.setFont(new Font("Century",Font.BOLD,15));
        dayLabel.setBounds(200,20,200,100);

        _header.add(dateLabel);
        _header.add(timeLabel);
        _header.add(dayLabel);
    }
}
/////////////////////////////////////////////////////////////////////////////////////////////////////
// Class: NextPage
// Parent Class: Template
// interface: ActionListener
// Description:This class is for next page after login page
// function name:constructor,actionperformed
//////////////////////////////////////////////////////////////////////////////////////////////////////
class NextPage extends Template implements ActionListener
{
    JLabel label;
    JButton pack,unpack;

    NextPage(String value)
    {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        label=new JLabel("Welcome:"+value);
        Dimension size=label.getPreferredSize();
        label.setBounds(40,50,size.width,size.height);
        label.setFont(new Font("Century",Font.BOLD,17));
        label.setForeground(Color.blue);

        pack=new JButton("Pack Files");
        Dimension bsize=pack.getPreferredSize();
        pack.setBounds(100,100,bsize.width,bsize.height);
        pack.addActionListener(this);

        unpack=new JButton("Unpack Files");
        Dimension b2size=unpack.getPreferredSize();
        unpack.setBounds(300,100,b2size.width,b2size.height);
        unpack.addActionListener(this);

        _header.add(label);
        _content.add(pack);
        _content.add(unpack);

        ClockHome();
        this.setSize(600,600);
        this.setResizable(false);
        this.setVisible(true);

    }
    public void actionPerformed(ActionEvent ae)
    {
        if(ae.getSource()==exit)
        {
            this.setVisible(false);
            System.exit(0);
        }
        if(ae.getSource()==minimize)
        {
            this.setState(this.ICONIFIED);
        }
        if(ae.getSource()==pack)
        {
            this.setVisible(false);
            try
            {
               PackFront obj=new PackFront();

            }
            catch(Exception e)
            {}
        }
        if(ae.getSource()==unpack)
        {
            this.setVisible(false);
            UnpackFront obj=new UnpackFront();

        }
    }
}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Class:PackFront
// Parent class:Template
// interface:ActionListener
// Description: This class is for designing packing page
// function:actionperformed
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

class PackFront extends Template implements ActionListener
{
    JButton SUBMIT,PREVIOUS;
    JLabel label1,label2,title;
    final JTextField text1,text2;

    public PackFront()
    {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        title=new JLabel("Marvellous Packing Portal");
        Dimension size=title.getPreferredSize();
        title.setBounds(40,50,size.width+60,size.height);
        title.setFont(new Font("Century",Font.BOLD,17));
        title.setForeground(Color.blue);

        label1=new JLabel();
        label1.setText("Directory name");
        label1.setForeground(Color.white);
        label1.setBounds(350,50,size.width,size.height);

        text1=new JTextField(15);
        Dimension tsize=text1.getPreferredSize();
        text1.setBounds(500,50,tsize.width,tsize.height);
        text1.setToolTipText("Enter the name of directory:");

        label2=new JLabel();
        label2.setText("Destination File name:");
        label2.setForeground(Color.white);
        label2.setBounds(350,100,size.width+60,size.height);
        
        text2=new JTextField(15);
        text2.setBounds(500,100,tsize.width,tsize.height);
        text2.setToolTipText("Enter Destination File name");

        SUBMIT=new JButton("SUBMIT");
        Dimension bsize=SUBMIT.getPreferredSize();
        SUBMIT.setBounds(350,200,bsize.width,bsize.height);
        SUBMIT.addActionListener(this);

        PREVIOUS=new JButton("PREVIOUS");
        Dimension b2size=PREVIOUS.getPreferredSize();
        PREVIOUS.setBounds(500,200,b2size.width,b2size.height);
        PREVIOUS.addActionListener(this);

        _header.add(title);
        _content.add(label1);
        _content.add(label2);
        _content.add(text1);
        _content.add(text2);
        _content.add(SUBMIT);
        _content.add(PREVIOUS);

        this.setSize(1000,400);
        this.setResizable(false);
        this.setVisible(true);
        text1.requestFocusInWindow();
    }
    public void actionPerformed(ActionEvent ae)
    {
        if(ae.getSource()==exit)
        {
            this.setVisible(false);
            System.exit(0);
        }
        if(ae.getSource()==minimize)
        {
            this.setState(this.ICONIFIED);
        }
        if(ae.getSource()==SUBMIT)
        {
            try
            {
                Packer obj=new Packer(text1.getText(),text2.getText());
                this.dispose();
                NextPage t=new NextPage("MarvellousAdmin");
            }
            catch(Exception e){}
        }
        if(ae.getSource()==PREVIOUS)
        {
            this.setVisible(false);
            this.dispose();
            NextPage t=new NextPage("MarvellousAdmin");
        }
    }
}
//////////////////////////////////////////////////////////////////////////////////////////////
// Class:InvalidFileException
// Parent Class: Exception
// Descrption: this class is for handling Error
///////////////////////////////////////////////////////////////////////////////////////////////

class InvalidFileException extends Exception
{
    public InvalidFileException(String str)
    {
        super(str);
    }
}
////////////////////////////////////////////////////////////////////////////////////////////////////
// Class name: UnpackFront
// Parent Class:Template
// interface :ActionListener
// Description:This class is designed a unpack frame
//////////////////////////////////////////////////////////////////////////////////////////////////////

class UnpackFront extends Template implements ActionListener
{
    JButton SUBMIT,PREVIOUS;
    JLabel label1,label2,title;
    final JTextField text1;

    public UnpackFront()
    {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        title=new JLabel("Unpacking Portal");
        Dimension size=title.getPreferredSize();
        title.setBounds(40,50,size.width+60,size.height);
        title.setFont(new Font("Century",Font.BOLD,17));
        title.setForeground(Color.blue);

        label1=new JLabel();
        label1.setText("File Name");
        label1.setForeground(Color.white);
        label1.setBounds(350,50,size.width,size.height);

        text1=new JTextField(15);
        Dimension tsize=text1.getPreferredSize();
        text1.setBounds(500,50,tsize.width,tsize.height);
        text1.setToolTipText("Enter name of Directory");

        SUBMIT=new JButton("Extract Here");
        Dimension bsize=SUBMIT.getPreferredSize();
        SUBMIT.setBounds(350,200,bsize.width,bsize.height);
        SUBMIT.addActionListener(this);

        PREVIOUS=new JButton("PREVIOUS");
        Dimension b2size=PREVIOUS.getPreferredSize();
        PREVIOUS.setBounds(500,200,b2size.width,b2size.height);
        PREVIOUS.addActionListener(this);

        _header.add(title);
        _content.add(label1);
        _content.add(text1);
        _content.add(SUBMIT);
        _content.add(PREVIOUS);

        this.setSize(1000,400);
        this.setResizable(false);
        this.setVisible(true);
        text1.requestFocusInWindow();

    }
    public void actionPerformed(ActionEvent ae)
    {
        if(ae.getSource() == exit)
        {
            this.setVisible(false);
            System.exit(0);
        }
        if(ae.getSource() == minimize)
        {
            this.setState(this.ICONIFIED);
        }
        if(ae.getSource() == SUBMIT)
        {
            try
            {
                Unpack obj=new Unpack(text1.getText());
                this.dispose();
                NextPage t=new NextPage("admin");
            }
            catch(InvalidFileException obj)
            {
                this.setVisible(false);
                this.dispose();

                JOptionPane.showMessageDialog(this,"Invalid Packed File","ERROR",JOptionPane.ERROR_MESSAGE);
                NextPage t=new NextPage("MarvellousAdmin");
            }
            catch(Exception e){}

        }
        if(ae.getSource()==PREVIOUS)
        {
            this.setVisible(false);
            this.dispose();
            NextPage t=new NextPage("admin");
        }
    }
}
///////////////////////////////////////////////////////////////////////////////////////////////////////////
// Class:Packer
// Description: This class is for packing the files in one folder
// function:listAllFiles,Pack
///////////////////////////////////////////////////////////////////////////////////////////////////////////

class Packer
{
    FileOutputStream outstream=null;
    String ValidExt[]={".txt",".c",".java",".cpp"};

    public Packer(String src,String Dest) throws Exception
    {
        String Magic="Marvellous11";
        byte arr[]=Magic.getBytes();
        File outfile=new File(Dest);
        
        File infile=null;
        outstream=new FileOutputStream(Dest);
        outstream.write(arr,0,arr.length);

        File folder=new File(src);
        System.setProperty("user.dir",src);
        listAllFiles(src);
    }
    //////////////////////////////////////////////////////////////////////////////////////////
    // Function name:listAllFile
    // Description: In this function we are listing the all file in arraylist
    // Input parameter:String
    //////////////////////////////////////////////////////////////////////////////////////////

    public void listAllFiles(String path)
    {
        try
        (Stream<Path>paths = Files.walk(Paths.get(path)))
        {
            paths.forEach(filePath->
            {
                if(Files.isRegularFile(filePath))
                {
                    try
                    {
                        String name=filePath.getFileName().toString();
                        String ext=name.substring(name.lastIndexOf("."));

                        List <String> list=Arrays.asList(ValidExt);
                        if(list.contains(ext))
                        {
                            File file=new File(filePath.getFileName().toString());
                            Pack(file.getAbsolutePath());
                        }
                    }
                    catch(Exception e)
                    {
                        System.out.println(e);
                    }
                }
            });

        }
        catch(IOException e)
        {
            System.out.println(e);
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Function :Pack 
    // Description : This functon is actually pack the files in one folder
    // Input: filePath
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void Pack(String filePath)
    {
        FileInputStream instream=null;
        try
        {
            byte []buffer=new byte[1024];
            int length;
            byte temp[]=new byte[100];
            File fobj=new File(filePath);
            String Header=filePath+" "+fobj.length();
            for(int i=Header.length();i<100;i++)
            {
                Header +=" ";
            }
            temp=Header.getBytes();
            instream=new FileInputStream(filePath);
            outstream.write(temp,0,temp.length);

            while((length=instream.read(buffer))>0)
            {
                outstream.write(buffer,0,length);
            }
            instream.close();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
}
///////////////////////////////////////////////////////////////////////////////////////////
// Class : Unpack
// Input:filePath
// function: unpack
// Description : this function unpack the pack file by checking the magic number
////////////////////////////////////////////////////////////////////////////////////////////
class Unpack
{
    FileOutputStream outstream=null;
    public Unpack(String src) throws Exception
    {
       unpack(src);
    }
    public void unpack(String filePath) throws Exception
    {
        try
        {
            FileInputStream instream=new FileInputStream(filePath);
            byte header[]=new byte[100];
            int length=0;

            byte Magic[]=new byte[12];
            instream.read(Magic,0,Magic.length);

            String Magicstr=new String(Magic);
            
            if(!Magicstr.equals("Marvellous11"))
            {
                throw new InvalidFileException("Invalid packed file format");
            }

            while((length=instream.read(header,0,100)) > 0 )
            {
                String str = new String(header);
    
                String ext=str.substring(str.lastIndexOf("/"));
                ext=ext.substring(1);
                
                String words[]=ext.split("\\s");
                String filename=words[0];
                int size=Integer.parseInt(words[1]);
                byte arr[]=new byte[size];
                instream.read(arr,0,size);
               
                FileOutputStream fout=new FileOutputStream(filename);
                fout.write(arr,0,size);
                
            }
        }
        catch(InvalidFileException obj)
        {
            throw new InvalidFileException("Invalid packed file Format");
        }
        catch(Exception e)
        {}
    }

}
