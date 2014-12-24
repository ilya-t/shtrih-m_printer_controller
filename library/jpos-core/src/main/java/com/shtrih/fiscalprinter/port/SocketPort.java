/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shtrih.fiscalprinter.port;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import com.shtrih.util.Localizer;

/**
 * @author V.Kravtsov
 */
public class SocketPort implements PrinterPort {

	private int timeout = 3000;
	private final Socket socket;
	private InputStream in;
	private OutputStream out;
	private String portName = "";
	static Logger logger = Logger.getLogger(SocketPort.class);

	public SocketPort() throws Exception {
		socket = new Socket();
	}

	@Override
	public void open(int timeout) throws Exception {
		if (!socket.isConnected()) {
			socket.setSoTimeout(this.timeout);

			StringTokenizer tokenizer = new StringTokenizer(portName, ":");
			String host = tokenizer.nextToken();
			int port = Integer.parseInt(tokenizer.nextToken());
			socket.connect(new InetSocketAddress(host, port));
			in = socket.getInputStream();
			out = socket.getOutputStream();
		}
	}

	void waitConnected(int timeout) throws Exception {
		long startTime = System.currentTimeMillis();
		for (;;) {
			Thread.sleep(0, 001);
			long currentTime = System.currentTimeMillis();
			if (socket.isConnected()) {
				break;
			}
			if ((currentTime - startTime) > timeout) {
				noConnectionError();
			}
		}
	}

	@Override
	public void close() throws Exception {
		socket.close();
	}

	@Override
	public int readByte() throws Exception {
		int b = doReadByte();
		return b;
	}

	public int doReadByte() throws Exception {
		int result;
		long startTime = System.currentTimeMillis();
		for (;;) {
			Thread.sleep(0, 001);
			long currentTime = System.currentTimeMillis();
			if (in.available() > 0) {
				result = in.read();
				if (result >= 0) {
					return result;
				}
			}
			if ((currentTime - startTime) > timeout) {
				noConnectionError();
			}
		}
	}

	private void noConnectionError() throws Exception {
		throw new IOException(Localizer.getString(Localizer.NoConnection));
	}

	@Override
	public byte[] readBytes(int len) throws Exception {
		byte[] data = new byte[len];
		int offset = 0;
		while (len > 0) {
			int count = in.read(data, offset, len);
			if (count == -1) {
				break;
			}
			len -= count;
			offset += count;
		}
		return data;
	}

	public void connect() throws Exception {
		if (!socket.isConnected()) {
			open(10000);
		}
	}

	@Override
	public void write(byte[] b) throws Exception {
		for (int i = 0; i < 2; i++) {
			try {
				connect();
				out.write(b);
				out.flush();
				return;
			} catch (IOException e) {
				socket.close();
				if (i == 1)
					throw e;
				logger.error(e);
			}
		}
	}

	@Override
	public void write(int b) throws Exception {
		out.write(b);
		out.flush();
	}

	@Override
	public void setBaudRate(int baudRate) throws Exception {
	}

	@Override
	public void setTimeout(int timeout) throws Exception {
		this.timeout = timeout;
	}

	@Override
	public void setPortName(String portName) throws Exception {
		this.portName = portName;
	}

	public InputStream getInputStream() throws Exception {
		return socket.getInputStream();
	}

	public OutputStream getOutputStream() throws Exception {
		return socket.getOutputStream();
	}

}
