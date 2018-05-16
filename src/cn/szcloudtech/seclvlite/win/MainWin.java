package cn.szcloudtech.seclvlite.win;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import cn.szcloudtech.ctlog.CTLog;
import cn.szcloudtech.seclvlite.service.ChannelDetector;
import cn.szcloudtech.seclvlite.service.ForwardManager;
import cn.szcloudtech.seclvlite.service.SecLevelService;
import cn.szcloudtech.seclvlite.util.Global;
import cn.szcloudtech.seclvlite.util.NetUtils;

public class MainWin extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3805649186153012448L;

	public MainWin() {
		JButton btnStartServer = new JButton("启动服务");
		JButton btnStopServer = new JButton("停止服务");
		JButton btnStartForward = new JButton("启动转发");
		JButton btnStopForward = new JButton("停止转发");
		JLabel lbListenPort = new JLabel("监听端口");
		JLabel lbForwardIP = new JLabel("转发地址");
		JLabel lbForwardPort = new JLabel("转发端口");
		JTextField tfListenPort = new JTextField();
		JTextField tfForwardIP = new JTextField();
		JTextField tfForwardPort = new JTextField();
		
		lbListenPort.setBounds(200, 50, 70, 30);
		tfListenPort.setBounds(300, 50, 150, 30);
		btnStartServer.setBounds(200, 100, 100, 30);
		btnStopServer.setBounds(350, 100, 100, 30);
		lbForwardIP.setBounds(200, 150, 70, 30);
		tfForwardIP.setBounds(300, 150, 150, 30);
		lbForwardPort.setBounds(200, 200, 70, 30);
		tfForwardPort.setBounds(300, 200, 150, 30);
		btnStartForward.setBounds(200, 250, 100, 30);
		btnStopForward.setBounds(350, 250, 100, 30);
		
		this.add(lbListenPort);
		this.add(tfListenPort);
		this.add(btnStartServer);
		this.add(btnStopServer);
		this.add(btnStartForward);
		this.add(btnStopForward);
		this.add(lbForwardIP);
		this.add(lbForwardPort);
		this.add(tfForwardIP);
		this.add(tfForwardPort);
		
		lbListenPort.setEnabled(true);
		tfListenPort.setEnabled(true);
		btnStartServer.setEnabled(true);
		btnStopServer.setEnabled(false);
		lbForwardIP.setEnabled(false);
		tfForwardIP.setEnabled(false);
		lbForwardPort.setEnabled(false);
		tfForwardPort.setEnabled(false);
		btnStartForward.setEnabled(false);
		btnStopForward.setEnabled(false);
		
		this.setLayout(null);
		this.setTitle("̫秒级站采集软件");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(650, 350);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setResizable(false);
		//setup the log tool
		CTLog.setRootPath(Global.ROOT_PATH);
		CTLog.setEnabled(true);
		
		btnStartServer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String port = tfListenPort.getText();
				if (port.isEmpty()) {
					JOptionPane.showMessageDialog(null, "端口不能为空！", "错误", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (!NetUtils.isPortCorrect(port)) {
					JOptionPane.showMessageDialog(null, "端口格式不正确！", "错误", JOptionPane.ERROR_MESSAGE);
					return;
				}
				lbListenPort.setEnabled(false);
				tfListenPort.setEnabled(false);
				btnStartServer.setEnabled(false);
				btnStopServer.setEnabled(true);
				lbForwardIP.setEnabled(true);
				tfForwardIP.setEnabled(true);
				lbForwardPort.setEnabled(true);
				tfForwardPort.setEnabled(true);
				btnStartForward.setEnabled(true);
				btnStopForward.setEnabled(false);
				
				SecLevelService.getInstance().start(Integer.valueOf(port));
				ChannelDetector.getInstance().start();
			}
		});
		btnStopServer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				lbListenPort.setEnabled(true);
				tfListenPort.setEnabled(true);
				btnStartServer.setEnabled(true);
				btnStopServer.setEnabled(false);
				lbForwardIP.setEnabled(false);
				tfForwardIP.setEnabled(false);
				lbForwardPort.setEnabled(false);
				tfForwardPort.setEnabled(false);
				btnStartForward.setEnabled(false);
				btnStopForward.setEnabled(false);
				
				SecLevelService.getInstance().stop();
				ForwardManager.getInstance().stop();
				ChannelDetector.getInstance().stop();
			}
		});
		btnStartForward.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String ip = tfForwardIP.getText();
				String port = tfForwardPort.getText();
				if (ip.isEmpty()) {
					JOptionPane.showMessageDialog(null, "地址不能为空！", "错误", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (port.isEmpty()) {
					JOptionPane.showMessageDialog(null, "端口不能为空！", "错误", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (!NetUtils.isIpCorrect(ip)) {
					JOptionPane.showMessageDialog(null, "地址格式不正确！", "错误", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (!NetUtils.isPortCorrect(port)) {
					JOptionPane.showMessageDialog(null, "端口格式不正确！", "错误", JOptionPane.ERROR_MESSAGE);
					return;
				}
				lbForwardIP.setEnabled(false);
				tfForwardIP.setEnabled(false);
				lbForwardPort.setEnabled(false);
				tfForwardPort.setEnabled(false);
				btnStartForward.setEnabled(false);
				btnStopForward.setEnabled(true);
				ForwardManager.getInstance().start(ip, Integer.valueOf(port));
			}
		});
		btnStopForward.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				lbForwardIP.setEnabled(true);
				tfForwardIP.setEnabled(true);
				lbForwardPort.setEnabled(true);
				tfForwardPort.setEnabled(true);
				btnStartForward.setEnabled(true);
				btnStopForward.setEnabled(false);
				ForwardManager.getInstance().stop();
			}
		});
		
		this.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
				
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				SecLevelService.getInstance().stop();
				ForwardManager.getInstance().stop();
				ChannelDetector.getInstance().stop();
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				
			}
		});
	}
	
	public static void main(String[] args) {
		new MainWin();
	}

}
