package com.ui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.util.Constants;
import com.util.SizeComparator;
import com.vo.FileVO;
import com.yahoo.platform.yui.compressor.CssCompressor;
import com.yahoo.platform.yui.compressor.JavaScriptCompressor;

import worker.Worker;
import worker.WorkerUtility;

public class SpiderUI implements ActionListener{
	JTextPane panelFrame404;
	final JFrame frame;
	final JFrame settingsFrame;
	final JFrame logFrame;
	JFrame logFrame404;
	JFrame logResourcesFrame;
	final JFrame HTUFrame;
	final JTabbedPane jtabs;
	final JPanel jpHtmlPanel;
	final JPanel jpConfigPanel;
	JPanel jpCSSPanel;
	final JPanel jpJSPanel;
	final JPanel jpMinPanel;
	final JPanel jp404Panel;
	JPanel crawlPanel;
	JPanel inputHtmlPanel;
	JButton jbBrowseHtml;
	JButton jbBrowseBComp;
	JButton jbCrawlHtml;
	JButton jbReset;
	JButton jbSettingOK;
	JButton jbHowToUse;
	JButton jbSettings;
	JButton jb404Find;
	JButton jb404Export;
	JTextField jtInputHtml;
	JTextField jtInputBComp;
	JTextField jtInputFileTypes;
	JTextField jt404;
	JLabel statusLabel;
	JLabel statusLogPathLabel;
	JLabel statusLogMinLabel;
	JLabel statusHTMLPathLabel;
	JLabel workspaceLabel;
	JLabel bCompDirLabel;
	JLabel htmlFoundLabel;
	JLabel status404Label;
	JLabel bCompLabel;
	JLabel workspaceInputLabel;
	JLabel bCompInputLabel;
	JButton jbUnusedHtml;
	JButton jbExportHtml;
	JButton jbCSSGenerate[];
	JButton jbJSGenerate[];
	JButton jbMinGenerate[];
	JButton jbMinJsGenerate[];
	GridLayout frameLayout;
	GridLayout tempLayout;
	JTextField jtCSSSetting;
	JTextField jtJSSetting;
	
	JButton jbUnusedResources;
	JButton jbExportUnusedResources;
	
	JPanel statusLogPathPanel;
	JPanel statusLogHtmlPanel;
	JPanel statusLog404;
	JPanel statusLogMinPanel;
	File fileWorkSpaceDir;
	File fileBCompDir;
	File sourceWorkSpaceDir;
	File sourceBCompDir;
	File testFile;
	File testBCompFile;
	File source;
	boolean bCompFound=false;
	
	JScrollPane scrollFrameCSS;
	JScrollPane scrollFrameJS;
	JScrollPane scrollFrameMinify;
	JScrollPane scrollFrame404;
	JScrollPane scrollFrameResources;
	
	StyledDocument docRes;
	SimpleAttributeSet keyWordRes;
	StyledDocument doc404;
	SimpleAttributeSet keyWord404;
	StyledDocument doc;
	SimpleAttributeSet keyWord;
	StyledDocument docHTU;
	SimpleAttributeSet keyWordHTU;
	JProgressBar progressBar;
	JProgressBar progressBarHTML;
	JProgressBar progressBarUnused;
	JProgressBar progress404;
	JTextPane panelFrameResources;
	
	InputStream is;
	Image imageGear;
	Image cssGear;
	Image jsGear;
	Image minGear;
	Image spiderGear;
	Image bcGear;
	Image resetGear;
	Image qn1Gear;
	Image Gear404;
	Image logo;
	Image gearGear;
	ImageIcon spiderImg;
	ImageIcon bcImg;
	ImageIcon gearImg;
	ImageIcon resetImg;
	ImageIcon qn1Img;
	ImageIcon Img404;
	ImageIcon ImgLogo;
	JCheckBox cbxDupSel;
	
	
	GridLayout oneTwoGrid;
	GridLayout oneThreeGrid;
	
	final JFileChooser fileDialogWorkSpaceDir;
	final JFileChooser fileDialogBCompDir;
	String strBComp="";
	
	public static void main(String[] args) {
		new SpiderUI();
	}//end of main
	
	public SpiderUI(){
		try {
			is = SpiderUI.class.getResourceAsStream("/images/fs.png");
			imageGear = ImageIO.read(is);
			is = SpiderUI.class.getResourceAsStream("/images/css.png");
			cssGear = ImageIO.read(is);
			is = SpiderUI.class.getResourceAsStream("/images/js.png");
			jsGear = ImageIO.read(is);
			is = SpiderUI.class.getResourceAsStream("/images/compress.png");
			minGear = ImageIO.read(is);
			is = SpiderUI.class.getResourceAsStream("/images/spider1.png");
			spiderGear= ImageIO.read(is);
			is = SpiderUI.class.getResourceAsStream("/images/gear.png");
			gearGear= ImageIO.read(is);
			is = SpiderUI.class.getResourceAsStream("/images/bc.png");
			bcGear= ImageIO.read(is);
			is = SpiderUI.class.getResourceAsStream("/images/reset1.png");
			resetGear= ImageIO.read(is);
			is = SpiderUI.class.getResourceAsStream("/images/qn21.png");
			qn1Gear= ImageIO.read(is);
			is = SpiderUI.class.getResourceAsStream("/images/404.png");
			Gear404= ImageIO.read(is);
			
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
			//UIManager.getLookAndFeelDefaults().put("defaultFont", new Font("Comic Sans MS", Font.PLAIN, Constants.intDefaultFont));
			UIManager.getLookAndFeelDefaults().put("ScrollBar.minimumThumbSize", new Dimension(30, 30));
		}catch (Exception localException) {
		}
		oneTwoGrid = new GridLayout(1,2);
		oneThreeGrid= new GridLayout(1,3);
		
		progressBar = new JProgressBar(0, 100);
	    progressBar.setValue(0);
	    progressBar.setStringPainted(true);
		
	    progressBarHTML=new JProgressBar(0, 100);
	    progressBarHTML.setValue(0);
	    progressBarHTML.setStringPainted(true);
	    
	    progressBarUnused=new JProgressBar(0, 100);
	    progressBarUnused.setValue(0);
	    progressBarUnused.setStringPainted(true);
	    
	    progress404=new JProgressBar(0, 100);
	    progress404.setValue(0);
	    progress404.setStringPainted(true);
	    
		this.jtabs = new JTabbedPane();
		frameLayout = new GridLayout(6,1);
		jpHtmlPanel=new JPanel();
		jpHtmlPanel.setLayout(frameLayout);
		
		jbSettings=new JButton("CSS/JS Linebreak Settings");
		jbSettings.setToolTipText("Set Linebreak Settings for CSS/JS. More info available on 'How to Use' on main tab");
		
		GridLayout htmlLayout = new GridLayout(7,1);
		jpConfigPanel=new JPanel();
		jpConfigPanel.setLayout(htmlLayout);
		
		jpCSSPanel=new JPanel();
		
		jpJSPanel=new JPanel();
		jpMinPanel=new JPanel();
		jp404Panel=new JPanel();
		
		JPanel temp = new JPanel();
		
		GridLayout htmlLayout1=new GridLayout(1,3);
		JPanel htmlPanel1 = new JPanel();
		htmlPanel1.setLayout(htmlLayout1);
		
		temp.setLayout(htmlLayout1);
		
		jbUnusedResources=new JButton("Show Unused Resources");
		jbExportUnusedResources=new JButton("Export in txt format");
		jbExportUnusedResources.setEnabled(false);
		jbUnusedResources.setEnabled(false);
		
		JPanel genericPanel1 = new JPanel();
		JPanel genericPanel2 = new JPanel();
		genericPanel1.setLayout(oneTwoGrid);
		genericPanel1.add(new JLabel("Resource Type : (Enter comma separated file types)"));
		jtInputFileTypes=new JTextField();
		genericPanel1.add(jtInputFileTypes);
		jtInputFileTypes.setToolTipText("Enter input like <png,pdf>");
		
		genericPanel2.setLayout(htmlLayout1);
		genericPanel2.add(new JLabel("Find Unused Resources"));
		genericPanel2.add(jbUnusedResources);
		genericPanel2.add(jbExportUnusedResources);
		
		jbUnusedHtml=new JButton("Show Unused Htmls");
		jbExportHtml=new JButton("Export in txt format");
		jbExportHtml.setEnabled(false);
		htmlFoundLabel=new JLabel("Htmls Found:0");
		htmlPanel1.add(htmlFoundLabel);
		htmlPanel1.add(jbUnusedHtml);
		htmlPanel1.add(jbExportHtml);
		//Border titleBorder = new TitledBorder(new LineBorder(Color.BLUE), "Unused HTMLs");
		//htmlPanel1.setBorder(titleBorder); 
		
		JPanel logoPanel = new JPanel();
		JPanel statusPanel = new JPanel();
		JPanel statusBCPanel = new JPanel();
		GridLayout readOnlyOneOne=new GridLayout(1,1);
		statusLogPathPanel = new JPanel();
		statusLogHtmlPanel = new JPanel();
		statusLog404=new JPanel();
		JPanel statusLogCSSPanel = new JPanel();
		JPanel statusLogJSPanel = new JPanel();
		statusLogMinPanel = new JPanel();
		
		cbxDupSel=new JCheckBox("Also Check for Duplicate Selectors");
		cbxDupSel.setSelected(true);
		
		statusLogPathPanel.setLayout(readOnlyOneOne);
		statusLogPathPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		statusLogPathPanel.setBackground(Color.LIGHT_GRAY);
		statusLogPathLabel = new JLabel("Status : waiting for inputs...");
		statusLogPathPanel.add(statusLogPathLabel);
		
		statusLogMinPanel.setLayout(readOnlyOneOne);
		statusLogMinPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		statusLogMinPanel.setBackground(Color.LIGHT_GRAY);
		statusLogMinLabel = new JLabel("Status : Press the button against the file which you want to minify. JS Section starts below.");
		statusLogMinPanel.add(statusLogMinLabel);
		
		statusLogHtmlPanel.setLayout(readOnlyOneOne);
		statusLogHtmlPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		statusLogHtmlPanel.setBackground(Color.LIGHT_GRAY);
		statusHTMLPathLabel = new JLabel("Status : Click on the respective button to find unused html or other unused resource files...");
		statusLogHtmlPanel.add(statusHTMLPathLabel);
		
		statusLog404.setLayout(readOnlyOneOne);
		statusLog404.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		statusLog404.setBackground(Color.LIGHT_GRAY);
		status404Label=new JLabel("Status : Click the button to check for 404 Resources");
		statusLog404.add(status404Label);
		
				
		GridLayout readOnlyLayout=new GridLayout(1,2);
		statusPanel.setLayout(readOnlyLayout);
		statusBCPanel.setLayout(readOnlyLayout);
		statusPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		statusPanel.setBackground(Color.LIGHT_GRAY);
		statusBCPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		statusBCPanel.setBackground(Color.LIGHT_GRAY);
		statusLabel = new JLabel("Project Directory :");
		statusPanel.add(statusLabel);
		
		workspaceLabel = new JLabel("Project Directory Not Set");
		statusPanel.add(workspaceLabel);
		
		bCompDirLabel = new JLabel("Beyond Compare Directory :");
		statusBCPanel.add(bCompDirLabel);
		bCompLabel = new JLabel("Beyond Compare Directory Not Set");
		statusBCPanel.add(bCompLabel);
		
		inputHtmlPanel = new JPanel();
		GridLayout oneTwoThree = new GridLayout(1,3);
		inputHtmlPanel.setLayout(oneTwoThree);
		workspaceInputLabel=new JLabel("Project Directory :");
		jtInputHtml=new JTextField();
		jbBrowseHtml=new JButton("Browse");
		
		
		bCompInputLabel=new JLabel("Beyond Compare Path (Optional) :");
		jtInputBComp=new JTextField();
		jbBrowseBComp=new JButton("Browse");
		JPanel inputBCompPanel = new JPanel();
		inputBCompPanel.setLayout(oneTwoThree);
		inputBCompPanel.add(bCompInputLabel);
		inputBCompPanel.add(jtInputBComp);
		inputBCompPanel.add(jbBrowseBComp);
		
		
		inputHtmlPanel.add(workspaceInputLabel);
		inputHtmlPanel.add(jtInputHtml);
		inputHtmlPanel.add(jbBrowseHtml);
		
		ImageIcon configImg = new ImageIcon(imageGear);
		ImageIcon cssImg = new ImageIcon(cssGear);
		ImageIcon jsImg = new ImageIcon(jsGear);
		ImageIcon minImg = new ImageIcon(minGear);
		spiderImg = new ImageIcon(spiderGear);
		gearImg = new ImageIcon(gearGear);
		bcImg = new ImageIcon(bcGear);
		resetImg=new ImageIcon(resetGear);
		qn1Img=new ImageIcon(qn1Gear);
		Img404=new ImageIcon(Gear404);
		
		
		crawlPanel = new JPanel();
		jbCrawlHtml=new JButton("Crawl",spiderImg);
		jbReset=new JButton("Reset",resetImg);
		jbHowToUse=new JButton("How to Use?",qn1Img);
		jbCrawlHtml.setEnabled(false);
		jbReset.setEnabled(false);
		crawlPanel.add(jbCrawlHtml);
		crawlPanel.add(jbReset);
		crawlPanel.add(jbHowToUse);
		
		jpConfigPanel.add(new JLabel("HTML Section"));
		jpConfigPanel.add(htmlPanel1);
		jpConfigPanel.add(temp);
		jpConfigPanel.add(new JLabel("Generic Section"));
		jpConfigPanel.add(genericPanel1);
		jpConfigPanel.add(genericPanel2);
		jpConfigPanel.add(statusLogHtmlPanel);
		

		
	
		//jpHtmlPanel.add(logoPanel);
		jpHtmlPanel.add(statusBCPanel);
		jpHtmlPanel.add(statusPanel);
		jpHtmlPanel.add(inputBCompPanel);
		jpHtmlPanel.add(inputHtmlPanel);
		jpHtmlPanel.add(crawlPanel);
		
		jpHtmlPanel.add(statusLogPathPanel);
		
		this.fileDialogWorkSpaceDir = new JFileChooser();
		this.fileDialogWorkSpaceDir.setMultiSelectionEnabled(false);
		this.fileDialogWorkSpaceDir.setCurrentDirectory(new File("D:/"));
		this.fileDialogWorkSpaceDir.setFileSelectionMode(1);
		
		this.fileDialogBCompDir = new JFileChooser();
		this.fileDialogBCompDir.setMultiSelectionEnabled(false);
		this.fileDialogBCompDir.setCurrentDirectory(new File("D:/"));
		this.fileDialogBCompDir.setFileSelectionMode(1);
		
		
		JPanel jp404_1=new JPanel();
		JPanel jp404_2=new JPanel();
		jb404Find=new JButton("Show 404 Resources");
		jb404Export=new JButton("Export in txt format");
		jt404=new JTextField();
		jb404Find.setEnabled(false);
		jb404Export.setEnabled(false);
		jp404_1.setLayout(oneTwoGrid);
		jp404_2.setLayout(oneTwoThree);
		jp404Panel.setLayout(new GridLayout(4, 1));
		jp404Panel.add(new JLabel("Find resources which are being referenced but are not present in the WebContent:"));
		jp404_1.add(new JLabel("Resource Types: (Enter comma separated file types)"));
		jp404_1.add(jt404);
		jp404_2.add(new JLabel("Find 404 References"));
		jp404_2.add(jb404Find);
		jp404_2.add(jb404Export);
		jp404Panel.add(jp404_1);
		jp404Panel.add(jp404_2);
		jp404Panel.add(statusLog404);
		
		JPanel jpSettings=new JPanel(new GridLayout(3, 1));
		JPanel tmp1=new JPanel(new GridLayout(1, 2));
		JPanel tmp2=new JPanel(new GridLayout(1, 2));
		JPanel tmp3=new JPanel();
		tmp1.add(new JLabel("CSS Linebreak:"));
		jtCSSSetting=new JTextField(5);
		jtCSSSetting.setText(String.valueOf(Worker.intCSSSetting));
		tmp1.add(jtCSSSetting);
		jpSettings.add(tmp1);
		tmp2.add(new JLabel("JS Linebreak:"));
		jtJSSetting=new JTextField(5);
		jtJSSetting.setText(String.valueOf(Worker.intJSSetting));
		tmp2.add(jtJSSetting);
		jpSettings.add(tmp2);
		jbSettingOK=new JButton("OK");
		tmp3.add(jbSettingOK);
		jpSettings.add(tmp3);
		
		scrollFrameCSS = new JScrollPane(jpCSSPanel, 
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,  
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollFrameJS = new JScrollPane(jpJSPanel, 
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,  
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		scrollFrameMinify = new JScrollPane(jpMinPanel, 
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,  
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		scrollFrame404 = new JScrollPane(jp404Panel, 
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,  
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		jtabs.addTab("Set Path ", gearImg, jpHtmlPanel, "Configure paths here");
		jtabs.addTab("Unused Resources ", configImg, jpConfigPanel, "Find out unused resources here");
		jtabs.addTab("Cleanup CSS ", cssImg, scrollFrameCSS, "Find out unused CSS here");
		jtabs.addTab("Cleanup Javascript ", jsImg, scrollFrameJS, "Find out unused Javascript here");
		jtabs.addTab("Minify ", minImg, scrollFrameMinify, "Minify Files here");
		jtabs.addTab("404 Resources ", Img404, scrollFrame404, "Find the resources which are not present in the Web-Content but still being refernced");
		
		jtabs.setEnabledAt(1,false);
		jtabs.setEnabledAt(2,false);
		jtabs.setEnabledAt(3,false);
		jtabs.setEnabledAt(4,false);
		jtabs.setEnabledAt(5,false);
		
		jbCrawlHtml.addActionListener(this);
		jbReset.addActionListener(this);
		jbHowToUse.addActionListener(this);
		jbUnusedHtml.addActionListener(this);
		jbExportHtml.addActionListener(this);
		jbUnusedResources.addActionListener(this);
		jb404Find.addActionListener(this);
		jb404Export.addActionListener(this);
		jbSettings.addActionListener(this);
		jbSettingOK.addActionListener(this);
		jbExportUnusedResources.addActionListener(this);
		JTextPane panelHTUFrame=new JTextPane();
		JScrollPane scrollHTUFrame=new JScrollPane(panelHTUFrame);
		
		JTextPane panelFrame=new JTextPane();
		JScrollPane scrollFrame=new JScrollPane(panelFrame);
		
		panelFrameResources=new JTextPane();
		scrollFrameResources=new JScrollPane(panelFrameResources);
		panelFrameResources.setEditable(false);
		panelFrameResources.setBackground(Color.LIGHT_GRAY);
		scrollFrameResources.setBackground(Color.LIGHT_GRAY);
		logResourcesFrame=new JFrame("Unused Resources");
		logResourcesFrame.setSize(550,250);
		logResourcesFrame.add(scrollFrameResources);
		logResourcesFrame.setBackground(Color.LIGHT_GRAY);
		docRes = panelFrameResources.getStyledDocument();
		keyWordRes = new SimpleAttributeSet();
		logResourcesFrame.setSize(550,250);
		logResourcesFrame.add(scrollFrameResources);
		panelFrameResources.setEditable(false);
		
		
		panelFrame404=new JTextPane();
		JScrollPane scrollFrame404=new JScrollPane(panelFrame404);
		panelFrame404.setEditable(false);
		panelFrame404.setBackground(Color.LIGHT_GRAY);
		scrollFrame404.setBackground(Color.LIGHT_GRAY);
		logFrame404=new JFrame("404 Resources");
		logFrame404.setSize(550,250);
		logFrame404.add(scrollFrame404);
		logFrame404.setBackground(Color.LIGHT_GRAY);
		doc404 = panelFrame404.getStyledDocument();
		keyWord404 = new SimpleAttributeSet();
		
				
		panelFrame.setEditable(false);
		panelFrame.setBackground(Color.LIGHT_GRAY);
		scrollFrame.setBackground(Color.LIGHT_GRAY);
		logFrame=new JFrame("Unused HTML Files");
		logFrame.setSize(550,250);
		logFrame.add(scrollFrame);
		logFrame.setBackground(Color.LIGHT_GRAY);
		logFrame.setLocationByPlatform(true);
		doc = panelFrame.getStyledDocument();
		keyWord = new SimpleAttributeSet();
		
		logFrame.setSize(550,250);
		logFrame.add(scrollFrame);
		
		
		panelHTUFrame.setEditable(false);
		HTUFrame=new JFrame("How to Use?");
		HTUFrame.setSize(550,250);
		HTUFrame.add(scrollHTUFrame);
		HTUFrame.setBackground(Color.LIGHT_GRAY);
		HTUFrame.setLocationByPlatform(true);
		docHTU = panelHTUFrame.getStyledDocument();
		keyWordHTU = new SimpleAttributeSet();
	
		StyleConstants.setFontFamily(keyWord, "Times New Roman");
		StyleConstants.setFontFamily(keyWordHTU, "Times New Roman");
		StyleConstants.setFontFamily(keyWordRes, "Times New Roman");
		StyleConstants.setFontFamily(keyWord404, "Times New Roman");
		
		jbBrowseBComp.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
				int returnVal = SpiderUI.this.fileDialogBCompDir.showOpenDialog(SpiderUI.this.frame);
				if (returnVal == 0) {
					fileBCompDir = fileDialogBCompDir.getSelectedFile();
					//    inputDirValue.setText(fileJspInputDir.toString());
					bCompLabel.setText(fileBCompDir.getAbsolutePath());
					jtInputBComp.setText(fileBCompDir.getAbsolutePath());
					sourceBCompDir=new File(bCompLabel.getText());
					validateBComp();
				}
				else {
					//Kraken.this.scan.setEnabled(false);
				}
			}
		});
		
		jbBrowseHtml.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
				int returnVal = SpiderUI.this.fileDialogWorkSpaceDir.showOpenDialog(SpiderUI.this.frame);
				if (returnVal == 0) {
					fileWorkSpaceDir = fileDialogWorkSpaceDir.getSelectedFile();
					//    inputDirValue.setText(fileJspInputDir.toString());
					workspaceLabel.setText(fileWorkSpaceDir.getAbsolutePath());
					jtInputHtml.setText(fileWorkSpaceDir.getAbsolutePath());
					sourceWorkSpaceDir=new File(workspaceLabel.getText());
					enableDisable();
				}
				else {
					//Kraken.this.scan.setEnabled(false);
				}
			}
		});
		jtabs.addChangeListener(new ChangeListener() {
	        public void stateChanged(ChangeEvent e) {
	            if(jtabs.getSelectedIndex()==0){
	            	frame.setSize(Constants.intDefaultFrameWidth, Constants.intDefaultFrameHeight);
	            }
	        }
	    });
		jtabs.addChangeListener(new ChangeListener() {
	        public void stateChanged(ChangeEvent e) {
	            if(jtabs.getSelectedIndex()==1){
	            	frame.setSize(Constants.intDefaultFrameWidth, Constants.intDefaultFrameHeight);
	            	if(Worker.liDisplayHtmlFilesVO==null || Worker.liDisplayHtmlFilesVO.size()==0){
	            		htmlFoundLabel.setText("Find Unused Htmls");
	            	}
	            	else{
	            		htmlFoundLabel.setText(Worker.liDisplayHtmlFilesVO.size() +" unused Htmls found");
	            	}
	            	
	            }
	        }
	    });
		jtabs.addChangeListener(new ChangeListener() {
	        public void stateChanged(ChangeEvent e) {
	            if(jtabs.getSelectedIndex()==2){
	            	frame.setSize(Constants.intDefaultFrameWidth, Constants.intDefaultFrameHeight);
	            	if(Worker.liAllCssFilesVO.size()<=5){
	            		frame.setSize(Constants.intDefaultFrameWidth, 280);
	            	}
	            	if(Worker.liAllCssFilesVO.size()==1){
	            		frame.setSize(Constants.intDefaultFrameWidth, 160);
	            	}
	            	if(Worker.liAllCssFilesVO.size()==2){
	            		frame.setSize(Constants.intDefaultFrameWidth, 200);
	            	}
	            }
	        }
	    });
		jtabs.addChangeListener(new ChangeListener() {
	        public void stateChanged(ChangeEvent e) {
	            if(jtabs.getSelectedIndex()==3){
	            	frame.setSize(Constants.intDefaultFrameWidth, Constants.intDefaultFrameHeight);
	            	if(Worker.liAllJsFilesWithUnusedFunctionsVO.size()<=5){
	            		frame.setSize(Constants.intDefaultFrameWidth, 280);
	            	}
	            	if(Worker.liAllJsFilesWithUnusedFunctionsVO.size()==1){
	            		frame.setSize(Constants.intDefaultFrameWidth, 160);
	            	}
	            	if(Worker.liAllJsFilesWithUnusedFunctionsVO.size()==2){
	            		frame.setSize(Constants.intDefaultFrameWidth, 200);
	            	}
	            }
	        }
	    });
		jtabs.addChangeListener(new ChangeListener() {
	        public void stateChanged(ChangeEvent e) {
	            if(jtabs.getSelectedIndex()==4){
	            	frame.setSize(Constants.intDefaultFrameWidth, Constants.intDefaultFrameHeight);
	            }
	        }
	    });
		jtabs.addChangeListener(new ChangeListener() {
	        public void stateChanged(ChangeEvent e) {
	            if(jtabs.getSelectedIndex()==5){
	            	frame.setSize(Constants.intDefaultFrameWidth, Constants.intDefaultFrame404Height);
	            }
	        }
	    });
		
		DocumentListener documentListener404Type= new DocumentListener(){
			public void removeUpdate(DocumentEvent e){
				validate404Type();
			}
			public void insertUpdate(DocumentEvent e){
				validate404Type();
			}
			public void changedUpdate(DocumentEvent e){
				validate404Type();
			}
		};
		jt404.getDocument().addDocumentListener(documentListener404Type);
		
		DocumentListener documentListenerFileType= new DocumentListener(){
			public void removeUpdate(DocumentEvent e){
				validateFileType();
			}
			public void insertUpdate(DocumentEvent e){
				validateFileType();
			}
			public void changedUpdate(DocumentEvent e){
				validateFileType();
			}
		};
		jtInputFileTypes.getDocument().addDocumentListener(documentListenerFileType);
		
		
		DocumentListener documentListenerBComp= new DocumentListener(){
			public void removeUpdate(DocumentEvent e){
				sourceBCompDir=new File(jtInputBComp.getText());
				validateBComp();
			}
			public void insertUpdate(DocumentEvent e){
				sourceBCompDir=new File(jtInputBComp.getText());
				validateBComp();
			}
			public void changedUpdate(DocumentEvent e){
				sourceBCompDir=new File(jtInputBComp.getText());
				validateBComp();
			}
		};
		jtInputBComp.getDocument().addDocumentListener(documentListenerBComp);
		
		
		DocumentListener documentListenerInput = new DocumentListener(){
			public void removeUpdate(DocumentEvent e){
				validateInput();
				enableDisable();
			}
			public void insertUpdate(DocumentEvent e){
				validateInput();
				enableDisable();
			}
			public void changedUpdate(DocumentEvent e){
			}
		};
		jtInputHtml.getDocument().addDocumentListener(documentListenerInput);
		
		
		jtabs.setSelectedIndex(0);
		
	this.frame = new JFrame("Web Optimization Tool");
	this.frame.setSize(Constants.intDefaultFrameWidth, Constants.intDefaultFrameHeight);
	this.frame.setDefaultCloseOperation(3);
	this.frame.setLocationRelativeTo(null);
	this.frame.setResizable(false);
	this.frame.setVisible(true);
	JPanel p= new JPanel(new GridLayout(2, 1));
	JPanel p1= new JPanel(new GridLayout(1, 1));
	JPanel p2= new JPanel(new GridLayout(1, 1));
	JPanel plogo= new JPanel(new GridLayout(1, 1));
	plogo.setLayout(new FlowLayout(FlowLayout.RIGHT));
	p1.add(plogo);
	plogo.setPreferredSize(new Dimension(Constants.intDefaultFrameWidth, 40));
	p2.add(jtabs);
	p.add(p1);
	p.add(p2);
	this.frame.setLayout(new BorderLayout());
	//this.frame.add(plogo, BorderLayout.PAGE_START);
	this.frame.add(jtabs,BorderLayout.CENTER);
	//this.frame.add(p);
	
	this.settingsFrame = new JFrame("Linebreak Settings");
	this.settingsFrame.add(jpSettings);
	this.settingsFrame.setSize(300,140);
	this.settingsFrame.setLocationRelativeTo(null);
	this.settingsFrame.setVisible(false);
	
	//this.frame.setIconImage(logo);
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==jbCrawlHtml){
			Worker.fileDir=new File(Constants.strExpDirectory);
			Worker.fileDir.mkdir();
			frameLayout = new GridLayout(7,1);
			jpHtmlPanel.setLayout(frameLayout);
			this.frame.setSize(Constants.intDefaultFrameWidth, Constants.intDefaultFrameHeight+35);
			jpHtmlPanel.remove(statusLogPathPanel);
			jpHtmlPanel.add(progressBar);
			jpHtmlPanel.add(statusLogPathPanel);
			
			jbBrowseHtml.setEnabled(false);
			jbBrowseBComp.setEnabled(false);
			jbReset.setEnabled(false);
			jtInputHtml.setEnabled(false);
			jtInputBComp.setEnabled(false);
			
			crawlPanel.remove(jbCrawlHtml);
			crawlPanel.remove(jbReset);
			crawlPanel.remove(jbHowToUse);
			jbCrawlHtml.setText("Crawling...");
			jbCrawlHtml.setEnabled(false);
			jbReset.setEnabled(false);
			crawlPanel.add(jbCrawlHtml);
			crawlPanel.add(jbReset);
			crawlPanel.add(jbHowToUse);
			statusLogPathLabel.setText("Crawling through Project Configuration...");
			new Thread(){
				public void run(){
					Worker.resetValues();
					fetchAllFiles(sourceWorkSpaceDir.toString());
					Collections.sort(Worker.liAllCssFilesVO, new SizeComparator());
					Collections.sort(Worker.liAllJsFilesVO, new SizeComparator());
					jpCSSPanel.removeAll();
					populateCSSPanel(jpCSSPanel);
					try {
						jpJSPanel.removeAll();
						populateJSPanel(jpJSPanel);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					jpMinPanel.removeAll();
					populateMinifyPanel(jpMinPanel);
					try {Thread.sleep(100);} catch (InterruptedException ignore) {}
				}
			}.start();
			
			new Thread(){
				public void run(){
					while(Worker.counter<Worker.max){
					progressBar.setValue((int)(((double)Worker.counter/Worker.max)*100));
					try {Thread.sleep(100);} catch (InterruptedException ignore) {}
				}
					if(Worker.counter==Worker.max){
						progressBar.setValue(100);
						try {Thread.sleep(1000L);} catch (InterruptedException ignore) {}
						frameLayout = new GridLayout(6,1);
						jpHtmlPanel.setLayout(frameLayout);
						jpHtmlPanel.remove(progressBar);
						frame.setSize(Constants.intDefaultFrameWidth, Constants.intDefaultFrameHeight);
						jtInputHtml.setEnabled(true);
						jtInputBComp.setEnabled(true);
						jbBrowseHtml.setEnabled(true);
						jbBrowseBComp.setEnabled(true);
						
						crawlPanel.remove(jbCrawlHtml);
						crawlPanel.remove(jbReset);
						crawlPanel.remove(jbHowToUse);
						jbCrawlHtml.setText("Crawl");
						jbCrawlHtml.setEnabled(true);
						jbReset.setEnabled(true);
						crawlPanel.add(jbCrawlHtml);
						crawlPanel.add(jbReset);
						crawlPanel.add(jbHowToUse);
						
						JOptionPane.showMessageDialog(jpHtmlPanel, "Crawl Complete for Project Path :"+sourceWorkSpaceDir.getAbsolutePath()+"\nPlease click OK Button to proceed.", "Message", JOptionPane.INFORMATION_MESSAGE);
						statusLogPathLabel.setText("Crawling completed...");
						
						jtabs.setEnabledAt(1,true);
						jtabs.setEnabledAt(2,true);
						jtabs.setEnabledAt(3,true);
						jtabs.setEnabledAt(4,true);
						jtabs.setEnabledAt(5,true);
						jtabs.setSelectedIndex(1);
						//try {Thread.sleep(1000L);jtabs.setSelectedIndex(1);} catch (InterruptedException ignore) {}
						
					}
				}
			}.start();
		}
		try {
			if(e.getSource()==jbUnusedHtml){
				//Worker.refreshValues();
				if(Worker.liDisplayHtmlFilesVO!=null && Worker.liDisplayHtmlFilesVO.size()!=0){
					if(!logFrame.isVisible()){
						logFrame.setVisible(true);
					}
				}
				else{
					tempLayout = new GridLayout(8,1);
					jpConfigPanel.setLayout(tempLayout);
					jpConfigPanel.remove(statusLogHtmlPanel);
					jpConfigPanel.add(progressBarHTML);
					jpConfigPanel.add(statusLogHtmlPanel);
					statusHTMLPathLabel.setText("Status : Searching Unused HTML files...");
				new Thread(){
					public void run(){
						try {
							frame.setSize(Constants.intDefaultFrameWidth, frame.getHeight()+45);
							jbExportHtml.setEnabled(false);
							jbUnusedResources.setEnabled(false);
							jtInputFileTypes.setEnabled(false);
							jbExportUnusedResources.setEnabled(false);
							Worker.findUnusedHtml1();
						} catch (IOException e) {e.printStackTrace();}
						
						if(Worker.liDisplayHtmlFilesVO!=null && !Worker.liDisplayHtmlFilesVO.isEmpty()){
							for(int i=0;i<Worker.liDisplayHtmlFilesVO.size();i++){
								try {
										doc.insertString(doc.getLength(), (i+1)+". "+Worker.liDisplayHtmlFilesVO.get(i).getFile().getAbsolutePath()+"\n", null );	
								} catch (BadLocationException e1) {
									e1.printStackTrace();
								}
							}
							if(!logFrame.isVisible()){
								logFrame.setVisible(true);
							}
						}
						
						try {Thread.sleep(100);} catch (InterruptedException ignore) {}
					}
				}.start();

				new Thread(){
					public void run(){
						while(Worker.counterHTML<Worker.maxHTML){
							progressBarHTML.setValue((int)(((double)Worker.counterHTML/Worker.maxHTML)*100));
							try {Thread.sleep(100);} catch (InterruptedException ignore) {}
						}
						if(Worker.counterHTML==Worker.maxHTML){
							progressBarHTML.setValue(100);
							try {Thread.sleep(1000L);} catch (InterruptedException ignore) {}
							tempLayout = new GridLayout(7,1);
							jpConfigPanel.setLayout(tempLayout);
							jpConfigPanel.remove(progressBarHTML);
							frame.setSize(Constants.intDefaultFrameWidth, frame.getHeight()-45);
							jbExportHtml.setEnabled(true);
							jbUnusedResources.setEnabled(true);
							jbExportUnusedResources.setEnabled(true);
							jtInputFileTypes.setEnabled(true);
							statusHTMLPathLabel.setText("Status : Search Complete.");
							validateFileType();
							//jpConfigPanel.add(progressBarHTML);
							//jpConfigPanel.add(statusLogHtmlPanel);
						}
					}
				}.start();
				}
				jbExportHtml.setEnabled(true);
			}
			if(e.getSource()==jbExportHtml){
				File newDir=new File(Worker.fileDir.getAbsolutePath());
				newDir.mkdir();
				FileWriter fw=new FileWriter(newDir.getAbsolutePath()+"\\"+"UnusedHtml.txt"); 
				BufferedWriter out = new BufferedWriter(fw);
				if(Worker.liDisplayHtmlFilesVO!=null && !Worker.liDisplayHtmlFilesVO.isEmpty()){
					for(int i=0;i<Worker.liDisplayHtmlFilesVO.size();i++){
						try {
							out.write((i+1)+". "+Worker.liDisplayHtmlFilesVO.get(i).getFile().getAbsolutePath()+"\n");
							out.newLine();
							//doc.insertString(doc.getLength(), (i+1)+". "+Worker.liDisplayHtmlFilesVO.get(i).getFile().getAbsolutePath()+"\n", null );
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				}
				out.close();
				JOptionPane.showMessageDialog (null, "File \"UnusedHtml.txt\" has been exported to "+newDir.getAbsolutePath()+"\\", "File Exported", JOptionPane.INFORMATION_MESSAGE);
				statusHTMLPathLabel.setText("Status : UnusedHtml.txt has been exported to "+newDir.getAbsolutePath());
			}
			if(e.getSource()==jbReset){
				jtInputHtml.setText("");
				jtInputBComp.setText("");
				workspaceLabel.setText("Not a valid directory");
				bCompLabel.setText("Beyond Compare Directory Not Set");
				Worker.resetValues();
			}
			if(e.getSource()==jbHowToUse){
				try {
					Worker.populateInstructions();
					if(docHTU.getLength()==0){
						for(int i=0;i<Worker.liInstructions.size();i++){
							docHTU.insertString(docHTU.getLength(), Worker.liInstructions.get(i)+"\n", null );
						}
					}
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
				HTUFrame.setVisible(true);
			}
		
			if(Constants.strCleanupCSS.equalsIgnoreCase(e.getActionCommand())){
				for(int i=0;i<this.jbCSSGenerate.length;i++){	
					if (this.jbCSSGenerate[i] == e.getSource()){
						Worker.cleanupCSS2(Worker.liAllCssFilesVO.get(i));	
						File newDir=new File(Worker.fileDir.getAbsolutePath()+"\\CleanedUpCSS\\");
						File f = new File(Worker.fileDir.getAbsolutePath()+"\\CleanedUpCSS\\"+Worker.liAllCssFilesVO.get(i).getFile().getName());
						String strMessage="CSS Clean-Up Done!\nCleaned-up file has been placed at "+newDir.getAbsolutePath()+"\\";
						if(cbxDupSel.isSelected()){
							Worker.mapDupSel=new HashMap<String, ArrayList<FileVO>>();
							Worker.removeDupSelectors(f);
							WorkerUtility.writeDupSelector(f);
							if(!Worker.mapDupSel.isEmpty()){
								strMessage+="\nDuplicate Selectors Found! "+Worker.liAllCssFilesVO.get(i).getFile().getName()+"_DupSelector.txt has been exported." ;
							}
						}
						if(f.exists() && bCompFound && !strBComp.isEmpty()){
							Process runtime1 = Runtime.getRuntime().exec("\""+sourceBCompDir+"\\"+strBComp+".exe"+"\" " 
									+"\""+Worker.liAllCssFilesVO.get(i).getFile().getAbsolutePath()+ "\" "+"\""+newDir.getAbsolutePath()+"\\"+Worker.liAllCssFilesVO.get(i).getFile().getName()+"\"");
						}
						JOptionPane.showMessageDialog (null,strMessage, Worker.liAllCssFilesVO.get(i).getFile().getName()+" - Cleaned", JOptionPane.INFORMATION_MESSAGE);		
					}
				}
			}
			if(Constants.strCleanupJS.equalsIgnoreCase(e.getActionCommand())){
				for(int i=0;i<this.jbJSGenerate.length;i++){	
					if (this.jbJSGenerate[i] == e.getSource()){
						Worker.cleanupJS(Worker.liAllJsFilesWithUnusedFunctionsVO.get(i));	
						File newDir=new File(Worker.fileDir.getAbsolutePath()+"\\CleanedUpJS\\");
						newDir.mkdir();
						File f = new File(newDir.getAbsolutePath()+"\\"+Worker.liAllJsFilesWithUnusedFunctionsVO.get(i).getFile().getName());
						if(f.exists() && bCompFound){
						Process runtime1 = Runtime.getRuntime().exec("\""+sourceBCompDir+"\\"+strBComp+".exe"+"\" " 
								+"\""+Worker.liAllJsFilesWithUnusedFunctionsVO.get(i).getFile().getAbsolutePath()+ "\" "+"\""+ newDir.getAbsolutePath()+ "\\"+Worker.liAllJsFilesWithUnusedFunctionsVO.get(i).getFile().getName()+"\"");
					}
					JOptionPane.showMessageDialog (null, "JS Clean-Up Done!\nCleaned-up file has been placed at "+newDir.getAbsolutePath()+"\\", Worker.liAllJsFilesWithUnusedFunctionsVO.get(i).getFile().getName()+" - Cleaned", JOptionPane.INFORMATION_MESSAGE);				
					}
				}
			}
			if(Constants.strMinifyCSS.equalsIgnoreCase(e.getActionCommand())){
				for(int i=0;i<this.jbMinGenerate.length;i++){	
					if (this.jbMinGenerate[i] == e.getSource()){
						File newDir=new File(Worker.fileDir.getAbsolutePath()+"\\MinifiedCSS\\");
						newDir.mkdir();
						CssCompressor css=new CssCompressor(new InputStreamReader(new FileInputStream(Worker.liAllCssFilesVO.get(i).getFile())));
						FileWriter fw=new FileWriter(newDir.getAbsolutePath()+"\\"+Worker.liAllCssFilesVO.get(i).getFile().getName()); 
						//-1 to compress whole file in a single line
						//0 for every new selector on new line
						//if we give any larger no. say 1000, then it will try to accommodate 1000 characters on a single line, the larger the number the more minified the file will be
						css.compress(fw, Worker.intCSSSetting);
						fw.close();
						JOptionPane.showMessageDialog (null, "Minification Done!\nMinified File has been placed at "+newDir.getAbsolutePath()+"\\", Worker.liAllCssFilesVO.get(i).getFile().getName()+" - Minified", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
			if(Constants.strMinifyJS.equalsIgnoreCase(e.getActionCommand())){
				for(int i=0;i<this.jbMinJsGenerate.length;i++){	
					if (this.jbMinJsGenerate[i] == e.getSource()){
						File newDir=new File(Worker.fileDir.getAbsolutePath()+"\\MinifiedJS\\");
						newDir.mkdir();
						//Worker.minifyJSFile(Worker.liAllJsFilesVO.get(i).getFile(),newDir.getAbsolutePath()+"\\"+Worker.liAllJsFilesVO.get(i).getFile().getName());						
						JavaScriptCompressor js=new JavaScriptCompressor(new InputStreamReader(new FileInputStream(Worker.liAllJsFilesVO.get(i).getFile())), null);
						FileWriter fw=new FileWriter(newDir.getAbsolutePath()+"\\"+Worker.liAllJsFilesVO.get(i).getFile().getName());
						//-1 to compress whole file in a single line
						//0 for every new selector on new line
						//if we give any larger no. say 1000, then it will try to accommodate 1000 characters on a single line, the larger the number the more minified the file will be
						js.compress(fw, Worker.intJSSetting, false, false, true, true);
						fw.close();
						JOptionPane.showMessageDialog (null, "Minification Done!\nMinified File has been placed at "+newDir.getAbsolutePath()+"\\", Worker.liAllJsFilesVO.get(i).getFile().getName()+" - Minified", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
			if(e.getSource()==jbUnusedResources){
				tempLayout = new GridLayout(8,1);
				jpConfigPanel.setLayout(tempLayout);
				jpConfigPanel.remove(statusLogHtmlPanel);
				jpConfigPanel.add(progressBarUnused);
				jpConfigPanel.add(statusLogHtmlPanel);
				Worker.counterUnused=0;
				progressBarUnused.setValue(Worker.counterUnused);
				jbUnusedHtml.setEnabled(false);
				jbExportHtml.setEnabled(false);
				jbUnusedResources.setEnabled(false);
				jbExportUnusedResources.setEnabled(false);
				jtInputFileTypes.setEnabled(false);
				
				panelFrameResources=new JTextPane();
				scrollFrameResources=new JScrollPane(panelFrameResources);
				panelFrameResources.setEditable(false);
				panelFrameResources.setBackground(Color.LIGHT_GRAY);
				scrollFrameResources.setBackground(Color.LIGHT_GRAY);
				logResourcesFrame=new JFrame("Unused Resources");
				logResourcesFrame.setSize(550,250);
				logResourcesFrame.add(scrollFrameResources);
				logResourcesFrame.setBackground(Color.LIGHT_GRAY);
				docRes = panelFrameResources.getStyledDocument();
				
				this.frame.setSize(Constants.intDefaultFrameWidth, this.frame.getHeight()+45);
				statusHTMLPathLabel.setText("Status : Searching Unused Resources of type "+jtInputFileTypes.getText()+"...");
				new Thread(){
					public void run(){
						String arTypes[]=jtInputFileTypes.getText().split(",");
						Worker.map = new HashMap<String, ArrayList<FileVO>>();
						Worker.mapUnused = new HashMap<String, ArrayList<FileVO>>();
						for(String str:arTypes){
							str=Worker.cleanType(str);
							Worker.map.put(str, new ArrayList<FileVO>());
							Worker.mapUnused.put(str, new ArrayList<FileVO>());
						}
						int totalSize = 0;
						WorkerUtility.fetchSelectedFileTypes(sourceWorkSpaceDir.toString(),Worker.map);
						/*for (Map.Entry<String, ArrayList<FileVO>> entry : Worker.map.entrySet()){
							totalSize=totalSize+entry.getValue().size();
						}*/
						Worker.counterUnused=0;
						Worker.maxUnused=Worker.liAllFilesVO.size();
						List<FileVO> liFiles = new ArrayList<>();
						for (Map.Entry<String, ArrayList<FileVO>> entry : Worker.map.entrySet()){
							liFiles.addAll(entry.getValue());
						}
						
						try {
							WorkerUtility.findUnusedResources(liFiles);
						} catch (IOException e) {
							e.printStackTrace();
						}
						
						boolean blWrite=false;
						for (Map.Entry<String, ArrayList<FileVO>> entry : Worker.mapUnused.entrySet()){
							try {
								if(Worker.mapUnused.get(entry.getKey()).size()!=0){
									blWrite=true;
									docRes.insertString(docRes.getLength(), "Unused "+entry.getKey() +" files:-"+"\n", null );
								}
							} catch (BadLocationException e) {
								e.printStackTrace();
							}
							for(int i=0;i<Worker.mapUnused.get(entry.getKey()).size();i++){
								if(Worker.mapUnused.get(entry.getKey())!=null && Worker.mapUnused.get(entry.getKey()).size()!=0){
									try {
										docRes.insertString(docRes.getLength(), (i+1)+". "+Worker.mapUnused.get(entry.getKey()).get(i).getFile().getAbsolutePath()+"\n", null );
									} catch (BadLocationException e) {
										e.printStackTrace();
									}						
								}
							}		
							try {
								docRes.insertString(docRes.getLength(), "\n", null );
							} catch (BadLocationException e) {
								e.printStackTrace();
							}
						}
						
						//write here
						if(blWrite){
							logResourcesFrame.setVisible(true);
							statusHTMLPathLabel.setText("Status : Unused Resources found of type "+jtInputFileTypes.getText());
						}
						else{
							statusHTMLPathLabel.setText("Status : 0 Unused Resources found of type "+jtInputFileTypes.getText());
						}
						try {Thread.sleep(100);} catch (InterruptedException ignore) {}
					}
				}.start();
			new Thread(){
				public void run(){
					while(Worker.counterUnused<Worker.maxUnused){
						progressBarUnused.setValue((int)(((double)Worker.counterUnused/Worker.maxUnused)*100));
						try {Thread.sleep(100);} catch (InterruptedException ignore) {}
					}
					if(Worker.counterUnused==Worker.maxUnused){
						progressBarUnused.setValue(100);
						try {Thread.sleep(1000L);} catch (InterruptedException ignore) {}
						tempLayout = new GridLayout(7,1);
						jpConfigPanel.setLayout(tempLayout);
						jpConfigPanel.remove(progressBarUnused);
						jbUnusedHtml.setEnabled(true);
						jbExportHtml.setEnabled(true);
						jbExportUnusedResources.setEnabled(true);
						jtInputFileTypes.setEnabled(true);
						validateFileType();
						frame.setSize(Constants.intDefaultFrameWidth, frame.getHeight()-45);
						statusHTMLPathLabel.setText("Status : Search Complete for "+jtInputFileTypes.getText());
					}
				}
			}.start();
			}
			
			if(e.getSource()==jbExportUnusedResources){
				File newDir=new File(Worker.fileDir.getAbsolutePath());
				newDir.mkdir();
				FileWriter fw=new FileWriter(newDir.getAbsolutePath()+"\\"+"UnusedResources.txt"); 
				BufferedWriter out = new BufferedWriter(fw);
				for (Map.Entry<String, ArrayList<FileVO>> entry : Worker.mapUnused.entrySet()){
					if(Worker.mapUnused.get(entry.getKey())!=null && Worker.mapUnused.get(entry.getKey()).size()!=0){
						out.write("Unused "+entry.getKey() +" files:-");
						out.newLine();
						for(int i=0;i<Worker.mapUnused.get(entry.getKey()).size();i++){
							out.write((i+1)+". "+Worker.mapUnused.get(entry.getKey()).get(i).getFile().getAbsolutePath());
							out.newLine();
						}
					}
					out.newLine();
				}
				out.close();
				JOptionPane.showMessageDialog (null, "File \"UnusedResources.txt\" has been exported to "+newDir.getAbsolutePath()+"\\", "File Exported", JOptionPane.INFORMATION_MESSAGE);
				statusHTMLPathLabel.setText("Status : UnusedResources.txt has been exported to "+newDir.getAbsolutePath());
			}
			if(e.getSource()==jb404Find){
				tempLayout = new GridLayout(5,1);
				jp404Panel.setLayout(tempLayout);
				jp404Panel.remove(statusLog404);
				jp404Panel.add(progress404);
				jp404Panel.add(statusLog404);
				jb404Find.setEnabled(false);
				jb404Export.setEnabled(false);
				jt404.setEnabled(false);
				
				panelFrame404=new JTextPane();
				JScrollPane scrollFrame404=new JScrollPane(panelFrame404);
				panelFrame404.setEditable(false);
				panelFrame404.setBackground(Color.LIGHT_GRAY);
				scrollFrame404.setBackground(Color.LIGHT_GRAY);
				logFrame404=new JFrame("404 Resources");
				logFrame404.setSize(550,250);
				logFrame404.add(scrollFrame404);
				logFrame404.setBackground(Color.LIGHT_GRAY);
				doc404 = panelFrame404.getStyledDocument();
				keyWord404 = new SimpleAttributeSet();
				
				this.frame.setSize(Constants.intDefaultFrameWidth, this.frame.getHeight()+45);
				new Thread(){
					public void run(){
						String ar404types[]=jt404.getText().split(",");
						Worker.map404 = new HashMap<String, ArrayList<FileVO>>();
						Worker.mapUnused404 = new HashMap<String, ArrayList<FileVO>>();
						Worker.li404Resources=new ArrayList<FileVO>();
						for(String str:ar404types){
							str=Worker.cleanType(str);
							Worker.map404.put(str, new ArrayList<FileVO>());
							Worker.mapUnused404.put(str, new ArrayList<FileVO>());
						}
						for (Map.Entry<String, ArrayList<FileVO>> entry : Worker.map404.entrySet()){
							WorkerUtility.fetchSelectedFileTypesNew(sourceWorkSpaceDir.toString(),entry.getKey(),Worker.map404);
						}
						for (Map.Entry<String, ArrayList<FileVO>> entry : Worker.map404.entrySet()){
							try {
								WorkerUtility.find404References(Worker.map404.get(entry.getKey()),entry.getKey());
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						int i=0;
						for(FileVO liFile:Worker.li404Resources){
							i++;
							try {
								doc404.insertString(doc404.getLength(), i+". "+"File : "+liFile.getFile().getAbsolutePath() + "\t\n 404 Resource : "+liFile.getStr404File()+"\n", null );
								doc404.insertString(doc404.getLength(), "Code at Line No."+liFile.getIntLineNo()+" : " +liFile.getStrCode()+"\n", null );
								doc404.insertString(doc404.getLength(), "\n", null );
							} catch (BadLocationException e) {
								e.printStackTrace();
							}	
						}
						status404Label.setText("Status : "+Worker.li404Resources.size() +" References found for "+jt404.getText());
						try {Thread.sleep(100);} catch (InterruptedException ignore) {}
					}
				}.start();
				
				new Thread(){
					public void run(){
						while(Worker.counter404<Worker.max404){
							progress404.setValue((int)(((double)Worker.counter404/Worker.max404)*100));
							try {Thread.sleep(100);} catch (InterruptedException ignore) {}
						}
						if(Worker.counter404==Worker.max404){
							progress404.setValue(100);
							try {Thread.sleep(1000L);} catch (InterruptedException ignore) {}
							tempLayout = new GridLayout(4,1);
							jp404Panel.setLayout(tempLayout);
							jp404Panel.remove(progress404);
							if(Worker.li404Resources.size()!=0){
								logFrame404.setVisible(true);
							}
							frame.setSize(Constants.intDefaultFrameWidth, frame.getHeight()-45);
							
							jb404Find.setEnabled(true);
							jb404Export.setEnabled(true);
							jt404.setEnabled(true);
						}
					}
				}.start();
			}
			if(e.getSource()==jb404Export){
				if(Worker.li404Resources!=null && !Worker.li404Resources.isEmpty()){
					int i=0;
					File newDir=new File(Worker.fileDir.getAbsolutePath());
					newDir.mkdir();
					FileWriter fw=new FileWriter(newDir.getAbsolutePath()+"\\"+"404Resources.txt"); 
					BufferedWriter out = new BufferedWriter(fw);
					for(FileVO liFile:Worker.li404Resources){
						i++;
						out.write(i+". "+"File : "+liFile.getFile().getAbsolutePath() + "\t 404 Resource : "+liFile.getStr404File());
						out.newLine();
						out.write("Code at Line No."+liFile.getIntLineNo()+" : " +liFile.getStrCode());
						out.newLine();
						out.newLine();
					}
					out.close();
					status404Label.setText("Status : File \"404Resources.txt\" has been exported to "+newDir.getAbsolutePath());
					JOptionPane.showMessageDialog (null, "File \"404Resources.txt\" has been exported to "+newDir.getAbsolutePath()+"\\", "File Exported", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		if(e.getSource()==jbSettings){
			this.settingsFrame.setVisible(true);
		}
		if(e.getSource()==jbSettingOK){
			try{
			int cssSet=Integer.parseInt(jtCSSSetting.getText());
			int jsSet=Integer.parseInt(jtJSSetting.getText());
			Worker.intCSSSetting=cssSet;
			Worker.intJSSetting=jsSet;
			}
			catch(Exception exp){
				JOptionPane.showMessageDialog (null, "An error occured - Invalid Input","Error!", JOptionPane.ERROR_MESSAGE);
				jtCSSSetting.setText(String.valueOf(Worker.intCSSSetting));
				jtJSSetting.setText(String.valueOf(Worker.intJSSetting));
				//Worker.intCSSSetting=0;
				//Worker.intJSSetting=0;
			}
			this.settingsFrame.setVisible(false);
		}
		
	}
	
	public void populateMinifyPanel(JPanel jpMinPanel){
		GridLayout minLayout = new GridLayout(Worker.liAllCssFilesVO.size()+Worker.liAllJsFilesVO.size()+1,1);
		jpMinPanel.setLayout(minLayout);
		JPanel jpSet=new JPanel(new FlowLayout(FlowLayout.LEFT));
		jpSet.add(jbSettings);
		jpMinPanel.add(jpSet);
		
		JPanel jpMinGrid=new JPanel();
		jpMinGrid.setLayout(oneTwoGrid);
		JLabel jLabel=null;
		jbMinGenerate=new JButton[Worker.liAllCssFilesVO.size()];
		jbMinJsGenerate=new JButton[Worker.liAllJsFilesVO.size()];
		int k=0;
		for(int i=0;i<Worker.liAllCssFilesVO.size();i++){
			jpMinGrid=new JPanel();
			jpMinGrid.setLayout(oneTwoGrid);
			jLabel=new JLabel((i+1)+". "+Worker.liAllCssFilesVO.get(i).getStrFileName()+"  "+Worker.liAllCssFilesVO.get(i).getIntFileSize()+" "+Worker.liAllCssFilesVO.get(i).getStrFileSizeType());
			jLabel.setToolTipText(Worker.liAllCssFilesVO.get(i).getFile().getPath());
			jbMinGenerate[i]=new JButton(Constants.strMinifyCSS);
			jbMinGenerate[i].setToolTipText("Minify "+Worker.liAllCssFilesVO.get(i).getStrFileName());
			jbMinGenerate[i].addActionListener(this);
			jpMinGrid.add(jLabel);
			jpMinGrid.add(jbMinGenerate[i]);
			jpMinPanel.add(jpMinGrid);
			k=i;
		}
		for(int i=0;i<Worker.liAllJsFilesVO.size();i++){
			jpMinGrid=new JPanel();
			jpMinGrid.setLayout(oneTwoGrid);
			jLabel=new JLabel((i+1)+". "+Worker.liAllJsFilesVO.get(i).getStrFileName()+"  "+Worker.liAllJsFilesVO.get(i).getIntFileSize()+" "+Worker.liAllJsFilesVO.get(i).getStrFileSizeType());
			jLabel.setToolTipText(Worker.liAllJsFilesVO.get(i).getFile().getPath());
			jbMinJsGenerate[i]=new JButton(Constants.strMinifyJS);
			jbMinJsGenerate[i].setToolTipText("Minify "+Worker.liAllJsFilesVO.get(i).getStrFileName());
			jbMinJsGenerate[i].addActionListener(this);
			jpMinGrid.add(jLabel);
			jpMinGrid.add(jbMinJsGenerate[i]);
			jpMinPanel.add(jpMinGrid);
		}
	}
	
	
	public void populateJSPanel(JPanel jpJSPanel) throws IOException{
		Worker.populateJSFiles();
		GridLayout jsLayout = new GridLayout(Worker.liAllJsFilesWithUnusedFunctionsVO.size(),1);
		jpJSPanel.setLayout(jsLayout);
		JPanel jpJSGrid=new JPanel();
		jpJSGrid.setLayout(oneTwoGrid);
		JLabel jLabel=null;
		jbJSGenerate=new JButton[Worker.liAllJsFilesWithUnusedFunctionsVO.size()];
		
		for(int i=0;i<Worker.liAllJsFilesWithUnusedFunctionsVO.size();i++){
			jpJSGrid=new JPanel();
			jpJSGrid.setLayout(oneTwoGrid);
			jLabel=new JLabel((i+1)+". "+Worker.liAllJsFilesWithUnusedFunctionsVO.get(i).getStrFileName()+"  "+Worker.liAllJsFilesWithUnusedFunctionsVO.get(i).getIntFileSize()+" "+Worker.liAllJsFilesWithUnusedFunctionsVO.get(i).getStrFileSizeType());
			jLabel.setToolTipText(Worker.liAllJsFilesWithUnusedFunctionsVO.get(i).getFile().getPath());
			if(bCompFound){
				jbJSGenerate[i]=new JButton(Constants.strCleanupJS,bcImg);
			}
			else{
				jbJSGenerate[i]=new JButton(Constants.strCleanupJS);
			}
			jbJSGenerate[i].addActionListener(this);
			jbJSGenerate[i].setToolTipText("Clean-up "+Worker.liAllJsFilesWithUnusedFunctionsVO.get(i).getStrFileName());
			jpJSGrid.add(jLabel);
			jpJSGrid.add(jbJSGenerate[i]);
			jpJSPanel.add(jpJSGrid);
		}
	}
	
	public void populateCSSPanel(JPanel jpCSSPanel){
		GridLayout cssLayout = new GridLayout(Worker.liAllCssFilesVO.size()+1,1);
		jpCSSPanel.setLayout(cssLayout);
		
		JPanel jpCBX=new JPanel();
		jpCBX.setLayout(new FlowLayout(FlowLayout.LEFT));
		jpCBX.add(cbxDupSel);
		jpCSSPanel.add(jpCBX);
		
		JPanel jpCSSGrid=new JPanel();
		jpCSSGrid.setLayout(oneTwoGrid);
		JLabel jLabel=null;
		jbCSSGenerate=new JButton[Worker.liAllCssFilesVO.size()];
		
		for(int i=0;i<Worker.liAllCssFilesVO.size();i++){
			jpCSSGrid=new JPanel();
			jpCSSGrid.setLayout(oneTwoGrid);
			jLabel=new JLabel((i+1)+". "+Worker.liAllCssFilesVO.get(i).getStrFileName()+"  "+Worker.liAllCssFilesVO.get(i).getIntFileSize()+" "+Worker.liAllCssFilesVO.get(i).getStrFileSizeType());
			jLabel.setToolTipText(Worker.liAllCssFilesVO.get(i).getFile().getPath());
			if(bCompFound){
				jbCSSGenerate[i]=new JButton(Constants.strCleanupCSS,bcImg);
			}
			else{
				jbCSSGenerate[i]=new JButton(Constants.strCleanupCSS);
			}
			jbCSSGenerate[i].addActionListener(this);
			jbCSSGenerate[i].setToolTipText("Clean-up "+Worker.liAllCssFilesVO.get(i).getStrFileName());
			jpCSSGrid.add(jLabel);
			jpCSSGrid.add(jbCSSGenerate[i]);
			jpCSSPanel.add(jpCSSGrid);
		}
	}

	public void fetchAllFiles(String directoryName){
		File directory = new File(directoryName);
		File[] fList = directory.listFiles();
		for (File file : fList) {
			if (file.isFile()) {
				if((!file.getPath().contains(".svn")) && (file.getPath().endsWith(".java") || file.getPath().endsWith(".html") || file.getPath().endsWith(".jsp") || file.getPath().endsWith(".js") || file.getPath().endsWith(".css") || file.getPath().endsWith(".xml") || file.getPath().endsWith(".properties"))){
				//if((!file.getPath().contains(".svn") && !file.getPath().endsWith(".java")) && (file.getPath().endsWith(".jsp") || file.getPath().endsWith(".js") || file.getPath().endsWith(".css") || file.getPath().endsWith(".xml") || file.getPath().endsWith(".properties"))){
					FileVO objFileVO=new FileVO();
					objFileVO.setFile(file);
					objFileVO.setStrFileName(file.getName());
					int size=(int)file.length()/1024;
					if(size==0){
						objFileVO.setIntFileSize((int)file.length());
						objFileVO.setStrFileSizeType("B");
					}
					else{
						objFileVO.setIntFileSize((int)file.length()/1024);
						objFileVO.setStrFileSizeType("KB");
					}
					
					Worker.liAllFilesVO.add(objFileVO);
					if(file.getPath().endsWith(".html") ){
						Worker.liAllHtmlFilesVO.add(objFileVO);
					}
					else if(file.getPath().endsWith(".css") ){
						Worker.liAllCssFilesVO.add(objFileVO);
					}
					else if(file.getPath().endsWith(".js") ){
						Worker.liAllJsFilesVO.add(objFileVO);
					}
					else if(file.getPath().endsWith(".jsp") ){
						Worker.liAllJspFilesVO.add(objFileVO);
					}
				}
			} else if (file.isDirectory()) {
				if(!file.getPath().endsWith("classes") && !file.getPath().endsWith("lib") && !file.getPath().endsWith(".svn") && !file.getPath().endsWith("bin")){
					fetchAllFiles(file.getAbsolutePath());
				}
			}
		}
	}
	public void validateInput(){
		testFile=null;
		testFile=new File(jtInputHtml.getText().trim());
		if(testFile.isDirectory()){
			workspaceLabel.setText(jtInputHtml.getText().trim());
			source=testFile;
		}
		else{
			workspaceLabel.setText("Not a valid directory");
			source=null;
		}
	}
	public void enableDisable(){
		if(source==null ){
			jbCrawlHtml.setEnabled(false);
			jbReset.setEnabled(false);
			statusLogPathLabel.setText("Status : Project Directory Not Set");
		}
		else if(source!=null){
			sourceWorkSpaceDir=new File(workspaceLabel.getText());
			jbCrawlHtml.setEnabled(true);
			jbReset.setEnabled(true);
			File f = new File(workspaceLabel.getText()+"\\.project");
			if(f.exists())
				statusLogPathLabel.setText("Status : Project Found!");
			else
				statusLogPathLabel.setText("Status : This is not a project directory. Though I can still work.");
		}

	}
	public void validateBComp(){
		testBCompFile=null;
		// for beyond compare 3 and above
		File f = new File(sourceBCompDir+"\\BComp.exe");
		// for beyond compare 2
		File fileBC2 = new File(sourceBCompDir+"\\BC2.exe");
		if(f.exists() || fileBC2.exists()){
			bCompFound=true;
			bCompLabel.setText(sourceBCompDir.getAbsolutePath());
			statusLogPathLabel.setText("Status : Beyond Compare Found!");
			if(f.exists())
				strBComp="BComp";
			else if(fileBC2.exists())
				strBComp="BC2";
		}
		else{
			statusLogPathLabel.setText("Status : Beyond Compare not found. It will not be launched.");
			bCompLabel.setText("Beyond Compare Directory Not Set");
			bCompFound=false;
		}
	}
	public void validateFileType(){
		if(jtInputFileTypes.getText().isEmpty()){
			jbUnusedResources.setEnabled(false);
			jbExportUnusedResources.setEnabled(false);
		}
		else{
			jbUnusedResources.setEnabled(true);
		}
	}
	public void validate404Type(){
		if(jt404.getText().isEmpty()){
			jb404Find.setEnabled(false);
			jb404Export.setEnabled(false);
		}
		else{
			jb404Find.setEnabled(true);
		}
	}
	
	
	
}
