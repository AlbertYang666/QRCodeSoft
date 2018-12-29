/**  
 
* @Title: RunFrame.java
 
* @Package com.albertyy.QrCode
 
* @Description: TODO
 
* @author yangxianyang  
 
* @date 2018年12月10日 下午8:06:46
 
* @version V1.0  
 
*/

package com.albertyy.QrCode;

/**  
*   
* 项目名称：QRCodeSoft  
* 类名称：RunFrame  
* 类描述：主类，窗口类  
* 创建人：yangyangyang  
* 创建时间：2018年12月10日 下午8:06:46  
* 修改人：yangyangyang  
* 修改时间：2018年12月10日 下午8:06:46  
* 修改备注：  
* @version   
*   
*/

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
 
 
 
 
public class RunFrame extends JFrame implements ActionListener {
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 3106386478150246852L;
	JPanel jp;
	JButton jb1, jb2;
	JLabel jl1;
	JTextArea jtf;
	JScrollPane js;
	
 
	public RunFrame() {
		setTitle("二维码生成器");
		setSize(400, 300);
		setLocation(500, 230);
		setResizable(false);
		jl1 = new JLabel("***************************请输入文字或链接***************************");
		jtf = new JTextArea();
		js=new JScrollPane(jtf);
		jp=new JPanel();
		jb1 = new JButton("开始");
		jb1.addActionListener(this);
		jb2 = new JButton("退出");
		jb2.addActionListener(this);
		jp.add(jb1);jp.add(jb2);
		this.add(js);
		this.add(jl1, BorderLayout.NORTH);
		this.add(jp,BorderLayout.SOUTH);
 
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
 
	public static void main(String[] args) {
 
		RunFrame rc = new RunFrame();
	}
 
	@Override
	public void actionPerformed(ActionEvent e) {
 
		if (e.getSource() == jb1) {
			String text =jtf.getText(); 
			try {
                //照片路径，和生成二维码的路径
				QRCodeUtil.encode(text, "f:/我.jpg", "f:/photo", true);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (e.getSource() == jb2) {
			System.exit(1);
		}
	}
}

