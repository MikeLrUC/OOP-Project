package UI;

import core.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Represents the Project UI
 */
public class ProjectUI {
    private int centerIndex, projectIndex;
    private String pathName;
    private ArrayList<ResearchCenter> researchCenters;
    private JFrame frame;
    private JLabel projectLabel;
    private JDialog createTaskDialog, listerTaskDialog, displayTasksDialog, doubleListerDialog;
    private ButtonListener buttonListener;
    private ListListener listListener;
    private JButton createTaskButton, removeTaskButton, listTaskButton, updateTaskButton, addDocenteButton, addBolseiroButton, changeRespButton, totalCostButton, endButton, backButton;
    private JButton backCreateTaskButton, trueCreateTaskButton, backListerDialogButton;
    private JButton trueRemoveTaskButton;
    private JButton listAllButton, listNotStartedButton, listNotConcludedEtcButton, listConcludedButton, backListerButton, backDoubleListerButton;
    private JButton trueUpdateTaskButton, trueChangeTaskResp, trueAddDocenteButton, trueAddBolseiroButton;
    private JTextField diaInicioCreateTaskTextField, mesInicioCreateTaskTextField, anoInicioCreateTaskTextField, mesFimCreateTaskTextField, incrementoTaskTextField;
    private JComboBox<String> typeCreateTaskBox;
    private JList<Object> peopleCreateTaskList, listerList, doubleLeftList, doubleRightList;
    private ArrayList<Object> chosenTasks, chosenPeople;
    private JanelaListener windowListener;

    /**
     * Creates a ProjectUI with the chosen project in a previously chosen researchCenter
     * @param researchCenters ArrayList with the centers Data base
     * @param centerIndex int index for the current center
     * @param projectIndex int index for the current project
     * @param pathName saving path for file.obj
     */
    public ProjectUI(ArrayList<ResearchCenter> researchCenters, int centerIndex, int projectIndex, String pathName) {
        this.researchCenters = researchCenters;
        this.centerIndex = centerIndex;
        this.projectIndex = projectIndex;
        this.pathName = pathName;
        drawer();
    }

    /**
     * Draws a ProjectUI
     */
    private void drawer() {
        frame = new JFrame();
        /*Listeners*/
        windowListener = new JanelaListener();
        frame.addWindowListener(windowListener);
        buttonListener = new ButtonListener();
        listListener = new ListListener();

        /*Frame Settings*/
        frame.setTitle("Project Manager");
        frame.setSize(750, 350);
        frame.setLayout(new GridLayout(4, 1));
        frame.setLocationRelativeTo(null);

        /*Panels*/
        JPanel titlePanel = new JPanel();
        JPanel topPanel = new JPanel();
        JPanel middlePanel = new JPanel();
        JPanel bottomPanel = new JPanel();

        /*Labels*/
        projectLabel = new JLabel(
                "Projeto \"" + researchCenters.get(centerIndex).getProjects().get(projectIndex).getNome() + "\" (" +
                        +researchCenters.get(centerIndex).getProjects().get(projectIndex).getDataInicio().get(Calendar.DAY_OF_MONTH) + "/"
                        + (researchCenters.get(centerIndex).getProjects().get(projectIndex).getDataInicio().get(Calendar.MONTH) + 1) + "/"
                        + researchCenters.get(centerIndex).getProjects().get(projectIndex).getDataInicio().get(Calendar.YEAR) + " - "
                        + researchCenters.get(centerIndex).getProjects().get(projectIndex).getEtc().get(Calendar.DAY_OF_MONTH) + "/"
                        + (researchCenters.get(centerIndex).getProjects().get(projectIndex).getEtc().get(Calendar.MONTH) + 1) + "/"
                        + researchCenters.get(centerIndex).getProjects().get(projectIndex).getEtc().get(Calendar.YEAR) + ")"
        );
        projectLabel.setFont(new Font(projectLabel.getFont().getName(), Font.BOLD, 28));
        JLabel tarefasLabel = new JLabel("Tarefas:");
        JLabel pessoasLabel = new JLabel("Pessoas:");
        JLabel othersLabel = new JLabel("Outros:");

        /*Buttons*/
        //Task related Buttons
        createTaskButton = new JButton("Criar");
        createTaskButton.addActionListener(buttonListener);
        removeTaskButton = new JButton("Eliminar");
        removeTaskButton.addActionListener(buttonListener);
        listTaskButton = new JButton("Listar");
        listTaskButton.addActionListener(buttonListener);
        updateTaskButton = new JButton("Atualizar");
        updateTaskButton.addActionListener(buttonListener);

        //People related Buttons
        addDocenteButton = new JButton("Associar Docente ao Projeto");
        addDocenteButton.addActionListener(buttonListener);
        addBolseiroButton = new JButton("Associar Bolseiro ao Projeto");
        addBolseiroButton.addActionListener(buttonListener);
        changeRespButton = new JButton("Atribuir");
        changeRespButton.addActionListener(buttonListener);

        //Other Buttons
        totalCostButton = new JButton("Custo Total");
        totalCostButton.addActionListener(buttonListener);
        endButton = new JButton("TERMINAR!");
        endButton.addActionListener(buttonListener);
        backButton = new JButton("Voltar");
        backButton.addActionListener(buttonListener);

        /*Verificação se o projeto está acabado para o desenhar como tal*/
        if (researchCenters.get(centerIndex).getProjects().get(projectIndex).endProject()){
            finalizer();
        }

        /*Adding Components to main Panels*/
        titlePanel.add(projectLabel);

        topPanel.add(tarefasLabel);
        topPanel.add(createTaskButton);
        topPanel.add(removeTaskButton);
        topPanel.add(listTaskButton);
        topPanel.add(updateTaskButton);
        topPanel.add(changeRespButton);

        middlePanel.add(pessoasLabel);
        middlePanel.add(addDocenteButton);
        middlePanel.add(addBolseiroButton);


        bottomPanel.add(othersLabel);
        bottomPanel.add(totalCostButton);
        bottomPanel.add(endButton);
        bottomPanel.add(backButton);

        /*Adding main panels to the main Frame*/
        frame.add(titlePanel);
        frame.add(topPanel);
        frame.add(middlePanel);
        frame.add(bottomPanel);

        frame.setVisible(true);
    }

    /**
     * Draws a generic list see-only display for the User
     *
     * @param objects Arraylist with the objects to add to that list
     * @param title String to the User know what is being listed
     */
    private void listerDrawer(ArrayList<Object> objects, String title) {
        /*Frame Settings*/
        listerTaskDialog = new JDialog();
        listerTaskDialog.setModal(true);
        listerTaskDialog.setSize(550, 550);
        listerTaskDialog.setLocationRelativeTo(null);
        listerTaskDialog.setLayout(new BorderLayout());
        listerTaskDialog.setTitle(title);

        /*Panels*/
        JPanel topListerPanel = new JPanel();
        JPanel middleListerPanel = new JPanel();
        JPanel bottomListerPanel = new JPanel();

        /*Button*/
        backListerDialogButton = new JButton("Voltar");
        backListerDialogButton.addActionListener(buttonListener);

        /*Label*/
        JLabel titleListerLabel = new JLabel(title);
        titleListerLabel.setFont(new Font(titleListerLabel.getFont().getName(), Font.PLAIN, 20));

        /*Scroller Panel*/
        //Initialization
        DefaultListModel<Object> tasksListModel = new DefaultListModel<>();
        //Adding objects to the list
        if (objects != null) {
            tasksListModel.addAll(objects);
        }
        //Adding list to the JList and Settings
        listerList = new JList<>(tasksListModel);
        listerList.setFixedCellWidth(450);
        listerList.setFixedCellHeight(40);
        listerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listerList.addListSelectionListener(listListener);
        //Adding the Jlist to the JScrollPane
        JScrollPane listerScroller = new JScrollPane(listerList);

        /*Adding the components to the main Panels*/
        topListerPanel.add(titleListerLabel);
        middleListerPanel.add(listerScroller);
        bottomListerPanel.add(backListerDialogButton);

        /*Adding the main Panels to the main Frame*/
        listerTaskDialog.add(topListerPanel, BorderLayout.NORTH);
        listerTaskDialog.add(middleListerPanel, BorderLayout.CENTER);
        listerTaskDialog.add(bottomListerPanel, BorderLayout.SOUTH);
    }

    /**
     * Draws a generic double list see-only display for the User
     *
     * @param left Arraylist with the objects to add to the left list
     * @param right Arraylist with the objects to add to the Right list
     * @param titleLabel String to the User know what is being listed
     * @param leftLabel String Information of the left list to the User
     * @param rightLabel String Information of the right list to the User
     */
    private void doubleListerDrawer(ArrayList<Object> left, ArrayList<Object> right, String titleLabel, String leftLabel, String rightLabel) {
        doubleListerDialog = new JDialog();
        /*Settings*/
        doubleListerDialog.setModal(true);
        doubleListerDialog.setSize(950, 600);
        doubleListerDialog.setLocationRelativeTo(null);
        doubleListerDialog.setLayout(new BorderLayout());
        doubleListerDialog.setTitle("Trocar Responsável");

        /*Panels*/
        JPanel topPanel = new JPanel();
        JPanel leftPanel = new JPanel(new BorderLayout());
        JPanel rightPanel = new JPanel(new BorderLayout());
        JPanel bottomPanel = new JPanel();

        /*Lists*/
        DefaultListModel<Object> leftObjs = new DefaultListModel<>();
        DefaultListModel<Object> rightObjs = new DefaultListModel<>();
        if ((left != null) && (right != null)) {
            leftObjs.addAll(left);
            rightObjs.addAll(right);
        }

        doubleLeftList = new JList<>(leftObjs);
        doubleLeftList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        doubleLeftList.setFixedCellWidth(450);
        doubleLeftList.setFixedCellHeight(40);
        doubleRightList = new JList<>(rightObjs);
        doubleRightList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        doubleRightList.setFixedCellWidth(450);
        doubleRightList.setFixedCellHeight(40);

        /*Scrollers*/
        JScrollPane leftScroll = new JScrollPane(doubleLeftList);
        JScrollPane rightScroll = new JScrollPane(doubleRightList);

        /*Labels*/
        JLabel doubleTitleLabel = new JLabel(titleLabel);
        doubleTitleLabel.setFont(new Font(doubleTitleLabel.getFont().getName(), Font.BOLD, 22));
        JLabel doubleLeftLabel = new JLabel(leftLabel);
        JLabel doubleRightLabel = new JLabel(rightLabel);

        /*Button*/
        backDoubleListerButton = new JButton("Voltar");
        backDoubleListerButton.addActionListener(buttonListener);

        /*Adding Components to Panels*/
        topPanel.add(doubleTitleLabel);

        leftPanel.add(doubleLeftLabel, BorderLayout.NORTH);
        leftPanel.add(leftScroll, BorderLayout.CENTER);

        rightPanel.add(doubleRightLabel, BorderLayout.NORTH);
        rightPanel.add(rightScroll, BorderLayout.CENTER);

        bottomPanel.add(backDoubleListerButton);

        /*Adding Panels to main Dialog frame*/
        doubleListerDialog.add(topPanel, BorderLayout.NORTH);
        doubleListerDialog.add(leftPanel, BorderLayout.WEST);
        doubleListerDialog.add(rightPanel, BorderLayout.EAST);
        doubleListerDialog.add(bottomPanel, BorderLayout.SOUTH);
    }

    /**
     * Drawer responsible for the create task menu
     */
    private void createTaskDrawer() {
        createTaskDialog = new JDialog();
        createTaskDialog.setModal(true);
        createTaskDialog.setSize(550, 400);
        createTaskDialog.setLocationRelativeTo(null);
        createTaskDialog.setLayout(new GridLayout(1, 2));
        createTaskDialog.setTitle("Criar Tarefa");

        /*Panels*/
        //Left Panels
        JPanel leftCreateTaskPanel = new JPanel(new GridLayout(4, 1));
        JPanel informationCreateTaskPanel = new JPanel(new BorderLayout());
        JPanel titleCreateTaskPanel = new JPanel(new FlowLayout());
        JPanel fieldFillCreateTaskPanel = new JPanel(new FlowLayout());
        JPanel inicioCreateTaskPanel = new JPanel(new GridLayout(2, 1));
        JPanel topInicioCreateTaskPanel = new JPanel(new FlowLayout());
        JPanel bottomInicioCreateTaskPanel = new JPanel(new FlowLayout());
        JPanel fimCreateTaskPanel = new JPanel(new GridLayout(2, 1));
        JPanel topFimCreateTaskPanel = new JPanel(new FlowLayout());
        JPanel bottomFimCreateTaskPanel = new JPanel(new FlowLayout());
        JPanel typeCreateTaskPanel = new JPanel(new FlowLayout());

        //Right Panels
        JPanel rightCreateTaskPanel = new JPanel(new BorderLayout());
        JPanel buttonerCreateTaskPanel = new JPanel();

        /*Labels*/
        //Left Labels
        JLabel titleCreateTaskLabel = new JLabel("Criar Tarefa");
        titleCreateTaskLabel.setFont(new Font(titleCreateTaskLabel.getFont().getName(), Font.BOLD, 30));
        JLabel fieldFillCreateTaskLabel = new JLabel("Preencha os seguintes campos:");
        fieldFillCreateTaskLabel.setFont(new Font(fieldFillCreateTaskLabel.getFont().getName(), Font.BOLD, 15));
        JLabel inicioCreateTaskLabel = new JLabel("Data de Início:");
        inicioCreateTaskLabel.setFont(new Font(inicioCreateTaskLabel.getFont().getName(), Font.BOLD, 14));
        JLabel diaInicioCreateTaskLabel = new JLabel("Dia:");
        JLabel mesInicioCreateTaskLabel = new JLabel("Mês:");
        JLabel anoInicioCreateTaskLabel = new JLabel("Ano:");
        JLabel etcCreateTaskLabel = new JLabel("Data estimada de Conclusão:");
        etcCreateTaskLabel.setFont(new Font(etcCreateTaskLabel.getFont().getName(), Font.BOLD, 14));
        JLabel mesFimCreateTaskLabel = new JLabel("Mêses depois da Data de Início:");
        JLabel typeCreateTaskLabel = new JLabel("Tipo:");

        //Right Labels
        JLabel choosePersonCreateTaskLabel = new JLabel("Escolha uma Pessoa:");

        /*Buttons*/
        backCreateTaskButton = new JButton("Voltar");
        backCreateTaskButton.addActionListener(buttonListener);
        trueCreateTaskButton = new JButton("Criar Tarefa");
        trueCreateTaskButton.addActionListener(buttonListener);
        trueCreateTaskButton.setEnabled(false);

        /*TextFields*/
        diaInicioCreateTaskTextField = new JTextField(2);
        mesInicioCreateTaskTextField = new JTextField(2);
        anoInicioCreateTaskTextField = new JTextField(2);

        mesFimCreateTaskTextField = new JTextField(2);

        /*Combo box*/
        typeCreateTaskBox = new JComboBox<>();
        typeCreateTaskBox.addItem("Documentação");
        typeCreateTaskBox.addItem("Design");
        typeCreateTaskBox.addItem("Desenvolvimento");

        /*List*/
        DefaultListModel<Object> peopleCreateTaskObjs = new DefaultListModel<>();
        peopleCreateTaskObjs.addAll(researchCenters.get(centerIndex).getProjects().get(projectIndex).getPessoas());
        peopleCreateTaskList = new JList<>(peopleCreateTaskObjs);
        peopleCreateTaskList.addListSelectionListener(listListener);
        peopleCreateTaskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        /*Setting up left side of the menu*/
        //Title and information
        titleCreateTaskPanel.add(titleCreateTaskLabel);
        fieldFillCreateTaskPanel.add(fieldFillCreateTaskLabel);

        informationCreateTaskPanel.add(titleCreateTaskPanel, BorderLayout.NORTH);
        informationCreateTaskPanel.add(fieldFillCreateTaskPanel, BorderLayout.SOUTH);

        //adding inicioLabels and TextFields
        topInicioCreateTaskPanel.add(inicioCreateTaskLabel);
        bottomInicioCreateTaskPanel.add(diaInicioCreateTaskLabel);
        bottomInicioCreateTaskPanel.add(diaInicioCreateTaskTextField);
        bottomInicioCreateTaskPanel.add(mesInicioCreateTaskLabel);
        bottomInicioCreateTaskPanel.add(mesInicioCreateTaskTextField);
        bottomInicioCreateTaskPanel.add(anoInicioCreateTaskLabel);
        bottomInicioCreateTaskPanel.add(anoInicioCreateTaskTextField);

        inicioCreateTaskPanel.add(topInicioCreateTaskPanel);
        inicioCreateTaskPanel.add(bottomInicioCreateTaskPanel);

        //adding fim Labels and TextFields
        topFimCreateTaskPanel.add(etcCreateTaskLabel);
        bottomFimCreateTaskPanel.add(mesFimCreateTaskLabel);
        bottomFimCreateTaskPanel.add(mesFimCreateTaskTextField);

        fimCreateTaskPanel.add(topFimCreateTaskPanel);
        fimCreateTaskPanel.add(bottomFimCreateTaskPanel);

        //adding type task ComboBox
        typeCreateTaskPanel.add(typeCreateTaskLabel);
        typeCreateTaskPanel.add(typeCreateTaskBox);

        //adding the left subpanels to the main left panel
        leftCreateTaskPanel.add(informationCreateTaskPanel);
        leftCreateTaskPanel.add(inicioCreateTaskPanel);
        leftCreateTaskPanel.add(fimCreateTaskPanel);
        leftCreateTaskPanel.add(typeCreateTaskPanel);

        /*Setting up right side of the menu*/
        //adding buttons to the buttoner
        buttonerCreateTaskPanel.add(backCreateTaskButton);
        buttonerCreateTaskPanel.add(trueCreateTaskButton);

        //adding the right components to the main right panel
        rightCreateTaskPanel.add(choosePersonCreateTaskLabel, BorderLayout.NORTH);
        rightCreateTaskPanel.add(peopleCreateTaskList, BorderLayout.CENTER);
        rightCreateTaskPanel.add(buttonerCreateTaskPanel, BorderLayout.SOUTH);

        /*Adding left and right panels to the main JDialog*/
        createTaskDialog.add(leftCreateTaskPanel);
        createTaskDialog.add(rightCreateTaskPanel);

        createTaskDialog.setVisible(true);
    }

    /**
     * Connection from FrontEnd to BackEnd for creating a task in the current project
     */
    private void taskCreater() {
        int diaInicio, mesInicio, anoInicio, mesFim, boxIndex;
        Calendar inicio, etc;
        Pessoa responsavel;
        Task tarefa;
        try {
            diaInicio = Integer.parseInt(diaInicioCreateTaskTextField.getText());
            mesInicio = Integer.parseInt(mesInicioCreateTaskTextField.getText());
            anoInicio = Integer.parseInt(anoInicioCreateTaskTextField.getText());
            mesFim = Integer.parseInt(mesFimCreateTaskTextField.getText());

            if ((1 <= diaInicio && diaInicio <= 31) && (1 <= mesInicio && mesInicio <= 12) && (anoInicio > 0) && (mesFim > 0)) {
                inicio = new GregorianCalendar();
                inicio.set(Calendar.DAY_OF_MONTH, diaInicio);
                inicio.set(Calendar.MONTH, mesInicio - 1);
                inicio.set(Calendar.YEAR, anoInicio);

                etc = new GregorianCalendar();
                etc.set(Calendar.DAY_OF_MONTH, diaInicio);
                etc.set(Calendar.MONTH, (mesInicio - 1) + mesFim);
                etc.set(Calendar.YEAR, anoInicio);

                boxIndex = typeCreateTaskBox.getSelectedIndex();
                if (boxIndex == 0) {
                    tarefa = new Documentation(inicio, etc);
                } else if (boxIndex == 1) {
                    tarefa = new Design(inicio, etc);
                } else {
                    tarefa = new Development(inicio, etc);
                }
                if (researchCenters.get(centerIndex).getProjects().get(projectIndex).getDataInicio().before(inicio)) {
                    responsavel = (Pessoa) peopleCreateTaskList.getSelectedValue();
                    if (researchCenters.get(centerIndex).getProjects().get(projectIndex).assignResp(responsavel, tarefa)) {
                        tarefa.setResponsavel(responsavel);
                        researchCenters.get(centerIndex).getProjects().get(projectIndex).addTask(tarefa);
                        JOptionPane.showMessageDialog(null, "Tarefa Criada Com Sucesso", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Verifique se:\n  As Datas são Compatíveis\n  A Pessoa não está sobrecarregada", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "A Tarefa não está contida na duração do Projeto", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Introduza Valores Válidos", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            createTaskDialog.setVisible(false);
            createTaskDialog.dispose();
            JOptionPane.showMessageDialog(null, "Introduza Valores válidos!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Drawer responsible for the remove task menu
     */
    private void removeTaskDialogDrawer() {
        listerDrawer(researchCenters.get(centerIndex).getProjects().get(projectIndex).getTasksNotConcluded(), "Remover Tarefas");
        /*Refactoring of the bottom panel to add a Button*/
        JPanel bottomPanel = new JPanel();

        trueRemoveTaskButton = new JButton("Eliminar Tarefa");
        trueRemoveTaskButton.setEnabled(false);
        trueRemoveTaskButton.addActionListener(buttonListener);

        bottomPanel.add(backListerDialogButton);
        bottomPanel.add(trueRemoveTaskButton);

        listerTaskDialog.add(bottomPanel, BorderLayout.SOUTH);

        listerTaskDialog.setVisible(true);
    }

    /**
     * Connection from FrontEnd to BackEnd for removing a task in the current project
     */
    private void taskRemover() {
        Task tarefa = (Task) listerList.getSelectedValue();
        listerTaskDialog.setVisible(false);
        listerTaskDialog.dispose();
        if ((tarefa != null) && researchCenters.get(centerIndex).getProjects().get(projectIndex).removeTask(tarefa)) {
            JOptionPane.showMessageDialog(null, "Tarefa Removida Com Sucesso", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Erro a remover Tarefa", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Drawer responsible of sub-menu for list choosing
     */
    private void displayTasksDrawer() {
        displayTasksDialog = new JDialog();
        /*Settings*/
        displayTasksDialog.setModal(true);
        displayTasksDialog.setSize(400, 400);
        displayTasksDialog.setLocationRelativeTo(null);
        displayTasksDialog.setLayout(new BorderLayout());
        displayTasksDialog.setTitle("Listar Tarefas");

        /*Panels*/
        JPanel topPanel = new JPanel(new FlowLayout());
        JPanel middlePanel = new JPanel(new GridLayout(4, 1));
        JPanel bottomPanel = new JPanel(new FlowLayout());

        /*Buttons*/
        listAllButton = new JButton("Todas");
        listAllButton.addActionListener(buttonListener);
        listNotStartedButton = new JButton("Não Iniciadas");
        listNotStartedButton.addActionListener(buttonListener);
        listConcludedButton = new JButton("Concluídas");
        listConcludedButton.addActionListener(buttonListener);
        listNotConcludedEtcButton = new JButton("Não Concluídas na Data Estimada");
        listNotConcludedEtcButton.addActionListener(buttonListener);
        backListerButton = new JButton("Voltar");
        backListerButton.addActionListener(buttonListener);

        /*Label*/
        JLabel titleDisplayTasksLabel = new JLabel("Listar");
        titleDisplayTasksLabel.setFont(new Font(titleDisplayTasksLabel.getFont().getName(), Font.BOLD, 20));

        /*Adding Components to Panels*/
        topPanel.add(titleDisplayTasksLabel);

        middlePanel.add(listAllButton);
        middlePanel.add(listNotStartedButton);
        middlePanel.add(listConcludedButton);
        middlePanel.add(listNotConcludedEtcButton);

        bottomPanel.add(backListerButton);

        /*Adding Panels to the Dialog frame*/
        displayTasksDialog.add(topPanel, BorderLayout.NORTH);
        displayTasksDialog.add(middlePanel, BorderLayout.CENTER);
        displayTasksDialog.add(bottomPanel, BorderLayout.SOUTH);

        displayTasksDialog.setVisible(true);
    }

    /**
     * Drawer responsible of the task udpating menu
     */
    private void updateTaskDialogDrawer() {
        listerDrawer(researchCenters.get(centerIndex).getProjects().get(projectIndex).getTasksNotConcluded(), "Atualizar Tarefas");
        /*Panels*/
        JPanel bottomPanel = new JPanel(new BorderLayout());
        JPanel bottomTopPanel = new JPanel(new FlowLayout());
        JPanel bottomBottomPanel = new JPanel(new FlowLayout());

        /*Label*/
        JLabel incrementoLabel = new JLabel("Incremento: ");

        /*TextField*/
        incrementoTaskTextField = new JTextField(10);

        /*Button*/
        trueUpdateTaskButton = new JButton("Atualizar Tarefa");
        trueUpdateTaskButton.addActionListener(buttonListener);

        /*Adding components to Panels*/
        bottomTopPanel.add(incrementoLabel);
        bottomTopPanel.add(incrementoTaskTextField);

        bottomBottomPanel.add(backListerDialogButton);
        bottomBottomPanel.add(trueUpdateTaskButton);

        /*Adding Sub-Panels to Main Panels and into the Dialog frame*/
        bottomPanel.add(bottomTopPanel, BorderLayout.NORTH);
        bottomPanel.add(bottomBottomPanel, BorderLayout.SOUTH);

        listerTaskDialog.add(bottomPanel, BorderLayout.SOUTH);

        listerTaskDialog.setVisible(true);

    }

    /**
     * Connection from FrontEnd to BackEnd for updating a task in the current project
     */
    private void taskUpdater() {
        Task task;
        try {
            int value = Integer.parseInt(incrementoTaskTextField.getText());
            if ((task = (Task) listerList.getSelectedValue()) != null) {
                if (researchCenters.get(centerIndex).getProjects().get(projectIndex).updateTaskPercentage(task, value)) {
                    listerTaskDialog.setVisible(false);
                    listerTaskDialog.dispose();
                    JOptionPane.showMessageDialog(null, "Valor Incrementado com Sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    listerTaskDialog.setVisible(false);
                    listerTaskDialog.dispose();
                    JOptionPane.showMessageDialog(null, "Valor Não Válido!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Escolha uma Tarefa!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            listerTaskDialog.setVisible(false);
            listerTaskDialog.dispose();
            JOptionPane.showMessageDialog(null, "Introduza Valores Válidos!", "Erro", JOptionPane.ERROR_MESSAGE);
        }

    }

    /**
     * Drawer responsible for the changing task responsible menu
     */
    private void changeRespDrawer() {
        doubleListerDrawer(chosenPeople, chosenTasks, "Trocar Responsável", "Escolha uma Pessoa:", "Escolha uma Tarefa:");
        /*Refactoring of the bottom panel to add a Button*/
        JPanel bottomPanel = new JPanel();

        trueChangeTaskResp = new JButton("Alterar");
        trueChangeTaskResp.addActionListener(buttonListener);

        bottomPanel.add(backDoubleListerButton);
        bottomPanel.add(trueChangeTaskResp);

        doubleListerDialog.add(bottomPanel, BorderLayout.SOUTH);

        doubleListerDialog.setVisible(true);
    }

    /**
     * Connection from FrontEnd to BackEnd for changing task responsible in the current project
     */
    private void respChanger() {
        Pessoa pessoa;
        Task tarefa;
        if (((pessoa = (Pessoa) doubleLeftList.getSelectedValue()) != null) && ((tarefa = (Task) doubleRightList.getSelectedValue()) != null)) {
            if (pessoa.equals(tarefa.getResponsavel())) {
                JOptionPane.showMessageDialog(null, "A Tarefa já está atribuída a essa pessoa!", "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                if (researchCenters.get(centerIndex).getProjects().get(projectIndex).changeTaskResp(pessoa, tarefa)) {
                    doubleListerDialog.setVisible(false);
                    doubleListerDialog.dispose();
                    JOptionPane.showMessageDialog(null, "Tarefa trocada com Sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "A Pessoa em causa está sobrecarregada ou o contrato acaba antes do periodo de execução da tarefa", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecione uma Pessoa e uma Tarefa!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Drawer responsible for the docente adder menu
     *
     * @param docentes Arraylist with the center docentes except the ones associated in the current project
     */
    private void addDocenteDrawer(ArrayList<Object> docentes) {
        listerDrawer(docentes, "Associar Docente");
        /*Refactoring of the bottom panel to add a Button*/
        JPanel bottomPanel = new JPanel();
        trueAddDocenteButton = new JButton("Associar");
        trueAddDocenteButton.addActionListener(buttonListener);
        trueAddDocenteButton.setEnabled(false);

        bottomPanel.add(backListerDialogButton);
        bottomPanel.add(trueAddDocenteButton);

        listerTaskDialog.add(bottomPanel, BorderLayout.SOUTH);

        listerTaskDialog.setVisible(true);
    }

    /**
     * Connection from FrontEnd to BackEnd for adding a Docente in the current project
     */
    private void docenteAdder() {
        Docente docente;
        if ((docente = ((Docente) listerList.getSelectedValue())) != null) {
            if (researchCenters.get(centerIndex).getProjects().get(projectIndex).addDocente(docente)) {
                listerTaskDialog.setVisible(false);
                listerTaskDialog.dispose();
                JOptionPane.showMessageDialog(null, "Docente Associado com Sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                listerTaskDialog.setVisible(false);
                listerTaskDialog.dispose();
                JOptionPane.showMessageDialog(null, "Docente não adicionado (Já no projeto)!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Drawer responsible for the adding a bolseiro menu
     *
     * @param docentes Arraylist with the docentes in the current project
     * @param bolseiros Arraylist with the bolseiros in the currente project
     */
    private void addBolseiroDrawer(ArrayList<Object> docentes, ArrayList<Object> bolseiros) {
        doubleListerDrawer(docentes, bolseiros, "Associar Bolseiro", "Escolha um Docente:", "Escolha um bolseiro:");
        /*Refactoring of the bottom panel to add a Button*/
        JPanel bottomPanel = new JPanel();

        trueAddBolseiroButton = new JButton("Associar");
        trueAddBolseiroButton.addActionListener(buttonListener);

        bottomPanel.add(backDoubleListerButton);
        bottomPanel.add(trueAddBolseiroButton);

        doubleListerDialog.add(bottomPanel, BorderLayout.SOUTH);

        doubleListerDialog.setVisible(true);
    }

    /**
     * Connection from FrontEnd to BackEnd for adding a Bolseiro in the current project
     */
    private void bolseiroAdder() {
        Bolseiro bolseiro;
        Docente docente;
        if ((((bolseiro = (Bolseiro) doubleRightList.getSelectedValue()) != null) && (bolseiro.getCusto() == 1200))) { //Se for um doutorado
            if ((docente = (Docente) doubleLeftList.getSelectedValue()) == null) {
                if(!researchCenters.get(centerIndex).getProjects().get(projectIndex).getBolseiros().contains(bolseiro)){ //Se o doutorado já tiver sido adicionado
                    researchCenters.get(centerIndex).getProjects().get(projectIndex).addBolseiro(bolseiro);
                    doubleListerDialog.setVisible(false);
                    doubleListerDialog.dispose();
                    JOptionPane.showMessageDialog(null, "Doutorado Associado Com sucesso", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Doutorado Já Associado!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Um Doutorado não Precisa de Orientador!", "Erro", JOptionPane.ERROR_MESSAGE);

            }
        } else { // Caso contrário é um Estudante
            if (((docente = (Docente) doubleLeftList.getSelectedValue()) != null) && (bolseiro = (Bolseiro) doubleRightList.getSelectedValue()) != null) {
                ((Estudante) bolseiro).setOrientador(docente);

                if (!docente.equals(((Estudante) bolseiro).getOrientador())){
                    researchCenters.get(centerIndex).getProjects().get(projectIndex).addBolseiro(bolseiro);
                    doubleListerDialog.setVisible(false);
                    doubleListerDialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "O Docente que escolheu já é Orientador do bolseiro!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
                JOptionPane.showMessageDialog(null, "Estudante Associado Com sucesso", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Selecione:\nUm Docente e um Estudante\nUm Doutorado", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Responsible for making sure a project is ended by blocking some buttons forever and changing the etc to the end date in the main label
     */
    private void finalizer(){
        projectLabel.setText("Projeto \"" + researchCenters.get(centerIndex).getProjects().get(projectIndex).getNome() + "\" (" +
                + researchCenters.get(centerIndex).getProjects().get(projectIndex).getDataInicio().get(Calendar.DAY_OF_MONTH) + "/"
                + (researchCenters.get(centerIndex).getProjects().get(projectIndex).getDataInicio().get(Calendar.MONTH) + 1) + "/"
                + researchCenters.get(centerIndex).getProjects().get(projectIndex).getDataInicio().get(Calendar.YEAR) + " - "
                + researchCenters.get(centerIndex).getProjects().get(projectIndex).getDataFim().get(Calendar.DAY_OF_MONTH) + "/"
                + (researchCenters.get(centerIndex).getProjects().get(projectIndex).getDataFim().get(Calendar.MONTH) + 1) + "/"
                + researchCenters.get(centerIndex).getProjects().get(projectIndex).getDataFim().get(Calendar.YEAR) + ")"

        );
        createTaskButton.setEnabled(false);
        removeTaskButton.setEnabled(false);
        updateTaskButton.setEnabled(false);
        changeRespButton.setEnabled(false);
        addDocenteButton.setEnabled(false);
        addBolseiroButton.setEnabled(false);
        endButton.setEnabled(false);
        endButton.setText("CONCLUÍDO");
    }

    /**
     * Represents a ButtonListener that implements the ActionListener class
     */
    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == backButton) { //Botão para voltar ao menu do Centro
                frame.setVisible(false);
                frame.dispose();
                new CenterUI(researchCenters, centerIndex, pathName);
            } else if (e.getSource() == createTaskButton) { //Botão para abrir o menu para criar uma task
                createTaskDrawer();
            } else if (e.getSource() == trueCreateTaskButton) { //Botão para criar uma task
                createTaskDialog.setVisible(false);
                createTaskDialog.dispose();
                taskCreater();
            } else if (e.getSource() == backCreateTaskButton) { //Botão para sair do menu para criar uma task
                createTaskDialog.setVisible(false);
                createTaskDialog.dispose();
            } else if (e.getSource() == removeTaskButton) { //Botão botão para abrir o menu para remover uma tarefa
                removeTaskDialogDrawer();
            } else if (e.getSource() == trueRemoveTaskButton) { //Botão para remover uma tarefa
                taskRemover();
            } else if (e.getSource() == listTaskButton) { //Botão para abrir o menu the listagens
                displayTasksDrawer();
            } else if (e.getSource() == backListerButton) { //Botão para sair do menu de listagens
                displayTasksDialog.setVisible(false);
                displayTasksDialog.dispose();
            } else if (e.getSource() == backDoubleListerButton) {   //Botão para sair dos menus de adicionar bolseiro, adiconar docente e alterar responsavel por uma tarefa
                doubleListerDialog.setVisible(false);
                doubleListerDialog.dispose();
            } else if (e.getSource() == listAllButton) {    //Botão para listar todas as tarefas
                chosenTasks = new ArrayList<>(researchCenters.get(centerIndex).getProjects().get(projectIndex).getTasks());
                listerDrawer(chosenTasks, "Todas as Tarefas");
                listerTaskDialog.setVisible(true);
            } else if (e.getSource() == listNotStartedButton) { //Botão para listar as tarefas não começadas
                chosenTasks = new ArrayList<>(researchCenters.get(centerIndex).getProjects().get(projectIndex).getTasksNotStarted());
                listerDrawer(chosenTasks, "Tarefas Não Iniciadas");
                listerTaskDialog.setVisible(true);
            } else if (e.getSource() == listConcludedButton) { //Botão para listar as tarefas concluídas
                chosenTasks = new ArrayList<>(researchCenters.get(centerIndex).getProjects().get(projectIndex).getTasksConcluded());
                listerDrawer(chosenTasks, "Tarefas Concluídas");
                listerTaskDialog.setVisible(true);
            } else if (e.getSource() == listNotConcludedEtcButton) { //Botão para listar as tarefas não concluídas antes da data estimada
                chosenTasks = new ArrayList<>(researchCenters.get(centerIndex).getProjects().get(projectIndex).getTasksNotConcludedInEtc());
                listerDrawer(chosenTasks, "Tarefas Não Concluídas na Data Estimada");
                listerTaskDialog.setVisible(true);
            } else if (e.getSource() == backListerDialogButton) { //Botão para sair dos 4 possíveis submenus de listagem + remover tarefas + atualizar tarefas
                listerTaskDialog.setVisible(false);
                listerTaskDialog.dispose();
            } else if (e.getSource() == updateTaskButton) {     //Botão para abrir o menu para atualizar uma tarefa
                updateTaskDialogDrawer();
            } else if (e.getSource() == changeRespButton) {     //Botão para abrir o menu para mudar o responsavel de uma tarefa
                chosenTasks = new ArrayList<>(researchCenters.get(centerIndex).getProjects().get(projectIndex).getTasksNotConcluded());
                chosenPeople = new ArrayList<>(researchCenters.get(centerIndex).getProjects().get(projectIndex).getPessoas());
                changeRespDrawer();
            } else if (e.getSource() == trueUpdateTaskButton) { //Botão para atualizar uma tarefa
                taskUpdater();
            } else if (e.getSource() == trueChangeTaskResp) {   //Botão para mudar o responsável de uma tarefa
                respChanger();
            } else if (e.getSource() == totalCostButton) {      //Botão para mostar o custo total do projeto
                JOptionPane.showMessageDialog(null, "Custo Total do Projeto: " + researchCenters.get(centerIndex).getProjects().get(projectIndex).getCost() + " €.", "Custo Total", JOptionPane.INFORMATION_MESSAGE);
            } else if (e.getSource() == addDocenteButton) { //Botão para abrir o menu para associar docentes
                chosenPeople = new ArrayList<>(researchCenters.get(centerIndex).getDocentesNotInProject(researchCenters.get(centerIndex).getProjects().get(projectIndex)));
                addDocenteDrawer(chosenPeople);
            } else if (e.getSource() == trueAddDocenteButton) { //Botão para associar docente
                docenteAdder();
            } else if (e.getSource() == addBolseiroButton) {//Botão para abrir o menu para associar bolseiros
                chosenPeople = new ArrayList<>(researchCenters.get(centerIndex).getProjects().get(projectIndex).getDocentes());
                addBolseiroDrawer(chosenPeople, researchCenters.get(centerIndex).getBolseirosNotAssociatedAndProject(researchCenters.get(centerIndex).getProjects().get(projectIndex)));
            } else if (e.getSource() == trueAddBolseiroButton) {//Botão para associar bolseiros
                bolseiroAdder();
            } else if (e.getSource() == endButton){ //Botão para terminar o projeto
                Calendar diaAtual = new GregorianCalendar();
                if (JOptionPane.showConfirmDialog(null, "Tem a certeza?", "Terminar Projeto", JOptionPane.YES_NO_OPTION) == 0) {
                    if (researchCenters.get(centerIndex).getProjects().get(projectIndex).endProject() ){
                        finalizer();
                    } else if(JOptionPane.showConfirmDialog(null, "O Projeto não está em Condições para Terminar:\n       - Tarefas por Concluir \n       - A Data Estimada de Conclusão ainda não foi alcançada\n\n Deseja terminá-lo na mesma? ", "Terminar Projeto", JOptionPane.YES_NO_OPTION) == 0){
                        researchCenters.get(centerIndex).getProjects().get(projectIndex).setAcabado(true);
                        researchCenters.get(centerIndex).getProjects().get(projectIndex).setDataFim(diaAtual);
                        finalizer();
                    }
                }
            }
        }
    }

    /**
     * Represents a ListListener that implements the ListSelectionListener class
     */
    private class ListListener implements ListSelectionListener {
        /**
         * Enable/ Disable of buttons if a list value is selected, or not, respectively
         * @param e ListSelectionEvent choosing an element in a given list
         */
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (e.getSource() == peopleCreateTaskList) {
                if (peopleCreateTaskList.getSelectedIndex() == -1) {
                    trueCreateTaskButton.setEnabled(false);
                } else {
                    trueCreateTaskButton.setEnabled(true);
                }
            } else if (e.getSource() == listerList) {
                if (listerList.getSelectedIndex() == -1) {
                    if (trueRemoveTaskButton != null) {
                        trueRemoveTaskButton.setEnabled(false);
                    }
                    if (trueAddDocenteButton != null) {
                        trueAddDocenteButton.setEnabled(false);
                    }
                } else {
                    if (trueRemoveTaskButton != null) {
                        trueRemoveTaskButton.setEnabled(true);
                    }
                    if (trueAddDocenteButton != null) {
                        trueAddDocenteButton.setEnabled(true);
                    }
                }
            }
        }
    }

    /**
     * Represents a WindowListener
     */
    private class JanelaListener implements WindowListener {

        @Override
        public void windowOpened(WindowEvent e) {

        }
        /**
         * Saves the current state of the program if the frame is closed
         *
         * @param e WindowEvent Window Closing
         */
        @Override
        public void windowClosing(WindowEvent e) {
            Booter booter = new Booter();
            if (booter.saveObjectFile(pathName, researchCenters)) {
                System.out.println("Ficheiro Objeto guardado com Sucesso");
            } else {
                System.out.println("Erro ao Guardar Ficheiro Objeto");
            }
            frame.dispose();
            System.exit(0);
        }

        @Override
        public void windowClosed(WindowEvent e) {

        }

        @Override
        public void windowIconified(WindowEvent e) {

        }

        @Override
        public void windowDeiconified(WindowEvent e) {

        }

        @Override
        public void windowActivated(WindowEvent e) {

        }

        @Override
        public void windowDeactivated(WindowEvent e) {

        }
    }
}
