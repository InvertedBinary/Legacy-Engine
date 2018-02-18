package com.IB.SL;

import com.IB.SL.network.Server;

public class BootDedi
{

	public static final String title = "Legacy Engine Dedicated Server [Build 1: 2/11/18]";
	private int port = 7381;

	public Server s;

	public BootDedi()
		{
			this.s = new Server(getPort());
			try {
				s.run();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	public static void main(String[] args) // TODO: Add Port as Argument
		{
			System.out.println("Initiating..\n\n------------------------------\n");
			new BootDedi();
		}

	public int getPort()
		{
			return port;
		}

	public void setPort(int port)
		{
			this.port = port;
		}

	public void startServer()
		{
			try {
				s.run();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
