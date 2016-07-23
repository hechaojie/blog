package com.blog.front.server;

import com.jfinal.core.JFinal;

public class Server {

	public static void main(String[] args) {
		JFinal.start("src/main/webapp", 10000, "/", 5);
	}

}
