package com.adam.generate;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.jdesktop.beansbinding.ELProperty;

/**
 * @author __USER__
 */
public class Pdm2Java extends javax.swing.JFrame {

    /** Creates new form T */
    public Pdm2Java() {
        initComponents();
    }

    // GEN-BEGIN:initComponents
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        pdmPath = new javax.swing.JTextField();
        choosePdmButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        outputPath = new javax.swing.JTextField();
        chooseOutputButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        projctNameCn = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        projctNameEn = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        basePackage = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        encoding = new javax.swing.JTextField();

        jLabel7 = new javax.swing.JLabel();
        framework = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        worldcase = new javax.swing.JComboBox();
        generateButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(java.awt.SystemColor.controlHighlight);

        jLabel1.setForeground(new java.awt.Color(0, 82, 232));
        jLabel1.setText("PDM\u6587\u4ef6:");

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, pdm2JavaConfig,
                ELProperty.create("${pdmPath}"), pdmPath, org.jdesktop.beansbinding.BeanProperty.create("text"));

        org.jdesktop.beansbinding.Binding projectNameZhBinding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, pdm2JavaConfig,
                ELProperty.create("${projectNameZh}"), projctNameCn,
                org.jdesktop.beansbinding.BeanProperty.create("text"));

        org.jdesktop.beansbinding.Binding projectNameEnBinding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, pdm2JavaConfig,
                ELProperty.create("${projectNameEn}"), projctNameEn,
                org.jdesktop.beansbinding.BeanProperty.create("text"));

        org.jdesktop.beansbinding.Binding basePackageBinding = org.jdesktop.beansbinding.Bindings
                .createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, pdm2JavaConfig,
                        ELProperty.create("${basePackage}"), basePackage,
                        org.jdesktop.beansbinding.BeanProperty.create("text"));

        org.jdesktop.beansbinding.Binding encodingBinding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, pdm2JavaConfig,
                ELProperty.create("${encoding}"), encoding, org.jdesktop.beansbinding.BeanProperty.create("text"));

        org.jdesktop.beansbinding.Binding frameworkBinding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, pdm2JavaConfig,
                ELProperty.create("${framework}"), framework,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));

        org.jdesktop.beansbinding.Binding projectOutputPathBinding = org.jdesktop.beansbinding.Bindings
                .createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, pdm2JavaConfig,
                        ELProperty.create("${projectOutputPath}"), outputPath,
                        org.jdesktop.beansbinding.BeanProperty.create("text"));

        org.jdesktop.beansbinding.Binding worldcaseBinding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, pdm2JavaConfig,
                ELProperty.create("${worldcase}"), worldcase,
                org.jdesktop.beansbinding.BeanProperty.create("selectedIndex"));
        bindingGroup.addBinding(binding);
        // bindingGroup.addBinding(worldcaseBinding);
        bindingGroup.addBinding(projectOutputPathBinding);
        // bindingGroup.addBinding(frameworkBinding);
        bindingGroup.addBinding(encodingBinding);
        bindingGroup.addBinding(basePackageBinding);
        bindingGroup.addBinding(projectNameEnBinding);
        bindingGroup.addBinding(projectNameZhBinding);
        choosePdmButton.setText("\u9009\u62e9PDM");
        choosePdmButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                choosePdmButtonMouseClicked(evt);
            }
        });

        jLabel2.setForeground(new java.awt.Color(0, 82, 232));
        jLabel2.setText("\u751f\u6210\u8def\u5f84:");

        chooseOutputButton.setText("\u9009\u62e9\u8def\u5f84");
        chooseOutputButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chooseOutputButtonMouseClicked(evt);
            }
        });
        chooseOutputButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chooseOutputButtonActionPerformed(evt);
            }
        });

        jLabel3.setForeground(new java.awt.Color(0, 82, 232));
        jLabel3.setText("\u9879\u76ee\u4e2d\u6587\u540d:");

        jLabel4.setForeground(new java.awt.Color(0, 82, 232));
        jLabel4.setText("\u9879\u76ee\u82f1\u6587\u540d:");

        jLabel5.setForeground(new java.awt.Color(0, 82, 232));
        jLabel5.setText("\u9879\u76ee\u57fa\u7840\u5305\u540d:");

        basePackage.setText("com.wireless.platform");

        jLabel6.setForeground(new java.awt.Color(0, 82, 232));
        jLabel6.setText("\u7f16\u7801:");

        encoding.setText("UTF-8");

        jLabel7.setForeground(new java.awt.Color(0, 82, 232));
        jLabel7.setText("\u9879\u76ee\u6846\u67b6:");

        framework.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "(spring/mybatis)", "(spring/ibatis)",
                "(spring/struts2/hibernate)" }));
        framework.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                frameworkActionPerformed(evt);
            }
        });

        jLabel8.setForeground(new java.awt.Color(0, 82, 232));
        jLabel8.setText("\u5927\u5c0f\u5199\u9009\u9879:");

        worldcase.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "全大写", "全小写", "大小写混合" }));
        worldcase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                worldcaseActionPerformed(evt);
            }
        });

        generateButton.setText("\u751f\u6210\u9879\u76ee");
        generateButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                generateButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout
                .setHorizontalGroup(jPanel1Layout
                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(
                                jPanel1Layout
                                        .createSequentialGroup()
                                        .addGap(38, 38, 38)
                                        .addGroup(
                                                jPanel1Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel2)
                                                        .addGroup(
                                                                jPanel1Layout
                                                                        .createSequentialGroup()
                                                                        .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addGroup(
                                                                                jPanel1Layout
                                                                                        .createParallelGroup(
                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                        .addComponent(jLabel4)
                                                                                        .addComponent(jLabel3)
                                                                                        .addComponent(jLabel5)
                                                                                        .addComponent(
                                                                                                jLabel6,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                57,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                        .addComponent(
                                                                                                jLabel7,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                57,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                        .addComponent(jLabel8))))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(
                                                jPanel1Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(pdmPath, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                649, Short.MAX_VALUE)
                                                        .addComponent(outputPath, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                649, Short.MAX_VALUE)
                                                        .addComponent(projctNameCn,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 367,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(projctNameEn,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 367,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(basePackage,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 367,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(encoding, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                367, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(framework,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 188,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(worldcase,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 188,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(53, 53, 53)
                                        .addGroup(
                                                jPanel1Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(choosePdmButton).addComponent(chooseOutputButton))
                                        .addGap(42, 42, 42))
                        .addGroup(
                                jPanel1Layout
                                        .createSequentialGroup()
                                        .addGap(351, 351, 351)
                                        .addComponent(generateButton, javax.swing.GroupLayout.DEFAULT_SIZE, 82,
                                                Short.MAX_VALUE).addGap(515, 515, 515)));
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                        jPanel1Layout
                                .createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addGroup(
                                        jPanel1Layout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel1)
                                                .addComponent(choosePdmButton)
                                                .addComponent(pdmPath, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(
                                        jPanel1Layout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel2)
                                                .addGroup(
                                                        jPanel1Layout
                                                                .createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.BASELINE)
                                                                .addComponent(outputPath,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(chooseOutputButton)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(
                                        jPanel1Layout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel3)
                                                .addComponent(projctNameCn, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(
                                        jPanel1Layout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel4)
                                                .addComponent(projctNameEn, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(
                                        jPanel1Layout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel5)
                                                .addComponent(basePackage, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(
                                        jPanel1Layout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel6)
                                                .addComponent(encoding, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(
                                        jPanel1Layout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel7)
                                                .addComponent(framework, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(
                                        jPanel1Layout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel8)
                                                .addComponent(worldcase, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)).addGap(50, 50, 50)
                                .addComponent(generateButton).addContainerGap(35, Short.MAX_VALUE)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addContainerGap()));

        bindingGroup.bind();

        pack();
    }// </editor-fold>
     // GEN-END:initComponents

    private void generateButtonMouseClicked(java.awt.event.MouseEvent evt) {
        pdm2JavaConfig.setFramework(framework.getSelectedIndex());
        pdm2JavaConfig.setWorldcase(worldcase.getSelectedIndex());
        System.out.println(pdm2JavaConfig);
        AutoGenerate autoGenerate = new AutoGenerate(pdm2JavaConfig);
        boolean flag = autoGenerate.generate();
        if (flag) {
            JOptionPane.showMessageDialog(this, "生成成功");
        } else {
            JOptionPane.showMessageDialog(this, "生成失败");
        }
    }

    private void choosePdmButtonMouseClicked(java.awt.event.MouseEvent evt) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setApproveButtonText("确定");
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            pdmPath.setText(fileChooser.getSelectedFile().getPath());
        }
    }

    private void chooseOutputButtonMouseClicked(java.awt.event.MouseEvent evt) {

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setApproveButtonText("确定");
        fileChooser.setFileSelectionMode(1);
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            outputPath.setText(fileChooser.getSelectedFile().getPath());
        }
    }

    private void chooseOutputButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void worldcaseActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void frameworkActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    // UIManager
                    // .setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Pdm2Java pdm2Java = new Pdm2Java();
                pdm2Java.setVisible(true);
            }
        });
    }

    // GEN-BEGIN:variables
    // Variables declaration - do not modify
    private javax.swing.JTextField basePackage;
    private javax.swing.JButton chooseOutputButton;
    private javax.swing.JButton choosePdmButton;
    private javax.swing.JTextField encoding;
    private javax.swing.JComboBox framework;
    private javax.swing.JButton generateButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField outputPath;
    private javax.swing.JTextField pdmPath;
    private javax.swing.JTextField projctNameCn;
    private javax.swing.JTextField projctNameEn;
    private javax.swing.JComboBox worldcase;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
    private Pdm2JavaConfig pdm2JavaConfig = new Pdm2JavaConfig();

    public Pdm2JavaConfig getPdm2JavaConfig() {
        return pdm2JavaConfig;
    }

    public void setPdm2JavaConfig(Pdm2JavaConfig pdm2JavaConfig) {
        this.pdm2JavaConfig = pdm2JavaConfig;
    }
}
