import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ToDoListGui extends JFrame implements ActionListener {
    private JPanel taskPanel,taskComponentPanel;

    public ToDoListGui(){
        super("To Do List Application");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(CommonCostants.GUI_SIZE);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

       addGuiComponent();
    }
    private void addGuiComponent(){
        JLabel bannerLable = new JLabel("To Do List");
        bannerLable.setFont(createFont("resources/LEMONMILK-Light.otf",36f));
        bannerLable.setBounds(
                (CommonCostants.GUI_SIZE.width - bannerLable.getPreferredSize().width)/2,
                    15,
                CommonCostants.BANNER_SIZE.width,
                CommonCostants.BANNER_SIZE.height
        );


        taskPanel =new JPanel();

        taskComponentPanel =new JPanel();
        taskComponentPanel.setLayout(new BoxLayout(taskComponentPanel,BoxLayout.Y_AXIS));
        taskPanel.add(taskComponentPanel);

        JScrollPane scrollPane=new JScrollPane(taskPanel);
        scrollPane.setBounds(8,70,CommonCostants.TASKPANEL_SIZE.width,CommonCostants.TASKPANEL_SIZE.height);
        scrollPane.setBorder(BorderFactory.createLoweredBevelBorder());
        scrollPane.setMaximumSize(CommonCostants.TASKPANEL_SIZE);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        //changing the scroll bar speed
        JScrollBar verticalScrollBar=scrollPane.getVerticalScrollBar();
        verticalScrollBar.setUnitIncrement(20);



        JButton addTaskButton =new JButton("Add Task");
        addTaskButton.setFont(createFont("resources/LEMONMILK-Light.otf",18f));
        addTaskButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addTaskButton.setBounds(-5, CommonCostants.GUI_SIZE.height-88,
                CommonCostants.ADDTASK_BUTTON_SIZE.width,CommonCostants.ADDTASK_BUTTON_SIZE.height
                );

        addTaskButton.addActionListener(this);

        this.getContentPane().add(bannerLable);
        this.getContentPane().add(scrollPane);
        this.getContentPane().add(addTaskButton);
    }

    private Font createFont(String resource, float size){
        String filePath =getClass().getClassLoader().getResource(resource).getPath();

        if(filePath.contains("%20")){
            filePath =getClass().getClassLoader().getResource(resource).getPath()
                    .replaceAll("%20"," ");

        }

        try{
            File customFontFile = new File(filePath);
            Font customFont = Font.createFont(Font.TRUETYPE_FONT,customFontFile).deriveFont(size);
            return customFont;
        }catch(Exception e){
            System.out.println(" "+e);
        }
        return null;

    }

    @Override
    public void actionPerformed(ActionEvent e){
        String command=e.getActionCommand();
        if(command.equalsIgnoreCase("Add task")){
            TaskComponent taskComponent=new TaskComponent(taskComponentPanel);
            taskComponentPanel.add(taskComponent);

            if(taskComponentPanel.getComponentCount()>1){
                TaskComponent previousTask = (TaskComponent) taskComponentPanel.getComponent(
                        taskComponentPanel.getComponentCount() -2);
                previousTask.getTaskField().setBackground(null);
            }


            taskComponent.getTaskField().requestFocus();
            repaint();
            revalidate();

        }
    }
}
